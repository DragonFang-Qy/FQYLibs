package com.fqy.fqylibs.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @Title: UtilsWindow.java
 * @Package com.fqy.fqylibs.utils
 * @Description: TODO
 * @author: Fang Qingyou
 * @date 2015年7月28日下午4:04:59
 * @version V1.0
 */
public class UtilsWindow {

	/**
	 * 工具类禁止实例化
	 */
	private UtilsWindow() {

	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 *            上下文
	 * @param dpValue
	 *            需要转换的dp 值
	 * @return int 类型的像素值
	 */
	public static int dip2px(Context context, float dpValue) {
		// 得到系统的独立像素密度
		float scale = context.getResources().getDisplayMetrics().density;
		// 0.5f 四舍五入的左右 效率比 math 的四舍五入效率高
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * 
	 * @param context
	 *            上下文
	 * @param dpValue
	 *            需要转换的dp 值
	 * @return int 类型的像素值
	 */
	public static int px2dip(Context context, float pxValue) {
		float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 * 
	 * @param context
	 * @param spValue
	 * @return
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 返回屏幕宽度
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年7月28日下午4:18:52
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		int width = metrics.widthPixels;
		return width;
	}

	/**
	 * 返回屏幕高度
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年7月28日下午4:05:02
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(metrics);
		int height = metrics.heightPixels;
		return height;
	}

}
