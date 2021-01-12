package com.bohui.video;


import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;


import com.bohui.utils.DevLog;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

/**
 * VLC自定义视频播放器
 * Created by yangwei on 2018/9/18.
 */
public class VideoPlayerUtils {
    private Context context;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mediaPlayer;
    private IVLCVout ivlcVout;
    private LibVLC libvlc;
    private Media media;
    private long totalTime = 0;
    private int videoWidth;//视频的宽度
    private int videoHight;//视频的高度

    public VideoPlayerUtils(Context context, SurfaceView surfaceView, int videoWidth, int videoHight) {
        this.surfaceView = surfaceView;
        this.context = context;
        this.videoWidth = videoWidth;
        this.videoHight = videoHight;
    }

    /**
     * 初始化MediaPlayer
     */
    public void initPlayer(Context context) {

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        libvlc = LibVLCUtil.getLibVLC(context, null);
        surfaceHolder.setKeepScreenOn(true);
        mediaPlayer = new MediaPlayer(libvlc);
        ivlcVout = mediaPlayer.getVLCVout();
        ivlcVout.setVideoView(surfaceView);
        ivlcVout.attachViews(onNewVideoLayoutListener);
        ivlcVout.addCallback(callback);
        ivlcVout.setWindowSize(videoWidth,videoHight);


    }

    /**
     * 设置播放路径播放音视频流
     *
     * @param playUrl
     */
    public void playVideo(String playUrl) {
        media = new Media(libvlc, Uri.parse(playUrl));//播放地址
        mediaPlayer.setMedia(media);
        mediaPlayer.setEventListener(eventListener);
        mediaPlayer.play();
    }

    public void onPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            if (videoPlayerCallback != null) {
                videoPlayerCallback.onPause(null);
            }
        }

        ivlcVout.detachViews();
        ivlcVout.removeCallback(callback);
        mediaPlayer.setEventListener(null);
    }

    public void onResume() {
        if(mediaPlayer!=null&&!mediaPlayer.isPlaying()){
            mediaPlayer.play();
        }
        ivlcVout.setVideoView(surfaceView);
        ivlcVout.attachViews(onNewVideoLayoutListener);
        ivlcVout.addCallback(callback);
//        ivlcVout.addCallback(callback);
        mediaPlayer.setEventListener(eventListener);
        if (videoPlayerCallback != null) {
            videoPlayerCallback.onResume(null);
        }
    }

    public void onDestory() {
        onPause();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    MediaPlayer.EventListener eventListener = new MediaPlayer.EventListener() {
        @Override
        public void onEvent(MediaPlayer.Event event) {
            try {
                if (event.getTimeChanged() == 0 || totalTime == 0 || event.getTimeChanged() > totalTime) {
                    return;
                }
                //播放结束
                if (mediaPlayer.getPlayerState() == Media.State.Ended) {
                    mediaPlayer.stop();
                }
            } catch (Exception e) {
                Log.d("vlc-event", e.toString());
            }

        }
    };
    IVLCVout.OnNewVideoLayoutListener onNewVideoLayoutListener = new IVLCVout.OnNewVideoLayoutListener() {
        @Override
        public void onNewVideoLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
            try {
                totalTime = mediaPlayer.getLength();
//                videoWidth = 400;
//                videoHight = 400;
                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                Display display = windowManager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                ViewGroup.LayoutParams layoutParams = surfaceView.getLayoutParams();
//                layoutParams.width = point.x;
//                layoutParams.height = (int) Math.ceil((float) videoHight * (float) point.x / (float) videoWidth);
                layoutParams.width = videoWidth;
                layoutParams.height = videoHight;
                surfaceView.setLayoutParams(layoutParams);

            } catch (Exception e) {
                Log.d("vlc-newlayout", e.toString());
            }
        }
    };
    IVLCVout.Callback callback = new IVLCVout.Callback() {
        @Override
        public void onSurfacesCreated(IVLCVout vlcVout) {
            DevLog.e("创建了surfaceview");
        }

        @Override
        public void onSurfacesDestroyed(IVLCVout vlcVout) {
            DevLog.e("surfaceview销毁了");
        }
    };
//    IVLCVout.Callback callback = new IVLCVout.Callback() {
//
//        @Override
//        public void onNewLayout(IVLCVout ivlcVout, int i, int i1, int i2, int i3, int i4, int i5) {
//            try {
//                totalTime = mediaPlayer.getLength();
//                videoWidth = i;
//                videoHight = i1;
//                WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//                Display display = windowManager.getDefaultDisplay();
//                Point point = new Point();
//                display.getSize(point);
//                ViewGroup.LayoutParams layoutParams = surfaceView.getLayoutParams();
//                layoutParams.width = point.x;
//                layoutParams.height = (int) Math.ceil((float) videoHight * (float) point.x / (float) videoWidth);
//                surfaceView.setLayoutParams(layoutParams);
//            } catch (Exception e) {
//                Log.d("vlc-newlayout", e.toString());
//            }
//        }
//
//        @Override
//        public void onSurfacesCreated(IVLCVout ivlcVout) {
//
//        }
//
//        @Override
//        public void onSurfacesDestroyed(IVLCVout ivlcVout) {
//
//        }
//    };

    /**
     * 播放器的回调接口
     */
    public interface VideoPlayerCallback {
        void onResume(Object obj);

        void onPause(Object obj);
    }

    private VideoPlayerCallback videoPlayerCallback;

    public void setVideoPlayerCallback(VideoPlayerCallback videoPlayerCallback) {
        this.videoPlayerCallback = videoPlayerCallback;
    }
}
