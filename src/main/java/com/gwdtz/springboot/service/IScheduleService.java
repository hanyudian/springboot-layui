package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.ScheduleDo;

import java.util.List;

/**
* @Author hanshuai(the developing of Four)
* @Description
 * @param
* @return
* @Date 2021/7/1 11:11
**/
public interface IScheduleService {

    List<ScheduleDo> getScheduleList(Integer userid, String persontype, String strkey);

    void insertSelective(ScheduleDo scheduleDo);

    ScheduleDo selectScheduleById(Long id);

    void deleteScheduleById(long id);

    List<ScheduleDo> getAll();

    void updateByPrimaryKeySelective(ScheduleDo scheduleDo);

    List<ScheduleDo> selectScheduleByUserId(Integer userid);

    List<ScheduleDo> getScheduleListByPersonType(Integer userid, Integer persontype);
}
