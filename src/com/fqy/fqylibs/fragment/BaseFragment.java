package com.fqy.fqylibs.fragment;

import com.fqy.fqylibs.activity.BaseActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.core.BitmapSize;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment implements OnTouchListener {
	protected LayoutInflater myInflater;
	protected BitmapUtils myBitmapUtils;
	protected View view;
	public static Context baseContext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		BaseActivity.TAG = getClass().getSimpleName();
		getView(inflater);
		initView();
		initListener();
		initHttpData();
		baseContext = getActivity();
		myBitmapUtils = new BitmapUtils(baseContext);
		myBitmapUtils.configMemoryCacheEnabled(true);
		myBitmapUtils.configDiskCacheEnabled(true);
		myBitmapUtils.configThreadPoolSize(4 * 1024 * 1024);
		myBitmapUtils.configDefaultBitmapMaxSize(100, 100);
		return view;
	}

	/**
	 * 设置布局
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月25日上午11:56:50
	 * @param inflater
	 */
	public abstract void getView(LayoutInflater inflater);

	public abstract void initView();

	public abstract void initListener();

	public abstract void initHttpData();

	@Override
	public void onResume() {
		super.onResume();
		this.initHttpData();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		view.setOnTouchListener(this);// 拦截触摸事件，防止内存泄露下去
		super.onViewCreated(view, savedInstanceState);
	}

	/**
	 * 拦截触摸事件，防止内存泄露下去
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return true;
	}
}
