

package com.hep.terminalhep.config;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.hep.terminalhep.R;
import com.hep.terminalhep.sdata.StaticStringConfig;
import com.hep.terminalhep.utils.LogManager;

/**
 * 
* @ClassName: BaseActivity
* @Description: TODO
* @author zhangj
* @date 2015年4月28日 下午1:51:37
 */
public abstract class BaseActivity extends Activity{
	
	private String pageName;
	private AlertDialog networkDialog = null;
	/**获取当前上下文*/
	protected abstract BaseActivity getThis(); 
	/**获取布局*/
	protected abstract int getResId();
	/**oncreate完成后的操作*/
	protected abstract void afterOnCreate();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (getResId() <= 0) {
			LogManager.getLogger().d("getResId %s", getResId());
			return;
		}
		if (getThis() == null) {
			LogManager.getLogger().d("getThis %s", "为空");
			return;
		}
		setContentView(getResId());
		ActivityManager.add(getThis());
		afterOnCreate();
		this.pageName = this.getClass().getName();
	}
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityManager.finish(this);
	}

	@Override
	protected void onPause() {
		super.onPause();	
	}

	@Override
	protected void onResume() {
		super.onResume();	
		checkNetworkStatus();
	}
	protected void checkNetworkStatus() {
		if (StaticStringConfig.NETWORK_DISABLE != getNetworkStatus()) {
			if(null != networkDialog) networkDialog.dismiss();
			return;
		}
		Builder b = new AlertDialog.Builder(this).setTitle(R.string.text1).setMessage(R.string.text2);
		networkDialog = b.setPositiveButton(R.string.text3, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = null;
                if(android.os.Build.VERSION.SDK_INT>10){
                    intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                }else{
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                startActivity(intent);
            }
        }).setNeutralButton(R.string.text4, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            	Intent intent = null;
                if(android.os.Build.VERSION.SDK_INT>10){
                    intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
                }else{
                    intent = new Intent();
                    ComponentName component = new ComponentName("com.android.settings","com.android.settings.wifi.WifiSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                startActivity(intent);
            }
        }).show();
	}
	
	public static int getNetworkStatus() {
        int status = StaticStringConfig.NETWORK_DISABLE;
        ConnectivityManager connectivityManager = 
        		(ConnectivityManager)ActivityContext.getCurrent().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return status;
        }        
        switch(networkInfo.getType()) {
        	case ConnectivityManager.TYPE_WIFI:
        		status= StaticStringConfig.NETWORK_WIFI;
        		break;
        	case ConnectivityManager.TYPE_MOBILE:
        		status= StaticStringConfig.NETWORK_MOBILE;
        		break;
        	default:
	        	status= StaticStringConfig.NETWORK_DISABLE;
	    		break;
        }        
        return status;
    }
	
}
