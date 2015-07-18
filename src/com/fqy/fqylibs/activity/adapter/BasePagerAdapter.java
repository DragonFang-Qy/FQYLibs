package com.fqy.fqylibs.activity.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Title: BasePagerAdapter.java
 * @Package com.fqy.fqylibs.activity.adapter
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年5月27日下午2:27:39
 * @version V1.0
 */
public class BasePagerAdapter<T> extends PagerAdapter {

	protected Context myContext;
	protected List<T> myList;
	protected boolean isInfiniteLoop;

	/**
	 * @param context
	 * @param list
	 * 
	 */
	public BasePagerAdapter(Context context, List<T> list) {
		myContext = context;
		myList = list;
		isInfiniteLoop = false;
	}

	/**
	 * @param context
	 * @param list
	 * @param b
	 *            无限循环
	 */
	public BasePagerAdapter(Context context, List<T> list, boolean b) {
		myContext = context;
		myList = list;
		isInfiniteLoop = b;
	}

	@Override
	public int getCount() {
		if (isInfiniteLoop) {
			return Integer.MAX_VALUE;
		} else {
			return myList.size();
		}

	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
		if (isInfiniteLoop && myList.size() != 0) {
			position = position % myList.size();
		}
		((ViewPager) container).removeView((View) myList.get(position));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		if (isInfiniteLoop && myList.size() > 0) {
			position = position % myList.size();
		}

		((ViewPager) container).addView((View) myList.get(position));
		return myList.get(position);
	}

}
