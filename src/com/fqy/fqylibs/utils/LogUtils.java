package com.fqy.fqylibs.utils;

import android.util.Log;

import com.fqy.fqylibs.BuildConfig;
import com.fqy.fqylibs.activity.BaseActivity;

public class LogUtils {

	private LogUtils() {

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
