

package com.hep.terminalhep.data.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hep.terminalhep.sdata.StaticStringConfig;

/**
 * 
* @ClassName: JsonUtils
* @Description: TODO 数据翻转
* @author zhangj
* @date 2015年4月28日 下午1:51:50
 */

public class JsonUtils {
	
	public static Gson BuildGson() {
		return new GsonBuilder().setDateFormat(StaticStringConfig.DATEFROMAT).create();
	}
	/**
	 * 
	* @Title: ToJson
	* @Description: TODO  object4json
	* @param @param obj
	* @param @return    设定文件
	* @author zhangj
	* @return String    返回类型
	* @date 2015年4月29日 下午4:37:21
	* @throws
	 */
	public static String ToJson(Object obj) {
		Gson gson = BuildGson();
		return gson.toJson(obj);
	}
	/**
	* @Title: ToJson
	* @Description: TODO  object4json
	* @param @param obj
	* @param @return    设定文件
	* @author zhangj
	* @return String    返回类型
	* @date 2015年4月29日 下午4:37:21
	* @throws
	 */
	public static <T> T ToEntity(String json, Type target) {
		Gson gson = BuildGson();
		String FiltrationData = json.replaceAll("new ", "").replaceAll("Date", "").replaceAll("\\(", "").replaceAll("\\)", "");
		return gson.fromJson(FiltrationData,target);
	}
}
