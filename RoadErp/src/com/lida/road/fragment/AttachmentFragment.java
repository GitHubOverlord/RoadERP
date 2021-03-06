package com.lida.road.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jun.android_frame.view.MyGridView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.adapter.AttachmentAdapater;
import com.lida.road.constant.Constant;
import com.lida.road.entity.AffixFile;

public class AttachmentFragment extends Fragment {
	private MyGridView gridView;
	private AttachmentAdapater attachmentAdapater;
	private View view;
	public static final String TAG = "fragment_attachment";
	public static final String BUNDLE_IMG = "bundle_img_url";

	public AttachmentFragment() {
		super();
	}

	public AttachmentFragment(List<AffixFile> list) {

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_attachment, container, false);
		gridView = (MyGridView) view.findViewById(R.id.picture_gridView);
		attachmentAdapater = new AttachmentAdapater(getActivity());
		Bundle bundle = getArguments();
		if (bundle != null) {
			List<AffixFile> list = (List<AffixFile>) bundle
					.getSerializable(BUNDLE_IMG);
			if (list != null) {
				attachmentAdapater.attachmentUrl
						.remove(attachmentAdapater.attachmentUrl.size() - 1);
				attachmentAdapater.attachmentUrl.addAll(list);
				attachmentAdapater.attachmentUrl.add(new AffixFile());
			}
		}
		gridView.setAdapter(attachmentAdapater);
		return view;

	}

	public List<AffixFile> getImgUrls() {
		List<AffixFile> returnPicList = new ArrayList<>();
		returnPicList.addAll(attachmentAdapater.attachmentUrl);
		if (returnPicList != null && returnPicList.size() > 0) {
			returnPicList.remove(returnPicList.size() - 1);
		}
		return returnPicList == null ? new ArrayList<AffixFile>()
				: returnPicList;
	}

	public List<AffixFile> getRemoveList() {
		return attachmentAdapater.removeList;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("xxxx");
		if (resultCode == Activity.RESULT_OK) {// 用户点击了确定
			String filPath = null;
			if (requestCode == AttachmentAdapater.REQUEST_CAMERA_CODE) {// 如果是拍照
				filPath = Environment.getExternalStorageDirectory()
						+ "/"+Constant.CAMERA_FILE_PATH;
			} else {
				if (data != null) {
					Uri uri = data.getData();
					if (uri == null) {
						SystemUtils.MToast("未获取到文件", getActivity());
						return;
					}
					Cursor c = getActivity().getContentResolver().query(uri,
							new String[] { MediaStore.MediaColumns.DATA },
							null, null, null);
					if (c != null && c.moveToFirst()) {
						filPath = c.getString(0);

					}
				} else {
					SystemUtils.MToast("选择附件失败！", getActivity());
				}
			}
			if (filPath != null) {
				System.out.println(filPath);
				attachmentAdapater.attachmentUrl
						.remove(attachmentAdapater.attachmentUrl.size() - 1);
				attachmentAdapater.attachmentUrl.add(new AffixFile(filPath));
				attachmentAdapater.attachmentUrl.add(new AffixFile());
				attachmentAdapater.notifyDataSetChanged();
				System.out.println("总共有："
						+ attachmentAdapater.attachmentUrl.size() + "数据");
			} else {
				SystemUtils.MToast("找不到文件位置！", getActivity());
			}

		} else if (resultCode == Activity.RESULT_CANCELED) {// 用户点击了取消
			SystemUtils.MToast("您选择了取消", getActivity());
		} else {// 其他
			SystemUtils.MToast("获取图片失败", getActivity());
		}
	}
}
