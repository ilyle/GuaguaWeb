package com.xiaoqi.guaguaweb.service;


import com.xiaoqi.guaguaweb.domain.User;

public interface UserService {
    /**
     * 用户登录
     *
     * @param username 登录名
     * @param password 密码
     * @return User对象
     */
    User login(String username, String password);

    /**
     * 使用token登录，一般用于自动登录
     *
     * @param token token
     * @return User对象
     */
    User loginWithToken(String token);

    /**
     * 用户登出
     *
     * @param uid 用户ID
     * @return User对象
     */
    User logout(String uid);

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @return User对象
     */
    User register(String username, String password);

    /**
     * 更新头像
     *
     * @param uid        用户ID
     * @param avatar 头像路径
     * @return User对象
     */
    User updateAvatar(String uid, String avatar);
}
