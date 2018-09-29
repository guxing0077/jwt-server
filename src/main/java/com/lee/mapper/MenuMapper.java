package com.lee.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.lee.entity.Menu;

public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 根据角色id获取权限id
     * @param roleId
     * @return
     */
	List<Integer> findPermIdsByRoleId(@Param("roleId") Integer roleId);

	/**
	 * 根据权限id查询权限标识
	 * @param permIds
	 * @return
	 */
	List<String> findUrlsByIds(@Param("permIds") List<Integer> permIds);
}
