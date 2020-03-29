package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 21:55
 */
@RestController
@RequestMapping("/ppt/image")
public class ImagePageController {

    /**
     * 制作图文页
     * @param pictureUrls
     * @param title
     * @param paragraph
     * @return
     */
    @PostMapping("/make")
    public UniversalResponseBody<FileDetail> makeImagePage(String[] pictureUrls,String title,String paragraph){
        return null;
    }
}
