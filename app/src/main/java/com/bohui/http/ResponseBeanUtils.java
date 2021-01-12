package com.bohui.http;

import java.io.Serializable;

/**
 * 请求网络返回结果的工具类
 * 
 * @author tony
 * 
 */
public class ResponseBeanUtils<T> implements Serializable{
	private String code;//状态
	private String msg;
	private T data;

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
