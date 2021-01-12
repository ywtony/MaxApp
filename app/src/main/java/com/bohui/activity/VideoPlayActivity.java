package com.bohui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.bohui.R;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.DevLog;
import com.bohui.utils.Screen;
import com.bohui.video.VideoPlayerUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 播放remp视频
 */
public class VideoPlayActivity extends Activity implements VideoPlayerUtils.VideoPlayerCallback {
    @BindView(R.id.videoplayer)
    SurfaceView surfaceView;
    private VideoPlayerUtils videoPlayerUtils;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videoplayer_layout);
        ButterKnife.bind(this);
        ActivityUtil.getInstance().addSmaillActivitys(this);
        url = getIntent().getStringExtra("url");
//        url  = "rtsp://admin:bw123456@222.72.158.166:60009";
        DevLog.e("url-----:" + url);

    }



    private void play(String url ) {
        int width = Screen.getWidthPixels(this);
        int height = Screen.getHeightPixels(this);
        videoPlayerUtils = new VideoPlayerUtils(this, surfaceView, width, height);
        videoPlayerUtils.setVideoPlayerCallback(this);

        try {
            //测试播放
//            String url = "rtmp://pull.yingerxiang.com/huadong/videoYDA10118070100032645?token=262f136af0ccfd68cce4b03b9ec08eda&time=139";

            DevLog.e("width------:" + width + "height:" + height);
            videoPlayerUtils.initPlayer(this);
            videoPlayerUtils.playVideo(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume(Object obj) {

    }

    @Override
    public void onPause(Object obj) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        play(url);
    }

    @Override
    protected void onStop() {
        super.onStop();
        DevLog.e("onstop:adhasdfasdfalsdfjasdfja");
        videoPlayerUtils.onDestory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
