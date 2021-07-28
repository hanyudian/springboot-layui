package com.gwdtz.springboot.entity.layui;

/**
 * @ClassName WangEditor
 * @Description
 * @Author hanshuai(the Developing of Four)
 * @Date 2021/7/26 11:38
 * @Version 1.0
 **/
public class WangEditor {
    private Integer errno;
    private String[] data;

    public WangEditor() {
        super();
    }

    public WangEditor(String[] data) {
        super();
        this.errno = 0;
        this.data = data;
    }

    public Integer getErrno() {
        return errno;
    }

    public void setErrno(Integer errno) {
        this.errno = errno;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
