package com.bohui.bean;

import java.io.Serializable;

public class VersionsInfo implements Serializable {

	private int VersionCode;// 版本号
	private String VersionName;// 版本名称
	private String VersionsSize;// 版本大小
	private int Versionslength;// 版本长度
	private String VersionsUrl;// 版本下载路径
	private String VersionsMessage;// 更新内容
	private boolean Disable;// 是否更新
	//检测新版本增加的字段
	private int DepartmentType;//类型
	private String APKUrlString;//apk下载路径
	private String VersionId;//版本号
	public int getVersionCode() {
		return VersionCode;
	}

	public void setVersionCode(int versionCode) {
		VersionCode = versionCode;
	}

	public String getVersionName() {
		return VersionName;
	}

	public int getDepartmentType() {
		return DepartmentType;
	}

	public void setDepartmentType(int departmentType) {
		DepartmentType = departmentType;
	}

	public String getAPKUrlString() {
		return APKUrlString;
	}

	public void setAPKUrlString(String aPKUrlString) {
		APKUrlString = aPKUrlString;
	}


	public String getVersionId() {
		return VersionId;
	}

	public void setVersionId(String versionId) {
		VersionId = versionId;
	}

	public void setVersionName(String versionName) {
		VersionName = versionName;
	}

	public String getVersionsSize() {
		return VersionsSize;
	}

	public void setVersionsSize(String versionsSize) {
		VersionsSize = versionsSize;
	}

	public int getVersionslength() {
		return Versionslength;
	}

	public void setVersionslength(int versionslength) {
		Versionslength = versionslength;
	}

	public String getVersionsUrl() {
		return VersionsUrl;
	}

	public void setVersionsUrl(String versionsUrl) {
		VersionsUrl = versionsUrl;
	}

	public String getVersionsMessage() {
		return VersionsMessage;
	}

	public void setVersionsMessage(String versionsMessage) {
		VersionsMessage = versionsMessage;
	}

	public boolean isDisable() {
		return Disable;
	}

	public void setDisable(boolean disable) {
		Disable = disable;
	}

}
