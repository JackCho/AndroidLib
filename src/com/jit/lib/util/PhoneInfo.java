package com.jit.lib.util;


import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

/**
 * 
 * 
 * FileName: PhoneInfo.java
 * Description：获取手机设备信息以及运营商信息
 * Created by 曹玉斌 on 2014-5-6
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class PhoneInfo {

	private static  TelephonyManager telephonyManager;
	
	/**
	 * 国际移动用户识别码
	 */
	private String IMSI;
	
	private static TelephonyManager getTelephonyManager(Context context) {
		if (telephonyManager == null) {
			telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}
		
		return telephonyManager;
	}
	
	/**
	 * 获取手机设备号
	 * @return
	 */
	public static String getImei(Context context){
		return Secure.getString(context.getContentResolver(),Secure.ANDROID_ID);
	}

	/**
	 * 获取电话号码,不一定获取的到
	 */
	public static String  getNativePhoneNumber(Context context) {
		String NativePhoneNumber = null;
		NativePhoneNumber = getTelephonyManager(context).getLine1Number();
		return NativePhoneNumber;
	}

	/**
	 * 获取手机服务商信息
	 */
	public String getProvidersName() {
		String ProvidersName = "N/A";
		try{
		IMSI = telephonyManager.getSubscriberId();
		// IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
		System.out.println(IMSI);
		if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
			ProvidersName = "中国移动";
		} else if (IMSI.startsWith("46001")) {
			ProvidersName = "中国联通";
		} else if (IMSI.startsWith("46003")) {
			ProvidersName = "中国电信";
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return ProvidersName;
	}
	
	public String  getPhoneInfo(Context context){
		TelephonyManager tm = getTelephonyManager(context);
        StringBuilder sb = new StringBuilder();
        sb.append("\nDeviceId(IMEI) = " + tm.getDeviceId());
        sb.append("\nDeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion());
        sb.append("\nLine1Number = " + tm.getLine1Number());
        sb.append("\nNetworkCountryIso = " + tm.getNetworkCountryIso());
        sb.append("\nNetworkOperator = " + tm.getNetworkOperator());
        sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());
        sb.append("\nNetworkType = " + tm.getNetworkType());
        sb.append("\nPhoneType = " + tm.getPhoneType());
        sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
        sb.append("\nSimOperator = " + tm.getSimOperator());
        sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
        sb.append("\nSimSerialNumber = " + tm.getSimSerialNumber());
        sb.append("\nSimState = " + tm.getSimState());
        sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
        sb.append("\nVoiceMailNumber = " + tm.getVoiceMailNumber());
       return  sb.toString();
	}
}