package com.jijian.ppt.mapper;

import com.jijian.ppt.POJO.Page;

import java.util.List;

/**
 * PPT页面具体信息数据库操作接口
 */
public interface TemplatePageDetailMapper {

    /**
     * 插入页具体信息
     * @param page
     * @return
     */
    int insertPageDetail(Page page);

    /**
     * 根据页面Id来获取页面的详细信息
     * @param pageId
     * @return
     */
    Page getPageByPageId(Integer pageId);

    /**
     * 根据模板Id查询页面
     * @param templateId
     * @return
     */
    List<Page> getPagesByTemplateId(Integer templateId);

    /**
     * 根据模板Id和类型Id查询页面
     * @param templateId
     * @param pageCategoryId
     * @return
     */
    List<Page> getPagesByTemplateIdAndCategoryId(Integer templateId,Integer pageCategoryId);
}
