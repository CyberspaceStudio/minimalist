package com.jijian.ppt.guiyiqiao;

import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Service;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author 桂乙侨
 * @Date 2020/3/26 16:33
 * @Version 1.0
 */
@Service
public class GYQService {
    /**
     * 目录页
     * 接收String[] 类型的多个标题名，用作目录
     * 这里认为 封面页生成后 从封面页的下一页开始作为目录页  必须已经存在
     * @Param pptFileName 已经生成封面页的ppt文件名
     * @param titles 预计生成的目录小标题字符串数组
     */
    public void setContent(String pptFilePath,String[] titles){
        //从已有的封面页 PPT 文件导入 ppt 对象
        XMLSlideShow ppt = null;

        //打开 pptFileName 文件
        try (final FileInputStream inputStream = new FileInputStream(pptFilePath)){
            ppt = new XMLSlideShow(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            //文件打开失败，日志记录处，暂未设置记录
        }

        //获取目录页幻灯片，默认为第2页（从1开始）
        XSLFSlide contentSlide = null;
        assert ppt != null;
        if(ppt.getSlides().size() > 1){
            contentSlide = ppt.getSlides().get(1);
        }else {
            contentSlide = ppt.createSlide();
        }

        for(int i =0; i < titles.length;i++){
            //创建文本框
            final XSLFTextBox box = contentSlide.createTextBox();
            //设置 x y 坐标
            int x = 350,y = 300;
            y -=(50*(titles.length/2));
            box.setAnchor(new Rectangle(x,y+50*i,200,50));
            //创建段落 并 添加文字
            final XSLFTextParagraph paragraph = box.addNewTextParagraph();
            final XSLFTextRun textRun = paragraph.addNewTextRun();
            textRun.setText((i+1)+"."+titles[i]);
            textRun.setFontSize(30.0);
            textRun.setFontColor(Color.BLACK);
        }

        //生成 过渡页

        //文件输出
        try(FileOutputStream fileOutputStream = new FileOutputStream(pptFilePath)) {
            ppt.write(fileOutputStream);
        }catch (IOException e){
            e.printStackTrace();
            //文件打开失败，日志记录处，暂未设置记录
        }
    }

    /**
     *  图文页
     *  预计从导入的ppt文件后直接添加幻灯片
     * @param paths 图片访问路径数组，
     * @param text 文本框的文字
     */

    public void setImageText(String pptFilePath,String[] paths,String text){
        //从已有的封面页 PPT 文件导入 ppt 对象
        XMLSlideShow ppt = null;
        //打开 pptFileName 文件
        try (final FileInputStream inputStream = new FileInputStream(pptFilePath)){
            ppt = new XMLSlideShow(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
            //文件打开失败，日志记录处，暂未设置记录
        }
        assert ppt != null;

        //创建空白幻灯片
        XSLFSlide imageSlide = ppt.createSlide();
        //根据paths元素数量，执行不同策略,暂定图片最多为4
        int len = paths.length > 4 ? 4:paths.length;
        byte[][] pictureBytes = new byte[len][];
        try {
            for(int i = 0;i < len;i++){
                pictureBytes[i] = IOUtils.toByteArray(new FileInputStream(paths[i]));
            }
        } catch (IOException e) {
             e.printStackTrace();
            //文件打开失败，日志记录处，暂未设置记录
        }
        //坐标生成处
        final Rectangle[] rectangles = getImageTextRectangle(len);

        int i = 0;
        while(i < len) {
            //为 ppt 对象添加多个图片
            XSLFPictureData xslfPictureData = ppt.addPicture(pictureBytes[i], PictureType.PNG);//png jpg均能通过
            //将图片放在幻灯片上
            XSLFPictureShape picture = imageSlide.createPicture(xslfPictureData);
            //并设置大小与坐标
            picture.setAnchor(rectangles[i]);
            i++;
        }
        //添加文本框
        final XSLFTextBox textBox = imageSlide.createTextBox();
        //设置位置与大小
        textBox.setAnchor(rectangles[i]);
        //添加段落
        final XSLFTextParagraph paragraph = textBox.addNewTextParagraph();
        //创建文本框
        final XSLFTextRun xslfTextRun = paragraph.addNewTextRun();
        //添加文本内容
        xslfTextRun.setFontSize( 40.0);
        xslfTextRun.setFontColor(Color.BLACK);
        xslfTextRun.setText(text);

        //文件输出
        try(FileOutputStream fileOutputStream = new FileOutputStream(pptFilePath)) {
            ppt.write(fileOutputStream);
        }catch (IOException e){
            e.printStackTrace();
            //文件打开失败，日志记录处，暂未设置记录
        }
    }

    /**
     * 根据pic 图片数量，产生对应的坐标
     * @param picNum
     * @return
     */
    private Rectangle[] getImageTextRectangle(int picNum){
        assert picNum > 0;
        assert picNum < 5 ;
        Rectangle[] ret = new Rectangle[picNum+1];
        switch (picNum){
            case 1:
                ret[0] =new Rectangle(150, 50, 220, 165);
                ret[1] =new Rectangle(0, 300, 750, 150);
            break;
            case 2:
                ret[0] =new Rectangle(50, 50, 220, 165);
                ret[1] =new Rectangle(300, 50, 220, 165);
                ret[2] =new Rectangle(0, 300, 250, 150);
                break;
            case 3:ret[0] =new Rectangle(50, 50, 220, 165);
                ret[1] =new Rectangle(300, 50, 220, 165);
                ret[2] =new Rectangle(50, 220, 220, 165);
                ret[3] =new Rectangle(300, 220, 250, 190);
                break;
            case 4:ret[0] =new Rectangle(50, 20, 220, 165);
                ret[1] =new Rectangle(350, 20, 220, 165);
                ret[2] =new Rectangle(50, 200, 220, 165);
                ret[3] =new Rectangle(350, 200, 220, 165);
                ret[4] =new Rectangle(0, 380, 750, 150);
            break;
        }
        return  ret;
    }
}
