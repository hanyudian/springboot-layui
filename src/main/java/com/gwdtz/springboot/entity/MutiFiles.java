package com.gwdtz.springboot.entity;

/**
 * @program: springboot
 * @Date: 2021-3-30 16:23
 * @Author: Miss.Chenmf
 * @Description:
 */
public class MutiFiles {
   private String filename;
   private String filetype;
   private String relativeurl;
    private String absoluteurl;
   private float filesize;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getRelativeurl() {
        return relativeurl;
    }

    public void setRelativeurl(String relativeurl) {
        this.relativeurl = relativeurl;
    }

    public String getAbsoluteurl() {
        return absoluteurl;
    }

    public void setAbsoluteurl(String absoluteurl) {
        this.absoluteurl = absoluteurl;
    }

    public float getFilesize() {
        return filesize;
    }

    public void setFilesize(float filesize) {
        this.filesize = filesize;
    }
}
