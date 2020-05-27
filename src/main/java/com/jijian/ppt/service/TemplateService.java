package com.jijian.ppt.service;

import com.jijian.ppt.POJO.Page;
import com.jijian.ppt.POJO.TemplateFileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 模板文件相关接口
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 13:58
 */
public interface TemplateService {


    /**
     * 上传模板文件
     * @param uploadFile
     * @param templateName
     * @param templateTag
     * @param req
     * @return
     * @throws Exception
     */
    UniversalResponseBody<TemplateFileDetail> uploadTemplateFile(MultipartFile uploadFile,String templateName,String templateTag, HttpServletRequest req) throws Exception;


    /**
     * 根据标签查找模板
     * @param templateTag
     * @return
     */
    UniversalResponseBody<List<TemplateFileDetail>> getTemplateByTag(String templateTag);

    /**
     * 查找模板相应类型的页面
     * @param templateId
     * @param pageCategoryId
     * @return
     */
    UniversalResponseBody<List<Page>> getPagesByCategoryId(Integer templateId, Integer pageCategoryId);

    /**
     * 批量插入模板页面的详细信息
     * @param page
     * @return
     */
    UniversalResponseBody<Page> insertTemplatePageDetail(Page page) throws Exception;
}
