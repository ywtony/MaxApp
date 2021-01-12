package com.bohui.adpter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bohui.R;
import com.bohui.entity.Fault;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/20 0020.
 */

public class AlarmsAdapter extends CommonAdapter {
    List<String>list=new ArrayList<>();
    public AlarmsAdapter(Context mContext, List<String> list, int layoutId){
        super(mContext, list,layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final Object o, int position) {
        ((TextView)holder.getView(R.id.tv_reason)).setText((String)o);
        final CheckBox checkBox=(CheckBox) holder.getView(R.id.cb_reason);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isChecked=checkBox.isChecked();
                if(isChecked&&!list.contains((String)o)){
                    list.add((String)o);
                }else if(!isChecked&&list.contains((String)o)) {
                    list.remove((String)o);
                }
            }
        });
    }
    public List<String>getReason(){
        return list;
    }
}
