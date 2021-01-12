package io.ionic.starter.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bohui.R;


public enum CustomToast {
	INSTANCE;// 实现单例
    private Toast mToast;
    private TextView mTvToast;
    private ImageView mIvToast;
    public void showToast(Context ctx, String content, int imgID) {
        if (mToast == null) {
            mToast = new Toast(ctx);
            mToast.setGravity(Gravity.CENTER, 0, 0);//设置toast显示的位置，这是居中
            mToast.setDuration(Toast.LENGTH_SHORT);//设置toast显示的时长
            View _root = LayoutInflater.from(ctx).inflate(R.layout.toast_custom_common, null);//自定义样式，自定义布局文件
            mTvToast = (TextView) _root.findViewById(R.id.tvCustomToast);
            mIvToast=(ImageView)_root.findViewById(R.id.ck);
            mToast.setView(_root);//设置自定义的view 
       }
        mTvToast.setText(content);//设置文本
        if(imgID!=0){
            mIvToast.setBackgroundResource(imgID);
        }
        mToast.show();//展示toast
        
    }
    
    public void showToastError(Context ctx, String content, int imgID) {
        if (mToast == null) {
            mToast = new Toast(ctx);
            mToast.setGravity(Gravity.CENTER, 0, 0);//设置toast显示的位置，这是居中
            mToast.setDuration(Toast.LENGTH_SHORT);//设置toast显示的时长
            View _root = LayoutInflater.from(ctx).inflate(R.layout.toast_custom_common_error, null);//自定义样式，自定义布局文件
            mTvToast = (TextView) _root.findViewById(R.id.tvCustomToast);
            mIvToast=(ImageView)_root.findViewById(R.id.ck);
            mToast.setView(_root);//设置自定义的view 
       }
        mTvToast.setText(content);//设置文本
        if(imgID!=0){
            mIvToast.setBackgroundResource(imgID);
        }
        mToast.show();//展示toast
        
    }
    
    
    
//    public void showToast(Context ctx, int stringId) {
//        showToast(ctx, ctx.getString(stringId));
//    }
    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
            mTvToast = null;
        }
    }
}
