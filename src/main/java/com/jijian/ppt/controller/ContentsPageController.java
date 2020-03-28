package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 目录页
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/27 21:37
 */
@RestController
@RequestMapping("/ppt/contents")
public class ContentsPageController {

    /**
     * 制作目录页
     * @param userId
     * @param fileId
     * @param titles
     * @return
     */
    @PostMapping("/make")
    public UniversalResponseBody<FileDetail> makeContentsPage(Integer userId,Integer fileId,String[] titles){
        return null;
    }
}
