package com.jit.lib.config;

/**
 * <h3>app升级配置类</h3> 需要在程序初始化时将URL赋值
 * 
 * @author Rocky
 */
public class UpgradeConfiguration {

	private static String UPGRADE_URL;
	private static String UPGRADE_APP_NAME;
	private static String UPGRADE_APP_VERSION;
	private static String UPGRADE_APP_CODE;

	/**
	 * 初始化app升配置级类
	 * 
	 * @param upgradeUrl
	 *            (升级App的url)
	 * @param upgradeAppName
	 *            (可为空.系统将取包名中最后一个单词)
	 * @param currentAppVersion
	 *            (当前版本号) 可用AppInfoUtil.getVersionNameStr(this);
	 * @param appCode           
	 *            (更新识别的appCode)
	 * 
	 */
	public static void init(String upgradeUrl, String upgradeAppName,
			String currentAppVersion, String appCode) {
		UPGRADE_URL = upgradeUrl;
		String name = "".equals(upgradeAppName) ? ProjectConfiguration.getAppName() : upgradeAppName;
		if (!upgradeAppName.endsWith("apk")) name = name + ".apk";
		UPGRADE_APP_NAME = name;
		UPGRADE_APP_VERSION = currentAppVersion;
		UPGRADE_APP_CODE = appCode;
	}

	public static String getUpgradeUrl() {
		return UPGRADE_URL;
	}

	public static String getUpgradeAppName() {
		return UPGRADE_APP_NAME;
	}

	public static String getUpgradeAppVersion() {
		return UPGRADE_APP_VERSION;
	}

	public static String getUpgradeAppCode() {
		return UPGRADE_APP_CODE;
	}
}
