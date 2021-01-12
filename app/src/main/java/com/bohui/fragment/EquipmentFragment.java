package com.bohui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.activity.FaultDescriptionActivity;
import com.bohui.activity.MainActivity;
import com.bohui.activity.TemperDetectActivity;
import com.bohui.adpter.FanAdapter;
import com.bohui.adpter.LightAdapter;
import com.bohui.adpter.TemperatureAdapter;
import com.bohui.entity.FanEntity;
import com.bohui.entity.LightEntity;
import com.bohui.entity.TemperatureEntity;
import com.bohui.utils.DataUtils;
import com.bohui.widget.BoHuiListView;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/18 0018.
 */

public class EquipmentFragment extends Fragment implements View.OnClickListener{
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
    @BindView(R.id.ll_fan)
    LinearLayout ll_fan;
    @BindView(R.id.ll_light)
    LinearLayout ll_light;
    @BindView(R.id.ll_temperature)
    LinearLayout ll_temperature;
    @BindView(R.id.list_fan)
    BoHuiListView list_fan;
    @BindView(R.id.list_light)
    BoHuiListView list_light;
    @BindView(R.id.list_temperature)
    BoHuiListView list_temperature;
    private List<FanEntity>fans;//风机
    private List<LightEntity>lights;//照明
    private List<TemperatureEntity>temperatures;//温度
    private FanAdapter fanAdapter;
    private LightAdapter lightAdapter;
    private TemperatureAdapter temperatureAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        view = inflater.inflate(R.layout.fragment_equipenment, container, false);
        ButterKnife.bind(this, view);
        fans=DataUtils.caeateFan();
        lights=DataUtils.caeateLights();
        temperatures=DataUtils.caeateTemperature();
        initView();
        return view;
    }

    private void initView(){
        iv_header_left.setVisibility(View.GONE);
        iv_header_menu.setVisibility(View.VISIBLE);
        iv_header_right.setVisibility(View.VISIBLE);
        iv_header_menu.setOnClickListener(this);
        iv_header_right.setOnClickListener(this);
        tv_title.setText(getResources().getString(R.string.menu_enuequipement));
        ll_fan.setVisibility(fans.size()<=0?View.GONE:View.VISIBLE);
        ll_light.setVisibility(lights.size()<=0?View.GONE:View.VISIBLE);
        ll_temperature.setVisibility(temperatures.size()<=0?View.GONE:View.VISIBLE);
        fanAdapter=new FanAdapter(mContext,fans,R.layout.item_faultcheck);
        lightAdapter=new LightAdapter(mContext,lights,R.layout.item_faultcheck);
        temperatureAdapter=new TemperatureAdapter(mContext,temperatures,R.layout.item_eqipement);
        list_fan.setAdapter(fanAdapter);
        list_light.setAdapter(lightAdapter);
        list_temperature.setAdapter(temperatureAdapter);

        list_temperature.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("jackFire","onItemClick list_temperature");
            }
        });
        list_light.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("jackFire","onItemClick list_light");
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_header_menu:
                if(MainActivity.instance!=null){
                    MainActivity.instance.openDrawer();
                }

                break;
            case R.id.iv_header_right:
                startActivity(new Intent(getActivity(), TemperDetectActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//    ButterKnife.unbind(this);
    }
}
