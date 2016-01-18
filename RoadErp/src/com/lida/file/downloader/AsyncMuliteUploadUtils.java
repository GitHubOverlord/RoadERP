package com.lida.file.downloader;

import java.io.File;
import java.util.Map;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import com.jun.android_frame.view.ShowMyCustomProgress;
import com.jun.frame.utils.SystemUtils;

public class AsyncMuliteUploadUtils {
	private Context context;

	public void upload(IUpload iUpload, Map<String, String> map, String[] urls,
			String path, Handler handler) {
		if (urls == null) {
			System.out.println("没有文件");
			return;
		}
		FormFile[] formFiles = new FormFile[urls.length];
		for (int i = 0; i < urls.length; i++) {
			File file = new File(urls[i]);
			formFiles[i] = new FormFile(file.getName(), file, file.getName()
					.substring(file.getName().lastIndexOf(".") + 1),
					"application/octet-stream");
		}
		AsyncMuliteUploadUtils.UpLoadAsyncTask upLoadAsyncTask = new AsyncMuliteUploadUtils.UpLoadAsyncTask(
				path, map, formFiles);
		upLoadAsyncTask.execute("");
	}

	class UpLoadAsyncTask extends AsyncTask<String, Integer, String> {
		private String path;
		private Map<String, String> map;
		private FormFile[] formFiles;
		private ShowMyCustomProgress showMyCustomProgress;

		UpLoadAsyncTask(String path, Map<String, String> map,
				FormFile[] formFiles) {
			this.path = path;
			this.map = map;
			this.formFiles = formFiles;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			showMyCustomProgress.dismiss();
			SystemUtils.MToast(result, context);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showMyCustomProgress = new ShowMyCustomProgress(context);
			showMyCustomProgress.setTitleAndBoyd("提示", "正在上传病害信息表");
			showMyCustomProgress.setCancelable(false);
			showMyCustomProgress.show();
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected String doInBackground(String... arg0) {
			boolean result = false;
			String message = "";
			try {
				result = SocketHttpRequester.post(path, map, formFiles);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (result) {
				message = "提交数据成功！";
			} else {
				message = "提交数据失败！";
			}
			return message;
		}

	}

}
