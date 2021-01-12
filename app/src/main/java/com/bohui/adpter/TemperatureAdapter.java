package com.bohui.adpter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.entity.LightEntity;
import com.bohui.entity.TemperatureEntity;
import com.bohui.utils.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class TemperatureAdapter extends CommonAdapter {
    private Context context;
    public TemperatureAdapter(Context mContext, List<TemperatureEntity> list, int layoutId){
        super(mContext, list,layoutId);
        context=mContext;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        final TemperatureEntity entity=(TemperatureEntity)o;
        (holder.getView(R.id.rl_temperature)).setBackgroundResource(R.drawable.shallow_red);
        (holder.getView(R.id.iv_temperature)).setBackgroundResource(R.drawable.thermometer);
        RelativeLayout rl_data=(holder.getView(R.id.rl_data));

        TextView temperature=(TextView)(holder.getView(R.id.tv_temperature));
        temperature.setText(entity.getTemperatureName());
        TextView tv_status=(TextView)(holder.getView(R.id.tv_status));
        tv_status.setText(entity.getStatus());
        TextView tv_data=(TextView)(holder.getView(R.id.tv_data));
        String temperatures=String.format(mContext.getResources().getString(R.string.temperatures), ((String)entity.getTemperatureValue()));
        tv_data.setText(temperatures);
        rl_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show(context,entity.getTemperatureName());
            }
        });
    }
}
