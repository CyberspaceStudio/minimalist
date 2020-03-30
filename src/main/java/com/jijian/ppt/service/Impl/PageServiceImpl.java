package com.jijian.ppt.service.Impl;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.service.PageService;
import com.jijian.ppt.mapper.FileDetailMapper;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * PPT页面操作
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/29 9:30
 */
@Service
public class PageServiceImpl implements PageService {

    @Resource
    private FileDetailMapper fileDetailMapper;

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
            return new UniversalResponseBody(ResponseResultEnum.FAILED.getCode(),ResponseResultEnum.FAILED.getMsg());
        }
        return new UniversalResponseBody<FileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),fileDetail);
    }
}
