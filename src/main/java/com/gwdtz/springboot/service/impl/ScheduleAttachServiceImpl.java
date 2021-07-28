package com.gwdtz.springboot.service.impl;


import com.gwdtz.springboot.dao.ScheduleAttachDao;
import com.gwdtz.springboot.entity.ScheduleAttachDo;
import com.gwdtz.springboot.service.IScheduleAttachService;
import com.gwdtz.springboot.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @Author hanshuai(the developing of Four)
* @Description
 * @param
* @return
* @Date 2021/7/1 11:13
**/
@Service
public class ScheduleAttachServiceImpl implements IScheduleAttachService {

    @Autowired
    ScheduleAttachDao scheduleAttachDao;

    @Override
    public void insertSelective(ScheduleAttachDo scheduleAttachDo) {
        scheduleAttachDao.insertSelective(scheduleAttachDo);
    }

    @Override
    public List<ScheduleAttachDo> getScheduleAttachList(String[] attachids) {
        return scheduleAttachDao.getScheduleAttachList(attachids);
    }

    @Override
    public Integer deleteByAttachId(String attachid) {
        return scheduleAttachDao.deleteByAttachId(attachid);
    }

    @Override
    public void deleteScheduleAttachByIds(String[] attachids) {
        scheduleAttachDao.deleteScheduleAttachByIds(attachids);
    }


}
