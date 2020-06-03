package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.CoverPage;
import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.service.CoverPageService;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 封面页
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/24 16:55
 */
@RestController
@RequestMapping("/ppt/cover")
public class CoverPageController {

    @Resource
    @Qualifier("coverPageServiceImpl")
    private CoverPageService coverPageService;


    /**
     * 制作封面页
     * @param userId
     * @param coverPage
     * @param templateId
     * @return
     */
    @PostMapping("/make")
    public UniversalResponseBody<FileDetail> makeCoverPage(Integer userId, CoverPage coverPage, Integer templateId) throws IOException {
        return coverPageService.makeCoverPageV1(userId, coverPage, templateId);
    }

    /**
     * 制作封面页
     * @param userId
     * @param coverPage
     * @param pageId
     * @param templateId
     * @return
     */
    @PostMapping("/make/v2")
    public UniversalResponseBody<FileDetail> makeCoverPageV2(Integer userId,Integer pageId, CoverPage coverPage, Integer templateId) throws IOException {
        return coverPageService.makeCoverPage(userId,pageId,coverPage, templateId);
    }
}
