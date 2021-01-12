package com.bohui.http;


import java.io.Serializable;
import java.util.List;

/**
 * 接收集合的泛型
 *
 * @param <T>
 * @author tony
 */
public class ResponseListBeanUtils<T> implements Serializable {
    private String code;//状态
    private String msg;
    private List<T> data;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }


}
