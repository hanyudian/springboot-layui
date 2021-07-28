package com.gwdtz.springboot.entity;

import java.util.List;

/**
* @Author hanshuai(the developing of Four)
* @Description
 * @param
* @return
* @Date 2021/6/4 9:13
**/
public class ModuleDO {
    List<ModuleDO> children;
    private Long id;
    private String code;
    private String title;
    private Long pid;
    private Integer ischild;
    private String href;
    private String icon;
    private String target;
    private Long deptid;
    private Integer sortno;
    private Integer status;
    private String createtime;
    private String updatetime;
    private Integer deleted;
    private String deptname;

    public List<ModuleDO> getChildren() {
        return children;
    }

    public void setChildren(List<ModuleDO> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getIschild() {
        return ischild;
    }

    public void setIschild(Integer ischild) {
        this.ischild = ischild;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Long getDeptid() {
        return deptid;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }
}
