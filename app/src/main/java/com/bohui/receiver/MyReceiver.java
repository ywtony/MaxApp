package com.bohui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.json.JSONException;

import cn.jpush.android.api.JPushInterface;

/**
 * create by yangwei
 * on 2020/12/21 22:35
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            LogUtil.log("执行了没有啊");
            Bundle bundle = intent.getExtras();
            switch (intent.getAction()) {
                //注册ID
                case JPushInterface.ACTION_REGISTRATION_ID: {
                    String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                    LogUtil.log("注册ID：$regId");
                }
                break;
                //链接
                case JPushInterface.ACTION_CONNECTION_CHANGE: {
                    boolean bool = bundle.getBoolean(JPushInterface.ACTION_CONNECTION_CHANGE);
                    LogUtil.log("链接状态：$bool");
                }
                break;
                //收到自定义消息
                case JPushInterface.ACTION_MESSAGE_RECEIVED: {
                    String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
                    LogUtil.log("自定义消息内容：$message" + message);
                    LogUtil.log(parseAllMessage(bundle));
                }
                break;
                //收到通知栏消息
                case JPushInterface.ACTION_NOTIFICATION_RECEIVED: {
                    int notificationId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                    LogUtil.log("通知Id：$notificationId");
                }
                break;
                //用户点开通知栏消息
                case JPushInterface.ACTION_NOTIFICATION_OPENED: {
                    LogUtil.log("用户打开了通知栏通知");
                }
                break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 解析推送过来的所有数据
     */
    private String parseAllMessage(Bundle bundle) {
//        StringBuffer sb = new StringBuilder();
//        bundle.keySet().forEach { it ->
//                when (it) {
//            JPushInterface.EXTRA_NOTIFICATION_ID -> {
//                sb.append("key:$it,value:${bundle.getInt(it)}")
//            }
////                JPushInterface.EXTRA_CONNECTION_CHANGE -> {
////                    sb.append("key:$it,value:${bundle.getInt(it)}")
////                }
//            JPushInterface.EXTRA_EXTRA -> {
//                var extra: String? = bundle?.getString(JPushInterface.EXTRA_EXTRA)
//                if (extra.isNullOrEmpty()) {
//                    LogUtil.log("This message has no Extra data")
//                    return@forEach
//                }
//                try {
//                    var json = JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA))
//                    json.keys().forEach {
//                        sb.append("key:$it,value:[$it - ${json.optString(it)}]")
//                    }
//                } catch (e:JSONException) {
//                    LogUtil.log("Get message extra JSON error!")
//                }
//            }
//            //默认
//                else -> {
//                sb.append("key:$it,value:$bundle.get(it)")
//            }
//        }
//        }
        return "sb.toString()";

    }
}
