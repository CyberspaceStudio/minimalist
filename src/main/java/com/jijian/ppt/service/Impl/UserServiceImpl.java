package com.jijian.ppt.service.Impl;

import com.jijian.ppt.POJO.DTO.TokenPO;
import com.jijian.ppt.POJO.Info.WxResponseInfo;
import com.jijian.ppt.POJO.User;
import com.jijian.ppt.service.UserService;

import com.jijian.ppt.mapper.TokenMapper;
import com.jijian.ppt.mapper.UserMapper;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.Tokenutil;
import com.jijian.ppt.utils.WeChatUtil;
import com.jijian.ppt.utils.response.UniversalResponseBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/2/2 14:36
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private WeChatUtil weChatUtil;
    @Resource
    private Tokenutil tokenutil;
    @Resource
    private UserMapper userMapper;
    @Resource
    private TokenMapper tokenMapper;


    /**
     * 微信服务器返回code无效的结果代码40029
     */
    private static String wxSucCode = "0";

    @Override
    public UniversalResponseBody<TokenPO> userWxLogin(String code){
        WxResponseInfo wxResponseInfo = weChatUtil.getWeChatResponseBody(code);
        //Code无效
        if (wxResponseInfo.getErrcode()!=null){
            //将微信返回错误代码及结果记录到日志中
            log.error("微信登录出错"+code+wxResponseInfo.getErrcode()+"\t"+ wxResponseInfo.getErrmsg());
            return new UniversalResponseBody(ResponseResultEnum.CODE_IS_INVALID.getCode(),ResponseResultEnum.CODE_IS_INVALID.getMsg());
        }
        String token = null;
        TokenPO tokenPO = null;
        //该用户在数据库中的数据
        log.info(wxResponseInfo.getOpenid());
        User user = userMapper.findUserByOpenId(wxResponseInfo.getOpenid());
        log.info(user.toString());
        //数据库中已经存在该用户
        if(user!=null) {
            token = tokenMapper.findTokenByUserId(user.getUserId());
            log.info("token为"+token+"\t");
            tokenPO = new TokenPO(user, token);
            return new UniversalResponseBody<TokenPO>(ResponseResultEnum.USER_HAVE_EXIST.getCode(),ResponseResultEnum.USER_HAVE_EXIST.getMsg(),tokenPO);
        }else {
            //插入用户
            user = new User(wxResponseInfo.getOpenid());
            userMapper.InsertUser(user);

            //根据userId生成token
            token = tokenutil.TokenByUserId(user.getUserId());
            tokenPO = new TokenPO(user, token);
            tokenMapper.insertToken(user.getUserId(), token);
        }
        return new UniversalResponseBody<TokenPO>(ResponseResultEnum.USER_LOGIN_SECCESS.getCode(),ResponseResultEnum.USER_LOGIN_SECCESS.getMsg(),tokenPO);
    }

}
