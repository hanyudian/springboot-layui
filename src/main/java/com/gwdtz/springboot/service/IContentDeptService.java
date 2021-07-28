package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.ContentDeptDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-6-16 15:55
 * @Author: Miss.Yanjc
 * @Description:
 */
public interface IContentDeptService {
    Integer insert(Long contentid, String deptid);
    /**
     * 删除记录
     *
     * @param contentid
     * @return
     */
    Integer deleteById(Long contentid, String deptids);
    Integer deleteByDeptId(String deptid);
    Integer deleteByContentid(Long contentid);
    List<ContentDeptDO> getContentDeptList(String contentid);
    Integer[] selectDeptById(@Param("contentid") Long contentid);
    Integer[] selectStatusById(@Param("contentid") Long contentid);
    Integer deleteByDeptIds(String[] deptids);
    Integer update(ContentDeptDO contentDeptDO);
    Integer update5(ContentDeptDO contentDeptDO);
    Integer update2(ContentDeptDO contentDeptDO);
    Integer updateRollback(ContentDeptDO contentDeptDO);
    Integer updatestatus5(Long id);
    ContentDeptDO selectCompleteByContentid(@Param("contentid") Long contentid,Long deptid);

    ContentDeptDO selectContentDeptById(long id);
}
