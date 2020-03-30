package com.jijian.ppt.service;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;

/**
 * @Author 桂乙侨
 * @Date 2020/3/29 22:19
 * @Version 1.0
 */
public interface ContentPageService {

    UniversalResponseBody<FileDetail> makeContentsPage(Integer fileId, String[] titles);
}
