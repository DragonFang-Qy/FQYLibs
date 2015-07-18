package com.fqy.fqylibs.activity.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @Title: BaseFragmentPagerAdapter.java
 * @Package com.fqy.fqylibs.activity.adapter
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年6月11日上午10:38:28
 * @version V1.0
 */
public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {

	/**
	 * 上下文对象
	 * 
	 * @author: Fang Qingyou
	 */
	protected Context myContext;
	/**
	 * Fragment 类数组
	 * 
	 * @author: Fang Qingyou
	 */
	protected Class[] myFragments;
	/**
	 * 无限循环标记
	 * 
	 * @author: Fang Qingyou
	 */
	protected boolean isInfiniteLoop;

	/**
	 * @author: Fang Qingyou
	 * @date 2015年6月11日上午11:20:34
	 * @param context
	 *            上下文
	 * @param fragments
	 *            Fragment 类数组
	 * @param fm
	 *            FragmentManager
	 */
	public BaseFragmentPagerAdapter(Context context, Class[] fragments,
			FragmentManager fm) {
		super(fm);
		isInfiniteLoop = false;
		myContext = context;
		myFragments = fragments;
	}

	/**
	 * @author: Fang Qingyou
	 * @date 2015年6月11日上午11:20:37
	 * @param context
	 *            上下文
	 * @param fragments
	 *            Fragment 类数组
	 * @param fm
	 *            FragmentManager
	 * @param b
	 *            循环标记
	 */
	public BaseFragmentPagerAdapter(Context context, Class[] fragments,
			FragmentManager fm, boolean b) {
		super(fm);
		isInfiniteLoop = false;
		myContext = context;
		myFragments = fragments;
		isInfiniteLoop = b;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		try {
			fragment = (Fragment) myFragments[position].newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return fragment;
	}

	@Override
	public int getCount() {

		if (isInfiniteLoop) {
			return Integer.MAX_VALUE;
		} else {
			return myFragments.length;
		}

	}

}
