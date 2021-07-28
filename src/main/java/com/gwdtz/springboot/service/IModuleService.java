package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.ModuleDO;
import com.gwdtz.springboot.entity.ModuleTree;

import com.gwdtz.springboot.entity.layui.UserModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-2-23 14:10
 * @Author: Mr.Yanjc
 * @Description:
 */
public interface IModuleService {
    List<ModuleDO> getModuleList(String deptid);
    List<ModuleDO> selectModuleByPid(Long pid);
    public List<ModuleTree> getAllListCheckJsonQxs(ModuleTree moduleTree,String Qxs);
    public List<ModuleTree> getAllListCheckJson(ModuleTree moduleTree);
    public List<ModuleTree> getAllListCheckJsonQxs(String deptlevelcode, ModuleTree moduleTree,String Qxs);
    Integer insertSelective(ModuleDO moduleDO);
    int deleteByPrimaryKey(String id);
    String getAllParentAndChildIds(String parentId, StringBuilder sb);
    int deleteParentNodesByAllIds(String[] allParentAndChildIds);
    ModuleDO selectModuleByPrimaryKey(String id);
    Integer updateByPrimaryKeySelective(ModuleDO record);
    Integer updateNameByPrimaryKeySelective(ModuleDO record);
    String getMaxSortno(long pid);
    Integer getIschild(long id);
    Integer updateIschild(ModuleDO record);

    ModuleDO findPid(@Param("deptid") long deptid);


    Integer getMaxId();

    void deleteModuleByDeptId(long deptid);

    Integer batchUrlUpdate(ModuleDO moduleDO);

    List<ModuleDO> getModuleListByCode(String s);


    public List<UserModule> getUserModuleQxs(UserModule userModule, String Qxs);


    List<ModuleDO> getModuleLists(String[] split);
}
