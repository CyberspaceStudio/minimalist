package com.jijian.ppt.Service.Impl;

import com.jijian.ppt.POJO.FileDetail;
import com.jijian.ppt.Service.TextPageService;
import com.jijian.ppt.utils.FileUtil;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 正文页相关接口
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/28 12:13
 */
@Service
@Slf4j
public class TextPageServiceImpl implements TextPageService {

    @Resource
    private FileUtil fileUtil;

    @Override
    public UniversalResponseBody<FileDetail> makeTextPage(Integer userId,Integer fileId, String paragraph) {
        return null;
    }
}
