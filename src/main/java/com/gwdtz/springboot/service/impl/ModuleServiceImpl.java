package com.gwdtz.springboot.service.impl;

import com.gwdtz.springboot.dao.DeptnameUserNameDao;
import com.gwdtz.springboot.dao.ModuleDao;
import com.gwdtz.springboot.dao.UserModuleDao;
import com.gwdtz.springboot.entity.DeptnameUserNameDo;
import com.gwdtz.springboot.entity.ModuleDO;
import com.gwdtz.springboot.entity.ModuleTree;
import com.gwdtz.springboot.entity.UserModuleDO;
import com.gwdtz.springboot.entity.layui.UserModule;
import com.gwdtz.springboot.service.IModuleService;
import com.gwdtz.springboot.utils.ListUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-2-23 15:10
 * @Author: Miss.Yanjc
 * @Description:
 */
@Service
public class ModuleServiceImpl implements IModuleService {
    @Autowired
    ModuleDao moduleDao;

    @Autowired
    DeptnameUserNameDao deptnameUserNameDao;

    @Autowired
    UserModuleDao userModuleDao;

    @Override
    public List<ModuleDO> getModuleList(String deptid) {
        return moduleDao.getModuleList(deptid);
    }

    @Override
    public List<ModuleDO> selectModuleByPid(Long pid) {
        return moduleDao.selectByPid(pid);
    }

    @Override
    public List<ModuleTree> getAllListCheckJsonQxs(ModuleTree moduleTree, String Qxs) {
        List<ModuleTree> list = moduleDao.getByPidTreeCheckQxs(String.valueOf(moduleTree.getId()), Qxs.split(","));
        diguiTreeCheckQxs(list, Qxs);
        return list;
    }

    public void diguiTreeCheckQxs(List<ModuleTree> list, String Qxs) {
        for (ModuleTree m : list) {
            List<ModuleTree> listTemp = moduleDao.getByPidTreeCheckQxs(String.valueOf(m.getId()), Qxs.split(","));
            if (listTemp.size() > 0) {
                m.setChild(listTemp);
                diguiTreeCheckQxs(listTemp, Qxs);
            }
        }
    }

    @Override
    public List<ModuleTree> getAllListCheckJson(ModuleTree moduleTree) {
        List<ModuleTree> list = moduleDao.getByPidTreeCheck(String.valueOf(moduleTree.getId()));
        diguiTreeCheck(list);
        return list;
    }

    public void diguiTreeCheck(List<ModuleTree> list) {
        for (ModuleTree m : list) {
            List<ModuleTree> listTemp = moduleDao.getByPidTreeCheck(String.valueOf(m.getId()));
            if (listTemp.size() > 0) {
                m.setChild(listTemp);
                diguiTreeCheck(listTemp);
            }
        }
    }

    @Override
    public Integer insertSelective(ModuleDO moduleDO) {
        return moduleDao.insertSelective(moduleDO);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return moduleDao.deleteByPrimaryKey(id);
    }

    @Override
    public String getAllParentAndChildIds(String parentId, StringBuilder sb) {
        String ids = parentId + ",";
        sb.append(ids);
        List<String> allNodesIds = moduleDao.getAllNodesIds(parentId);
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
        return moduleDao.deleteParentNodesByAllIds(allParentAndChildIds);
    }

    @Override
    public ModuleDO selectModuleByPrimaryKey(String id) {
        return moduleDao.selectModuleByPrimaryKey(id);
    }

    @Override
    public Integer updateByPrimaryKeySelective(ModuleDO moduleDO) {
        return moduleDao.updateByPrimaryKeySelective(moduleDO);
    }

    @Override
    public Integer updateNameByPrimaryKeySelective(ModuleDO moduleDO) {
        return moduleDao.updateNameByPrimaryKeySelective(moduleDO);
    }

    @Override
    public String getMaxSortno(long pid) {
        return moduleDao.getMaxSortno(pid);
    }

    @Override
    public Integer getIschild(long id) {
        return moduleDao.getIschild(id);
    }

    @Override
    public Integer updateIschild(ModuleDO moduleDO) {
        return moduleDao.updateIschild(moduleDO);
    }

    @Override
    public ModuleDO findPid(long deptid) {
        return moduleDao.findPid(deptid);
    }


    @Override
    public Integer getMaxId() {
        return moduleDao.getMaxId();
    }

    @Override
    public void deleteModuleByDeptId(long deptid) {
        moduleDao.deleteModuleByDeptId(deptid);
    }

    @Override
    public Integer batchUrlUpdate(ModuleDO moduleDO) {
        return moduleDao.batchUrlUpdate(moduleDO);
    }

    @Override
    public List<ModuleDO> getModuleListByCode(String s) {
        return moduleDao.getModuleListByCode(s);
    }



    @Override
    public List<ModuleTree> getAllListCheckJsonQxs(String deptlevelcode, ModuleTree moduleTree, String Qxs) {
        List<ModuleTree> list = moduleDao.getByPidTreeCheckQxs(String.valueOf(moduleTree.getId()), Qxs.split(","));
        ModuleTree moduleTree1 = new ModuleTree();
        int id = 10000;
        moduleTree1.setId(id);
        moduleTree1.setTitle("车间");
        moduleTree1.setPid(moduleTree.getId());
        List<DeptnameUserNameDo> deptnameUserNameDos = deptnameUserNameDao.selectDeptnameUsernameList();
        int[] includeQxs = {3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 27, 28, 33};
        List<ModuleTree> list1 = new ArrayList<>();
        for (DeptnameUserNameDo deptnameUserNameDo :
                deptnameUserNameDos) {
            ModuleTree moduleTree2 = new ModuleTree();
            moduleTree2.setPid(15);
            moduleTree2.setId(++id);
            moduleTree2.setTitle(deptnameUserNameDo.getTitle());
            moduleTree2.setIcon("fa fa-train");

            //权限取交集
            List<UserModuleDO> listqxs = userModuleDao.getUserModuleList(String.valueOf(deptnameUserNameDo.getUserid()));
            if (listqxs.size() == 0) {
                continue;
            }
            String strQxs = "";
            for (int i = 0; i < listqxs.size(); i++) {
                strQxs += listqxs.get(i).getModuleid() + ",";
            }
            strQxs = strQxs.substring(0, strQxs.length() - 1);
            int[] realitQxs = Arrays.stream(strQxs.split(",")).mapToInt(Integer::parseInt).toArray();
            int[] finalQxs = ListUtils.intersection(includeQxs, realitQxs);
            //重置
            strQxs = "";
            for (int i = 0; i < finalQxs.length; i++) {
                strQxs += finalQxs[i] + ",";
            }
            strQxs = strQxs.substring(0, strQxs.length() - 1);

            List<ModuleTree> list2 = moduleDao.getByPidTreeCheckQxs("2", strQxs.split(","));
            for (ModuleTree moduleTree3 : list2) {
                if (moduleTree3.getHref() != null) {
                    moduleTree3.setHref(moduleTree3.getHref() + "&flagname=" + deptnameUserNameDo.getUsername());
                }
            }
            diguiTreeCheckQxsHref(deptnameUserNameDo.getUsername(), list2, strQxs);
            moduleTree2.setChild(list2);
            list1.add(moduleTree2);
        }
        moduleTree1.setChild(list1);
        list.add(0, moduleTree1);
        diguiTreeCheckQxs(list, Qxs);
        return list;
    }

    public void diguiTreeCheckQxsHref(String username, List<ModuleTree> list, String Qxs) {
        for (ModuleTree m : list) {
            List<ModuleTree> listTemp = moduleDao.getByPidTreeCheckQxs(String.valueOf(m.getId()), Qxs.split(","));
            for (ModuleTree moduleTree : listTemp) {
                if (moduleTree.getHref() != null) {
                    moduleTree.setHref(moduleTree.getHref() + "&flagname=" + username);
                }
            }
            if (listTemp.size() > 0) {
                m.setChild(listTemp);
                diguiTreeCheckQxsHref(username, listTemp, Qxs);
            }
        }
    }


    //============================================================================================
    @Override
    public List<UserModule> getUserModuleQxs(UserModule userModule, String Qxs) {
        List<UserModule> list = moduleDao.getUserModuleRootQxs(String.valueOf(userModule.getId()), Qxs.split(","));
        getUserModuleChildQxs(list, Qxs);
        return list;
    }

    public void getUserModuleChildQxs(List<UserModule> list, String Qxs) {
        for (UserModule userModule : list) {
            List<UserModule> listTemp = moduleDao.getUserModuleRootQxs(String.valueOf(userModule.getId()), Qxs.split(","));
            if (listTemp.size() > 0) {
                userModule.setChildren(listTemp);
                getUserModuleChildQxs(listTemp, Qxs);
            }
        }
    }
    @Override
    public List<ModuleDO> getModuleLists(String[] split) {
        return moduleDao.getModuleLists(split);
    }

}