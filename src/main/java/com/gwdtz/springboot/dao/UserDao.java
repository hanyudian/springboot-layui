package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.UserDO;
import com.gwdtz.springboot.entity.UserLoginDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-2-23 14:46
 * @Author: Mr.Yanjc
 * @Description:
 */
@Repository
@Mapper

public interface UserDao {
    UserLoginDo findByName(@Param("username") String username);
    /**
     * 通过用户名称获取用户列表
     *
     * @param username
     * @return
     */
    UserDO getUserName(@Param("username") String username);
    /**
     * 通过部门编号获取用户列表
     *
     * @param
     * @return
     */
    List<UserDO> getUserList(@Param("username") String username,@Param("deptlevelcode") String deptlevelcode, @Param("name") String name,@Param("deptname") String deptname, @Param("rolename") String rolename);
    List<UserDO> getUserList1(@Param("deptid")String deptid);
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

    Integer updatePsw(UserDO  userDO);

    /**
     * 密码重置
     *
     * @param userDO 用户对象
     * @return
     */
    Integer passwordReset(UserDO userDO);
}
