

package com.hep.terminalhep.data.utils;

import com.hep.terminalhep.sdata.StaticStringConfig;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
* @ClassName: SharedPerUtils
* @Description: TODO
* @author zhangj
* @date 2015年4月28日 
 */

public class SharedPerUtils {
	
	private static SharedPreferences sharedPreferences;
	private static SharedPerUtils instance;
	
	public SharedPerUtils(Context context) {
		sharedPreferences = context.getSharedPreferences(StaticStringConfig.DATA_HEP,
				Context.MODE_PRIVATE);
	}

	public synchronized static SharedPerUtils getInstanse(Context context) {
		if (instance == null) {
			instance = new SharedPerUtils(context.getApplicationContext());
		}
		return instance;
	}
	
	
	public  String getData(String keyValues){
		String ipdata = sharedPreferences.getString(keyValues, "");
		return ipdata;
	}
	
	public void setData(String keyValues,String values){
		Editor editor = sharedPreferences.edit();
		editor.putString(keyValues, values);
		editor.commit();
	}
}
