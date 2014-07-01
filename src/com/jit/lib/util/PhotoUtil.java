package com.jit.lib.util;

import java.io.File;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import com.jit.lib.global.Constants;
import com.jit.lib.service.IPhoto;

/**
 * 
 * 
 * FileName: PhotoUtil.java
 * Description：拍照或选择图片的工具类
 * Created by 曹玉斌 on 2014-5-8
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class PhotoUtil {

	public static void startCamera4Photo(Activity activity) {
		if (CommonUtil.checkSDCardExist()) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File picFile = Constants.getCaptureFile();
			Uri photoUri = Uri.fromFile(picFile);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
			activity.startActivityForResult(intent, Constants.ACTIVITY_REQUEST_CAPTURE_BIG);
		} else {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			activity.startActivityForResult(intent, Constants.ACTIVITY_REQUEST_CAPTURE_SMALL);
		}
	}
	
	public static void startPhotoGallary(Activity activity) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		activity.startActivityForResult(intent, Constants.ACTIVITY_REQUEST_PICTURE_PICK);
	}
	
	/**
	 * 处理图片回调
	 * @param iPhoto 
	 * @param requestCode
	 * @param resultCode
	 * @param data
	 * @return true表示iPhoto处理图片
	 */
	public static boolean onActivityResult(IPhoto iPhoto, int requestCode, int resultCode, Intent data) {
		Uri uri = null;
		if (data != null) {
			uri = data.getData();
		}
		if (requestCode == Constants.ACTIVITY_REQUEST_CAPTURE_BIG) {
			if (resultCode == Activity.RESULT_OK) {
				File picFile = Constants.getCaptureFile();
				iPhoto.onCaptureFinish(picFile.getAbsolutePath(), null);
				return true;
			}
		} else if (requestCode == Constants.ACTIVITY_REQUEST_CAPTURE_SMALL) {
			if (resultCode == Activity.RESULT_OK) {
				Bitmap image = (Bitmap) data.getExtras().get("data");
				iPhoto.onCaptureFinish(null, image);
				return true;
			}
		} else if (requestCode == Constants.ACTIVITY_REQUEST_PICTURE_PICK) {
			if (uri != null)
				iPhoto.onPicturePickFinish(uri);
			return true;
		}
		return false; 
	}

}
