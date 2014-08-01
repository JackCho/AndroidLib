package com.jit.lib.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class StringUtils {

	public static boolean isEmpty(String s) {
		if (null == s)
			return true;
		if (s.length() == 0)
			return true;
		if (s.trim().length() == 0)
			return true;
		if (s.trim().equals("null")) 
			return true;
		return false;
	}

	public static String emptyStringIfNull(String s) {
		return (null == s) ? "" : s;
	}

	public static String humanReadableByteCount(long bytes) {
		int unit = 1024;
		if (bytes < unit)
			return bytes + " B";
		int exp = (int) (Math.log(bytes) / Math.log(unit));
		String pre = "KMGTPE".charAt(exp - 1) + "";
		return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}

	public static String subString(String s, int end) {
		if (s.length() > end) {
			return s.substring(0, end);
		}

		return s;
	}

	public static boolean isInteger(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean hasChinese(String content) {
		for (int i = 0; i < content.length(); i++) {
			if (content.substring(i, i + 1).matches("[\\u4e00-\\u9fa5]+")) {
				return true;
			}
		}
		return false;
	}

	public static String toMD5(String source) {
		if (null == source || "".equals(source))
			return null;
		try {
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(source.getBytes());
			return toHex(digest.digest());
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}

	public static String toHex(byte[] buf) {
		if (buf == null)
			return "";
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private final static String HEX = "0123456789ABCDEF";

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}

	public static String urlEncode(String url) {
		return url.replace("\"", "%22").replace("{", "%7B").replace("}", "%7D")
				.replace(" ", "%20");
	}

	/**
	 * 使用Gson把map转为Json字符串时，去除多余的",\等
	 * 
	 * @param content
	 * @return
	 */
	public static String removeSpecialCharacter(String content) {
		return content.replace("\"{", "{").replace("}\"", "}")
				.replace("\\", "");
	}

}
