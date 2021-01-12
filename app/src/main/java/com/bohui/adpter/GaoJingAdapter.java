package com.bohui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.bean.GaoJingDataList;
import com.bohui.utils.DevLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 告警数据源适配器
 */
public class GaoJingAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<GaoJingDataList> datas = new ArrayList<GaoJingDataList>();

    public GaoJingAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<GaoJingDataList> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final GaoJingDataList bean = datas.get(i);
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_fault, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String language = BoHuiApplication.getInstance().getConfigDB().getLanguage();
        DevLog.e("中英文如何显示：" + language);
        int level = bean.getAlarmSeverity();
        switch (level) {
            case 1:
                if ("zh".equals(language)) {
                    holder.tv_level.setText("提示");
                } else {
                    holder.tv_level.setText("Minor");
                }

//
                break;
            case 2:
                if ("zh".equals(language)) {
                    holder.tv_level.setText("一般");
                } else {
                    holder.tv_level.setText("Normal");
                }

//
                break;
            case 3:
                if ("zh".equals(language)) {
                    holder.tv_level.setText("严重");
                } else {
                    holder.tv_level.setText("Major");
                }

//
                break;
            case 4:
                if ("zh".equals(language)) {
                    holder.tv_level.setText("危急");
                } else {
                    holder.tv_level.setText("Critical");
                }

//
                break;
        }
        if ("zh".equals(language)) {
            holder.tv_clear.setText(context.getResources().getString(R.string.clear_alarm));
        } else {
            holder.tv_clear.setText(context.getResources().getString(R.string.clear_alarm));
        }


//        holder.tv_level.setText(bean.getAlarmSeverity() + "");
        holder.tv_content.setText(bean.getAdditionalText());
        holder.tv_time.setText(bean.getRaisedTime().replace("T", ""));
        holder.tv_type.setText(bean.getAlarmType().getName());
        holder.rel_dingwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gaojingCallBack != null) {
                    gaojingCallBack.callback(bean);
                }
            }
        });
        holder.rel_cleargaojing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gaojingCallBack!=null){
                    gaojingCallBack.clearGaojing(bean);
                }
            }
        });
        return view;
    }


    class ViewHolder {
        public TextView tv_level;
        public TextView tv_content;
        public TextView tv_time;
        public TextView tv_type;
        public RelativeLayout rel_dingwei;
        public RelativeLayout rel_cleargaojing;
        public TextView tv_clear;
        public ViewHolder(View view) {
            tv_level = (TextView) view.findViewById(R.id.fault_lever);
            tv_content = (TextView) view.findViewById(R.id.tv_faultData);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            rel_dingwei = (RelativeLayout) view.findViewById(R.id.rel_dingwei);
            rel_cleargaojing = (RelativeLayout) view.findViewById(R.id.rel_cleargaojing);
            tv_clear = (TextView)view.findViewById(R.id.item_tv_clear);
        }
    }

    public interface GaojingCallBack {
        void callback(GaoJingDataList bean);
        void clearGaojing(GaoJingDataList bean);
    }

    private GaojingCallBack gaojingCallBack;

    public void setGaojingCallBack(GaojingCallBack gaojingCallBack) {
        this.gaojingCallBack = gaojingCallBack;
    }
}
