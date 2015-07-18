

package com.hep.terminalhep.repositories;

import java.util.Map;

import android.content.Context;

import com.hep.terminalhep.data.utils.JsonUtils;
import com.hep.terminalhep.models.UserLoginModel;
import com.hep.terminalhep.sdata.StaticStringConfig;
import com.hep.terminalhep.utils.LogManager;
import com.hep.terminalhep.webapi.ApiEnvironment;
import com.hep.terminalhep.webapi.WebApiClient;

/**
 * @Title: @AccountNmberInformation.java
 * @author zhangj
 * @version 1.0
 * @date 2015年4月28日
 * @Description: TODO 用户登陆，注册，找回密码
 */

public class AccountNmberInformation {
	/**
	* @Title: UserLogin
	* @Description: TODO 用户登陆 成功返回是字符串，失败返回 null
	* @param @param cntext
	* @param @param map
	* @param @return    设定文件
	* @author zhangj
	* @return UserLoginModel    返回类型
	* @date 2015年4月29日 上午11:56:52
	* @throws
	 */
	public static UserLoginModel UserLogin(Context cntext,Map<String,Object> map){
		String result = WebApiClient.getInstance().soaphttp(ApiEnvironment.userlogin, map,cntext);
		if(!result.equals("") || !result.equals(null) ) {
			LogManager.getLogger(StaticStringConfig.DEVETAG).e("Response:success=>", result.equals("")||result.equals(null) ? null : result.toString());
			return JsonUtils.ToEntity(result, UserLoginModel.class) ;
		}else return null;
	}
	/**
	 * 
	* UserLogins(这里用一句话描述这个方法的作用)
	* @Title: UserLogins
	* @Description: TODO
	* @param @param cntext
	* @param @param map
	* @param @return    设定文件
	* @author zhangj
	* @return UserLoginModel    返回类型
	* @date 2015年4月30日 下午10:30:44
	* @throws
	 */
	public static UserLoginModel UserLogins(Context cntext,Map<String,Object> map){
		String result = WebApiClient.getInstance().soaphttp(ApiEnvironment.userRegister, map,cntext);
		if(!result.equals("") || !result.equals(null) ) {
			LogManager.getLogger(StaticStringConfig.DEVETAG).e("Response:success=>", result.equals("")||result.equals(null) ? null : result.toString());
			return JsonUtils.ToEntity(result, UserLoginModel.class) ;
		}else return null;
	}
}
