package com.gwdtz.springboot.service.impl;


import com.gwdtz.springboot.dao.ModuleIconDao;
import com.gwdtz.springboot.entity.ModuleIconDo;
import com.gwdtz.springboot.service.IModuleIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-4-30 15:56
 * @Author: Miss.Yanjc
 * @Description:
 */
@Service
public class ModuleIconServiceImpl implements IModuleIconService {

    @Autowired
    ModuleIconDao moduleIconDao;

    @Override
    public List<ModuleIconDo> selectModuleIconList() {
        return moduleIconDao.selectModuleIconList();
    }
}
