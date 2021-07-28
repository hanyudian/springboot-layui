package com.gwdtz.springboot.dao;


import com.gwdtz.springboot.entity.ModuleDO;
import com.gwdtz.springboot.entity.ModuleIconDo;
import org.springframework.stereotype.Repository;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Repository
@Mapper
public interface ModuleIconDao {

    List<ModuleIconDo> selectModuleIconList();
}
