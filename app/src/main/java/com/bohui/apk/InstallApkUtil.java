package com.bohui.apk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import com.bohui.utils.DevLog;

import java.io.File;

import androidx.core.content.FileProvider;

/**
 * 安装apk的帮助类
 *
 * @author tony
 */
public class InstallApkUtil {
    private InstallApkUtil() {
    }

    private static InstallApkUtil instance = null;

    public synchronized static InstallApkUtil getInstance() {
        if (instance == null) {
            instance = new InstallApkUtil();
        }
        return instance;
    }

    /**
     * 安装apk文件
     *
     * @param context
     * @param file
     */
//	public void install(Context context, File file) {
//		Intent intent = new Intent();
//		intent.setAction(Intent.ACTION_VIEW);
//		// apk 的MIMEType application/vnd.android.package-archive
//		String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
//				"apk");
//		intent.setDataAndType(Uri.fromFile(file), mimeType);
//		context.startActivity(intent);
//	}
    public void install(Context context, File file) {
        try {
            DevLog.e("开始更新apk");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //判断是否是AndroidN以及更高的版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(context, "com.bohui.fileprovider", file);
                intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
