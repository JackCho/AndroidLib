package com.jit.lib.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * FileName: ValidationUtil.java
 * Description：正则验证工具类，包括邮箱、手机、网址、数字
 * Created by 曹玉斌 on 2014-5-6
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class ValidationUtil {

	public static boolean isEmailValid(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-z0-9A-Z_]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

	public static boolean isUrlValid(String url) {
		try {
			new URL(url);
			return true;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean isPhoneValid(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
	
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}
}
