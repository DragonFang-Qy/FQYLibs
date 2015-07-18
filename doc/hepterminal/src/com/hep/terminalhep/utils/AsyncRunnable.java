

package com.hep.terminalhep.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * 
* @ClassName: AsyncRunnable
* @Description: TODO
* @author zhangj
* @date 2015年4月28日 
* @param <T>
 */

public class AsyncRunnable<T> {
	
	private Handler handler;
	private Runnable runnable;
	private T model = null;
	
	protected T doInBackground(Void... params) {
    	return null;
    }
    protected void onPostExecute(T model){
    	LogManager.getLogger().d("onPostExecute(%s)", model);
    	
    }
    
    protected void onPostError(Exception ex) {
    	LogManager.getLogger().e(ex, "[AsyncEntityTask:run] error");
    };
    
    private void postError(Exception ex) {
    	try {
    		onPostError(ex);
		} catch (Exception tr) {
			LogManager.getLogger().e(ex, "[AsyncEntityTask:run] error");
			LogManager.getLogger().e(tr, "[AsyncEntityTask:onPostError]error");
		}
    }
    
    public AsyncRunnable(){
		this.handler = new Handler(Looper.getMainLooper());
		this.runnable = new Runnable() {
			@Override
			public void run() {
				try {
					onPostExecute(model);
				} catch (Exception ex) {
					postError(ex);
				}				
			}
		};
	}
	
	public void execute() 	{		
		new Thread() {
			@Override
			public void run() {
				try	{
					model = doInBackground();
					handler.post(runnable);
				} catch (final Exception ex) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							postError(ex);							
						}
					});
				}
			}
		}.start();
	}
}
