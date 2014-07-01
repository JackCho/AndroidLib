package com.jit.lib.config;

import com.jit.lib.util.PreferenceUtil;

/**
 * <h3>平台切换配置类</h3> 
 * 需要在程序初始化时将URL赋值
 * @author Rocky
 */
public class PlatConfiguration {
	
	//以下3个常量由对应的app初始化时赋值
	private static String PUBLIC_ROOT_URL = "";
	private static String DEV_ROOT_URL = "";
	private static String TEST_ROOT_URL = "";
	/**
	 * 平台切换的key
	 */
	private static final String SWITCH_PLAT_TAG = "switch_plat_tag";
	/**
	 * 正式环境
	 */
	public static final int PUBLIC_PLAT_TAG = 1;
	/**
	 * 开发环境
	 */
	public static final int DEV_PLAT_TAG = 2;
	/**
	 * 测试环境
	 */
	public static final int TEST_PLAT_TAG = 3;
	
	
	/**
	 * 初始化URL路径(将程序定义的url传入到lib中)
	 * <li>依次为</li>
	 * <li>PUBLIC_PLAT_TAG</li>
	 * <li>DEV_PLAT_TAG</li>
	 * <li>TEST_PLAT_TAG</li>
	 */
	public static void init(String ...url){
		PUBLIC_ROOT_URL = url[0];
		DEV_ROOT_URL = url[1];
		TEST_ROOT_URL = url[2];
	}
	
	/**
	 * 从sp中获取rootUrl
	 * @return rootUrl
	 */
	public static String getRootUrl(){
		String rootUrl = PUBLIC_ROOT_URL;
		switch (getCurPlatTag()) {
		case PUBLIC_PLAT_TAG :
			rootUrl = PUBLIC_ROOT_URL;
			break;
		case DEV_PLAT_TAG:
			rootUrl = DEV_ROOT_URL;
			break;
		case TEST_PLAT_TAG:
			rootUrl = TEST_ROOT_URL;
			break;
		default:
			break;
		}
		return rootUrl;
	}
	
	/**
	 * 从sp中获取当前平台标识
	 * @return 当前平台标识
	 */
	public static int getCurPlatTag(){
		return PreferenceUtil.getInt(SWITCH_PLAT_TAG, 1);
	}
	
	/**
	 * 设定当前平台</b>
	 * @param 可选三个参数
	 * <li>PUBLIC_PLAT_TAG</li>
	 * <li>DEV_PLAT_TAG</li>
	 * <li>TEST_PLAT_TAG</li>
	 * 	
	 */
	public static void setPlatTag(int curPlat){
		if(curPlat<1||curPlat>3) return;
		PreferenceUtil.commitInt(SWITCH_PLAT_TAG, curPlat);
	}
	
}
