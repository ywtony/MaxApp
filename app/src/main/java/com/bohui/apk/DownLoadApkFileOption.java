package com.bohui.apk;

import android.content.Context;
import android.os.Environment;


import com.bohui.utils.DevLog;

import java.io.File;

/**
 * 配置要下载的apk
 * 
 * @author tony
 * 
 */
public class DownLoadApkFileOption {
	private String fileName;// 保存的文件名�?
	private String savePath;// 保存文件的位�?

	private DownLoadApkFileOption() {
	}

	private static DownLoadApkFileOption instance;

	public synchronized static DownLoadApkFileOption getinstance() {
		if (instance == null) {
			instance = new DownLoadApkFileOption();
		}
		return instance;
	}

	public String getFileName(Context context) {
		if (fileName == null) {
			return "wmtp"
					+ AppInfoUtil.getInstance().getInfo(context)
							.getVersionName() + ".apk";
		}
		return fileName;
	}

	public DownLoadApkFileOption setFileName(String fileName) {
		this.fileName = fileName;
		return instance;
	}

	public String getSavePath() {
		String filePath = null;
		if (DownLoadFileUtil.getInstance().isExternalStorageExists()) {
//			filePath = Environment.getExternalStorageDirectory()
//					+ "/downloadwenmingtaopu/";
			filePath = Environment.getExternalStorageDirectory()
					+ "/";
//			filePath = Environment.getRootDirectory() + "/wmtp/";
//						filePath = Environment.getRootDirectory() + "/";
			DevLog.e("if"+filePath);
		} else {
			filePath = Environment.getRootDirectory() + "/wmtp/";
			DevLog.e("else"+filePath);
		}
		File file = null;
		try {
			 file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file.getPath();
	}

	public DownLoadApkFileOption setSavePath(String savePath) {
		this.savePath = savePath;
		return instance;
	}

}
