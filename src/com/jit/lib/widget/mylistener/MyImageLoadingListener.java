package com.jit.lib.widget.mylistener;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageLoadingListener;

/**
 * 
 * 
 * FileName: MyImageLoadingListener.java
 * Description：ImageLoadingListener的实现类，简化图片加载的回调
 * Created by 曹玉斌 on 2014-5-6
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class MyImageLoadingListener implements ImageLoadingListener {

	@Override
	public void onLoadingStarted(String imageUri, View view) {

	}

	@Override
	public void onLoadingFailed(String imageUri, View view,
			FailReason failReason) {

	}

	@Override
	public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

	}

	@Override
	public void onLoadingCancelled(String imageUri, View view) {

	}

}
