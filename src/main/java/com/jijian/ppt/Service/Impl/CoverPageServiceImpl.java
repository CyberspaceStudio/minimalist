package com.jijian.ppt.Service.Impl;

import com.jijian.ppt.POJO.CoverPage;
import com.jijian.ppt.Service.CoverPageService;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.common.usermodel.fonts.FontInfo;
import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.sl.usermodel.MasterSheet;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextBox;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.List;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/24 17:04
 */
@Service
@Slf4j
public class CoverPageServiceImpl implements CoverPageService {
    @Override
    public UniversalResponseBody modifyCoverPage(Integer userId, CoverPage coverPage, Integer fileId) {
        //修改PPT首页
        return null;
    }

    /**
     * 给某个ppt第一次制作
     * @param userId
     * @param coverPage
     * @param templateId
     * @return
     */
    @Override
    public UniversalResponseBody makeCoverPage(Integer userId, CoverPage coverPage, Integer templateId) throws IOException {

        /*
        //生成一个新的ppt文件
        XMLSlideShow ppt = new XMLSlideShow();
        //创建幻灯片
        XSLFSlide slide = ppt.createSlide();
        XSLFTextBox textBox = slide.createTextBox();
        textBox.setAnchor(new Rectangle2D.Double(10,10, 0, 0));
        textBox.addNewTextParagraph().addNewTextRun().setText("创建幻灯片");
        */

        XMLSlideShow ppt = new XMLSlideShow();// 设置幻灯片大小
        ppt.setPageSize(new Dimension(760, 600));
        /*SlideMaster master = ppt.getSlidesMasters()[0]; // 设置母板背景,支持多种图片格式
        int picIndex = 0;
        try {
            picIndex = ppt.addPicture(new File("background.png"), Picture.PNG);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picture background = new Picture(picIndex);// 设置图片位置
        background.setAnchor(new java.awt.Rectangle(0, 0,
                ppt.getPageSize().width, ppt.getPageSize().height));
        master.addShape(background);*/


        //文件输出路径,此步骤为最后一步
        ppt.write(new FileOutputStream("/Users/mike/ppt1.pptx"));
        return null;
    }
}
