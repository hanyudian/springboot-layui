package com.gwdtz.springboot.service;


import com.gwdtz.springboot.entity.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-1-12 14:48
 * @Author: Miss.Chenmf
 * @Description:
 */
public interface IUserService {
    /**
     * 通过用户名称获取用户列表
     *
     * @param username
     * @return
     */
    UserDO getUserName(String username);
    /**
     * 通过部门编号获取用户列表
     *
     * @param
     * @return
     */
//    List<UserDO> getUserList();


    /**
     *
     * @param username
     * @param deptlevelcode
     * @return
     */
    List<UserDO> getUserList(String username,String deptlevelcode, String name, String deptname, String rolename);
    List<UserDO> getUserList1(String deptid);
    /**
     * 插入记录
     *
     * @param userDO 用户对象
     * @return
     */
    Integer insert(UserDO userDO);
    /**
     * 更新记录
     *
     * @param userDO 用户对象
     * @return
     */
    Integer update(UserDO userDO);
    /**
     * 删除记录
     *
     * @param userid
     * @return
     */
    Integer deleteByUserid(@Param("userUserid") Long userid);

    UserDO selectUserByUserid(@Param("userUserid") Long userid);


    /**
     * 重置密码
     *
     * @param userDO 用户对象
     * @return
     */
    Integer passwordReset(UserDO userDO);
}
