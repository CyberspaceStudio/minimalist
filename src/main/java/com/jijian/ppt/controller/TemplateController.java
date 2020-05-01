package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.TemplateFileDetail;
import com.jijian.ppt.service.TemplateService;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 模板文件
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 13:53
 */
@RestController
@RequestMapping("/ppt/template")
public class TemplateController {

    @Resource
    @Qualifier("templateServiceImpl")
    private TemplateService templateService;


    /**
     * 上传模板文件
     * @param uploadFile
     * @param templateFileDetail
     * @param req
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public UniversalResponseBody<TemplateFileDetail> uploadTemplate(MultipartFile uploadFile,TemplateFileDetail templateFileDetail, HttpServletRequest req) throws Exception {
        return templateService.uploadTemplateFile(uploadFile,templateFileDetail, req);
    }

    /**
     * 查找标签相关文件
     * @param templateTag
     * @return
     */
    @GetMapping("/tag")
    public UniversalResponseBody<List<TemplateFileDetail>> getTemplateByTag(String templateTag){
        return templateService.getTemplateByTag(templateTag);
    }

}
