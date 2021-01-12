package com.bohui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 简易封装下Adapter
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected List<T> datas;
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected  int layoutId;
    public CommonAdapter(Context mContext, List<T> datas, int layoutId){
        this.mContext = mContext;
        this.datas = datas;
        this.mInflater = LayoutInflater.from(mContext);
        this.layoutId = layoutId;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext,convertView,parent,layoutId,position);

        convert(holder,getItem(position),position);
        return holder.getmConvertView();
    }
    public abstract void convert(ViewHolder holder,T t,int position);
}
