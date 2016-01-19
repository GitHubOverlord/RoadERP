package com.lida.road.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.entity.AffixFile;
import com.lida.road.entity.Construction;

public class ConstructionMessageFragment extends Fragment {
	private View view;
	private ImgGridViewFragment constructionImgViewFragment,
			supervisionImgGridViewFragment;
	private Construction construction;
	private List<AffixFile> constructionList;
	private List<AffixFile> supervisionList;
	private TextView remarkTextView, dateTextView, spreadConstructionTextView;
	private LinearLayout constructionLayout;
	private boolean shouldSpread = false;
	public static final String TAG = "construction_message_fragment";

	public ConstructionMessageFragment(Construction construction,
			List<AffixFile> constructionList, List<AffixFile> supervisionList) {
		this.construction = construction;
		this.constructionList = constructionList;
		this.supervisionList = supervisionList;
	}

	public ConstructionMessageFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_include_construction,
				container, false);
		remarkTextView = (TextView) view.findViewById(R.id.tv_remark);
		spreadConstructionTextView = (TextView) view
				.findViewById(R.id.construction_details_spread);
		constructionLayout = (LinearLayout) view
				.findViewById(R.id.lv_construction_message);
		dateTextView = (TextView) view.findViewById(R.id.tv_date);
		constructionImgViewFragment = new ImgGridViewFragment(constructionList);
		supervisionImgGridViewFragment = new ImgGridViewFragment(
				supervisionList);
		spreadConstructionTextView.setOnClickListener(listener);
		SystemUtils.ifTextSetNullString(remarkTextView,
				construction.getRemark());
		SystemUtils.ifTextSetNullString(dateTextView,
				construction.getCompleteDate());
		addFragment(constructionImgViewFragment, ImgGridViewFragment.TAG,
				R.id.fragment_construction_img);
		addFragment(supervisionImgGridViewFragment, ImgGridViewFragment.TAG,
				R.id.fragment_supervision_img);
		if (shouldSpread) {
			constructionLayout.setVisibility(View.VISIBLE);
		}
		return view;
	}

	public OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			if (constructionLayout.getVisibility() == View.VISIBLE) {
				constructionLayout.setVisibility(View.GONE);
			} else {
				constructionLayout.setVisibility(View.VISIBLE);
			}

		}

	};

	private void addFragment(Fragment fragment, String tag, int id) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(id, fragment, tag);
		transaction.commit();
	}

	public void openConstructionMessage() {
		shouldSpread = true;
	}
}
