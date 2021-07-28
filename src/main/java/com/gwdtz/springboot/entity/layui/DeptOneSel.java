package com.gwdtz.springboot.entity.layui;

import java.util.List;

/**
 * @program: springboot
 * @Date: 2021-3-29 10:11
 * @Author: Mr.Wu
 * @Description:
 */
public class DeptOneSel {
    long id;
    String title;
    long pid;
    List<DeptOneSel> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public List<DeptOneSel> getChildren() {
        return children;
    }

    public void setChildren(List<DeptOneSel> children) {
        this.children = children;
    }

}
