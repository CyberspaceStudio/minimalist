package com.jijian.ppt.utils;

import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.*;
import java.util.List;

/**
 * 此类的主要目的是往图文页中插入内容
 * @author 武泽中
 * @version 1.0
 * @date 2020/3/26 17:00
 */
@Component
public class PictureUtil {


    public void insertText(Integer number,String titleContent,String textContent) throws IOException {
        InputStream is=new FileInputStream(new File("D:\\ppt\\test.pptx"));
        XMLSlideShow ppt=new XMLSlideShow(is);
        List<XSLFSlide> Slides = ppt.getSlides();
        XSLFSlide slide=Slides.get(4);//读取图文页

        XSLFTextShape text=slide.createTextBox();//插入文本框
        if(number.equals(2)){
            XSLFTextShape title=slide.createTextBox();//设置标题
            title.setAnchor(new Rectangle(10,400,800,50));
            text.setAnchor(new Rectangle(10,450,800,50));
            XSLFTextRun trTitle=title.addNewTextParagraph().addNewTextRun();
            trTitle.setText(titleContent);
        } else{
            XSLFTextShape title1 = slide.getPlaceholder(0);//title可访问内容部分
            title1.setText(titleContent);//设置标题
            if(number.equals(1)){
                text.setAnchor(new Rectangle(450,110,500,500));
            }else if(number.equals(3)){
                text.setAnchor(new Rectangle(50,420,700,200));
            }else if(number.equals(4)){
                text.setAnchor(new Rectangle(550,150,400,500));
            }else return;
        }
        XSLFTextRun trText=text.addNewTextParagraph().addNewTextRun();
        trText.setText(textContent);

        OutputStream os=new FileOutputStream(new File("D:\\ppt\\test.pptx"));
        ppt.write(os);
        ppt.close();
        os.close();
    }
    public void insertPicture(String p1) throws IOException {
        InputStream is=new FileInputStream(new File("D:\\ppt\\test.pptx"));
        XMLSlideShow ppt=new XMLSlideShow(is);
        List<XSLFSlide> Slides = ppt.getSlides();
        XSLFSlide slide=Slides.get(4);//读取图文页

        File picture1=new File(p1);//图片文件
        InputStream pictureIs=new FileInputStream(picture1);//图片文件输入流
        int len=(int)picture1.length();
        byte[] pictureData=new byte[len];
        pictureIs.read(pictureData);//将图片读入字节数组
        pictureIs.close();//关闭输入流

        XSLFPictureData pd=ppt.addPicture(pictureData, PictureData.PictureType.JPEG);//添加到ppt
        XSLFPictureShape pic=slide.createPicture(pd);//放入图文页
        pic.setAnchor(new Rectangle(50,100,400,400));

        OutputStream os=new FileOutputStream(new File("D:\\ppt\\test.pptx"));
        ppt.write(os);
        ppt.close();
        os.close();
    }
    public void insertPicture(String p1,String p2) throws IOException {
        InputStream is=new FileInputStream(new File("D:\\ppt\\test.pptx"));
        XMLSlideShow ppt=new XMLSlideShow(is);
        List<XSLFSlide> Slides = ppt.getSlides();
        XSLFSlide slide=Slides.get(4);//读取图文页

        File picture1=new File(p1);//图片文件
        InputStream pictureIs1=new FileInputStream(picture1);//图片文件输入流
        int len1=(int)picture1.length();
        byte[] pictureData1=new byte[len1];
        pictureIs1.read(pictureData1);//将图片读入字节数组
        pictureIs1.close();//关闭输入流

        XSLFPictureData pd1=ppt.addPicture(pictureData1, PictureData.PictureType.JPEG);//添加到ppt
        XSLFPictureShape pic1=slide.createPicture(pd1);//放入图文页
        pic1.setAnchor(new Rectangle(10,20,400,380));

        File picture2=new File(p2);//图片文件
        InputStream pictureIs2=new FileInputStream(picture2);//图片文件输入流
        int len2=(int)picture2.length();
        byte[] pictureData2=new byte[len2];
        pictureIs2.read(pictureData2);//将图片读入字节数组
        pictureIs2.close();//关闭输入流

        XSLFPictureData pd2=ppt.addPicture(pictureData2, PictureData.PictureType.JPEG);//添加到ppt
        XSLFPictureShape pic2=slide.createPicture(pd2);//放入图文页
        pic2.setAnchor(new Rectangle(450,20,400,380));

        OutputStream os=new FileOutputStream(new File("D:\\ppt\\test.pptx"));
        ppt.write(os);
        ppt.close();
        os.close();
    }
    public void insertPicture(String p1,String p2,String p3) throws IOException {
        InputStream is=new FileInputStream(new File("D:\\ppt\\test.pptx"));
        XMLSlideShow ppt=new XMLSlideShow(is);
        List<XSLFSlide> Slides = ppt.getSlides();
        XSLFSlide slide=Slides.get(4);//读取图文页

        File picture1=new File(p1);//图片文件
        InputStream pictureIs1=new FileInputStream(picture1);//图片文件输入流
        int len1=(int)picture1.length();
        byte[] pictureData1=new byte[len1];
        pictureIs1.read(pictureData1);//将图片读入字节数组
        pictureIs1.close();//关闭输入流

        XSLFPictureData pd1=ppt.addPicture(pictureData1, PictureData.PictureType.JPEG);//添加到ppt
        XSLFPictureShape pic1=slide.createPicture(pd1);//放入图文页
        pic1.setAnchor(new Rectangle(50,200,200,200));

        File picture2=new File(p2);//图片文件
        InputStream pictureIs2=new FileInputStream(picture2);//图片文件输入流
        int len2=(int)picture2.length();
        byte[] pictureData2=new byte[len2];
        pictureIs2.read(pictureData2);//将图片读入字节数组
        pictureIs2.close();//关闭输入流

        XSLFPictureData pd2=ppt.addPicture(pictureData2, PictureData.PictureType.JPEG);//添加到ppt
        XSLFPictureShape pic2=slide.createPicture(pd2);//放入图文页
        pic2.setAnchor(new Rectangle(350,200,200,200));

        File picture3=new File(p3);//图片文件
        InputStream pictureIs3=new FileInputStream(picture3);//图片文件输入流
        int len3=(int)picture3.length();
        byte[] pictureData3=new byte[len3];
        pictureIs3.read(pictureData3);//将图片读入字节数组
        pictureIs3.close();//关闭输入流

        XSLFPictureData pd3=ppt.addPicture(pictureData3, PictureData.PictureType.JPEG);//添加到ppt
        XSLFPictureShape pic3=slide.createPicture(pd3);//放入图文页
        pic3.setAnchor(new Rectangle(650,200,200,200));

        OutputStream os=new FileOutputStream(new File("D:\\ppt\\test.pptx"));
        ppt.write(os);
        ppt.close();
        os.close();

    }
    public void insertPicture(String p1,String p2,String p3,String p4) throws IOException {
        InputStream is=new FileInputStream(new File("D:\\ppt\\test.pptx"));
        XMLSlideShow ppt=new XMLSlideShow(is);
        List<XSLFSlide> Slides = ppt.getSlides();
        XSLFSlide slide=Slides.get(4);//读取图文页

        File picture1=new File(p1);//图片文件
        InputStream pictureIs1=new FileInputStream(picture1);//图片文件输入流
        int len1=(int)picture1.length();
        byte[] pictureData1=new byte[len1];
        pictureIs1.read(pictureData1);//将图片读入字节数组
        pictureIs1.close();//关闭输入流

        XSLFPictureData pd1=ppt.addPicture(pictureData1, PictureData.PictureType.JPEG);//添加到ppt
        XSLFPictureShape pic1=slide.createPicture(pd1);//放入图文页
        pic1.setAnchor(new Rectangle(50,100,200,200));

        File picture2=new File(p2);//图片文件
        InputStream pictureIs2=new FileInputStream(picture2);//图片文件输入流
        int len2=(int)picture2.length();
        byte[] pictureData2=new byte[len2];
        pictureIs2.read(pictureData2);//将图片读入字节数组
        pictureIs2.close();//关闭输入流

        XSLFPictureData pd2=ppt.addPicture(pictureData2, PictureData.PictureType.JPEG);//添加到ppt
        XSLFPictureShape pic2=slide.createPicture(pd2);//放入图文页
        pic2.setAnchor(new Rectangle(350,100,200,200));

        File picture3=new File(p3);//图片文件
        InputStream pictureIs3=new FileInputStream(picture3);//图片文件输入流
        int len3=(int)picture3.length();
        byte[] pictureData3=new byte[len3];
        pictureIs3.read(pictureData3);//将图片读入字节数组
        pictureIs3.close();//关闭输入流

        XSLFPictureData pd3=ppt.addPicture(pictureData3, PictureData.PictureType.JPEG);//添加到ppt
        XSLFPictureShape pic3=slide.createPicture(pd3);//放入图文页
        pic3.setAnchor(new Rectangle(50,320,200,200));

        File picture4=new File(p4);//图片文件
        InputStream pictureIs4=new FileInputStream(picture4);//图片文件输入流
        int len4=(int)picture4.length();
        byte[] pictureData4=new byte[len4];
        pictureIs4.read(pictureData4);//将图片读入字节数组
        pictureIs4.close();//关闭输入流

        XSLFPictureData pd4=ppt.addPicture(pictureData4, PictureData.PictureType.JPEG);//添加到ppt
        XSLFPictureShape pic4=slide.createPicture(pd4);//放入图文页
        pic4.setAnchor(new Rectangle(350,320,200,200));

        OutputStream os=new FileOutputStream(new File("D:\\ppt\\test.pptx"));
        ppt.write(os);
        ppt.close();
        os.close();
    }
}

