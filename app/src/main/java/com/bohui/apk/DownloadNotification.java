package com.bohui.apk;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;


import com.bohui.R;

import java.io.File;

import androidx.core.app.NotificationCompat;

/**
 * 下载apk包专用的notification
 *
 * @author tony
 */
public class DownloadNotification {
    private String id = "com.bohui.notification_notify";
    private String name = "bohui_notify";
    private static final int NOTIFICATION_ID = 0x111;
    private NotificationManager notificationManager = null;
    private Notification notification = null;
    private PendingIntent pendingIntent = null;
    private RemoteViews remoteViews;
    private Context context;

    public DownloadNotification(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public Notification initNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            // 新建通知
//			notification = new Notification(R.mipmap.ic_launcher, "正在下载",
//					System.currentTimeMillis());
//			notification.flags = Notification.FLAG_ONGOING_EVENT;
//			notification.flags = Notification.FLAG_AUTO_CANCEL;
            remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.downloadnotification_layout);
            remoteViews.setImageViewResource(R.id.downLoadIcon,
                    R.mipmap.app_icon);
            pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setChannelId(id)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.app_icon)
                    .setCustomContentView(remoteViews);
            notification = builder.build();
            return notification;
        } else {

            // 新建通知
//			notification = new Notification(R.mipmap.ic_launcher, "正在下载",
//					System.currentTimeMillis());
//			notification.flags = Notification.FLAG_ONGOING_EVENT;
//			notification.flags = Notification.FLAG_AUTO_CANCEL;
            remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.downloadnotification_layout);
            remoteViews.setImageViewResource(R.id.downLoadIcon,
                    R.mipmap.app_icon);
            pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setChannelId(id)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setSmallIcon(R.mipmap.app_icon)
                    .setCustomContentView(remoteViews);

            notification = builder.build();
            return notification;
        }

        // 是否下载成功�?

    }

    /**
     * 更新在�?知栏中更新下载进�?
     *
     * @param downFileSize 已下载文件的大小
     * @param fileSize     文件总大�?
     * @param tempProgress 下载进度中间变量
     * @param progress     下载进度
     */
    public void updateNotification(int downFileSize, int fileSize,
                                   int tempProgress, int progress) {
        synchronized (this) {
            if (downFileSize == fileSize) {
                // 下载完成
                notificationManager.cancel(R.id.downLoadIcon);
            } else if (tempProgress != progress) {
                // 下载进度发生改变，则发�?Message
                remoteViews.setTextViewText(R.id.progressPercent, progress
                        + "%");
                remoteViews.setProgressBar(R.id.downLoadProgress, 100,
                        progress, false);
                notification.contentView = remoteViews;
                notification.contentIntent = pendingIntent;
                notificationManager.notify(R.id.downLoadIcon, notification);
                tempProgress = progress;
            }
        }
    }

//	public void initNotify() {
//		notification.contentView = remoteViews;
//		notification.contentIntent = pendingIntent;
//		notificationManager.notify(R.id.downLoadIcon, notification);
//	}

    /**
     * 下载成功
     *
     * @param path
     */
    public void downLoadSuccess(File path) {
        notificationManager.cancel(R.id.downLoadIcon);
        InstallApkUtil.getInstance().install(context, path);
    }

    /**
     * 下载失败
     */
    public void downFailer() {
        Notification notificationFailer = new Notification(
                android.R.drawable.stat_sys_download_done, "下载失败，请重试",
                System.currentTimeMillis());
        notificationFailer.flags = Notification.FLAG_AUTO_CANCEL;
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                new Intent(), 0);
//		notificationFailer.setLatestEventInfo(context, "下载失败，请重试", null,
//				contentIntent);

//		notificationManager.notify(R.drawable.msg_state_fail_resend_pressed,
//				notificationFailer);
    }

    public void showNotication() {
        notificationManager.notify(NOTIFICATION_ID, initNotification());
    }

    public void showPause(String title, String time) {
        notificationManager.notify(NOTIFICATION_ID, initNotification());
    }

    public void cancelAll() {
        notificationManager.cancelAll();
    }
}
