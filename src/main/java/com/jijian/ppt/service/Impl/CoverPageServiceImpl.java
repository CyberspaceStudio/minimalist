package com.jijian.ppt.service.Impl;

import com.jijian.ppt.POJO.CoverPage;
import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.POJO.Page;
import com.jijian.ppt.mapper.TemplatePageDetailMapper;
import com.jijian.ppt.service.CoverPageService;
import com.jijian.ppt.mapper.FileDetailMapper;
import com.jijian.ppt.mapper.TemplateFileDetailMapper;
import com.jijian.ppt.utils.Enum.PageCategoryEnum;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.FileUtil;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.sl.usermodel.TextRun;
import org.apache.poi.sl.usermodel.TextShape;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
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

    @Resource
    private FileDetailMapper fileDetailMapper;
    @Resource
    private TemplateFileDetailMapper templateFileDetailMapper;
    @Resource
    private TemplatePageDetailMapper templatePageDetailMapper;

    @Override
    public UniversalResponseBody modifyCoverPage(Integer userId, CoverPage coverPage, Integer fileId) {
        //修改PPT首页
        return null;
    }

    /**
     * 制作封面页
     * @param userId
     * @param coverPage
     * @param templateId
     * @return
     */
    @Override
    @Deprecated
    public UniversalResponseBody<FileDetail> makeCoverPageV1(Integer userId, CoverPage coverPage, Integer templateId) throws IOException{
        return makeCoverPage(userId, 1,coverPage, templateId);
    }


    /**
     * 制作正文页，由于是第一次制作，所以要先生成一份ppt
     * @param userId
     * @param coverPage
     * @param templateId
     * @return
     */
    @Override
    public UniversalResponseBody<FileDetail> makeCoverPage(Integer userId, Integer pageId,CoverPage coverPage, Integer templateId) throws IOException {
        Page page = templatePageDetailMapper.getPageByPageId(pageId);
        //获取模板ppt文件的路径
        String templateFilePath =templateFileDetailMapper.GetTemplateFilePath(templateId);
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(templateFilePath));
        //获取模板的封面页
        XSLFSlide slide = ppt.getSlides().get(page.getPagePosition());
        //新建一个用户文件
        XMLSlideShow userFile = new XMLSlideShow();
        //读取模板文件的排版
        XSLFSlideLayout layout = slide.getSlideLayout();
        //设置PPT页面大小
        Dimension dimension = ppt.getPageSize();
        userFile.setPageSize(dimension);
        //将排版应用到用户文件
        XSLFSlide newSlide = userFile.createSlide(layout);
        for ( XSLFShape shape : slide.getShapes())
        {
            if ( shape instanceof XSLFTextShape)
            {
                XSLFTextShape txtshape = (XSLFTextShape)shape ;
                java.util.List<TextParagraph> list = ((TextShape) shape).getTextParagraphs();
                for (TextParagraph textParagraph:
                        list) {
                    List<TextRun> textRuns = textParagraph.getTextRuns();
                    for (TextRun textRun:
                            textRuns) {
                        String text = textRun.getRawText();
                        if (text.equals("Title")){
                            textRun.setText(coverPage.getTitle());
                        }
                        if (text.equals("subTitle")) {
                            textRun.setText(coverPage.getSubtitle());
                        }
                        if (text.equals("reporterName")){
                            textRun.setText(coverPage.getReporterName());
                        }
                        if (text.equals("reportTime")){
                            textRun.setText(coverPage.getReportTime());
                        }
                    }
                }
            }
        }
        //导入
        newSlide.importContent(slide);
        FileDetail fileDetail = new FileDetail();
        //生成文件路径+文件名

        FileUtil.GenerateFilePath(fileDetail);
        fileDetail.setUserId(userId);
        fileDetail.setTemplateId(templateId);
        fileDetail.setFileName(coverPage.getTitle());
        //将文件信息插入数据库
        fileDetailMapper.insertFileDetail(fileDetail);
        //输出文件
        FileOutputStream out = new FileOutputStream(fileDetail.getFilePath());
        userFile.write(out);
        out.close();
        userFile.close();
        fileDetail.setPageCounts(1);
        return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }
}