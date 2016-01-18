package com.lida.file.downloader;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.jun.android_frame.entity.BaseEntity;
import com.jun.android_frame.http.utils.HttpUtils;
import com.jun.android_frame.view.ShowMyCustomProgress;
import com.lida.file.downloader.CustomMultipartEntity.ProgressListener;
import com.loopj.android.http.PersistentCookieStore;

public class HttpMultipartPost extends AsyncTask<String, Integer, String> {
	private Context context;
	private String[] filePath;
	// private ProgressDialog pd;
	private long totalSize;
	private String requestUrl;
	public Map map;
	private ShowMyCustomProgress showMyCustomProgress;

	public HttpMultipartPost(Context context, Map map, String[] filePath,
			String requestUrl) {
		this.filePath = filePath;
		this.requestUrl = requestUrl;
		this.map = map;
		this.context = context;
		showMyCustomProgress = new ShowMyCustomProgress(context);
		showMyCustomProgress.setTitleAndBoyd("提示", "正在上传数据和文件");
		showMyCustomProgress.setCancelable(false);
	}

	@Override
	protected void onPreExecute() {
		showMyCustomProgress.show();
	}

	@Override
	public String doInBackground(String... params) {
		String serverResponse = null;
		HttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpPost httpPost = new HttpPost(requestUrl);
		System.out.println("url is " + requestUrl);
		try {
			CustomMultipartEntity multipartContent = new CustomMultipartEntity(
					new ProgressListener() {
						@Override
						public void transferred(long num) {
							publishProgress((int) ((num / (float) totalSize) * 100));
						}
					});

			Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry en = (Entry) it.next();
				multipartContent.addPart(
						en.getKey().toString(),
						new StringBody(en.getValue().toString(), Charset
								.forName("UTF-8")));

			}

			// 使用FileBody上传图片
			// if(=)
			for (String string : filePath) {
				multipartContent.addPart("files", new FileBody(new File(
						string)));
			}
			totalSize = multipartContent.getContentLength();
			System.out.println("文件大小：" + totalSize);
			// 上传
			httpPost.setEntity(multipartContent);
			PersistentCookieStore myCookieStore = new PersistentCookieStore(
					context);
			List<Cookie> cookies = myCookieStore.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					httpPost.setHeader("Cookie", "SESSIONID=" + c.getValue());

				}
			}
			HttpResponse response = httpClient.execute(httpPost, httpContext);
			HttpParams httpParams = httpClient.getParams();
			httpParams.toString();
			serverResponse = EntityUtils.toString(response.getEntity());
			System.out.println("serverResponse:" + serverResponse);
			BaseEntity baseEntity = new Gson().fromJson(serverResponse,
					BaseEntity.class);
			serverResponse = baseEntity.getStatus() + "";

		} catch (Exception e) {
			e.printStackTrace();
			return 0 + "";
		}
		System.out.println(serverResponse);
		return serverResponse;
	}

	@Override
	protected void onProgressUpdate(Integer... progress) {
		// pd.setProgress((int) (progress[0]));
	}

	@Override
	protected void onPostExecute(String results) {
		System.out.println(results);
		showMyCustomProgress.cancel();
	}

	@Override
	protected void onCancelled() {
		System.out.println("cancle");
	}

}