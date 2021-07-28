package com.gwdtz.springboot.entity;

public class ScheduleDetailDo {
    private Long id;

    private Long scheduleid;

    private String date;

    private String time;

    private Integer flag;

    public ScheduleDetailDo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleid() {
        return scheduleid;
    }

    public void setScheduleid(Long scheduleid) {
        this.scheduleid = scheduleid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}