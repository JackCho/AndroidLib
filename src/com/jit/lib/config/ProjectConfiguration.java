package com.jit.lib.config;

import java.io.File;

import android.os.Environment;

/**
 * 项目配置类
 * 在对应项目application中初始化
 * @author Rocky
 *
 */
public class ProjectConfiguration {
	
	/**
	 * 对应项目名称
	 * (如com.jitmarketing.energon 则该名字为energon)
	 * 对应项目启动时候.由对应项目的application中赋值
	 */
	private static String APP_NAME;
	
	/**
	 * 项目路径
	 */
	private static String ROOT_DIR;
	
	
	/**
	 * 项目配置类初始化
	 * @param packageName 根据包名生成项目名称
	 * (com.jitmarketing.energon 则该名字为energon)
	 *  截取最后一个单词
	 */
	public static void init(String packageName){
		String appName = packageName.substring(packageName.lastIndexOf(".")+1, packageName.length());
		APP_NAME = appName;
		ROOT_DIR = Environment.getExternalStorageDirectory() + File.separator + appName + File.separator ;
	}

	
//	/**
//	 * 项目配置类初始化
//	 * @param appName 对应项目名称
//	 */
//	public static void init(String appName){
//		APP_NAME = appName;
//		ROOT_DIR = Environment.getExternalStorageDirectory() + File.separator + appName + File.separator ;
//	}
	
	/**
	 * 获取appName
	 * (如com.jitmarketing.energon 则该名字为energon)
	 * @return
	 */
	public static String getAppName() {
		return APP_NAME;
	}
	
	/**
	 * 获取对应项目工作目录
	 * (如com.jitmarketing.energon 则该路径为"SDCard/energon/)
	 * @return
	 */
	public static String getRootDir() {
		return ROOT_DIR;
	}

}
