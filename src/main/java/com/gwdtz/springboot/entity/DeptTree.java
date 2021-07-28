package com.gwdtz.springboot.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
* @Author hanshuai(the developing of Four)
* @Description  dept树结构
 * @param
* @return
* @Date 2021/3/23 11:04
**/
@ApiModel(description = "部门树")
public class DeptTree {

    @ApiModelProperty(value = "部门id")
    long deptid;

    @ApiModelProperty(value = "部门名称")
    String deptname;

    @ApiModelProperty(value = "父节点")
    long pid;

    @ApiModelProperty(value = "是否是孩子节点（1有孩子，0没有孩子）")
    private Integer ischild;

    @ApiModelProperty(value = "孩子节点集合")
    private List<DeptTree> children;

    public long getDeptid() {
        return deptid;
    }

    public void setDeptid(long deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public Integer getIschild() {
        return ischild;
    }

    public void setIschild(Integer ischild) {
        this.ischild = ischild;
    }

    public List<DeptTree> getChildren() {
        return children;
    }

    public void setChildren(List<DeptTree> children) {
        this.children = children;
    }

}
