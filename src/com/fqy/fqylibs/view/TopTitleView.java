package com.fqy.fqylibs.view;

import android.app.usage.UsageEvents.Event;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.fqy.fqylibs.R;

/**
 * @Title: TopTitleView.java
 * @Package com.fqy.fqylibs.ui
 * @Description: 欢迎页
 * @author: Fang Qingyou
 * @date 2015年6月4日上午9:37:19
 * @version V1.0
 */
public class TopTitleView extends RelativeLayout implements OnClickListener {

	private Button mLeftButton;
	private Button mRightButton;
	private Button mTitleButton;

	private int dfTitleTextSize = 18;
	private int dfTextSize = 14;

	public static final int mTitleId = 10;
	public static final int mLeftId = 20;
	public static final int mRightId = 30;

	private OnTopTitleViewListerner listerner;

	public TopTitleView(Context context) {
		this(context, null, 0);
	}

	public TopTitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public TopTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		createView(context);

		// 通过ttvTa 设置自定义属性
		TypedArray ttvTa = context.obtainStyledAttributes(attrs,
				R.styleable.TopTitleView);

		// 设置自定义控件的背景
		int background = ttvTa.getResourceId(
				R.styleable.TopTitleView_background, R.color.red);

		this.setBackgroundResource(background);

		setTitleText(ttvTa.getString(R.styleable.TopTitleView_text_title));
		setLeftText(ttvTa.getString(R.styleable.TopTitleView_text_left));
		setRightText(ttvTa.getString(R.styleable.TopTitleView_text_right));

		setTitleBackground(ttvTa.getResourceId(
				R.styleable.TopTitleView_background_title, R.color.transparent));
		setLeftBackground(ttvTa.getResourceId(
				R.styleable.TopTitleView_background_left, R.color.transparent));
		setRightBackground(ttvTa.getResourceId(
				R.styleable.TopTitleView_background_right, R.color.transparent));

		// 资源回收
		ttvTa.recycle();

	}

	/** 初始化控件 */
	private void createView(Context context) {

		// 设置标题按钮
		mTitleButton = new Button(context);
		mTitleButton.setId(mTitleId);
		mTitleButton.setOnClickListener(this);// 监听
		mTitleButton.setTextColor(getResources().getColor(R.color.white));// 颜色
		mTitleButton.setBackgroundColor(Color.TRANSPARENT);// 背景
		mTitleButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, dfTitleTextSize); // 字体大小

		// 设置左边按钮
		mLeftButton = new Button(context);
		mTitleButton.setId(mLeftId);
		mLeftButton.setOnClickListener(this);
		mLeftButton.setTextColor(getResources().getColor(R.color.white));
		mLeftButton.setBackgroundColor(Color.TRANSPARENT);
		mLeftButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, dfTextSize);

		// 设置右边按钮
		mRightButton = new Button(context);
		mTitleButton.setId(mRightId);
		mRightButton.setOnClickListener(this);
		mRightButton.setTextColor(getResources().getColor(R.color.white));
		mRightButton.setBackgroundColor(Color.TRANSPARENT);
		mRightButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, dfTextSize);

		// 设置 标题的显示
		LayoutParams paramsTitle = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		paramsTitle.addRule(RelativeLayout.CENTER_IN_PARENT,
				RelativeLayout.TRUE);
		addView(mTitleButton, paramsTitle);

		// 设置 左边按钮的显示
		LayoutParams paramsLeft = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
				RelativeLayout.TRUE);
		addView(mLeftButton, paramsLeft);

		// 设置 右边按钮的显示
		LayoutParams paramsRight = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		paramsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				RelativeLayout.TRUE);
		addView(mRightButton, paramsRight);

	}

	/**
	 * 设置标题文字
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日上午9:57:15
	 * @param string
	 */
	private void setTitleText(String string) {
		mTitleButton.setText(string);
	}

	/**
	 * 设置标题文字
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日上午9:57:15
	 * @param resid
	 */
	private void setTitleText(int resid) {
		setTitleText(getResources().getString(resid));
	}

	/**
	 * 设置 标题文本 颜色
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:50:30
	 * @param resId
	 */
	public void setTitleTextColor(int resId) {
		mTitleButton.setTextColor(getResources().getColor(resId));
	}

	/**
	 * 设置 标题文本 大小
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:50:14
	 * @param resId
	 */
	public void setTitleTextSize(int resId) {
		mTitleButton.setTextSize(getResources().getDimension(resId));
	}

	/**
	 * 设置右边文字
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日上午9:57:27
	 * @param string
	 */
	private void setRightText(String string) {
		mRightButton.setText(string);
	}

	/**
	 * 设置右边文字
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日上午9:57:27
	 * @param resid
	 */
	private void setRightText(int resid) {
		setRightText(getResources().getString(resid));
	}

	/**
	 * 设置 右边文本 颜色
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:50:30
	 * @param resId
	 */
	public void setRightTextColor(int resId) {
		mRightButton.setTextColor(getResources().getColor(resId));
	}

	/**
	 * 设置 右边文本 大小
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:50:14
	 * @param resId
	 */
	public void setRightTextSize(int resId) {
		mRightButton.setTextSize(getResources().getDimension(resId));
	}

	/**
	 * 设置左边文字
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日上午9:57:53
	 * @param string
	 */
	private void setLeftText(String string) {
		mLeftButton.setText(string);
	}

	/**
	 * 设置 右边文本 颜色
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:50:30
	 * @param resId
	 */
	public void setLeftTextColor(int resId) {
		mLeftButton.setTextColor(getResources().getColor(resId));
	}

	/**
	 * 设置 右边文本 大小
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:50:14
	 * @param resId
	 */
	public void setLeftTextSize(int resId) {
		mLeftButton.setTextSize(getResources().getDimension(resId));
	}

	/**
	 * 设置右边文字
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日上午9:57:27
	 * @param resid
	 */
	private void setLeftText(int resid) {
		setLeftText(getResources().getString(resid));
	}

	/**
	 * 设置标题背景
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日上午10:04:06
	 * @param resourceId
	 */
	private void setTitleBackground(int resourceId) {
		mTitleButton.setBackgroundResource(resourceId);
	}

	/**
	 * 设置右边背景
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日上午10:29:39
	 * @param resourceId
	 */
	private void setRightBackground(int resourceId) {
		mRightButton.setBackgroundResource(resourceId);
	}

	/**
	 * 设置左边背景
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日上午10:29:42
	 * @param resourceId
	 */
	private void setLeftBackground(int resourceId) {
		mLeftButton.setBackgroundResource(resourceId);
	}

	@Override
	public void onClick(View v) {

		if (listerner == null) {
			listerner.onTopTitleViewClickListener(v.getId());
		}
	}

	/**
	 * 设置 标题 内边距
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:38:15
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setTitlePadding(int left, int top, int right, int bottom) {
		mTitleButton.setPadding(left, top, right, bottom);
	}

	/**
	 * 设置 左边按钮 外边距
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:39:39
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setTitleMargins(int left, int top, int right, int bottom) {
		LayoutParams paramsTitle = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		paramsTitle.addRule(RelativeLayout.CENTER_IN_PARENT,
				RelativeLayout.TRUE);
		paramsTitle.setMargins(left, top, right, bottom);
		mRightButton.setLayoutParams(paramsTitle);
	}

	/**
	 * 设置 左边按钮 内边距
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:39:08
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setLeftPadding(int left, int top, int right, int bottom) {
		mLeftButton.setPadding(left, top, right, bottom);
	}

	/**
	 * 设置 左边按钮 外边距
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:39:39
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setLeftMargins(int left, int top, int right, int bottom) {
		LayoutParams paramsLeft = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		paramsLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
				RelativeLayout.TRUE);
		paramsLeft.setMargins(left, top, right, bottom);
		mRightButton.setLayoutParams(paramsLeft);
	}

	/**
	 * 设置 右边按钮 内边距
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:39:39
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setRightPadding(int left, int top, int right, int bottom) {
		mRightButton.setPadding(left, top, right, bottom);
	}

	/**
	 * 设置 右边按钮 外边距
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月4日下午3:39:39
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void setRightMargins(int left, int top, int right, int bottom) {
		LayoutParams paramsRight = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		paramsRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				RelativeLayout.TRUE);
		paramsRight.setMargins(left, top, right, bottom);
		mRightButton.setLayoutParams(paramsRight);
	}

	/**
	 * SoTopView 控件点击监听器
	 * 
	 * @author 房庆佑 fangqingyou
	 * @since 2015年3月17日 下午2:55:10
	 */
	public static interface OnTopTitleViewListerner {
		/**
		 * 按钮被点击的回调
		 * 
		 */
		public void onTopTitleViewClickListener(int function);
	}

}
