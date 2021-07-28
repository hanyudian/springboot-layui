package com.gwdtz.springboot.utils;

import com.gwdtz.springboot.entity.LogDO;
import com.gwdtz.springboot.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;

/**
 * @ClassName LogUtils
 * @Description
 * @Author hanshuai(the Developing of Four)
 * @Date 2021/3/22 17:08
 * @Version 1.0
 **/
@Component
public class LogUtils {

    @Autowired
    ILogService logService1;

    private static ILogService logService;

    @PostConstruct
    public void init(){
        logService = logService1;
    }

    /**
    * @Author hanshuai(the developing of Four)
    * @Description
     * @param operation 用户操作
     * @param method 请求的方法
     * @param userid 用户id
     * @param username 用户名
     * @param param 请求的参数
    * @return void
    * @Date 2021/3/31 17:30
    **/
   public static void insert(String operation, String method,Integer userid,String username, String param) throws UnknownHostException {
    //public static void insert(String operation, String method, String param) throws UnknownHostException {
        LogDO logDO = new LogDO();
        logDO.setOperation(operation);
        logDO.setMethod(method);
        logDO.setParams(param);
        logDO.setUserid(userid);
        logDO.setUsername(username);
        logService.insert(logDO);
    }
}
