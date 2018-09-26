package com.lee.service;

import com.baomidou.mybatisplus.service.IService;
import com.lee.entity.User;
import com.lee.res.login.LoginReq;

public interface UserService extends IService<User> {

    /**
     * 用户登录
     * @param loginReq 用户名密码
     * @return token
     */
    String login(LoginReq loginReq);
}
