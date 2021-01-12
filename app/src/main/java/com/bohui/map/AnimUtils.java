package com.bohui.map;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.bohui.utils.DevLog;

/**
 * 高德地图坐标点的动画
 */
public class AnimUtils {
    private Circle ac;
    private Circle c, d;
    private AMap aMap;

    public AnimUtils(AMap aMap) {
        this.aMap = aMap;
    }

    /**
     * 添加坐标点，这里可以添加任意坐标点位置
     *
     * @param mylocation
     */
    public  void addLocationMarker(Marker marker, LatLng mylocation) {
        float accuracy = (float) ((mylocation.longitude / mylocation.latitude) * 100);
        DevLog.e("测试："+accuracy);
//        if (marker == null) {
            if (ac == null) {
                DevLog.e("执行了绘制方法");
                ac = aMap.addCircle(new CircleOptions().center(mylocation)
                        .fillColor(Color.argb(0, 98, 189, 255)).radius(30)
                        .strokeColor(Color.argb(0, 98, 198, 255)).strokeWidth(0));
            }
            if (c == null) {
                c = aMap.addCircle(new CircleOptions().center(mylocation)
                        .fillColor(Color.argb(0, 98, 198, 255))
                        .radius(40).strokeColor(Color.argb(0, 98, 198, 255))
                        .strokeWidth(0));
            }
            if (d == null) {
                d = aMap.addCircle(new CircleOptions().center(mylocation)
                        .fillColor(Color.argb(0, 98, 198, 255))
                        .radius(50).strokeColor(Color.argb(0, 98, 198, 255))
                        .strokeWidth(0));
            }
        handle.postDelayed(rb, 0);
        handle1.postDelayed(rb1, 800);
        handle2.postDelayed(rb2, 1600);
    }

    /**
     * 位置波纹扩散动画
     *
     * @param ac
     */
    private void Scalecircle1(final Circle ac) {
        DevLog.e("执行了动画");
        ValueAnimator vm = ValueAnimator.ofFloat(0, (float) ac.getRadius());
        vm.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float curent = (float) animation.getAnimatedValue();
                ac.setRadius(60);
                aMap.invalidate();
            }
        });
        ValueAnimator vm1 = ValueAnimator.ofInt(160, 0);
        vm1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int color = (int) animation.getAnimatedValue();
                ac.setFillColor(Color.argb(color, 98, 198, 255));
                aMap.invalidate();
            }
        });
        vm.setRepeatCount(Integer.MAX_VALUE);
        vm.setRepeatMode(ValueAnimator.RESTART);
        vm1.setRepeatCount(Integer.MAX_VALUE);
        vm1.setRepeatMode(ValueAnimator.RESTART);
        AnimatorSet set = new AnimatorSet();
        set.play(vm).with(vm1);
        set.setDuration(2500);
        set.setInterpolator(interpolator1);
        set.start();
    }

    private final Interpolator interpolator1 = new LinearInterpolator();
    Runnable rb = new Runnable() {
        @Override
        public void run() {
            Scalecircle1(ac);
        }
    };
    Handler handle = new Handler();

    Runnable rb1 = new Runnable() {
        @Override
        public void run() {
            Scalecircle1(c);
        }
    };
    Handler handle1 = new Handler();

    Runnable rb2 = new Runnable() {
        @Override
        public void run() {
            Scalecircle1(d);
        }
    };
    Handler handle2 = new Handler();
}
