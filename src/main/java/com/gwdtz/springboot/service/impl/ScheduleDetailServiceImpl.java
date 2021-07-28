package com.gwdtz.springboot.service.impl;


import com.gwdtz.springboot.dao.ScheduleDao;
import com.gwdtz.springboot.dao.ScheduleDetailDao;
import com.gwdtz.springboot.entity.ScheduleDetailDo;
import com.gwdtz.springboot.service.IScheduleDetailService;
import com.gwdtz.springboot.service.IScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @param
 * @Author hanshuai(the developing of Four)
 * @Description
 * @return
 * @Date 2021/7/1 11:13
 **/
@Service
public class ScheduleDetailServiceImpl implements IScheduleDetailService {

    @Autowired
    ScheduleDetailDao scheduleDetailDao;

    @Override
    public void insertSelective(ScheduleDetailDo scheduleDetailDo) {
        scheduleDetailDao.insertSelective(scheduleDetailDo);
    }

    @Override
    public void deleteScheduleDetailById(long id) {
        scheduleDetailDao.deleteScheduleDetailById(id);
    }

    @Override
    public ScheduleDetailDo getScheduleDetailByScheduleId(Long id) {
        return scheduleDetailDao.getScheduleDetailByScheduleId(id);
    }

    @Override
    public void deleteScheduleDetailByScheduleId(long id) {
        scheduleDetailDao.deleteScheduleDetailByScheduleId(id);
    }

    @Override
    public List<ScheduleDetailDo> getUnFinishSchedule(Long id, String date) {
        return scheduleDetailDao.getUnFinishSchedule(id, date);
    }

    @Override
    public void updateScheduleFinishFlag(long id) {
        scheduleDetailDao.updateScheduleFinishFlag(id);
    }

    @Override
    public ScheduleDetailDo getScheduleDetailById(long id) {
        return scheduleDetailDao.getScheduleDetailById(id);
    }

    @Override
    public int getUnFinishScheduleCount(Long id, String date) {
        return scheduleDetailDao.getUnFinishScheduleCount(id, date);
    }

    @Override
    public Integer getUnFinishScheduleCountByScheduleIds(String[] scheduleIds, String date) {
        return scheduleDetailDao.getUnFinishScheduleCountByScheduleIds(scheduleIds, date);
    }


}
