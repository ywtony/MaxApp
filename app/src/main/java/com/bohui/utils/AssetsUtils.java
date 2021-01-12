package com.bohui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;

import com.bohui.config.Config;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 获取资源文件
 */
public class AssetsUtils {
    private AssetsUtils() {
    }

    private static AssetsUtils instance;

    public static AssetsUtils getInstance() {
        if (instance == null) {
            instance = new AssetsUtils();
        }
        return instance;
    }

    /**
     * 获取assets中的jsonobj对象
     *
     * @param context
     * @param fileName
     * @return
     */
    public JSONObject getJsonObjFile(Context context, String fileName) {
        try {
            String json = readAssertResource(context, fileName);
            JSONObject arrs = new JSONObject(json);
            return arrs;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获取assets中的objectarr对象
     *
     * @param context
     * @param fileName
     * @return
     */
    public JSONArray getJsonArrsFile(Context context, String fileName) {
        try {
            String json = readAssertResource(context, fileName);
            JSONArray arrs = new JSONArray(json);
            return arrs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从assert文件夹中获取文件并转换String
     *
     * @param context
     * @param strAssertFileName
     * @return
     */
    public String readAssertResource(Context context, String strAssertFileName) {
        AssetManager assetManager = context.getAssets();
        String strResponse = "";
        try {
            InputStream ims = assetManager.open(strAssertFileName);
            strResponse = getStringFromInputStream(ims);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    private String getStringFromInputStream(InputStream a_is) {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try {
            br = new BufferedReader(new InputStreamReader(a_is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
        return sb.toString();
    }
}
