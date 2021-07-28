package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.UserModuleDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-3-5 15:00
 * @Author: Mr.Yanjc
 * @Description:
 */

@Repository
@Mapper
public interface UserModuleDao {
    /**
     * 插入记录
     *
     * @param
     * @return
     */
    Integer insert(@Param("userid") Long userid, @Param("moduleid") String moduleid);
    /**
     * 删除记录
     *
     * @param userid
     * @return
     */
    Integer deleteById(@Param("userid") Long userid);
    Integer deleteByModuleId(@Param("moduleid") String moduleid);
    Integer deleteByUserid(@Param("userid") Long userid);
    List<UserModuleDO> getUserModuleList(@Param("userid") String userid);
    Integer[] selectModuleById(@Param("userid") Long userid);
}
