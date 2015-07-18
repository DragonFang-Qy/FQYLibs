

package com.hep.terminalhep.sdata;

/**
 * 
* @ClassName: StaticStringConfig
* @Description: TODO
* @author zhangj
* @date 2015年4月28日 
 */

public class StaticStringConfig {
	/**
	 * 使用： LogManager
	 */ 
	public static final String DEFAULTTAG = "default_tag";
	/**
	 * 使用： SharedPerUtils
	 */
	public static final String DATA_HEP = "data_hep";
	/**
	 * 使用： JsonUtils
	 */
	public static final String DATEFROMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	/**
	 * 使用： BaseActivity
	 */
	public static final String REQUEST_CODE_SEED = "65535";
	/**
	 * 使用： BaseActivity
	 */
	public static final int NETWORK_DISABLE = 0;
	/**
	 * 使用： BaseActivity
	 */
	public static final int NETWORK_WIFI = 1;
	/**
	 * 使用： BaseActivity
	 */
	public static final int NETWORK_MOBILE = 2;
	/**
	 * 使用： BaseActivity
	 */
	public static final String TAG = DEFAULTTAG + ":AsyncRunnable" ;
	 /**
	  * 使用：develog跟踪 tag定义
	  * */
	 public static final String DEVETAG = DEFAULTTAG + "WebApi" ;
}
