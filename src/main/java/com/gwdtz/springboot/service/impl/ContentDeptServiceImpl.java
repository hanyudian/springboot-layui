package com.gwdtz.springboot.service.impl;

import com.gwdtz.springboot.dao.ContentDeptDao;
import com.gwdtz.springboot.entity.ContentDeptDO;
import com.gwdtz.springboot.service.IContentDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-3-3 15:56
 * @Author: Miss.Yanjc
 * @Description:
 */
@Service
public class ContentDeptServiceImpl implements IContentDeptService {
    @Autowired
    ContentDeptDao contentDeptDao;
    @Override
    public Integer deleteById(Long contentid, String deptids) {
        Integer num = contentDeptDao.deleteById(contentid);
        for (String deptid :
                deptids.split(",")) {
            if(!deptid.equals("22"))
             {
                num += contentDeptDao.insert(contentid, deptid);
            }
        }
        return num;
    }

    @Override
    public Integer deleteByDeptId(String deptid) {
        return contentDeptDao.deleteByDeptId(deptid);
    }

    @Override
    public Integer insert(Long contentid, String deptid) {
        return contentDeptDao.insert(contentid, deptid);
    }

    @Override
    public Integer deleteByContentid(Long contentid) {
        Integer num = contentDeptDao.deleteByContentid(contentid);
        return num;
    }

    @Override
    public List<ContentDeptDO> getContentDeptList(String contentid) {
        return contentDeptDao.getContentDeptList(contentid);
    }

    @Override
    public Integer[] selectDeptById(Long contentid) {
        Integer[] num = contentDeptDao.selectDeptById(contentid);
        return num;
    }
    @Override
    public Integer[] selectStatusById(Long contentid) {
        Integer[] num = contentDeptDao.selectStatusById(contentid);
        return num;
    }

    @Override
    public Integer deleteByDeptIds(String[] deptids) {
        Integer num = 0;
        for (String deptid :
                deptids) {
            num += contentDeptDao.deleteByDeptId(deptid);
        }
        return num;
    }
    @Override
    public Integer update(ContentDeptDO contentDeptDO) {
        return contentDeptDao.update(contentDeptDO);
    }
    @Override
    public Integer update5(ContentDeptDO contentDeptDO) {
        return contentDeptDao.update5(contentDeptDO);
    }
    @Override
    public Integer update2(ContentDeptDO contentDeptDO) {
        return contentDeptDao.update2(contentDeptDO);
    }
    @Override
    public Integer updateRollback(ContentDeptDO contentDeptDO) {
        return contentDeptDao.updateRollback(contentDeptDO);
    }
    @Override
    public Integer updatestatus5(Long id) {
        return contentDeptDao.updatestatus5(id);
    }
    @Override
    public ContentDeptDO selectCompleteByContentid(Long contentid,Long deptid) {
        return contentDeptDao.selectCompleteByContentid(contentid,deptid);
    }

    @Override
    public ContentDeptDO selectContentDeptById(long id) {
        return contentDeptDao.selectContentDeptById(id);
    }
}