package com.jit.lib.util;

import android.app.Activity;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.api.FrontiaAuthorization;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaAuthorizationListener.AuthorizationListener;
import com.baidu.frontia.api.FrontiaAuthorizationListener.UserInfoListener;

public class LoginHelper {

	private static FrontiaAuthorization mAuthorization;

	private static FrontiaAuthorization getAuthorization() {
		if (mAuthorization == null) {
			mAuthorization = Frontia.getAuthorization();
		}
		return mAuthorization;
	}

	/**
	 * 登录到指定的平台
	 * @param activity
	 * @param listener 回调监听
	 * @param type	   平台名称
	 * @param app_key  平台申请的app_key
	 * @param isSSO	   是否启用sso
	 */
	public static void login(Activity activity, AuthorizationListener listener,
			MediaType type, String app_key, boolean isSSO) {
		getAuthorization();
		String platform = type.toString();
		if (isSSO) {
			mAuthorization.enableSSO(platform, app_key);
		}
		mAuthorization.authorize(activity, platform, listener);
	}

	/**
	 * 判断指定平台是否已经登录
	 * @param type   平台名称
	 * @return
	 */
	public static boolean isAuthorizationReady(MediaType type) {
		getAuthorization();
		return mAuthorization.isAuthorizationReady(type.toString());
	}
	
	/**
	 * 获取指定平台的用户信息
	 * @param type     平台名称
	 * @param listener 获取信息的回调接口
	 */
	public static void getUserInfo(MediaType type, UserInfoListener listener) {
		getAuthorization();
		mAuthorization.getUserInfo(type.toString(), listener);
	}
	
	/**
	 * 退出指定的平台
	 * @param type   平台名称
	 * @return
	 */
	public static boolean logout(MediaType type) {
		getAuthorization();
		boolean result = mAuthorization.clearAuthorizationInfo(type.toString());
		if (result) {
			Frontia.setCurrentAccount(null);
		}
		return result;
	}
	
	/**
	 * 退出全部的平台
	 * @return
	 */
	public static boolean logoutAll() {
		getAuthorization();
		boolean result = mAuthorization.clearAllAuthorizationInfos();
		if (result) {
			Frontia.setCurrentAccount(null);
		}
		return result;
	}
	
}
