package com.jit.lib.base;

import android.app.Application;
import com.jit.lib.config.ProjectConfiguration;
import com.jit.lib.util.PreferenceUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 
 * 
 * FileName: BaseApplication.java
 * Description：全局Application基类
 * Created by 曹玉斌 on 2014-5-7
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class BaseApplication extends Application {

	private static BaseApplication application;
	
	public static BaseApplication newInstance() {
		return application;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		application = this;
		initUtils();
		initConfigurations();
	}
	
	
	/**
	 * 配置类的初始化
	 */
	private void initConfigurations() {
		ProjectConfiguration.init(getPackageName());
	}

	/**
	 * 工具类的初始化
	 */
	private void initUtils() {
		PreferenceUtil.init(this);
		initImageLoader();
	}
	
	/**
	 * ImageLoader的配置初始化
	 */
	private void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
				.memoryCacheExtraOptions(480, 800)
				.threadPoolSize(5)
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.build();
		ImageLoader.getInstance().init(config);
	}
}
