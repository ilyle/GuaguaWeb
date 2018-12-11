package com.xiaoqi.guaguaweb.mapper;

import com.xiaoqi.guaguaweb.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface UserMapper {
    /**
     * 根据用户名和密码查询用户
     *
     * @param username 登录名
     * @param password 密码
     * @return User对象
     */
    @Select("select * from tb_user where username = #{username} and password = #{password}")
    User findWithUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户uid查询用户
     *
     * @param uid uid
     * @return User对象
     */
    @Select("select * from tb_user where uid = #{uid}")
    User findWithUid(@Param("uid") String uid);

    /**
     * 根据Token查询用户
     *
     * @param token token
     * @return User对象
     */
    @Select("select * from tb_user where token = #{token}")
    User findWithToken(@Param("token") String token);

    /**
     * 设置用户登录状态为true
     *
     * @param uid uid
     */
    @Update("update tb_user set is_login = '1', token = #{token}, ts_token_expire = #{ts_token_expire} where uid = #{uid}")
    void setLogin(@Param("uid") String uid, @Param("token") String token, @Param("ts_token_expire") long tsTokenExpire);

    /**
     * 设置用户登录状态为false
     *
     * @param uid uid
     */
    @Update("update tb_user set is_login = '0', token = '', ts_token_expire = '0' where uid = #{uid}")
    void setLogout(@Param("uid") String uid);

    /**
     * 根据用户名查找用户，用于username已被注册的情况
     *
     * @param username 用户名
     * @return User对象
     */
    @Select("select * from tb_user where username = #{username}")
    User findWithUsername(@Param("username") String username);


    /**
     * 往tb_user表中插入新数据
     *
     * @param uid      uid
     * @param username 用户名
     * @param password 密码
     */
    @Insert("INSERT INTO tb_user (uid, username, password) VALUES (#{uid}, #{username}, #{password})")
    void insertUser(@Param("uid") String uid, @Param("username") String username, @Param("password") String password);

    /**
     * 更新tb_user的avatar字段
     *
     * @param uid    uid
     * @param avatar 头像路径
     */
    @Update("update tb_user set avatar = #{avatar} where uid = #{uid}")
    void updateAvatar(@Param("uid") String uid, @Param("avatar") String avatar);
}