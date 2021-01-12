package com.bohui.animation;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.bohui.activity.TestActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 告警动画
 */
public class MapIconAnim {
    private Circle ac;
    private Circle c;
    private long start;
    private circleTask mTimerTask;
    private AMap aMap;
    private LatLng mylocation;

    public MapIconAnim(AMap aMap, LatLng mylocation) {
        this.mylocation = mylocation;
        this.aMap = aMap;
    }

    private final Interpolator interpolator1 = new LinearInterpolator();
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LatLng mylocation = new LatLng(31.044252, 121.252102);
            addLocationMarker();
        }
    };


    private Timer timer = new Timer();

    /**
     * 开始动画效果
     */
    public void execute() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(1);
            }
        }, 3, 1000);
    }


    private void addLocationMarker() {
//        LatLng mylocation = new LatLng(31.044252, 121.252102);
//        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mylocation, 14.3f), 300, null);
        float accuracy = 120;
        if (ac == null) {
            ac = aMap.addCircle(new CircleOptions().center(mylocation)
                    .fillColor(Color.argb(0, 98, 198, 255)).radius(accuracy)
                    .strokeColor(Color.argb(0, 98, 198, 255)).strokeWidth(5));
        } else {
            ac.setCenter(mylocation);
            ac.setRadius(accuracy);
        }
        if (c == null) {
            c = aMap.addCircle(new CircleOptions().center(mylocation)
                    .fillColor(Color.argb(120, 244, 154, 193))
                    .radius(accuracy).strokeColor(Color.argb(120, 244, 154, 193))
                    .strokeWidth(0));
        } else {
            c.setCenter(mylocation);
            c.setRadius(accuracy);
        }
        Scalecircle(c);
    }

    public void Scalecircle(final Circle circle) {
        start = SystemClock.uptimeMillis();
        mTimerTask = new circleTask(circle, 1000);
        timer.schedule(mTimerTask, 0, 30);
    }


    private class circleTask extends TimerTask {
        private double r;
        private Circle circle;
        private long duration = 1000;

        public circleTask(Circle circle, long rate) {
            this.circle = circle;
            this.r = circle.getRadius();
            if (rate > 0) {
                this.duration = rate;
            }
        }

        @Override
        public void run() {
            try {
                long elapsed = SystemClock.uptimeMillis() - start;
                float input = (float) elapsed / duration;
//                外圈循环缩放
//                float t = interpolator.getInterpolation((float)(input-0.25));//return (float)(Math.sin(2 * mCycles * Math.PI * input))
//                double r1 = (t + 2) * r;
//                外圈放大后消失
                float t = interpolator1.getInterpolation(input);
                double r1 = (t + 1) * r;
                circle.setRadius(r1);
                if (input > 2) {
                    start = SystemClock.uptimeMillis();
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
