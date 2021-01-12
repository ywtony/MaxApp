package com.bohui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.adpter.GaoJingAdapter;
import com.bohui.adpter.HistoryGaoJingAdapter;
import com.bohui.bean.ButtomBean;
import com.bohui.bean.GaoJingDataList;
import com.bohui.bean.GaojingBean;
import com.bohui.dialog.BottomDialog;
import com.bohui.fragment.GaojingFragment;
import com.bohui.model.GaoJingModel;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.TimeUtil;
import com.bohui.utils.ToastUtil;
import com.bohui.view.IGaoJingView;
import com.dsy.datepicker.DatePicker;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 历史告警查询
 * create by yangwei
 * on 2020/11/22 14:24
 */
public class HistoryAlarmQueryActivity extends Activity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, View.OnClickListener {
    @BindView(R.id.iv_header_left)
    ImageView iv_header_left;
    @BindView(R.id.iv_header_menu)
    ImageView iv_header_menu;
    @BindView(R.id.iv_header_right)
    ImageView iv_header_right;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.listView)
    PullToRefreshListView listView;
    @BindView(R.id.tv_header_right)
    TextView tv_right;
    @BindView(R.id.tvAlarmLevel)
    TextView tvAlarmLevel;
    @BindView(R.id.tvRiskLevel)
    TextView tvRiskLevel;
    @BindView(R.id.tvStartTime)
    TextView tvStartTime;
    @BindView(R.id.tvEndTime)
    TextView tvEndTime;
    private int page = 1;// 当前页码
    private int totalPages;// 总页数
    private boolean isRefresh = true;
    private List<GaoJingDataList> datas = new ArrayList<>();
    private HistoryGaoJingAdapter adapter = null;
    /**
     *
     */
    private String alarmLevel = "";
    private String riskLevel = "";
    private String startTime = "";
    private String endTime = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_alarm_query_layout);
        ButterKnife.bind(this);
        initViews();
        getHistoryAlarmList();
    }

    private void initViews() {
        iv_header_left.setVisibility(View.VISIBLE);
        iv_header_left.setOnClickListener(this);
        tv_title.setText(getResources().getString(R.string.menu_histroy_alarm));
        listView.setOnItemClickListener(this);
        listView.setOnRefreshListener(this);
        tvAlarmLevel.setOnClickListener(this);
        tvRiskLevel.setOnClickListener(this);
        tvStartTime.setOnClickListener(this);
        tvEndTime.setOnClickListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        initListView();
        adapter = new HistoryGaoJingAdapter(this);
        listView.setAdapter(adapter);
//        tv_right.setText("提交告警");
//        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(this);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            stopRefresh();
        }

        ;
    };

    /**
     * 停止刷新
     */
    private void stopRefresh() {
        listView.onRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        isRefresh = true;
        page = 1;
        getHistoryAlarmList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        isRefresh = false;
        page++;
        if (page > totalPages) {
            ToastUtil.getInstance().show(this, "没有更多了！");
            page--;
            handler.sendEmptyMessage(0);
            return;
        }
        getHistoryAlarmList();
    }

    private void initListView() {
        listView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        listView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        listView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");

        // 设置PullRefreshListView下拉加载时的加载提示
        listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在加载...");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开加载更多...");
    }

    /**
     * 获取告警列表
     */
    private void getHistoryAlarmList() {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new GaoJingModel().getHistoryAlarmList(this,id, alarmLevel, riskLevel, startTime, endTime,  page, 30, new IGaoJingView() {
            @Override
            public void success(String msg) {
                stopRefresh();
                Gson gson = new Gson();
                GaojingBean bean = gson.fromJson(msg.toString(),
                        new TypeToken<GaojingBean>() {
                        }.getType());
                if (bean != null) {
                    HistoryAlarmQueryActivity.this.totalPages = bean.getTotalPageNum();
                    if (isRefresh) {
                        HistoryAlarmQueryActivity.this.datas = bean.getDataList();
                        adapter.setList(HistoryAlarmQueryActivity.this.datas);
                        listView.setAdapter(adapter);
                    } else {
                        datas.addAll(bean.getDataList());
                        adapter.setList(datas);
                        adapter.notifyDataSetChanged();
                    }

                }


            }

            @Override
            public void showToast(String msg) {


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_header_right:
                ActivityUtil.getInstance().leftToRightActivity(this, CustomGJSubmitActivity.class);
                break;
            case R.id.tvAlarmLevel: {//告警级别
                List<ButtomBean> datas = new ArrayList<>();
                datas.add(new ButtomBean("", R.string.cAll));
                datas.add(new ButtomBean("1", R.string.level1));
                datas.add(new ButtomBean("2", R.string.level2));
                BottomDialog bottomDialog = new BottomDialog(this, datas, 2);
                bottomDialog.setDialogSSIDListener(new BottomDialog.DialogSSIDListener() {
                    @Override
                    public void buttom(ButtomBean bean) {
//                        DevLog.e("输出了Msg:"+bean.getMsg()+bean.getEnMsg());
                        tvAlarmLevel.setText(bean.getContentId());
                        alarmLevel = bean.getId();
                        isRefresh = true;
                        page = 1;
                        getHistoryAlarmList();
                    }
                });
                bottomDialog.show();
            }
                break;
            case R.id.tvRiskLevel://风险级别
                List<ButtomBean> datas = new ArrayList<>();
                datas.add(new ButtomBean("", R.string.cAll));
                datas.add(new ButtomBean("A", R.string.aLevel));
                datas.add(new ButtomBean("B", R.string.bLevel));
                datas.add(new ButtomBean("C", R.string.cLevel));
                BottomDialog bottomDialog = new BottomDialog(this, datas, 2);
                bottomDialog.setDialogSSIDListener(new BottomDialog.DialogSSIDListener() {
                    @Override
                    public void buttom(ButtomBean bean) {
//                        DevLog.e("输出了Msg:"+bean.getMsg()+bean.getEnMsg());
                        tvRiskLevel.setText(bean.getContentId());
                        riskLevel = bean.getId();
                        isRefresh = true;
                        page = 1;
                        getHistoryAlarmList();
                    }
                });
                bottomDialog.show();
                break;
            case R.id.tvStartTime://开始时间
                shwoDateDialog(0);
                break;
            case R.id.tvEndTime://结束时间
                shwoDateDialog(1);
                break;
            case R.id.iv_header_left:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 选择日期
     * @param type 0开始时间 1结束时间
     */
    private void shwoDateDialog(final int type){
        DatePicker datePicker = new DatePicker(this, new DatePicker.ResultHandler() {
            @Override
            public void handle(String time) {
                switch (type){
                    case 0:
                        startTime = time.substring(0,10);
                        tvStartTime.setText(startTime);
                        break;
                    case 1:
                        endTime = time.substring(0,10);
                        tvEndTime.setText(endTime);
                        break;
                }
                isRefresh = true;
                page = 1;
                getHistoryAlarmList();
            }
        });
        datePicker.setIsLoop(false);//是否可以循环滚动
        datePicker.showSpecificTime(false);//是否显示分钟 默认true
        datePicker.show();//显示当前时间
    }
}
