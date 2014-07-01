package com.jit.lib.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jit.lib.R;

/**
 * 
 * 
 * FileName: UIHelper.java
 * Description：UI相关的工具类，包括展示ProgressDialog,Toast,startActivity等操作
 * Created by 曹玉斌 on 2014-5-6
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class UIHelper {

	private static ProgressDialog mDialog;
	private static Dialog mAlertDialog;
	private static Toast mToast;

	public static void hideTitle(Activity activity) {
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public static void showProgressDialog(Context context, String title, String msg) {
		if (mDialog != null && mDialog.isShowing() == true) {
			return;
		}
		mDialog = ProgressDialog.show(context, title, msg, true, false);
	}

	public static void showProgressDialog(Context context, int titleId, int msgId) {
		showProgressDialog(context, context.getString(titleId),
				context.getString(msgId));
	}

	public static void dismissProgressDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
	}
	
	public static boolean isDialogShowing() {
		if (mDialog != null) {
			return mDialog.isShowing();
		}
		return false;
	}

	public static void showToastShort(Context context, String content) {
		showToast(context, content, 0);
	}

	public static void showToastShort(Context context, int resId) {
		showToastShort(context, context.getString(resId));
	}

	public static void showToastLong(Context context, String content) {
		showToast(context, content, 1);
	}

	public static void showToastLong(Context context, int resId) {
		showToastLong(context, context.getString(resId));
	}

	public static void showToast(Context context, String content, int duration) {
		if (mToast == null) {
			mToast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(content);
		}
		mToast.show();
	}

	public static void toast(Context context, String content) {
		showToastShort(context, content);
	}

	public static void toast(Context context, int contentid) {
		toast(context, context.getString(contentid));
	}

	public static void showToast(Context context, int resId, int duration) {
		showToast(context, context.getString(resId), duration);
	}
	
	public static void jumpActivity(Context context, Class<?> cls){
		jumpActivityWithBundle(context, cls, null);
	}
	
	public static void jumpActivityWithBundle(Context context, Class<?> cls, Bundle bundle){
		Intent intent = new Intent(context, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
		((Activity)context).overridePendingTransition(R.anim.in_from_right, 0);
	}
	
	public static void jumpActivityForResult(Context context, Class<?> cls, int requestCode){
		jumpActivityWithBundleForResult(context, cls, null, requestCode);
	}
	
	public static void jumpActivityWithBundleForResult(Context context, Class<?> cls, Bundle bundle, int requestCode){
		Intent intent = new Intent(context, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		((Activity)context).startActivityForResult(intent, requestCode);
		((Activity)context).overridePendingTransition(R.anim.in_from_right, 0);
	}
	
	
	public static void hideSystemKeyBoard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
	}
	
	public static void showSystemKeyBoard(View v) {
		InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(v, 0);
	}

	public static void call(Activity context, String phoneNum) {
		context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum)));
		context.overridePendingTransition(R.anim.in_from_right, 0);
	}

	public static void sendEmail(Context context, String addr) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		String[] receivers = new String[] { addr };
		emailIntent.putExtra(Intent.EXTRA_EMAIL, receivers);
		emailIntent.setType("plain/text");
		context.startActivity(Intent.createChooser(emailIntent,"Choose Email Client"));
	}

	public static void sendMsg(Context context, String tele, String content) {
		Uri uri = Uri.parse("smsto:" + tele);            
		Intent it = new Intent(Intent.ACTION_SENDTO, uri);            
		if (!StringUtils.isEmpty(content)) {
			it.putExtra("sms_body", content);      
		}
		context.startActivity(it); 
	}

	public static void showMapByWeb(Context context, double lat, double lng,
			String title, String addrDesc) {
		String url = "http://api.map.baidu.com/marker?location=" + lat + ","
				+ lng + "&title=" + title + "&content=" + addrDesc
				+ "&output=html";
		showWebPage(context, url);
	}

	public static void showWebPage(Context context, String url) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri uri = Uri.parse(url);
		intent.setData(uri);
		context.startActivity(intent);
	}
	
	public static void showNetConnectFailureDialog(final Activity activity) {
		mAlertDialog = new AlertDialog.Builder(activity)
		.setTitle(R.string.lib_noteTitle)
		.setMessage(R.string.lib_connectionErrorMsg1)
		.setPositiveButton(R.string.lib_sureTitle,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
					    Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
						activity.startActivity(intent);
					}
				})
				.setNegativeButton(R.string.lib_cancelTitle, 
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// do nothing
							}
						}).create();
    	if (mAlertDialog != null && mAlertDialog.isShowing() == false) {
    		mAlertDialog.show();
    	}
	}
	

}
