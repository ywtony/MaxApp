package com.bohui.utils;

public class FileTypeUtils {
    public static String formatFileSize(long size) {
        if (size < 524288L) {
            return String.format("%.2f K", (float)size / 1024.0F);
        } else {
            return size < 536870912L ? String.format("%.2f M", (float)size / 1048576.0F) : String.format("%.2f G", (float)size / 1.07374182E9F);
        }
    }
}
