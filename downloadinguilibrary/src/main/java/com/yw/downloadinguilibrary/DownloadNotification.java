package com.yw.downloadinguilibrary;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import java.io.File;

import androidx.annotation.RequiresApi;

/**
 * 下载apk包专用的notification
 *
 * @author tony
 */
public class DownloadNotification {
    private String channelId = "download_id";
    private String channelName = "download_apk";
    private NotificationManager notificationManager = null;
    private Notification notification = null;
    private PendingIntent pendingIntent = null;
    private RemoteViews remoteViews;
    private Context context;

    private DownloadNotification(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        initNotification();
    }

    private static DownloadNotification instance;

    public static synchronized DownloadNotification getDefault(Context context) {
        if (instance == null) {
            instance = new DownloadNotification(context);
        }
        return instance;
    }


    private void initNotification() {
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel(notificationManager);
            notification = new Notification.Builder(context, channelId).build();
        } else {
            notification = new Notification();
        }
        notification.icon = R.drawable.ic_launcher;
        notification.tickerText = "下载中";
        notification.flags = Notification.FLAG_ONGOING_EVENT;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.downloadnotification_layout);
        remoteViews.setImageViewResource(R.id.downLoadIcon,
                R.drawable.ic_launcher);
        pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel(NotificationManager notificationManager) {
        @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
        notificationManager.createNotificationChannel(channel);
    }

    /**
     * 更新在�?知栏中更新下载进�?
     *
     * @param progress 下载进度
     */
    public void updateNotification(int progress) {
        synchronized (this) {
            remoteViews.setTextViewText(R.id.progressPercent, progress
                    + "%");
            remoteViews.setProgressBar(R.id.downLoadProgress, 100,
                    progress, false);
            notification.contentView = remoteViews;
            notification.contentIntent = pendingIntent;
            notificationManager.notify(R.id.downLoadIcon, notification);
        }
    }

    /**
     * 开始通知
     */
    public void startNotify() {
        notification.contentView = remoteViews;
        notification.contentIntent = pendingIntent;
        notificationManager.notify(R.id.downLoadIcon, notification);
    }

    /**
     * 取消通知
     */
    public void cancelNotify() {
        if (notificationManager != null) {
            notificationManager.cancel(R.id.downLoadIcon);
        }
    }

}