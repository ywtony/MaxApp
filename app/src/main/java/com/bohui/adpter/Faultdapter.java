package com.bohui.adpter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.entity.FanEntity;
import com.bohui.entity.Fault;

import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class Faultdapter extends CommonAdapter {
    public Faultdapter(Context mContext, List<Fault> list, int layoutId){
        super(mContext, list,layoutId);
    }

    @Override
    public void convert(ViewHolder holder, Object o, int position) {
        Fault entity=(Fault)o;

        TextView fault_lever=(TextView)(holder.getView(R.id.fault_lever));
        TextView tv_faultData=(TextView)(holder.getView(R.id.tv_faultData));
        TextView tv_time=(TextView)(holder.getView(R.id.tv_time));
        String level=String.format(mContext.getResources().getString(R.string.fault_level), ((String)entity.getFaultLevel()));
        fault_lever.setText(level);
        tv_faultData.setText(entity.getDescribe());
        tv_time.setText(entity.getFaultTime());

    }
}
