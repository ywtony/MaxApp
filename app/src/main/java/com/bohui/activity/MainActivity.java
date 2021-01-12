package com.bohui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.adpter.ProjectAdapter;
import com.bohui.bean.AlarmType;
import com.bohui.bean.ExAttrs;
import com.bohui.bean.GaoJingDataList;
import com.bohui.bean.GaoJingUpdateBean;
import com.bohui.bean.GaojingBean;
import com.bohui.bean.MainPage;
import com.bohui.bean.MapBean;
import com.bohui.bean.MessageEvent;
import com.bohui.bean.ObjData;
import com.bohui.dialog.LoadingDialog;
import com.bohui.entity.Gasentitiy;
import com.bohui.fragment.EquipmentFragment;
import com.bohui.fragment.GaojingFragment;
import com.bohui.fragment.HomeFragment;
import com.bohui.fragment.MapFragment;
import com.bohui.fragment.MeFragment;
import com.bohui.fragment.SubmitGJFragment;
import com.bohui.fragment.TubiaoFragment;
import com.bohui.inter.DrawWhat;
import com.bohui.location.LocationUtils;
import com.bohui.model.CountAllUnclearedAlarmModel;
import com.bohui.model.GetAlarmMsgModel;
import com.bohui.model.LoginOutModel;
import com.bohui.model.MapDataModel;
import com.bohui.model.PageListModel;
import com.bohui.model.PageModel;
import com.bohui.model.ResourceModel;
import com.bohui.model.SearchAllAlarmTypeModel;
import com.bohui.presenter.IBasePresenter;
import com.bohui.presenter.IBasePresneterImpl;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.AlarmTypeUtils;
import com.bohui.utils.AssetsUtils;
import com.bohui.utils.DataUtils;
import com.bohui.utils.DevLog;
import com.bohui.utils.JSONUtil;
import com.bohui.utils.PermissionManager;
import com.bohui.utils.StatusBarUtil;
import com.bohui.utils.ToastUtils;
import com.bohui.view.IBaseView;
import com.bohui.view.IClearGaojingView;
import com.bohui.view.IGaoJingView;
import com.bohui.view.IMapDataView;
import com.bohui.view.IPageListView;
import com.bohui.view.IResourceView;
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

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.ionic.starter.activity.Demo1ListActivity;
import io.ionic.starter.utils.PreferenceUtils;

/**
 * App主类
 */
public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener, PermissionManager.PermissionsResultListener {
    public static final String WEATHER_INTENT_NAME = "weather";
    @BindView(R.id.rb_home)
    RadioButton rb_home;
    @BindView(R.id.rb_enuequipement)
    RadioButton rb_enuequipement;
    @BindView(R.id.rb_me)
    RadioButton rb_me;
    @BindView(R.id.rb_submitgj)
    RadioButton rb_submitgj;
    private RadioButton[] radioButtons;
    @BindView(R.id.rg_tab)
    RadioGroup radioGroup;
    @BindView(R.id.list_project)
    ListView list_project;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.main_tv_news)
    TextView tv_news;
    private Context mContext;
    private FragmentManager fragmentManager;//模块管理
    private HomeFragment homeFragment;//首页
    private GaojingFragment eqFragment;//设备
    private MeFragment meFragment;//我的
    private SubmitGJFragment submitGJFragment;//提交告警
    private Fragment mFragment;
    private MapFragment mapFragment;
    private TubiaoFragment tubiaoFragment;
    private int mClickCount = 0;
    private long mFirstClick = 0; ///第一次点击
    private long mLastClick = 0; ///第二次点击
    private Resources rs;
    private long firstTime = 0L;
    private FragmentTransaction transaction;
    private ProjectAdapter adapter;
    private List<MainPage> list;
    private LoadingDialog loadingdialog;
    public static MainActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        instance = this;
        setContentView(R.layout.activity_mains);
        ActivityUtil.getInstance().addSmaillActivitys(this);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //StatusBarUtil.setWindowStatusBarColor(this, getResources().getColor(R.color.bg_header));
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        fragmentManager = getSupportFragmentManager();//获得片段管理
        list = new ArrayList<>();
        initView();
        radioButtons = new RadioButton[]{rb_home, rb_enuequipement, rb_me};
        rb_home.setChecked(true);
        rb_home.performClick();//模拟人点击
//        getGaojingCount();
//        startAlarmTimer();
        //获取所有的告警类型，登录后立马加载并把数据缓存到本地
        AlarmTypeUtils.getInstance().setAlarmTypeData(this);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            DevLog.e("当前系统为6.0以下系统");
        } else {
            PermissionManager.requestPermission(this, "", 1, this, Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        loadingdialog = new LoadingDialog(this);
        new LocationUtils(MainActivity.this);
        final NetDEVSDK oNetSDKDemo = new NetDEVSDK();
        oNetSDKDemo.NETDEV_Init();
        NetDEVSDK.NETDEV_SetT2UPayLoad(800);
//        getResource();//获取资源文件路径接口

        //android10以后模拟生成UUID
        pUtils = new PreferenceUtils(this);
        if (pUtils.getUUID() == null || "".equals(pUtils.getUUID())) {
            pUtils.saveUUID();
        }

    }

    private PreferenceUtils pUtils;

    private void showDialog() {
        if (loadingdialog != null && !loadingdialog.isShowing()) {
            loadingdialog.show();
        }
    }

    private void dimssDialog() {
        if (loadingdialog != null && loadingdialog.isShowing()) {
            loadingdialog.dismiss();
        }
    }


    public void initView() {
        radioGroup.setOnCheckedChangeListener(this);
        rb_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFragment != null && mFragment.toString().indexOf("HomeFragment") != -1) {

                    if (mFirstClick != 0 && System.currentTimeMillis() - mFirstClick > 3 * 100) {
                        mClickCount = 0;
                    }
                    mClickCount++;
                    if (mClickCount == 1) {
                        mFirstClick = System.currentTimeMillis();
                    } else if (mClickCount == 2) {
                        mLastClick = System.currentTimeMillis();
                        if (mLastClick - mFirstClick < 3 * 100) {
                        }
                    }
                }
            }
        });
        mFragment = new HomeFragment();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content, mFragment);//替换片段
        transaction.commit();
        adapter = new ProjectAdapter(mContext);
        list_project.setAdapter(adapter);
        list_project.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
//                Intent intent = new Intent(MainActivity.this, GasDetailsActivity.class);
//                intent.putExtra(WEATHER_INTENT_NAME, DataUtils.createWeather());
//                startActivity(intent);
//                showDialog();
                DevLog.e(JSONUtil.getInstance().getString(list.get(i)));
                MainPage page = list.get(i);
                switch (page.getPageType()) {
                    case 3://宇视摄像头
                        loginYushiVideo(page);
                        break;
                    case 2://H5page
                        toTaskManager(page);
                        break;
                    default:
                        toIndex(i);
                        break;
                }


            }
        });
    }

    /**
     * 去任务管理
     */
    private void toTaskManager(MainPage page) {
        String json = page.getExAttrs().replaceAll("\\\\", "");
        ExAttrs bean = (ExAttrs) JSONUtil.getInstance().getObject(json, ExAttrs.class);
        if (bean == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("url", bean.getUrl());
        intent.setClass(this, TaskManagerActivity.class);
        startActivity(intent);
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
                AlertDialog.Builder oBuilder = new AlertDialog.Builder(this);
                oBuilder.setTitle("Fail");
                oBuilder.setMessage("Report Mobile Info Fail.");
                oBuilder.setPositiveButton("OK", null);
                oBuilder.show();
            } else {
                yushiVideo(bean);
            }
        } else {
            AlertDialog.Builder oBuilder = new AlertDialog.Builder(this);
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
        intent.setClass(this, Demo1ListActivity.class);
        startActivity(intent);
    }

    @SuppressLint("WrongConstant")
    private void toIndex(int i) {
        BoHuiApplication.getInstance().setMainHome(true);
        MessageEvent event = new MessageEvent();
        event.what = MessageEvent.LOAD_INDEX_PAGE;
        event.obj = list.get(i);
        EventBus.getDefault().post(event);
        BoHuiApplication.getInstance().setLatlng("");
        drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        DevLog.e("执行了：onCheckedChanged");
        Fragment fragment = getFragmentByRadioResId(0, checkedId);
        if (null == fragment) {
            return;
        }
        transaction = fragmentManager.beginTransaction();
        if (mFragment == null) {
            transaction.replace(R.id.content, fragment);//替换片段
            transaction.commit();
        } else {
//            transaction.replace(R.id.content,fragment);
//            transaction.commit();
            if (!fragment.isAdded()) {
                transaction.hide(mFragment).add(R.id.content, fragment).commit();
            } else {
                transaction.hide(mFragment).show(fragment).commit();
            }
        }
        mFragment = fragment;
    }

    private void changeFragment(int checkId) {
        try {

            DevLog.e("执行前。。。。。|||||");
            Fragment fragment = getFragmentByRadioResId(1, checkId);
            if (null == fragment) {
                return;
            }
            transaction = fragmentManager.beginTransaction();
            DevLog.e("执行后。。。。。|||||");
            if (mFragment == null) {
                transaction.replace(R.id.content, fragment);//替换片段
                transaction.commit();
            } else {
                transaction.replace(R.id.content, fragment);//替换片段
                transaction.commit();
//                if (!fragment.isAdded()) {
//                    DevLog.e("执行崩溃中。。。。。|||||");
////                    transaction.remove(fragment).commit();
//                    transaction.hide(mFragment).remove(fragment).add(R.id.content, fragment).commit();
//                    DevLog.e("执行崩溃后。。。。。|||||");
//                } else {
//                    transaction.hide(mFragment).show(fragment).commit();
//                }
            }
            mFragment = fragment;
        } catch (Exception e) {
            DevLog.e("Exception——告警：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 根据radio资源id获取不同的Fragment
     *
     * @param resId radio资源id
     * @return
     */
    private Fragment getFragmentByRadioResId(int type, int resId) {
        changeTextColor(resId);
        Fragment fragment = null;
        switch (resId) {
            case R.id.rb_home:
                if (BoHuiApplication.getInstance().getFragmentType() == 1) {//地图
//                    mapFragment = MapFragment.getInstance();
                    if (mapFragment == null) {
                        mapFragment = new MapFragment();
                    }
                    fragment = mapFragment;
                } else if (BoHuiApplication.getInstance().getFragmentType() == 2) {//图表
                    if (tubiaoFragment == null) {
                        tubiaoFragment = new TubiaoFragment();
                    }
                    fragment = tubiaoFragment;
                } else {
                    if (homeFragment == null) {
                        homeFragment = new HomeFragment();
                    }
                    BoHuiApplication.getInstance().setClickHome(true);
                    homeFragment.type = type;
                    fragment = homeFragment;
                }
                onOpenSlide();
                break;
            case R.id.rb_enuequipement:
                if (eqFragment == null) {
                    eqFragment = new GaojingFragment();
                }
                fragment = eqFragment;
                onOpenSlide();
                break;
            case R.id.rb_me:
                if (meFragment == null) {
                    meFragment = new MeFragment();
                }
                fragment = meFragment;
                onProhibitSlide();
                break;
            case R.id.rb_submitgj:
                if (submitGJFragment == null) {
                    submitGJFragment = new SubmitGJFragment();
                }
                fragment = submitGJFragment;
                onProhibitSlide();
                break;
        }
        return fragment;

    }


    @Override
    public void onBackPressed() {
        if (firstTime + 2000 > System.currentTimeMillis()) {
            logout();
            super.onBackPressed();
        } else {
            ToastUtils.show(MainActivity.this, "再按一次退出程序");
        }
        firstTime = System.currentTimeMillis();
    }

    //更换字体颜色
    public void changeTextColor(int id) {
        if (rs == null) {
            rs = getResources();
        }
        for (RadioButton radioButton : radioButtons) {
            Log.e("jackFire", "rb_enuequipement" + rb_enuequipement.getId());
            if (radioButton.getId() == id) {
                radioButton.setTextColor(rs.getColor(R.color.theme_color));
            } else {
                radioButton.setTextColor(rs.getColor(R.color.tv_menu_color));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
//        if (id == null || "".equals(id)) {
//            ActivityUtil.getInstance().leftToRightActivity(this, LoginActivity.class);
//        }

    }

    public void openDrawer() {
//        initData();
        getPageList();

    }

    //禁止滑动
    protected void onProhibitSlide() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }


    //打开手势滑动
    protected void onOpenSlide() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        DevLog.e("执行了ondestroy方法");

    }

    private void logout() {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new LoginOutModel().logout(this, id, new IClearGaojingView() {
            @Override
            public void success(String msg) {
                BoHuiApplication.getInstance().getAuthConfig().removeToken();
                ActivityUtil.getInstance().finishSmaillActivitys();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                finish();
            }
        });
    }

    /**
     * 获取主页属性
     */
    private void getPageList() {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new PageListModel().getPageList(this, id, new IPageListView() {
            @SuppressLint("WrongConstant")
            @Override
            public void success(String msg) {
                Gson gson = new Gson();
                List<MainPage> bean = gson.fromJson(msg.toString(),
                        new TypeToken<List<MainPage>>() {
                        }.getType());
                if (bean != null && bean.size() > 0) {
                    MainActivity.this.list = bean;
                    if (MainActivity.this.list != null) {
                        adapter.setList(MainActivity.this.list);
                        list_project.setAdapter(adapter);
                    }
                }
                drawerLayout.openDrawer(Gravity.START);
            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    private void initData() {
        JSONArray arrs = AssetsUtils.getInstance().getJsonArrsFile(this, "pagelist.json");
        Gson gson = new Gson();
        List<MainPage> bean = gson.fromJson(arrs.toString(),
                new TypeToken<List<MainPage>>() {
                }.getType());
        if (bean != null && bean.size() > 0) {
            this.list = bean;
            if (this.list != null) {
                adapter.setList(this.list);
                list_project.setAdapter(adapter);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(final MessageEvent event) {
        switch (event.what) {
            case MessageEvent.UPDATE_MSG_COUNT://更新告警消息数量
                tv_news.setText(BoHuiApplication.getInstance().getCount() + "");
                break;
            case MessageEvent.UPDATE_ALARM_MSG://定时更新告警信息
                getAlarmMsg();
                getGaojingCount();
                break;
            case MessageEvent.LOAD_INDEX_PAGE_ALARM:
                //切换tab
                rb_home.setChecked(true);
                //加载定位到的页面
                String pageId = (String) event.obj;
                homeFragment.loadIndexPageALARM(pageId);
                break;
            case MessageEvent.TO_MAPVIEW1: {
                DevLog.e("得到了一个mapview的地址");
                BoHuiApplication.getInstance().setFragmentType(1);
                changeFragment(R.id.rb_home);
                DevLog.e("MainActivity_11111:显示mapview");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MessageEvent event1 = new MessageEvent();
                        event1.what = MessageEvent.TO_MAPVIEW;
                        event1.obj = event.obj;
                        EventBus.getDefault().post(event1);
                        dimssDialog();
                    }
                }, 1000);

            }
            break;
            case MessageEvent.TO_TUBIA01: {

                BoHuiApplication.getInstance().setFragmentType(2);
                changeFragment(R.id.rb_home);
                DevLog.e("MainActivity_11111:显示图表");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MessageEvent event1 = new MessageEvent();
                        event1.what = MessageEvent.TO_TUBIA0;
                        event1.obj = event.obj;
                        EventBus.getDefault().post(event1);
                        dimssDialog();
                    }
                }, 1000);

            }
            break;
            case MessageEvent.TO_INDEX1://定位

                rb_home.setChecked(true);
                BoHuiApplication.getInstance().setFragmentType(0);
                changeFragment(R.id.rb_home);
                DevLog.e("MainActivity_11111:显示首页");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        MessageEvent event1 = new MessageEvent();
                        event1.what = MessageEvent.LOAD_INDEX_PAGE;
//                        MainPage mainPage = new MainPage();
//                        mainPage.setID(Integer.parseInt(event.type));
                        event1.obj = event.obj;
                        event1.state = event.state;
                        EventBus.getDefault().post(event1);
                        dimssDialog();
                    }
                }, 1000);


                break;
//            case MessageEvent.TO_LOCATION://定位当指定的坐标位置
//                rb_home.setChecked(true);
//                BoHuiApplication.getInstance().setFragmentType(0);
//                changeFragment(R.id.rb_home);
//                DevLog.e("MainActivity_11111:显示首页");
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        MessageEvent event1 = new MessageEvent();
//                        event1.what = MessageEvent.SHOW_LOCATION;
////                        MainPage mainPage = new MainPage();
////                        mainPage.setID(Integer.parseInt(event.type));
//                        event1.obj = event.obj;
//                        event1.state = event.state;
//                        EventBus.getDefault().post(event1);
//                        dimssDialog();
//                    }
//                }, 1000);
//                break;
        }
    }

    /**
     * 初始化告警消息数量
     */
    private void getGaojingCount() {
        DevLog.e("执行了家在告警数据量接口");
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new CountAllUnclearedAlarmModel().getCountAllUnclearedAlarm(this, id, new IGaoJingView() {
            @Override
            public void success(String msg) {
                try {
                    JSONObject obj = new JSONObject(msg);
                    String count = obj.getString("count");
                    BoHuiApplication.getInstance().setCount(Integer.parseInt(count));
                    MessageEvent event = new MessageEvent();
                    event.what = MessageEvent.UPDATE_MSG_COUNT;
                    event.count = Integer.parseInt(count);
                    EventBus.getDefault().post(event);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                DevLog.e(msg);
            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    private void getAlarmMsg() {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new GetAlarmMsgModel().getAlarmMsgData(this, id, new IGaoJingView() {
            @Override
            public void success(String msg) {
                DevLog.e("告警消息" + msg);
                if (!"[]".equals(msg)) {
                    Gson gson = new Gson();
                    List<GaoJingUpdateBean> datas = gson.fromJson(msg.toString(),
                            new TypeToken<List<GaoJingUpdateBean>>() {
                            }.getType());
                    if (datas != null) {
                        for (int i = 0; i < datas.size(); i++) {
                            DevLog.e("告警的objid:" + datas.get(i).getAlarm().getMeasObjID());
                            GaoJingUpdateBean data = datas.get(i);
                            if (data.getAlarmMsgType() == 1) {//新告警
                                if (homeFragment != null) {
                                    homeFragment.updateAlarmData(data.getAlarm(), data.getAlarmMsgType());
                                }
                                BoHuiApplication.getInstance().setCount(BoHuiApplication.getInstance().getCount() + 1);
                                MessageEvent event = new MessageEvent();
                                event.what = MessageEvent.UPDATE_MSG_COUNT;
                                event.obj = data;
                                EventBus.getDefault().post(event);

                            } else if (data.getAlarmMsgType() == 3) {//确认告警
                                try {
                                    if (homeFragment != null) {
                                        homeFragment.updateAlarmData(data.getAlarm(), data.getAlarmMsgType());
                                    }
                                    BoHuiApplication.getInstance().setCount(BoHuiApplication.getInstance().getCount() - 1);
                                    MessageEvent event = new MessageEvent();
                                    event.what = MessageEvent.UPDATE_MSG_COUNT;
                                    event.obj = data;
                                    EventBus.getDefault().post(event);
                                } catch (Exception e) {
                                    e.printStackTrace();
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

    private Timer timer = new Timer();

    private void startAlarmTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                DevLog.e("告警消息定时刷新");
                MessageEvent event = new MessageEvent();
                event.what = MessageEvent.UPDATE_ALARM_MSG;
                EventBus.getDefault().post(event);
            }
        }, 10000, 8000);
    }


    @Override
    public void onPermissionGranted(int requestCode) {
        DevLog.e("开始定位");
        new Thread() {
            @Override
            public void run() {
                super.run();
                new LocationUtils(MainActivity.this);
            }
        }.start();
    }

    @Override
    public void onPermissionDenied(int requestCode) {
        DevLog.e("定位失败");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getGaojingCount();
        startAlarmTimer();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (submitGJFragment != null) {
            submitGJFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getResource() {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new ResourceModel().getResource(this, id, new IResourceView() {
            @Override
            public void success(String msg) {

            }
        });
    }

}

