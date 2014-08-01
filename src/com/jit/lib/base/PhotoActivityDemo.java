package com.jit.lib.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.jit.lib.service.IPhoto;
import com.jit.lib.util.PhotoUtil;

public class PhotoActivityDemo extends Activity implements IPhoto{

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//基于返回值去处理其他的requestCode分支
		PhotoUtil.onActivityResult(this, requestCode, resultCode, data);
	}

	@Override
	public void onPicturePickFinish(Uri uri) {
		
	}

	@Override
	public void onCaptureFinish(String filePath, Bitmap image) {
		
	}
}
