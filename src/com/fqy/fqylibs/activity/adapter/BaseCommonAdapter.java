package com.fqy.fqylibs.activity.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @Title: BaseCommonAdapter.java
 * @Package com.fqy.fqylibs.activity.adapter
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年6月13日上午8:58:54
 * @version V1.0
 */
public abstract class BaseCommonAdapter<T> extends BaseListAdapter<T> {

	private int myLayoutId;

	public BaseCommonAdapter(Context context, List<T> list, int layoutId) {
		super(context, list);
		myLayoutId = layoutId;
	}

	@Override
	public T getItem(int position) {
		return myList.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = ViewHolder.getViewHolder(myContext, position,
				convertView, parent, myLayoutId);
		convert(holder, getItem(position));
		return holder.getConvertView();
	}

	public abstract void convert(ViewHolder holder, T t);
}
