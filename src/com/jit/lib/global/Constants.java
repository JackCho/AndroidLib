package com.jit.lib.global;

import java.io.File;

import android.os.Environment;

import com.jit.lib.util.CommonUtil;


/**
 * 
 * @author Rocky
 *
 */
public class Constants {
	
	
	//social demo
	public final static String SINA_APP_KEY = "1098403121";
	
	public static int ACTIVITY_REQUEST_CAPTURE_BIG = 1;
	public static int ACTIVITY_REQUEST_CAPTURE_SMALL = 2;
	public static int ACTIVITY_REQUEST_PICTURE_PICK = 3;
	public static int ACTIVITY_REQUEST_RECORDER = 4;
	public static int ACTIVITY_REQUEST_SEND_SMS = 5;
	public static int ACTIVITY_REQUEST_SEND_EMAIL = 6;
	public static final int ACTIVITY_RESUME_SHOW_STATUS = 7;
	public static final int WHAT_REQUEST_TIMEOUT = 408;
	

	private static final String CaptureFileName = "photo.jpg";
	public static final String PictureCompressTmpName = "compress_tmp.jpg";
	
	public static int NET_CONNECT_TIMEOUT = 5;// 网络超时时间
	
	
	
	
	//TODO  ?
	public static File getCaptureFile() {
		File result = null;
		String root = null,root1 = null;
		if (CommonUtil.checkSDCardExist()) {
			root = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/weixun";
			File dir = new File(root);
			if (!dir.exists()) {
				dir.mkdir();
			}
			root1 = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/weixun/image";
			File dir1 = new File(root1);
			if (!dir1.exists()) {
				dir1.mkdir();
			}
			result = new File(dir1, CaptureFileName);
		} else {
			root = "/weixun/image";
			result = new File(root, CaptureFileName);
		}
		return result;
	}

}
