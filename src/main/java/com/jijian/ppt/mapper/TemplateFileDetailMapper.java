package com.jijian.ppt.mapper;

import com.jijian.ppt.POJO.TemplateFileDetail;

import java.util.List;

/**
 * 模板文件数据库接口
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 14:08
 */
public interface TemplateFileDetailMapper {

    /**
     * 插入模板文件相关信息
     * @param templateFileDetail
     * @return
     */
    int InsertTemplateFileDetail (TemplateFileDetail templateFileDetail);


    /**
     *根据模板id查找模板文件路径
     * @param templateId
     * @return
     */
    String GetTemplateFilePath(Integer templateId);

    /**
     * 根据模板文件标签查询相关模板文件
     * @param templateTag
     * @return
     */
    List<TemplateFileDetail> GetTemplateDetailByTag(String templateTag);

    /**
     * 根据模板Id查找模板
     * @param templateId
     * @return
     */
    TemplateFileDetail getTemplateFileDetailByTemplateId(Integer templateId);
}
