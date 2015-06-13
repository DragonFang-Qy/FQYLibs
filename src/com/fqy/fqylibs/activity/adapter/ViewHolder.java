package com.fqy.fqylibs.activity.adapter;

import android.content.Context;
import android.text.Layout;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @Title: ViewHolderCommon.java
 * @Package com.fqy.fqylibs.activity.adapter
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年6月13日上午9:01:18
 * @version V1.0
 */
public class ViewHolder {
	private Context myContext;
	private View myConvertView;
	private SparseArray<View> myArray;
	private int myPosition;

	public ViewHolder(Context context, int position, ViewGroup parent,
			int layoutId) {
		myConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		myPosition = position;
		myArray = new SparseArray<View>();

		myConvertView.setTag(this);
	};

	public static ViewHolder getViewHolder(Context context, int position,
			View convertView, ViewGroup parent, int layoutId) {

		if (convertView == null) {
			return new ViewHolder(context, position, parent, layoutId);

		} else {
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.myPosition = position;
			return holder;
		}

	};

	/**
	 * 得到ConvertView 对象
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月13日下午5:30:32
	 * @return
	 */
	public View getConvertView() {
		return myConvertView;
	}

	/**
	 * 通过viewId 获取控件
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月13日下午5:31:01
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {

		View view = myArray.get(viewId);

		if (view == null) {
			view = myConvertView.findViewById(viewId);
			myArray.put(viewId, view);
		}

		return (T) view;
	}

}
