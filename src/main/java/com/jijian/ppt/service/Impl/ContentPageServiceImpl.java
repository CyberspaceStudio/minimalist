package com.jijian.ppt.service.Impl;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.mapper.FileDetailMapper;
import com.jijian.ppt.mapper.TemplateFileDetailMapper;
import com.jijian.ppt.service.ContentPageService;
import com.jijian.ppt.utils.Enum.PageCategoryEnum;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author 桂乙侨
 * @Date 2020/3/29 22:20
 * @Version 1.0
 */
@Slf4j
@Service
public class ContentPageServiceImpl implements ContentPageService {

    @Resource
    private FileDetailMapper fileDetailMapper;
    @Resource
    private TemplateFileDetailMapper templateFileDetailMapper;
    /**
     *  已经创建封面页 ,在其后面追加，默认为第2页
     * @param fileId ppt文件的id
     * @param titles 小标题
     * @return 返回文件 FileDetail
     */
    @Override
    public UniversalResponseBody<FileDetail> makeContentsPage(Integer fileId, String[] titles) throws IOException {
        FileDetail fileDetail = fileDetailMapper.getDetailByFileId(fileId);

        FileInputStream inputStream = new FileInputStream(fileDetail.getFilePath());
        XMLSlideShow  ppt = new XMLSlideShow(inputStream);

        //获取模板ppt文件的路径
        String templateFilePath =templateFileDetailMapper.GetTemplateFilePath(fileDetail.getTemplateId());

        FileInputStream fileInputStream = new FileInputStream(templateFilePath);
        XMLSlideShow template = new XMLSlideShow(fileInputStream);

        //获取模板的目录页 此处使用第1页
        XSLFSlide templateContentSlide = template.getSlides().get(PageCategoryEnum.CONTENT_PAGE.getPageCategoryId());

        //读取模板文件的排版
        XSLFSlideLayout layout = templateContentSlide.getSlideLayout();
        //将排版应用到用户文件
        XSLFSlide userContentSlide = ppt.createSlide(layout);

        for(int i =0; i < titles.length;i++){
            //创建文本框
            XSLFTextBox box = userContentSlide.createTextBox();
            //设置 x y 坐标
            int x = 350,y = 250;
            y =y - (50*(titles.length/2));
            box.setAnchor(new Rectangle(x,y+50*i,200,50));
            //创建段落 并 添加文字
            XSLFTextParagraph paragraph = box.addNewTextParagraph();
            XSLFTextRun textRun = paragraph.addNewTextRun();
            textRun.setText((i+1)+"."+titles[i]);
            textRun.setFontSize(30.0);
            textRun.setFontColor(Color.BLACK);
        }
        //导入上下文
        userContentSlide.importContent(templateContentSlide);
        FileOutputStream fileOutputStream = new FileOutputStream(fileDetail.getFilePath());
        ppt.write(fileOutputStream);

        Integer pageCount = fileDetail.getPageCounts();
        pageCount++;
        fileDetailMapper.updatePageCount(fileId,pageCount);
        fileDetail.setPageCounts(pageCount);
        fileInputStream.close();
        fileOutputStream.close();
        inputStream.close();
        return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }
}
