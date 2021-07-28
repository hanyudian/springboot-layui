package com.gwdtz.springboot.entity;

public class ScheduleAttachDo {
    private Long id;

    private String attachrealname;

    private String attachabsoluteurl;

    private String attachrelativeurl;

    private String attachtype;

    private Double attachsize;

    private String createtime;

    private String updatetime;

    private String publishuser;

    private String publiship;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAttachrealname() {
        return attachrealname;
    }

    public void setAttachrealname(String attachrealname) {
        this.attachrealname = attachrealname;
    }

    public String getAttachabsoluteurl() {
        return attachabsoluteurl;
    }

    public void setAttachabsoluteurl(String attachabsoluteurl) {
        this.attachabsoluteurl = attachabsoluteurl;
    }

    public String getAttachrelativeurl() {
        return attachrelativeurl;
    }

    public void setAttachrelativeurl(String attachrelativeurl) {
        this.attachrelativeurl = attachrelativeurl;
    }

    public String getAttachtype() {
        return attachtype;
    }

    public void setAttachtype(String attachtype) {
        this.attachtype = attachtype;
    }

    public Double getAttachsize() {
        return attachsize;
    }

    public void setAttachsize(Double attachsize) {
        this.attachsize = attachsize;
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

    public String getPublishuser() {
        return publishuser;
    }

    public void setPublishuser(String publishuser) {
        this.publishuser = publishuser;
    }

    public String getPubliship() {
        return publiship;
    }

    public void setPubliship(String publiship) {
        this.publiship = publiship;
    }
}