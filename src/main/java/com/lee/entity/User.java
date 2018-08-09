package com.lee.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.util.Date;

@TableName("user")
@Data
public class User {

    private Integer id;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private Date createTime;
    private Date updateTime;
}
