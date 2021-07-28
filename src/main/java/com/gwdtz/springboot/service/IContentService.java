package com.gwdtz.springboot.service;

import com.gwdtz.springboot.entity.ContentAttach;
import com.gwdtz.springboot.entity.ContentAttachDO;
import com.gwdtz.springboot.entity.ContentDO;
import com.gwdtz.springboot.entity.ModuleDO;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IContentService {
    /**
     * 通过部门编号获取样式列表
     *
     * @param deptid
     * @return
     */
    List<ContentDO> getContentList(Integer deptid, String title, String serial, String createtime);
    List<ContentDO> getContentList1(String modulepid, Integer top, String strkey);
    List<ContentDO> getContentList2(String deptid, String modulepid, Integer top, String strkey);
    List<ContentDO> getContentList3(String deptid, String modulepid, Integer top, String strkey, String status);

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

    /**
     * 读取id的最大值
     *
     * @param
     * @return
     */
    Long getMaxId();

    /**
     * 批量插入附件列表
     *
     * @param
     * @return
     */
    Integer addBatchContentAttach(ContentAttachDO contentAttachDO);

    /**
     * 读取附件列表
     *
     * @param
     * @return
     */
    List<ContentAttach> getContentAttachList(@Param("contentid") String contentid);

    Integer deleteByAttachId(@Param("attachid") String attachid);

    Integer deleteAttachByContentId(@Param("contentid") String contentid);

    List<ContentDO> getContentListByTitle(String deptid, String searchContent, Integer top, String modulepid);

    ModuleDO getModuleInfoByContentId(long id);

    Integer updatevisittimes(Long id);


    void updateUndo1(Long contentid, Integer undo);
}