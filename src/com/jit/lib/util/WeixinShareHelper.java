package com.jit.lib.util;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXMediaMessage.IMediaObject;
import com.tencent.mm.sdk.openapi.WXTextObject;

/**
 * 
 * 
 * FileName: WeixinShareHelper.java
 * Description：微信分享工具类
 * Created by 曹玉斌 on 2014-5-6
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class WeixinShareHelper {

	public static String WX_APPID = "wx786a82e1935cb359";
	private static final int MAX_THUMB = 32 * 1024;
	
    public static void sendToWeixin(final Context context,String title,String desc,int flag){
    	IWXAPI wxapi = WXAPIFactory.createWXAPI(context, WX_APPID, true);
		wxapi.registerApp(WX_APPID);
		WXTextObject textObj = new WXTextObject();
		textObj.text = title;
		send(wxapi, title, desc, textObj, flag);
    }
    
  private static void send(IWXAPI wxapi, String title,String desc,IMediaObject imObject,int flag){
    	String t = "";
    	String d = "";
    	if (title.length()>60)
    		t = title.substring(0, 58)+"...";
    	else
    		t = title;
    	if (desc.length()>32)
    		d = desc.substring(0, 30)+"...";
    	else
    		d = desc;
    	WXMediaMessage msg = new WXMediaMessage();
		msg.title = t;
		msg.description = d;
		msg.mediaObject = imObject;
		
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("appdata");;
		req.message = msg;
		if (flag == 0) {
			req.scene = SendMessageToWX.Req.WXSceneSession;
		}else {
			req.scene = SendMessageToWX.Req.WXSceneTimeline;
		}
		wxapi.sendReq(req);
    }
    
	public static final void shareToWxWithPic(final Context context, String title, String desc,
											String content, String  imgurl,int flag) {
		IWXAPI wxapi = WXAPIFactory.createWXAPI(context, WX_APPID, true);
		wxapi.registerApp(WX_APPID);

	    WXImageObject imgObj = new WXImageObject();
	    imgObj.setImagePath(imgurl);
	     
	    WXMediaMessage msg = new WXMediaMessage();
	    msg.mediaObject = imgObj;
	    msg.description = desc;
	    File file = new File(imgurl);
	    if (file.exists()) {
	    	 BitmapFactory.Options options = new BitmapFactory.Options();
		   	long size = file.length();
		   	if (size > MAX_THUMB) {
		   	 long divier = size / MAX_THUMB;
		   	 options.inSampleSize = (int) divier;
			}
		    Bitmap bmp = BitmapFactory.decodeFile(imgurl, options);
		    ByteArrayOutputStream baos = new ByteArrayOutputStream();    
		    bmp.compress(Bitmap.CompressFormat.PNG, 90, baos);    
		    byte[] bs = baos.toByteArray();
		    msg.thumbData = bs;
		    if (!bmp.isRecycled()) {
		    	bmp.recycle();
			}
	   }
	   
	    msg.title = title;
	    SendMessageToWX.Req req = new SendMessageToWX.Req();
	    req.transaction = buildTransaction("appdata");;
	    req.message = msg;
	    
		if (flag == 0) {
			req.scene = SendMessageToWX.Req.WXSceneSession;
		}else {
			req.scene = SendMessageToWX.Req.WXSceneTimeline;
		}
		wxapi.sendReq(req);
	}

	private final static String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

}
