package com.bohui.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.maps2d.model.TextOptions;
import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.bean.AlarmType;
import com.bohui.bean.ConfigBean;
import com.bohui.bean.ExAttrs;
import com.bohui.bean.GMapBean;
import com.bohui.bean.GaoJingDataList;
import com.bohui.bean.GaoJingUpdateBean;
import com.bohui.bean.MapBean;
import com.bohui.bean.MessageEvent;
import com.bohui.bean.Points;
import com.bohui.bean.PolyLines_Child;
import com.bohui.bean.Polylines;
import com.bohui.bean.ZJPoint;
import com.bohui.model.GetAlarmMsgModel;
import com.bohui.model.GetUnclearedAlarmsByMeasObjIDsModel;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.DevLog;
import com.bohui.utils.JSONUtil;
import com.bohui.view.IGaoJingView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 高德地图组件
 */
public class GMapViewActivity extends Activity {
    @BindView(R.id.mapview)
    MapView mapView;
    @BindView(R.id.clearmapview)
    Button btn_clear;
    private MapBean gMapBean;
    private Bitmap alarmBitmap;
    private List<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gmapview_layout);
        ActivityUtil.getInstance().addSmaillActivitys(this);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        String json = getIntent().getStringExtra("data");
        if (json != null) {
            gMapBean = (MapBean) JSONUtil.getInstance().getObject(json, MapBean.class);
        }
        if (gMapBean != null) {
            double lat = Double.parseDouble(gMapBean.getMapCenterLat());
            double lon = Double.parseDouble(gMapBean.getMapCenterLng());
            mapView.getMap().moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lon)));
            mapView.getMap().moveCamera(CameraUpdateFactory.zoomTo(gMapBean.getMapInitialZoom()));
            initLine(gMapBean);
            initPoints(gMapBean);

        }
        /**
         * 监听地图的缩放级别
         * 初始化默认级别
         *
         *
         *
         *
         *
         *
         */
        mapView.getMap().setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                int zoom = (int) cameraPosition.zoom;

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

            }
        });

//        mapView.getMap().getCameraPosition().zoom;


//gMapBean.getMeasObjID()
//        getAlarmMsg();
        getFirstRefreshAlarm();
//        startAlarmTimer();
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mapView.getMap().clear();
                markers.get(0).remove();
            }
        });
    }

    private void initLine(MapBean mapBean) {
        List<LatLng> latLngs = new ArrayList<>();
        if (mapBean.getPolylines() != null) {
            List<Polylines> datas = mapBean.getPolylines();
            for (int i = 0; i < datas.size(); i++) {
                List<PolyLines_Child> childs = datas.get(i).getArealist();
                if (childs != null) {
                    for (int j = 0; j < childs.size(); j++) {
                        double startLat = Double.parseDouble(childs.get(j).getStartlatitude());
                        double startLon = Double.parseDouble(childs.get(j).getStartlongitude());
                        latLngs.add(new LatLng(startLat, startLon));
                        double endLat = Double.parseDouble(childs.get(j).getEndlatitude());
                        double endLon = Double.parseDouble(childs.get(j).getEndlongitude());
                        latLngs.add(new LatLng(endLat, endLon));

                    }
                }
            }
        }

        Polyline polyline = mapView.getMap().addPolyline(new PolylineOptions().
                addAll(latLngs).width(mapBean.getPolylines().get(0).getStrokeweight()).color(Color.parseColor(mapBean.getPolylines().get(0).getColor())));
//        mapBean.getPolylines().get(0).getColor();//绘制线的颜色
    }

    private void initPoints(final MapBean mapBean) {
        List<LatLng> latLngs = new ArrayList<>();
        if (mapBean.getPoints() != null) {
            List<Points> datas = mapBean.getPoints();
            for (int i = 0; i < datas.size(); i++) {
                final Points point = datas.get(i);
                if (point != null) {
                    double lat = point.getLat();
                    double lng = point.getLng();
                    LatLng latLng = new LatLng(lat, lng);
                    if (point.getType().equals("Stl")) {
                        mapView.getMap().addText(new TextOptions().text(point.getText()).position(latLng).fontColor(Color.parseColor(point.getFontColor())).fontSize(point.getFontSize()).backgroundColor(Color.parseColor(point.getBackColor())));
                    } else if (point.getType().equals("Stp")) {
                        ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
                        String imagePath = configBean.getIp() + "/";
                        DevLog.e("points：" + imagePath + point.getGifIcon());
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
                                MarkerOptions options = new MarkerOptions();
                                options.anchor(0.5f, 1.0f);
                                options.position(new LatLng(point.getLat(), point.getLng()));
                                options.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                                Marker marker = mapView.getMap().addMarker(options);


                            }

                            @Override
                            public void onLoadingCancelled(String s, View view) {

                            }
                        });
                    }


                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mapView.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 获取告警信息_定时刷新告警
     * 地图上需要展示告警信息
     * 1.如果收到告警，就在地图上显示一张图片
     * 2.如果告警被移除了就删除这条告警
     */
    private void getAlarmMsg() {
        final List<Integer> objids = new ArrayList<>();
        if (gMapBean != null) {
            for (int i = 0; i < gMapBean.getPolylines().size(); i++) {
                objids.add(gMapBean.getPolylines().get(i).getDataID());
            }
            for (int i = 0; i < gMapBean.getPoints().size(); i++) {
                objids.add(Integer.parseInt(gMapBean.getPoints().get(i).getDataID()));
            }
        }
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new GetAlarmMsgModel().getAlarmMsgData(this, id, new IGaoJingView() {
            @Override
            public void success(String msg) {
                if (!"[]".equals(msg)) {
                    Gson gson = new Gson();
                    List<GaoJingUpdateBean> datas = gson.fromJson(msg.toString(),
                            new TypeToken<List<GaoJingUpdateBean>>() {
                            }.getType());
                    if (datas != null) {

                        for (int i = 0; i < datas.size(); i++) {
                            GaoJingUpdateBean data = datas.get(i);
                            if (data != null) {
                                DevLog.e("告警信息 null" + data.getAlarmMsgType());
                            } else {
                                DevLog.e("告警信息 null" + data.getAlarmMsgType());
                            }
                            DevLog.e("告警的objid:" + datas.get(i).getAlarm().getMeasObjID() + "|" + data.getAlarmMsgType() + "|" + datas.size());
                            if (data.getAlarmMsgType() == 1) {//新告警
                                for (int j = 0; j < objids.size(); j++) {
                                    if (objids.get(j) == data.getAlarm().getMeasObjID()) {
                                        showAlarm(data.getAlarm(), data.getAlarmMsgType());
                                    }
                                }

                            } else if (data.getAlarmMsgType() == 3) {//确认告警
                                DevLog.e("查看日志信息：" + markers.size() + "|");
                                for (int j = 0; j < markers.size(); j++) {
                                    GaoJingDataList gjd = (GaoJingDataList) markers.get(j).getObject();
                                    for (int k = 0; k < objids.size(); k++) {
                                        DevLog.e("展示基础信息：" + gjd.getMeasObjID() + "|" + objids.get(k));
                                        if (gjd.getMeasObjID() == objids.get(k)) {
                                            markers.get(j).remove();
                                        }
                                    }

                                }
                            }
                        }
                    }
                }

            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    /**
     * 获取第一次进入地图页面时的告警
     */
    private void getFirstRefreshAlarm() {
        StringBuffer objIds = new StringBuffer();
        if (gMapBean != null) {
            for (int i = 0; i < gMapBean.getPolylines().size(); i++) {

                objIds.append(gMapBean.getPolylines().get(i).getDataID());
                objIds.append(",");
            }
            for (int i = 0; i < gMapBean.getPoints().size(); i++) {
                String dataId = gMapBean.getPoints().get(i).getDataID();
                if (dataId != null && !"".equals(dataId)) {
                    objIds.append(gMapBean.getPoints().get(i).getDataID());
                    objIds.append(",");
                }

            }
            if (objIds.length() > 0) {
                objIds.deleteCharAt(objIds.length() - 1);
            }
        }
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new GetUnclearedAlarmsByMeasObjIDsModel().getGetUnclearedAlarmsByMeasObjIDsData(this, id, objIds.toString(), new IGaoJingView() {
            @Override
            public void success(String msg) {
                DevLog.e("获取到了页面元素数据：" + msg);
                if (!"[]".equals(msg)) {
                    DevLog.e("获取到了");
                    Gson gson = new Gson();
                    List<GaoJingDataList> datas = gson.fromJson(msg.toString(),
                            new TypeToken<List<GaoJingDataList>>() {
                            }.getType());
                    if (datas != null) {
                        for (int i = 0; i < datas.size(); i++) {
                            GaoJingDataList data = datas.get(i);
//                            if (gMapBean.getMeasObjID() != null && !"".equals(gMapBean.getMeasObjID())) {
//                                String[] arr = gMapBean.getMeasObjID().split(",");
//                                if (Integer.parseInt(arr[0]) == datas.get(i).getMeasObjID()) {
                            DevLog.e("找到与之匹配的告警信息");
                            gMapBean.setAlarmLevel(data.getAlarmSeverity());
                            gMapBean.setAlarmSource(data.getAlarmSource());
                            gMapBean.setAlarmType(data.getAlarmType());
                            gMapBean.setGaojing(data);
                            showAlarm(data, -1);
//                                }
//                            }

                        }
//                        showAlarm(gMapBean);

                    }
                }
            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    /**
     * 显示地图告警
     */
    private void showAlarm(GaoJingDataList data, int alarmType) {
        List<AlarmType> ats = BoHuiApplication.getInstance().getDatas();

        if (ats != null) {
            for (AlarmType at : ats) {
                //找到与之匹配的数据
                if (at.getID().equals(gMapBean.getAlarmType().getID()) && at.getObjTypeID() == gMapBean.getAlarmType().getObjTypeID()) {
                    setAlramTypeBitmap(at, data, alarmType);
                }
            }

        } else {
            DevLog.e("告警列表信息为空");
        }


    }

    /**
     * 异步加载图片后设置在指定的位置
     *
     * @param at
     */
    private void setAlramTypeBitmap(AlarmType at, final GaoJingDataList data, final int alarmType) {
        if (at.getAlarmImage() != null && !"".equals(at.getAlarmImage())) {//如果告警图片不为空，就把告警图片下载并绘制到地图上
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
            String imagePath = configBean.getIp() + "/";
            DevLog.e("地图告警信息：" + imagePath + at.getAlarmImage());
            ImageLoader.getInstance().loadImage(imagePath + at.getAlarmImage(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    alarmBitmap = bitmap;
                    MarkerOptions options = new MarkerOptions();
                    options.anchor(0.5f, 1.0f);
                    options.position(new LatLng(data.getLatitude(), data.getLongitude()));
                    options.icon(BitmapDescriptorFactory.fromBitmap(alarmBitmap));
                    Marker marker = mapView.getMap().addMarker(options);
                    marker.setObject(data);
                    markers.add(marker);


//                    mapView.getMap().addMarker(null).remove();
//                    marker.setObject(point);
//                    markers.add(marker);

                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        } else {
            int alarmLevel = at.getAlarmSeverity();//告警级别
            String color = null;
            if (alarmLevel > 0) {
                switch (alarmLevel) {//根据告警级别来绘制不同颜色的线段
                    case 1:
                        color = "#0066ff";
                        break;
                    case 2:
                        color = "#ffff00";
                        break;
                    case 3:
                        color = "#ffae00";
                        break;
                    case 4:
                        color = "#ff0000";
                        break;
                }
            } else {
//                color = exAttrs1.getStroke();
            }
            LatLng latLng = new LatLng(data.getLatitude(), data.getLongitude());
//            mapView.getMap().addCircle(new CircleOptions().
//                    center(latLng).
//                    radius(20).
//                    fillColor(Color.parseColor(color)).
//                    strokeColor(Color.parseColor(color)).
//                    strokeWidth(15));
            MarkerOptions options = new MarkerOptions();
            options.anchor(0.5f, 1.0f);
            options.position(new LatLng(data.getLatitude(), data.getLongitude()));
            View view = LayoutInflater.from(GMapViewActivity.this).inflate(R.layout.marker_layout, null);
            TextView tv_bg = view.findViewById(R.id.marker_tv);
            switch (alarmLevel) {//根据告警级别来绘制不同颜色的线段
                case 1:
                    tv_bg.setBackgroundResource(R.drawable.markerbitmap_1);
                    break;
                case 2:
                    tv_bg.setBackgroundResource(R.drawable.markerbitmap_2);
                    break;
                case 3:
                    tv_bg.setBackgroundResource(R.drawable.markerbitmap_3);
                    break;
                case 4:
                    tv_bg.setBackgroundResource(R.drawable.markerbitmap_4);
                    break;
            }
            Bitmap bitmap = convertViewToBitmap(view);
            options.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            Marker marker = mapView.getMap().addMarker(options);
            marker.setObject(data);
            markers.add(marker);
        }
    }

    /**
     * 将view转换为bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        return bitmap;

    }

    private Timer timer = new Timer();

    private void startAlarmTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DevLog.e("告警消息定时刷新1111111111");
                MessageEvent event = new MessageEvent();
                event.what = MessageEvent.MAP_REFRESH;
                EventBus.getDefault().post(event);
            }
        }, 8000, 5000);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.UPDATE_ALARM_MSG://定时更新告警信息
                DevLog.e("使用主页的定时器");
//                getAlarmMsg();
                break;
            case MessageEvent.UPDATE_MSG_COUNT://收到信息
                GaoJingUpdateBean data = (GaoJingUpdateBean) event.obj;
                timeShowAlarmMsg(data);
                break;
        }
    }

    private void timeShowAlarmMsg(GaoJingUpdateBean data) {
        final List<Integer> objids = new ArrayList<>();
        if (gMapBean != null) {
            for (int i = 0; i < gMapBean.getPolylines().size(); i++) {
                objids.add(gMapBean.getPolylines().get(i).getDataID());
            }
            for (int i = 0; i < gMapBean.getPoints().size(); i++) {
                objids.add(Integer.parseInt(gMapBean.getPoints().get(i).getDataID()));
            }
        }
        if (data.getAlarmMsgType() == 1) {//新告警
            for (int j = 0; j < objids.size(); j++) {
                if (objids.get(j) == data.getAlarm().getMeasObjID()) {
                    showAlarm(data.getAlarm(), data.getAlarmMsgType());
                }
            }

        } else if (data.getAlarmMsgType() == 3) {//清除告警
            DevLog.e("查看日志信息：" + markers.size() + "|");//
            int size = markers.size();
            for (int j = 0; j < size; j++) {
                GaoJingDataList gjd = (GaoJingDataList) markers.get(j).getObject();
                for (int k = 0; k < objids.size(); k++) {
                    DevLog.e("展示基础信息：" + gjd.getMeasObjID() + "|" + objids.get(k));
                    if (gjd.getMeasObjID() == objids.get(k)) {
                        markers.get(j).remove();
                        objids.remove(k);
                    }
                }

            }
        }
    }
}
