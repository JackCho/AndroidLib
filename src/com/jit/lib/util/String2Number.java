package com.jit.lib.util;


/**
 * 
 * 
 * FileName: String2Number.java
 * Description：数据转换，String-->int
 * 						String --> double
 * 						String --> long
 * Created by 曹玉斌 on 2014-5-6
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class String2Number {

	/**
	 * String-->int
	 * @param str 传入的String
	 * @param defaul 默认值
	 * @return
	 */
	public static int parseInt(String str,int defaultValue){
		if (str == null || str.equals("")) {
			return defaultValue;
		}
		int i;
		try {
			i = Integer.parseInt(str);
			return i;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultValue;
		}
		
	}
	/**
	 * String --> double
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static double parseDouble(String str,double defaultValue) {
		if (str == null || str.equals("")) {
			return defaultValue;
		}
		double d;
		try {
			d = Double.parseDouble(str);
			return d;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}
	
	/**
	 * String --> long
	 * @param str
	 * @param defaultValue 默认值
	 * @return
	 */
	public static long parseLong(String str,long defaultValue) {
		if (str == null || str.equals("")) {
			return defaultValue;
		}
		long l;
		try {
			l = Long.parseLong(str);
			return l;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultValue;
		}
		
	}
}
