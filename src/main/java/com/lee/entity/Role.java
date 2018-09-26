package com.lee.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("t_role")
public class Role {
    //id
    private Integer id;
    //角色名称
    private String name;
}
