package com.fqy.fqylibs.manage;

import android.app.Activity;

import java.util.Stack;

import com.fqy.fqylibs.utils.UtilsLog;

/**
 * 自定义 activity 管理器
 * 
 * @Title: FQYActivityManage.java
 * @Package com.fqy.fqylibs.manage
 * @author: Fang Qingyou
 * @date 2015年7月11日下午5:51:55
 * @version V1.0
 */
public class FQYActivityManage {

	private static FQYActivityManage manage;
	private static Stack<Activity> activityStack;

	private FQYActivityManage() {

	}

	/**
	 * 得到一个 FQYActivityManage 对象
	 * 
	 * @return
	 */
	public static FQYActivityManage getFQYActivityManage() {
		// 保证只有一个 FQYActivityManage对象
		if (manage == null) {
			manage = new FQYActivityManage();
		}
		return manage;
	}

	/**
	 * 获得当前栈顶的activity
	 * 
	 * @return
	 */
	public Activity getCurrentStackTopActivity() {
		Activity activity = null;
		if (!activityStack.empty()) {
			activity = activityStack.lastElement();
		}
		return activity;
	}

	/**
	 * 将新的activity 压栈
	 * 
	 * @param activity
	 */
	public void pushActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);

		UtilsLog.e("push入栈 " + activity.toString());
	}

	/**
	 * 退出栈顶 activity
	 */
	public void pullActivity() {

		Activity lastElement = null;

		if (!activityStack.empty()) {
			lastElement = activityStack.lastElement();

			UtilsLog.e("pull出栈 " + lastElement.toString());

			lastElement.finish();
			activityStack.remove(lastElement);
		}
	}

	/**
	 * 关闭应用程序
	 */
	public void pullAllActivity() {
		int size = activityStack.size();
		for (int i = 0; i < size; i++) {
			pullActivity();
			size--;
		}

	}

	/**
	 * 保留指定activity
	 */
	public void pullAllActivityExceptOne(Class<?> cls) {
		Activity activity = getCurrentStackTopActivity();
		int size = activityStack.size();
		for (int i = 0; i < size; i++) {

			if (activity == null) {
				break;// 栈中没有activity 不做操作
			}

			if (activity.getClass().equals(cls)) {
				break;// 于指定类名相同时 不操作
			}

			activityStack.remove(i);
		}
	}

}
