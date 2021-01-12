package com.bohui.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * @author lyk
 * 
 *         网络�?��
 */
public class NetworkUtils {

	private static NetworkUtils instance = null;

	public static synchronized NetworkUtils getInstance() {
		if (instance == null) {
			instance = new NetworkUtils();
		}
		return instance;
	}

	/**
	 * 获取是否有网络连接信息的方法
	 * 
	 * @param context
	 * @return
	 */
	public static NetworkInfo getActiveNetwork(Context context) {
		if (context == null)
			return null;
		ConnectivityManager mConnMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (mConnMgr == null)
			return null;
		NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo();
		// 获取体验网络连接信息
		return aActiveInfo;
	}

	/**
	 * @param context
	 * @return �?��网络连接
	 */
	public static boolean isNetConnected(Context context) {
		boolean isNetConnected;
		if (context == null)
			return false;
		// 获得网络连接服务
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager == null)
			return false;
		NetworkInfo info = connManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {

			isNetConnected = true;
		} else {
//			L.i("没有可用网络");
			isNetConnected = false;
		}
		return isNetConnected;
	}

}
