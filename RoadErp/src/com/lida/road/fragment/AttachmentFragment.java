package com.lida.road.fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.adapter.AttachmentAdapater;

public class AttachmentFragment extends Fragment {
	private GridView gridView;
	private AttachmentAdapater attachmentAdapater;
	private View view;
	public static final String TAG = "fragment_attachment";

	public AttachmentFragment() {
		super();
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_attachment, container, false);
		gridView = (GridView) view.findViewById(R.id.picture_gridView);
		attachmentAdapater = new AttachmentAdapater(getActivity());
		gridView.setAdapter(attachmentAdapater);
		return view;

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("xxxx");
		if (resultCode == Activity.RESULT_OK) {// 用户点击了确定
			if (data != null) {
				Uri uri = data.getData();
				if (uri == null) {
					SystemUtils.MToast("未获取到文件", getActivity());
					return;
				}
				Cursor c = getActivity().getContentResolver().query(uri,
						new String[] { MediaStore.MediaColumns.DATA }, null,
						null, null);
				if (c != null && c.moveToFirst()) {
					String filPath = c.getString(0);
					System.out.println(filPath);
					attachmentAdapater.attachmentUrl
							.remove(attachmentAdapater.attachmentUrl.size() - 1);
					attachmentAdapater.attachmentUrl.add(filPath);
					attachmentAdapater.attachmentUrl.add("");
					attachmentAdapater.notifyDataSetChanged();
					System.out.println("总共有："
							+ attachmentAdapater.attachmentUrl.size() + "数据");
				}
			}

		} else if (resultCode == Activity.RESULT_CANCELED) {// 用户点击了取消
			SystemUtils.MToast("您选择了取消", getActivity());
		} else {// 其他
			SystemUtils.MToast("获取图片失败", getActivity());
		}
	}

}
