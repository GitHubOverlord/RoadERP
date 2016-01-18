package com.lida.road.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.cookie.Cookie;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.jun.android_frame.constant.Constant;
import com.lida.road.application.MainApplication;
import com.loopj.android.http.PersistentCookieStore;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

public class AuthImageDownloader extends BaseImageDownloader {

	private static final int MAX_REDIRECT_COUNT = 5;

	public AuthImageDownloader(Context context) {
		super(context);
	}

	public AuthImageDownloader(Context context, int connectTimeout,
			int readTimeout) {
		super(context, connectTimeout, readTimeout);
	}

	protected InputStream getStreamFromNetwork(String imageUri, Object extra)
			throws IOException {
		HttpURLConnection conn = connectTo(imageUri);

		int redirectCount = 0;
		while (conn.getResponseCode() / 100 == 3
				&& redirectCount < MAX_REDIRECT_COUNT) {
			conn = connectTo(conn.getHeaderField("Location"));
			redirectCount++;
		}

		return new BufferedInputStream(conn.getInputStream(), BUFFER_SIZE);
	}

	/**
	 * 获取带有用户验证信息的HttpURLConnection
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	private HttpURLConnection connectTo(String url) throws IOException {
		String encodedUrl = Uri.encode(url, ALLOWED_URI_CHARS);
		HttpURLConnection conn = (HttpURLConnection) new URL(encodedUrl)
				.openConnection();
                //这句话为urlconnection加入身份验证信息
		List<Cookie> cookies = MainApplication.persistentCookieStore.getCookies();
		for (Cookie cookie : cookies) {
			conn.setRequestProperty(cookie.getValue(),
    				cookie.getValue());
		}
//		for (Header header : Constant.headers) {
//			if (header.getValue().contains("JSESSIONID")) {
//				conn.setRequestProperty("JSESSIONID",
//	    				header.getValue().split("=|;")[1]);
//				System.out.println("name:JSESSIONID"+header.getValue().split("=|;")[1]);
//			}
//			
//		}
		conn.setConnectTimeout(connectTimeout);
		conn.setReadTimeout(readTimeout);
		conn.connect();
		return conn;
	}
}