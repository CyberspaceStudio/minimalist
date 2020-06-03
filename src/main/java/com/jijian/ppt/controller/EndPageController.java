package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.CoverPage;
import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.service.EndPageService;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 结束页
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/31 8:34
 */
@RestController
@RequestMapping("/ppt/end")
public class EndPageController {


    @Resource
    @Qualifier("endPageServiceImpl")
    private EndPageService endPageService;

    /**
     * 制作结束页
     * @param fileId
     * @param title
     * @return
     * @throws IOException
     */
    @PostMapping("/make")
    public UniversalResponseBody<FileDetail> makeEndPage(Integer fileId,String title) throws IOException {
        return endPageService.makeEndPageV1(fileId, title);
    }

    /**
     * 制作结束页
     * @param fileId
     * @param pageId
     * @param title
     * @return
     * @throws IOException
     */
    @PostMapping("/make/v2")
    public UniversalResponseBody<FileDetail> makeEndPage(Integer fileId,Integer pageId,String title) throws IOException {
        return endPageService.makeEndPage(fileId,pageId, title);
    }
}
