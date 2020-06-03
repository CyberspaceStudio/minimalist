package com.jijian.ppt.service.Impl;

import com.jijian.ppt.POJO.Page;
import com.jijian.ppt.POJO.TemplateFileDetail;
import com.jijian.ppt.mapper.TemplatePageDetailMapper;
import com.jijian.ppt.service.TemplateService;
import com.jijian.ppt.mapper.TemplateFileDetailMapper;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.PdfToImg;
import com.jijian.ppt.utils.Pptx2PdfUtil;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 模板文件
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 13:59
 */
@Service
@Slf4j
public class TemplateServiceImpl implements TemplateService {

    @Resource
    private TemplateFileDetailMapper templateFileDetailMapper;
    @Resource
    private TemplatePageDetailMapper templatePageDetailMapper;
    @Resource
    private Pptx2PdfUtil pptx2PdfUtil;
    @Resource
    private PdfToImg pdfToImg;

    private static String templateDirectory = "/a-minimalist/template";

    /**
     * 上传模板文件
     * @param uploadFile
     * @param templateName
     * @param templateTag
     * @param req
     * @return
     * @throws Exception
     */
    @Override
    public UniversalResponseBody<TemplateFileDetail> uploadTemplateFile(MultipartFile uploadFile,String templateName,String templateTag, HttpServletRequest req) throws Exception {
        TemplateFileDetail templateFileDetail = new TemplateFileDetail();
        templateFileDetail.setTemplateName(templateName);
        templateFileDetail.setTemplateTag(templateTag);
        String uuid = UUID.randomUUID().toString();
        String newName = uuid+".pptx";
        String filePath =templateDirectory+"/"+uuid+"/"+ newName;
        String parentDirectory =  templateDirectory+ "/"+uuid+"/";
        File parentFile = new File(parentDirectory);
        if (!parentFile.isDirectory()) {
            //每个文件对应一个日期下的一个文件夹，如果不存在则新建一个文件夹
            parentFile.mkdirs();
        }
        templateFileDetail.setFilePath(filePath);
        //文件的保存操作
        uploadFile.transferTo(new File(parentDirectory,newName));
        List<String> imgUrls =  pdfToImg.pdfToImageOnePageOnImage(pptx2PdfUtil.fileToPdf(filePath));
        templateFileDetailMapper.InsertTemplateFileDetail(templateFileDetail);
        templateFileDetail.setCoverImageUrl(imgUrls.get(0));
        templateFileDetailMapper.InsertTemplateFileDetail(templateFileDetail);
        return new UniversalResponseBody<TemplateFileDetail>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),templateFileDetail);
    }


    @Override
    public UniversalResponseBody<List<TemplateFileDetail>> getTemplateByTag(String templateTag) {
        List<TemplateFileDetail> templateFileDetails = templateFileDetailMapper.GetTemplateDetailByTag(templateTag);
        if (templateFileDetails == null){
            return new UniversalResponseBody(ResponseResultEnum.PARAM_IS_INVALID.getCode(),ResponseResultEnum.PARAM_IS_INVALID.getMsg());
        }else{
            return new UniversalResponseBody<List<TemplateFileDetail>>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),templateFileDetails);
        }
    }

    @Override
    public UniversalResponseBody<List<Page>> getPagesByCategoryId(Integer templateId, Integer pageCategoryId) {
        List<Page> pageList = templatePageDetailMapper.getPagesByTemplateIdAndCategoryId(templateId,pageCategoryId);
        if (pageList == null){
            return new UniversalResponseBody(ResponseResultEnum.PARAM_IS_INVALID.getCode(),ResponseResultEnum.PARAM_IS_INVALID.getMsg());
        }else{
            return new UniversalResponseBody< List<Page>>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),pageList);
        }
    }

    @Override
    public UniversalResponseBody<Page> insertTemplatePageDetail(Page page) throws Exception {
        String filePath = templateFileDetailMapper.GetTemplateFilePath(page.getTemplateId());
        List<String> imgUrls =  pdfToImg.pdfToImageOnePageOnImage(pptx2PdfUtil.fileToPdf(filePath));
            page.setPageImageUrl(imgUrls.get(page.getPagePosition()));
            if (templatePageDetailMapper.insertPageDetail(page) <= 0) {
                log.error("模板页面详细信息插入错误" + page.toString());
            }
        return new UniversalResponseBody<Page>(ResponseResultEnum.SUCCESS.getCode(),ResponseResultEnum.SUCCESS.getMsg(),page);
    }
}
