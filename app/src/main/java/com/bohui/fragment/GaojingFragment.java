package com.bohui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.activity.CustomGJSubmitActivity;
import com.bohui.activity.FaultDescriptionActivity;
import com.bohui.activity.HistoryAlarmQueryActivity;
import com.bohui.activity.LoginActivity;
import com.bohui.adpter.GaoJingAdapter;
import com.bohui.bean.GaoJingDataList;
import com.bohui.bean.GaojingBean;
import com.bohui.bean.MainPage;
import com.bohui.bean.MessageEvent;
import com.bohui.bean.ObjData;
import com.bohui.bean.User;
import com.bohui.config.Constants;
import com.bohui.model.GaoJIngPageModel;
import com.bohui.model.GaoJingModel;
import com.bohui.model.GetUnclearedAlarmsByMeasObjIDsModel;
import com.bohui.presenter.IBasePresenter;
import com.bohui.presenter.IBasePresneterImpl;
import com.bohui.utils.ActivityUtil;
import com.bohui.utils.AssetsUtils;
import com.bohui.utils.DevLog;
import com.bohui.utils.ImageLoaderUtils;
import com.bohui.utils.JSONUtil;
import com.bohui.utils.ToastUtil;
import com.bohui.view.IGaoJingView;
import com.bohui.widget.CircleImageView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GaojingFragment extends Fragment implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView>, GaoJingAdapter.GaojingCallBack, View.OnClickListener {
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
    private int page = 1;// 当前页码
    private int totalPages;// 总页数
    private boolean isRefresh = true;
    private List<GaoJingDataList> datas = new ArrayList<>();
    private GaoJingAdapter adapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.gaojing_layout, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initViews();
        getGaojingList();
//        initData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

    private void initViews() {
        iv_header_left.setVisibility(View.GONE);
        tv_title.setText(getResources().getString(R.string.menu_enuequipement));
        listView.setOnItemClickListener(this);
        listView.setOnRefreshListener(this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        initListView();
        adapter = new GaoJingAdapter(getActivity());
        adapter.setGaojingCallBack(this);
        listView.setAdapter(adapter);
        tv_right.setText("历史告警");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.what) {
            case MessageEvent.REFRESH_GJLIST:
                //刷新告警列表
                getGaojingList();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
        getGaojingList();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        isRefresh = false;
        page++;
        if (page > totalPages) {
            ToastUtil.getInstance().show(getActivity(), "没有更多了！");
            page--;
            handler.sendEmptyMessage(0);
            return;
        }
        getGaojingList();
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
    private void getGaojingList() {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new GaoJingModel().getGaoJingList(getActivity(), id, page, 10, new IGaoJingView() {
            @Override
            public void success(String msg) {
                stopRefresh();
                Gson gson = new Gson();
                GaojingBean bean = gson.fromJson(msg.toString(),
                        new TypeToken<GaojingBean>() {
                        }.getType());
                if (bean != null) {
                    GaojingFragment.this.totalPages = bean.getTotalPageNum();
                    if (isRefresh) {
                        GaojingFragment.this.datas = bean.getDataList();
                        adapter.setList(GaojingFragment.this.datas);
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

    private void initData() {
        JSONObject obj = AssetsUtils.getInstance().getJsonObjFile(getActivity(), "gaojing.json");
        Gson gson = new Gson();
        GaojingBean bean = gson.fromJson(obj.toString(),
                new TypeToken<GaojingBean>() {
                }.getType());
        if (bean != null) {
            this.datas = bean.getDataList();
            adapter.setList(this.datas);
            listView.setAdapter(adapter);
        }
    }



    @Override
    public void callback(GaoJingDataList bean) {

        getGaojingPageData(bean.getMeasObjID() + "", bean.getLatitude(), bean.getLongitude());
    }

    @Override
    public void clearGaojing(GaoJingDataList bean) {
        Intent intent = new Intent();
        intent.putExtra("bean", bean);
        intent.setClass(getActivity(), FaultDescriptionActivity.class);
        startActivity(intent);
    }

    /**
     * 定位到告警
     */
    private void getGaojingPageData(String measObjIDS, final float lat, final float lng) {
        String id = BoHuiApplication.getInstance().getAuthConfig().getToken();
        new GaoJIngPageModel().getGaoJingPage(getActivity(), id, measObjIDS, new IGaoJingView() {
            @Override
            public void success(String msg) {
                try {
                    JSONArray arrs = new JSONArray(msg);
                    JSONObject obj = arrs.getJSONObject(0);
                    String pageID = obj.getString("PageID");
                    MessageEvent event = new MessageEvent();
//                    event.what = MessageEvent.LOAD_INDEX_PAGE_ALARM;
                    event.what = MessageEvent.TO_INDEX1;
                    MainPage mainPage = new MainPage();
                    mainPage.setID(Integer.parseInt(pageID));
                    event.obj = mainPage;
                    event.state = lat + "," + lng;
                    BoHuiApplication.getInstance().setLatlng(lat + "," + lng);
                    DevLog.e("我要打印一下经纬度信息：" + event.state);
                    EventBus.getDefault().post(event);
                } catch (Exception e) {
                    e.printStackTrace();
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
                ActivityUtil.getInstance().leftToRightActivity(getActivity(), HistoryAlarmQueryActivity.class);
                break;
        }
    }
}
