package com.jit.lib.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

import com.jit.lib.R;
import com.jit.lib.service.INet;
import com.jit.lib.util.AppManager;
import com.jit.lib.util.HttpUtil;
import com.jit.lib.util.StringUtils;
import com.jit.lib.util.ThreadPool;
import com.jit.lib.util.UIHelper;
import com.lidroid.xutils.ViewUtils;

public abstract class BaseActivity extends FragmentActivity {
	
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
		super.onCreate(savedInstanceState);
		mActivity = this;
		initTitleTypeAndLayout();
		initData();
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
		mHandler.removeCallbacksAndMessages(null);
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
	 * 加载布局
	 */
	private void initTitleTypeAndLayout() {
		setContentView(getLayoutId());
		//为XUtils初始化(必须在布局加载之后)
		ViewUtils.inject(this);
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
		
		if (isShowPb) {
			UIHelper.showProgressDialog(this, "", getString(R.string.loading));
		}
		
		ThreadPool.getInstance().execute(new Runnable() {
			
			@Override
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
			}
		});
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
	
	
}
