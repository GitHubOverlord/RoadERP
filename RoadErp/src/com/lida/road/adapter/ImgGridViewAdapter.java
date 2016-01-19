package com.lida.road.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jun.android_frame.http.utils.AsyncCacheFileDownloads;
import com.jun.frame.utils.MediaFile;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.entity.AffixFile;

public class ImgGridViewAdapter extends BaseAdapter {
	private Context context;
	public List<AffixFile> list;
	private LayoutInflater layoutInflater;

	public ImgGridViewAdapter(Context context, List<AffixFile> list) {
		this.context = context;
		layoutInflater = LayoutInflater.from(context);
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		String imgUrl = list.get(position).getPath();
		final String url = (HTTPConstant.HOST_URL_NO_SPRIT + imgUrl)
				.replaceAll("\\\\", "/");

		RelativeLayout relativeLayout;
		final ImageView imageView;
		convertView = layoutInflater.inflate(R.layout.gridview_attachment,
				parent, false);
		relativeLayout = (RelativeLayout) convertView
				.findViewById(R.id.attachement_rl);
		imageView = (ImageView) convertView.findViewById(R.id.attachement_iv);
		boolean isVedio = MediaFile.isVideoFileType(url);
		if (isVedio) {
			imageView.setBackgroundResource(R.drawable.bg_vedio);
		} else {
			imageView.setBackgroundResource(R.drawable.bg_pic);

		}
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				System.out.println(url);
				String[] type = url.split("\\.");
				AsyncCacheFileDownloads asyncCacheFileDownloads = new AsyncCacheFileDownloads();
				try {
					asyncCacheFileDownloads.downloadFile(url,
							type[type.length - 1], context);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		return convertView;
	}
}
