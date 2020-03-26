package com.jijian.ppt.Service;

import com.jijian.ppt.POJO.DTO.TokenPO;
import com.jijian.ppt.utils.response.UniversalResponseBody;


/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/2/2 14:35
 */
public interface UserService {

    /**
     * 用户微信登录
     *
     * @param code
     * @return
     * @throws Exception
     */
    UniversalResponseBody<TokenPO> userWxLogin(String code);



}
