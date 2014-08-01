package com.jit.lib.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.text.TextUtils;

public class DateUtil {

	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 时间字符串转Date
	 */
	public static Date str2Date(String str) {
		return str2Date(str, null);
	}

	public static Date str2Date(String str, String format) {
		if (str == null || str.length() == 0) {
			return null;
		}
		if (format == null || format.length() == 0) {
			format = DEFAULT_FORMAT;
		}
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(str);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 时间字符串转Calendar
	 */
	public static Calendar str2Calendar(String str) {
		return str2Calendar(str, null);
	}

	public static Calendar str2Calendar(String str, String format) {
		Date date = str2Date(str, format);
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c;
	}

	/**
	 * Calendar转时间字符串
	 */
	public static String date2Str(Calendar c) {
		return date2Str(c, null);
	}

	public static String date2Str(Calendar c, String format) {
		if (c == null) {
			return null;
		}
		return date2Str(c.getTime(), format);
	}

	public static String date2Str(Date d) {
		return date2Str(d, null);
	}

	public static String date2Str(Date d, String format) {
		if (d == null) {
			return null;
		}
		if (format == null || format.length() == 0) {
			format = DEFAULT_FORMAT;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String s = sdf.format(d);
		return s;
	}

	/**
	 * 获取当前时间字符串
	 */
	public static String getCurDateStr() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-"
				+ c.get(Calendar.DAY_OF_MONTH) + "-"
				+ c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE)
				+ ":" + c.get(Calendar.SECOND);
	}

	public static String getCurDateStr(String format) {
		Calendar c = Calendar.getInstance();
		return date2Str(c, format);
	}

	/**
	 * 基于毫秒获取时间字符串
	 */
	public static String getMillon(long time) {
		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(time);
	}

	public static String getDay(long time) {
		return new SimpleDateFormat("yyyy-MM-dd").format(time);
	}

	public static String getSMillon(long time) {
		return new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(time);
	}

	private final static long DAY = 60 * 60 * 24 * 1000;
	private final static long TWO_DAY = 2 * DAY;
	private final static long THREE_DAY = 3 * DAY;

	/**
	 * 计算过去时间距离现在相差多少时间
	 * 
	 * @param past
	 * @return
	 */
	public static String twoDateDistance(String past) {
		if (TextUtils.isEmpty(past)) {
			return null;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT);
		Date startDate = null;
		try {
			startDate = sdf.parse(past);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date endDate = new Date();
		long timeLong = endDate.getTime() - startDate.getTime();
		Date todayZeroDate = new Date();
		todayZeroDate.setHours(0);
		todayZeroDate.setMinutes(0);

		long todayPastTime = endDate.getTime() - startDate.getTime();
		if (timeLong < todayPastTime) {
			return past.substring(11, 16);
		} else if (timeLong < TWO_DAY) {
			return "昨天";
		} else if (timeLong < THREE_DAY) {
			return "前天";
		} else {
			return past.substring(5, 10);
		}
	}

	public static String getDistanceTime(long time2) {
		Date now = new Date();
		String rs = "";
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
		String d = format.format(time2);
		Date date = null;
		try {
			date = format.parse(d);// 把字符类型的转换成日期类型的！
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if (now.getDate() - date.getDate() == 0) {// 当前时间和时间戳转换来的时间的天数对比
			int hour = date.getHours();
			
			DateFormat df2 = new SimpleDateFormat("HH:mm");
			if (hour > 12) {
				rs = "下午  " + df2.format(time2);
			} else {
				rs = "上午  " + df2.format(time2);
			}
			return rs;
		} else if (now.getDate() - date.getDate() == 1) {
			DateFormat df2 = new SimpleDateFormat("HH:mm");
			rs = "昨天  " + df2.format(time2);
			return rs;
		} else {
			DateFormat df2 = new SimpleDateFormat("MM-dd HH:mm");
			rs = df2.format(time2);
			return rs;
		}
	}
}
