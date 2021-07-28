package com.gwdtz.springboot.entity;

/**
 * @program: springboot
 * @Date: 2021-3-22 16:29
 * @Author: Miss.Chenmf
 * @Description:
 */
public class UserLoginDo {
    private Integer userid;
    private String username;
    private String password;
    private Integer deptid;
    private String realname;
    private String phone;
    private Integer roleid;
    private Integer status;
    private String createtime;
    private String updatetime;
    private Integer deleted;
    private String deptlevelcode;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getRoleid() {
        return roleid;
    }

    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
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

    public String getDeptlevelcode() {
        return deptlevelcode;
    }

    public void setDeptlevelcode(String deptlevelcode) {
        this.deptlevelcode = deptlevelcode;
    }
}
