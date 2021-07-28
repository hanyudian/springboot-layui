package com.gwdtz.springboot.utils;

import com.gwdtz.springboot.entity.DeptDO;
import com.gwdtz.springboot.entity.UserLoginDo;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName UserUtils
 * @Description
 * @Author hanshuai(the Developing of Four)
 * @Date 2021/3/22 17:32
 * @Version 1.0
 **/
@Component
public class UserUtils {

    /**
    * @Author hanshuai(the developing of Four)
    * @Description 获取用户格式为DeptDo
     * @param username
    * @return com.gwdtz.springboot.entity.DeptDO
    * @Date 2021/4/7 9:56
    **/
    public static DeptDO deptGet(String username){
        DeptDO deptDO = new DeptDO();
        try {
            deptDO = (DeptDO)RedisUtil.hmget(username,DeptDO.class);
            return deptDO;
        } catch (Exception e) {
            System.err.println("用户没有从redis缓存中获取到！");
            return deptDO;
        }
    }

    /**
    * @Author hanshuai(the developing of Four)
    * @Description 获取用户格式为DeptDo
     * @param request
    * @return com.gwdtz.springboot.entity.DeptDO
    * @Date 2021/3/23 10:22
    **/
    public static DeptDO deptGet(HttpServletRequest request){
        DeptDO deptDO = new DeptDO();
        try {
            deptDO = (DeptDO)RedisUtil.hmget(request.getParameter("username"),DeptDO.class);
            return deptDO;
        } catch (Exception e) {
            System.err.println("用户没有从redis缓存中获取到！");
            return deptDO;
        }
    }
    public static UserLoginDo userGet(String username){
        UserLoginDo userLoginDo = new UserLoginDo();
        try {
            userLoginDo = (UserLoginDo)RedisUtil.hmget(username,UserLoginDo.class);
            return userLoginDo;
        } catch (Exception e) {
            System.err.println("用户没有从redis缓存中获取到！");
            return userLoginDo;
        }
    }


    /**
    * @Author hanshuai(the developing of Four)
    * @Description 获取用户格式为UserLoginDo
     * @param request username
    * @return com.gwdtz.springboot.entity.UserLoginDo
    * @Date 2021/3/23 10:22
    **/
    public static UserLoginDo userGet(HttpServletRequest request){
        UserLoginDo userLoginDo = new UserLoginDo();
        try {
            String aa=request.getParameter("username");
            userLoginDo = (UserLoginDo)RedisUtil.hmget(request.getParameter("username"),UserLoginDo.class);
            return userLoginDo;
        } catch (Exception e) {
            System.err.println("用户没有从redis缓存中获取到！");
            return userLoginDo;
        }
    }

    /**
     * @Author hanshuai(the developing of Four)
     * @Description 获取用户格式为UserLoginDo
     * @param request usernamecookie
     * @return com.gwdtz.springboot.entity.UserLoginDo
     * @Date 2021/3/23 10:22
     **/
    public static UserLoginDo userCookieGet(HttpServletRequest request){
        UserLoginDo userLoginDo = new UserLoginDo();
        try {
            userLoginDo = (UserLoginDo)RedisUtil.hmget(request.getParameter("usernamecookie"),UserLoginDo.class);
            return userLoginDo;
        } catch (Exception e) {
            System.err.println("用户没有从redis缓存中获取到！");
            return userLoginDo;
        }
    }
}
