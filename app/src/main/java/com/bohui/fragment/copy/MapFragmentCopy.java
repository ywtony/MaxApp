package com.bohui.fragment.copy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.maps2d.model.TextOptions;
import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.activity.FaultDescriptionActivity;
import com.bohui.activity.FaultListActivity;
import com.bohui.activity.MainActivity;
import com.bohui.bean.AlarmType;
import com.bohui.bean.ConfigBean;
import com.bohui.bean.GaoJingDataList;
import com.bohui.bean.GaoJingUpdateBean;
import com.bohui.bean.MainPage;
import com.bohui.bean.MapBean;
import com.bohui.bean.MessageEvent;
import com.bohui.bean.Points;
import com.bohui.bean.PolyLines_Child;
import com.bohui.bean.Polylines;
import com.bohui.inter.DrawWhat;
import com.bohui.model.GetAlarmMsgModel;
import com.bohui.model.GetUnclearedAlarmsByMeasObjIDsModel;
import com.bohui.model.MapDataModel;
import com.bohui.model.PageModel;
import com.bohui.utils.DevLog;
import com.bohui.utils.JSONUtil;
import com.bohui.view.IGaoJingView;
import com.bohui.view.IMapDataView;
import com.bohui.view.IPageView;
import com.bohui.widget.BitmapWeight;
import com.bohui.widget.ImageViewGif;
import com.bohui.widget.dialog.BottomToTopDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 地图
 */
public class MapFragmentCopy extends Fragment implements View.OnClickListener {
    private static MapFragmentCopy mHomeFragment;
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.iv_header_menu)
    ImageView iv_header_menu;
    @BindView(R.id.iv_header_right)
    ImageView iv_header_right;
    @BindView(R.id.tv_title)
    TextView tv_title;


    @BindView(R.id.clearmapview)
    Button btn_clear;
    @BindView(R.id.img_test)
    ImageViewGif imageViewGif;
    @BindView(R.id.map_tv_weixing_mode)
    TextView tv_weixing;
    @BindView(R.id.map_tv_normal_mode)
    TextView tv_normal;
    private MapBean gMapBean;
    private Bitmap alarmBitmap;
    private List<Marker> markers = new ArrayList<>();
    private String data = null;
    private Marker currentMarker = null;
    //    AnimUtils animUtils = null;
    private String state = null;
    MainPage mainPage = null;
    MapView mapView;

    public static MapFragmentCopy getInstance() {
        synchronized (MapFragmentCopy.class) {
            if (mHomeFragment == null) {
                mHomeFragment = new MapFragmentCopy();
            }
            return mHomeFragment;
        }
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            DevLog.e("隐藏");
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }

        } else {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }

            DevLog.e("不隐藏");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gmapview_layout, container, false);
        ButterKnife.bind(this, view);
        mapView = view.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        //设置地图模式
//        mapView.getMap().setTrafficEnabled(true);
        mapView.getMap().setMapType(AMap.MAP_TYPE_SATELLITE);
        if (data != null) {
            initMapData(data, true);
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
        mapView.getMap().setInfoWindowAdapter(new MyInfoWindow());
        mapView.getMap().setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker != null) {
                    currentMarker = marker;
//                ToastUtil.getInstance().show(getActivity(),"d点击了marker");
                    marker.showInfoWindow();
                }

                return false;
            }
        });
        mapView.getMap().setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (mapView.getMap() != null && currentMarker != null) {
                    if (currentMarker.isInfoWindowShown()) {
                        currentMarker.hideInfoWindow();
                    }
                }
            }
        });
        mapView.getMap().setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                GaoJingDataList gaoJingDataList = (GaoJingDataList) marker.getObject();
                Intent intent = new Intent();
                intent.putExtra("bean", gaoJingDataList);
                intent.setClass(getActivity(), FaultDescriptionActivity.class);
                startActivity(intent);
            }
        });

//        mapView.getMap().getCameraPosition().zoom;


//gMapBean.getMeasObjID()
//        getAlarmMsg();

//        startAlarmTimer();

//        animUtils = new AnimUtils(mapView.getMap());
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mapView.getMap().clear();
                markers.get(0).remove();
            }
        });
        iv_header_left.setVisibility(View.GONE);
        iv_header_menu.setVisibility(View.VISIBLE);
        iv_header_right.setVisibility(View.GONE);
        iv_header_menu.setOnClickListener(this);
        iv_header_right.setOnClickListener(this);
//        tv_title.setText(getResources().getString(R.string.menu_home));
        tv_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.getMap().setMapType(AMap.MAP_TYPE_NORMAL);
            }
        });
        tv_weixing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapView.getMap().setMapType(AMap.MAP_TYPE_SATELLITE);
            }
        });
        return view;
    }

    private void initMapData(String json, boolean bool) {
        if (json != null) {
            gMapBean = (MapBean) JSONUtil.getInstance().getObject(json, MapBean.class);
//            tv_title.setText(gMapBean.getName());
        }
        if (gMapBean != null) {
//            int showType = gMapBean.getMapShowType();
//            switch (showType) {
//                case 0:
//                    mapView.getMap().setMapType(AMap.MAP_TYPE_NORMAL);
//                    break;
//                case 1:
//                case 2:
//                    mapView.getMap().setMapType(AMap.MAP_TYPE_SATELLITE);
//                    break;
//            }
            mapView.getMap().clear();
//            markers.clear();
            double lat = Double.parseDouble(gMapBean.getMapCenterLat());
            double lon = Double.parseDouble(gMapBean.getMapCenterLng());
            initLine(gMapBean);
            initPoints(gMapBean);
            if (bool) {
                mapView.getMap().moveCamera(CameraUpdateFactory.zoomTo(gMapBean.getMapInitialZoom()));
                if (BoHuiApplication.getInstance().getLatlng() == null || "".equals(BoHuiApplication.getInstance().getLatlng())) {
                    mapView.getMap().moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(lat, lon)));
                } else {
                    String[] strs = BoHuiApplication.getInstance().getLatlng().split(",");
                    float latt = Float.parseFloat(strs[0]);
                    float lngg = Float.parseFloat(strs[1]);
                    addAlarmIcon(latt, lngg);
                    mapView.getMap().moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latt, lngg)));
                }
            }

        }
        getFirstRefreshAlarm(bool);
    }

    private void addAlarmIcon(float lat, float lng) {

        MarkerOptions options = new MarkerOptions();
        options.title("您好啊");
        options.anchor(0.5f, 1.0f);
        options.position(new LatLng(lat, lng));
        BitmapDescriptor bitmapDescriptor1 = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.alarm_un_icon));
        options.icon(bitmapDescriptor1);
        options.period(1);
        Marker marker = mapView.getMap().addMarker(options);
//        marker.setObject(new Points(lat,lng));
//        mapView.getMap().addMarker(options);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_menu:
                if (MainActivity.instance != null) {
                    MainActivity.instance.openDrawer();
                }

                break;
            case R.id.iv_header_right:
                startActivity(new Intent(getActivity(), FaultListActivity.class));
                break;
        }
    }


    private void initLine(MapBean mapBean) {
        List<List<LatLng>> datasAlls = new ArrayList<>();

        if (mapBean.getPolylines() != null) {
            List<Polylines> datas = mapBean.getPolylines();
            for (int i = 0; i < datas.size(); i++) {
                List<LatLng> latLngs = new ArrayList<>();
                List<PolyLines_Child> childs = datas.get(i).getArealist();
//                if(i==1){


                if (childs != null) {
                    for (int j = 0; j < childs.size(); j++) {
                        double startLat = Double.parseDouble(childs.get(j).getStartlatitude());
                        double startLon = Double.parseDouble(childs.get(j).getStartlongitude());
                        latLngs.add(new LatLng(startLat, startLon));
                        double endLat = Double.parseDouble(childs.get(j).getEndlatitude());
                        double endLon = Double.parseDouble(childs.get(j).getEndlongitude());
                        latLngs.add(new LatLng(endLat, endLon));
                        float[] results = new float[1];
                        Location.distanceBetween(startLat, startLon, endLat, endLon, results);
                        if (results[0] > 1000) {
                            DevLog.e("坐标点之间的距离坐标：" + i + "-----" + j + "---" + startLat + "|" + startLon + "|" + endLat + "|" + endLon);
                            DevLog.e("坐标点之间的距离：" + i + "-----" + j + "---" + results[0]);
                        }
                    }
                }
//                }
                datasAlls.add(latLngs);
            }
        }
        for (int k = 0; k < datasAlls.size(); k++) {

            List<LatLng> latLngs = datasAlls.get(k);
            DevLog.e("坐标点的总个数：" + latLngs.size());
            Polyline polyline = mapView.getMap().addPolyline(new PolylineOptions().
                    addAll(latLngs).width(mapBean.getPolylines().get(0).getStrokeweight()).color(Color.parseColor(mapBean.getPolylines().get(0).getColor())));
        }

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
                    DevLog.e("type;" + point.getType());
                    LatLng latLng = new LatLng(lat, lng);
                    if (point.getType().equals("Stl")) {
                        mapView.getMap().addText(new TextOptions().text(point.getText()).position(latLng).fontColor(Color.parseColor(point.getFontColor())).fontSize(point.getFontSize()).backgroundColor(Color.parseColor(point.getBackColor())));
                    } else if (point.getType().equals("Stp")) {
                        ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
                        String imagePath = configBean.getIp() + "/";
                        DevLog.e("points：" + imagePath + point.getGifIcon());
                        new BitmapWeight().loadImageFromNet(getActivity(), mapView, imagePath, point);
//                        new BitmapWeight().createBitmap(mapView, imagePath, point, null);
//                        new BitmapWeight().loadImage(getActivity(),mapView,imagePath,point,animUtils);
//                        ImageLoader.getInstance().loadImage(imagePath + point.getGifIcon(), new ImageLoadingListener() {
//                            @Override
//                            public void onLoadingStarted(String s, View view) {
//
//                            }
//
//                            @Override
//                            public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//                            }
//
//                            @Override
//                            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
////                                    alarmBitmap = bitmap;
//                                DevLog.e("测试图片是否执行完成了");
//                                MarkerOptions options = new MarkerOptions();
//                                options.title("您好啊");
//                                options.anchor(0.5f, 1.0f);
//                                options.position(new LatLng(point.getLat(), point.getLng()));
//                                options.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
//                                Marker marker = mapView.getMap().addMarker(options);
//                                animUtils.addLocationMarker(marker, new LatLng(point.getLat(), point.getLng()));
//                            }
//
//                            @Override
//                            public void onLoadingCancelled(String s, View view) {
//
//                            }
//                        });
                    }
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        if (mapView != null) {
            mapView.onResume();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        if (mapView != null) {
            mapView.onPause();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mapView != null) {
            mapView.onDestroy();
        }

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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
    private void getAlarmMsg(boolean bool) {
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
        new GetAlarmMsgModel().getAlarmMsgData(getActivity(), id, new IGaoJingView() {
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
                                        showAlarm(data.getAlarm(), data.getAlarmMsgType(), bool);
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
    private void getFirstRefreshAlarm(final boolean bool) {
        Set<String> sets = new HashSet<>();
        StringBuffer objIds = new StringBuffer();
        if (gMapBean != null) {
            for (int i = 0; i < gMapBean.getPolylines().size(); i++) {
                objIds.append(gMapBean.getPolylines().get(i).getDataID());
                objIds.append(",");
            }
            for (int i = 0; i < gMapBean.getPoints().size(); i++) {
                String dataid = gMapBean.getPoints().get(i).getDataID();
                if (dataid != null && !"".equals(dataid)) {
                    objIds.append(gMapBean.getPoints().get(i).getDataID());
                    objIds.append(",");
                }
            }
            if (objIds.length() > 0) {
                objIds.deleteCharAt(objIds.length() - 1);
            }
        }
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new GetUnclearedAlarmsByMeasObjIDsModel().getGetUnclearedAlarmsByMeasObjIDsData(getActivity(), id, objIds.toString(), new IGaoJingView() {
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
                            showAlarm(data, -1, bool);
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
    private void showAlarm(GaoJingDataList data, int alarmType, boolean bool) {
        List<AlarmType> ats = BoHuiApplication.getInstance().getDatas();
        DevLog.e("告警条数：" + ats.size());
        if (ats != null) {

            markers.clear();
            for (AlarmType at : ats) {
                //找到与之匹配的数据
                if (at.getID().equals(gMapBean.getAlarmType().getID()) && at.getObjTypeID() == gMapBean.getAlarmType().getObjTypeID()) {
                    DevLog.e("看看总共有几条：" + gMapBean.getAlarmType().getID());
                    setAlramTypeBitmap(at, data, alarmType, bool);
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
    private void setAlramTypeBitmap(AlarmType at, final GaoJingDataList data, final int alarmType, boolean bool) {
        if (at.getAlarmImage() != null && !"".equals(at.getAlarmImage())) {//如果告警图片不为空，就把告警图片下载并绘制到地图上
            ConfigBean configBean = BoHuiApplication.getInstance().getConfigDB().getConfig();
            String imagePath = configBean.getIp() + "/";
            DevLog.e("地图告警信息：" + imagePath + at.getAlarmImage());
            DevLog.e("不同的经纬度：" + data.getLatitude() + "|" + data.getLongitude());
//            new BitmapWeight().loadAlarmImages(getActivity(),mapView,imagePath+ at.getAlarmImage(),data,markers);
            loadImgFromNet(imagePath + at.getAlarmImage(), at, data, alarmType, bool);
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
            options.title("波汇");
            options.anchor(0.5f, 1.0f);
            options.position(new LatLng(data.getLatitude(), data.getLongitude()));
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.marker_layout, null);
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
//            animUtils.addLocationMarker(marker, new LatLng(data.getLatitude(), data.getLongitude()));
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

    private String locationState;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.UPDATE_ALARM_MSG://定时更新告警信息
                DevLog.e("使用主页的定时器");
//                getAlarmMsg();
                if (this.data != null) {
                    initMapData(this.data, false);
                }
                break;
            case MessageEvent.UPDATE_MSG_COUNT://收到信息
//                GaoJingUpdateBean data = (GaoJingUpdateBean) event.obj;
//                timeShowAlarmMsg(data);
                break;
            case MessageEvent.TO_MAPVIEW:
                DevLog.e("找到了地图数据1111111111" + BoHuiApplication.getInstance().getName());
                this.data = (String) event.obj;
//                if (mainPage != null) {
//                    tv_title.setText(mainPage.getName());
//                } else {

                tv_title.setText(BoHuiApplication.getInstance().getName());
//                }
                locationState = event.state;
                initMapData(this.data, true);

                break;
            case MessageEvent.LOAD_INDEX_PAGE://加载主页数据
                DevLog.e("全局的经纬度：" + BoHuiApplication.getInstance().getLatlng());
                state = event.state;
                DevLog.e("加载主页数据:mapfragment-------" + state);

                mainPage = (MainPage) event.obj;
                tv_title.setText(mainPage.getName());
                getPage(mainPage.getID() + "", event.state);
                break;
        }
    }


    private void timeShowAlarmMsg(GaoJingUpdateBean data, boolean bool) {
        if (data == null)
            return;
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
                    showAlarm(data.getAlarm(), data.getAlarmMsgType(), bool);
                }
            }

        } else if (data.getAlarmMsgType() == 3) {//确认告警
            DevLog.e("查看日志信息：" + markers.size() + "|");//
            int size = markers.size();
            for (int j = 0; j < size; j++) {
                GaoJingDataList gjd = (GaoJingDataList) markers.get(j).getObject();
                for (int k = 0; k < objids.size(); k++) {
                    DevLog.e("展示基础信息：" + gjd.getMeasObjID() + "|" + objids.get(k));
                    if (gjd.getMeasObjID() == objids.get(k)) {
                        DevLog.e("看看清空了多少：" + j);
                        markers.get(j).remove();
                    }
                }

            }
        }
    }


    private void getPage(final String pageId, final String state) {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new PageModel().getPage(getActivity(), id, pageId, new IPageView() {
            @Override
            public void success(String msg) {
                Gson gson = new Gson();
                MainPage bean = gson.fromJson(msg,
                        new TypeToken<MainPage>() {
                        }.getType());
                if (bean != null) {
                    tv_title.setText(bean.getName());
                    getBoxList(bean, pageId, state);
                }
            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    private void getBoxList(final MainPage mainPage, final String pageId, final String state) {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new MapDataModel().getMapData(getActivity(), id, mainPage.getID() + "", new IMapDataView() {
            @Override
            public void success(String msg) {
//                DevLog.e("获取到了地图数据");
                try {
                    Gson gson = new Gson();
                    List<MapBean> bean = gson.fromJson(msg,
                            new TypeToken<List<MapBean>>() {
                            }.getType());
                    if (bean != null && bean.size() > 0) {
                        boolean flag = true;
                        List<MapBean> mapBeans = bean;
                        for (int i = 0; i < bean.size(); i++) {
                            if ((bean.get(i).getPID() + "").equals(pageId)) {
                                if (bean.get(i).getType().equals(DrawWhat.DistributeChart)) {//找到图表
                                    //将首页替换成为图表页面
                                    flag = false;

                                    MessageEvent event = new MessageEvent();
                                    event.what = MessageEvent.TO_TUBIA01;
                                    event.obj = bean.get(i).getMeasObjID();
                                    EventBus.getDefault().post(event);
                                    return;
                                } else if (bean.get(i).getType().equals(DrawWhat.MapClass)) {//如果加载的是地图组件
                                    DevLog.e("找到了地图哈哈哈");
                                    flag = false;
                                    //将首页替换成为地图页面
//                                  Intent intent = new Intent();
//                                  intent.putExtra("data", JSONUtil.getInstance().getString(bean.get(i)));
//                                  intent.setClass(getActivity(), GMapViewActivity.class);
//                                  startActivity(intent);
                                    MessageEvent event = new MessageEvent();
                                    event.what = MessageEvent.TO_MAPVIEW1;
                                    event.obj = JSONUtil.getInstance().getString(bean.get(i));
                                    EventBus.getDefault().post(event);
                                    return;
                                }
                            }

                        }
                        if (flag) {
                            MessageEvent event = new MessageEvent();
                            event.what = MessageEvent.TO_INDEX1;
                            event.obj = mainPage;
                            EventBus.getDefault().post(event);
                        }

                    } else {
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void showToast(String msg) {

            }
        });
    }


    private void loadImgFromNet(String url, final AlarmType at, final GaoJingDataList data, final int alarmType, boolean bool) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new SimpleBitmapDisplayer())
                .build();
        ImageLoader.getInstance().loadImage(url, options, new SimpleImageLoadingListener() {

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
                loadImgFromNet(imageUri, at, data, alarmType, bool);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                super.onLoadingComplete(imageUri, view, bitmap);
                alarmBitmap = bitmap;
                MarkerOptions options = new MarkerOptions();
                options.title("您好啊");
                options.anchor(0.5f, 1.0f);
//                    DevLog.e("不同的经纬度："+data.getLatitude()+"|"+data.getLongitude());
                options.position(new LatLng(data.getLatitude(), data.getLongitude()));
                BitmapDescriptor bitmapDescriptor1 = BitmapDescriptorFactory.fromBitmap(alarmBitmap);
                Bitmap bitmap1 = Bitmap.createBitmap(bitmapDescriptor1.getBitmap().getWidth(), bitmapDescriptor1.getBitmap().getHeight(), Bitmap.Config.ARGB_8888);
                BitmapDescriptor bitmapDescriptor2 = BitmapDescriptorFactory.fromBitmap(bitmap1);
                ArrayList<BitmapDescriptor> bitmaps = new ArrayList<>();
                bitmaps.add(bitmapDescriptor1);
                bitmaps.add(bitmapDescriptor2);
                options.icons(bitmaps);
                options.period(1);
                Marker marker = mapView.getMap().addMarker(options);
                marker.setObject(data);
                markers.add(marker);
//                animUtils.addLocationMarker(marker, new LatLng(data.getLatitude(), data.getLongitude()));
//                new MapIconAnim(mapView.getMap(), marker.getPosition()).execute();
                if (bool) {
                    if (BoHuiApplication.getInstance().getLatlng() == null || "".equals(BoHuiApplication.getInstance().getLatlng())) {
                        DevLog.e("状态为空");
                        mapView.getMap().moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(data.getLatitude(), data.getLongitude())));
                    } else {
                        DevLog.e("状态不为空");
                        String[] strs = BoHuiApplication.getInstance().getLatlng().split(",");
                        float latt = Float.parseFloat(strs[0]);
                        float lngg = Float.parseFloat(strs[1]);
                        mapView.getMap().moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latt, lngg)));

                    }
                }

            }
        });
    }

    class MyInfoWindow implements AMap.InfoWindowAdapter {
        View infoWindow = null;

        @Override
        public View getInfoWindow(Marker marker) {
            if (infoWindow == null) {
                infoWindow = LayoutInflater.from(getActivity()).inflate(
                        R.layout.custom_info_window, null);
            }
//            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.height = Dimension.dp2px(getActivity(),90);
//            params.width = Dimension.dp2px(getActivity(),250);
//            infoWindow.setLayoutParams(params);
            render(marker, infoWindow);
            return infoWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }

        /**
         * 自定义infowinfow窗口
         */
        public void render(final Marker marker, View view) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GaoJingDataList gaoJingDataList = (GaoJingDataList) marker.getObject();
                    Intent intent = new Intent();
                    intent.putExtra("bean", gaoJingDataList);
                    intent.setClass(getActivity(), FaultDescriptionActivity.class);
                    startActivity(intent);
                }
            });
            //如果想修改自定义Infow中内容，请通过view找到它并修改
            TextView tv_type = view.findViewById(R.id.info_tv_type);
            TextView tv_level = view.findViewById(R.id.info_tv_level);
            TextView tv_time = view.findViewById(R.id.info_tv_time);
            ImageView iv_location = view.findViewById(R.id.iv_tolocation);

            GaoJingDataList data = (GaoJingDataList) marker.getObject();
            iv_location.setOnClickListener(v -> {
                BottomToTopDialog bottomToTopDialog = new BottomToTopDialog(getActivity(), data.getLatitude(), data.getLongitude(), data.getAlarmType().getName());
                bottomToTopDialog.show();
            });
            if (data.getAlarmType() != null) {
                tv_type.setText("告警类别：" + data.getAlarmType().getName());
                int level = data.getAlarmType().getAlarmSeverity();
                switch (level) {
                    case 1:
                        tv_level.setText("告警级别：提示");
                        break;
                    case 2:
                        tv_level.setText("告警级别：一般");
                        break;
                    case 3:
                        tv_level.setText("告警级别：严重");
                        break;
                    case 4:
                        tv_level.setText("告警级别：危急");
                        break;
                    default:
                        break;
                }
            } else {
                tv_type.setText("告警类别：");
                tv_level.setText("异常");
            }


            tv_time.setText("告警时间：" + data.getRaisedTime().replace("T", ""));
        }
    }
}
