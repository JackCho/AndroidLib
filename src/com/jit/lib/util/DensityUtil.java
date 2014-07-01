package com.jit.lib.util;

import android.content.Context;
import android.view.View;

/**
 * 
 * 
 * FileName: DensityUtil.java
 * Description：手机分辨率工具类，获取手机宽高以及单位之间的转换
 * Created by 曹玉斌 on 2014-5-6
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class DensityUtil {

	public static final float getDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}
	
	public static final float getHeightInPx(Context context) {
		final float height = context.getResources().getDisplayMetrics().heightPixels;
		return height;
	}

	public static final float getWidthInPx(Context context) {
		final float width = context.getResources().getDisplayMetrics().widthPixels;
		return width;
	}

	public static final int getHeightInDp(Context context) {
		final float height = context.getResources().getDisplayMetrics().heightPixels;
		int heightInDp = px2dip(context, height);
		return heightInDp;
	}

	public static final int getWidthInDp(Context context) {
		final float height = context.getResources().getDisplayMetrics().heightPixels;
		int widthInDp = px2dip(context, height);
		return widthInDp;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int px2sp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int sp2px(Context context, float spValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (spValue * scale + 0.5f);
	}

	public static int getHeight(View view) {
		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		view.measure(w, h);
		return view.getMeasuredHeight();
	}
}
