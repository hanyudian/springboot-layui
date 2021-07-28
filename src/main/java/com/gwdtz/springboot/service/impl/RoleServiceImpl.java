package com.gwdtz.springboot.service.impl;

import com.gwdtz.springboot.dao.RoleDao;
import com.gwdtz.springboot.entity.RoleDO;
import com.gwdtz.springboot.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-2-23 16:10
 * @Author: Mr.Yanjc
 * @Description:
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public List<RoleDO> selectList() {
        return roleDao.selectList();
    }

    @Override
    public RoleDO getRoleName(String name) {
        return roleDao.getRoleName(name);
    }

    @Override
    public List<RoleDO> getRoleList(String name, String description) {
        return roleDao.getRoleList(name,description);
    }

    @Override
    public Integer insert(RoleDO roleDO) {
        return roleDao.insert(roleDO);
    }

    @Override
    public Integer update(RoleDO roleDO) {
        return roleDao.update(roleDO);
    }

    @Override
    public Integer deleteById(Long id) {
        return roleDao.deleteById(id);
    }

    @Override
    public RoleDO selectRoleById(Long id) {
        return roleDao.selectRoleById(id);
    }
}
