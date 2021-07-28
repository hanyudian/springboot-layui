package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.DeptDO;
import com.gwdtz.springboot.entity.DeptTree;
import com.gwdtz.springboot.entity.DeptTreeDO;
import com.gwdtz.springboot.entity.layui.DeptOneSel;

import java.util.List;

/**
* @Author hanshuai(the developing of Four)
* @Description
 * @param
* @return
* @Date 2021/2/24 14:40
**/
public interface IDeptService {
    List<DeptDO> getAll();

    List<DeptTree> getAllListCheckJson(DeptTree deptTree);

    List<DeptTreeDO> getAllListCheckJsongy(DeptTreeDO deptTreeDO);

    List<DeptTreeDO> getAllListCheckJsonSix(DeptTreeDO deptTreeDO);

    List<DeptTreeDO> getAllListCheckJsongySix(DeptTreeDO deptTreeDO);

    List<DeptDO> getDepartmentListByDeptId(long deptid);

    DeptDO selectDeptById(long deptid);

    List<DeptDO> getDepartmentListByDeptLevelCode(String deptlevelcode);

    Integer getPid(Integer id);

    Integer insertSelective(DeptDO deptDO);

    Integer updateByPrimaryKeySelective(DeptDO record);

    int deleteByPrimaryKey(Long deptid);

    String getAllParentAndChildIds(String parentId, StringBuilder sb);

    int deleteParentNodesByAllIds(String[] allParentAndChildIds);

    int updateParentNodesByAllIds(String[] allParentAndChildIds);

    String getMaxSortno(Integer pid);

    Integer getIschild(Integer deptid);

    Integer updateIschild(DeptDO record);

    String getDeptName(Integer deptid);

    Integer updateDeleted(Integer deptid);

    List<DeptTreeDO> getByPidTreeCheckgy(long id);

    List<DeptTreeDO> getByPidTreeCheckgySix(long id);

    /**
     * @Author hanshuai(the developing of Four)
     * @Description 返回sys_dept表的树结构
     * @param pid
     * @return java.util.List<com.gwdtz.springboot.entity.ModuleTree>
     * @Date 2021/2/25 15:29
     **/
    //前台调用
    List<DeptTree> getDeptList(Long pid,Long top);


    List<DeptTreeDO> getfrontnav(DeptTreeDO deptTreeDO);

    List<DeptOneSel> getDeptOneSel(DeptOneSel deptOneSel);
}
