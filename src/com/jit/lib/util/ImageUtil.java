package com.jit.lib.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;

/**
 * 
 * 
 * FileName: ImageUtil.java
 * Description：图片操作助手类
 * Created by 曹玉斌 on 2014-5-6
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class ImageUtil {

    /**
     * convert Bitmap to byte array
     * 
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     * 
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     * 
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable)d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     * 
     * @param b
     * @return
     */
    @SuppressWarnings("deprecation")
	public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     * 
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     * 
     * @param b
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }

    /**
     * scale image
     * 
     * @param org
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float)newWidth / org.getWidth(), (float)newHeight / org.getHeight());
    }

    /**
     * scale image
     * 
     * @param org
     * @param scaleWidth sacle of width
     * @param scaleHeight scale of height
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }
    
    /**
     * 根据长宽的最大值和最小值，获取等比例的大小
     * @param bitmap
     * @param min
     * @param max
     * @return
     */
    public static int[] getRealBitmapSize(Bitmap bitmap, int min, int max) {
    	int[] size = new int[2];
    	int realWidth;
		int realHeight;
		if (bitmap == null) {
			return size;
		}
		int bmpWidth = bitmap.getWidth();
		int bmpHeight = bitmap.getHeight();
		if (bmpWidth > bmpHeight) {
			if (bmpHeight < min) {
				realHeight = min;
				realWidth = realHeight * bmpWidth / bmpHeight;
				if (realWidth > max) {
					realWidth = max;
					realHeight = realWidth * bmpHeight / bmpWidth;
				}
			} else if (bmpHeight < max) {
				realHeight = bmpHeight;
				realWidth = realHeight * bmpWidth / bmpHeight;
				if (realWidth > max) {
					realWidth = max;
					realHeight = realWidth * bmpHeight / bmpWidth;
				}
			} else {
				realWidth = max;
				realHeight = realWidth * bmpHeight / bmpWidth;
			}
		} else {
			if (bmpWidth < min) {
				realWidth = min;
				realHeight = realWidth * bmpHeight / bmpWidth;
				if (realHeight > max) {
					realHeight = max;
					realWidth = realHeight * bmpWidth / bmpHeight;
				} 
			} else if (bmpWidth < max) {
				realWidth = bmpWidth;
				realHeight = realWidth * bmpHeight / bmpWidth;
				if (realHeight > max) {
					realHeight = max;
					realWidth = realHeight * bmpWidth / bmpHeight;
				} 
			} else {
				realHeight = max;
				realWidth = realHeight * bmpWidth / bmpHeight;;
			}
		}
		
		size[0] = realWidth;
		size[1] = realHeight;
		return size;
     }
    
    public static int[] getImageSize(Bitmap bmp) {
    	int[] size = new int[2];
    	size[0] = bmp.getWidth();
    	size[1] = bmp.getHeight();
    	return size;
    }
    
    
    public static int[] getImageSizeWithPath(String path) {
    	int[] size = new int[2];
    	if (new File(path).exists()) {
    		Bitmap bmp = BitmapFactory.decodeFile(path);
    		size = getImageSize(bmp);
    		bmp.recycle();
		}
    	return size;
    }
    
	/**
	 * 读取图片属性：旋转的角度
	 * @param path 图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return degree;
		}
		return degree;
	}
	
	/**
	 * 旋转图片，使图片保持正确的方向。
	 * @param bitmap 原始图片
	 * @param degrees 原始图片的角度
	 * @return Bitmap 旋转后的图片
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int degrees) {
		if (degrees == 0 || null == bitmap) {
			return bitmap;
		}
		Matrix matrix = new Matrix();
		matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
		Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		if (null != bitmap) {
			bitmap.recycle();
		}
		return bmp;
	}
}
