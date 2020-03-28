package com.jijian.ppt.Service;

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
     * @param templateFileDetail
     * @param req
     * @return
     * @throws IOException
     */
    public UniversalResponseBody<TemplateFileDetail> uploadTemplateFile(MultipartFile uploadFile,TemplateFileDetail templateFileDetail, HttpServletRequest req) throws IOException;


    /**
     * 根据标签查找模板
     * @param templateTag
     * @return
     */
    public UniversalResponseBody<List<TemplateFileDetail>> getTemplateByTag(String templateTag);
}
