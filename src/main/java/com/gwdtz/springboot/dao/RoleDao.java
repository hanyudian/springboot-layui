package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.RoleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @program: springboot
 * @Date: 2021-2-23 10:50
 * @Author: Mr.Yanjc
 * @Description:
 */
@Repository
@Mapper
public interface RoleDao {
    List<RoleDO> selectList();
    /**
     * 通过角色名称获取角色列表
     *
     * @param name
     * @return
     */
    RoleDO getRoleName(@Param("name") String name);
    /**
     * 通过状态获取角色列表
     *
     * @param
     * @param name
     * @param description
     * @return
     */
    List<RoleDO> getRoleList(@Param("name") String name,@Param("description") String description);
    /**
     * 插入记录
     *
     * @param roleDO 角色对象
     * @return
     */
    Integer insert(RoleDO roleDO);
    /**
     * 更新记录
     *
     * @param roleDO 角色对象
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
