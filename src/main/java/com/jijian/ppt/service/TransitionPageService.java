package com.jijian.ppt.service;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;

import java.io.IOException;

/**
 * 过渡页
 * @author 郭树耸
 * @version 1.0
 * @date 2020/5/3 20:35
 */
public interface TransitionPageService {

    /**
     * 制作过渡页
     * @param fileId
     * @param title
     * @return
     */
    UniversalResponseBody<FileDetail> makeTransitionPageV1(Integer fileId, String title) throws IOException;

    /**
     * 制作过渡页
     * @param fileId
     * @param title
     * @return
     */
    UniversalResponseBody<FileDetail> makeTransitionPage(Integer fileId, Integer pageId,String title) throws IOException;
}
