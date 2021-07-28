package com.gwdtz.springboot.dao;


import com.gwdtz.springboot.entity.ModuleManageUrlDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ModuleManageUrlDao {
    List<ModuleManageUrlDO> selectModuleManageUrlList();
}
