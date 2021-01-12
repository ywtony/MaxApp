package com.bohui.adpter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.entity.FanEntity;
import com.bohui.entity.LightEntity;
import com.bohui.utils.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class LightAdapter extends CommonAdapter {
    private Context context;
    public LightAdapter(Context mContext, List<LightEntity> list, int layoutId){
        super(mContext, list,layoutId);
        context=mContext;
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
       final LightEntity entity=(LightEntity)o;
        (holder.getView(R.id.rl_temperature)).setBackgroundResource(R.drawable.shallow_green);
        (holder.getView(R.id.iv_temperature)).setBackgroundResource(R.drawable.lamp);
        RelativeLayout rl_data=(RelativeLayout) (holder.getView(R.id.rl_data));

        TextView temperature=(TextView)(holder.getView(R.id.tv_temperature));
        TextView tv_status=(TextView)(holder.getView(R.id.tv_status));
        CheckBox checkbox=(CheckBox)(holder.getView(R.id.checkbox));
        temperature.setText(entity.getLightName());
        tv_status.setText(entity.getStatus());
        checkbox.setChecked(entity.isOpen());
        rl_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.show(context,entity.getLightName());
            }
        });
    }
}
