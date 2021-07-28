package com.gwdtz.springboot.entity;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-3-29 10:11
 * @Author: Mr.Wu
 * @Description:
 */
public class DeptTreeDO {
    long id;
    String name;
    long pid;
    List<DeptTreeDO> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public List<DeptTreeDO> getChildren() {
        return children;
    }

    public void setChildren(List<DeptTreeDO> children) {
        this.children = children;
    }

}
