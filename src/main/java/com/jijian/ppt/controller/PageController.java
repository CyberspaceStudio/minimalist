package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.service.PageService;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * PPT页操作
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/29 9:13
 */
@RestController
@RequestMapping("/ppt/page")
public class PageController {

    @Resource
    @Qualifier("pageServiceImpl")
    private PageService pageService;

    /**
     * 移动页面
     * @param fileId
     * @param formPageNum
     * @param toPageNum
     * @return
     */
    @PostMapping("/move")
    public UniversalResponseBody<FileDetail> movePage(Integer fileId,Integer formPageNum,Integer toPageNum) throws IOException {
        return pageService.movePage(fileId, formPageNum, toPageNum);
    }

    /**
     * 删除页面
     * @param fileId
     * @param pageNum
     * @return
     */
    @PostMapping("/delete")
    public UniversalResponseBody<FileDetail> deletePage(Integer fileId,Integer pageNum){
        return pageService.deletePage(fileId, pageNum);
    }
}
