package com.gwdtz.springboot.service.impl;


import com.gwdtz.springboot.dao.ModuleManageUrlDao;
import com.gwdtz.springboot.entity.ModuleManageUrlDO;
import com.gwdtz.springboot.service.IModuleManageUrlService;
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
public class ModuleManageUrlServiceImpl implements IModuleManageUrlService {
    @Autowired
    ModuleManageUrlDao moduleManageUrlDao;
    @Override
    public List<ModuleManageUrlDO> selectModuleManageUrlList() {
        return moduleManageUrlDao.selectModuleManageUrlList();
    }
}
