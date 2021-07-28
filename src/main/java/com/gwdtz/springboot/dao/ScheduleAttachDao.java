package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.ScheduleAttachDo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScheduleAttachDao {
    int deleteByPrimaryKey(Long id);

    int insert(ScheduleAttachDo record);

    int insertSelective(ScheduleAttachDo record);

    ScheduleAttachDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScheduleAttachDo record);

    int updateByPrimaryKey(ScheduleAttachDo record);

    List<ScheduleAttachDo> getScheduleAttachList(String[] ids);

    Integer deleteByAttachId(String attachid);


    void deleteScheduleAttachByIds(String[] ids);
}