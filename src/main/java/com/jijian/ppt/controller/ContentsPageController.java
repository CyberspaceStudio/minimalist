package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.service.ContentPageService;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 目录页
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/27 21:37
 */
@RestController
@RequestMapping("/ppt/contents")
public class ContentsPageController {

    @Resource
    @Qualifier("contentPageServiceImpl")
    private ContentPageService contentPageService;

    /**
     * 制作目录页
     * @apiNote 此方法日后将被移除
     * @param fileId
     * @param titles
     * @return
     */
    @PostMapping("/make")
    public UniversalResponseBody<FileDetail> makeContentsPage(Integer fileId,String[] titles) throws IOException {
        return contentPageService.makeContentsPageV1(fileId,titles);
    }
    /**
     * 制作目录页
     * @param fileId
     * @param pageId
     * @param titles
     * @return
     */
    @PostMapping("/make/v2")
    public UniversalResponseBody<FileDetail> makeContentsPageV2(Integer fileId,Integer pageId,String[] titles) throws IOException {
        return contentPageService.makeContentsPage(fileId,pageId ,titles);
    }
}
