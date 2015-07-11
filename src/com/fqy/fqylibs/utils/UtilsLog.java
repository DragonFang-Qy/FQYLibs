package com.fqy.fqylibs.utils;

import android.util.Log;

import com.fqy.fqylibs.BuildConfig;
import com.fqy.fqylibs.activity.BaseActivity;

/**
 * <p>
 * log工具类&nbsp;&nbsp;打包之后不打印 </br>部分方法的使用需要继承{@link BaseActivity} </br>
 * {@link #v(String)}、{@link #d(String)}、{@link #i(String)}、{@link #w(String)}、
 * {@link #e(String)},以上方法的使用必须继承{@link BaseActivity}
 * </p>
 * 
 * @Title UtilsLog.java
 * @Package com.fqy.fqylibs.utils
 * @Description
 * @author Fang Qingyou
 * @date 2015年7月11日下午4:35:16
 * @version V1.0
 */
public class UtilsLog {

	private UtilsLog() {

	}

	/**
	 * tag 使用类名
	 * 
	 * @param msg
	 */
	public static void v(String msg) {

		if (BuildConfig.DEBUG) {
			Log.v(BaseActivity.TAG, msg);
		}
	}

	/**
	 * 打包之后不显示log日志
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void v(String tag, String msg) {

		if (BuildConfig.DEBUG) {
			Log.v(tag, msg);
		}
	}

	/**
	 * tag 使用类名
	 * 
	 * @param msg
	 */
	public static void d(String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(BaseActivity.TAG, msg);
		}
	}

	/**
	 * 打包之后不显示log日志
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.d(tag, msg);
		}
	}

	/**
	 * tag 使用类名
	 * 
	 * @param msg
	 */
	public static void i(String msg) {
		if (BuildConfig.DEBUG) {
			Log.i(BaseActivity.TAG, msg);
		}
	}

	/**
	 * 打包之后不显示log日志
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void i(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.i(tag, msg);
		}

	}

	/**
	 * tag 使用类名
	 * 
	 * @param msg
	 */
	public static void w(String msg) {
		if (BuildConfig.DEBUG) {
			Log.w(BaseActivity.TAG, msg);
		}
	}

	/**
	 * 打包之后不显示log日志
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void w(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.w(tag, msg);
		}
	}

	/**
	 * tag 使用类名
	 * 
	 * @param msg
	 */
	public static void e(String msg) {
		if (BuildConfig.DEBUG) {
			Log.e(BaseActivity.TAG, msg);
		}
	}

	/**
	 * 打包之后不显示log日志
	 * 
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		if (BuildConfig.DEBUG) {
			Log.e(tag, msg);
		}
	}
}
