package com.gwdtz.springboot.entity;

import java.util.List;

/**
* @Author hanshuai(the developing of Four)
* @Description
 * @param
* @return
* @Date 2021/6/4 9:14
**/
public class ModuleTree {
    private long id;
    private long pid;
    private String title;
    private String icon;
    private String href;
    private String target;
    private Integer ischild;
    private List<ModuleTree> child;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getIschild() {
        return ischild;
    }

    public void setIschild(Integer ischild) {
        this.ischild = ischild;
    }

    public List<ModuleTree> getChild() {
        return child;
    }

    public void setChild(List<ModuleTree> child) {
        this.child = child;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }
}
