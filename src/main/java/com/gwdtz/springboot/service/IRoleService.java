package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.RoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-2-23 16:10
 * @Author: Mr.Yanjc
 * @Description:
 */
public interface IRoleService {
    List<RoleDO> selectList();
    /**
     * 通过导航名称获取前台导航列表
     *
     * @param name
     * @return
     */
    RoleDO getRoleName(String name);
    /**
     * 通过部门编号获取前台导航列表
     *
     * @param
     * @param name
     * @param description
     * @return
     */
    List<RoleDO> getRoleList(String name, String description);
    /**
     * 插入记录
     *
     * @param roleDO 前台导航对象
     * @return
     */
    Integer insert(RoleDO roleDO);
    /**
     * 更新记录
     *
     * @param roleDO 前台导航对象
     * @return
     */
    Integer update(RoleDO roleDO);
    /**
     * 删除记录
     *
     * @param id
     * @return
     */
    Integer deleteById(@Param("roleId") Long id);

    RoleDO selectRoleById(@Param("roleId") Long id);
}
