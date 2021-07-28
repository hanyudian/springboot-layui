package com.gwdtz.springboot.service.impl;


import com.gwdtz.springboot.dao.SafetyDayDao;
import com.gwdtz.springboot.entity.SafetyDayDo;
import com.gwdtz.springboot.service.ISafetyDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SafetyDayServiceImpl implements ISafetyDayService {

    @Autowired
    SafetyDayDao safetyDayDao;

    @Override
    public SafetyDayDo existToday(Integer deptid) {
        return safetyDayDao.existToday(deptid);
    }



    @Override
    public SafetyDayDo getSafetyToday(Integer deptid) {
        return safetyDayDao.getSafetyToday(deptid);
    }

    @Override
    public List<SafetyDayDo> getAll() {
        return safetyDayDao.getAll();
    }

    @Override
    public void updateEvent(long aDay, long bDay, long cDay, long dDay, Integer deptid) {
        safetyDayDao.updateEvent(aDay,bDay,cDay,dDay,deptid);
    }

    @Override
    public SafetyDayDo existYesterday(Integer deptid) {
        return safetyDayDao.existYesterday(deptid);
    }
}
