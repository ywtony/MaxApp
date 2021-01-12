package com.bohui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;

import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.animation.MapIconAnim;
import com.bohui.bean.AlarmType;
import com.bohui.bean.GaoJingDataList;
import com.bohui.bean.Points;
import com.bohui.map.AnimUtils;
import com.bohui.utils.DevLog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * 制作一个Bitmap的工具类
 * 单独的线程加载单独的图片
 */
public class BitmapWeight {

    public void createBitmap(final MapView mapView, final String imagePath, final Points point, final AnimUtils animUtils) {
        ImageLoader.getInstance().loadImage(imagePath + point.getGifIcon(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
//                                    alarmBitmap = bitmap;
                DevLog.e("测试图片是否执行完成了:" + imagePath + point.getGifIcon());
                MarkerOptions options = new MarkerOptions();
                options.title("您好啊");
                options.anchor(0.5f, 1.0f);
                options.position(new LatLng(point.getLat(), point.getLng()));
                BitmapDescriptor bitmapDescriptor1 = BitmapDescriptorFactory.fromBitmap(bitmap);
                Bitmap bitmap1 = Bitmap.createBitmap(bitmapDescriptor1.getBitmap().getWidth(), bitmapDescriptor1.getBitmap().getHeight(), Bitmap.Config.ARGB_8888);
                BitmapDescriptor bitmapDescriptor2 = BitmapDescriptorFactory.fromBitmap(bitmap1);
                ArrayList<BitmapDescriptor> bitmaps = new ArrayList<>();
                bitmaps.add(bitmapDescriptor1);
                bitmaps.add(bitmapDescriptor2);
                options.icons(bitmaps);
                options.period(1);
                DevLog.e("执行到这里了");
                Marker marker = mapView.getMap().addMarker(options);
//                animUtils.addLocationMarker(marker, new LatLng(point.getLat(), point.getLng()));
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    public void loadImgFromNet(final MapView mapView, final String imagePath, final Points point) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer())
                .build();
        ImageLoader.getInstance().loadImage(imagePath + point.getGifIcon(), options, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
                loadImgFromNet(mapView, imagePath + point.getGifIcon(), point);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                super.onLoadingComplete(imageUri, view, bitmap);
                DevLog.e("加载静态文本图片");
                MarkerOptions options = new MarkerOptions();
                options.title("您好啊");
                options.anchor(0.5f, 1.0f);
//                    DevLog.e("不同的经纬度："+data.getLatitude()+"|"+data.getLongitude());
                options.position(new LatLng(point.getLat(), point.getLng()));
                BitmapDescriptor bitmapDescriptor1 = BitmapDescriptorFactory.fromBitmap(bitmap);
//                Bitmap bitmap1 = Bitmap.createBitmap(bitmapDescriptor1.getBitmap().getWidth(), bitmapDescriptor1.getBitmap().getHeight(), Bitmap.Config.ARGB_8888);
//                BitmapDescriptor bitmapDescriptor2 = BitmapDescriptorFactory.fromBitmap(bitmap1);
//                ArrayList<BitmapDescriptor> bitmaps = new ArrayList<>();
//                bitmaps.add(bitmapDescriptor1);
//                bitmaps.add(bitmapDescriptor2);
//                options.icons(bitmaps);
                options.icon(bitmapDescriptor1);
                options.period(1);
                Marker marker = mapView.getMap().addMarker(options);
                marker.setObject(point);
                mapView.getMap().addMarker(options);
//                markers.add(marker);
//                animUtils.addLocationMarker(marker, new LatLng(data.getLatitude(), data.getLongitude()));
//                new MapIconAnim(mapView.getMap(), marker.getPosition()).execute();
//                if (BoHuiApplication.getInstance().getLatlng() == null || "".equals(BoHuiApplication.getInstance().getLatlng())) {
//                    DevLog.e("状态为空");
//                    mapView.getMap().moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(data.getLatitude(), data.getLongitude())));
//                } else {
//                    DevLog.e("状态不为空");
//                    String[] strs = BoHuiApplication.getInstance().getLatlng().split(",");
//                    float latt = Float.parseFloat(strs[0]);
//                    float lngg = Float.parseFloat(strs[1]);
//                    mapView.getMap().moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latt, lngg)));
//
//                }

            }
        });
    }

    public void loadImageFromNet(Context context, final MapView mapView, final String imagePath, final Points point) {
//        Glide.with(context)
//                .load(imagePath+point.getGifIcon()).addListener(new RequestListener<Drawable>() {
//            @Override
//            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                return false;
//            }
//
//            @Override
//            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                DevLog.e("加载完成Glide");
//                return true;
//            }
//        });
        String url = imagePath + point.getGifIcon();
        String tail = url.substring(url.lastIndexOf(".") + 1).toLowerCase();
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(tail);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.app_icon)
                .error(R.mipmap.app_icon)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).signature(new MediaStoreSignature(type, System.currentTimeMillis(), 0));


        Glide.with(context).asBitmap().load(imagePath + point.getGifIcon()).apply(options).listener(new RequestListener<Bitmap>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                DevLog.e("加载完成Glide");
                MarkerOptions options = new MarkerOptions();
                options.title("您好啊");
                options.anchor(0.5f, 1.0f);
//                    DevLog.e("不同的经纬度："+data.getLatitude()+"|"+data.getLongitude());
                options.position(new LatLng(point.getLat(), point.getLng()));
                BitmapDescriptor bitmapDescriptor1 = BitmapDescriptorFactory.fromBitmap(resource);
//                Bitmap bitmap1 = Bitmap.createBitmap(bitmapDescriptor1.getBitmap().getWidth(), bitmapDescriptor1.getBitmap().getHeight(), Bitmap.Config.ARGB_8888);
//                BitmapDescriptor bitmapDescriptor2 = BitmapDescriptorFactory.fromBitmap(bitmap1);
//                ArrayList<BitmapDescriptor> bitmaps = new ArrayList<>();
//                bitmaps.add(bitmapDescriptor1);
//                bitmaps.add(bitmapDescriptor2);
//                options.icons(bitmaps);
                options.icon(bitmapDescriptor1);
                options.period(1);
                Marker marker = mapView.getMap().addMarker(options);
                marker.setObject(point);
                mapView.getMap().addMarker(options);
                return true;
            }
        }).submit();

    }

    /**
     * 绘制地图上坐标点上的图片
     *
     * @param context
     * @param mapView
     * @param imagePath
     * @param point
     * @param animUtils
     */
    public void loadImage(Context context, final MapView mapView, String imagePath, final Points point, final AnimUtils animUtils) {
        DevLog.e("测试图片是否执行完成了");
        MarkerOptions options = new MarkerOptions();
        options.title("您好啊");
        options.anchor(0.5f, 1.0f);
        options.position(new LatLng(point.getLat(), point.getLng()));
        View view = LayoutInflater.from(context).inflate(R.layout.loadgif_layout, null);
        ImageViewGif gifImg = view.findViewById(R.id.gif_img);
        gifImg.loadGif(imagePath);
        options.icon(BitmapDescriptorFactory.fromView(view));
//        options.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
        Marker marker = mapView.getMap().addMarker(options);
//        animUtils.addLocationMarker(marker, new LatLng(point.getLat(), point.getLng()));
    }

    public void loadAlarmImages(Context context, MapView mapView, String gifPath, GaoJingDataList data, List<Marker> markers) {
        DevLog.e("加载告警。。。。。");
        MarkerOptions options = new MarkerOptions();
        options.title("您好啊");
        options.anchor(0.5f, 1.0f);
//                    DevLog.e("不同的经纬度："+data.getLatitude()+"|"+data.getLongitude());
        options.position(new LatLng(data.getLatitude(), data.getLongitude()));
        View view = LayoutInflater.from(context).inflate(R.layout.loadgif_layout, null);
        ImageViewGif gifImg = view.findViewById(R.id.gif_img);
        gifImg.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        gifImg.loadGif(gifPath);
        options.icon(BitmapDescriptorFactory.fromBitmap(BitmapDescriptorFactory.fromView(view).getBitmap()));
        Marker marker = mapView.getMap().addMarker(options);
        marker.setObject(data);
        markers.add(marker);
//                animUtils.addLocationMarker(marker, new LatLng(data.getLatitude(), data.getLongitude()));
        new MapIconAnim(mapView.getMap(), marker.getPosition()).execute();
        mapView.getMap().moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(data.getLatitude(), data.getLongitude())));

    }
}
