package com.jijian.ppt.service;


import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * PPT页面操作
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/29 9:23
 */
public interface PageService {
    /**
     * 移动页面
     * @param fileId
     * @param formPageNum
     * @param toPageNum
     * @return
     */
    UniversalResponseBody<FileDetail> movePage(Integer fileId,Integer formPageNum,Integer toPageNum) throws FileNotFoundException;

    /**
     * 删除页面
     * @param fileId
     * @param pageNum
     * @return
     */
    UniversalResponseBody<FileDetail> deletePage(Integer fileId,Integer pageNum);


    /**
     * 文件预览
     * @param fileId
     * @return
     */
    UniversalResponseBody<List<String>> filePreview(Integer fileId) throws Exception;
}
