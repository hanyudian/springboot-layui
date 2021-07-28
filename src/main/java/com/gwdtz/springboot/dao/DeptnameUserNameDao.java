package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.DeptnameUserNameDo;

import java.util.List;

public interface DeptnameUserNameDao {
    int deleteByPrimaryKey(Integer id);

    int insert(DeptnameUserNameDo record);

    int insertSelective(DeptnameUserNameDo record);

    DeptnameUserNameDo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeptnameUserNameDo record);

    int updateByPrimaryKey(DeptnameUserNameDo record);

    List<DeptnameUserNameDo> selectDeptnameUsernameList();
}