package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.SafetyDayDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SafetyDayDao {
    int deleteByPrimaryKey(Long id);

    int insert(SafetyDayDo record);

    int insertSelective(SafetyDayDo record);

    SafetyDayDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SafetyDayDo record);

    int updateByPrimaryKey(SafetyDayDo record);

    SafetyDayDo existToday(@Param("deptid") Integer deptid);

    SafetyDayDo getSafetyToday(@Param("deptid") Integer deptid);

    List<SafetyDayDo> getAll();

    void updateEvent(@Param("aDay") long aDay,@Param("bDay") long bDay,@Param("cDay") long cDay, @Param("dDay")long dDay, @Param("deptid")Integer deptid);

    SafetyDayDo existYesterday(@Param("deptid") Integer deptid);
}