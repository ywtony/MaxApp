package com.yw.downloadlibrary.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * create by yangwei
 * on 2020-03-13 14:12
 */
public class PathUtil {
    /**
     * data/data/files 目录
     *
     * @param context
     * @param sourceName
     * @return
     */
    public static File getPath(Context context, String sourceName) {
        File extractFile = context.getFileStreamPath(sourceName);
        return extractFile;
    }

    public static File createDiskCacheDir(Context context, String dirName) {
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + dirName);

    }

    public static String createDisKCacheDir(Context context, String dirName) {
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + dirName).getPath();
    }

    /**
     * 创建从服务器下载下来的图片
     *
     * @return 返回图片存放路径
     */
    public static String createLocalFilePath(Context context, String dirName) {
        String path = "";
        if (MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {//判断外部存储是否可用
            path = context.getExternalFilesDir(dirName).getAbsolutePath();
        } else {//没外部存储就使用内部存储
            path = context.getFilesDir() + File.separator + dirName;
        }
        File file = new File(path);
//        if (!file.exists()) {//判断文件目录是否存在
//            file.mkdirs();
//        }

        return file.getPath();
    }
}
