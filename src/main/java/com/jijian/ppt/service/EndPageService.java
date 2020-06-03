package com.jijian.ppt.service;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 结束页
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/31 8:35
 */
public interface EndPageService {

    /**
     * 制作结束页
     * @param fileId
     * @param title
     * @return
     */
    UniversalResponseBody<FileDetail> makeEndPageV1(Integer fileId,String title) throws IOException;

    /**
     * 制作结束页
     * @param fileId
     * @param pageId
     * @param title
     * @return
     */
    UniversalResponseBody<FileDetail> makeEndPage(Integer fileId,Integer pageId,String title) throws IOException;
}
