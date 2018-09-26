package com.lee.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.lee.common.BaseController;
import com.lee.common.JsonRes;
import com.lee.entity.User;
import com.lee.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiImplicitParam(name = "tokenKey", paramType = "header")
    @GetMapping("security/list")
    public JsonRes<User> users(){
        return success(userService.selectList(new EntityWrapper<User>()));
    }

}