package com.bohui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 宽高一致的linerlayout
 * 
 * @author yw-tony
 *
 */
@SuppressLint("NewApi")
public class CLinearLayout extends LinearLayout {
	public CLinearLayout(Context context) {
		super(context);
	}

	public CLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public CLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, widthMeasureSpec);
	}
}
