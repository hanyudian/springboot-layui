package com.gwdtz.springboot.entity;

public class ModuleManageUrlDO {
    private Integer id;
    private String modulename;
    private String manageurl;

    public ModuleManageUrlDO(Integer id, String modulename, String manageurl) {
        this.id = id;
        this.modulename = modulename;
        this.manageurl = manageurl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getManageurl() {
        return manageurl;
    }

    public void setManageurl(String manageurl) {
        this.manageurl = manageurl;
    }
}
