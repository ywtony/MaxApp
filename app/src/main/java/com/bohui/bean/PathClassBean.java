package com.bohui.bean;

import java.io.Serializable;

/**
 * create by yangwei
 * on 2020-01-03 14:43
 */
public class PathClassBean implements Serializable {
    private String Path;
    private String Size;

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }
}
