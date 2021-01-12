package com.bohui.linchart;

import android.content.Context;
import android.graphics.Color;

import com.bohui.R;
import com.bohui.utils.DevLog;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/21 0021.
 */

public class LineChartManager {
    private Context context;
    private LineChart lineChart;
    //左边Y轴
    private YAxis leftAxis;
    //右边Y轴
    private YAxis rightAxis;
    //X轴
    private XAxis xAxis;

    public LineChartManager(LineChart mLineChart, Context context) {
        this.lineChart = mLineChart;
        leftAxis = lineChart.getAxisLeft();
        rightAxis = lineChart.getAxisRight();
        xAxis = lineChart.getXAxis();
        this.context = context;
    }

    /**
     * 初始化LineChart
     */
    private void initLineChart() {
        lineChart.setDrawGridBackground(false);
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);
        //显示边界
        lineChart.setDrawBorders(false);

        //设置动画效果
        lineChart.animateY(1000, Easing.EasingOption.Linear);
        lineChart.animateX(1000, Easing.EasingOption.Linear);

        //折线图例 标签 设置
        Legend legend = lineChart.getLegend();

        legend.setForm(Legend.LegendForm.NONE);
        legend.setTextSize(11f);
        //显示位置
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setEnabled(false);


        //XY轴的设置
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftAxis.setAxisMinimum(0f);
        // rightAxis.setAxisMinimum(0f);


        rightAxis.setDrawAxisLine(true);
        rightAxis.setDrawGridLines(false);
        rightAxis.setTextColor(Color.TRANSPARENT);
        rightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        rightAxis.setXOffset(15f);


    }

    /**
     * 初始化曲线 每一个LineDataSet代表一条线  *  * @param lineDataSet  * @param color  * @param mode 折线图是否填充
     */
    private void initLineDataSet(LineDataSet lineDataSet, int color, boolean mode) {
        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setCircleRadius(1f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(0f);
        //设置折线图填充
        lineDataSet.setDrawFilled(mode);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER
        );
    }

    /**
     * 展示折线图(一条)  *  * @param xAxisValues  * @param yAxisValues  * @param label  * @param color
     */
    public void showLineChart(List<Float> xAxisValues, List<Float> yAxisValues, String label, int color) {
        initLineChart();
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < xAxisValues.size(); i++) {
            entries.add(new Entry(xAxisValues.get(i), yAxisValues.get(i)));
        }
        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, label);
        initLineDataSet(lineDataSet, color, true);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        LineData data = new LineData(dataSets);
        //设置X轴的刻度数
        xAxis.setLabelCount(xAxisValues.size(), true);
        /*
        xAxis.setTextSize(12);
        xAxis.setTextColor(context.getResources()
        .getColor(R.color.theme_color));*/
        lineChart.setData(data);
    }

    /**
     * 展示线性图(多条)  *  * @param xAxisValues  * @param yAxisValues 多条曲线Y轴数据集合的集合  * @param labels  * @param colours
     */
    public void showLineChart(List<Float> xAxisValues, List<List<Float>> yAxisValues, List<String> labels, List<Integer> colours) {
        try {


            initLineChart();
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            for (int i = 0; i < yAxisValues.size(); i++) {
                ArrayList<Entry> entries = new ArrayList<>();
                int x = 0;
                for (int j = 0; j < yAxisValues.get(i).size(); j++) {
//                    if (j >= xAxisValues.size()) {
//                        j = xAxisValues.size() - 1;
//
//                    }
                    x++;
                    if(x>=xAxisValues.size()){
                        x = xAxisValues.size()-1;
                    }
//                    DevLog.e("到那个位置会报outofmemary呢："+j);
                    float xx = xAxisValues.get(j);
                    float yy = yAxisValues.get(i).get(j);
                    entries.add(new Entry(xx, yy));
                }
                LineDataSet lineDataSet = new LineDataSet(entries, labels.get(i));

                initLineDataSet(lineDataSet, colours.get(i), false);
                dataSets.add(lineDataSet);

            }
            LineData data = new LineData(dataSets);
            xAxis.setLabelCount(10, true);

            lineChart.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置Y轴值  *  * @param max  * @param min  * @param labelCount
     */
    public void setYAxis(float max, float min, int labelCount) {
        if (max < min) {
            return;
        }
        leftAxis.setAxisMaximum(max);
        leftAxis.setAxisMinimum(min);
        leftAxis.setLabelCount(labelCount, false);

       /* rightAxis.setAxisMaximum(max);
        rightAxis.setAxisMinimum(min);
        rightAxis.setLabelCount(labelCount, false);*/
        lineChart.invalidate();
    }

    /**
     * 设置X轴的值  *  * @param max  * @param min  * @param labelCount
     */
    public void setXAxis(float max, float min, int labelCount) {
        xAxis.setAxisMaximum(max);
        xAxis.setAxisMinimum(min);
        xAxis.setLabelCount(labelCount, true);

        lineChart.invalidate();
    }

    /**
     * 设置高限制线  *  * @param high  * @param name
     */
    public void setHightLimitLine(float high, String name, int color) {
        if (name == null) {
            name = "高限制线";
        }
        LimitLine hightLimit = new LimitLine(high, name);
        hightLimit.setLineWidth(2f);
        hightLimit.setTextSize(10f);
        hightLimit.setLineColor(color);
        hightLimit.setTextColor(color);
        leftAxis.addLimitLine(hightLimit);
        lineChart.invalidate();
    }

    /**
     * 设置低限制线  *  * @param low  * @param name
     */
    public void setLowLimitLine(int low, String name) {
        if (name == null) {
            name = "低限制线";
        }
        LimitLine hightLimit = new LimitLine(low, name);
        hightLimit.setLineWidth(4f);
        hightLimit.setTextSize(10f);
        leftAxis.addLimitLine(hightLimit);
        lineChart.invalidate();
    }

    /**
     * 设置描述信息  *  * @param str
     */
    public void setDescription(String str) {
        Description description = new Description();
        description.setText(context.getResources().getString(R.string.distances));
        description.setTextColor(context.getResources().getColor(R.color.theme_color));
        description.setTextSize(12f);
        description.setText("");
        lineChart.setDescription(description);
        lineChart.invalidate();
    }

    public void hide(Context context) {
        lineChart.getAxisRight().setEnabled(false); // 隐藏右边 的坐标轴
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM); // 让x轴在下面
        lineChart.getXAxis().setGridColor(
                context.getResources().getColor(R.color.white));
        lineChart.getXAxis().setCenterAxisLabels(false);
        lineChart.setGridBackgroundColor(R.color.white
        );

    }
}