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


       return null;
    }
}
