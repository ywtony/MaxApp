package com.bohui.http;

/**
 * 请求网络数据�?
 * 
 * @author tony
 * 
 */
public interface JsonResponseInter {

	/**
	 * 网络请求成功时的执行函数
	 * 
	 * @param statusCode
	 * @param successJson
	 */
	public void onSuccess(int statusCode, String successJson);

	/**
	 * 网络请求失败时的执行函数
	 * 
	 * @param statusCode
	 * @param errJson
	 */
	public void onFailure(int statusCode, String errJson);

}
