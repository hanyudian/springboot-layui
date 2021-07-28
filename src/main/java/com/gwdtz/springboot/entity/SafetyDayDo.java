package com.gwdtz.springboot.entity;

import java.util.Date;

public class SafetyDayDo {
    private Long id;

    private Long deptid;

    private String deptname;

    private Date updateflag;

    private Long aday;

    private Long bday;

    private Long cday;

    private Long dday;

    private Integer status;

    private Integer deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptid() {
        return deptid;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public Date getupdateflag() {
        return updateflag;
    }

    public void setupdateflag(Date updateflag) {
        this.updateflag = updateflag;
    }

    public Long getAday() {
        return aday;
    }

    public void setAday(Long aday) {
        this.aday = aday;
    }

    public Long getBday() {
        return bday;
    }

    public void setBday(Long bday) {
        this.bday = bday;
    }

    public Long getCday() {
        return cday;
    }

    public void setCday(Long cday) {
        this.cday = cday;
    }

    public Long getDday() {
        return dday;
    }

    public void setDday(Long dday) {
        this.dday = dday;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}