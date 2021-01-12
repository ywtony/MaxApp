package com.bohui.receiver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.bohui.R;
import com.bohui.utils.ValidUtils;


import androidx.core.app.NotificationCompat;


import cn.jpush.android.api.CustomMessage;

/**
 * 通知工具类
 * create by yangwei
 * on 2020/8/9 14:21
 */
public class NotificationUtils {
    /**
     * 显示推送来的消息
     *
     * @param context ctx
     */
    public static void showNotification(Context context, CustomMessage customMessage, boolean offlineChannel) {
        new Handler().postDelayed(() -> {
            String channelId = "1";
            String channelName = "channel_name";
            // 跳转的Activity
            Intent intent = new Intent();
            if (!ValidUtils.isValid(customMessage)) {
                return;
            }

            //这个地方
            setNotification(context, intent, channelId, channelName, customMessage);
        }, 1000);

    }

    private static void setNotification(Context context, Intent intent, String channelId, String channelName, CustomMessage pushMessage) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }
        //适配安卓8.0的消息渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, channelId);

        if (ValidUtils.isValid(pushMessage)) {
            String title = "来自" + context.getString(R.string.app_name) + "的推送";
            if (ValidUtils.isValid(pushMessage.title)) {
                title = pushMessage.title;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                Log.e("测试横幅通知是否有效：", "进入了");
                //自动取消
                notification
                        .setAutoCancel(true)
                        .setContentText(pushMessage.message)
                        .setContentTitle(title)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_CALL)
                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setDefaults(Notification.DEFAULT_ALL)
                        .setFullScreenIntent(pendingIntent, true);

            }
            notificationManager.notify((int) (System.currentTimeMillis() / 1000), notification.build());
        }
    }


}
