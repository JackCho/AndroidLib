package com.jit.lib.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.jit.lib.R;
import com.jit.lib.service.INet;
import com.jit.lib.util.AppManager;
import com.jit.lib.util.HttpUtil;
import com.jit.lib.util.StringUtils;
import com.jit.lib.util.UIHelper;
import com.lidroid.xutils.ViewUtils;
/**
 * 
 * 
 * FileName: BaseActivity.java
 * Description：所有Activity的基类
 * Created by 曹玉斌 on 2014-5-7
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
@SuppressLint("HandlerLeak")
public abstract class BaseActivity extends FragmentActivity {
	
	public static final int TITLE_TYPE_NO_TITLE = 0;
	public static final int TITLE_TYPE_NORMAL = 1;
	public static final int TITLE_TYPE_CUSTOM = 2;
	
	private INet iNet;
	
	private final static int SHOW_PB = 0;
	private final static int NOT_SHOW_PB = 1;
	
	public final static String LOCAL_THREAD = "local_thread";
	
	/**
	 * 为子类提供(Activity的)上下文
	 */
	protected BaseActivity mActivity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		initStyle();
		super.onCreate(savedInstanceState);
		mActivity = this;
		initTitleTypeAndLayout();
		initData();
		initTitle();
		initView();
		afterViewInit();
		if (!HttpUtil.isNetworkAvailable(this)) {
			UIHelper.showNetConnectFailureDialog(this);
		}
		AppManager.getAppManager().addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		AppManager.getAppManager().finishActivity(this);
		super.onDestroy();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			finish();
			overridePendingTransition(0, R.anim.out_from_right);
		}
		return false;
	}

	public void terminate(View v) {
		UIHelper.hideSystemKeyBoard(v);
		finish();
		overridePendingTransition(0, R.anim.out_from_right);
	}
	
	
	/**
	 * 初始化Title并加载布局
	 */
	private void initTitleTypeAndLayout() {
		int titleType = getTitleType();
		if (titleType == TITLE_TYPE_CUSTOM && this.getTitleLayout() > 0) {
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			setContentView(getLayoutId());
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					getTitleLayout());
		} else if (titleType == TITLE_TYPE_NO_TITLE) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(getLayoutId());
		} else {
			setContentView(getLayoutId());
		}
		//为XUtils初始化(必须在布局加载之后)
		ViewUtils.inject(this);
		
	}

	

	/**
	 * 代码执行于super.onCreate(savedInstanceState);之前， 主要用于初始化theme等
	 */
	protected void initStyle() {

	}
	
	/**
	 * 异步线程请求数据，适合耗时操作
	 * @param iNet
	 * 			
	 * @param what
	 */
	protected void startThread(final INet iNet, final int what) {
		startThread(iNet, what, true);
	}
	
	/**
	 * 异步线程请求数据，适合耗时操作
	 * @param iNet
	 * 			耗时请求的回调
	 * @param what
	 * 			回调的标志位
	 * @param isShowPb
	 * 			是否显示进度条
	 */
	protected void startThread(final INet iNet, final int what, final boolean isShowPb) {
		this.iNet =  iNet;
		
//		if (!HttpUtil.isNetworkAvailable(this)) {
//			UIHelper.showToastShort(BaseActivity.this, R.string.retry_after_conneted);
//			return;
//		}
		
		if (isShowPb) {
			UIHelper.showProgressDialog(this, "", getString(R.string.loading));
		}
		
		new Thread(){
			public void run() {
				String result = iNet.run(what);
				Message msg = mHandler.obtainMessage();
				msg.what = what;
				msg.obj = result;
				if (isShowPb) {
					msg.arg1 = SHOW_PB;
				} else {
					msg.arg1 = NOT_SHOW_PB;
				}
				mHandler.sendMessage(msg);
			};
		}.start();
	}
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			
			int arg1 = msg.arg1;
			if (arg1 == SHOW_PB) {
				UIHelper.dismissProgressDialog();
			}
			
			String result = (String) msg.obj;
			if (StringUtils.isEmpty(result)) {
				UIHelper.showToastShort(BaseActivity.this, R.string.retry_after_conneted);
			} else if (result.startsWith("statusCode_")) {
				UIHelper.showToastShort(BaseActivity.this, result.substring(result.indexOf("_") + 1));
			} else if (iNet != null) {
				iNet.handleAction(msg);
			}
		};
	};
	
	/**
	 * 得到layout布局文件，R.layout.activity_xxxx
	 * 
	 * @return
	 */
	protected abstract int getLayoutId();
	
	/**
	 * 初始化title(设置监听,加载标题文字等)
	 * 
	 */
	protected abstract void initTitle();

	/**
	 * 初始化数据，包括从bundle中获取数据保存到当前activity中
	 */
	protected abstract void initData();
	
	
	/**
	 * 初始化界面，如获取界面中View的名称并保存，定义Title的文字，以及定义各个控件的处理事件
	 */
	protected abstract void initView();

	/**
	 * 界面初始化之后的后处理，如启动网络读取数据、启动定位等
	 */
	protected abstract void afterViewInit();
	
	
	/**
	 * 指定当前Activity的Title显示类别，值应为：TITLE_TYPE_NO_TITLE、TITLE_TYPE_NORMAL、
	 * TITLE_TYPE_CUSTOM, 如果值为TITLE_TYPE_CUSTOM，需要重写getTitleLayout中返回TitleLayout
	 * id
	 * 
	 * @return
	 */
	protected abstract int getTitleType();
	
	/**
	 * 对于需要带Title行的Activity，重写此方法，返回title的layout xml id
	 * 
	 * @return
	 */
	protected abstract int getTitleLayout();
	
}
