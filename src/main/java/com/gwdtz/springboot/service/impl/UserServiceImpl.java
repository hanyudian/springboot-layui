package com.gwdtz.springboot.service.impl;

import com.gwdtz.springboot.dao.UserDao;
import com.gwdtz.springboot.entity.UserDO;
import com.gwdtz.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-1-12 14:48
 * @Author: Miss.Chenmf
 * @Description:
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UserDao userDao;
    @Override
    public UserDO getUserName(String username) {
        return userDao.getUserName(username);
    }

    @Override
    public List<UserDO> getUserList(String username, String deptlevelcode, String name, String deptname, String rolename) {
        return userDao.getUserList(username,deptlevelcode,name,deptname,rolename);
    }
    @Override
    public List<UserDO> getUserList1(String deptid) {
        return userDao.getUserList1(deptid);
    }

    @Override
    public Integer insert(UserDO userDO) {
        return userDao.insert(userDO);
    }

    @Override
    public Integer update(UserDO userDO) {
        return userDao.update(userDO);
    }

    @Override
    public Integer deleteByUserid(Long userid) {
        return userDao.deleteByUserid(userid);
    }

    @Override
    public UserDO selectUserByUserid(Long userid) {
        return userDao.selectUserByUserid(userid);
    }

    @Override
    public Integer passwordReset(UserDO userDO) {
        return userDao.passwordReset(userDO);
    }
}
