package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.ContentDeptDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-6-16 15:00
 * @Author: Mr.Yanjc
 * @Description:
 */

@Repository
@Mapper
public interface ContentDeptDao {
    /**
     * 插入记录
     *
     * @param
     * @return
     */
    Integer insert(@Param("contentid") Long contentid, @Param("deptid") String deptid);
    /**
     * 删除记录
     *
     * @param contentid
     * @return
     */
    Integer deleteById(@Param("contentid") Long contentid);
    Integer deleteByDeptId(@Param("deptid") String deptid);
    Integer deleteByContentid(@Param("contentid") Long contentid);
    List<ContentDeptDO> getContentDeptList(@Param("contentid") String contentid);
    Integer[] selectDeptById(@Param("contentid") Long contentid);
    Integer[] selectStatusById(@Param("contentid") Long contentid);
    Integer update(ContentDeptDO contentDeptDO);
    Integer update5(ContentDeptDO contentDeptDO);
    Integer update2(ContentDeptDO contentDeptDO);
    Integer updateRollback(ContentDeptDO contentDeptDO);
    Integer updatestatus5(Long id);
    ContentDeptDO selectCompleteByContentid(@Param("contentid") Long contentid,Long deptid);

    ContentDeptDO selectContentDeptById(long id);
}
