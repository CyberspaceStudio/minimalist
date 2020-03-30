package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.Service.ImagePageService;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 *图文页
 * @author 武泽中
 * @version 1.0
 * @date 2020/3/30 13：49
 */
@RestController
@RequestMapping("/ppt/image")
public class ImagePageController {
    @Autowired
    private ImagePageService imagePageService;

    /**
     * 制作图文页
     * @param pictureUrls
     * @param title
     * @param paragraph
     * @return
     */
    @PostMapping("/make")
    public UniversalResponseBody<FileDetail> makeImagePage(Integer fileId,String[] pictureUrls,String title,String paragraph) throws IOException {
        return imagePageService.makeImagePage(fileId,pictureUrls,title,paragraph);
    }
}
