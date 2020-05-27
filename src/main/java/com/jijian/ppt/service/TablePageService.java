package com.jijian.ppt.service;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;

import java.io.IOException;

/**
 *图表页相关服务层
 * @author 武泽中
 * @version 1.0
 * @date 2020/4/12
 */
public interface TablePageService {
    /**
     * 初次制作图表页
     * @param fileId
     * @param pageId
     * @param names
     * @param values
     * @param chartTitle
     * @return
     * @throws IOException
     */
    public UniversalResponseBody<FileDetail> makeTablePage(Integer fileId, Integer pageId,String[] names, String[] values, String chartTitle,String title,String paragraph) throws IOException;
}
