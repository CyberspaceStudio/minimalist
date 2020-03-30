package com.jijian.ppt.service;

import com.jijian.ppt.POJO.CoverPage;
import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import java.io.IOException;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/24 17:02
 */
public interface CoverPageService {

    /**
     * 修改封面页
     * @param userId
     * @param coverPage
     * @param fileId
     * @return
     */
    UniversalResponseBody<FileDetail> modifyCoverPage(Integer userId, CoverPage coverPage,Integer fileId);

    /**
     * 制作封面页
     * @param userId
     * @param coverPage
     * @param templateId
     * @return
     */
    UniversalResponseBody<FileDetail> makeCoverPage(Integer userId, CoverPage coverPage, Integer templateId) throws IOException;
}
