package com.jit.lib.service;

import android.os.Message;


public interface INet {
	
	/**
	 * 网络请求
	 */
	public String run(int what);
	
	/**
	 * 处理网络请求回调
	 */
	public void handleAction(Message msg);
}
