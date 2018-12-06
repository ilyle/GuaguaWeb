package com.xiaoqi.guaguaweb.service.impl;

import com.xiaoqi.guaguaweb.constant.Constant;
import com.xiaoqi.guaguaweb.domain.User;
import com.xiaoqi.guaguaweb.mapper.UserMapper;
import com.xiaoqi.guaguaweb.service.UserService;
import com.xiaoqi.guaguaweb.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(String username, String password) {
        long ts = System.currentTimeMillis(); // 当前时间戳
        long tsTokenExpire = ts + Constant.TS_3_DAYS;
        User user = userMapper.findWithUsernameAndPassword(username, password);
        if (user == null) {
            return null;
        } else {
            String token = Md5Util.getMD5Str(username + user.getUid() + ts);
            userMapper.setLogin(user.getUid(), token, tsTokenExpire);
            return userMapper.findWithUsernameAndPassword(username, password);
        }
    }

    public User loginWithToken(String token) {
        long ts = System.currentTimeMillis(); // 当前时间戳
        User user = userMapper.findWithToken(token);
        if (user != null && ts <= user.getTsTokenExpire()) { // token未过期
            long tsTokenExpire = ts + Constant.TS_3_DAYS; // 刷新token过期时间戳
            userMapper.setLogin(user.getUid(), token, tsTokenExpire);
            return userMapper.findWithUid(user.getUid());
        } else { // token已过期
            return null;
        }
    }

    public User logout(String uid) {
        userMapper.setLogout(uid);
        return userMapper.findWithUid(uid);
    }

    public User register(String username, String password) {
        User user = userMapper.findWithUsername(username);
        if (user != null) { // 用户名已被注册
            return null;
        } else {
            long ts = System.currentTimeMillis();
            String uid = Md5Util.getMD5Str(username + ts);
            uid = "gg-" + uid.substring(0, 20);
            userMapper.insertUser(uid, username, password);
            return userMapper.findWithUid(uid);
        }
    }
}
