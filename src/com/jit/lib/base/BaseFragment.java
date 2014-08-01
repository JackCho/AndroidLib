package com.jit.lib.base;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jit.lib.service.INet;
import com.jit.lib.util.HttpUtil;
import com.jit.lib.util.StringUtils;
import com.jit.lib.util.ThreadPool;
import com.jit.lib.util.UIHelper;
import com.lidroid.xutils.ViewUtils;

public abstract class BaseFragment extends Fragment{
	/**
	 * 为子类提供(Activity的)上下文
	 */
	protected BaseActivity mActivity;
	
	private INet iNet;
	
	private final static int SHOW_PB = 0;
	private final static int NOT_SHOW_PB = 1;
	
	public final static String LOCAL_THREAD = "local_thread";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		mActivity =(BaseActivity) getActivity();
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(getLayoutId(), null);
		//为XUtils初始化(必须在布局加载之后)
		ViewUtils.inject(this,root);
		initData();
		initView(root);
		afterViewInit();
		if (!HttpUtil.isNetworkAvailable(mActivity)) {
			UIHelper.showNetConnectFailureDialog(mActivity);
		}
		return root;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacksAndMessages(null);
	}
	
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
	 * @param root onCreateView返回的根视图的view
	 */
	protected abstract void initView(View root);

	/**
	 * 界面初始化之后的后处理，如启动网络读取数据、启动定位等
	 */
	protected abstract void afterViewInit();
	
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
		
		if (!HttpUtil.isNetworkAvailable(mActivity)) {
			UIHelper.showToastShort(mActivity, "请连接网络后重试");
			return;
		}
		
		if (isShowPb) {
			UIHelper.showProgressDialog(mActivity, "", "正在为您努力加载...");
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
	
	protected Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			
			int arg1 = msg.arg1;
			if (arg1 == SHOW_PB) {
				UIHelper.dismissProgressDialog();
			}
			
			String result = (String) msg.obj;
			if (StringUtils.isEmpty(result)) {
				UIHelper.showToastShort(mActivity, "请连接网络后重试");
			} else if (result.startsWith("statusCode_")) {
				UIHelper.showToastShort(mActivity, result.substring(result.indexOf("_") + 1));
			} else if (iNet != null) {
				iNet.handleAction(msg);
			}
		};
	};

}
