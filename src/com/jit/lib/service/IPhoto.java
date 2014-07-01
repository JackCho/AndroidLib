package com.jit.lib.service;

import android.graphics.Bitmap;
import android.net.Uri;

public interface IPhoto {

	public void onPicturePickFinish(Uri uri);
	
	public void onCaptureFinish(String filePath, Bitmap image);
}
