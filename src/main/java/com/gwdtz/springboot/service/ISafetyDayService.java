package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.SafetyDayDo;

import java.util.List;

/**
* @Author hanshuai(the developing of Four)
* @Description
 * @param
* @return
* @Date 2021/6/16 10:20
**/
public interface ISafetyDayService {

    SafetyDayDo existToday(Integer deptid);

    SafetyDayDo getSafetyToday(Integer deptid);

    List<SafetyDayDo> getAll();

    void updateEvent(long aDay, long bDay, long cDay, long dDay, Integer deptid);

    SafetyDayDo existYesterday(Integer deptid);
}
