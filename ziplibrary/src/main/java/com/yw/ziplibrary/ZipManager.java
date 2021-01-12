package com.yw.ziplibrary;


import com.yw.ziplibrary.inter.UnZipCallback;
import com.yw.ziplibrary.inter.ZipCallback;
import com.yw.ziplibrary.utils.FileUtils;
import com.yw.ziplibrary.zip.ZipUtils;

/**
 * zip解压的工具类
 * create by yangwei
 * on 2019-12-24 10:01
 */
public class ZipManager {
    private ZipUtils zipUtils;

    private ZipManager() {
        zipUtils = new ZipUtils();
    }

    private static ZipManager instance;

    public synchronized static ZipManager getDefault() {
        if (instance == null) {
            instance = new ZipManager();
        }
        return instance;
    }

    /**
     * 解压文件
     *
     * @param zipFilePath   zip文件的本地路径
     * @param outForderPath 输出文件夹的路径
     * @param unZipCallback 解压结果回调
     */
    public void unZip(String zipFilePath, String outForderPath, UnZipCallback unZipCallback) {
        if(FileUtils.getInstance().checkZip(zipFilePath)){
            zipUtils.setUnZipCallback(unZipCallback);
            zipUtils.unZipFile(zipFilePath, outForderPath);
        }

    }

    /**
     * 压缩文件
     *
     * @param srcPath     源文件或者文件夹路径
     * @param filePath    压缩后的zip文件路径
     * @param zipCallback 压缩回调函数
     */
    public void zip(String srcPath, String filePath, ZipCallback zipCallback) {
        zipUtils.setZipCallback(zipCallback);
        zipUtils.zipFolder(srcPath, filePath);
    }
}
