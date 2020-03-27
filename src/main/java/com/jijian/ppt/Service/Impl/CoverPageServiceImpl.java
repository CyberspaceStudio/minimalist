package com.jijian.ppt.Service.Impl;

import com.jijian.ppt.POJO.CoverPage;
import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.Service.CoverPageService;
import com.jijian.ppt.mapper.FileDetailMapper;
import com.jijian.ppt.utils.Enum.FileCategoryEnum;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.FileUtil;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.apache.poi.common.usermodel.fonts.FontInfo;
import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.sl.usermodel.MasterSheet;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.Slide;
import org.apache.poi.sl.usermodel.SlideShow;
import org.apache.poi.xslf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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

    @Resource
    private FileDetailMapper fileDetailMapper;
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
        //打开ppt文件
        String templateFilePath = fileDetailMapper.getPathByFileId(templateId);
        //XMLSlideShow ppt = new XMLSlideShow( new FileInputStream("C:\\Users\\24605\\Desktop\\minimalist\\src\\main\\resources\\static\\pptTemplate\\template.pptx") );
        System.out.println(templateFilePath);
        XMLSlideShow ppt = new XMLSlideShow(new FileInputStream(templateFilePath));

        System.out.println(coverPage.toString());
        for( XSLFSlide slide : ppt.getSlides() )
            {
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
                    else if ( shape instanceof XSLFConnectorShape )
                    {
                        XSLFConnectorShape connectorShape = (XSLFConnectorShape)shape ;
                    }
                    else if ( shape instanceof XSLFPictureShape )
                    {
                        XSLFPictureShape picShape = (XSLFPictureShape)shape ;
                    }
                }
            }
            FileDetail fileDetail = new FileDetail();
            //生成文件路径+文件名
            fileUtil.GenerateFilePath(fileDetail);
            fileDetail.setUserId(userId);
            //用户PPT
            fileDetail.setFileCategoryId(FileCategoryEnum.USERFILE.getFileCategoryId());
            //将文件信息插入数据库
            fileDetailMapper.insertFileDetail(fileDetail);
            //输出文件
            System.out.println(fileDetail.getFilePath());
            FileOutputStream out = new FileOutputStream(fileDetail.getFilePath()) ;
            //将文件信息返回给前端
            ppt.write(out);
            out.close();
            ppt.close();

            return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }
}
