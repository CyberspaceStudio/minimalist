package com.jijian.ppt.mapper;

import com.jijian.ppt.POJO.User;

/**
 * @author 郭树耸
 * @version 1.0
 * @date 2020/3/24 11:03
 */
public interface UserMapper {

    /**
     * 根据UserId查找用户
     * @param userId
     * @return
     */
    User findUserByUserId(Integer userId);

    /**
    * 根据微信返回的openId查找用户
     * @param openid
     * @return
     */
    User findUserByOpenId(String openid);


    /**
     * 新增用户,并返回数据库递增生成的userid
     * @param user
     * @return
     */
    int InsertUser(User user);
}
