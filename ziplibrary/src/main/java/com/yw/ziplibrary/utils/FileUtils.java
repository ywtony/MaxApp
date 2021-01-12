package com.yw.ziplibrary.utils;

import android.util.Log;

import java.io.File;

/**
 * 文件工具类
 * create by yangwei
 * on 2019-12-24 14:04
 */
public class FileUtils {
    private FileUtils() {
    }

    private static FileUtils instance;

    public synchronized static FileUtils getInstance() {
        if (instance == null) {
            instance = new FileUtils();
        }
        return instance;
    }

    /**
     * 检查是否是zip格式的文件
     *
     * @param filePath
     * @return
     */
    public boolean checkZip(String filePath) {
        if (filePath != null) {
            if (!filePath.endsWith(".zip")) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查文件后缀名是否是zip
     *
     * @param file
     * @return
     */
    public boolean checkZip(File file) {
        String filePath = file.getPath();
        if (filePath != null) {
            if (!filePath.endsWith(".zip") || file.length() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 根据文件夹路径以及指定的文件名判断文件夹下是否存在指定的文件
     *
     * @param boxImgPath
     * @param fileName
     * @return
     */
    public boolean isExitsFile(String boxImgPath, String fileName) {
        File file = new File(boxImgPath);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return false;
            }
            for (File f : files) {
                if (f.getName().equals(fileName)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isDirNull(String boxImgPth) {
        Log.e("localPath:", boxImgPth);
        File file = new File(boxImgPth);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length > 1) {
                return false;
            }
        }
        return true;
    }
}
