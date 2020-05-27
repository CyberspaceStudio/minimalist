package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.service.TransitionPageService;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 过渡页
 * @author 郭树耸
 * @version 1.0
 * @date 2020/5/3 20:33
 */
@RestController
@RequestMapping("/ppt/transition")
public class TransitionPageController {

    @Resource
    @Qualifier("transitionPageServiceImpl")
    private TransitionPageService transitionPageService;

    /**
     * 制作过渡页
     * @param fileId
     * @param title
     * @return
     * @throws IOException
     */
    @PostMapping("/make")
    public UniversalResponseBody<FileDetail> makeTransitionPage(Integer fileId, String title) throws IOException {
        return transitionPageService.makeTransitionPageV1(fileId, title);
    }

    /**
     * 制作过渡页
     * @param fileId
     * @param pageId
     * @param title
     * @return
     * @throws IOException
     */
    @PostMapping("/make/v2")
    public UniversalResponseBody<FileDetail> makeTransitionPageV2(Integer fileId, Integer pageId,String title) throws IOException {
        return transitionPageService.makeTransitionPage(fileId, pageId,title);
    }
}
