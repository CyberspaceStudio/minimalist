package com.jijian.ppt.controller;

import com.jijian.ppt.service.ImageService;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片
 * @author 郭树耸
 * @version 1.0
 * @date 2020/5/3 21:41
 */
@RestController
@RequestMapping("/image")
public class ImageController {

    @Resource
    @Qualifier("imageServiceImpl")
    private ImageService imageService;

    /**
     * 上传图片
     * @param uploadFile
     * @param req
     * @return url 图片路径
     */
    @PostMapping("/upload")
    public UniversalResponseBody<String> uploadImage(MultipartFile uploadFile, HttpServletRequest req) throws Exception {
        return imageService.uploadImage(uploadFile, req);
    }
}
