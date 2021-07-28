package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.ScheduleDetailDo;

import java.util.List;

/**
* @Author hanshuai(the developing of Four)
* @Description
 * @param
* @return
* @Date 2021/7/1 11:11
**/
public interface IScheduleDetailService {

    void insertSelective(ScheduleDetailDo scheduleDetailDo);

    void deleteScheduleDetailById(long id);

    ScheduleDetailDo getScheduleDetailByScheduleId(Long id);

    void deleteScheduleDetailByScheduleId(long id);

    List<ScheduleDetailDo> getUnFinishSchedule(Long id, String date);

    void updateScheduleFinishFlag(long id);

    ScheduleDetailDo getScheduleDetailById(long id);

    int getUnFinishScheduleCount(Long id, String date);

    Integer getUnFinishScheduleCountByScheduleIds(String[] split, String dateByString);
}
