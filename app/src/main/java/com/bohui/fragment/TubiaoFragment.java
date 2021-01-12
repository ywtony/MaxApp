package com.bohui.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.activity.FaultListActivity;
import com.bohui.activity.MainActivity;
import com.bohui.activity.TemperDetectActivity;
import com.bohui.adpter.ImageAdapter;
import com.bohui.bean.CustomGjBean;
import com.bohui.bean.GaoJingUpdateBean;
import com.bohui.bean.LocalImage;
import com.bohui.bean.MainPage;
import com.bohui.bean.MapBean;
import com.bohui.bean.MessageEvent;
import com.bohui.dialog.LoadingDialog;
import com.bohui.inter.DrawWhat;
import com.bohui.linchart.LineChartManager;
import com.bohui.model.GetCharDataModel;
import com.bohui.model.IconModel;
import com.bohui.model.MapDataModel;
import com.bohui.model.PageModel;
import com.bohui.utils.AssetsUtils;
import com.bohui.utils.DataUtils;
import com.bohui.utils.DevLog;
import com.bohui.utils.JSONUtil;
import com.bohui.utils.Screen;
import com.bohui.view.IIconView;
import com.bohui.view.IMapDataView;
import com.bohui.view.IPageView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 图表
 */
public class TubiaoFragment extends Fragment implements View.OnClickListener {
    private Context context;
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.iv_header_menu)
    ImageView iv_header_menu;
    @BindView(R.id.iv_header_right)
    ImageView iv_header_right;
    @BindView(R.id.tv_title)
    TextView tv_title;
    String[] ids = null;
    private List<List<Float>> datas = new ArrayList<>();

    private String data = null;
    private View view;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_temperdetecct, container, false);
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        if (data != null) {
            initChartsData(data);
        }
        iv_header_left.setVisibility(View.GONE);
        iv_header_menu.setVisibility(View.VISIBLE);
        iv_header_right.setVisibility(View.GONE);
        iv_header_menu.setOnClickListener(this);
        iv_header_right.setOnClickListener(this);
        tv_title.setText("温度曲线");
        return view;
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

    private void initChartsData(String id) {
        ids = id.split(",");
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
//        initData();
//        initView(initData());
        if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                getIconData(ids[i]);
            }
        }
        getChartData(id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_menu:
                if(MainActivity.instance!=null){
                    MainActivity.instance.openDrawer();
                }
                break;
            case R.id.iv_header_right:
                startActivity(new Intent(getActivity(), FaultListActivity.class));
                break;
        }
    }

    private void initView(List<List<Float>> yValues, View view) {
        DevLog.e("集合大小：" + datas.size());
        iv_header_left.setOnClickListener(this);
//        tv_title.setText(getResources().getString(R.string.temper_detection));
        LineChart lineChart = (LineChart) view.findViewById(R.id.line_chart2);
        LineChartManager lineChartManager = new LineChartManager(lineChart, context);
//        lineChart.setMinimumHeight(Screen.getHeightPixels(getActivity()));

        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i <= yValues.get(0).size(); i++) {
            xValues.add((float) i);
        }

        //设置y轴的数据()
//        List<List<Float>> yValues = new ArrayList<>();
//        for (int i = 0; i < 3; i++) {
//            List<Float> yValue = new ArrayList<>();
//            for (int j = 0; j <= 7; j++) {
//                yValue.add((float) (Math.random() * 2 + 15));
//            }
//            yValues.add(yValue);
//        }

//        yValues.add(datas);

        //颜色集合
        List<Integer> colours = new ArrayList<>();
        colours.add(getResources().getColor(R.color.yellow));
        colours.add(getResources().getColor(R.color.green));
        colours.add(getResources().getColor(R.color.red));


        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("");
        names.add("");
        names.add("");

        lineChartManager.showLineChart(xValues, yValues, names, colours);
        lineChartManager.setYAxis(100, 0, 7);
        lineChartManager.hide(getActivity());

        // 网格线：垂直于轴线对应每个值画的轴线；
        // 限制线：最值等线。
        XAxis xAxis = lineChart.getXAxis();    // 获取X轴
        YAxis yAxis = lineChart.getAxisLeft(); // 获取Y轴,mLineChart.getAxis(YAxis.AxisDependency.LEFT);也可以获取Y轴
        // 轴颜色
        yAxis.setTextColor(getResources().getColor(R.color.tv_title_color));  // 标签字体颜色
        yAxis.setTextSize(10);    // 标签字体大小，dp，6-24之间，默认为10dp
        yAxis.setTypeface(null);    // 标签字体
        yAxis.setGridColor(getResources().getColor(R.color.split_line_bg));    // 网格线颜色，默认GRAY
        yAxis.setGridLineWidth(0.5f);    // 网格线宽度，dp，默认1dp
        yAxis.setAxisLineColor(Color.WHITE);  // 坐标轴颜色，默认GRAY.测试到一个bug，假如左侧线只有1dp，
        // 那么如果x轴有线且有网格线，当刻度拉的正好位置时会覆盖到y轴的轴线，变为X轴网格线颜色，结局办法是，要么不画轴线，要么就是坐标轴稍微宽点
        xAxis.setAxisLineColor(getResources().getColor(R.color.split_line_bg));
        yAxis.setAxisLineWidth(1);  // 坐标轴线宽度，dp，默认1dp
        // X轴更多属性
        xAxis.setLabelRotationAngle(0);   // 标签倾斜


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        ButterKnife.unbind(this);
    }


    private List<Float> initData() {
        try {
            List<Float> datas = new ArrayList<>();
            JSONArray arrs = AssetsUtils.getInstance().getJsonArrsFile(getActivity(), "tubiao.json");
            JSONArray arr = arrs.getJSONArray(0);
            String[] strArr = arr.toString().replace("[", "").replace("]", "").split(",");
            DevLog.e(strArr.length + "-------------");
            for (int i = 0; i < strArr.length; i++) {
                datas.add(Float.parseFloat(strArr[i]));
            }
            return datas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void getIconData(String measObjId) {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new IconModel().getIconData(getActivity(), id, measObjId, "false", "false", 0 + "", 0 + "", new IIconView() {
            @Override
            public void success(String msg) {
                DevLog.e("sdadfasdfasdfd");
                List<Float> datas = new ArrayList<>();
                String[] strArr = msg.toString().replace("[", "").replace("]", "").split(",");
                DevLog.e(strArr.length + "-------------");
                for (int i = 0; i < strArr.length; i++) {
                    if (strArr[i] != null && !"null".equals(strArr[i])) {
                        datas.add(Float.parseFloat(strArr[i]));
                    }

                }
                TubiaoFragment.this.datas.add(datas);
                initView(TubiaoFragment.this.datas, view);
            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    private void getChartData(String measObjIds) {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new GetCharDataModel().getChardata(getActivity(), id, measObjIds, new IPageView() {
            @Override
            public void success(String msg) {

            }

            @Override
            public void showToast(String msg) {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.TO_TUBIA0:
                this.data = (String) event.obj;
                initChartsData(this.data);
                break;
            case MessageEvent.LOAD_INDEX_PAGE://加载主页数据
                DevLog.e("加载主页数据:tubiaofragment");
                MainPage mainPage = (MainPage) event.obj;
                getPage(mainPage.getID() + "");
                break;

        }
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
                if (bean != null) {
                    getBoxList(bean, pageId);
                }
            }

            @Override
            public void showToast(String msg) {

            }
        });
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
//                                Intent intent = new Intent();
//                                intent.putExtra("data", JSONUtil.getInstance().getString(bean.get(i)));
//                                intent.setClass(getActivity(), GMapViewActivity.class);
//                                startActivity(intent);
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
}
