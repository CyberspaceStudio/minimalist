package com.jijian.ppt.service.Impl;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.POJO.Page;
import com.jijian.ppt.mapper.TemplatePageDetailMapper;
import com.jijian.ppt.service.TextPageService;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * 正文页相关接口
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 12:13
 */
@Service
@Slf4j
public class TextPageServiceImpl implements TextPageService {

    @Resource
    private FileDetailMapper fileDetailMapper;
    @Resource
    private TemplateFileDetailMapper templateFileDetailMapper;
    @Resource
    private TemplatePageDetailMapper templatePageDetailMapper;

    @Override
    public UniversalResponseBody<FileDetail> makeTextPage(Integer fileId, String title,String paragraph) throws IOException {
        //读取文件详细信息
        FileDetail fileDetail = fileDetailMapper.getDetailByFileId(fileId);

        //获取模板ppt文件的路径
        String templateFilePath =templateFileDetailMapper.GetTemplateFilePath(fileDetail.getTemplateId());

        //读取模板文件
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(templateFilePath));

        //获取模板的正文页
        XSLFSlide slide = ppt.getSlides().get(PageCategoryEnum.TEXT_PAGE.getPageCategoryId());
        //读取用户文件
        XMLSlideShow userFile = new XMLSlideShow(new FileInputStream(fileDetail.getFilePath()));
        //读取模板文件的排版
        XSLFSlideLayout layout = slide.getSlideLayout();
        //将排版应用到用户文件
        XSLFSlide newSlide = userFile.createSlide(layout);

        for ( XSLFShape shape : slide.getShapes())
        {
            if ( shape instanceof XSLFTextShape)
            {
                XSLFTextShape txtshape = (XSLFTextShape)shape ;
                List<TextParagraph> list = ((TextShape) shape).getTextParagraphs();
                for (TextParagraph textParagraph:
                        list) {
                    List<TextRun> textRuns = textParagraph.getTextRuns();
                    for (TextRun textRun:
                            textRuns) {
                        String text = textRun.getRawText();
                        if (text.equals("Title")){
                            textRun.setText(title);
                        }
                        if (text.equals("Paragraph")) {
                            textRun.setText(paragraph);
                        }
                    }

                }
            }
        }
        //导入上下文
        newSlide.importContent(slide);
        //输出文件
        FileOutputStream out = new FileOutputStream(fileDetail.getFilePath());
        userFile.write(out);
        out.close();
        userFile.close();
        Integer pageCount = fileDetail.getPageCounts();
        pageCount++;
        fileDetailMapper.updatePageCount(fileId,pageCount);
        fileDetail.setPageCounts(pageCount);
        return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }

    @Override
    public UniversalResponseBody<FileDetail> makeTextPageV2(Integer fileId,Integer pageId,String[] titles,String[] subTitles,String[] paragraphs) throws IOException {
        //读取文件详细信息
        FileDetail fileDetail = fileDetailMapper.getDetailByFileId(fileId);
        //从数据库根据pageId读取相应页面的位置
        Page page = templatePageDetailMapper.getPageByPageId(pageId);
        //获取模板ppt文件的路径
        String templateFilePath =templateFileDetailMapper.GetTemplateFilePath(fileDetail.getTemplateId());
        //读取模板文件
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(templateFilePath));
        //获取模板的正文页
        XSLFSlide slide = ppt.getSlides().get(page.getPagePosition());
        //读取用户文件
        XMLSlideShow userFile = new XMLSlideShow(new FileInputStream(fileDetail.getFilePath()));
        //读取模板文件的排版
        XSLFSlideLayout layout = slide.getSlideLayout();
        //将排版应用到用户文件
        XSLFSlide newSlide = userFile.createSlide(layout);
        int i = 0,j = 0,k = 0;
        for ( XSLFShape shape : slide.getShapes())
        {
            if ( shape instanceof XSLFTextShape)
            {
                XSLFTextShape txtshape = (XSLFTextShape)shape ;
                List<TextParagraph> list = ((TextShape) shape).getTextParagraphs();
                for (TextParagraph textParagraph:
                        list) {
                    List<TextRun> textRuns = textParagraph.getTextRuns();
                    for (TextRun textRun:
                            textRuns) {
                        String text = textRun.getRawText();
                        if (text.equals("Title")){
                            if (i < titles.length){
                                textRun.setText(titles[i]);
                                i++;
                            }else {
                                textRun.setText("");
                            }
                        }
                        if (text.equals("subTitle")){
                            if (j < subTitles.length){
                                textRun.setText(subTitles[j]);
                                j++;
                            }else {
                                textRun.setText("");
                            }
                        }
                        if (text.equals("Paragraph")){
                            if (k < paragraphs.length){
                                textRun.setText(paragraphs[j]);
                                k++;
                            }else {
                                textRun.setText("");
                            }
                        }
                    }

                }
            }
        }
        //导入上下文
        newSlide.importContent(slide);
        //输出文件
        FileOutputStream out = new FileOutputStream(fileDetail.getFilePath());
        userFile.write(out);
        out.close();
        userFile.close();
        Integer pageCount = fileDetail.getPageCounts();
        pageCount++;
        fileDetailMapper.updatePageCount(fileId,pageCount);
        fileDetail.setPageCounts(pageCount);
        return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }
}
