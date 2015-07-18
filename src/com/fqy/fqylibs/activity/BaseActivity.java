package com.fqy.fqylibs.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.fqy.fqylibs.FQYApplication;
import com.fqy.fqylibs.R;
import com.fqy.fqylibs.manage.FQYActivityManage;
import com.lidroid.xutils.BitmapUtils;

/**
 * @Title: BaseActivity.java
 * @Package com.fqy.fqylibs.activity
 * @author: Fang Qingyou
 * @date 2015年7月11日下午4:37:38
 * @version V1.0
 */
public abstract class BaseActivity extends FragmentActivity {
	/** 基类(Activity)所使用的TAG标签 */
	public static String TAG = null;
	public static Context baseContext;

	protected FQYActivityManage activityManage;
	protected BitmapUtils myBitmapUtils;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		// 去除标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		// 用于确定当前界面是属于哪个活动(Activity), 让新加入开发的人快速锁定所在的界面,不得擅自移除.
		this.TAG = getClass().getSimpleName();

		// 获得 Activity栈对象
		activityManage = ((FQYApplication) this.getApplication())
				.getActivityManage();
		// 压栈
		activityManage.pushActivity(this);

		setContentView();
		initView();
		initListener();
		getHttpData();

		baseContext = getBaseContext();
		myBitmapUtils = new BitmapUtils(baseContext);

	}

	/**
	 * @Title: BaseActivity.java
	 * @Package com.fqy.fqylibs.activity
	 * @Description: 设置布局 (规范化，避免遗忘)
	 * @author: Fang Qingyou
	 * @date 2015年5月28日下午5:59:25
	 * @version V1.0
	 */
	public abstract void setContentView();

	/**
	 * @Title: BaseActivity.java
	 * @Package com.fqy.fqylibs.activity
	 * @Description: TODO 寻找控件，初始化控件(规范化，避免遗忘)
	 * @author: Fang Qingyou
	 * @date 2015年5月28日下午6:03:49
	 * @version V1.0
	 */
	public abstract void initView();

	/**
	 * @Title: BaseActivity.java
	 * @Package com.fqy.fqylibs.activity
	 * @Description: TODO 设置监听 (规范化，避免遗忘)
	 * @author: Fang Qingyou
	 * @date 2015年5月28日下午6:04:26
	 * @version V1.0
	 */
	public abstract void initListener();

	/**
	 * 获得网路数据
	 * 
	 * @author: Fang Qingyou
	 * @date 2015年6月26日下午2:22:45
	 */
	public abstract void getHttpData();

	@Override
	protected void onResume() {
		super.onResume();
		// 刷新数据
		this.getHttpData();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

	@Override
	public void startActivity(Intent intent) {
		super.startActivity(intent);
		overridePendingTransition(R.anim.over_left_in, R.anim.over_left_out);

	}
}
