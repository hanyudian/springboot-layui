package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.ModuleManageUrlDO;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-4-30 15:55
 * @Author: Miss.Yanjc
 * @Description:
 */
public interface IModuleManageUrlService {
    List<ModuleManageUrlDO> selectModuleManageUrlList();
}
