package com.bohui.utils;

import android.app.Activity;
import android.content.Intent;


import com.bohui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangwei on 16/8/5.
 */
public class ActivityUtil {
    public List<Activity> smallActivitys = null;

    private ActivityUtil() {
    }

    private static ActivityUtil instance = null;

    public static ActivityUtil getInstance() {
        if (instance == null) {
            instance = new ActivityUtil();
        }
        return instance;
    }

    public void leftToRightActivity(Activity activity, Class<?> classes) {

        Intent intent = new Intent();
        intent.setClass(activity, classes);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.in_from_right,
                R.anim.out_to_left);

    }
    public void leftToRightActivity(Activity activity, Class<?> classes,String id,String type,String title,String picurl) {
        Intent intent = new Intent();
        intent.putExtra("id",id);
        intent.putExtra("type",type);
        intent.putExtra("picurl",picurl);
        intent.putExtra("title",title);
        intent.setClass(activity, classes);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.in_from_right,
                R.anim.out_to_left);

    }

    public void RightToLeftActivity(Activity activity, Class<?> classes) {

        Intent intent = new Intent();
        intent.setClass(activity, classes);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.in_from_left,
                R.anim.out_to_right);

    }

    public void RightToLeftFinised(Activity activity) {

        activity.overridePendingTransition(R.anim.in_from_left,
                R.anim.out_to_right);

    }

    public void TopToBotoomFinised(Activity activity) {

        activity.overridePendingTransition(0, R.anim.out_to_bottom);

    }

    public void BotoomToTopActivity(Activity activity, Class<?> classes) {

        Intent intent = new Intent();
        intent.setClass(activity, classes);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.in_from_bottom,
                R.anim.alpha_out);

    }

    /**
     * 跳转Activity
     *
     * @param activity
     *            源Activity
     * @param classes
     *            目标Activity
     */
    public void toActivity(Activity activity, Class<?> classes) {
        Intent intent = new Intent();
        intent.setClass(activity, classes);
        activity.startActivity(intent);
    }

    /**
     * 返回到上一个Activity
     *
     * @param activity
     */
    public void toLastActivity(Activity activity) {
        activity.finish();
    }

    /**
     * 添加少量的activity到集合中，以便统一关闭
     *
     * @param activity
     */
    public void addSmaillActivitys(Activity activity) {
        if (smallActivitys == null) {
            smallActivitys = new ArrayList<Activity>();
        }
        smallActivitys.add(activity);
    }

    /**
     * 关闭少量的Activity
     */
    public void finishSmaillActivitys() {
        if (smallActivitys != null && smallActivitys.size() > 0) {
            for (Activity ac : smallActivitys) {
                if (ac != null) {
                    ac.finish();
                }
            }
        }
    }
}
