package com.jijian.ppt.utils;

import org.apache.poi.sl.usermodel.AutoShape;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.sl.usermodel.TextRun;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.List;

/**
 * 此类的主要目的是把ppt文件转换为xml文件
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/24 18:31
 */
@Component
public class PPToXmlUtil {

    public static XMLSlideShow getPPT() throws IOException {
//导入要修改的PPT
        InputStream is = new FileInputStream(new File("C:\\Users\\12456\\Desktop\\服务简报内容\\模板.pptx"));
        XMLSlideShow ppt = new XMLSlideShow(is);
        List<XSLFSlide> Slides = ppt.getSlides();
        for (XSLFSlide slide : Slides) {
            List<XSLFShape> shapes = slide.getShapes();
            for (XSLFShape shape : shapes) {
                if (shape != null) {
                    if (shape instanceof AutoShape) {
                        try {
                            if (((AutoShape) shape).getText().contains("{ye}")) {
// 替换文字内容.用TextRun获取替换的文本来设置样式

                                TextRun rt=((AutoShape) shape).setText(((AutoShape) shape).getText().replace("{ye}", "2018"));

                                rt.setFontColor(Color.red);
                                rt.setFontSize(13.5);
                                rt.setFontFamily("微软雅黑");

                            } else if (((AutoShape) shape).getText().contains("{mo}")) {
                                ((AutoShape) shape).setText(((AutoShape) shape).getText().replace("{mo}", "7"));
                            }
// System.out.println(((AutoShape)shape).getText());
                        } catch (Exception ex) {


                            ex.printStackTrace();
                        }
// }else {
// System.out.println("Process me: " +
// shape.getClass());
                    }
                }
            }
        }
        return ppt;
    }


    public static void PptExportUtil() throws IOException {
        //读取模板ppt
        SlideShow ppt = new XMLSlideShow(new FileInputStream(ResourceUtils.getFile("classpath:static/zhmd.pptx")));
        //提取文本信息
        List<XSLFSlide> slides = ppt.getSlides();
        //   SlideShow slideShow = copyPage(slides.get(1), ppt,2);
        for (XSLFSlide slide : slides) {
            List<XSLFShape> shapes = slide.getShapes();
            for(int i=0;i<shapes.size();i++){
                Rectangle2D anchor = shapes.get(i).getAnchor();
                if (shapes.get(i) instanceof XSLFTextBox) {
                    XSLFTextBox txShape = (XSLFTextBox) shapes.get(i);
                    if (txShape.getText().contains("{schemeName}")) {
                        // 替换文字内容.用TextRun获取替换的文本来设置样式
                        TextRun rt = txShape.setText(txShape.getText().replace("{schemeName}", "测试方案"));
                        rt.setFontColor(Color.BLACK);
                        rt.setFontSize(20.0);
                        rt.setBold(true);
                        rt.setFontFamily("微软雅黑");
                    }
                    else if (txShape.getText().contains("{time}")) {
                        TextRun textRun = txShape.setText(txShape.getText().replace("{time}", "2019-1-19"));
                        textRun.setFontColor(Color.BLACK);
                        textRun.setFontSize(20.0);
                        textRun.setFontFamily("微软雅黑");
                    }   else if (txShape.getText().contains("{projectAdd}")) {
                        TextRun textRun = txShape.setText(txShape.getText().replace("{projectAdd}", "成都市经江区"));
                        textRun.setFontColor(Color.BLACK);
                        textRun.setFontSize(16.0);
                        textRun.setFontFamily("微软雅黑");
                    } else if (txShape.getText().contains("{rzl}")) {
                        TextRun textRun = txShape.setText(txShape.getText().replace("{rzl}", "90%"));
                        textRun.setFontColor(Color.BLACK);
                        textRun.setFontSize(16.0);
                        textRun.setFontFamily("微软雅黑");
                    }
                    else if (txShape.getText().contains("{cg}")) {
                        TextRun textRun = txShape.setText(txShape.getText().replace("{cg}", "30"));
                        textRun.setFontColor(Color.BLACK);
                        textRun.setFontSize(16.0);
                        textRun.setFontFamily("微软雅黑");
                    }
                    else if (txShape.getText().contains("{mediaImg2}")) {
                        byte[] bytes = IOUtils.toByteArray(new FileInputStream(ResourceUtils.getFile("classpath:static/ceshi4.jpg")));
                        PictureData pictureData = ppt.addPicture(bytes, XSLFPictureData.PictureType.JPEG);
                        XSLFPictureShape picture = slide.createPicture(pictureData);
                        picture.setAnchor(anchor);
                    }
                    else if (txShape.getText().contains("{mediaImg1}")) {
                        byte[] bytes = IOUtils.toByteArray(new FileInputStream(ResourceUtils.getFile("classpath:static/ceshi4.jpg")));
                        PictureData pictureData = ppt.addPicture(bytes, XSLFPictureData.PictureType.JPEG);
                        XSLFPictureShape picture = slide.createPicture(pictureData);
                        picture.setAnchor(anchor);
                    }
                    else if(txShape.getText().contains("{projectImg}")){
                        byte[] bytes = IOUtils.toByteArray(new FileInputStream(ResourceUtils.getFile("classpath:static/ceshi5.jpg")));
                        PictureData pictureData = ppt.addPicture(bytes, XSLFPictureData.PictureType.JPEG);
                        XSLFPictureShape picture = slide.createPicture(pictureData);
                        picture.setAnchor(anchor);
                    }
                }
            }
        }
        OutputStream outputStreams = new FileOutputStream("F:\\test2.pptx");
        ppt.write(outputStreams);
    }


    /**
     * @return
     * @Author
     * @Description //TODO 复制ppt中的幻灯片 ,并设置幻灯片在ppt中的位置
     * @Date 2019/1/24 11:16
     * @Param slide：被复制的幻灯片，ppt：ppt对象， index：复制的ppt插入到第几页
     */
    public static XSLFSlide copyPage(XSLFSlide slide, XMLSlideShow ppt, int index) throws IOException {
        List<XSLFShape> shapes = slide.getShapes();
        XSLFSlide slide2 = ppt.createSlide();
        //   if (shapes.size() > 0) {
//        for (XSLFShape shape : shapes) {
//            slide2.importContent(shape.getSheet());
        //       }
//    }
        slide2.importContent(slide);
        //排序（在PPT中的第几页）
        ppt.setSlideOrder(slide2, index);
        return slide2;
    }
    public static void main(String[] args) {
        try {
            PptExportUtil();
            System.out.println("执行完成！！！！！！！！！");
        } catch (IOException e) { e.printStackTrace(); } }
}
