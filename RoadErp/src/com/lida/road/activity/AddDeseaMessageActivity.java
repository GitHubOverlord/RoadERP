package com.lida.road.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.GridView;

import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.adapter.AttachmentAdapater;

public class AddDeseaMessageActivity extends MainBaseActivity {
	private GridView gridView;
	private AttachmentAdapater attachmentAdapater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_disease_message);
		initView();
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.add_disease_message_attachment);
		attachmentAdapater = new AttachmentAdapater(
				AddDeseaMessageActivity.this);
		gridView.setAdapter(attachmentAdapater);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {// 用户点击了确定
			if (data != null) {
				Uri uri = data.getData();
				if (uri == null) {
					SystemUtils.MToast("未获取到文件", AddDeseaMessageActivity.this);
					return;
				}
				Cursor c = getContentResolver().query(uri,
						new String[] { MediaStore.MediaColumns.DATA }, null,
						null, null);
				if (c != null && c.moveToFirst()) {
					String filPath = c.getString(0);
					System.out.println(filPath);
					attachmentAdapater.attachmentUrl.add(filPath);
					attachmentAdapater.notifyDataSetChanged();
					System.out.println("总共有："
							+ attachmentAdapater.attachmentUrl.size() + "数据");
				}
			}

		} else if (resultCode == RESULT_CANCELED) {// 用户点击了取消
			SystemUtils.MToast("您选择了取消", AddDeseaMessageActivity.this);
		} else {// 其他
			SystemUtils.MToast("获取图片失败", AddDeseaMessageActivity.this);
		}

	}
}
