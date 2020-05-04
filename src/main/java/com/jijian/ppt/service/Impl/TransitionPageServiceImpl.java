package com.jijian.ppt.service.Impl;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.mapper.FileDetailMapper;
import com.jijian.ppt.mapper.TemplateFileDetailMapper;
import com.jijian.ppt.service.TransitionPageService;
import com.jijian.ppt.utils.Enum.PageCategoryEnum;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.response.UniversalResponseBody;
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
 * @author 郭树耸
 * @version 1.0
 * @date 2020/5/3 20:36
 */
@Service
public class TransitionPageServiceImpl implements TransitionPageService {


    @Resource
    private FileDetailMapper fileDetailMapper;
    @Resource
    private TemplateFileDetailMapper templateFileDetailMapper;

    @Override
    public UniversalResponseBody<FileDetail> makeTransitionPage(Integer fileId, String title) throws IOException {
        //读取文件详细信息
        FileDetail fileDetail = fileDetailMapper.getDetailByFileId(fileId);

        //获取模板ppt文件的路径
        String templateFilePath =templateFileDetailMapper.GetTemplateFilePath(fileDetail.getTemplateId());

        //读取模板文件
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(templateFilePath));

        //获取模板的结束页
        XSLFSlide slide = ppt.getSlides().get(PageCategoryEnum.TRANSITION_PAGE.getPageCategoryId());

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
                java.util.List<TextParagraph> list = ((TextShape) shape).getTextParagraphs();
                for (TextParagraph textParagraph:
                        list) {
                    List<TextRun> textRuns = textParagraph.getTextRuns();
                    for (TextRun textRun:
                            textRuns) {
                        String text = textRun.getRawText();
                        if (text.equals("Title")){
                            textRun.setText(title);
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
