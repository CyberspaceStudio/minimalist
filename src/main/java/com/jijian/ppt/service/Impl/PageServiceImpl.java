package com.jijian.ppt.service.Impl;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.service.PageService;
import com.jijian.ppt.mapper.FileDetailMapper;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.PdfToImg;
import com.jijian.ppt.utils.Pptx2PdfUtil;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * PPT页面操作
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/29 9:30
 */
@Service
@Slf4j
public class PageServiceImpl implements PageService {

    @Resource
    private FileDetailMapper fileDetailMapper;
    @Resource
    private Pptx2PdfUtil pptx2PdfUtil;
    @Resource
    private PdfToImg pdfToImg;

    @Override
    public UniversalResponseBody<FileDetail> movePage(Integer fileId, Integer formPageNum, Integer toPageNum) {
        FileDetail fileDetail = fileDetailMapper.getDetailByFileId(fileId);
       try {
           FileInputStream inputStream = new FileInputStream(fileDetail.getFilePath());
           XMLSlideShow userFile = new XMLSlideShow(inputStream);
           XSLFSlide xslfSlide = userFile.getSlides().get(formPageNum-1);
           userFile.setSlideOrder(xslfSlide,toPageNum-1);
           FileOutputStream outputStream = new FileOutputStream(fileDetail.getFilePath());
           userFile.write(outputStream);
           outputStream.close();
           userFile.close();
       }catch (IOException e){
           e.printStackTrace();
           return new UniversalResponseBody(ResponseResultEnum.FAILED.getCode(),ResponseResultEnum.FAILED.getMsg());
       }

        return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }

    @Override
    public UniversalResponseBody<FileDetail> deletePage(Integer fileId, Integer pageNum) {
        FileDetail fileDetail = fileDetailMapper.getDetailByFileId(fileId);
        try{
            FileInputStream inputStream = new FileInputStream(fileDetail.getFilePath());
            XMLSlideShow userFile = new XMLSlideShow(inputStream);
            //删除页面
            userFile.removeSlide(pageNum-1);
            FileOutputStream outputStream = new FileOutputStream(fileDetail.getFilePath());
            userFile.write(outputStream);
            outputStream.close();
            userFile.close();
        }catch (IOException e){
            e.printStackTrace();
            return new UniversalResponseBody(ResponseResultEnum.FAILED.getCode(),ResponseResultEnum.FAILED.getMsg());
        }
        Integer pageCount = fileDetail.getPageCounts();
        pageCount--;
        fileDetailMapper.updatePageCount(fileId,pageCount);
        fileDetail.setPageCounts(pageCount);
        return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }

    @Override
    public UniversalResponseBody<List<String>> filePreview(Integer fileId) throws Exception {
        FileDetail fileDetail = fileDetailMapper.getDetailByFileId(fileId);
        if (fileDetail == null){
            return new UniversalResponseBody(ResponseResultEnum.FAILED.getCode(),ResponseResultEnum.FAILED.getMsg());
        }
        List<String> imgUrls =  pdfToImg.pdfToImageOnePageOnImage(pptx2PdfUtil.fileToPdf(fileDetail.getFilePath()));
        log.info(fileDetail.getFilePath());
        return new UniversalResponseBody<List<String>>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),imgUrls);
    }
}
