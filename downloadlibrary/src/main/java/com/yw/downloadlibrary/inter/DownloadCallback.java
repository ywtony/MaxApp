package com.yw.downloadlibrary.inter;

import com.yw.downloadlibrary.bean.DownloadInfo;

/**
 * 下载回调
 * 监听：下载进度、下载完成、下载失败等
 * create by yangwei
 * on 2019-12-23 18:24
 */
public interface DownloadCallback {
    /**
     *连接成功
     */
    void connectSuccess();

    /**
     * 开始下载
     */
    void startDownload();

    /**
     * 更新下载进度，下载中。。。
     * @param downloadInfo
     */
    void downloadUpdate(DownloadInfo downloadInfo);

    /**
     * 下载成功
     */
    void downloadSuccess(String filePath);

    /**
     * 下载失败
     * @param errMsg
     */
    void downloadFail(String errMsg);
}
