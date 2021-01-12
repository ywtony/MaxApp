package com.yw.ziplibrary.zip;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.yw.ziplibrary.inter.UnZipCallback;
import com.yw.ziplibrary.inter.ZipCallback;
import com.yw.ziplibrary.utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import androidx.annotation.RequiresApi;

/**
 * zip解压的工具类
 * create by yangwei
 * on 2019-12-24 10:03
 */
public class ZipUtils {
    private static final String TAG = "ZipUtils";

    /**
     * 解压zip文件
     *
     * @param zipFilePath   需要解压的文件路径
     * @param outFolderPath 解压后的文件夹路径
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void unZipFile(String zipFilePath, String outFolderPath) {
        if (!FileUtils.getInstance().checkZip(zipFilePath)) {//判断要解压的文件是否是zip格式的文件
            if (unZipCallback != null) {
                unZipCallback.unZipFail(outFolderPath,"文件类型不是.zip");
                return;
            }
        }

        ZipInputStream inzip = null;
        ZipEntry zipEntry = null;
        String szName = "";
        try {
            inzip = new ZipInputStream(new FileInputStream(zipFilePath), Charset.forName("GBK"));
            //开始解压
            if (unZipCallback != null) {
                unZipCallback.startUnZip(null);
            }
            while ((zipEntry = inzip.getNextEntry()) != null) {
                szName = zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    //获取部件的文件夹名称
                    szName = szName.substring(0, szName.length() - 1);
                    File folder = new File(outFolderPath + File.separator + szName);
                    folder.mkdirs();

                } else {
                    Log.e(TAG, outFolderPath + File.separator + szName);
                    File file = new File(outFolderPath + File.separator + szName);
                    if (!file.exists()) {
                        Log.e(TAG, "Create the file:" + outFolderPath + File.separator + szName);
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    // 获取文件的输出流
                    FileOutputStream out = new FileOutputStream(file);
                    int len;
                    byte[] buffer = new byte[1024];
                    // 读取（字节）字节到缓冲区
                    while ((len = inzip.read(buffer)) != -1) {
                        // 从缓冲区（0）位置写入（字节）字节
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    if (out != null) {
                        out.close();
                    }

                }
            }
            if (unZipCallback != null) {
                unZipCallback.unZipSuccess(outFolderPath);
            }

        } catch (Exception e) {
            if (unZipCallback != null) {
                unZipCallback.unZipFail(outFolderPath,e.getMessage());
            }
        } finally {
            try {
                if (inzip != null) {
                    inzip.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 将zip文件解压到指定的目录
     *
     * @param zipFileString
     * @param outPathString
     * @param szName
     * @throws Exception
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void unZipFile(String zipFileString, String outPathString, String szName) {
        ZipInputStream inZip = null;
        ZipEntry zipEntry = null;
        try {
            inZip = new ZipInputStream(new FileInputStream(zipFileString), Charset.forName("GBK"));
            if (unZipCallback != null) {
                unZipCallback.startUnZip(null);
            }
            while ((zipEntry = inZip.getNextEntry()) != null) {
                if (zipEntry.isDirectory()) {
                    //获取部件的文件夹名
                    szName = szName.substring(0, szName.length() - 1);
                    File folder = new File(outPathString + File.separator + szName);
                    folder.mkdirs();
                } else {
                    Log.e(TAG, outPathString + File.separator + szName);
                    File file = new File(outPathString + File.separator + szName);
                    if (!file.exists()) {
                        Log.e(TAG, "Create the file:" + outPathString + File.separator + szName);
                        file.getParentFile().mkdirs();
                        file.createNewFile();
                    }
                    // 获取文件的输出流
                    FileOutputStream out = new FileOutputStream(file);
                    int len;
                    byte[] buffer = new byte[1024];
                    // 读取（字节）字节到缓冲区
                    while ((len = inZip.read(buffer)) != -1) {
                        // 从缓冲区（0）位置写入（字节）字节
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    if (out != null) {
                        out.close();
                    }

                }
            }
            if (unZipCallback != null) {
                unZipCallback.unZipSuccess(outPathString);
            }

        } catch (Exception e) {
            if (unZipCallback != null) {
                unZipCallback.unZipFail(outPathString,e.getMessage());
            }
        } finally {
            try {
                if (inZip != null) {
                    inZip.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩文件和文件夹
     *
     * @param srcFilePath
     * @param zipFilePath
     */
    public void zipFolder(String srcFilePath, String zipFilePath) {
        //创建ZIP
        ZipOutputStream outZip = null;
        try {
            if (zipCallback != null) {
                zipCallback.startZip(null);
            }
            outZip = new ZipOutputStream(new FileOutputStream(zipFilePath));
            //创建文件
            File file = new File(srcFilePath);
            //压缩
            Log.e(TAG, "---->" + file.getParent() + "===" + file.getAbsolutePath());
            zipFiles(file.getParent() + File.separator, file.getName(), outZip);
            if (zipCallback != null) {
                zipCallback.zipSuccess(zipFilePath);
            }
        } catch (Exception e) {
            if (zipCallback != null) {
                zipCallback.zipFail(e.getMessage());
            }
        } finally {
            try {
                if (outZip != null) {
                    //完成和关闭
                    outZip.finish();
                    outZip.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 压缩文件
     *
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     */
    private void zipFiles(String folderString, String fileString, ZipOutputStream zipOutputSteam) throws Exception {
        Log.e(TAG, "folderString:" + folderString + "\n" +
                "fileString:" + fileString + "\n==========================");
        if (zipOutputSteam == null)
            return;
        File file = new File(folderString + fileString);
        if (file.isFile()) {
            ZipEntry zipEntry = new ZipEntry(fileString);
            FileInputStream inputStream = new FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);
            int len;
            byte[] buffer = new byte[4096];
            while ((len = inputStream.read(buffer)) != -1) {
                zipOutputSteam.write(buffer, 0, len);
            }
            zipOutputSteam.closeEntry();
        } else {
            //文件夹
            String fileList[] = file.list();
            //没有子文件和压缩
            if (fileList.length <= 0) {
                ZipEntry zipEntry = new ZipEntry(fileString + File.separator);
                zipOutputSteam.putNextEntry(zipEntry);
                zipOutputSteam.closeEntry();
            }
            //子文件和递归
            for (int i = 0; i < fileList.length; i++) {
                zipFiles(folderString + fileString + "/", fileList[i], zipOutputSteam);
            }
        }
    }

    /**
     * 返回zip的文件输入流
     *
     * @param zipFileString zip的名称
     * @param fileString    ZIP的文件名
     * @return InputStream
     * @throws Exception
     */
    public static InputStream unZip(String zipFileString, String fileString) throws Exception {
        ZipFile zipFile = new ZipFile(zipFileString);
        ZipEntry zipEntry = zipFile.getEntry(fileString);
        return zipFile.getInputStream(zipEntry);
    }

    /**
     * 返回ZIP中的文件列表（文件和文件夹）
     *
     * @param zipFileString  ZIP的名称
     * @param bContainFolder 是否包含文件夹
     * @param bContainFile   是否包含文件
     * @return
     * @throws Exception
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<File> getFileList(String zipFileString, boolean bContainFolder, boolean bContainFile) throws Exception {
        List<File> fileList = new ArrayList<File>();
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString), Charset.forName("GBK"));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                // 获取部件的文件夹名
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(szName);
                if (bContainFolder) {
                    fileList.add(folder);
                }
            } else {
                File file = new File(szName);
                if (bContainFile) {
                    fileList.add(file);
                }
            }
        }
        inZip.close();
        return fileList;
    }

    private ZipCallback zipCallback;

    public void setZipCallback(ZipCallback zipCallback) {
        this.zipCallback = zipCallback;
    }

    private UnZipCallback unZipCallback;

    public void setUnZipCallback(UnZipCallback unZipCallback) {
        this.unZipCallback = unZipCallback;
    }

}
