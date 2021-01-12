package com.bohui.fragment;

import android.app.AlertDialog;
import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.activity.FaultDescriptionActivity;
import com.bohui.activity.FaultListActivity;
import com.bohui.activity.GMapViewActivity;
import com.bohui.activity.LoginActivity;
import com.bohui.activity.MainActivity;
import com.bohui.activity.TemperDetectActivity;
import com.bohui.activity.VideoPlayActivity;
import com.bohui.bean.ExAttrs;
import com.bohui.bean.GJPoint;
import com.bohui.bean.GaoJingDataList;
import com.bohui.bean.GaoJingUpdateBean;
import com.bohui.bean.GaojingBean;
import com.bohui.bean.MainPage;
import com.bohui.bean.MapBean;
import com.bohui.bean.MessageEvent;
import com.bohui.bean.ObjData;
import com.bohui.bean.ZJAlarmBean;
import com.bohui.dialog.GJDialog;
import com.bohui.inter.DrawWhat;
import com.bohui.model.ControlModel;
import com.bohui.model.CountAllUnclearedAlarmModel;
import com.bohui.model.GaoJIngPageModel;
import com.bohui.model.GaoJingModel;
import com.bohui.model.GetAlarmMsgModel;
import com.bohui.model.GetUnclearedAlarmsByMeasObjIDsModel;
import com.bohui.model.IconModel;
import com.bohui.model.MainPageModel;
import com.bohui.model.MapDataModel;
import com.bohui.model.ObjectDataModel;
import com.bohui.model.PageListModel;
import com.bohui.model.PageModel;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.AssetsUtils;
import com.bohui.utils.DevLog;
import com.bohui.utils.JSONUtil;
import com.bohui.utils.StatusBarUtil;
import com.bohui.utils.StatusBarUtils;
import com.bohui.utils.ToastUtil;
import com.bohui.view.DrawMapView;
import com.bohui.view.ICongrolView;
import com.bohui.view.IGaoJingView;
import com.bohui.view.IIconView;
import com.bohui.view.IMainPageView;
import com.bohui.view.IMapDataView;
import com.bohui.view.IObjDataView;
import com.bohui.view.IPageListView;
import com.bohui.view.IPageView;
import com.bohui.view.MapView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdk.NETDEV_CLOUD_LIMIT_INFO_S;
import com.sdk.NETDEV_CLOUD_MOBILE_INFO_S;
import com.sdk.NetDEVSDK;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.ionic.starter.activity.Demo1ListActivity;

/**
 * 首页
 * Created by Administrator on 2018/4/18 0018.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, DrawMapView.PageClickListener {
    private Context mContext;
    private View view;
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.iv_header_menu)
    ImageView iv_header_menu;
    @BindView(R.id.iv_header_right)
    ImageView iv_header_right;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rl_top)
    RelativeLayout rl_top;
    @BindView(R.id.mapview)
    DrawMapView mapView;
    @BindView(R.id.rel_main)
    RelativeLayout rel_main;
    @BindView(R.id.tv_header_right)
    TextView tv_right;
    private StringBuffer objIds = new StringBuffer();
    private MainPage mainPage = new MainPage();
    private List<MapBean> mapBeans = new ArrayList<>();
    private int height = 0;//高度设置
    public int type = 0;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DevLog.e("调用了MainPage");
    }

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
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment_hone, container, false);
        ButterKnife.bind(this, view);
        initView();
        DevLog.e("查看类型是多少：" + type);
//        getMapData();
        //http://host/images/box/xxx.png
        if (!BoHuiApplication.getInstance().isMainHome()) {
            if (!BoHuiApplication.getInstance().isHomeCreate()) {
                getMainPageData();
                BoHuiApplication.getInstance().setHomeCreate(true);
//                mapView.setPageClickListener(this);
//        createFengji();
//        if (true) {
                startTimer();
            } else if (BoHuiApplication.getInstance().isClickHome()) {
                getMainPageData();
                BoHuiApplication.getInstance().setHomeCreate(true);
//                mapView.setPageClickListener(this);
            }
        }
        mapView.setPageClickListener(this);
        tv_right.setVisibility(View.VISIBLE);
        String language = BoHuiApplication.getInstance().getConfigDB().getLanguage();
//        if ("zh".equals(language)) {
//            tv_right.setText("刷新");
//        } else {
//            tv_right.setText("Refresh");
//        }
        tv_right.setText(R.string.refresh);
        tv_right.setOnClickListener(this);

//        getPageList();
//        getGaojingList();
//        postControl();
//        getJMainPage();
//        getIconData();
//        getGaojingPageData();
//        getAlarmMsg();
//        getUncleardAlarms();
        return view;
    }

    private void initView() {
        iv_header_left.setVisibility(View.GONE);
        iv_header_menu.setVisibility(View.VISIBLE);
        iv_header_right.setVisibility(View.GONE);
        iv_header_menu.setOnClickListener(this);
        iv_header_right.setOnClickListener(this);
        tv_title.setText(getResources().getString(R.string.menu_home));
        int height = StatusBarUtils.getInstance().getStatusBarHeight(getActivity());
        //measure方法的参数值都设为0即可
        rl_top.measure(0, 0);
        //获取组件宽度
        int width = rl_top.getMeasuredWidth();
        //获取组件高度
        int height_1 = rl_top.getMeasuredHeight();
        DevLog.e("height:" + height + "|height_1:" + height_1);
        height = StatusBarUtils.getInstance().getStatusBarHeight(getActivity()) + height_1;
        mapView.setTopHeight(0);

//        rl_top.setVisibility(View.GONE);
//        mapView.setTopHeight(0);


        int result = 0;
        int resourceId = getActivity().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getActivity().getResources().getDimensionPixelSize(resourceId);
        }
        DevLog.e("获取状态栏高度：" + result);
//
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_menu:
                if(MainActivity.instance!=null){
                    MainActivity.instance.openDrawer();
                }

                break;
//            case R.id.iv_header_right:
//                startActivity(new Intent(getActivity(), FaultListActivity.class));
//                break;
            case R.id.tv_header_right://刷新当前页面
                if (mainPage != null) {
                    getPage(mainPage.getID() + "");
                }
                break;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        DevLog.e("-------------onresume");

    }

    @Override
    public void onPause() {
        super.onPause();
        DevLog.e("-------------执行了onpause");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.unbind(this);
    }

    private void getBoxList(final MainPage mainPage, final String pageId) {

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
                        HomeFragment.this.mapBeans = bean;


                        if (pageId != null) {


                            for (int i = 0; i < bean.size(); i++) {
                                if (pageId != null && pageId.equals(bean.get(i).getPID() + "")) {
                                    if (bean.get(i).getType().equals(DrawWhat.DistributeChart)) {//找到图表
                                        //将首页替换成为图表页面
                                        flag = false;
                                        DevLog.e("mainPage_Name:" + mainPage.getName());
                                        BoHuiApplication.getInstance().setName(mainPage.getName());
                                        MessageEvent event = new MessageEvent();
                                        event.what = MessageEvent.TO_TUBIA01;
                                        event.obj = bean.get(i).getMeasObjID();
                                        EventBus.getDefault().post(event);

//                                Intent intent = new Intent();
//                                intent.putExtra("id", bean.get(i).getMeasObjID());
//                                intent.setClass(getActivity(), TemperDetectActivity.class);
//                                startActivity(intent);
                                        return;
                                    } else if (bean.get(i).getType().equals(DrawWhat.MapClass)) {//如果加载的是地图组件
                                        flag = false;
                                        DevLog.e("找到了地图哈哈哈");
                                        //将首页替换成为地图页面
//                                Intent intent = new Intent();
//                                intent.putExtra("data", JSONUtil.getInstance().getString(bean.get(i)));
//                                intent.setClass(getActivity(), GMapViewActivity.class);
//                                startActivity(intent);
                                        DevLog.e("mainPage_Name:" + mainPage.getName());
                                        BoHuiApplication.getInstance().setName(mainPage.getName());
                                        MessageEvent event = new MessageEvent();
                                        event.what = MessageEvent.TO_MAPVIEW1;
                                        event.state = locationState;
                                        event.obj = JSONUtil.getInstance().getString(bean.get(i));
                                        EventBus.getDefault().post(event);
                                        return;
                                    }
                                }
                            }

                            if (flag) {

                                DevLog.e("加载boxlist成功");
//                                    mapView.resetBitmap();
//                                    mapView.draw(mainPage, mapBeans);
                                updateData(mapBeans, true);
//                                    getClearAlarmData(mapBeans);
                            }
                        } else {
                            for (int i = 0; i < bean.size(); i++) {
//                                if ((bean.get(i).getPID()+"").equals(pageId)) {
                                if (bean.get(i).getType().equals(DrawWhat.DistributeChart)) {//找到图表
                                    //将首页替换成为图表页面
                                    flag = false;
                                    BoHuiApplication.getInstance().setName(mainPage.getName());
                                    MessageEvent event = new MessageEvent();
                                    event.what = MessageEvent.TO_TUBIA01;
                                    event.obj = bean.get(i).getMeasObjID();
                                    EventBus.getDefault().post(event);

                                    return;
                                } else if (bean.get(i).getType().equals(DrawWhat.MapClass)) {//如果加载的是地图组件
                                    BoHuiApplication.getInstance().setName(mainPage.getName());
                                    flag = false;
                                    DevLog.e("找到了地图哈哈哈");
                                    //将首页替换成为地图页面
                                    MessageEvent event = new MessageEvent();
                                    event.what = MessageEvent.TO_MAPVIEW1;
                                    event.obj = JSONUtil.getInstance().getString(bean.get(i));
                                    EventBus.getDefault().post(event);
                                    return;
                                }
//                                }
                            }

                            if (flag) {

                                DevLog.e("加载boxlist成功");
//                                    mapView.resetBitmap();
//                                    mapView.draw(mainPage, mapBeans);
                                updateData(mapBeans, true);
//                                    getClearAlarmData(mapBeans);
                            }
                        }
//                        ToastUtil.getInstance().show(getActivity(), "不得了啊");
                    } else {
//                        ToastUtil.getInstance().show(getActivity(),"获取到的数据为空");
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

    private void copyList(List<MapBean> datas) {
        if (mapBeans != null && mapBeans.size() > 0) {

        }
    }

    /**
     * 获取主页数据
     */
    private void getMainPageData() {
        DevLog.e("执行了oncreate方法");
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new MainPageModel().getMainPageData(getActivity(), id, new IMainPageView() {
            @Override
            public void success(String msg) {
                Gson gson = new Gson();
                MainPage bean = gson.fromJson(msg,
                        new TypeToken<MainPage>() {
                        }.getType());
                if (bean != null) {
                    DevLog.e("执行了oncreate方法" + bean.getName());
                    HomeFragment.this.mainPage = bean;
                    tv_title.setText(mainPage.getName());
                    getBoxList(bean, null);
                }
            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    private void getPage(final String pageId) {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new PageModel().getPage(getActivity(), id, pageId, new IPageView() {
            @Override
            public void success(String msg) {
                Gson gson = new Gson();
                MainPage bean = gson.fromJson(msg,
                        new TypeToken<MainPage>() {
                        }.getType());
                switch (bean.getPageType()) {
                    case 3://宇视摄像头
                        loginYushiVideo(bean);
                        break;
                    case 2://H5page
                        break;
                    default:
                        if (bean != null) {
                            HomeFragment.this.mainPage = bean;
                            //设置标题
                            tv_title.setText(mainPage.getName());
                            getBoxList(bean, pageId);
                        }
                        break;
                }

            }

            @Override
            public void showToast(String msg) {

            }
        });
    }
    private void loginYushiVideo(MainPage page) {
        String json = page.getExAttrs().replaceAll("\\\\", "");
        ExAttrs bean = (ExAttrs) JSONUtil.getInstance().getObject(json, ExAttrs.class);
        if (bean == null) {
            return;
        }
        int iNoAccountFlag = 0;
        if ((0 == bean.getUser().length()) && (0 == bean.getUser().length())) {
            iNoAccountFlag = 1;
        }
        NetDEVSDK.glpcloudID = NetDEVSDK.NETDEV_LoginCloudEx(bean.getUrl(), bean.getUser(), bean.getPwd(), iNoAccountFlag);
        if (0 != NetDEVSDK.glpcloudID) {
            NetDEVSDK.NETDEV_SetClientID("1234567890987654321");          /* ClienID must be Unique! */
            NETDEV_CLOUD_MOBILE_INFO_S stMobileInfo = new NETDEV_CLOUD_MOBILE_INFO_S();
            NETDEV_CLOUD_LIMIT_INFO_S stLimitInfo = new NETDEV_CLOUD_LIMIT_INFO_S();
            stMobileInfo.szMobileModule = "A2017";
            stMobileInfo.szSystemType = "Andriod";
            stMobileInfo.szSystemVersion = "7.0.0.1";
            stMobileInfo.bDonotDisturb = 1;
            stMobileInfo.bPushBuiltFlag = 0;
            stMobileInfo.szAppName = "io.ionic.starter";
            stMobileInfo.szAppLanguage = "zh-cn";
            stMobileInfo.szAppVersion = "V0.0.0.1";
            stMobileInfo.bIosEnvir = 1;
            stLimitInfo.udwMaxAppTimeS = 0;
            stLimitInfo.udwMaxDeviceNum = 0;
            stLimitInfo.udwMaxRTSNum = 0;
            int iRet = NetDEVSDK.NETDEV_ReportMobileInfo(NetDEVSDK.glpcloudID, stMobileInfo, stLimitInfo);
            if (0 == iRet) {
                AlertDialog.Builder oBuilder = new AlertDialog.Builder(getActivity());
                oBuilder.setTitle("Fail");
                oBuilder.setMessage("Report Mobile Info Fail.");
                oBuilder.setPositiveButton("OK", null);
                oBuilder.show();
            } else {
                yushiVideo(bean);
            }
        } else {
            AlertDialog.Builder oBuilder = new AlertDialog.Builder(getActivity());
            oBuilder.setTitle("Fail");
            oBuilder.setMessage("Login failed, please check if the input is correct.");
            oBuilder.setPositiveButton("OK", null);
            oBuilder.show();
        }

    }

    private void yushiVideo(ExAttrs exAttrs) {
        Intent intent = new Intent();
        intent.putExtra("url", exAttrs.getUrl());
        intent.putExtra("name", exAttrs.getUser());
        intent.putExtra("pwd", exAttrs.getPwd());
        intent.setClass(getActivity(), Demo1ListActivity.class);
        startActivity(intent);
    }
    @Override
    public void clickStaticLabel(Object mainPage, Object obj) {
        String pageId = (String) obj;
        getPage(pageId);
//        ToastUtil.getInstance().show(getActivity(), "您点击了静态文字");
    }

    @Override
    public void clickDynamicLable(Object mainPage, Object obj) {
//        ToastUtil.getInstance().show(getActivity(), "您点击了动态文字");
        this.mainPage = (MainPage) mainPage;
        String pageId = (String) obj;
        getPage(pageId);
    }

    @Override
    public void clickStaticpicture(Object mainPage, Object obj) {
//        ToastUtil.getInstance().show(getActivity(), "您点击了静态图片");
        this.mainPage = (MainPage) mainPage;
        String pageId = (String) obj;
        getPage(pageId);
    }

    @Override
    public void clickDynamicPicture(Object mainPage, Object obj) {
//        ToastUtil.getInstance().show(getActivity(), "您点击了动态图片");
        this.mainPage = (MainPage) mainPage;
        String pageId = (String) obj;
        getPage(pageId);
    }

    @Override
    public void clickControlButton(Object mainPage, Object obj) {
//        ToastUtil.getInstance().show(getActivity(), "您点击了控制按钮");
        this.mainPage = (MainPage) mainPage;
        String pageId = (String) obj;
        getPage(pageId);
    }

    @Override
    public void clickStatusSwitch(Object mainPage, Object obj, int cv) {
//        ToastUtil.getInstance().show(getActivity(), "您点击了按钮");
        this.mainPage = (MainPage) mainPage;
        ExAttrs exAttrs = (ExAttrs) obj;
        postControl(exAttrs, cv);
    }

    //点击了视频组件
    @Override
    public void clickVideo(Object mainPage, Object obj) {
        Intent intent = new Intent();
        ExAttrs exAttrs = (ExAttrs) obj;
        if (exAttrs != null && exAttrs.getPlayUrl() != null) {
            intent.putExtra("url", exAttrs.getPlayUrl());
        }
        intent.setClass(getActivity(), VideoPlayActivity.class);
        startActivity(intent);
    }

//    private GJDialog gjDialog = null;

    @Override
    public void clickGJ(Object obj) {
        GJPoint gjPoint = (GJPoint) obj;
//        if (gjDialog == null) {
//            gjDialog = new GJDialog(getActivity(), gjPoint);
//        }
//        if (gjDialog != null && !gjDialog.isShowing()) {
//            gjDialog.show();
//        }
        GJDialog gjDialog = new GJDialog(getActivity(), gjPoint);
        gjDialog.setDialogSSIDListener(new GJDialog.DialogGJListener() {
            @Override
            public void clearGj(GJPoint bean) {

                Intent intent = new Intent();
                GaoJingDataList gaoJingDataList = new GaoJingDataList();
                gaoJingDataList.setAlarmID(Integer.parseInt(bean.getAlarmId()));
                intent.putExtra("bean", gaoJingDataList);
                intent.setClass(getActivity(), FaultDescriptionActivity.class);
                startActivity(intent);
            }
        });
        gjDialog.show();
//        ToastUtil.getInstance().show(getActivity(), "您点击了告警:"+gjPoint.getAlarmLevel()+"|"+gjPoint.getAlarmType()+"|"+gjPoint.getAlarmTime());
    }

    private void createFengji() {
        ImageView fengjiView = new ImageView(getActivity());
        fengjiView.setLayoutParams(new RelativeLayout.LayoutParams(150, 150));
        fengjiView.setImageResource(R.mipmap.tree); //图片资源
        fengjiView.setX(150);
        fengjiView.setY(150);
        fengjiView.setImageResource(R.drawable.fengji);
        AnimationDrawable anim = (AnimationDrawable) fengjiView.getDrawable();
        rel_main.addView(fengjiView);
        anim.start();
    }

    /**
     * 获取更新数据
     *
     * @param bean
     * @param flag，false不更新底图，true更新底图
     */
    private void updateData(List<MapBean> bean, final boolean flag) {
        DevLog.e("执行了updatedata方法");
        if (bean != null) {
            objIds = new StringBuffer();
            for (int i = 0; i < bean.size(); i++) {
                String dataid = bean.get(i).getMeasObjID();
                if (dataid != null && !"".equals(dataid)) {
                    objIds.append(bean.get(i).getMeasObjID());
                    objIds.append(",");
                }

            }
            if (objIds.length() > 0) {
                objIds.deleteCharAt(objIds.length() - 1);
            }
        //objIds.append("1101,70001,1104,70001,70001,70001,1104,");
        }
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new ObjectDataModel().getObjDatas(getActivity(), id, objIds.toString(), new IObjDataView() {
            @Override
            public void success(String msg) {
                try {
                    DevLog.e("执行了updatedata方法-----onsuccess");
                    Gson gson = new Gson();
                    List<ObjData> bean = gson.fromJson(msg,
                            new TypeToken<List<ObjData>>() {
                            }.getType());
                    if (bean != null && bean.size() > 0) {
                        for (int i = 0; i < mapBeans.size(); i++) {//循环boxlist
                            MapBean mapBean = mapBeans.get(i);//根据不同的控件类型组装不同的数据
                            if (mapBean.getType().equals(DrawWhat.StatusSwitch)) {
                                for (int j = 0; j < bean.size(); j++) {
                                    if (mapBean.getMeasObjID() != null && !"".equals(mapBean.getMeasObjID())) {
                                        String[] arr = mapBean.getMeasObjID().split(",");
                                        if (Integer.parseInt(arr[0]) == bean.get(j).getId()) {
                                            mapBeans.get(i).setCV(bean.get(j).getCV());
                                        }
                                    }

                                }
                            } else if (mapBean.getType().equals(DrawWhat.ControlButton)) {

                            } else if (mapBean.getType().equals(DrawWhat.DynamicPicture)) {
                                for (int j = 0; j < bean.size(); j++) {
                                    if (mapBean.getMeasObjID() != null && !"".equals(mapBean.getMeasObjID())) {
                                        if (Integer.parseInt(mapBean.getMeasObjID()) == bean.get(j).getId()) {
                                            DevLog.e("动态图片的CV值：" + bean.get(j).getCV());
                                            mapBeans.get(i).setCV(bean.get(j).getCV());
                                        }
                                    }

                                }
                            } else if (mapBean.getType().equals(DrawWhat.StaticPicture)) {

                            } else if (mapBean.getType().equals(DrawWhat.StaticLabel)) {

                            } else if (mapBean.getType().equals(DrawWhat.DynamicLabel)) {//此处只有动态文本需要组装数据
                                for (int j = 0; j < bean.size(); j++) {
                                    if (mapBean.getMeasObjID() != null && !"".equals(mapBean.getMeasObjID())) {
                                        if (Integer.parseInt(mapBean.getMeasObjID()) == bean.get(j).getId()) {
                                            mapBeans.get(i).setCV(bean.get(j).getCV());
                                        }
                                    }

                                }
                            }

                        }
                        DevLog.e("数据重新组装完成");
//                        mapView.resetBitmap();
//                        mapView.draw(mainPage, mapBeans);
//                        ToastUtil.getInstance().show(getActivity(), "不得了啊");
                    } else {
//                        ToastUtil.getInstance().show(getActivity(),"获取到的数据为空");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    DevLog.e("装载数据完成，执行下一个方法");
                    getClearAlarmData(mapBeans, flag);
                }
            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    private Timer timer = new Timer();

    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DevLog.e("执行了操作");
                MessageEvent event = new MessageEvent();
                event.what = MessageEvent.UPDATE_INDEX_DATA;
                EventBus.getDefault().post(event);
            }
        }, 8000, 5000);
    }

    private String locationState;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.UPDATE_INDEX_DATA://更新首页数据
                updateData(HomeFragment.this.mapBeans, false);
                break;
            case MessageEvent.LOAD_INDEX_PAGE://加载主页数据

//                mapView.setBackgroundPicture();
                mainPage = (MainPage) event.obj;
                locationState = event.state;
                tv_title.setText(mainPage.getName());
                DevLog.e("加载主页数据:homefragment" + mainPage.getBackgroundImage());
                getPage(mainPage.getID() + "");
                break;
//            case MessageEvent.LOAD_INDEX_PAGE_ALARM:
//                String pageId = (String) event.obj;
//                getPage(pageId + "");
//                break;
        }
    }

    public void loadIndexPageALARM(String pageId) {
        getPage(pageId + "");
    }

    private void postControl(ExAttrs exAttrs, int cv) {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        if (cv == 0) {
            cv = 1;
        }
        new ControlModel().postControl(getActivity(), id, exAttrs.getSwitchMeasObjID(), cv + "", new ICongrolView() {
            @Override
            public void success(String msg) {

            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    /*假数据部分****************************/
//    private void getJMainPage() {
//        JSONObject obj = AssetsUtils.getInstance().getJsonObjFile(getActivity(), "mainpage.json");
//        Gson gson = new Gson();
//        MainPage bean = gson.fromJson(obj.toString(),
//                new TypeToken<MainPage>() {
//                }.getType());
//        if (bean != null) {
//            HomeFragment.this.mainPage = bean;
//            getJBoxList();
//        }
//    }

//    private void getJBoxList() {
//        JSONArray obj = AssetsUtils.getInstance().getJsonArrsFile(getActivity(), "boxlist.json");
//        Gson gson = new Gson();
//        List<MapBean> bean = gson.fromJson(obj.toString(),
//                new TypeToken<List<MapBean>>() {
//                }.getType());
//        if (bean != null && bean.size() > 0) {
//            HomeFragment.this.mapBeans = bean;
//            mapView.resetBitmap();
//            mapView.draw(mainPage, bean);
//            getUpdateData();
//        }
//    }

    //    private void getUpdateData() {
//        JSONArray obj = AssetsUtils.getInstance().getJsonArrsFile(getActivity(), "update.json");
//        Gson gson = new Gson();
//        List<ObjData> bean = gson.fromJson(obj.toString(),
//                new TypeToken<List<ObjData>>() {
//                }.getType());
//        if (bean != null && bean.size() > 0) {
//            for (int i = 0; i < mapBeans.size(); i++) {//循环boxlist
//                MapBean mapBean = mapBeans.get(i);//根据不同的控件类型组装不同的数据
//                if (mapBean.getType().equals(DrawWhat.StatusSwitch)) {
//                    for (int j = 0; j < bean.size(); j++) {
//                        if (mapBean.getMeasObjID() != null && !"".equals(mapBean.getMeasObjID())) {
//                            String[] arr = mapBean.getMeasObjID().split(",");
//                            if (Integer.parseInt(arr[0]) == bean.get(j).getId()) {
//                                mapBeans.get(i).setCV(bean.get(j).getCV());
//                            }
//                        }
//
//                    }
//                } else if (mapBean.getType().equals(DrawWhat.ControlButton)) {
//
//                } else if (mapBean.getType().equals(DrawWhat.DynamicPicture)) {
//                    for (int j = 0; j < bean.size(); j++) {
//                        if (mapBean.getMeasObjID() != null && !"".equals(mapBean.getMeasObjID())) {
//                            if (Integer.parseInt(mapBean.getMeasObjID()) == bean.get(j).getId()) {
//                                DevLog.e("动态图片的CV值："+bean.get(j).getCV());
//                                mapBeans.get(i).setCV(bean.get(j).getCV());
//                            }
//                        }
//
//                    }
//                } else if (mapBean.getType().equals(DrawWhat.StaticPicture)) {
//
//                } else if (mapBean.getType().equals(DrawWhat.StaticLabel)) {
//
//                } else if (mapBean.getType().equals(DrawWhat.DynamicLabel)) {//此处只有动态文本需要组装数据
//                    for (int j = 0; j < bean.size(); j++) {
//                        if (mapBean.getMeasObjID() != null && !"".equals(mapBean.getMeasObjID())) {
//                            if (Integer.parseInt(mapBean.getMeasObjID()) == bean.get(j).getId()) {
//                                mapBeans.get(i).setCV(bean.get(j).getCV());
//                            }
//                        }
//
//                    }
//                }
//
//
//            }
//            DevLog.e("数据重新组装完成");
//            mapView.resetBitmap();
//            mapView.draw(mainPage, mapBeans);
//        }
//    }
    List<ZJAlarmBean> alarms = new ArrayList<>();

    /**
     * 更新有新告警的组件
     *
     * @param data
     */
    public void updateAlarmData(GaoJingDataList data, int type) {
        if (data != null) {
            for (int i = 0; i < mapBeans.size(); i++) {//循环boxlist
                MapBean mapBean = mapBeans.get(i);//根据不同的控件类型组装不同的数据
                if (mapBean.getType().equals(DrawWhat.StatusSwitch)) {
                    if (mapBean.getMeasObjID() != null && !"".equals(mapBean.getMeasObjID())) {
                        String[] arr = mapBean.getMeasObjID().split(",");
                        if (Integer.parseInt(arr[0]) == data.getMeasObjID()) {
                            DevLog.e("有新的告警进来了");
                        }
                    }

                } else if (mapBean.getType().equals(DrawWhat.ControlButton)) {

                } else if (mapBean.getType().equals(DrawWhat.DynamicPicture)) {
                    if (mapBean.getMeasObjID() != null && !"".equals(mapBean.getMeasObjID())) {
                        if (Integer.parseInt(mapBean.getMeasObjID()) == data.getMeasObjID()) {
                            DevLog.e("已经检测到了动态图片" + mapBean.getMeasObjID() + "|" + data.getMeasObjID());
                            if (type == 2) {
                                mapBeans.get(i).setAlarmLevel(-1);
                            } else {
                                mapBeans.get(i).setAlarmLevel(data.getAlarmSeverity());
                            }

                        }
                    }

                } else if (mapBean.getType().equals(DrawWhat.StaticPicture)) {
                } else if (mapBean.getType().equals(DrawWhat.StaticLabel)) {

                } else if (mapBean.getType().equals(DrawWhat.DynamicLabel)) {//此处只有动态文本需要组装数据
                    if (mapBean.getMeasObjID() != null && !"".equals(mapBean.getMeasObjID())) {
                        DevLog.e("动态文本的id：" + mapBean.getMeasObjID() + "|" + data.getMeasObjID());
                        if (Integer.parseInt(mapBean.getMeasObjID()) == data.getMeasObjID()) {
                            DevLog.e("有新的告警进来了");
                            mapBeans.get(i).setAlarmLevel(data.getAlarmSeverity());
                        }
                    }

                } else if (mapBean.getType().equals(DrawWhat.Distribute)) {//周界安防组件
                    if (mapBean.getMeasObjID() != null && !"".equals(mapBean.getMeasObjID())) {
                        DevLog.e("动态文本的id：" + mapBean.getMeasObjID() + "|" + data.getMeasObjID());
                        if (Integer.parseInt(mapBean.getMeasObjID()) == data.getMeasObjID()) {
                            DevLog.e("有新的告警进来了】】】】】】】】】");
                            ZJAlarmBean zjAlarmBean = new ZJAlarmBean();
                            zjAlarmBean.setAlarmSeverity(data.getAlarmSeverity() + "");
                            zjAlarmBean.setAlarmSource(data.getAlarmSource() + "");
                            zjAlarmBean.setAlarmType(data.getAlarmType().getName() + "");
                            zjAlarmBean.setAlarmTime(data.getRaisedTime());
                            zjAlarmBean.setAlarmId(data.getAlarmID() + "");
                            alarms.add(zjAlarmBean);
//                            mapBeans.get(i).setAlarmLevel(data.getAlarmSeverity());
//                            DevLog.e("测试这个东东看看能否优化一下：" + data.getAlarmSource());
//                            mapBeans.get(i).setAlarmSource(data.getAlarmSource());
//                            mapBeans.get(i).setAlarmType(data.getAlarmType());
                        }
                    }
                } else if (mapBean.getType().equals(DrawWhat.NetVideo)) {//视频告警

                }
            }

        }

    }

    /**
     * 每次切换页面都需要加载这个方法，用来加载旧的告警信息
     *
     * @param bean
     */
    private void getClearAlarmData(List<MapBean> bean, final boolean flag) {
        StringBuffer objIds = new StringBuffer();
        if (bean != null) {
            for (int i = 0; i < bean.size(); i++) {
                String dataid = bean.get(i).getMeasObjID();
                if (dataid != null && !"".equals(dataid)) {
                    objIds.append(bean.get(i).getMeasObjID());
                    objIds.append(",");
                }
            }
            if (objIds.length() > 0) {
                objIds.deleteCharAt(objIds.length() - 1);
            }
//            objIds.append("1101,70001,1104,70001,70001,70001,1104,");
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
                        alarms.clear();
                        for (int i = 0; i < datas.size(); i++) {
                            updateAlarmData(datas.get(i), 1);

                        }
                    }
                }
                if (flag) {
                    mapView.setBackgroundPicture();
                }
                DevLog.e("数据重新组装完成");
                mapView.resetBitmap();
                if(mainPage!=null){
                    DevLog.e("mainPage不为空");
                }else{
                    DevLog.e("mainPage为空");
                }
                mapView.draw(mainPage, mapBeans, alarms);
            }

            @Override
            public void showToast(String msg) {

            }
        });
    }
}
