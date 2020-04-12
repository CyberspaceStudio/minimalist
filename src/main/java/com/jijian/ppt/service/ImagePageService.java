package com.jijian.ppt.service;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;

import java.io.IOException;

/**
 *图文页相关服务层
 * @author 武泽中
 * @version 1.0
 * @date 2020/3/30 13：49
 */
public interface ImagePageService {
    /**
     * 初次制作图文页
     * @param fileId
     * @param pictureUrls
     * @param title
     * @param paragraph
     * @return
     * @throws IOException
     */

    UniversalResponseBody<FileDetail> makeImagePage(Integer fileId,String[] pictureUrls,String title,String paragraph) throws IOException;



    public UniversalResponseBody<FileDetail> makeImagePage(Integer fileId,String[] pictureUrls,String title,String paragraph) throws IOException;

}
