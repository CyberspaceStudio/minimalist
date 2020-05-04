package com.jijian.ppt.service;

import com.jijian.ppt.POJO.TemplateFileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 图片相关操作
 * @author 郭树耸
 * @version 1.0
 * @date 2020/5/3 21:44
 */
public interface ImageService {

    /**
     * 上传图片
     * @param uploadFile
     * @param req
     * @return
     * @throws IOException
     */
    UniversalResponseBody<String> uploadImage(MultipartFile uploadFile, HttpServletRequest req) throws Exception;
}
