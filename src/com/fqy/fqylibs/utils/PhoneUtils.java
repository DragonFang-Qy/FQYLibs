package com.fqy.fqylibs.utils;

import java.util.regex.Pattern;

import com.fqy.fqylibs.FQYApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @Title: FQYPhoneUtils.java
 * @Package com.fqy.fqylibs.utils
 * @Description: 对手机的一些操作,包括如下方法：
 *               <p>
 *               验证是不是手机号 {@link #isPhoneNumber(String)},</br> 验证是否联网(无参)
 *               {@link #isNetworkConnected()},</br> 验证是否联网 (有参)
 *               {@link #isNetworkConnected(Context)},
 *               </p>
 * @author: Fang Qingyou
 * @date 2015年5月27日上午9:43:40
 * @version V1.0
 */
public class PhoneUtils {

	/**
	 * 验证是不是手机号码
	 * 
	 * @param phone
	 *            需要验证的手机号码 （ps:String 类型）
	 * @return boolean true 是手机号码， false 不是手机号码
	 */
	public static boolean isPhoneNumber(String phone) {

		// 包含最新的号码段 15年3月2日
		String regex = "^[1]([30-39]|45|47|[50-53]|[55-59]|[76-78]|[80-89]|70){2}[0-9]{8}";

		return Pattern.compile(regex).matcher(phone).matches();

		// 也可以使用如下代码
		// Pattern pattern = Pattern.compile(regex);
		// Matcher matcher = pattern.matcher(phone);
		// boolean b = matcher.matches();
		// return b;
	}

	/**
	 * 验证是否联网
	 * 
	 * @param context
	 *            上下文对象
	 * @return boolean true 联网 ， false 未联网
	 */
	public static boolean isNetworkConnected(Context context) {
		NetworkInfo activeNetworkInfo = null;
		if (context != null) {
			ConnectivityManager manager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			activeNetworkInfo = manager.getActiveNetworkInfo();
		}

		if (activeNetworkInfo != null) {
			return activeNetworkInfo.isAvailable();
		}
		return false;
	}

	/**
	 * 验证是否联网 <h1>ps: 必须重写一个Application 且继承 FQYApplication</h1>
	 * 
	 * @return boolean true 联网 ， false 未联网
	 */
	public static boolean isNetworkConnected() {
		NetworkInfo activeNetworkInfo = null;
		if (FQYApplication.appContext != null) {
			ConnectivityManager manager = (ConnectivityManager) FQYApplication.appContext
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			activeNetworkInfo = manager.getActiveNetworkInfo();
		}

		if (activeNetworkInfo != null) {
			return activeNetworkInfo.isAvailable();
		}
		return false;
	}

}
