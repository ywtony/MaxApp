package com.bohui.apk;

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
    private String bvrobotVersion; // bvrobot 版本号
    private String systemVersion; // linux sys 版本号
    private String stm32Version; // stm32 版本号
    private int apkSize;
    private boolean NeedUpdate;
    private String Version;
    private String Path;
    private int Size;

    public boolean isNeedUpdate() {
        return NeedUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        NeedUpdate = needUpdate;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    public int getApkSize() {
        return apkSize;
    }

    public void setApkSize(int apkSize) {
        this.apkSize = apkSize;
    }

    public String getBvrobotVersion() {
        return bvrobotVersion;
    }

    public void setBvrobotVersion(String bvrobotVersion) {
        this.bvrobotVersion = bvrobotVersion;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
    }

    public String getStm32Version() {
        return stm32Version;
    }

    public void setStm32Version(String stm32Version) {
        this.stm32Version = stm32Version;
    }

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

    //新版的强制更新
    private boolean isNew;// 是否是新版本
    private boolean isNewBVROBOT; // 是否是新版本
    private boolean isNewSTM32;// 是否是新版本
    private boolean isNewSYSTEM;// 是否是新版本(必须要强制更新)
    private boolean mustUpdateBVROBOT;
    // 是否强制更新
    private boolean mustUpdateSTM32;// 是否强制更新
    private boolean mustUpdateSYSTEM;// 是否强制更新
    private String version; // 版本号
    private String url; // 下载地址
    private boolean mustUpdate;// 是否强制更新

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isNewBVROBOT() {
        return isNewBVROBOT;
    }

    public void setNewBVROBOT(boolean newBVROBOT) {
        isNewBVROBOT = newBVROBOT;
    }

    public boolean isNewSTM32() {
        return isNewSTM32;
    }

    public void setNewSTM32(boolean newSTM32) {
        isNewSTM32 = newSTM32;
    }

    public boolean isNewSYSTEM() {
        return isNewSYSTEM;
    }

    public void setNewSYSTEM(boolean newSYSTEM) {
        isNewSYSTEM = newSYSTEM;
    }

    public boolean isMustUpdateBVROBOT() {
        return mustUpdateBVROBOT;
    }

    public void setMustUpdateBVROBOT(boolean mustUpdateBVROBOT) {
        this.mustUpdateBVROBOT = mustUpdateBVROBOT;
    }

    public boolean isMustUpdateSTM32() {
        return mustUpdateSTM32;
    }

    public void setMustUpdateSTM32(boolean mustUpdateSTM32) {
        this.mustUpdateSTM32 = mustUpdateSTM32;
    }

    public boolean isMustUpdateSYSTEM() {
        return mustUpdateSYSTEM;
    }

    public void setMustUpdateSYSTEM(boolean mustUpdateSYSTEM) {
        this.mustUpdateSYSTEM = mustUpdateSYSTEM;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isMustUpdate() {
        return mustUpdate;
    }

    public void setMustUpdate(boolean mustUpdate) {
        this.mustUpdate = mustUpdate;
    }
}
