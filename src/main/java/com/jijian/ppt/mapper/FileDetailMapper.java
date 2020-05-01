package com.jijian.ppt.mapper;

import com.jijian.ppt.POJO.FileDetail;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/26 14:33
 */
public interface FileDetailMapper {

    /**
     * 插入文件的具体信息
     * @param fileDetail
     * @return
     */
    int insertFileDetail(FileDetail fileDetail);


    /**
     * 根据文件id获取文件路径
     * @param fileId
     * @return
     */
    String getPathByFileId(Integer fileId);


    /**
     * 根据Id获取文件详细信息
     * @param fileId
     * @return
     */
    FileDetail getDetailByFileId(Integer fileId);


    /**
     * 修改ppt页数
     * @param fileId
     * @param pageCounts
     * @return
     */
    int updatePageCount(Integer fileId,Integer pageCounts);



}
