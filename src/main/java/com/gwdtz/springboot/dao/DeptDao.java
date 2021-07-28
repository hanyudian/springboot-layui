package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.DeptDO;
import com.gwdtz.springboot.entity.DeptTree;
import com.gwdtz.springboot.entity.DeptTreeDO;
import com.gwdtz.springboot.entity.layui.DeptOneSel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @Author hanshuai(the developing of Four)
* @Description
 * @param
* @return
* @Date 2021/2/24 15:05
**/
@Repository
@Mapper
public interface DeptDao {
    /**
    * @Author hanshuai(the developing of Four)
    * @Description 获取所有部门信息
     * @param
    * @return java.util.List<com.swust.springboot.entity.DeptDO>
    * @Date 2021/2/24 14:37
    **/
    List<DeptDO> getAll();

    /**
    * @Author hanshuai(the developing of Four)
    * @Description 返回树根节点
     * @param deptid
    * @return java.util.List<com.swust.springboot.entity.DeptTree>
    * @Date 2021/3/23 11:25
    **/
    List<DeptTree> getTreeRoot(@Param("deptid") long deptid);

    /**
    * @Author hanshuai(the developing of Four)
    * @Description 返回sys_dept表的树结构
     * @param pid
    * @return java.util.List<com.swust.springboot.entity.ModuleTree>
    * @Date 2021/2/25 15:29
    **/
    List<DeptTree> getByPidTreeCheck(@Param("pid") long pid);

    /**
    * @Author hanshuai(the developing of Four)
    * @Description sys_dept表返回id与pid等于deptid的部门信息
     * @param deptid
    * @return java.util.List<com.swust.springboot.entity.DeptDO>
    * @Date 2021/3/3 9:54
    **/
    List<DeptDO> getDepartmentListByDeptId(@Param("deptid") long deptid);

    /**
    * @Author hanshuai(the developing of Four)
    * @Description sys_dept表返回deptid等于deptid的信息
     * @param deptid
    * @return com.swust.springboot.entity.DeptDO
    * @Date 2021/3/3 14:27
    **/
    DeptDO selectDeptById(@Param("deptid") long deptid);



    /**
    * @Author hanshuai(the developing of Four)
    * @Description 根据dept的deptlveelcode模糊查找
     * @param deptlevelcode
    * @return java.util.List<com.swust.springboot.entity.DeptDO>
    * @Date 2021/3/24 15:20
    **/
    List<DeptDO> getDepartmentListByDeptLevelCode(String deptlevelcode);

    List<DeptTreeDO> getByPidTreeCheckgy(@Param("id") long id);

    List<DeptTreeDO> getByDeptCheck(@Param("id") long id);

    List<DeptTreeDO> getByPidTreeCheckgySix(@Param("id") long id, @Param("num") Integer num);

    List<DeptTreeDO> getByDeptCheckSix(@Param("id") long id, @Param("num") Integer num);

    Integer getPid(Integer id);

    int updateByPrimaryKeySelective(DeptDO record);

    int insertSelective(DeptDO record);

    int deleteByPrimaryKey(Long deptid);

    List<String> getAllNodesIds(String parentId);

    int deleteParentNodesByAllIds(@Param("ids") String[] allParentAndChildIds);

    int updateParentNodesByAllIds(@Param("ids") String[] allParentAndChildIds);

    String getMaxSortno(Integer pid);

    Integer getIschild(Integer deptid);

    Integer updateIschild(DeptDO record);

    String getDeptName(Integer deptid);

    Integer updateDeleted(Integer deptid);

    /**
     * @Author hanshuai(the developing of Four)
     * @Description 返回sys_dept表的树结构
     * @param pid
     * @return java.util.List<com.swust.springboot.entity.ModuleTree>
     * @Date 2021/2/25 15:29
     **/
    List<DeptTree> getDeptList(@Param("pid") Long pid,@Param("top") Long top);

    //管理员登录后显示部门树结构
    List<DeptTreeDO> getbypidfrontnav(@Param("id") long id);

    List<DeptOneSel> getDeptOneSelRoot(long id);

    List<DeptOneSel> getDeptOneSelChild(long id);
}