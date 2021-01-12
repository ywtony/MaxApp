package com.bohui.apk;

import java.io.Serializable;

/**
 * apk下载实体类
 * 
 * @author tony
 * 
 */
public class DownInfo implements Serializable {
	
	private int Downloaded;// 当前下载量
	private int Progresss;// 当前下载进度
	private int Length;// 文件总大小
	private int TempProgress;// 进度中间变量值
	public int getDownloaded() {
		return Downloaded;
	}
	public void setDownloaded(int downloaded) {
		Downloaded = downloaded;
	}
	public int getProgresss() {
		return Progresss;
	}
	public void setProgresss(int progresss) {
		Progresss = progresss;
	}
	public int getLength() {
		return Length;
	}
	public void setLength(int length) {
		Length = length;
	}
	public int getTempProgress() {
		return TempProgress;
	}
	public void setTempProgress(int tempProgress) {
		TempProgress = tempProgress;
	}

	

}
