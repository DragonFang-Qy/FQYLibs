

package com.hep.terminalhep.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

/**
 * 
* @ClassName: ActivityManager
* @Description: TODO
* @author zhangj
* @date 2015年4月28日
 */

public class ActivityManager {

	private static Stack<Activity> ActivityStack;
	
	public static boolean isRunningForeground(){
		ActivityContext context = ActivityContext.getCurrent();
        String packageName = context.getPackageName();
        String topActivityName = getTopActivityName();
        if (packageName!=null && topActivityName!=null && topActivityName.startsWith(packageName)) {
            return true;
        } else {
            return false;
        }
    }
	
	public static String getTopActivityName(){
		ActivityContext context = ActivityContext.getCurrent();
        String topActivityName=null;
        android.app.ActivityManager activityManager = (android.app.ActivityManager)(context.getSystemService(android.content.Context.ACTIVITY_SERVICE ));
         List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
         if(runningTaskInfos != null){
             ComponentName component =runningTaskInfos.get(0).topActivity;
             topActivityName = component.getClassName();
         }
         return topActivityName;
    }

	public static void add(Activity activity){
		if(null == ActivityStack){
			ActivityStack = new Stack<Activity>();
		}
		ActivityStack.add(activity);
		
	}
	
	public static int size(){
		return ActivityStack.size();
	}

	public static Activity getCurrent(){
		Activity activity = ActivityStack.lastElement();
		return activity;
	}
	
	
	public static void startForResult(Class<?> clazz, int requestCode, Bundle bundle){
		Activity activity = getCurrent();
		Intent intent = new Intent();
		intent.setClass(activity, clazz);
		if(null != bundle) intent.putExtras(bundle);	
		activity.startActivityForResult(intent, requestCode);
	}

	public static void finishCurrent(){
		Activity activity = ActivityStack.lastElement();
		finish(activity);
	}

	public static void finish(Activity activity){
		if(null == activity) return;
		ActivityStack.remove(activity);
		activity.finish();
		activity=null;
	}

	public static void finishActivities(Class<?> clazz){
		List<Activity> targetActivities = new ArrayList<Activity>();
		
		for (Activity activity : ActivityStack) {
			if(activity.getClass().equals(clazz) ){
				targetActivities.add(activity);
			}
		}
		
		for (Activity activity : targetActivities) {
			finish(activity);
		}
	}

	public static void finishAll(){
		int count = ActivityStack.size();
		for (int i = 0, size = count; i < size; i++){
			Activity activity = ActivityStack.get(i);
            if (null != activity){
            	activity.finish();
            }
	    }
		ActivityStack.clear();
	}
}
