

package com.hep.terminalhep.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

/**
 * 
* @ClassName: HepLog
* @Description: TODO
* @author zhangj
* @date 2015年4月28日 
 */

public class HepLog {
		public static String getFileLineMethod() {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
			StringBuffer toStringBuffer = new StringBuffer("[")
					.append(traceElement.getFileName()).append(" | ")
					.append(traceElement.getLineNumber()).append(" | ")
					.append(traceElement.getMethodName()).append("()").append("]");
			return toStringBuffer.toString();
		}

		public static String _FILE_() {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
			return traceElement.getFileName();
		}

		public static String _FUNC_() {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
			return traceElement.getMethodName();
		}

		public static int _LINE_() {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
			return traceElement.getLineNumber();
		}

		public static String _TIME_() {
			Date now = new Date(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			return sdf.format(now);

		}

		public static void v(String msg) {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
			StringBuffer toStringBuffer = new StringBuffer("[")
					.append(traceElement.getFileName()).append(" | ")
					.append(traceElement.getLineNumber()).append(" | ")
					.append(traceElement.getMethodName()).append("()").append("]");
			String TAG = toStringBuffer.toString();
			Log.v(TAG, msg);
		}

		public static void d(String msg) {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
			StringBuffer toStringBuffer = new StringBuffer("[")
					.append(traceElement.getFileName()).append(" | ")
					.append(traceElement.getLineNumber()).append(" | ")
					.append(traceElement.getMethodName()).append("()").append("]");
			String TAG = toStringBuffer.toString();
		}

		public static void i(String msg) {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
			StringBuffer toStringBuffer = new StringBuffer("[")
					.append(traceElement.getFileName()).append(" | ")
					.append(traceElement.getLineNumber()).append(" | ")
					.append(traceElement.getMethodName()).append("()").append("]");
			String TAG = toStringBuffer.toString();
		}

		public static void w(String msg) {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
			StringBuffer toStringBuffer = new StringBuffer("[")
					.append(traceElement.getFileName()).append(" | ")
					.append(traceElement.getLineNumber()).append(" | ")
					.append(traceElement.getMethodName()).append("()").append("]");
			String TAG = toStringBuffer.toString();
		}

		public static void e(String msg) {
			StackTraceElement traceElement = ((new Exception()).getStackTrace())[1];
			StringBuffer toStringBuffer = new StringBuffer("[")
					.append(traceElement.getFileName()).append(" | ")
					.append(traceElement.getLineNumber()).append(" | ")
					.append(traceElement.getMethodName()).append("()").append("]");
			String TAG = toStringBuffer.toString();
		}
}
