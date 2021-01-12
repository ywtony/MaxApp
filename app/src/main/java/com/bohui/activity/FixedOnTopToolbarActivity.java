package com.bohui.activity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bohui.R;
import com.bohui.presenter.IBasePresenter;
import com.wq.photo.util.StatusBarCompat;

import androidx.annotation.LayoutRes;
import androidx.appcompat.widget.ViewStubCompat;
//import com.githang.statusbar.StatusBarCompat;


/**
 * 全局自定义TitleBarActivity
 * 使用全局TitleBar的Activity必须集成自该Activity
 * 设置TitleBar控件是否显示时使用INVISIABLE，不要使用GONE
 *
 * @author 赵尉尉
 */
public abstract class FixedOnTopToolbarActivity<P extends IBasePresenter> extends BaseActivity<P> {

    private Toolbar mToolbar;
    private ViewStubCompat mContentView;

    private View mTitleContainer;
    /**
     * 标题栏返回按钮
     */
    protected TextView mTitleBack;
    /**
     * 标题栏标题
     */
    protected TextView mTitleTitle;
    /**
     * 标题栏功能按钮
     */
    protected TextView mTitleFunction;
    /**
     * 标题栏分割线
     */
    protected TextView mTitleDivider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_fix_on_top_toolbar);
//        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.bg_header));
        mContentView = (ViewStubCompat) findViewById(R.id.fix_on_top_toolbar_content_stub);
        mToolbar = (Toolbar) findViewById(R.id.fix_on_top_toolbar_bar);
        mToolbar.setTitle(getTitle());

        mTitleContainer = mToolbar.findViewById(R.id.title_container);
        mTitleBack = (TextView) mToolbar.findViewById(R.id.title_back);
        mTitleBack.setOnClickListener(mTitleOnClickListener);
        mTitleTitle = (TextView) mToolbar.findViewById(R.id.title_title);
        mTitleTitle.setOnClickListener(mTitleOnClickListener);
        mTitleFunction = (TextView) mToolbar.findViewById(R.id.title_fuction);
        mTitleFunction.setOnClickListener(mTitleOnClickListener);
        mTitleDivider = (TextView) mToolbar.findViewById(R.id.title_divider);

        mTitleTitle.setText(getTitle());
    }

    private View.OnClickListener mTitleOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.title_back) {
                onBackClick(v);
            } else if (v.getId() == R.id.title_title) {
                onTitleClick(v);
            } else if (v.getId() == R.id.title_fuction) {
                onFunctionClick(v);
            }
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mContentView.setLayoutResource(layoutResID);
        mContentView.inflate();
    }

    protected Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * 标题栏返回按钮被点击时调用该方法
     *
     * @param view 返回按钮
     */
    protected abstract void onBackClick(View view);

    /**
     * 标题栏标题被点击时调用该方法
     *
     * @param view 标题
     */
    protected abstract void onTitleClick(View view);

    /**
     * 标题栏功能按钮被点击时调用该方法
     *
     * @param view 功能按钮
     */
    protected abstract void onFunctionClick(View view);

    /**
     * 点击返回键时触发
     * 如不需要与TitleBar点击触发同一事件请重写此方法
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isUseSameBackFuction()) {
            onBackClick(mTitleBack);
        }
    }

    /**
     * 如不需要与TitleBar点击触发同一事件请重写此方法并返回false
     *
     * @return boolean 是否与TitleBar返回点击使用同一处理方法
     */
    protected boolean isUseSameBackFuction() {
        return true;
    }

    /**
     * 设置标题栏分割线是否显示
     *
     * @param visibility
     */
    protected void setTitleDividerVisibility(int visibility) {
        mTitleDivider.setVisibility(visibility);
    }

    /**
     * 设置标题栏分割线颜色
     *
     * @param color
     */
    protected void setTitleDividerBackgroudColor(int color) {
        mTitleDivider.setBackgroundColor(color);
    }

    /**
     * 设置标题栏背景色
     *
     * @param color 背景色
     */
    protected void setTitleBackgroudColor(int color) {
        mTitleContainer.setBackgroundColor(color);
    }

    /**
     * 设置标题栏返回键背景
     *
     * @param drawable Drawable
     */
    protected void setTitleBackBackgroudDrawable(Drawable drawable) {
        mTitleBack.setBackgroundDrawable(drawable);
    }

    /**
     * 设置标题栏返回键背景
     *
     * @param resId 资源标识
     */
    protected void setTitleBackBackgroudDrawable(int resId) {
        mTitleBack.setBackgroundDrawable(getResources().getDrawable(resId));
    }

    /**
     * 从资源id获取Drawable
     *
     * @param resId
     * @return
     */
    protected Drawable getCompoundDrawable(int resId) {
        if (resId == 0) {
            return null;
        }
        try {
            return getResources().getDrawable(resId);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 设置返回按钮的drawableLeft,drawableTop,drawableRight,drawableBootom
     * drawable为空时传入0即可
     *
     * @param leftResId
     * @param topResId
     * @param rightResId
     * @param bottomResId
     */
    protected void setTitleBackCompoundDrawable(int leftResId, int topResId, int rightResId, int bottomResId) {
        setTitleBackCompoundDrawable(getCompoundDrawable(leftResId), getCompoundDrawable(topResId), getCompoundDrawable(rightResId), getCompoundDrawable(bottomResId));
    }

    /**
     * 设置返回按钮的drawableLeft,drawableTop,drawableRight,drawableBootom
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    protected void setTitleBackCompoundDrawable(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        mTitleBack.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * 设置标题按钮的drawableLeft,drawableTop,drawableRight,drawableBootom
     * drawable为空时传入0即可
     *
     * @param leftResId
     * @param topResId
     * @param rightResId
     * @param bottomResId
     */
    protected void setTitleTitleCompoundDrawable(int leftResId, int topResId, int rightResId, int bottomResId) {
        setTitleTitleCompoundDrawable(getCompoundDrawable(leftResId), getCompoundDrawable(topResId), getCompoundDrawable(rightResId), getCompoundDrawable(bottomResId));
    }

    /**
     * 设置标题的drawableLeft,drawableTop,drawableRight,drawableBootom
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    protected void setTitleTitleCompoundDrawable(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        mTitleTitle.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    /**
     * 设置标题按钮的drawableLeft,drawableTop,drawableRight,drawableBootom
     * drawable为空时传入0即可
     *
     * @param leftResId
     * @param topResId
     * @param rightResId
     * @param bottomResId
     */
    protected void setTitleFunctionCompoundDrawable(int leftResId, int topResId, int rightResId, int bottomResId) {
        setTitleFunctionCompoundDrawable(getCompoundDrawable(leftResId), getCompoundDrawable(topResId), getCompoundDrawable(rightResId), getCompoundDrawable(bottomResId));
    }

    /**
     * 设置标题的drawableLeft,drawableTop,drawableRight,drawableBootom
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    protected void setTitleFunctionCompoundDrawable(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        mTitleFunction.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

}
