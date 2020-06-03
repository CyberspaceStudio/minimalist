package com.jijian.ppt.controller;

import com.jijian.ppt.POJO.Page;
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
import java.util.Arrays;
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
     * @param templateName 模板名称
     * @param templateTag 模板标签
     * @param req
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    public UniversalResponseBody<TemplateFileDetail> uploadTemplate(MultipartFile uploadFile,String templateName,String templateTag, HttpServletRequest req) throws Exception {
        return templateService.uploadTemplateFile(uploadFile,templateName,templateTag, req);
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

    /**
     * 查找模板相应类型的页面
     * @param templateId 模板Id
     * @param pageCategoryId 页面类型Id
     * @return
     */
    @GetMapping("/pages")
    public UniversalResponseBody<List<Page>> getPagesByCategoryId(Integer templateId,Integer pageCategoryId){
        return templateService.getPagesByCategoryId(templateId, pageCategoryId);
    }

    /**
     * 添加模板页面相应的具体信息
     * @apiNote 请提示用户pagePosition将正常页数减一
     * @param page
     * @return
     * @throws Exception
     */
    @PostMapping("/page/detail")
    public UniversalResponseBody<Page> insertTemplatePageDetail(Page page) throws Exception {
       return templateService.insertTemplatePageDetail(page);
    }
}
