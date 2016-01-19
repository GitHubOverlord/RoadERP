package com.lida.road.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jun.android_frame.view.MyGridView;
import com.lida.road.R;
import com.lida.road.adapter.ImgGridViewAdapter;
import com.lida.road.entity.AffixFile;

/**
 * 专门用来显示图片的fragment
 * 
 * @author Administrator
 * 
 */
public class ImgGridViewFragment extends Fragment {
	private MyGridView gridView;
	private ImgGridViewAdapter imgGridViewAdapter;
	private View view;
	public static final String TAG = "fragment_img_grid_view";
	public static final String BUNDLE_IMG_URL = "img_url";
	private List<AffixFile> list;

	public ImgGridViewFragment(List<AffixFile> list) {
		this.list = list;
	}

	public ImgGridViewFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (getArguments() != null) {
			list = (List<AffixFile>) getArguments().getSerializable(
					BUNDLE_IMG_URL);
		}
		if (list == null) {
			list = new ArrayList<AffixFile>();
		}

		view = inflater.inflate(R.layout.fragment_img_grid_view, container,
				false);
		gridView = (MyGridView) view.findViewById(R.id.picture_gridView);
		imgGridViewAdapter = new ImgGridViewAdapter(getActivity(), list);
		gridView.setAdapter(imgGridViewAdapter);
		return view;

	}

	public List<AffixFile> getImgUrls() {
		return list;
	}
}
