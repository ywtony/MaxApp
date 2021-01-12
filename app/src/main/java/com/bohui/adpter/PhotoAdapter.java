package com.bohui.adpter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bohui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class PhotoAdapter extends CommonAdapter {
    public PhotoAdapter(Context mContext, List<Drawable> list, int layoutId){
        super(mContext, list,layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final Object o, int position) {
        ((ImageView)holder.getView(R.id.iv_photo)).setImageDrawable((Drawable) o);
    }
}
