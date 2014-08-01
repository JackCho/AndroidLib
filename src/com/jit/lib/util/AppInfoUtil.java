package com.jit.lib.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

public class AppInfoUtil {
	
	/**
	 * 返回版本号
	 * @param context
	 * @return
	 */
	public static float getVersionName(Context context) {
		try {
			PackageManager pm = context.getPackageManager();

			PackageInfo pinfo = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			float versionCode = Float.parseFloat(pinfo.versionName);
			return versionCode;
		} catch (NameNotFoundException e) {
		}
		return 0;
	}

	public static String getVersionNameStr(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pinfo = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			return pinfo.versionName;
		} catch (NameNotFoundException e) {
		}
		return "";
	}

	public static int getVersionCode(Context context) {
		try {
			PackageManager pm = context.getPackageManager();

			PackageInfo pinfo = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			return pinfo.versionCode;
		} catch (NameNotFoundException e) {
		}
		return 0;
	}
	
	public static String getVersionCodeStr(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pinfo = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_CONFIGURATIONS);
			return String.valueOf(pinfo.versionCode);
		} catch (NameNotFoundException e) {
		}
		return "";
	}
	
	/**
	 * 获取APP label名字
	 * @param context
	 * @return
	 */
	public static String getAppNameStr(Context context) {
		try {
			PackageManager pm = context.getPackageManager();
			ApplicationInfo appInfo = pm.getApplicationInfo(
					context.getPackageName(), 0);
			return (String) pm.getApplicationLabel(appInfo);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return "";
	}
	
	/**
	 * 获取app图标
	 * @param context
	 * @return
	 */
	public static Drawable getAppIcon(Context context) {
		PackageManager pm = context.getPackageManager();
		try {
			ApplicationInfo appInfo = pm.getApplicationInfo(
					context.getPackageName(), 0);
			return pm.getApplicationIcon(appInfo);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}
