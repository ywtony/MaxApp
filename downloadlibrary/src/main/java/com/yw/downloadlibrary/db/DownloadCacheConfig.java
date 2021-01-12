package com.yw.downloadlibrary.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.yw.downloadlibrary.Config;
import com.yw.downloadlibrary.DownLoadManager;
import com.yw.downloadlibrary.bean.DownloadInfo;
import com.yw.downloadlibrary.inter.DownloadCallback;
import com.yw.downloadlibrary.utils.JSONUtil;

/**
 * 下载进度配置
 * 用于存储下载位置等信息
 * create by yangwei
 * on 2019-12-23 20:24
 */
public class DownloadCacheConfig {
    private SharedPreferences sp;// 配置�?
    private static final String DOWNLOAD_INFO = "download_info";

    private DownloadCacheConfig(Context context) {
        sp = context.getSharedPreferences(Config.CONIFG_NAME,
                Context.MODE_PRIVATE);
    }

    private DownloadCacheConfig() {
    }

    private static DownloadCacheConfig instance;

    public static synchronized DownloadCacheConfig getDefault(Context context) {
        if (instance == null) {
            instance = new DownloadCacheConfig(context);
        }
        return instance;
    }

    /**
     * 存储下载信息
     *
     * @param
     */
    public void setDownloadInfo(String info) {
        try {
            if (info == null || "".equals(info)) {
                return;
            }
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(DOWNLOAD_INFO, info);
            edit.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取下载信息
     *
     * @return
     */
    public DownloadInfo getDownloadInfo() {

        DownloadInfo downloadInfo = null;
        try {

            String str = sp.getString(DOWNLOAD_INFO, "");
            downloadInfo = (DownloadInfo) JSONUtil.getInstance().getObject(str, DownloadInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return downloadInfo;

    }

    /**
     * 删除登陆信息
     */
    public void removeDownloadInfo() {
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(DOWNLOAD_INFO);
        edit.commit();
    }


}
