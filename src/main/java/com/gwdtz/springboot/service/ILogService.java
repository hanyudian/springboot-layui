package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.LogDO;

import java.net.UnknownHostException;
import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-2-23 16:10
 * @Author: Mr.Yanjc
 * @Description:
 */
public interface ILogService {
    /**
     * 通过用户名称获取日志列表
     *
     * @param username
     * @return
     */
    LogDO getLogName(String username);
    /**
     * 通过用户编号获取日志列表
     *
     * @param
     * @param name
     * @param ip
     * @param operation
     * @param time
     * @return
     */
    List<LogDO> getLogList(String name, String ip, String operation, String time);
    /**
     * 插入记录
     *
     * @param logDO 日志对象
     * @return
     */
    Integer insert(LogDO logDO) throws UnknownHostException;
}
