package com.jijian.ppt.POJO;

import lombok.Data;

import java.util.UUID;

/**
 * PPT文件类
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/26 9:22
 */
@Data
public class FileDetail {

    /**
     * 文件id
     */
    private Integer fileId;

    /**
     * 文件所属的用户id
     */
    private Integer userId;

    /**
     * 所属模板文件Id
     */
    private Integer templateId;

    /**
     * 文件存储路径
     */
    private String filePath;

}
