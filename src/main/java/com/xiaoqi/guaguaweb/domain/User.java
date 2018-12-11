package com.xiaoqi.guaguaweb.domain;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;
    private String uid; // md5(username + ts)
    private String username;
    private String password; // 由客户端对原始密码做MD5加密
    private String token; // MD5(username + password)
    private long tsTokenExpire; // token过期时间戳
    private boolean isLogin;
    private String avatar; // 图像路径

    public User(Integer id, String uid, String username, String password,  String avatar, boolean isLogin, String token, long tsTokenExpire) {
        setId(id);
        setUid(uid);
        setUsername(username);
        setPassword(password);
        setToken(token);
        setTsTokenExpire(tsTokenExpire);
        setIsLogin(isLogin);
        setAvatar(avatar);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTsTokenExpire() {
        return tsTokenExpire;
    }

    public void setTsTokenExpire(long tsTokenExpire) {
        this.tsTokenExpire = tsTokenExpire;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
