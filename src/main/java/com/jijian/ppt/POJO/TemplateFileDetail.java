package com.jijian.ppt.POJO;

import lombok.Data;

/**
 * 模板文件类
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 13:46
 */
@Data
public class TemplateFileDetail {

    /**
     * 模板文件id
     */
    private Integer templateId;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 模板文件路径
     */
    private String filePath;

    /**
     * 封面路径url
     */
    private String coverImageUrl;

    /**
     * 模板文件标签
     */
    private String templateTag;
}
