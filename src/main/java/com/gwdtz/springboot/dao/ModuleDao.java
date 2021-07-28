package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.ModuleDO;
import com.gwdtz.springboot.entity.ModuleTree;

import com.gwdtz.springboot.entity.layui.UserModule;
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
public interface ModuleDao {
    List<ModuleDO> getModuleAllList();

    List<ModuleDO> getModuleList(@Param("deptid") String deptid);

    int deleteByPrimaryKey(@Param("id") String id);

    List<String> getAllNodesIds(String parentId);

    int deleteParentNodesByAllIds(@Param("ids") String[] allParentAndChildIds);


    Integer insertSelective(ModuleDO record);

    ModuleDO selectModuleByPrimaryKey(@Param("id") String id);

    List<ModuleDO> selectByPid(@Param("pid") Long pid);

    //获取网站各模块
    List<ModuleTree> getByPidTreeCheck(@Param("id") String id);

    //根据用户获取网站各模块
    List<ModuleTree> getByPidTreeCheckQxs(@Param("id") String id, @Param("ids") String[] ids);

    int updateByPrimaryKeySelective(ModuleDO record);
    int updateNameByPrimaryKeySelective(ModuleDO record);

    String getMaxSortno(long pid);

    Integer getIschild(long id);

    Integer updateIschild(ModuleDO record);

    ModuleDO findPid(@Param("deptid") long deptid);

    Integer getMaxId();

    void deleteModuleByDeptId(@Param("deptid") long deptid);

    Integer batchUrlUpdate(ModuleDO moduleDO);

    Integer updateTemplate(ModuleDO record);

    Integer deleteByDeptId(@Param("deptid") String deptid);

    List<ModuleTree> getByPidTreeCheckQxsHref(String valueOf, String[] split);

    List<ModuleDO> getModuleListByCode(@Param("code") String s);

    List<UserModule> getUserModuleRootQxs(@Param("id") String id, @Param("ids") String[] ids);

    List<ModuleDO> getModuleLists(@Param("ids") String[] ids);
}
