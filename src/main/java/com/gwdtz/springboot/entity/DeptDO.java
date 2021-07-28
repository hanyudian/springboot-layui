package com.gwdtz.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "部门信息")
public class DeptDO {

    @ApiModelProperty(hidden = true)
    private Long deptid;

    @ApiModelProperty(value = "部门名称")
    private String deptname;

    @ApiModelProperty(value = "级别编码")
    private String deptlevelcode;

    @ApiModelProperty(value = "部门唯一编码")
    private String deptcode;

    @ApiModelProperty(value = "父节点")
    private Long pid;

    @ApiModelProperty(value = "排序号")
    private Integer sortno;

    @ApiModelProperty(value = "是否是孩子节点（1有孩子，2没有孩子")
    private Integer ischild;

    @ApiModelProperty(value = "状态（1正常，0弃用）")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    private String createtime;

    @ApiModelProperty(value = "更新时间")
    private String updatetime;

    @ApiModelProperty(value = "是否删除（1未删除，2已删除）")
    private Integer deleted;

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

    public String getDeptlevelcode() {
        return deptlevelcode;
    }

    public void setDeptlevelcode(String deptlevelcode) {
        this.deptlevelcode = deptlevelcode;
    }

    public String getDeptcode() {
        return deptcode;
    }

    public void setDeptcode(String deptcode) {
        this.deptcode = deptcode;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }

    public Integer getIschild() {
        return ischild;
    }

    public void setIschild(Integer ischild) {
        this.ischild = ischild;
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

    @Override
    public String toString() {
        return "DeptDO{" +
                "deptid=" + deptid +
                ", deptname='" + deptname + '\'' +
                ", deptlevelcode='" + deptlevelcode + '\'' +
                ", deptcode='" + deptcode + '\'' +
                ", pid=" + pid +
                ", sortno=" + sortno +
                ", ischild=" + ischild +
                ", status=" + status +
                ", createtime='" + createtime + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}