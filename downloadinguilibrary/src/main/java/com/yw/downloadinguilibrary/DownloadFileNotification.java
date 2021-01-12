package com.yw.downloadinguilibrary;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;


/**
 * 下载apk包专用的notification
 * 
 * @author tony
 * 
 */
public class DownloadFileNotification {
	private NotificationManager notificationManager = null;
	private Notification notification = null;
	private PendingIntent pendingIntent = null;
	private RemoteViews remoteViews;
	private Context context;

	public DownloadFileNotification(Context context) {
		this.context = context;
		notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void initNotification(int logo) {
		// 新建通知
		notification = new Notification(logo, "正在下载", System.currentTimeMillis());
		notification.flags = Notification.FLAG_ONGOING_EVENT;
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		remoteViews = new RemoteViews(context.getPackageName(), R.layout.downloadnotification_layout);
		remoteViews.setImageViewResource(R.id.downLoadIcon, logo);
		pendingIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
		// 是否下载成功�?

	}

	/**
	 * 更新在�?知栏中更新下载进�?
	 * 
	 * @param downFileSize
	 *            已下载文件的大小
	 * @param fileSize
	 *            文件总大�?
	 * @param tempProgress
	 *            下载进度中间变量
	 * @param progress
	 *            下载进度
	 */
	public void updateNotification(String name,int downFileSize, int fileSize, int tempProgress, int progress) {
		String fileName = null;
		if(name==null){
			fileName = "正在下载...";
		}else{
			fileName = "正在下载“"+name+"”";
		}
		synchronized (this) {
			if (downFileSize == fileSize) {
				// 下载完成
				notificationManager.cancel(fileSize);
			} else if (tempProgress != progress) {
				// 下载进度发生改变，则发�?Message
				remoteViews.setTextViewText(R.id.downloadtext,fileName);
				remoteViews.setTextViewText(R.id.progressPercent, progress + "%");
				remoteViews.setProgressBar(R.id.downLoadProgress, 100, progress, false);
				notification.contentView = remoteViews;
				notification.contentIntent = pendingIntent;
				notificationManager.notify(fileSize, notification);
				tempProgress = progress;
			}
		}
	}

	public void initNotify(int fileSize,String name) {
		String fileName = null;
		if(name==null){
			fileName = "正在下载...";
		}else{
			fileName = "正在下载“"+name+"”";
		}
		remoteViews.setTextViewText(R.id.downloadtext,fileName);
		remoteViews.setTextViewText(R.id.progressPercent, 2 + "%");
		remoteViews.setProgressBar(R.id.downLoadProgress, 100, 2, false);
		notification.contentView = remoteViews;
		notification.contentIntent = pendingIntent;
		notificationManager.notify(fileSize, notification);
	}

	/**
	 * 下载成功
	 * 
	 * @param
	 */
	public void downLoadSuccess(int filesize) {
		notificationManager.cancel(filesize);
		// InstallApkUtil.getInstance().install(context, path);
	}

	/**
	 * 下载失败
	 */
	public void downFailer(int downFail) {
		Notification notificationFailer = new Notification(android.R.drawable.stat_sys_download_done, "下载失败，请重试",
				System.currentTimeMillis());
		notificationFailer.flags = Notification.FLAG_AUTO_CANCEL;
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
		// notificationFailer.setLatestEventInfo(context, "下载失败，请重试", null,
		// contentIntent);
		
		notificationManager.notify(downFail, notificationFailer);
	}
}
