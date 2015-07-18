

package com.hep.terminalhep.utils;

import java.util.HashMap;
import java.util.Locale;

import com.hep.terminalhep.BuildConfig;
import com.hep.terminalhep.sdata.StaticStringConfig;

import android.util.Log;

/**
 * 
* @ClassName: LogManager
* @Description: TODO
* @author zhangj
* @date 2015年4月28日
 */

public class LogManager {
	
	private static Logger Logger;
	private static HashMap<String,Logger> LoggerMap = null;
	
	public static Logger getLogger() {
		return getLogger(null);
	}
	
	public static Logger getLogger(String tag) {
		if(BuildConfig.DEBUG) {
			return getDebugLogger(tag);			
		} else {
			if (Logger == null) {
				Logger = new Logger();
			}
			return Logger;
		}
	}
	
	private static Logger getDebugLogger(String tag) {
		if(null == tag) tag = StaticStringConfig.DEFAULTTAG;
		
		Logger logger;
		if(null == LoggerMap) {
			synchronized (LogManager.class) {
				if(null == LoggerMap) LoggerMap = new HashMap<String,Logger>();	
			}
		}

		if (LoggerMap.containsKey(tag))
			logger = LoggerMap.get(tag);
		else {
			logger = new DebugLogger(tag);
			LoggerMap.put(tag, logger);
		}
		return logger;
	}

	private static class DebugLogger extends Logger {
	    private String tag;
	    
	    private DebugLogger(String tag) {    
	    	
	    	if(null != this.tag && tag == this.tag) return;    	
	    	this.tag = null == tag? StaticStringConfig.DEFAULTTAG:tag;
	    }
	    
	    @Override
	    public void v(String format, Object... args) {
	    	Log.v(tag, buildSimpleMessage(format, args));
	    }

	    @Override
	    public void d(String format, Object... args) {
	    	Log.d(tag, buildSimpleMessage(format, args));
	    }
	    
	    @Override
	    public void i(String format, Object... args) {
	    	Log.i(tag, buildSimpleMessage(format, args));
	    }
	    
	    @Override
	    public void w(String format, Object... args) {
	    	Log.w(tag, buildMessage(format, args));
	    }
	    
	    @Override
	    public void w(Throwable tr, String format, Object... args) {
	    	Log.w(tag, buildMessage(format, args), tr);
	    }

	    @Override
	    public void e(String format, Object... args) {
	    	Log.e(tag, buildMessage(format, args));
	    }

	    @Override
	    public void e(Throwable tr, String format, Object... args) {
	    	Log.e(tag, buildMessage(format, args), tr);
	    }

	    @Override
	    public void wtf(String format, Object... args) {
	    	Log.wtf(tag, buildMessage(format, args));
	    }

	    @Override
	    public void wtf(Throwable tr, String format, Object... args) {
	    	Log.wtf(tag, buildMessage(format, args), tr);
	    }

	    private String buildSimpleMessage(String format, Object... args) {
	    	return (args == null) ? format : String.format(Locale.US, format, args);
	    }
	    private String buildMessage(String format, Object... args) {
	        String msg = buildSimpleMessage(format, args);
	        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

	        String caller = "<unknown>";
	        for (int i = 2; i < trace.length; i++) {
	            Class<?> clazz = trace[i].getClass();
	            if (!clazz.equals(DebugLogger.class)) {
	                String callingClass = trace[i].getClassName();
	                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
	                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

	                caller = callingClass + "." + trace[i].getMethodName();
	                break;
	            }
	        }
	        return String.format(Locale.US, "[%d] %s: %s",
	                Thread.currentThread().getId(), caller, msg);
	    }
	}
}
