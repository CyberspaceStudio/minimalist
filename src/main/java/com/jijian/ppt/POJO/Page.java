package com.jijian.ppt.POJO;

import lombok.Data;

/**
 * PPT具体页面类
 * @program: minimalist
 * @author: GuoShuSong
 * @create: 2020-05-27 11:45
 **/
@Data
public class Page {

    /**
     * 页面Id
     * @ignore
     */
    Integer pageId;

    /**
     * 所属模板Id
     */
    Integer templateId;

    /**
     * 页面在模板处于的第几页
     */
    Integer pagePosition;

    /**
     * 页面类型id
     */
    Integer pageCategoryId;


    /**
     * 页面预览图片地址
     * @ignore
     */
    String pageImageUrl;
}
