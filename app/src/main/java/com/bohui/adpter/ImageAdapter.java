package com.bohui.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bohui.R;
import com.bohui.bean.LocalImage;
import com.bohui.utils.LoadLocalImageUtil;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter  extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<LocalImage> datas = new ArrayList<LocalImage>();

    public ImageAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<LocalImage> datas) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final LocalImage bean = datas.get(i);
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.image_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //此处应该显示本地图片
        String url = bean.getLocalUrl();
        if (url.equals("camera")) {
            holder.iv_del.setVisibility(View.GONE);
            LoadLocalImageUtil.getInstance().displayFromDrawable(R.drawable.add, holder.iv_img);
        } else {
            holder.iv_del.setVisibility(View.VISIBLE);
            LoadLocalImageUtil.getInstance().displayFromSDCard(url, holder.iv_img);
        }
        holder.iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (delCallback != null) {
                    delCallback.callback(i);
                }
            }
        });
        return view;
    }


    class ViewHolder {
        public ImageView iv_img;
        public ImageView iv_del;

        public ViewHolder(View view) {
            iv_img = view.findViewById(R.id.item_iv_img);
            iv_del = view.findViewById(R.id.item_iv_del);
        }
    }

    public interface DelCallback {
        void callback(int position);
    }

    private DelCallback delCallback;

    public void setDelCallback(DelCallback delCallback) {
        this.delCallback = delCallback;
    }
}