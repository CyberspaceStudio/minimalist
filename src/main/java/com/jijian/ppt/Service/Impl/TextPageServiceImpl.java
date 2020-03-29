package com.jijian.ppt.Service.Impl;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.Service.TextPageService;
import com.jijian.ppt.mapper.FileDetailMapper;
import com.jijian.ppt.mapper.TemplateFileDetailMapper;
import com.jijian.ppt.utils.Enum.PageCategoryEnum;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.FileUtil;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
    private FileUtil fileUtil;
    @Resource
    private FileDetailMapper fileDetailMapper;
    @Resource
    private TemplateFileDetailMapper templateFileDetailMapper;

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
        //导入上下文
        newSlide.importContent(slide);
        for ( XSLFShape shape : newSlide.getShapes())
        {
            if ( shape instanceof XSLFTextShape)
            {
                XSLFTextShape txtshape = (XSLFTextShape)shape ;
                if (((XSLFTextShape) shape).getText().contains("{Title}")){
                    txtshape.setText(title);
                }
                if (((XSLFTextShape) shape).getText().contains("{Paragraph}")){
                    txtshape.setText(paragraph);
                }

            }
        }
        //输出文件
        FileOutputStream out = new FileOutputStream(fileDetail.getFilePath());
        userFile.write(out);
        out.close();
        userFile.close();
        return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }
}
