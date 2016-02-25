package com.lida.road.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jun.android_frame.dao.CacheFileDao;
import com.jun.android_frame.entity.CacheFileBean;
import com.jun.android_frame.http.utils.AsyncCacheFileDownloads;
import com.jun.frame.utils.BitmapUtilities;
import com.jun.frame.utils.MediaFile;
import com.jun.frame.utils.VedioThumbnailUtil;
import com.lida.road.R;
import com.lida.road.constant.Constant;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.entity.AffixFile;
import com.lida.road.utils.PersistenceManager;

public class AttachmentAdapater extends BaseAdapter {
	private Context context;
	public List<AffixFile> attachmentUrl = new ArrayList<AffixFile>();
	public List<AffixFile> removeList = new ArrayList<AffixFile>();
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
			String id = attachmentUrl.get(position).getId();
			if (id != null && !id.equals("")) {// 表示从网络来的
				boolean isVedio = MediaFile.isVideoFileType(fileName);
				String url = (HTTPConstant.HOST_URL_NO_SPRIT + fileName)
						.replaceAll("\\\\", "/");
				CacheFileBean cacheFileBean = CacheFileDao.getInstance(context)
						.findFileIsEixt(url, context);
				if (cacheFileBean != null) {
					Bitmap bm;
					if (isVedio) {// 判断是文件还是视频
						VedioThumbnailUtil vedioThumbnailUtil = new VedioThumbnailUtil();
						bm = vedioThumbnailUtil.getVideoThumbnail(cacheFileBean
								.getLocalPath());
					} else {
						BitmapUtilities bitmapUtilities = new BitmapUtilities();
						bm = bitmapUtilities.getBitmapThumbnail(
								cacheFileBean.getLocalPath(), 160, 160);
					}
					Drawable drawable = new BitmapDrawable(bm);
					imageView.setBackgroundDrawable(drawable);
				} else {
					if (isVedio) {
						imageView.setBackgroundResource(R.drawable.bg_vedio);
					} else {
						imageView.setBackgroundResource(R.drawable.bg_pic);

					}
				}

			} else {// 表示从本地来的

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

		}
		relativeLayout.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				// 长按事件，如果不是第一张，我们就弹出删除按钮
				if (!(position == attachmentUrl.size() - 1)) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							context);
					builder.setTitle("确定要删除？");
					builder.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									AffixFile affixFile = attachmentUrl
											.get(position);
									// 如果附件的id部位空，那么证明这是从网络获取的一张图片，我们删除他的话，需要记录在删除List中
									if (affixFile.getId() != null
											&& !affixFile.getId().equals("")) {
										removeList.add(affixFile);
									}
									for (int i = 0; i < removeList.size(); i++) {
										System.out.println(removeList.get(i)
												.getPath());
									}
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

				return false;
			}
		});
		relativeLayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 点击事件，如果是第一个，那么我们就弹出选照片的dialog，否则的话，我们就打开这张图片
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
										String state = Environment
												.getExternalStorageState();
										if (state
												.equals(Environment.MEDIA_MOUNTED)) {
											Intent intent1 = new Intent(
													MediaStore.ACTION_IMAGE_CAPTURE);
											Constant.CAMERA_FILE_PATH = UUID
													.randomUUID().toString()
													+ ".jpg";
											intent1.putExtra(
													MediaStore.EXTRA_OUTPUT,
													Uri.fromFile(new File(
															Environment
																	.getExternalStorageDirectory(),
															Constant.CAMERA_FILE_PATH)));
											((Activity) context)
													.startActivityForResult(
															intent1,
															REQUEST_CAMERA_CODE);
										} else {
											Toast.makeText(context, "没有SD卡",
													Toast.LENGTH_LONG).show();
										}

										break;
									case 2:// 从本地选择
										Intent intent3 = new Intent();
										intent3.setType("image/*|vedio/*");
										intent3.setAction(Intent.ACTION_PICK);
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
					// TODO 这里是打开图片，看细节
					String imgUrl = attachmentUrl.get(position).getPath();
					final String url = (HTTPConstant.HOST_URL_NO_SPRIT + imgUrl)
							.replaceAll("\\\\", "/");
					System.out.println(url);
					String[] type = url.split("\\.");
					AsyncCacheFileDownloads asyncCacheFileDownloads = new AsyncCacheFileDownloads();
					try {
						asyncCacheFileDownloads.downloadFile(url,
								type[type.length - 1], context, imageView);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		});
		return convertView;
	}
}
