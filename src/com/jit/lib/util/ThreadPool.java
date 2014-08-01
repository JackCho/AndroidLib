package com.jit.lib.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
	
	private static ExecutorService pool = null;
	//默认线程池大小为3
	private final static int threadNum = 3;
	
	public static ExecutorService  getInstance() {
		if (pool == null) {
			pool = Executors.newFixedThreadPool(threadNum);
		}
		return pool;
	}

}
