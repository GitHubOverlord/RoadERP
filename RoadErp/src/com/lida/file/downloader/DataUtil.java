/*
 * 文 件 名:DataUtil.java
 * 创 建 人： 赵宏
 * 日    期： 2014-09-23 
 * 版 本 号： 1.00
 */

package com.lida.file.downloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


//2014-9-23
/**
 * 工具类.
 * @author 赵宏
 * @version 1.01
 * @since 1.01
 */
public class DataUtil {
    //	public static final String IMG_SERVER_URL = "http://10.0.6.22:8080/ws/upload/uploadFile";
	/**
	 * 将流转换成字符串.
	 * @param stream 待转换的流
	 * @return 转换后的流
	 * @throws IOException
	 */
	public static String getNetData(InputStream stream) throws IOException {
		String bString = new String(getNetDataBytes(stream), "utf-8");
		// if (bString.contains("partyTag"))
		// {
		// Log.i("tag","DataUtil.getNetData-网络返回原始数据2->" +
		// bString.substring(bString.indexOf("partyTag")));
		// }
		// if
		// (bString.contains("/upload/activity/2/6/9/7/2697666bd6e4bfbb186b6efc567d4b42_150x120.jpg"))
		// {
		// Log.i("tag","DataUtil.getNetData-网络返回原始数据2->" +
		// bString.substring(bString.indexOf("/upload/activity/2/6/9/7/2697666bd6e4bfbb186b6efc567d4b42_150x120.jpg")));
		// }
		// if (bString.contains("\"partyID\":\"2\""))
		// {
		// Log.i("tag","DataUtil.getNetData-网络返回原始数据2->" +
		// bString.substring(bString.indexOf("\"partyID\":\"2\"")));
		// }
		return bString;
	}
	
	/*
	 * 为ImageLoader 配置config参数
	 * @param context 内容
	 * @return  ImageLoaderConfiguration类型
	 */
//	public static ImageLoaderConfiguration getImageLoaderConfig (Context context)
//	{
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//		.memoryCacheExtraOptions(480, 800)
//		.threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY - 1)
//		.denyCacheImageMultipleSizesInMemory()
//		.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
//		.discCacheSize(50 * 1024 * 1024)
//		.denyCacheImageMultipleSizesInMemory()
//		.discCacheFileNameGenerator(new Md5FileNameGenerator())
//		.tasksProcessingOrder(QueueProcessingType.LIFO)
//		.writeDebugLogs() // Remove for release app
//		.build();
//		return config;
//	}
	


	private static long lastClickTime;

	//王登辉
	/**
	 *  防止多次点击按钮 上次点击后 两秒内点击无效.
	 * @return 是否快速双击。如果两次点击相差2秒，则返回true，否则返回false
	 */
	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		
		if (0 < timeD && timeD < 2000) {
			return true;
		}
		
		lastClickTime = time;
		
		return false;
	}

	/**
	 * 获取系统当前时间.
	 * @return 当前系统时间字符串
	 */
	@SuppressLint("SimpleDateFormat")
	private String getCurrentData() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd ");
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String data = dateFormat.format(curDate);
		
		return data;
	}

	/**
	 * 将分钟转化为字符串.
	 * @param intTime 分钟
	 * @return 转换后的字符串
	 */
	public static String minToString(int intTime) {
		String strTime = "";
		if (60 > intTime) {
			strTime = "0小时" + intTime + "分钟";
		} else {
			int mins = intTime % 60;
			int totalHour = intTime / 60;
			if (24 <= totalHour) {
				int day = totalHour / 24;
				int hour = totalHour % 24;
				strTime = day + "天" + hour + "小时" + mins + "分钟";
			} else {
				strTime = totalHour + "小时" + mins + "分钟";
			}
		}
		
		return strTime;
	}

	/**
	 * 如果一个数字小于10，那么在前面添加一个0
	 * @param i 数字
	 * @return 前面补零的字符串
	 */
	public static String add0(int i) {
		DecimalFormat decimalFormat = new DecimalFormat("00");
		
		return decimalFormat.format(i);
	}
	
	/**
     * 校验银行卡卡号.
     * @param cardId 银行卡卡号字符串
     * @return 正确返回true，否则返回false 
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        
        return cardId.charAt(cardId.length() - 1) == bit;
    }
    
	 /**
     * 得到银行卡校验码.<P>
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位。
     * @param nonCheckCodeCardId 无校验码的银行卡号
     * @return 校验码
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            throw new IllegalArgumentException("Bank card code must be number!");
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }

	/**
	 * 将流转换成byte[].
	 * @param stream 待转换的流
	 * @return 转换后的byte[]
	 */
	public static byte[] getNetDataBytes(InputStream stream) throws IOException {
		BufferedInputStream bufferedStream = new BufferedInputStream(stream,
				8000);
		byte[] bytes = new byte[bufferedStream.available()];
		bufferedStream.read(bytes);
		return bytes;
	}

	/**
	 * 去除List中重复的数据
	 * @param arlList 去重前的列表
	 */
	public static void removeDuplicate(ArrayList arlList) {
		HashSet h = new HashSet(arlList);
		arlList.clear();
		arlList.addAll(h);
	}

	/**
	 * 检查字符串是否为空.
	 * @param string 字符串
	 * @return 如果字符串为空，则返回“”，否则返回原来字符串
	 */
	public static String NullOrString(String string) {
		return string == null ? "" : string;
	}

	/** 日期时间格式模板0 */
	public static final String DATETYPE_0 = "yyyy-MM-dd";
	/** 日期时间格式模板1 */
	public static final String DATETYPE_1 = "yyyy-MM-dd HH:mm";
	/** 日期时间格式模板2 */
	public static final String DATETYPE_2 = "MM-dd";
	/** 日期时间格式模板3 */
	public static final String DATETYPE_3 = "MM.dd";
	/** 日期时间格式模板4 */
	public static final String DATETYPE_4 = "HH:mm";
	/** 日期时间格式模板5 */
	public static final String DATETYPE_5 = "yyyy-MM.dd";
	/** 日期时间格式模板6 */
	public static final String DATETYPE_6 = "yyyy.MM.dd";
	/** 日期时间格式模板7 */
	public static final String DATETYPE_7 = "MM-dd HH:mm";
	// 日期时间格式模板8
	public static final String DATETYPE_8 = "dd/MM";

	/**
	 * 将字符串转化为时间.
	 * @param string 时间字符串
	 * @return Date 时间
	 */
	public static Date parseDate(String string) {
		try {
			// string.indexOf(SYApplication.NOSET) == -1
			if (string != null && !string.equals("")) {
				return new SimpleDateFormat(DATETYPE_1).parse(string);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 将毫秒数转换为时间格式的字符串.
	 * @param longTime 毫秒数
	 * @return 格式化后的时间字符串。
	 */
	public static String getStringDate6(long longTime) {
		return new SimpleDateFormat(DATETYPE_6).format(longTime);
	}

	/**
	 * 将毫秒数转换为时间格式的字符串.
	 * @param longTime 毫秒数
	 * @return 格式化后的时间字符串。
	 */
	public static String getStringDate7(long longTime) {
		return new SimpleDateFormat(DATETYPE_7).format(longTime);
	}

	/**
	 * 将毫秒数转化为字符串.
	 * @param longTime 时间毫秒数
	 * @return String 格式化后的文本
	 */
	public static String getStringDate(long longTime) {
		return new SimpleDateFormat(DATETYPE_1).format(longTime);
	}

	/**
	 * 将毫秒数转化为字符串.
	 * @param longTime 时间毫秒数
	 * @return String 格式化后的文本
	 */
	public static String getStringDate0(long longTime) {
		return new SimpleDateFormat(DATETYPE_0).format(longTime);
	}

	/**
	 * 将毫秒数转换为时间格式的字符串.
	 * @param longTime 毫秒数
	 * @return 格式化后的时间字符串。
	 */
	public static String getStringTime(String longTime) {
		Calendar calendar = Calendar.getInstance();
		long time = Long.parseLong(longTime);
		calendar.setTimeInMillis(time);
		if (calendar.get(Calendar.HOUR_OF_DAY) < 12) {
			return new SimpleDateFormat(DATETYPE_3).format(time) + " 上午 "
					+ new SimpleDateFormat(DATETYPE_4).format(time);
		} else {
			return new SimpleDateFormat(DATETYPE_3).format(time) + " 下午 "
					+ new SimpleDateFormat(DATETYPE_4).format(time);
		}
	}

	/**
	 * 将毫秒数转换为时间格式的字符串.
	 * @param longTime 毫秒数
	 * @return 格式化后的时间字符串。
	 */
	public static String getStringTime2(String longTime) {
		Calendar calendar = Calendar.getInstance();
		long time = Long.parseLong(longTime);
		calendar.setTimeInMillis(time);
		if (calendar.get(Calendar.HOUR_OF_DAY) < 12) {
			return "上午 " + new SimpleDateFormat(DATETYPE_4).format(time);
		} else {
			return "下午 " + new SimpleDateFormat(DATETYPE_4).format(time);
		}
	}

	/**
	 * 将毫秒数转换为时间格式的字符串.
	 * @param longTime 毫秒数
	 * @return 格式化后的时间字符串。
	 */
	public static String getDayTimeStr(long longTime) {
		return new SimpleDateFormat(DATETYPE_4).format(longTime);
	}

	/**
	 * 将秒数转换为时间格式的字符串.
	 * @param time 时间
	 * @return 格式化后的时间字符串
	 */
	public static String getDayTimeStr2(int time) {
		SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
		String hms = formatter.format(time);
		
		return hms;
	}

	/**
	 * 将毫秒数转化为字符串.<P>
	 * 如果是去年就去年，如果是同一年 的就显示月日
	 * @param longTime 时间毫秒数
	 * @return 格式化后的文本
	 */
	public static String getStringDate2(long longTime) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		calendar.setTimeInMillis(longTime);
		if (calendar.get(Calendar.YEAR) > year) {
			return "去年";
		} else {
			return new SimpleDateFormat(DATETYPE_2).format(longTime);
		}

		// return new SimpleDateFormat(DATETYPE_1).format(longTime * 1000);
	}

	/**
	 * 将毫秒数转换为字符串.
	 * @param longTime 毫秒数
	 * @return 转换后的字符串。
	 */
	public static String getStringDate8(long longTime) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		calendar.setTimeInMillis(longTime);
		if (calendar.get(Calendar.YEAR) > year) {
			return "去年";
		} else {
			return new SimpleDateFormat(DATETYPE_8).format(longTime);
		}
	}

	/**
	 * 将毫秒数转换为字符串.
	 * @param longTime 毫秒数
	 * @return 转换后的字符串。
	 */
	public static String getStringDate9(long longTime) {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		calendar.setTimeInMillis(longTime);
		if (calendar.get(Calendar.YEAR) > year) {
			return "去年";
		} else {
			return new SimpleDateFormat(DATETYPE_4).format(longTime);
		}
	}

	/**
	 * 将字符串转化为毫秒数.
	 * @param StringTime 时间字符串 
	 * @return 时间毫秒数
	 * @throws ParseException
	 */
	public static Long getLongDate(String StringTime) throws ParseException {
		Date date = parseDate(StringTime);
		if (date != null) {
			return date.getTime();
		}
		return 0L;
	}

	/**
	 * 将String进行MD5编码.
	 * @param value 待编码的字符串
	 * @return 编码后的字符串。
	 */
	public static String encodeMD5(String value) {
		StringBuffer enStr = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(value.getBytes());
			byte[] b = algorithm.digest();
			for (int i = 0; i < b.length; i++) {
				String tmp = Integer.toHexString(b[i] & 0xFF);
				if (tmp.length() == 1) {
					enStr.append("0").append(tmp);
				} else {
					enStr.append(tmp);
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return enStr.toString();
	}

	/**
	 * 将byte[]进行MD5编码.
	 * @param fileField 待编码的字节型数据
	 * @return 编码后的字符串。
	 */
	public static String encodeMD5(byte[] fileField) {
		StringBuffer enStr = new StringBuffer();
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(fileField);
			byte[] b = algorithm.digest();
			for (int i = 0; i < b.length; i++) {
				String tmp = Integer.toHexString(b[i] & 0xFF);
				if (tmp.length() == 1) {
					enStr.append("0").append(tmp);
				} else {
					enStr.append(tmp);
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return enStr.toString();
	}

	/**
	 * 将HttpEntity转化成byte[].
	 * @param entity 网络返回数据对象
	 * @return 转换后的字节型数据。
	 * @throws IOException
	 */
	public static byte[] getNetDataBytes(HttpEntity entity) throws IOException {
		byte[] byets = null;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		entity.writeTo(byteArrayOutputStream);
		byets = byteArrayOutputStream.toByteArray();
		Log.i("tag", "DataHandler.getNetDataBytes-length->" + byets.length);
		return byets;
	}

	/**
	 * 从HttpEntity中返回UTF-8编码形式的字符串.
	 * @param entity 网络返回数据对象
	 * @return 编码后的字符串
	 * @throws IOException
	 */
	public static String getNetData(HttpEntity entity) throws IOException {
		String bString = new String(getNetDataBytes(entity), "utf-8");
		bString = bString.trim();
		Log.i("tag", "DataUtil.getNetData-网络返回的原始数据->" + bString);
		
		return bString;
	}

	/**
	 * 将字符串转化成JSON对象.
	 * @param string JSON字符串
	 * @return JSON对象
	 */
	public static JSONObject getJsonObject(String string) {
		try {
			return new JSONObject(string);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 得到Base64编码字符串.<P>
	 * 将InputStream进行Base64编码,并按照默认方式转换成String
	 * @param stream 网络返回数据对象
	 * @return 编码后的字符串。
	 */
	public static String getBase64Str(InputStream stream) {
		try {
			int count = stream.available();
			byte[] buffer = new byte[count];
			new BufferedInputStream(stream).read(buffer);
			return Base64.encodeToString(buffer, Base64.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * HttpEntity转JSONObject.
	 * @param entity 网络返回数据对象
	 * @return JSONObject对象。
	 * @throws JSONException
	 * @throws IOException
	 */
	public static JSONObject getJsonObject(HttpEntity entity)
			throws JSONException, IOException {
		JSONObject object = new JSONObject(getNetData(entity));
		Log.i("tag", "DataUtil.getJsonObject-网络返回的JSON对象->" + object);
		return object;
	}

	/*
	 * 通过经纬度计算两点的距离
	 * 
	 * @param ox 
	 * @param oy
	 * @param dx
	 * @param dy
	 * @return
	 */
	/*
	 * public static String getDistatce(GPSPoint gpsPoint) { if (gpsPoint ==
	 * null) { return SYApplication.NOSET; } double GPS_W = gpsPoint.getW_gps();
	 * double GPS_J = gpsPoint.getJ_gps(); GPSPoint point =
	 * SYApplication.app.mInfo.memberInfo.getGpsPoint(); if (point == null) {
	 * return "无距离"; } if (GPS_W == 0 || GPS_J == 0) { return "无距离"; }
	 * 
	 * double R = 6371.229; double distance = 0.0; double dLat = (GPS_W -
	 * point.w_gps) * Math.PI / 180; double dLon = (GPS_J - point.j_gps) *
	 * Math.PI / 180; double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
	 * Math.cos(GPS_W * Math.PI / 180) * Math.cos(point.w_gps * Math.PI / 180) *
	 * Math.sin(dLon / 2) Math.sin(dLon / 2); distance = (2 *
	 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))) * R; distance *= 1000;
	 * 
	 * String dis = null; if (distance >= 100) { distance = distance / 100; dis
	 * = String.valueOf(distance); return dis.substring(0, dis.indexOf(".") + 2)
	 * + "km"; } else { return "100m内"; } }
	 */

	/*	
	 * 根据出生日期得到年龄 yt
	 * 
	 * @param birthday 1998-9-9
	 * @return
	 */
	/*
	 * public static String getAge(String birthday) { try { // 毫秒数 Calendar cal
	 * = Calendar.getInstance(Locale.CHINESE); cal.setTime(new
	 * Date(SYApplication.app.mktime)); String string =
	 * (String.valueOf(cal.get(Calendar.YEAR) -
	 * Integer.parseInt(birthday.substring(0, 4)))) + "岁"; return string; }
	 * catch (NumberFormatException e) { return "未设置"; }
	 * 
	 * }
	 */

	/**
	 * Bitmap转 byte[].
	 * @param bm 需要转换的Bitmap
	 * @return 转化后的字节型数据。
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 进度条对话框
	 */
	public static ProgressDialog pd; 


	/**
	 * 清除对话框.
	 */
	public static void clearDialog() {
		if (pd != null)
			pd.dismiss();
	}

	/**
	 * 将字节型数据转换为BMP图片.
	 * @param b 字节型数据
	 * @return BMP图片
	 */
	public static Bitmap Bytes2Bimap(byte[] b) {
		return (b.length != 0) ? BitmapFactory.decodeByteArray(b, 0, b.length) : null;
	}

	/**
	 * 用来判断活动是否开始或者结束.
	 * @param startTimeStr 活动开始时间
	 * @param endTimeStr 活动结束时间
	 * @return 状态字符串
	 */
	public static String partyBeginOrFinish(String startTimeStr, String endTimeStr) {
		String beginOrFinishStr = null;
		long nowTime;
		long startTime = 0;
		long endTime = 0;
		try {
			if (startTimeStr != null)
				startTime = DataUtil.getLongDate(startTimeStr);
			if (endTimeStr != null)
				endTime = DataUtil.getLongDate(endTimeStr);
			nowTime = System.currentTimeMillis();
			if (nowTime < startTime) {
				return "即将开始";
			}
			if (nowTime < endTime) {
				return "进行中";
			}
			if (nowTime > endTime) {
				return "已结束";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return beginOrFinishStr;
	}

	/**
	 * 判断第一个时间和第二个时间相差多少（天/小时/分钟）.
	 * @param time1 第一个时间
	 * @param time2 第二个时间
	 * @return 时间相差的字符串
	 */
	public static String fromTime(long time1, long time2) {
		long time = time2 - time1;
		StringBuffer timeStr = new StringBuffer();
		long day = time / (24 * 60 * 60 * 1000);
		long hour = (time - 24 * 60 * 60 * 1000 * day) / (60 * 60 * 1000);
		long minute = (time - 24 * 60 * 60 * 1000 * day - 60 * 60 * 1000 * hour)
				/ (60 * 1000);
		
		if (day > 0) {
			timeStr.append(day + "天");
		}
		
		if (hour > 0) {
			timeStr.append(hour + "时");
		}
		
		if (minute > 0) {
			timeStr.append(minute + "分");
		}
		
		return timeStr.toString();
	}

	/**
	 * 获取屏幕高度宽度.
	 * @param context 内容
	 */
	public static void getWithHeigh(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		// 取屏幕宽高
		// DisplayMetrics metrics =
		// SYApplication.app.getResources().getDisplayMetrics();
		int[] phoneWidthHeigh = new int[] { dm.widthPixels, dm.heightPixels };
		int phoneWidth = dm.widthPixels;
		int phoneHeigh = dm.heightPixels;
	}

	/** 
	 * 打招呼Dialog 
	 */
	public static Dialog dialog_create;

	/**
	 * 创建活动提示选择.
	 * @param activity Activity类型
	 */
	public static void showCreateDialog(final Activity activity) {
		
	}

	private static int nowYear = -1;

	/**
	 * 去掉时间中不需要的部分.<P>
	 * 该方法用来去掉时间中不需要的部分，如将 2012-01-01 15:20:00 修改为 1月1日 15:20 如果是去年就显示xxxx年xx月。
	 * 如果type=0 ，则返回具体时间，如果type=1，则返回日期。
	 * @param dateTimeStr 时间字符串
	 * @param type 类型
	 * @return 处理后的时间字符串
	 */
	public static String dateTimeChange(String dateTimeStr, int type) {
		if (nowYear == -1) {
			nowYear = Calendar.getInstance().get(Calendar.YEAR);
		}
		if (type == 0) {
			String[] dataTime = dateTimeStr.split(" ");
			String[] datas = dataTime[0].split("-");
			String[] times = dataTime[1].split(":");
			if (nowYear == Integer.parseInt(datas[0])) {
				return datas[1].replaceFirst("^(0+)", "") + "月"
						+ datas[2].replaceFirst("^(0+)", "") + "日" + " "
						+ times[0] + ":" + times[1];
			} else {
				return datas[0] + "年" + datas[1].replaceFirst("^(0+)", "") + "月";
			}
		} else {
			String[] datas = dateTimeStr.split("-");
			if (nowYear == Integer.parseInt(datas[0])) {
				return datas[1].replaceFirst("^(0+)", "") + "月"
						+ datas[2].replaceFirst("^(0+)", "") + "日";
			} else {
				return datas[0] + "年" + datas[1].replaceFirst("^(0+)", "") + "月";
			}
		}
	}

	public static Dialog dialog_big;

	/**
	 * 从流中读取数据（帮助用）.
	 * @param inStream 输入的流
	 * @return 字节型数据组
	 * @throws Exception
	 */
	public static byte[] read(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		
		return outStream.toByteArray();
	}

	/**
	 * 得到圆角的图片.
	 * @param bitmap 待处理的图片
	 * @param roundPx 圆角参数，数字越大则圆角越大
	 * @return 圆角的图片。
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);

		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, 10, 10, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 检查是否输入.<P>
	 * 目前只支持EditText和Button
	 * @param obj 检查对象
	 * @return 如果输入为空，返回true，否则返回false。
	 */
	public static boolean checkIfNull(Object obj) {
		if (obj.getClass() == EditText.class) {
			return (((EditText) obj).getText().toString().trim().length() == 0) ? true : false;
		} else if (obj.getClass() == Button.class) {
			return (((Button) obj).getText().toString().trim().length() == 0) ? true : false;
		} else {
			return false;
		}
	}

	/**
	 * 网络是否可用.
	 * @param context 内容
	 * @return 如果网络可用，返回true，否则返回false。
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = cm.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	/**
	 * 是否联网网络.
	 * @param context 内容
	 * @return 如果连接上网络，返回true，否则返回false。
	 */
	public static boolean IsHaveInternet(final Context context) {
		try {
			ConnectivityManager manger = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = manger.getActiveNetworkInfo();
			if (info != null) {
				return true;
			} else {
				//getToast("当前没有任何网络可连接， 请检查网络后重试！");
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 修订图片的大小.
	 * @param inputStream 输入流
	 * @param sizew 待设定的宽度
	 * @param sizeh 待设定的高度
	 * @return 修订后的图片
	 * @throws IOException
	 */
	public static Bitmap revitionImageSize(InputStream inputStream, int sizew, int sizeh) throws IOException {

		// 取得图片
		InputStream temp = inputStream;
		BitmapFactory.Options options = new BitmapFactory.Options();
		// 这个参数代表，不为bitmap分配内存空间，只记录一些该图片的信息（例如图片大小），说白了就是为了内存优化
		options.inJustDecodeBounds = true;
		// 通过创建图片的方式，取得options的内容（这里就是利用了java的地址传递来赋值）
		BitmapFactory.decodeStream(temp, null, options);
		// 关闭流
		temp.close();

		// 生成压缩的图片
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			// 这一步是根据要设置的大小，使宽和高都能满足
			if ((options.outWidth >> i <= sizew)
					&& (options.outHeight >> i <= sizeh)) {
				// 重新取得流，注意：这里一定要再次加载，不能二次使用之前的流！
				temp = inputStream;
				// 这个参数表示 新生成的图片为原始图片的几分之一。
				options.inSampleSize = (int) Math.pow(2.0D, i);
				// 这里之前设置为了true，所以要改为false，否则就创建不出图片
				options.inJustDecodeBounds = false;

				bitmap = BitmapFactory.decodeStream(temp, null, options);
				break;
			}
			i += 1;
		}
		
		return bitmap;
	}

	//by yetao
	/**
	 * 改变大小的BMP图片.<P>
	 * 注意一下，返回的那个bitmap会很大，你用完以后要把它回收掉，不然你很容易内存报oom错误.
	 * @param bitmap 待改变大小的BMP图片
	 * @param newWidth 新图片的宽度
	 * @return 改变后的BMP图片。
	 */
	public static Bitmap ResizeBitmap(Bitmap bitmap, int newWidth) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float temp = ((float) height) / ((float) width);
		int newHeight = (int) ((newWidth) * temp);
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		// resize the bit map
		matrix.postScale(scaleWidth, scaleHeight);
		// matrix.postRotate(45);
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
		bitmap.recycle();
		
		return resizedBitmap;
	}

	/**
	 * 按图片大小(字节大小)缩放图片.
	 * @param path 图片文件路径
	 * @return 转换后的图片。
	 */
	public static Bitmap fitSizeImg(String path) {
		if (path == null || path.length() < 1)
			return null;
		File file = new File(path);
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new BitmapFactory.Options();
		
		// 数字越大读出的图片占用的heap越小 不然总是溢出
		if (file.length() < 20480) { // 0-20k
			opts.inSampleSize = 1;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 2;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize = 4;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 6;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 10;
		}
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
		
		return resizeBmp;
	}

	/**
	 * 设置HTML字符串.
	 * @param color 颜色代码 如#ffffff
	 * @param content 内容
	 * @return 变成HTML格式的字符串。
	 */
	public static StringBuffer setHtmlStr(String color, String content) {
		StringBuffer htmlBuffer = new StringBuffer();
		htmlBuffer.append("<font color=")
		          .append(color)
		          .append(">")
		          .append(content)
		          .append("</font>");
		
		return htmlBuffer;
	}

	/**
	 * 手机验证.
	 * @param phone 电话号码
	 * @return 如果电话号码符合规则，则返回true；否则，返回false
	 */
	public static boolean checkPhone(String phone) {
		if (phone.length() != 11) {
			return false;
		}
		Pattern pattern = Pattern.compile("^1[34578]\\d{9}$");
		Matcher matcher = pattern.matcher(phone);
		
		return matcher.matches();
	}

	/**
	 * 验证QQ号码.
	 * @param QQ 待验证的QQ号码字符串
	 * @return 如果符合QQ号码规则，返回true，否则返回false。
	 */
	public static boolean checkQQ(String QQ) {
		String regex = "^[1-9][0-9]{4,}$";
		Pattern pattern = Pattern.compile(regex);
	     Matcher m = pattern.matcher(QQ);
		return m.matches();
	}

	/**
	 * 手机验证.
	 * @param mobiles 手机号码
	 * @return 如果符合手机号码规则，返回true，否则返回false。
	 */
	public static boolean isMobileNumber(String mobiles) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 字符串是否在长度内.
	 * @param str 字符串
	 * @param minLength 最小长度
	 * @param maxLength 最大长度
	 * @return 在长度内返回true，否则返回false
	 */
	public static boolean isLength(String str, int minLength, int maxLength) {
		return str.length() >= minLength && str.length() <= maxLength;
	}

	/**
	 * 邮箱验证
	 * @param phone 邮箱号码字符串
	 * @return 如果是符合邮箱字符串规则，返回true，否则返回false。 
	 */
	public static boolean checkEmail(String phone) {
		// Pattern pattern =
		// Pattern.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$");
		Pattern pattern = Pattern
				.compile("^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$");
		Matcher matcher = pattern.matcher(phone);
		
		return matcher.matches();
	}

	/*
	 * 根据用户信息生成DES
	 * 
	 * @param context
	 * @param memberInfo
	 * @return
	 */
	// public static String createDES(Context context, MemberInfo memberInfo) {
	// // 生成DES
	// String imel = ((TelephonyManager)
	// context.getSystemService(context.TELEPHONY_SERVICE)).getDeviceId();
	// StringBuffer stringBuffer = new StringBuffer();
	// // 平台登录 id+密码+imel 加密方式
	// if (memberInfo.getThridType() == 0) {
	// stringBuffer.append(memberInfo.getMemberID());
	// stringBuffer.append("+");
	// stringBuffer.append(memberInfo.getPassword());
	// stringBuffer.append("+");
	// stringBuffer.append(imel);
	// Log.i("tag", stringBuffer.toString());
	// // 新浪第三方 id+第三方id+imel 加密方式
	// } else if (memberInfo.getThridType() == 1) {
	// stringBuffer.append(memberInfo.getMemberID());
	// stringBuffer.append("+");
	// stringBuffer.append("");
	// stringBuffer.append("+");
	// stringBuffer.append(imel);
	// Log.i("tag", stringBuffer.toString());
	// }
	// try {
	// DES3 des3 = new DES3();
	// String loginCode1 = des3.encrypt(stringBuffer.toString());
	// des3 = null;
	// return loginCode1;
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return "";
	// }

	/**
	 * 获取手机品牌型号和系统号码.
	 * @param context 内容
	 * @return 手机品牌型号和系统号码
	 */
	public static String getPhoneInfo(Context context) {
		String BRAND = android.os.Build.BRAND;
		String MODEL = android.os.Build.MODEL;
		String RELEASE = android.os.Build.VERSION.RELEASE;
		
		return BRAND + "_" + MODEL + "_" + RELEASE;
	}

	/**
	 * 判断字符串是否包含中文.
	 * @param str 字符串
	 * @return 如果字符串包含中文，返回true，否则返回false。
	 */
	public static boolean isCN(String str) {
		try {
			byte[] bytes = str.getBytes("UTF-8");

			return (bytes.length == str.length())? false : true ;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * 判断EditText是否是空.
	 * @param editText 判断的EditText
	 * @return 如果为空，则返回true，否则返回false
	 */
	public static boolean isNull(EditText editText) {
		String userName = editText.getText().toString();
		
		return isNull(userName)? true : false;
	}

	/**
	 * 判断字符串是否为空或空串.
	 * @param str 字符串
	 * @return 如果字符串为null或空串，则返回true，否则返回false。
	 */
	public static boolean isNull(String str) {
		return str == null || str.equals("");
	}

	/**
	 * 得到根View.
	 * @param context 返回根view的activity
	 * @return 根view
	 */
	public static View getRootView(Activity context) {
		return ((ViewGroup) context.findViewById(android.R.id.content))
				.getChildAt(0);
	}

	/**
	 * 返回价格只取小数点后两位.
	 * @param d double型的数
	 * @return 返回四舍五入的取小数点后两位的值
	 */
	public static BigDecimal setScale(double d) {
		BigDecimal b = new BigDecimal(d);
		BigDecimal t = b.setScale(2, BigDecimal.ROUND_HALF_UP);
		return t;
	}

	/**
	 * 解析时间数据变为小时分秒的字符串数组.
	 * @param time long型的时间
	 * @return 小时 分钟 秒 的字符数组
	 */
	public static List<String> parseTime(long time) {

		List<String> hms = new ArrayList<String>();
		long hour = time / (60 * 60 * 1000);
		long minute = (time - hour * 60 * 60 * 1000) / (60 * 1000);
		long second = (time - hour * 60 * 60 * 1000 - minute * 60 * 1000) / 1000;
		
		if (second >= 60) {
			second = second % 60;
			minute += second / 60;
		}
		
		if (minute >= 60) {
			minute = minute % 60;
			hour += minute / 60;
		}
		
		String sh = "";
		String sm = "";
		String ss = "";
		if (hour < 10) {
			sh = "0" + String.valueOf(hour);
		} else {
			sh = String.valueOf(hour);
		}
		
		if (minute < 10) {
			sm = "0" + String.valueOf(minute);
		} else {
			sm = String.valueOf(minute);
		}
		
		if (second < 10) {
			ss = "0" + String.valueOf(second);
		} else {
			ss = String.valueOf(second);
		}
		hms.add(sh);
		hms.add(sm);
		hms.add(ss);
		
		return hms;
	}

	/**
	 * 判断字符串是否是空或者全是空格.
	 * @param str 判断的字符串
	 * @return 如果全是空，返回true，否则返回false
	 */
	public static boolean isNullorEmpty(String str) {
		if(str == null){
			return true;
		}
		
		String text = str.replaceAll(" ", "");
		
		return text.equals("");
	}

	/**
	 * 判断文件是否存在
	 * @param path 文件路径
	 * @return 文件存在则返回true，否则返回false
	 */
	public static boolean isFileExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		return true;
	}
	

	
}

/*
 * 当onchange返回时 调用存数据
 * 
 * @param object
 * @param isList
 *            true代表list false代表object
 */

// public void saveData(Object object,boolean isList){
// if (object != null)
// {
// if (isList)
// {
// List<?> list = (List<?>) object;
// if (list.size() == 0)
// {
// return;
// }
// }
// CacheHandler handler = new CacheHandler();
// }
// }

/*
 * 当onchange返回时 调用存数据
 * 
 * @param object
 * @param isList
 *            true代表list false代表object
 */

// public void saveData(Object object,boolean isList){
// if (object != null)
// {
// if (isList)
// {
// List<?> list = (List<?>) object;
// if (list.size() == 0)
// {
// return;
// }
// }
// CacheHandler handler = new CacheHandler();
//
// DataUtil.encodeMD5(new
// StringBuffer(url).append(netBuilder.getParamStr()).toString());
// handler.saveData(isList ? DataType.LIST : DataType.OBJECT, taskCode, object);
// }
//
// DataUtil.encodeMD5(new
// StringBuffer(url).append(netBuilder.getParamStr()).toString());
// handler.saveData(isList ? DataType.LIST : DataType.OBJECT, taskCode, object);
// }
//
// }

