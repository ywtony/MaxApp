package com.bohui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bohui.BoHuiApplication;
import com.bohui.R;
import com.bohui.bean.ButtomBean;

import java.util.ArrayList;
import java.util.List;

public class BottomAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<ButtomBean> datas = new ArrayList<ButtomBean>();
    private int type;

    public BottomAdapter(Context context, int type) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.type = type;
    }

    public void setList(List<ButtomBean> datas) {
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
        final ButtomBean bean = datas.get(i);
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.buttom_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String language = BoHuiApplication.getInstance().getConfigDB().getLanguage();
//        if ("zh".equals(language)) {
        switch (type){
            case 1:
                holder.tv_ssid.setText(bean.getMsg());
                break;
            case 2:
                holder.tv_ssid.setText(bean.getContentId());
                break;
        }

//        } else {
//            holder.tv_ssid.setText(bean.getEnMsg());
//        }

//        if (bean.isSelect()) {
//            holder.tv_name.setBackgroundResource(R.drawable.tv_circle_bg_selected);
//        } else {
//            holder.tv_name.setBackgroundResource(R.drawable.tv_circle_bg);
//        }
//
//        holder.tv_name.setText(bean.getName());

        return view;
    }


    class ViewHolder {
        public TextView tv_ssid;

        public ViewHolder(View view) {
            tv_ssid = (TextView) view.findViewById(R.id.ssid_tv_ssid);
        }
    }
}
