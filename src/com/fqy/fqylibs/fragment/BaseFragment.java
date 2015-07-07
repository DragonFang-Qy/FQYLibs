package com.fqy.fqylibs.fragment;

import com.fqy.fqylibs.activity.BaseActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {
	protected LayoutInflater myInflater;
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
}
