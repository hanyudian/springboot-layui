package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.LogDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 * @program: springboot
 * @Date: 2021-2-23 10:50
 * @Author: Miss.Yanjc
 * @Description:
 */
@Repository
@Mapper
public interface LogDao {
    /**
     * 通过用户名称获取日志列表
     *
     * @param username
     * @return
     */
    LogDO getLogName(@Param("username") String username);
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
    List<LogDO> getLogList(@Param("name") String name, @Param("ip") String ip,@Param("operation") String operation, @Param("time") String time);
    /**
     * 插入记录
     *
     * @param logDO 日志对象
     * @return
     */
    Integer insert(LogDO logDO);
}
