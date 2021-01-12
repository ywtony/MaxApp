package com.yw.downloadlibrary;


import android.content.Context;

import com.yw.downloadlibrary.bean.DownloadInfo;
import com.yw.downloadlibrary.http.InitDownloadThread;
import com.yw.downloadlibrary.inter.DownloadCallback;
import com.yw.downloadlibrary.msg.MsgHandler;

/**
 * 下载管理类
 * 断点续传
 * 如果要多任务下载，需要一个hashmap来维护多个任务，这个后续会补充上去
 * create by yangwei
 * on 2019-12-23 18:14
 */
public class DownLoadManager {
    private MsgHandler handler;
    public boolean isPause = false;//是否暂停线程，默认不暂停
    private Context context;

    private DownLoadManager(Context context) {
        this.handler = new MsgHandler(context);
        this.context = context;
    }

    private static DownLoadManager instance;

    public static DownLoadManager getDefault(Context context) {
        synchronized (DownLoadManager.class) {
            if (instance == null) {
                instance = new DownLoadManager(context);
            }
        }
        return instance;
    }

    public MsgHandler getHandler() {
        return handler;
    }

    /**
     * 设置下载路径并开始下载任务
     *
     * @param downloadInfo
     */
    public void startDownloadTask(DownloadCallback downloadCallback, DownloadInfo downloadInfo) {
        this.handler.setDownloadCallback(downloadCallback);
        new InitDownloadThread(context, downloadInfo).start();
    }
}
