package com.bohui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bohui.utils.DevLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import androidx.annotation.Nullable;

/**
 * 用于显示Gif的ImageView
 */
@SuppressLint("AppCompatCustomView")
public class ImageViewGif extends ImageView {
    public ImageViewGif(Context context) {
        super(context);
    }

    public ImageViewGif(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 加载gif动画
     *
     * @param gifPath
     */
    public void loadGif(String gifPath) {
        //http://222.72.158.166:60008/images/alarmImage/mdig_red.gif
        DevLog.e("gifPaht:"+gifPath);
        Glide.with(this).load(gifPath).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                DevLog.e("gif加载失败");
                return false;

            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                DevLog.e("gif加载完成");
                return false;
            }
        }).into(ImageViewGif.this);
    }
}
