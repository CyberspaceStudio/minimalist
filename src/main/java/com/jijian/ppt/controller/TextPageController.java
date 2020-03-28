package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 正文页
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 11:22
 */
@RestController
@RequestMapping("/ppt/text")
public class TextPageController {

    /**
     * 制作正文页
     * @param userId 用户id
     * @param fileId 文件id
     * @param title 标题
     * @param paragraph 段落
     * @return
     */
    @PostMapping("/make")
    public UniversalResponseBody<FileDetail> makeTextPage(Integer userId,Integer fileId,String title,String paragraph){
        return null;
    }
}
