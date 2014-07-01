package com.jit.lib.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jit.lib.R;
import com.jit.lib.config.ProjectConfiguration;
import com.jit.lib.config.UpgradeConfiguration;
import com.jit.lib.model.ResultEntity;
import com.jit.lib.model.UpgradeEntity;

/**
 * 自动更新类
 * 需要初始化 UpgradeConfiguration
 * @author Rocky
 *
 */
public class UpgradeUtil {
	/**
	 * 更新URL例子
	 * 待完善
	 * 要求:http://121.199.42.125:10001/Geteway.ashx?action=CheckAppVersion&request={"Parameters":{"Version":"1.0.9"},"UserID":"11","Token":null,"ClientID":"86a575e616044da3ac2c3ab492e44445","AppCode":"and_86a575e616044da3ac2c3ab492e44445"}
	 * 实际:http://121.199.42.125:10001/Geteway.ashx?action=CheckAppVersion&request={"Parameters":{"Version":"1.0.9"},"AppCode":"and_86a575e616044da3ac2c3ab492e44445"}
	 * 
	 * 机制:将version和app_code发送到服务器 根据返回的结果 判断是否更新
	 */
	
	private static final String UPGRADE_URL = "http://121.199.42.125:10001/Geteway.ashx";
	private static final String ACTION = "CheckAppVersion";
	
	/**
	 * app检查更新
	 */
	public static void checkUpdate(final Context context) {

		new AsyncTask<Void, Void, ResultEntity<UpgradeEntity>>() {
			@Override
			protected ResultEntity<UpgradeEntity> doInBackground(Void... params) {
				// 发送请求 更新检查 
				Map<String,String> reqP = new HashMap<String,String>();
				reqP.put("action", ACTION);
				Map<String,String> reqParams = new HashMap<String,String>();
				reqParams.put("AppCode", UpgradeConfiguration.getUpgradeAppCode());
				Map<String,String> reqsubParams = new HashMap<String,String>();
				reqsubParams.put("Version", UpgradeConfiguration.getUpgradeAppVersion());
				reqParams.put("Parameters",  new Gson().toJson(reqsubParams));
				reqP.put("request", new Gson().toJson(reqParams));
				String result = HttpUtil.getRequest(UPGRADE_URL, reqP);
				Gson gson = new Gson();
				ResultEntity<UpgradeEntity> resultEntity = gson.fromJson(result, new TypeToken<ResultEntity<UpgradeEntity>>() {}.getType());
				return resultEntity;
			}

			protected void onPostExecute(ResultEntity<UpgradeEntity> result) {
				if (result != null && 0 <= result.getResultCode()
						&& result.getResultCode() <= 99) {
					UpgradeEntity upgradeEntity = result.getData();
					/**
					 * 如果不需要升级 getVersion为空
					 */
					if (null != upgradeEntity
							&& upgradeEntity.getVersion() != null) {
						showUpgradeDialog(context, upgradeEntity);
					}
				}
			};
		}.execute();
	}

	/**
	 * 下载apk的进度条对话框
	 * 
	 * @param context
	 * @param upgradeEntity
	 */
	private static void showDownloadingDialog(final Context context,
			final UpgradeEntity upgradeEntity) {
		View layout = LayoutInflater.from(context).inflate(
				R.layout.download_apk_layout, null);
		final TextView tvDownloadSize = (TextView) layout
				.findViewById(R.id.download_size);
		final AlertDialog alertDialog = new AlertDialog.Builder(context)
				.create();
		alertDialog.setCancelable(false);
		alertDialog.setView(layout);
		alertDialog.show();
		new Thread() {
			public void run() {
				try {
					boolean isSuccess = downApk(context,
							upgradeEntity.getPackageUrl(), tvDownloadSize);
					alertDialog.dismiss();
					// 下载完成后 打开安装
					if (isSuccess) {
						((Activity) context).runOnUiThread(new Runnable() {
							@Override
							public void run() {
								openProcedureFile(context);
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			};
		}.start();
	}

	/**
	 * 下载apk
	 * 
	 * @param context
	 * @param upgradeUrl
	 * @param tvDownloadSize
	 * @return
	 * @throws Exception
	 */
	private static boolean downApk(Context context, String upgradeUrl,
			final TextView tvDownloadSize) throws Exception {
		if (upgradeUrl == null || upgradeUrl.length() == 0) {
			return false;
		}

		File file = null;
		try {
			file = getFileByPath(getFilePath()); // 保存的路径
			URL url = new URL(upgradeUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setConnectTimeout(5000);// 连接主机的超时时间（单位：毫秒）
			conn.setReadTimeout(5000); // 从主机读取数据的超时时间（单位：毫秒）

			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream is = null;
				FileOutputStream fos = null;
				BufferedInputStream bis = null;
				try {
					final int apkLength = conn.getContentLength();
					if (file.exists()) {
						// 本地已经存在于服务器相同的apk 直接返回下载成功
						if (file.length() == apkLength) {
							return true;
						} else {
							file.delete();
							file.createNewFile();
						}
					} else {
						File pfile = file.getParentFile();
						file.createNewFile();
					}
					is = conn.getInputStream();

					fos = new FileOutputStream(file);
					bis = new BufferedInputStream(is);
					byte[] buffer = new byte[65536];
					int len = 0;
					int total = 0;
					while ((len = bis.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
						total += len;
						final int downloadSize = total;
						((Activity) context).runOnUiThread(new Runnable() {
							@Override
							public void run() {
								double fen = (double) downloadSize * 1.0
										/ (double) apkLength * 1.0;
								int show = (int) (fen * 100);
								tvDownloadSize.setText("下载进度 " + show + "%");
							}
						});
					}
				} catch (Exception e) {
					return false;
				} finally {
					try {
						if (fos != null) {
							fos.close();
						}
						if (bis != null) {
							bis.close();
						}
						if (is != null) {
							is.close();
						}
					} catch (Exception e) {
						return false;
					}
				}

				return true;
			} else {
				if (file != null && file.exists()) {
					try {
						file.delete();
					} catch (Exception e1) {
						return false;
					}
				}
				return false;
			}
		} catch (Exception e) {
			if (file != null && file.exists()) {
				try {
					file.delete();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			return false;
		}
	}
	/**
	 * 生成文件路径
	 * @param filepath
	 * @return
	 */
	private static File getFileByPath(String filepath) {
		File file = null;
		file = new File(filepath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		return file;
	}

	/**
	 * 得到下载的app的path
	 * @return
	 */
	private static String getFilePath() {
		return ProjectConfiguration.getRootDir() + File.separator
				+ UpgradeConfiguration.getUpgradeAppVersion() + File.separator
				+ UpgradeConfiguration.getUpgradeAppName();
	}

	/**
	 * 显示是否更新对话框
	 * @param context
	 * @param upgradeEntity
	 */
	private static void showUpgradeDialog(final Context context,
			final UpgradeEntity upgradeEntity) {
		new AlertDialog.Builder(context).setTitle("提示")
				.setMessage("检测到新版本是否升级？")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						showDownloadingDialog(context, upgradeEntity);
					}
				})
				.setNegativeButton("否", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create().show();

	}

	/**
	 * 安装下载的程序
	 * @param context
	 */
	private static void openProcedureFile(Context context) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + getFilePath()),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}
