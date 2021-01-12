package com.bohui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.bean.MainPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class ProjectAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<MainPage> datas = new ArrayList<MainPage>();

    public ProjectAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<MainPage> datas) {
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
        final MainPage bean = datas.get(i);
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_project, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.tv_name.setText(bean.getName());
        return view;
    }


    class ViewHolder {
        public TextView tv_name;

        public ViewHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.tv_project);
        }
    }
}