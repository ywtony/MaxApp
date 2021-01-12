package com.bohui.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;


import com.bohui.bean.VersionsInfo;

import java.util.Iterator;
import java.util.List;

/**
 * 应用版本信息
 * 
 * @author tony
 * 
 */
public class AppInfoUtil {
	private AppInfoUtil() {

	}

	private static AppInfoUtil instance = null;

	public synchronized static AppInfoUtil getInstance() {
		if (instance == null) {
			instance = new AppInfoUtil();
		}
		return instance;
	}

	/**
	 * 获取应用的版本信�?
	 * 
	 * @param context
	 * @return
	 */
	public VersionsInfo getInfo(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),
					0);
			String versionName = info.versionName;
			int versionCode = info.versionCode;
			VersionsInfo infoBean = new VersionsInfo();
			infoBean.setVersionCode(versionCode);
			infoBean.setVersionName(versionName);
			return infoBean;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 根据pid获取应用名称
	 * 
	 * @param pID
	 * @return
	 */
	public String getAppName(Context context, int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = context.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
					.next());
			try {
				if (info.pid == pID) {
					CharSequence c = pm.getApplicationLabel(pm
							.getApplicationInfo(info.processName,
									PackageManager.GET_META_DATA));
					// Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
					// info.processName +"  Label: "+c.toString());
					// processName = c.toString();
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
				Log.e("Process", "Error>> :" + e.toString());
			}
		}
		return processName;
	}
	/**
	 * 初始化百度sdk
	 */
	public void sdkInfo(Context context){
		try {

			int pid = android.os.Process.myPid();
			String processAppName = AppInfoUtil.getInstance().getAppName(context,
					pid);
			Log.e("pid", pid + "pid");
			Log.e("proccessAppName", processAppName + "appName");
			// 如果使用到百度地图或者类似启动remote service的第三方库，这个if判断不能�?
			if (processAppName == null || processAppName.equals("")) {
				DevLog.e("为空");
				// workaround for baidu location sdk
				// 百度定位sdk，定位服务运行在�?��单独的进程，每次定位服务启动的时候，都会调用application::onCreate
				// 创建新的进程�?
				// 但环信的sdk只需要在主进程中初始化一次�? 这个特殊处理是，如果从pid 找不到对应的processInfo
				// processName�?
				// 则此application::onCreate 是被service 调用的，直接返回
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
