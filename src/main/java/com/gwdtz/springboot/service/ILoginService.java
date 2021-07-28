package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.UserDO;
import com.gwdtz.springboot.entity.UserLoginDo;

/**
 * @program: springboot
 * @Date: 2021-1-11 14:35
 * @Author: Mr.Wu
 * @Description:
 */
public interface ILoginService {
    UserLoginDo findByName(String username);
    Integer updatePsw(UserDO  userDO);
}
