package com.bohui.http;



/**
 * 基础control
 * 
 * @author yw-tony
 *
 */
public class BaseModel {

	public BaseModel() {
		super();

	}

	/**
	 * 获取用户id
	 * 
	 * @return
	 */
	public String getId() {
		try {
//			String id = SmartMarketApp.getInstance().userdb.getUserInfo().getId();
//			return id;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
