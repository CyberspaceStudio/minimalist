package com.jijian.ppt.utils.Enum;

/**
 * 文件类别枚举类
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/26 9:30
 */
public enum FileCategoryEnum {


    /**
     * 模板文件
     */
    TEMPLATE(0,"模板"),

    /**
     * 普通用户文件
     */
    USERFILE(1,"用户文件");


    /**
     * 文件类别ID
     */
    private Integer fileCategoryId;

    /**
     * 文件类别明细
     */
    private String fileCategoryDetail;

    FileCategoryEnum(Integer fileCategoryId, String fileCategoryDetail) {
        this.fileCategoryId = fileCategoryId;
        this.fileCategoryDetail = fileCategoryDetail;
    }

    public Integer getFileCategoryId() {
        return fileCategoryId;
    }

    public void setFileCategoryId(Integer fileCategoryId) {
        this.fileCategoryId = fileCategoryId;
    }

    public String getFileCategoryDetail() {
        return fileCategoryDetail;
    }

    public void setFileCategoryDetail(String fileCategoryDetail) {
        this.fileCategoryDetail = fileCategoryDetail;
    }
}
