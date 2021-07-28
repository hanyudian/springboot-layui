package com.gwdtz.springboot.entity.layui;

import java.util.List;

/**
 * @ClassName UserModule
 * @Description
 * @Author hanshuai(the Developing of Four)
 * @Date 2021/7/19 16:27
 * @Version 1.0
 **/
public class UserModule {
    private long id;
    private long pid;
    private String title;
    private String icon;
    private String href;
    private String target;
    private Integer ischild;
    private List<UserModule> children;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
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

    public List<UserModule> getChildren() {
        return children;
    }

    public void setChildren(List<UserModule> children) {
        this.children = children;
    }
}
