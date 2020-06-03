package com.jijian.ppt.service;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;

import java.io.IOException;

/**
 * 正文页相关服务层
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 11:27
 */
public interface TextPageService {

    /**
     * 初次制作正文页
     * @param fileId
     * @param title
     * @param paragraph
     * @return
     * @throws IOException
     */
    UniversalResponseBody<FileDetail> makeTextPage(Integer fileId,String title,String paragraph) throws IOException;

    /**
     * 制作正文页第二版
     * @param fileId
     * @param pageId
     * @param titles
     * @param subTitles
     * @param paragraphs
     * @return
     * @throws IOException
     */
    UniversalResponseBody<FileDetail> makeTextPageV2(Integer fileId,Integer pageId,String[] titles,String[] subTitles,String[] paragraphs) throws IOException;
}
