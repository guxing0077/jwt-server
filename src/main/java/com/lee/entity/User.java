package com.lee.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@TableName("t_user")
@Data
public class User {

    private Integer id;
    private String userName;
    private String password;
    private Integer roleId;

    public User(){

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}
