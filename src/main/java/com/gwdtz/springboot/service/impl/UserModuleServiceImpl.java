package com.gwdtz.springboot.service.impl;

import com.gwdtz.springboot.dao.UserModuleDao;
import com.gwdtz.springboot.entity.UserModuleDO;
import com.gwdtz.springboot.service.IUserModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-3-3 15:56
 * @Author: Miss.Yanjc
 * @Description:
 */
@Service
public class UserModuleServiceImpl implements IUserModuleService {
    @Autowired
    UserModuleDao userModuleDao;
    @Override
    public Integer deleteById(Long userid, String moduleids) {
        Integer num = userModuleDao.deleteById(userid);
        for (String moduleid :
                moduleids.split(",")) {
            num += userModuleDao.insert(userid, moduleid);
        }
        return num;
    }

    @Override
    public Integer deleteByModuleId(String moduleid) {
        return userModuleDao.deleteByModuleId(moduleid);
    }

    @Override
    public Integer insert(Long userid, String moduleid) {
        return userModuleDao.insert(userid, moduleid);
    }

    @Override
    public Integer deleteByUserid(Long userid) {
        return userModuleDao.deleteByUserid(userid);
    }

    @Override
    public List<UserModuleDO> getUserModuleList(String userid) {
        return userModuleDao.getUserModuleList(userid);
    }

    @Override
    public Integer[] selectModuleById(Long userid) {
        Integer[] num = userModuleDao.selectModuleById(userid);
        return num;
    }

    @Override
    public Integer deleteByModuleIds(String[] moduleids) {
        Integer num = 0;
        for (String moduleid :
                moduleids) {
            num += userModuleDao.deleteByModuleId(moduleid);
        }
        return num;
    }
}