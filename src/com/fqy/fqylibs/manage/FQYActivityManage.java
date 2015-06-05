package com.fqy.fqylibs.manage;

import android.app.Activity;

import java.util.Stack;

import com.fqy.fqylibs.utils.LogUtils;

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

		LogUtils.e("push入栈 " + activity.toString());
	}

	/**
	 * 退出栈顶 activity
	 */
	public void pullActivity() {

		Activity lastElement = null;

		if (!activityStack.empty()) {
			lastElement = activityStack.lastElement();

			LogUtils.e("pull出栈 " + lastElement.toString());

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

	// /**
	// * 保留指定activity
	// */
	// public void pullAllActivity(Class<?> cls) {
	// Activity activity = getCurrentStackTopActivity();
	// int size = activityStack.size();
	// for (int i = 0; i < size; i++) {
	//
	// if (activity == null) {
	// break;// 栈中没有activity 不做操作
	// }
	//
	// if (activity.getClass().equals(cls)) {
	// break;
	// }
	//
	// size--;
	// }
	// }

}
