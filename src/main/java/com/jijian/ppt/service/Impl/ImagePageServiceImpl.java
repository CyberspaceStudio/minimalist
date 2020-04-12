package com.jijian.ppt.service.Impl;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.service.ImagePageService;
import com.jijian.ppt.mapper.FileDetailMapper;
import com.jijian.ppt.mapper.TemplateFileDetailMapper;
import com.jijian.ppt.utils.Enum.PageCategoryEnum;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.io.*;
import java.net.URL;

/**
 *图文页相关接口
 * @author 武泽中
 * @version 1.0
 * @date 2020/3/30 13：49
 */
@Service
@Slf4j
public class ImagePageServiceImpl implements ImagePageService {
    @Resource
    private FileDetailMapper fileDetailMapper;
    @Resource
    private TemplateFileDetailMapper templateFileDetailMapper;

    /**
     * 从inputStream中读取文件到byte数组
     * @param url
     * @return
     * @throws IOException
     */
    private static byte[] getByte(String url) throws IOException {
        URL picture=new URL(url);
        InputStream inStream=picture.openStream();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = inStream.read(buffer)) != -1) {
            //write(byte[] b, int off, int len)
            //将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte 数组输出流。
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }
    @Override
    public UniversalResponseBody<FileDetail> makeImagePage(Integer fileId,String[] pictureUrls,String title,String paragraph) throws IOException{
        //读取文件详细信息
        FileDetail fileDetail = fileDetailMapper.getDetailByFileId(fileId);

        //获取模板ppt文件的路径
        String templateFilePath =templateFileDetailMapper.GetTemplateFilePath(fileDetail.getTemplateId());

        //读取模板文件
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(templateFilePath));

        //获取模板的图文页
        XSLFSlide templateSlide = ppt.getSlides().get(PageCategoryEnum.PICTURE_PAGE.getPageCategoryId());
        //读取用户文件
        XMLSlideShow userFile = new XMLSlideShow(new FileInputStream(fileDetail.getFilePath()));
        //读取模板文件的排版
        XSLFSlideLayout layout = templateSlide.getSlideLayout();
        //将排版应用到用户文件
        XSLFSlide slide = userFile.createSlide(layout);
        slide.importContent(templateSlide);


        //插入文本
        Integer imageNumber=pictureUrls.length;
        XSLFTextShape text=slide.createTextBox();//插入文本框
        if(imageNumber.equals(2)){
            XSLFTextShape title1=slide.createTextBox();//设置标题
            title1.setAnchor(new Rectangle(10,400,800,50));//设置标题位置
            text.setAnchor(new Rectangle(10,450,800,50));//设置文本框位置
            XSLFTextRun trTitle=title1.addNewTextParagraph().addNewTextRun();//写入标题内容
            trTitle.setText(title);
        } else{
            XSLFTextShape title1 = slide.getPlaceholder(0);//标题为默认位置
            title1.setText(title);//写入标题内容
            if(imageNumber.equals(1)){
                text.setAnchor(new Rectangle(450,110,500,500));//设置文本框位置
            }else if(imageNumber.equals(3)){
                text.setAnchor(new Rectangle(50,420,700,200));//设置文本框位置
            }else if(imageNumber.equals(4)){
                text.setAnchor(new Rectangle(550,150,400,500));//设置文本框位置
            }
        }
        XSLFTextRun trText=text.addNewTextParagraph().addNewTextRun();//写入正文内容
        trText.setText(paragraph);

        //插入图片
        if(imageNumber.equals(1))
        {
            byte[] pictureData=ImagePageServiceImpl.getByte(pictureUrls[0]);//读取字节流文件

            XSLFPictureData pd=null;
            String pictureType=pictureUrls[0].substring(pictureUrls[0].length()-4,pictureUrls[0].length());//判断图片类型
            if(pictureType.equals(".jpg")) {
                pd=userFile.addPicture(pictureData, PictureData.PictureType.JPEG);//添加到userFile
            } else if(pictureType.equals(".png")) {
                pd=userFile.addPicture(pictureData, PictureData.PictureType.PNG);//添加到userFile
            }
            XSLFPictureShape pic=slide.createPicture(pd);//放入图文页
            pic.setAnchor(new Rectangle(50,100,400,400));
        }
        else if(imageNumber.equals(2)){
            byte[] pictureData1=ImagePageServiceImpl.getByte(pictureUrls[0]);//读取字节流文件

            XSLFPictureData pd1=null;
            String pictureType1=pictureUrls[0].substring(pictureUrls[0].length()-4,pictureUrls[0].length());//判断图片类型
            if(pictureType1.equals(".jpg")) {
                pd1=userFile.addPicture(pictureData1, PictureData.PictureType.JPEG);//添加到userFile
            } else if(pictureType1.equals(".png")) {
                pd1=userFile.addPicture(pictureData1, PictureData.PictureType.PNG);//添加到userFile
            }
            XSLFPictureShape pic1=slide.createPicture(pd1);//放入图文页
            pic1.setAnchor(new Rectangle(10,20,400,380));

            byte[] pictureData2=ImagePageServiceImpl.getByte(pictureUrls[1]);//读取字节流文件

            XSLFPictureData pd2=null;
            String pictureType2=pictureUrls[1].substring(pictureUrls[1].length()-4,pictureUrls[1].length());//判断图片类型
            if(pictureType2.equals(".jpg"))
                pd2=userFile.addPicture(pictureData2, PictureData.PictureType.JPEG);//添加到userFile
            else if(pictureType2.equals(".png"))
                pd2=userFile.addPicture(pictureData2, PictureData.PictureType.PNG);//添加到userFile
            XSLFPictureShape pic2=slide.createPicture(pd2);//放入图文页
            pic2.setAnchor(new Rectangle(450,20,400,380));
        }
        else if(imageNumber.equals(3)){
            byte[] pictureData1=ImagePageServiceImpl.getByte(pictureUrls[0]);//读取字节流文件

            XSLFPictureData pd1=null;
            String pictureType1=pictureUrls[0].substring(pictureUrls[0].length()-4,pictureUrls[0].length());//判断图片类型
            if(pictureType1.equals(".jpg"))
                pd1=userFile.addPicture(pictureData1, PictureData.PictureType.JPEG);//添加到userFile
            else if(pictureType1.equals(".png"))
                pd1=userFile.addPicture(pictureData1, PictureData.PictureType.PNG);//添加到userFile
            XSLFPictureShape pic1=slide.createPicture(pd1);//放入图文页
            pic1.setAnchor(new Rectangle(50,200,200,200));

            byte[] pictureData2=ImagePageServiceImpl.getByte(pictureUrls[1]);//读取字节流文件

            XSLFPictureData pd2=null;
            String pictureType2=pictureUrls[1].substring(pictureUrls[1].length()-4,pictureUrls[1].length());//判断图片类型
            if(pictureType2.equals(".jpg"))
                pd2=userFile.addPicture(pictureData2, PictureData.PictureType.JPEG);//添加到userFile
            else if(pictureType2.equals(".png"))
                pd2=userFile.addPicture(pictureData2, PictureData.PictureType.PNG);//添加到userFile
            XSLFPictureShape pic2=slide.createPicture(pd2);//放入图文页
            pic2.setAnchor(new Rectangle(350,200,200,200));

            byte[] pictureData3=ImagePageServiceImpl.getByte(pictureUrls[2]);//读取字节流文件

            XSLFPictureData pd3=null;
            String pictureType3=pictureUrls[2].substring(pictureUrls[2].length()-4,pictureUrls[2].length());//判断图片类型
            if(pictureType3.equals(".jpg"))
                pd3=userFile.addPicture(pictureData3, PictureData.PictureType.JPEG);//添加到userFile
            else if(pictureType3.equals(".png"))
                pd3=userFile.addPicture(pictureData3, PictureData.PictureType.PNG);//添加到userFile
            XSLFPictureShape pic3=slide.createPicture(pd3);//放入图文页
            pic3.setAnchor(new Rectangle(650,200,200,200));
        }
        else if(imageNumber.equals(4)){
            byte[] pictureData1=ImagePageServiceImpl.getByte(pictureUrls[0]);//读取字节流文件

            XSLFPictureData pd1=null;
            String pictureType1=pictureUrls[0].substring(pictureUrls[0].length()-4,pictureUrls[0].length());//判断图片类型
            if(pictureType1.equals(".jpg"))
                pd1=userFile.addPicture(pictureData1, PictureData.PictureType.JPEG);//添加到userFile
            else if(pictureType1.equals(".png"))
                pd1=userFile.addPicture(pictureData1, PictureData.PictureType.PNG);//添加到userFile
            XSLFPictureShape pic1=slide.createPicture(pd1);//放入图文页
            pic1.setAnchor(new Rectangle(50,100,200,200));

            byte[] pictureData2=ImagePageServiceImpl.getByte(pictureUrls[1]);//读取字节流文件

            XSLFPictureData pd2=null;
            String pictureType2=pictureUrls[1].substring(pictureUrls[1].length()-4,pictureUrls[1].length());//判断图片类型
            if(pictureType2.equals(".jpg"))
                pd2=userFile.addPicture(pictureData2, PictureData.PictureType.JPEG);//添加到userFile
            else if(pictureType2.equals(".png"))
                pd2=userFile.addPicture(pictureData2, PictureData.PictureType.PNG);//添加到userFile
            XSLFPictureShape pic2=slide.createPicture(pd2);//放入图文页
            pic2.setAnchor(new Rectangle(350,100,200,200));

            byte[] pictureData3=ImagePageServiceImpl.getByte(pictureUrls[2]);//读取字节流文件

            XSLFPictureData pd3=null;
            String pictureType3=pictureUrls[2].substring(pictureUrls[2].length()-4,pictureUrls[2].length());//判断图片类型
            if(pictureType3.equals(".jpg"))
                pd3=userFile.addPicture(pictureData3, PictureData.PictureType.JPEG);//添加到userFile
            else if(pictureType3.equals(".png"))
                pd3=userFile.addPicture(pictureData3, PictureData.PictureType.PNG);//添加到userFile
            XSLFPictureShape pic3=slide.createPicture(pd3);//放入图文页
            pic3.setAnchor(new Rectangle(50,320,200,200));

            byte[] pictureData4=ImagePageServiceImpl.getByte(pictureUrls[3]);//读取字节流文件

            XSLFPictureData pd4=null;
            String pictureType4=pictureUrls[3].substring(pictureUrls[3].length()-4,pictureUrls[3].length());//判断图片类型
            if(pictureType4.equals(".jpg"))
                pd4=userFile.addPicture(pictureData4, PictureData.PictureType.JPEG);//添加到userFile
            else if(pictureType4.equals(".png"))
                pd4=userFile.addPicture(pictureData4, PictureData.PictureType.PNG);//添加到userFile
            XSLFPictureShape pic4=slide.createPicture(pd4);//放入图文页
            pic4.setAnchor(new Rectangle(350,320,200,200));
        }

        //输出文件
        FileOutputStream out = new FileOutputStream(fileDetail.getFilePath());
        userFile.write(out);
        out.close();
        userFile.close();
        return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }
}
