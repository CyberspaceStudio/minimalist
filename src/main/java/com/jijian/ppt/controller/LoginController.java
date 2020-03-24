package com.jijian.ppt.controller;


import com.jijian.ppt.POJO.DTO.TokenPO;
import com.jijian.ppt.Service.UserService;
import com.jijian.ppt.annotation.PassToken;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 登录
 * @author 郭树耸
 * @version 1.0
 * @date 2020/2/8 11:20
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;


    /**
     * 用户微信小程序登录
     * @param code
     * @return
     */
    @PassToken
    @PostMapping("/wx")
    public UniversalResponseBody<TokenPO> userWxLogin(String code){
        return userService.userWxLogin(code);
    }


}
