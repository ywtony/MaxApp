package com.yw.downloadlibrary.bean;

import java.io.Serializable;

/**
 * 下载实体类,用于存储下载信息。如：文件大小、下载进度等
 * create by yangwei
 * on 2019-12-23 18:30
 */
public class DownloadInfo implements Serializable {
    private String fileName;//文件名称
    private int fileSize;//文件大小
    private int process;//下载进度
    private String url;//下载地址
    private int startLocation;//初始化下载位置
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public int getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(int startLocation) {
        this.startLocation = startLocation;
    }
}
