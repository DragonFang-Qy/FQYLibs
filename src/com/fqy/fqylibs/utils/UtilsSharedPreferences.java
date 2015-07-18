package com.fqy.fqylibs.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @Title: FQYSharedPreferencesUtils.java
 * @Package com.fqy.fqylibs.utils
 * @Description: TODO SharedPreferences 的工具类(抽象类)，get、set在子类中实现。
 *               <p>
 *               提供了两个构造方法 ， </br>有sp文件名
 *               {@link #FQYSharedPreferencesUtils(Context)}</br> 无sp文件名
 *               {@link #FQYSharedPreferencesUtils(Context, String)}
 *               </p>
 * @author: Fang Qingyou
 * @date 2015年5月27日上午10:06:06
 * @version V1.0
 */
public class UtilsSharedPreferences {

	protected static UtilsSharedPreferences sp;
	protected static SharedPreferences preferences;
	protected static Editor edit;

	/**
	 * 构造方法 默认sp 文件名为FQYData
	 * 
	 * @param context
	 */
	protected UtilsSharedPreferences(Context context) {
		preferences = context.getSharedPreferences("FQYData",
				Context.MODE_PRIVATE);
		edit = preferences.edit();
	}

	/**
	 * 构造方法 自己设置sp 文件名,通过 name参数
	 * 
	 * @param context
	 * @param name
	 */
	protected UtilsSharedPreferences(Context context, String name) {
		preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		edit = preferences.edit();
	}

}
