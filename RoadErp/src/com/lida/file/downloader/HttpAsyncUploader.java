package com.lida.file.downloader;

import java.io.File;
import java.io.FileNotFoundException;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * 异步上传单个文件
 */
public class HttpAsyncUploader {
	private static final String TAG = "HttpAsyncUploader";

	Context mContext;

	public HttpAsyncUploader() {
	}

	public void uploadFile(String uri, String path,
			AsyncHttpResponseHandler asyncHttpResponseHandler,
			RequestParams requestParams) {

		File myFile = new File(path);

		try {
			requestParams.put("*", myFile, "application/octet-stream");

			AsyncHttpClient client = new AsyncHttpClient();
			HttpClientUtil.post(path, requestParams, asyncHttpResponseHandler);

		} catch (FileNotFoundException e) {

		}
	}
}