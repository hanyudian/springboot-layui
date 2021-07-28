package com.gwdtz.springboot.dao;

import com.gwdtz.springboot.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ContentDao {

    List<ContentDO> getContentList(@Param("deptid") Integer deptid,@Param("title") String title,@Param("serial") String serial, @Param("createtime") String createtime);


    List<ContentDO> getContentList1(@Param("modulepid") String modulepid, @Param("top") Integer top, @Param("strkey") String strkey);
    List<ContentDO> getContentList2(@Param("deptid") String deptid, @Param("modulepid") String modulepid, @Param("top") Integer top, @Param("strkey") String strkey);
    List<ContentDO> getContentList3(@Param("deptid") String deptid, @Param("modulepid") String modulepid, @Param("top") Integer top, @Param("strkey") String strkey, @Param("status") String status);
    /**
     * 插入记录
     *
     * @param contentDO 样式对象
     * @return
     */
    Integer insert(ContentDO contentDO);
    Integer insert1(ContentDO contentDO);
    /**
     * 更新记录
     *
     * @param contentDO 样式对象
     * @return
     */
    Integer update(ContentDO contentDO);
    Integer update1(ContentDO contentDO);
//    Integer updateUndo(Long contentid);
    /**
     * 删除记录
     *
     * @param id
     * @return
     */
    Integer deleteById(@Param("contentId") Long id);
    Integer deleteByModuleid(@Param("moduleid") Long moduleid);

    ContentDO selectContentById(@Param("contentId") Long id);
    ContentDO selectContentByModuleid(Long moduleid, Long deptid);
    Long getMaxId();

    Integer addBatchContentAttach(ContentAttachDO contentAttachDO);

    List<ContentAttach> getContentAttachList(@Param("contentid") String contentid);

    Integer deleteByAttachId(@Param("attachid") String attachid);
    Integer deleteAttachByContentId(@Param("contentid") String contentid);

    List<ContentDO> getContentListByTitle(@Param("deptid") String deptid, @Param("title") String searchContent, @Param("top") Integer top, @Param("modulepid") String modulepid);

    ModuleDO getModuleInfoByContentId(@Param("id") long id);

    Integer updatevisittimes(@Param("contentId") Long id);

    void updateUndo1(@Param("contentid")Long contentid,@Param("undo") Integer undo);
}
