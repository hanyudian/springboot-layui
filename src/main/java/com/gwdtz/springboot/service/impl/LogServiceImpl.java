package com.gwdtz.springboot.service.impl;

import com.gwdtz.springboot.dao.LogDao;
import com.gwdtz.springboot.entity.LogDO;
import com.gwdtz.springboot.service.ILogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.net.InetAddress;
import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-2-23 16:10
 * @Author: Mr.Yanjc
 * @Description:
 */
@Service
public class LogServiceImpl implements ILogService {
    @Autowired
    LogDao logDao;

    @Override
    public LogDO getLogName(String username) {
        return logDao.getLogName(username);
    }

    @Override
    public List<LogDO> getLogList(String name, String ip, String operation, String time) {
        return logDao.getLogList(name, ip, operation, time);
    }

    @Override
    public Integer insert(LogDO logDo) throws UnknownHostException {
        logDo.setIp(InetAddress.getLocalHost().getHostAddress().toString());
        return logDao.insert(logDo);
    }


}
