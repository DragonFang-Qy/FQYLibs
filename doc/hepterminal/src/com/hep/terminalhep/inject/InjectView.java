

package com.hep.terminalhep.inject;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;

/**
 * 
* @ClassName: InjectView
* @Description: TODO
* @author zhangj
* @date 2015年4月28日
 */

public class InjectView {
	public static void autoInjectAllField(Activity activity,Context context){
		Class clazz = context.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(ViewInject.class)) {
				ViewInject inject = field.getAnnotation(ViewInject.class);
				int id = (inject).id();
				if (id > 0) {
					field.setAccessible(true);
					try {
						field.set(activity, activity.findViewById(id));
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
