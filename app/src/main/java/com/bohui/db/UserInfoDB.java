package com.bohui.db;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bohui.bean.User;
import com.google.gson.Gson;


/**
 * 用户缓存数据
 * @author tony
 *
 */
public class UserInfoDB {
	private SharedPreferences sp;// 配置�?
	final static String regularEx = "|";

	public UserInfoDB(Context context) {
		sp = context.getSharedPreferences("user.db",
				context.MODE_PRIVATE);
	}

	/**
	 * 存储用户基本信息
	 * 
	 * @param info
	 */
	public void setUserInfo(String info) {
		Editor edit = sp.edit();
		edit.putString("userinfo", info);
		edit.commit();
	}

	/**
	 * 获取用户基本信息(返回user对象)
	 * 
	 * @return
	 */
	public User getUserInfo() {
		String json = sp.getString("userinfo", "");
		return  new Gson().fromJson(json, User.class);

	}

	public void removeUserInfo() {
		try {
			Editor edit = sp.edit();
			edit.remove("userinfo");
			edit.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取sharedpreference对象
	 * 
	 * @return
	 */
	public SharedPreferences getSp() {
		return sp;
	}
	
}
