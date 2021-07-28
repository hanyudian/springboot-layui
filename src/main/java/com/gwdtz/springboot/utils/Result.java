package com.gwdtz.springboot.utils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Auther: Jiangtian
 * @Date: 2019/7/23 15:58
 * @Description:
 */
@Data
public class Result {
    @ApiModelProperty(value="请求响应code，200为成功，500为失败")
    private int code;
    @ApiModelProperty(value="响应异常码详细信息",name = "msg")
    private Object data;
    @ApiModelProperty(value="需要返回的数据",name = "data")
    private String msg;

    public Result(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
