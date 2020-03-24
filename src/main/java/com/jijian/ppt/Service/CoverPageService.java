package com.jijian.ppt.Service;

import com.jijian.ppt.POJO.CoverPage;
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
    UniversalResponseBody modifyCoverPage(Integer userId, CoverPage coverPage,Integer fileId);

    /**
     *
     * @param userId
     * @param coverPage
     * @param templateId
     * @return
     */
    UniversalResponseBody makeCoverPage(Integer userId, CoverPage coverPage,Integer templateId) throws IOException;
}
