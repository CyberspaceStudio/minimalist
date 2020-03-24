package com.jijian.ppt.POJO;


import lombok.Data;


/**
 * 用户对象
 * @author 郭树耸
 * @version 1.0
 * @date 2020/2/1 13:26
 */
@Data
public class User {

    /**
     * 用户唯一id
     */
    private Integer userId;


    /**
     * 用户在系统中的唯一ID
     */
    private String openid;


    public User(Integer userId) {
        this.userId = userId;
    }

    public User() {
    }

    public User(String openid){
        this.openid = openid;
    }

}
