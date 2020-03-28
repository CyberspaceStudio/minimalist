package com.jijian.ppt.Service.Impl;

import com.jijian.ppt.POJO.CoverPage;
import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.Service.CoverPageService;
import com.jijian.ppt.mapper.FileDetailMapper;
import com.jijian.ppt.mapper.TemplateFileDetailMapper;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.FileUtil;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;

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
    private FileUtil fileUtil;


    @Override
    public UniversalResponseBody modifyCoverPage(Integer userId, CoverPage coverPage, Integer fileId) {
        //修改PPT首页
        return null;
    }

    /**
     * 制作正文页，由于是第一次制作，所以要先生成一份ppt
     * @param userId
     * @param coverPage
     * @param templateId
     * @return
     */
    @Override
    public UniversalResponseBody<FileDetail> makeCoverPage(Integer userId, CoverPage coverPage, Integer templateId) throws IOException {
        //获取模板ppt文件的路径
        String templateFilePath =templateFileDetailMapper.GetTemplateFilePath(templateId);
        //XMLSlideShow ppt = new XMLSlideShow( new FileInputStream("C:\\Users\\24605\\Desktop\\minimalist\\src\\main\\resources\\static\\pptTemplate\\template.pptx") );

        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(templateFilePath));
        XSLFSlide  slide = ppt.getSlides().get(0);
        for( XSLFShape shape : slide.getShapes() )
                {
                    if ( shape instanceof XSLFTextShape)
                    {
                        XSLFTextShape txtshape = (XSLFTextShape)shape ;
                        if (((XSLFTextShape) shape).getText().contains("{Title}")){
                            txtshape.setText(coverPage.getTitle());
                        }
                        if (((XSLFTextShape) shape).getText().contains("{subTitle}")){
                            txtshape.setText(coverPage.getSubtitle());
                        }
                        if (((XSLFTextShape) shape).getText().contains("{reporterName}")){
                            txtshape.setText(coverPage.getReporterName());
                        }
                        if (((XSLFTextShape) shape).getText().contains("{reportTime}")){
                            txtshape.setText(coverPage.getReportTime());
                        }
                    }
                }
            FileDetail fileDetail = new FileDetail();
            //生成文件路径+文件名
            fileUtil.GenerateFilePath(fileDetail);
            fileDetail.setUserId(userId);
            fileDetail.setTemplateId(templateId);
            //将文件信息插入数据库
            fileDetailMapper.insertFileDetail(fileDetail);
            //输出文件
            FileOutputStream out = new FileOutputStream(fileDetail.getFilePath()) ;
            //将文件信息返回给前端
            ppt.write(out);
            out.close();
            ppt.close();
            return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }
}
