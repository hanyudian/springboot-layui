package com.gwdtz.springboot.service;


import com.gwdtz.springboot.entity.ModuleIconDo;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-4-30 15:55
 * @Author: Miss.Yanjc
 * @Description:
 */
public interface IModuleIconService {
    List<ModuleIconDo> selectModuleIconList();
}
