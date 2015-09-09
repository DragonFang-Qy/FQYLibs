package com.fqy.fqylibs.activity.adapter;

import java.util.List;

import com.fqy.fqylibs.model.ADModel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Title: ADAdpter.java
 * @Package com.fqy.fqylibs.activity.adapter
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年9月8日上午10:29:08
 * @version V1.0
 */
public class ADAdpter<T extends ADModel> extends BaseListAdapter<T> {

	public ADAdpter(Context context, List<T> list) {
		super(context, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
