package com.gwdtz.springboot.service.impl;

import com.gwdtz.springboot.dao.DeptDao;
import com.gwdtz.springboot.entity.DeptDO;
import com.gwdtz.springboot.entity.DeptTree;
import com.gwdtz.springboot.entity.DeptTreeDO;
import com.gwdtz.springboot.entity.layui.DeptOneSel;
import com.gwdtz.springboot.service.IDeptService;
import com.gwdtz.springboot.utils.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
* @Author hanshuai(the developing of Four)
* @Description
 * @param
* @return
* @Date 2021/2/24 14:40
**/
@Service
public class DeptServiceImpl implements IDeptService {

    @Autowired
    DeptDao deptDao;

    @Override
    public List<DeptDO> getAll() {
        return deptDao.getAll();
    }


    @Override
    public List<DeptTreeDO> getByPidTreeCheckgy(long id){
        return  deptDao.getByPidTreeCheckgy(id);
    }

    @Override
    public List<DeptTree> getAllListCheckJson(DeptTree deptTree) {
        //先获取数根节点
        List<DeptTree> list = deptDao.getTreeRoot(deptTree.getDeptid());
        //递归获取子节点
        diguiTreeCheck(list);
        return list;
    }

    @Override
    public List<DeptTreeDO> getAllListCheckJsongy(DeptTreeDO deptTreeDO) {
        //先获取数根节点
        List<DeptTreeDO> list = deptDao.getByDeptCheck(deptTreeDO.getId());
        //递归获取子节点
        diguiTreeCheckgy(list);
        return list;
    }

    public void diguiTreeCheckgy(List<DeptTreeDO> list){
        for(DeptTreeDO deptTreeDO : list){
            List<DeptTreeDO> listTemp = deptDao.getByPidTreeCheckgy(deptTreeDO.getId());
            if(listTemp.size() > 0){
                deptTreeDO.setChildren(listTemp);
                diguiTreeCheckgy(listTemp);
            }
        }
    }

    @Override
    public List<DeptTreeDO> getByPidTreeCheckgySix(long id){
        return  deptDao.getByPidTreeCheckgySix(id, 6);
    }

    @Override
    public List<DeptTreeDO> getAllListCheckJsongySix(DeptTreeDO deptTreeDO) {
        //先获取数根节点
        List<DeptTreeDO> list = deptDao.getByDeptCheckSix(deptTreeDO.getId(), 6);
        //递归获取子节点
        diguiTreeCheckgySix(list);
        return list;
    }

    @Override
    public List<DeptTreeDO> getAllListCheckJsonSix(DeptTreeDO deptTreeDO) {
        //先获取数根节点
        List<DeptTreeDO> list = deptDao.getByDeptCheckSix(deptTreeDO.getId(), 6);
        //递归获取子节点
        diguiTreeCheckgySix(list);
        return list;
    }

    public void diguiTreeCheckgySix(List<DeptTreeDO> list){
        for(DeptTreeDO deptTreeDO : list){
            List<DeptTreeDO> listTemp = deptDao.getByPidTreeCheckgySix(deptTreeDO.getId(), 6);
            if(listTemp.size() > 0){
                deptTreeDO.setChildren(listTemp);
                diguiTreeCheckgySix(listTemp);
            }
        }
    }

    @Override
    public List<DeptDO> getDepartmentListByDeptId(long deptid) {
        return deptDao.getDepartmentListByDeptId(deptid);
    }

    @Override
    public DeptDO selectDeptById(long deptid) {
        return deptDao.selectDeptById(deptid);
    }


    @Override
    public List<DeptDO> getDepartmentListByDeptLevelCode(String deptlevelcode) {
        return deptDao.getDepartmentListByDeptLevelCode(deptlevelcode);
    }

    public void diguiTreeCheck(List<DeptTree> list){
        for(DeptTree deptTree : list){
            List<DeptTree> listTemp = deptDao.getByPidTreeCheck(deptTree.getDeptid());
            if(listTemp.size() > 0){
                deptTree.setChildren(listTemp);
                diguiTreeCheck(listTemp);
            }
        }
    }

    @Override
    public  Integer getPid(Integer id){
        return deptDao.getPid(id);
    }

    @Override
    public Integer insertSelective(DeptDO deptDO) {
        return deptDao.insertSelective(deptDO);
    }

    @Override
    public Integer updateByPrimaryKeySelective(DeptDO deptDO) {
        return deptDao.updateByPrimaryKeySelective(deptDO);
    }

    @Override
    public int deleteByPrimaryKey(Long deptid) {
        return deptDao.deleteByPrimaryKey(deptid);
    }

    @Override
    public String getAllParentAndChildIds(String parentId, StringBuilder sb) {
        String ids = parentId + ",";
        sb.append(ids);
        List<String> allNodesIds = deptDao.getAllNodesIds(parentId);
        if (allNodesIds.size() > 0) {
            String join = StringUtils.join(allNodesIds.toArray(), ',');
            sb.append(join).append(',');
            for (String s : allNodesIds) {
                getAllParentAndChildIds(s, sb);
            }
        }
        String paerentAndChildIds = sb.toString().substring(0, sb.toString().length() - 1);
        List<String> list1 = Arrays.asList(paerentAndChildIds.split(","));
        List<String> list = ListUtils.removeDuplicate(list1);
        return StringUtils.join(list.toArray(), ',');
    }

    @Override
    public int deleteParentNodesByAllIds(String[] allParentAndChildIds) {
        return deptDao.deleteParentNodesByAllIds(allParentAndChildIds);
    }

    @Override
    public int updateParentNodesByAllIds(String[] allParentAndChildIds) {
        return  deptDao.updateParentNodesByAllIds(allParentAndChildIds);
    }

    @Override
    public  String getMaxSortno(Integer pid){
        return deptDao.getMaxSortno(pid);
    }

    @Override
    public  Integer getIschild(Integer deptid){
        return deptDao.getIschild(deptid);
    }

    @Override
    public Integer updateIschild(DeptDO deptDO) {
        return deptDao.updateIschild(deptDO);
    }

    @Override
    public String getDeptName(Integer deptid){
        return deptDao.getDeptName(deptid);
    }

    @Override
    public Integer updateDeleted(Integer deptid) {
        return deptDao.updateDeleted(deptid);
    }




    @Override
    public List<DeptTree> getDeptList(Long pid,Long top) {
        return deptDao.getDeptList(pid,top);
    }

    @Override
    public List<DeptTreeDO> getfrontnav(DeptTreeDO deptTreeDO) {
        //先获取数根节点
        List<DeptTreeDO> list = deptDao.getbypidfrontnav(deptTreeDO.getId());
        //递归获取子节点
        diguiTreefrontnav(list);
        return list;
    }



    public void diguiTreefrontnav(List<DeptTreeDO> list){
        for(DeptTreeDO deptTreeDO : list){
            List<DeptTreeDO> listTemp = deptDao.getbypidfrontnav(deptTreeDO.getId());
            if(listTemp.size() > 0){
                deptTreeDO.setChildren(listTemp);
                diguiTreeCheckgy(listTemp);
            }
        }
    }

    @Override
    public List<DeptOneSel> getDeptOneSel(DeptOneSel deptOneSel) {
        //先获取数根节点
        List<DeptOneSel> list = deptDao.getDeptOneSelRoot(deptOneSel.getId());
        //递归获取子节点
        getDeptOneSelChild(list);
        return list;
    }

    public void getDeptOneSelChild(List<DeptOneSel> list){
        for(DeptOneSel deptOneSelDo : list){
            List<DeptOneSel> listTemp = deptDao.getDeptOneSelChild(deptOneSelDo.getId());
            if(listTemp.size() > 0){
                deptOneSelDo.setChildren(listTemp);
                getDeptOneSelChild(listTemp);
            }
        }
    }

}


