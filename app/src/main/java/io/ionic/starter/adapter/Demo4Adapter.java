package io.ionic.starter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bohui.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.ionic.starter.activity.CMainShuMenu;
import io.ionic.starter.model.AllModel;


public class Demo4Adapter extends  RecyclerView.Adapter<Demo4Adapter.ViewHolder>  {
    private  LayoutInflater mLayoutInflater;
    private  Context mContext;
    private List<AllModel> list;
    private ArrayList<String> szDevNameList ;
    private ArrayList<Long> cloudLpUserIDList;
    private long lpUerID;

    public Demo4Adapter(Context context, List<AllModel> list, ArrayList<String> szDevNameList , ArrayList<Long> cloudLpUserIDList, long lpUerID) {
        mContext = context;
        this.list = list;
        this.szDevNameList = szDevNameList;
        this.cloudLpUserIDList = cloudLpUserIDList;
        this.lpUerID = lpUerID;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo2,null, false);

        return new ViewHolder(view );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        float scaleRatio[] = new float[list.size()];
//        long lpLiveViewHandle[] = new long[list.size()];
//        lpLiveViewHandle[position] = 0;
//        scaleRatio[position] = 1.0f;
//
        if(list.get(position).getStatus().equals("1")){
            holder.txt6.setText("在线");
            holder.txt6.setTextColor(mContext.getResources().getColor(R.color.red));
        } else {
            holder.txt6.setText("不在线");
            holder.txt6.setTextColor(mContext.getResources().getColor(R.color.white));
        }

        holder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).getStatus().equals("1")){
                    Intent intent = new Intent(mContext, CMainShuMenu.class);
//                    intent.putExtra("m_dwChannelID", position+1);
                    intent.putExtra("models", (Serializable) list);
                    intent.putExtra("lpUerID", lpUerID);
                    intent.putExtra("m_dwChannelID", list.get(position).getId());
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "当前设备不在线", Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.img_tou.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView img_tou;
        TextView txt6;
        LinearLayout ll_content;
        ViewHolder(View view) {
            super(view);
            img_tou=  view.findViewById(R.id.img_tou);
            txt6=  view.findViewById(R.id.txt6);
            ll_content=  view.findViewById(R.id.ll_content);
        }
    }
}
