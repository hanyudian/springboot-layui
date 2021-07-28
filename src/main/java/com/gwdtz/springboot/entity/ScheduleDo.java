package com.gwdtz.springboot.entity;

public class ScheduleDo {
    private Long id;

    private Long userid;

    private String attachids;

    private String title;

    private Integer type;

    private Integer year;

    private Integer quarter;

    private Integer week;

    private Integer day;

    private Integer persontype;

    private Integer open;

    private String date;

    private String time;

    private String calltime;

    private String jsonArray;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getPersontype() {
        return persontype;
    }

    public void setPersontype(Integer persontype) {
        this.persontype = persontype;
    }

    public String getAttachids() {
        return attachids;
    }

    public void setAttachids(String attachids) {
        this.attachids = attachids;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOpen() {
        return open;
    }

    public void setOpen(Integer open) {
        this.open = open;
    }

    public String getCalltime() {
        return calltime;
    }

    public void setCalltime(String calltime) {
        this.calltime = calltime;
    }

    public String getJsonArray() {
        return jsonArray;
    }

    public void setJsonArray(String jsonArray) {
        this.jsonArray = jsonArray;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getQuarter() {
        return quarter;
    }

    public void setQuarter(Integer quarter) {
        this.quarter = quarter;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
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
}