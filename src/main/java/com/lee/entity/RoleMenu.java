package com.lee.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName("r_role_menu")
public class RoleMenu {
    //id
    private Integer id;
    //角色id
    private Integer roleId;
    //菜单权限id
    private Integer menuId;
}
