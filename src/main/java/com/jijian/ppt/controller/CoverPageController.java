package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.CoverPage;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 封面页
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/24 16:55
 */
@RestController
@RequestMapping("/ppt/cover")
public class CoverPageController {

    /**
     * 修改封面页
     * @param userId 用户id
     * @param coverPage
     * @param fileId 模板id
     * @return
     */
    @RequestMapping("/modify")
    public UniversalResponseBody modifyCoverPage(Integer userId, CoverPage coverPage,Integer fileId){
        return null;
    }

    /**
     * 制作封面页
     * @param userId
     * @param coverPage
     * @param templateId
     * @return
     */
    @RequestMapping("/make")
    public UniversalResponseBody makeCoverPage(Integer userId,CoverPage coverPage,Integer templateId){
        return null;
    }
}
