package com.hep.terminalhep.webapi;

/**
 * 
 * @ClassName: ApiEnvironment
 * @Description: TODO 网络交互 ，协议基础常量信息配置
 * @author zhangj
 * @date 2015年4月28日
 */

public class ApiEnvironment {
	/**
	 * IP地址
	 */
	public static String SOAPIPADDRESS = "http://10.1.140.188:1234/";
	
	/**
	 * EndPoint
	 */
	public static String ENDPOINT = "MobileAppWebService.asmx";
	/**
	 *SOAP 命名空间
	 */
	public static String  SOAPSPACE = "hep.webservice";
	/**
	 * SOAP header
	 */
	public static String SOAPHEADER = "SoapHeaderVerification";
	/**
	 *  SOAP header 字段信息  
	 */
	public static String KEYNUMBER = "AppId";
	public static String KEYVALUE = "1";
	public static String KEYNUMBERS = "Authorize";
	public static String KEYVALUES = "123456";
	
	/**
	 * 方法名： 登陆
	 */
	public static String userlogin = "UserLogin";
	
	/**
	 * 方法名： 注册
	 */
	public static String userRegister = "UserTest";
	
}
