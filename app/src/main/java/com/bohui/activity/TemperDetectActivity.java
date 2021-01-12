package com.bohui.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.bean.MainPage;
import com.bohui.linchart.LineChartManager;
import com.bohui.model.GetCharDataModel;
import com.bohui.model.IconModel;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.AssetsUtils;
import com.bohui.utils.DevLog;
import com.bohui.utils.Screen;
import com.bohui.view.IIconView;
import com.bohui.view.IPageView;
//import com.githang.statusbar.StatusBarCompat;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/21 0021.
 */

public class TemperDetectActivity extends Activity implements View.OnClickListener {
    private Context context;
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.tv_title)
    TextView tv_title;
    String[] ids = null;
    private List<List<Float>> datas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperdetecct);
        ActivityUtil.getInstance().addSmaillActivitys(this);
        ButterKnife.bind(this);
        ids = getIntent().getStringExtra("id").split(",");
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
        context = this;
//        initData();
//        initView(initData());
        if (ids != null) {
            for (int i = 0; i < ids.length; i++) {
                getIconData(ids[i]);
            }
        }
        getChartData(getIntent().getStringExtra("id"));
    }

    private void initView(List<List<Float>> yValues) {
        DevLog.e("集合大小：" + datas.size());
        iv_header_left.setOnClickListener(this);
        tv_title.setText(getResources().getString(R.string.temper_detection));
        LineChart lineChart = (LineChart) findViewById(R.id.line_chart2);
        LineChartManager lineChartManager = new LineChartManager(lineChart, context);
//        lineChart.setMinimumHeight(Screen.getHeightPixels(this));

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
        lineChartManager.hide(context);

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
        xAxis.setLabelRotationAngle(45);   // 标签倾斜


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_header_left:
                finish();
                break;
        }
    }

    private List<Float> initData() {
        try {
            List<Float> datas = new ArrayList<>();
            JSONArray arrs = AssetsUtils.getInstance().getJsonArrsFile(this, "tubiao.json");
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
        new IconModel().getIconData(this, id, measObjId, "false", "false", 0 + "", 0 + "", new IIconView() {
            @Override
            public void success(String msg) {
                DevLog.e("sdadfasdfasdfd");
                List<Float> datas = new ArrayList<>();
                String[] strArr = msg.toString().replace("[", "").replace("]", "").split(",");
                DevLog.e(strArr.length + "-------------");
                for (int i = 0; i < strArr.length; i++) {
                    if (strArr[i] != null&&!"null".equals(strArr[i])) {
                        datas.add(Float.parseFloat(strArr[i]));
                    }

                }
                TemperDetectActivity.this.datas.add(datas);
                initView(TemperDetectActivity.this.datas);
            }

            @Override
            public void showToast(String msg) {

            }
        });
    }
    private void getChartData(String measObjIds){
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new GetCharDataModel().getChardata(this, id, measObjIds, new IPageView() {
            @Override
            public void success(String msg) {

            }

            @Override
            public void showToast(String msg) {

            }
        });
    }
}
