package com.bohui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

@SuppressLint("ClickableViewAccessibility")
public class NoScrollGridView extends GridView {
	private static final int Blank_POSITION = -1;
	public NoScrollGridView(Context context) {
		super(context);
	}

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

	public interface OnTouchBlankPositionListener {
		/**
		 * 
		 * @return 是否要终止事件的路由
		 */
		boolean onTouchBlankPosition();
	}

	private OnTouchBlankPositionListener mTouchBlankPosListener;

	public void setOnTouchBlankPositionListener(
			OnTouchBlankPositionListener listener) {
		mTouchBlankPosListener = listener;
	}
	/**
	 * 设置点击GridView的空白区域的点击事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (mTouchBlankPosListener != null) {
			if (!isEnabled()) {
				// A disabled view that is clickable still consumes the touch
				// events, it just doesn't respond to them.
				return isClickable() || isLongClickable();
			}

			if (event.getActionMasked() == MotionEvent.ACTION_UP) {
				final int motionPosition = pointToPosition((int) event.getX(),
						(int) event.getY());
				if (motionPosition == Blank_POSITION) {
					return mTouchBlankPosListener.onTouchBlankPosition();
				}
			}
		}

		return super.onTouchEvent(event);
	}
}
