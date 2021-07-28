package com.gwdtz.springboot.entity;

/**
 * @program: springboot
 * @Date: 2021-4-7 10:41
 * @Author: Miss.Chenmf
 * @Description:
 */
public class ContentAttach {
    private long id;
    private long contentid;
    private String attachrealname;
    private String attachrelativeurl;
    private String attachtype;
    private String attachsize;
    private String createtime;
    private String updatetime;
    private String attachabsoluteurl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getContentid() {
        return contentid;
    }

    public void setContentid(long contentid) {
        this.contentid = contentid;
    }

    public String getAttachrealname() {
        return attachrealname;
    }

    public void setAttachrealname(String attachrealname) {
        this.attachrealname = attachrealname;
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

    public String getAttachsize() {
        return attachsize;
    }

    public void setAttachsize(String attachsize) {
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

    public String getAttachabsoluteurl() {
        return attachabsoluteurl;
    }

    public void setAttachabsoluteurl(String attachabsoluteurl) {
        this.attachabsoluteurl = attachabsoluteurl;
    }
}
