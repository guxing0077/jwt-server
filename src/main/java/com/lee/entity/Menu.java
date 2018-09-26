package com.lee.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("t_menu")
public class Menu {
    //id
    private Integer id;
    //菜单权限名称
    private String name;
    //url
    private String url;
    //权限标识
    private String perms;
    //类型，0-菜单，1-按钮
    private Integer type;
}
