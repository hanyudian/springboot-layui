package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.ScheduleDetailDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
@Mapper
public interface ScheduleDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(ScheduleDetailDo record);

    int insertSelective(ScheduleDetailDo record);

    ScheduleDetailDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScheduleDetailDo record);

    int updateByPrimaryKey(ScheduleDetailDo record);

    void deleteScheduleDetailById(Long id);

    ScheduleDetailDo getScheduleDetailByScheduleId(Long id);

    void deleteScheduleDetailByScheduleId(@Param("scheduleId") long id);

    List<ScheduleDetailDo> getUnFinishSchedule(@Param("id") Long id, @Param("date") String date);

    void updateScheduleFinishFlag(long id);

    ScheduleDetailDo getScheduleDetailById(long id);

    int getUnFinishScheduleCount(@Param("id") Long id, @Param("date") String date);

    Integer getUnFinishScheduleCountByScheduleIds(@Param("scheduleIds")String[] scheduleIds,@Param("date") String date);
}