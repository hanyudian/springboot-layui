package com.gwdtz.springboot.service.impl;

import com.gwdtz.springboot.dao.ContentDao;
import com.gwdtz.springboot.entity.*;
import com.gwdtz.springboot.service.IContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl implements IContentService {
    @Autowired
    ContentDao contentDao;


    @Override
    public List<ContentDO> getContentList(Integer deptid, String title, String serial, String createtime) {
        return contentDao.getContentList(deptid, title, serial, createtime);
    }

    @Override
    public List<ContentDO> getContentList1(String modulepid,Integer top,String strkey) {
        return contentDao.getContentList1(modulepid,top,strkey);
    }
    @Override
    public List<ContentDO> getContentList2(String deptid,String modulepid,Integer top,String strkey) {
        return contentDao.getContentList2(deptid,modulepid,top,strkey);
    }
    @Override
    public List<ContentDO> getContentList3(String deptid,String modulepid,Integer top,String strkey, String status) {
        return contentDao.getContentList3(deptid,modulepid,top,strkey,status);
    }

    @Override
    public Integer insert(ContentDO contentDO) {
        return contentDao.insert(contentDO);
    }
    @Override
    public Integer insert1(ContentDO contentDO) {
        return contentDao.insert1(contentDO);
    }
    @Override
    public Integer update(ContentDO contentDO) {
        return contentDao.update(contentDO);
    }
    @Override
    public Integer update1(ContentDO contentDO) {
        return contentDao.update1(contentDO);
    }
//    @Override
//    public Integer updateUndo(Long contentid) {
//        return contentDao.updateUndo(contentid);
//    }

    @Override
    public Integer deleteById(Long id) {
        return contentDao.deleteById(id);
    }

    @Override
    public Integer deleteByModuleid(Long moduleid) {
        return contentDao.deleteByModuleid(moduleid);
    }
    @Override
    public ContentDO selectContentById(Long id) {
        return contentDao.selectContentById(id);
    }
    @Override
    public ContentDO selectContentByModuleid(Long moduleid, Long deptid) {
        return contentDao.selectContentByModuleid(moduleid, deptid);
    }

    @Override
    public Long getMaxId() {
        return contentDao.getMaxId();
    }
    @Override
    public Integer addBatchContentAttach(ContentAttachDO contentAttachDO){
        return contentDao.addBatchContentAttach(contentAttachDO);
    }
    @Override
    public List<ContentAttach> getContentAttachList(String contentid){
        return contentDao.getContentAttachList(contentid);
    }

    @Override
    public Integer deleteByAttachId(String attachid) {
        return contentDao.deleteByAttachId(attachid);
    }

    @Override
    public Integer deleteAttachByContentId(String contentid) {
        return contentDao.deleteAttachByContentId(contentid);
    }

    @Override
    public List<ContentDO> getContentListByTitle(String deptid, String searchContent, Integer top, String modulepid) {
        return contentDao.getContentListByTitle(deptid, searchContent, top, modulepid);
    }

    @Override
    public ModuleDO getModuleInfoByContentId(long id) {
        return contentDao.getModuleInfoByContentId(id);
    }

    @Override
    public Integer updatevisittimes(Long id) {
        return contentDao.updatevisittimes(id);
    }

    @Override
    public void updateUndo1(Long contentid, Integer undo) {
        contentDao.updateUndo1(contentid, undo);
    }




}
