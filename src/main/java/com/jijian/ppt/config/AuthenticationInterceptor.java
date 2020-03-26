package com.jijian.ppt.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.jijian.ppt.POJO.User;
import com.jijian.ppt.annotation.PassToken;
import com.jijian.ppt.annotation.UserLogin;
import com.jijian.ppt.mapper.TokenMapper;
import com.jijian.ppt.mapper.UserMapper;
import com.jijian.ppt.utils.Enum.ResponseResultEnum;
import com.jijian.ppt.utils.response.ResponseException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/2/2 20:01
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Resource
    private UserMapper userMapper;
    @Resource
    private TokenMapper tokenMapper;

    public AuthenticationInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLogin.class)) {
            UserLogin userLogin = method.getAnnotation(UserLogin.class);
            if (userLogin.required()) {
                // 执行认证
                if (token == null) {
                    throw new ResponseException("登陆失败", ResponseResultEnum.USER_NO_TOKEN.getCode(),ResponseResultEnum.USER_NO_TOKEN.getMsg());
                }
                // 获取 token 中的userId
                Integer userId;
                try {
                    userId = Integer.valueOf(JWT.decode(token).getAudience().get(0));
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                User user =userMapper.findUserByUserId(userId);
                if (user == null) {
                    //用户不存在
                    throw  new ResponseException("用户不存在",ResponseResultEnum.USER_LOGIN_ERROR.getCode(),ResponseResultEnum.USER_LOGIN_ERROR.getMsg());
                }
                //将请求中带有的token与数据库中的token进行比较
                String trueToken = tokenMapper.findTokenByUserId(userId);
                if (trueToken.equals(token)){
                    return true;
                }else {
                    return false;
                }
            }


        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
