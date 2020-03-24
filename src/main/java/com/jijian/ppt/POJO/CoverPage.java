package com.jijian.ppt.POJO;

import lombok.Data;

/**
 * PPT封面页对应的对象
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/24 16:56
 */
@Data
public class CoverPage {

    /**
     * 标题
     */
    private String title;

    /**
     * 副标题
     */
    private String subtitle;

}
