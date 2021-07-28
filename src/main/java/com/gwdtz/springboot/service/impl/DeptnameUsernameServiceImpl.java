package com.gwdtz.springboot.service.impl;


import com.gwdtz.springboot.dao.DeptnameUserNameDao;
import com.gwdtz.springboot.entity.DeptnameUserNameDo;
import com.gwdtz.springboot.service.IDeptnameUsernameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DeptnameUsernameServiceImpl implements IDeptnameUsernameService {

    @Autowired
    DeptnameUserNameDao deptnameUserNameDao;

    @Override
    public List<DeptnameUserNameDo> selectDeptnameUsernameList() {
        return deptnameUserNameDao.selectDeptnameUsernameList();
    }
}
