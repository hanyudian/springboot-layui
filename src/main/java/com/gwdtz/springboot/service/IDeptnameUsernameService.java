package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.DeptnameUserNameDo;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-4-30 15:55
 * @Author: Miss.Yanjc
 * @Description:
 */
public interface IDeptnameUsernameService {
    List<DeptnameUserNameDo> selectDeptnameUsernameList();
}
