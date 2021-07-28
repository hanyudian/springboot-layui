package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.ScheduleAttachDo;

import java.util.List;

/**
* @Author hanshuai(the developing of Four)
* @Description
 * @param
* @return
* @Date 2021/7/1 11:11
**/
public interface IScheduleAttachService {

    void insertSelective(ScheduleAttachDo scheduleAttachDo);

    List<ScheduleAttachDo> getScheduleAttachList(String[] attachids);

    Integer deleteByAttachId(String attachid);

    void deleteScheduleAttachByIds(String[] split);
}
