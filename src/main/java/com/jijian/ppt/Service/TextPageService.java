package com.jijian.ppt.Service;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;

/**
 * 正文页相关服务层
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 11:27
 */
public interface TextPageService {

    /**
     * 初次制作正文页
     * @param userId
     * @param fileId
     * @param paragraph
     * @return
     */
    public UniversalResponseBody<FileDetail> makeTextPage(Integer userId,Integer fileId,String paragraph);
}
