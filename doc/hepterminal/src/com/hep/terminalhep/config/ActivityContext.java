

package com.hep.terminalhep.config;

import android.app.Application;

/**
 * 
* @ClassName: ActivityContext
* @Description: TODO
* @author zhangj
* @date 2015年4月28日
 */
public class ActivityContext extends Application {
	
	private static ActivityContext Instance = null;
	
	public static final ActivityContext getCurrent() {
		return Instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Instance = this;
	}
	
	@Override
	public void onTerminate() {
		super.onTerminate();
		try {
			this.exit();
		} catch (Exception e) {				
		}
	}	
	
	public void exit() {
		ActivityManager.finishAll();
		System.exit(0);
	}
}
