package com.fqy.fqylibs.activity.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @Title: FQYBaseAdapter.java
 * @Package com.fqy.fqylibs.activity.adapter
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年5月27日下午2:17:30
 * @version V1.0
 */
public abstract class BaseListAdapter<T> extends BaseAdapter {

	protected Context myContext;
	protected List<T> myList;
	protected LayoutInflater inflater;

	public BaseListAdapter(Context context, List<T> list) {
		myContext = context;
		myList = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return myList.size();
	}

	@Override
	public Object getItem(int position) {
		return myList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
