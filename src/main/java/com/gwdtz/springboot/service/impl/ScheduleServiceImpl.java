package com.gwdtz.springboot.service.impl;


import com.gwdtz.springboot.dao.ScheduleDao;
import com.gwdtz.springboot.entity.ScheduleDo;
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
public class ScheduleServiceImpl implements IScheduleService {

    @Autowired
    ScheduleDao scheduleDao;

    @Override
    public List<ScheduleDo> getScheduleList(Integer userid, String persontype, String strkey) {
        return scheduleDao.getScheduleList(userid, persontype, strkey);
    }

    @Override
    public void insertSelective(ScheduleDo scheduleDo) {
        scheduleDao.insertSelective(scheduleDo);
    }

    @Override
    public ScheduleDo selectScheduleById(Long id) {
        return scheduleDao.selectScheduleById(id);
    }

    @Override
    public void deleteScheduleById(long id) {
        scheduleDao.deleteScheduleById(id);
    }

    @Override
    public List<ScheduleDo> getAll() {
        return scheduleDao.getAll();
    }

    @Override
    public void updateByPrimaryKeySelective(ScheduleDo scheduleDo) {
        scheduleDao.updateByPrimaryKeySelective(scheduleDo);
    }

    @Override
    public List<ScheduleDo> selectScheduleByUserId(Integer userid) {
        return scheduleDao.selectScheduleByUserId(userid);
    }

    @Override
    public List<ScheduleDo> getScheduleListByPersonType(Integer userid, Integer persontype) {
        return scheduleDao.getScheduleListByPersonType(userid, persontype);
    }
}
