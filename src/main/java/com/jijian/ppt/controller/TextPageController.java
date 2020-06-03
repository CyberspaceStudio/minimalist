package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.service.TextPageService;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 正文页
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 11:22
 */
@RestController
@RequestMapping("/ppt/text")
public class TextPageController {

    @Resource
    @Qualifier("textPageServiceImpl")
    private TextPageService textPageService;

    /**
     * 制作正文页
     * @param fileId 文件id
     * @param title 标题
     * @param paragraph 段落
     * @return
     */
    @PostMapping("/make")
    public UniversalResponseBody<FileDetail> makeTextPage(Integer fileId,String title,String paragraph) throws IOException {
        return textPageService.makeTextPage(fileId, title, paragraph);
    }

    /**
     * 制作正文页V2
     * @param fileId 文件Id
     * @param pageId 页面Id
     * @param titles  标题数组
     * @param subTitles  副标题数组
     * @param paragraphs  段落数组
     * @return
     * @throws IOException
     */
    @PostMapping("/make/v2")
    public UniversalResponseBody<FileDetail> makeTextPageV2(Integer fileId,Integer pageId,String[] titles,String[] subTitles,String[] paragraphs) throws IOException {
        return textPageService.makeTextPageV2(fileId, pageId, titles, subTitles, paragraphs);
    }
}
