package com.jit.lib.util;

import android.content.Context;
import android.net.Uri;

import com.baidu.frontia.Frontia;
import com.baidu.frontia.api.FrontiaAuthorization.MediaType;
import com.baidu.frontia.api.FrontiaSocialShare;
import com.baidu.frontia.api.FrontiaSocialShareContent;
import com.baidu.frontia.api.FrontiaSocialShareListener;

/**
 * 
 * 
 * FileName: ShareHelper.java
 * Description：使用百度社会化分享分享到不同的平台
 * Created by 曹玉斌 on 2014-5-7
 * Copyright (c) 2014年 JIT. All rights reserved.
 *
 */
public class ShareHelper {
	
	/**
	 * 分享动作
	 * @param context
	 * @param clientName 分享平台提示（比如企信）
	 * @param content 分享的内容，可以通过initShareContent方法构建
	 * @param type    枚举类型，表示分享到不同的平台
	 * @param listener 分享的回调监听
	 */
	public static void share(Context context, String clientName, FrontiaSocialShareContent content,
							MediaType type, FrontiaSocialShareListener listener) {
		FrontiaSocialShare share = initSocialShare(context);
		share.setClientName(type.toString(), clientName);
		share.share(content, type.toString(), listener, true);
	}
	
	/**
	 * 初始化FrontiaSocialShare
	 * @param context
	 * @return
	 */
	private static FrontiaSocialShare initSocialShare(Context context) {
		FrontiaSocialShare share = Frontia.getSocialShare();
		share.setContext(context);
		share.setClientId(MediaType.SINAWEIBO.toString(), "1098403121");
		share.setClientId(MediaType.QZONE.toString(), "100358052");
		share.setClientId(MediaType.QQFRIEND.toString(), "100358052");
		share.setClientName(MediaType.QQFRIEND.toString(), "百度");
		share.setClientId(MediaType.WEIXIN.toString(), "wx329c742cb69b41b8");
		return share;
	}
	
	/**
	 * 构建FrontiaSocialShareContent
	 * @param title 标题
	 * @param content 内容
	 * @param linkUrl 链接
	 * @param imageUri 图片Uri，可以是本地Uri，也可以是图片链接
	 * @return
	 */
	public static FrontiaSocialShareContent initShareContent(String title, String content, String linkUrl,String imageUri) {
		FrontiaSocialShareContent shareContent = new FrontiaSocialShareContent();
		shareContent.setTitle(title);
		shareContent.setContent(content);
		shareContent.setLinkUrl(linkUrl);
		shareContent.setImageUri(Uri.parse(imageUri));
		return shareContent;
	}

}
