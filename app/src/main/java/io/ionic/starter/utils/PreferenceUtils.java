package io.ionic.starter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Administrator on 2017-08-09.
 */

public class PreferenceUtils {
    private Context context;

    public PreferenceUtils(Context context) {
        this.context = context;
    }

    public void savename(String name, String pass) {
        SharedPreferences preferences = context.getSharedPreferences("dataContext", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("name", name);
        editor.putString("pass", pass);
        editor.commit();
    }


    public String getname() {
        String name;
        SharedPreferences preferences = context.getSharedPreferences("dataContext", Context.MODE_PRIVATE);
        name = preferences.getString("name", null);
        return name;
    }

    public String getpass() {
        String pass;
        SharedPreferences preferences = context.getSharedPreferences("dataContext", Context.MODE_PRIVATE);
        pass = preferences.getString("pass", null);
        return pass;
    }

    public void saveUser(String user, Map<String, Object> map) {
        // 一般Mode都使用private,比较安全
        SharedPreferences preferences = context.getSharedPreferences(user,
                Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        // Map类提供了一个称为entrySet()的方法，这个方法返回一个Map.Entry实例化后的对象集。
        // 接着，Map.Entry类提供了一个getKey()方法和一个getValue()方法，
        // 因此，上面的代码可以被组织得更符合逻辑
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object object = entry.getValue();
            // 根据值得不同类型，添加
            if (object instanceof Boolean) {
                Boolean new_name = (Boolean) object;
                editor.putBoolean(key, new_name);
            } else if (object instanceof Integer) {
                Integer integer = (Integer) object;
                editor.putInt(key, integer);
            } else if (object instanceof Float) {
                Float f = (Float) object;
                editor.putFloat(key, f);
            } else if (object instanceof Long) {
                Long l = (Long) object;
                editor.putLong(key, l);
            } else if (object instanceof String) {
                String s = (String) object;
                editor.putString(key, s);
            }
        }
        editor.commit();
    }

    // 读取数据
    public Map<String, ?> getUser(String fileName) {
        Map<String, ?> map = null;
        // 读取数据用不到edit
        SharedPreferences preferences = context.getSharedPreferences(fileName,
                Context.MODE_APPEND);
        //Context.MODE_APPEND可以对已存在的值进行修改
        map = preferences.getAll();
        return map;
    }

    /**
     * 模拟设备唯一ID
     */
    public void saveUUID() {
        SharedPreferences preferences = context.getSharedPreferences("dataContext", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString("uuid", UUID.randomUUID().toString());
        editor.commit();
    }


    public String getUUID() {
        String name;
        SharedPreferences preferences = context.getSharedPreferences("dataContext", Context.MODE_PRIVATE);
        name = preferences.getString("uuid", null);
        return name;
    }

}
