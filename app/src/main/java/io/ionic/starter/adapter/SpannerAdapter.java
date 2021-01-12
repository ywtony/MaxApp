package io.ionic.starter.adapter;

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

public class SpannerAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> datas = new ArrayList<String>();
    private int type;

    public SpannerAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.type = type;
    }

    public void setList(List<String> datas) {
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
        final String bean = datas.get(i);
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.spanner_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_ssid.setText(bean);

        return view;
    }


    class ViewHolder {
        public TextView tv_ssid;

        public ViewHolder(View view) {
            tv_ssid = (TextView) view.findViewById(R.id.ssid_tv_ssid);
        }
    }
}
