package io.ionic.starter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bohui.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import io.ionic.starter.model.JiankongModel;


public class DemoAdapter extends  RecyclerView.Adapter<DemoAdapter.ViewHolder>  {
    private  OnOrderClickLister mOrderClickListener;
    private  LayoutInflater mLayoutInflater;
    private  Context mContext;
    private List<JiankongModel> list;

    public DemoAdapter(Context context, List<JiankongModel> list) {
        mContext = context;
        this.list = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo,null, false);

        return new ViewHolder(view );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txt_value1.setText(list.get(position).getStation_name());
        holder.ll_come.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOrderClickListener != null){
                     mOrderClickListener.onIntent(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_value1;
        LinearLayout ll_come;

        ViewHolder(View view) {
            super(view);
            txt_value1= (TextView) view.findViewById(R.id.txt_value1);
            ll_come=  view.findViewById(R.id.ll_come);
        }
    }

    public void setListener(OnOrderClickLister listener) {
        this.mOrderClickListener = listener;
    }

    public interface OnOrderClickLister {
        void onIntent(int position);
    }


}
