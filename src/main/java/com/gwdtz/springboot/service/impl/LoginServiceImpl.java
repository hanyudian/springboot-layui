package com.gwdtz.springboot.service.impl;

import com.gwdtz.springboot.dao.UserDao;
import com.gwdtz.springboot.entity.UserDO;
import com.gwdtz.springboot.entity.UserLoginDo;
import com.gwdtz.springboot.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: springboot
 * @Date: 2021-1-11 14:35
 * @Author: Mr.Wu
 * @Description:
 */
@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private UserDao userDao;

    @Override
    public UserLoginDo findByName(String username) {
        return userDao.findByName(username);
    }

    @Override
    public Integer updatePsw(UserDO userDO) {
        return userDao.updatePsw(userDO);
    }
}
