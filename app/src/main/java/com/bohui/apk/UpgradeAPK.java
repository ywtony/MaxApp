package com.bohui.apk;

import android.content.Context;


/**
 * app自动更新
 */
public class UpgradeAPK {
    /**
     * 构造方法
     *
     * @param context
     * @param url       apk下载路径
     * @param length    apk大小
     * @param message   apk更新内容
     * @param isDisable 是否强制更新
     */
    public UpgradeAPK(Context context, String url, int length, String message, boolean isDisable) {
        // 如果已下载完成，则标记不再下载，如果正在下载中，则提示应用已在下载列�?
        DownLoadDialog downDialog = new DownLoadDialog(context, url,
                length, message, isDisable);
        downDialog.show();
    }
}
