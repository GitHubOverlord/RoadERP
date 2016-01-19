package com.lida.road.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jun.frame.utils.BitmapUtilities;
import com.jun.frame.utils.MediaFile;
import com.jun.frame.utils.VedioThumbnailUtil;
import com.jun.frame.utils.ViewUtils;
import com.lida.road.R;
import com.lida.road.entity.AffixFile;

public class AttachmentAdapater extends BaseAdapter {
	private Context context;
	public List<AffixFile> attachmentUrl = new ArrayList<AffixFile>();
	private LayoutInflater layoutInflater;
	private String choicePicuterRemind[] = { "录制视频", "拍照", "从本地选择" };
	public static final int REQUEST_VEDIO_CODE = 555;
	public static final int REQUEST_CAMERA_CODE = 666;
	public static final int REQUEST_LOCAL_CODE = 777;

	public AttachmentAdapater(Context context) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		attachmentUrl.add(new AffixFile());
	}

	@Override
	public int getCount() {
		return attachmentUrl.size();
	}

	@Override
	public Object getItem(int position) {
		return attachmentUrl.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		System.out.println("add item to gridview");
		RelativeLayout relativeLayout;
		final ImageView imageView;
		convertView = layoutInflater.inflate(R.layout.gridview_attachment,
				parent, false);
		relativeLayout = (RelativeLayout) convertView
				.findViewById(R.id.attachement_rl);
		imageView = (ImageView) convertView.findViewById(R.id.attachement_iv);
		if (position == attachmentUrl.size() - 1) {
			imageView.setBackgroundResource(R.drawable.icon_camera);
		} else {
			String fileName = attachmentUrl.get(position).getPath();
			boolean isAudio = MediaFile.isVideoFileType(fileName);
			Bitmap bm;
			if (isAudio) {// 判断是文件还是视频
				VedioThumbnailUtil vedioThumbnailUtil = new VedioThumbnailUtil();
				bm = vedioThumbnailUtil.getVideoThumbnail(fileName);
			} else {
				BitmapUtilities bitmapUtilities = new BitmapUtilities();
				bm = bitmapUtilities.getBitmapThumbnail(fileName, 160, 160);
			}

			Drawable drawable = new BitmapDrawable(bm);
			imageView.setBackgroundDrawable(drawable);
		}
		relativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (position == attachmentUrl.size() - 1) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setItems(choicePicuterRemind,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									switch (which) {
									case 0:// 视频
										Intent intent = new Intent(
												MediaStore.ACTION_VIDEO_CAPTURE);
										// MediaStore.EXTRA_VIDEO_QUALITY：这个值的范围是0~1，0的时候质量最差且文件最小，1的时候质量最高且文件最大。
										intent.putExtra(
												MediaStore.EXTRA_VIDEO_QUALITY,
												1);
										((Activity) context)
												.startActivityForResult(intent,
														REQUEST_VEDIO_CODE);
										break;
									case 1:// 拍照
										Intent intent1 = new Intent(
												MediaStore.ACTION_IMAGE_CAPTURE);
										((Activity) context)
												.startActivityForResult(
														intent1,
														REQUEST_CAMERA_CODE);
										break;
									case 2:// 从本地选择
										Intent intent3 = new Intent();
										intent3.setType("image/*|vedio/*");
										intent3.setAction(Intent.ACTION_GET_CONTENT);
										((Activity) context)
												.startActivityForResult(
														intent3,
														REQUEST_LOCAL_CODE);
										break;
									default:
										break;
									}

								}
							});
					builder.create();
					builder.show();
				} else {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setTitle("确定要删除？");
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									attachmentUrl.remove(position);
									AttachmentAdapater.this
											.notifyDataSetChanged();
								}
							});
					builder.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							});
					builder.create();
					builder.show();
				}

			}
		});
		return convertView;
	}
}
