package com.lee.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    /**
     * 根据角色id获取权限id
     * @param roleId
     * @return
     */
    List<Integer> findPermsByRoleId(@Param("roleId") Integer roleId);
}
