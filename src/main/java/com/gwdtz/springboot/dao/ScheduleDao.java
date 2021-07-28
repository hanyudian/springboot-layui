package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.ScheduleDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ScheduleDao {
    int deleteByPrimaryKey(Long id);

    int insert(ScheduleDo record);

    int insertSelective(ScheduleDo record);

    ScheduleDo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScheduleDo record);

    int updateByPrimaryKey(ScheduleDo record);

    List<ScheduleDo> getScheduleList(@Param("userid") Integer userid, @Param("persontype") String persontype, @Param("strkey") String strkey);

    ScheduleDo selectScheduleById(Long id);

    void deleteScheduleById(long id);

    List<ScheduleDo> getAll();

    List<ScheduleDo> selectScheduleByUserId(Integer userid);

    List<ScheduleDo> getScheduleListByPersonType(@Param("userid") Integer userid,@Param("persontype") Integer persontype);
}