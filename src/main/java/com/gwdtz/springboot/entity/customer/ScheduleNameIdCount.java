package com.gwdtz.springboot.entity.customer;

/**
 * @ClassName ScheduleNameIdCount
 * @Description 此类仅用于前端日程自动球球是返回数据
 * @Author hanshuai(the Developing of Four)
 * @Date 2021/7/7 18:39
 * @Version 1.0
 **/
public class ScheduleNameIdCount {

    private String name;
    private Integer persontype;
    private Integer count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPersontype() {
        return persontype;
    }

    public void setPersontype(Integer persontype) {
        this.persontype = persontype;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
