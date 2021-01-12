package com.bohui.apk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;

/**
 * 下载apk时的文件帮助�?
 * 
 * @author tony
 * 
 */
public class DownLoadFileUtil {
	private DownLoadFileUtil() {
	}

	private static DownLoadFileUtil instance = null;

	public synchronized static DownLoadFileUtil getInstance() {
		if (instance == null) {
			instance = new DownLoadFileUtil();
		}
		return instance;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @return
	 */
	public boolean isFileExists(Context context) {
		File file = new File(DownLoadApkFileOption.getinstance().getSavePath(),
				DownLoadApkFileOption.getinstance().getFileName(context));
		return file.exists();
	}

	/**
	 * 判断手机内存卡是否存�?
	 * 
	 * @return
	 */
	public boolean isExternalStorageExists() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断文件大小是否�?��
	 * 
	 * @param context
	 * @param
	 * @return
	 */
	public boolean isFileFull(Context context) {
		File file = new File(DownLoadApkFileOption.getinstance().getSavePath(),
				DownLoadApkFileOption.getinstance().getFileName(context));
		PackageManager pm = context.getPackageManager();
		PackageInfo info = pm.getPackageArchiveInfo(file.getPath(), PackageManager.GET_ACTIVITIES);
		if(info != null){
			return true; 
		}
//		if (file.exists() && file.isFile()) {
//			Long fileLong = Long.valueOf(file.length());
//			Long dLong = Long.valueOf(length);
//			if (fileLong.equals(dLong)) {
//				return true;
//			}
//		}
		return false;
	}

	/**
	 * 删除文件
	 * 
	 * @param context
	 * @return
	 */
	public boolean deleteFile(Context context) {
		try {
			File file = new File(DownLoadApkFileOption.getinstance()
					.getSavePath(), DownLoadApkFileOption.getinstance()
					.getFileName(context));
			return file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
