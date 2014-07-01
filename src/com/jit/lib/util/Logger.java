package com.jit.lib.util;

import android.util.Log;

/**
 * 
 * 
 * FileName: Logger.java
 * Description：打印日志管理类(DEBUG_STATE开发环境为true,正式环境为false)
 * Created by 曹玉斌 on 2014-5-6
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class Logger {
	private static final String COMMON_TAG = "qixin";
	
	public static boolean DEBUG_STATE = true;

    public static void v(String tag, String msg) {
        if (DEBUG_STATE) {
        	Log.v(COMMON_TAG, "[" + tag + "] " + msg);
        }
    }
    
    public static void d(String tag, String msg) {
        if (DEBUG_STATE) {
        	Log.d(COMMON_TAG, "[" + tag + "] " + msg);
        }
    }
    
    public static void i(String tag, String msg) {
        if (DEBUG_STATE) {
        	Log.i(COMMON_TAG, "[" + tag + "] " + msg);
        }
    }
    
    public static void e(String tag, String msg) {
    	 if (DEBUG_STATE) {
    		 Log.e(COMMON_TAG, "[" + tag + "] " + msg);
    	 }
        
    }
    
    public static void w(String tag, String msg) {
    	 if (DEBUG_STATE) {
    		 Log.w(COMMON_TAG, "[" + tag + "] " + msg);
    	 }
        
    }
    
}
