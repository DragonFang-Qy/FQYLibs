package com.fqy.fqylibs.utils;

import com.fqy.fqylibs.activity.BaseActivity;
import com.fqy.fqylibs.fragment.BaseFragment;

import android.widget.Toast;

/**
 * @Title: UtilsToast.java
 * @Package com.fqy.fqylibs.utils
 * @Description: TODO 欢迎页
 * @author: Fang Qingyou
 * @date 2015年7月7日上午10:01:00
 * @version V1.0
 */
public class UtilsToast {

	public static void toastShort(String showStr) {
		Toast.makeText(
				BaseActivity.baseContext != null ? BaseActivity.baseContext
						: BaseFragment.baseContext, showStr, Toast.LENGTH_SHORT)
				.show();
	}

	public static void toastLong(String showStr) {
		Toast.makeText(
				BaseActivity.baseContext != null ? BaseActivity.baseContext
						: BaseFragment.baseContext, showStr, Toast.LENGTH_LONG)
				.show();
	}

	public static void toastShort(int resId) {
		Toast.makeText(
				BaseActivity.baseContext != null ? BaseActivity.baseContext
						: BaseFragment.baseContext,
				BaseActivity.baseContext != null ? BaseActivity.baseContext
						.getResources().getString(resId)
						: BaseFragment.baseContext.getResources().getString(
								resId), Toast.LENGTH_SHORT).show();
	}

	public static void toastLong(int resId) {
		Toast.makeText(
				BaseActivity.baseContext != null ? BaseActivity.baseContext
						: BaseFragment.baseContext,
				BaseActivity.baseContext != null ? BaseActivity.baseContext
						.getResources().getString(resId)
						: BaseFragment.baseContext.getResources().getString(
								resId), Toast.LENGTH_LONG).show();
	}

}
