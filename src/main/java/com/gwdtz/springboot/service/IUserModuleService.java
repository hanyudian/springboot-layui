package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.UserModuleDO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-3-5 15:55
 * @Author: Miss.Yanjc
 * @Description:
 */
public interface IUserModuleService {
    /**
     * 删除记录
     *
     * @param userid
     * @return
     */
    Integer deleteById(Long userid, String moduleids);
    //删除一条
    Integer deleteByModuleId(String moduleid);
    Integer insert(Long userid, String moduleid);
    Integer deleteByUserid(Long userid);
    List<UserModuleDO> getUserModuleList(String userid);
    Integer[] selectModuleById(@Param("userid") Long userid);

    Integer deleteByModuleIds(String[] moduleids);
}
