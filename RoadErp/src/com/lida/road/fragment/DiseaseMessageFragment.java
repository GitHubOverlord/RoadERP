package com.lida.road.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lida.road.R;
import com.lida.road.entity.AffixFile;
import com.lida.road.entity.DiseaseRecord;
import com.lida.road.utils.StringAdapter;

public class DiseaseMessageFragment extends Fragment {
	private TextView expandDiseaseMessageLayout;
	private LinearLayout diseaseMessageLayout;
	private View view;
	private DiseaseRecord diseaseRecord;
	public static final String BUNDLE_DISEASE_MESSAGE = "disease_message";
	public static final String BUNDLE_DISEASE_ATTACHMENT = "disease_message_attachment";
	public static final String TAG = "disease_message_fragment";
	private TextView diseaseNumber, routeNumber, stakeNumber, diseaseLevel,
			diseaseCatogory, diseaseType, diseasePosition, suggestFix,
			workDate, measurementUnit, estimatedAmount, reportTime,
			reportPeople, reportEnterpris, phoneNumber;
	private ImgGridViewFragment imgGridViewFragment;
	private List<AffixFile> affixFiles;
	private boolean shouSpread = false;

	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_include_disease_details,
				container, false);
		diseaseRecord = (DiseaseRecord) getArguments().getSerializable(
				BUNDLE_DISEASE_MESSAGE);
		affixFiles = (List<AffixFile>) getArguments().getSerializable(
				BUNDLE_DISEASE_ATTACHMENT);
		diseaseNumber = (TextView) view.findViewById(R.id.disease_number);
		routeNumber = (TextView) view.findViewById(R.id.route_number);
		stakeNumber = (TextView) view.findViewById(R.id.stake_number);
		diseaseLevel = (TextView) view.findViewById(R.id.disease_level);
		diseaseCatogory = (TextView) view.findViewById(R.id.disease_catogory);
		diseasePosition = (TextView) view.findViewById(R.id.disease_position);
		diseaseType = (TextView) view.findViewById(R.id.disease_type);
		suggestFix = (TextView) view.findViewById(R.id.suggest_fix);
		workDate = (TextView) view.findViewById(R.id.work_date);
		measurementUnit = (TextView) view.findViewById(R.id.measurement_unit);
		estimatedAmount = (TextView) view.findViewById(R.id.estimated_amount);
		reportTime = (TextView) view.findViewById(R.id.report_time);
		reportPeople = (TextView) view.findViewById(R.id.report_people);
		reportEnterpris = (TextView) view.findViewById(R.id.report_enterpris);
		phoneNumber = (TextView) view.findViewById(R.id.report_number);
		diseaseMessageLayout = (LinearLayout) view
				.findViewById(R.id.lv_disease_message);
		expandDiseaseMessageLayout = (TextView) view
				.findViewById(R.id.construction_details_spread);
		expandDiseaseMessageLayout.setOnClickListener(listener);
		setDateToView(diseaseRecord);
		imgGridViewFragment = new ImgGridViewFragment(affixFiles);
		addFragment(imgGridViewFragment, ImgGridViewFragment.TAG,
				R.id.fragment_disease_img);
		if (shouSpread) {
			diseaseMessageLayout.setVisibility(View.VISIBLE);
		}
		return view;

	}

	private void addFragment(Fragment fragment, String tag, int id) {
		FragmentManager manager = getActivity().getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(id, fragment, tag);
		transaction.commit();
	}

	public void openDiseaseMessage() {
		shouSpread = true;

	}

	/**
	 * 
	 */
	OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			switch (arg0.getId()) {
			case R.id.construction_details_spread:
				if (diseaseMessageLayout.getVisibility() == View.VISIBLE) {// 判断如果是现实状态
					diseaseMessageLayout.setVisibility(View.GONE);

				} else {
					diseaseMessageLayout.setVisibility(View.VISIBLE);
				}
				break;

			default:
				break;
			}
		}
	};

	private void setDateToView(DiseaseRecord diseaseRecord) {
		diseaseNumber.setText(diseaseRecord.getSn() == null ? ""
				: diseaseRecord.getSn());
		routeNumber.setText(diseaseRecord.getRouteCode() == null ? ""
				: diseaseRecord.getRouteCode());
		stakeNumber.setText(diseaseRecord.getStake() + "");
		diseaseLevel.setText(diseaseRecord.getDiseaseLevel() == null ? ""
				: diseaseRecord.getDiseaseLevel());
		diseaseCatogory.setText(StringAdapter.diseaseCatogoryIdToString(
				diseaseRecord.getDiseasePart(), getActivity()));
		diseaseType.setText(diseaseRecord.getDiseaseType() == null ? ""
				: diseaseRecord.getDiseaseType());
		diseasePosition.setText(diseaseRecord.getDiseasePosition() == null ? ""
				: diseaseRecord.getDiseasePosition());
		suggestFix.setText(diseaseRecord.getEstimatingScheme() == null ? ""
				: diseaseRecord.getEstimatingScheme());
		workDate.setText(diseaseRecord.getEstimatingJob() + "");
		measurementUnit.setText(diseaseRecord.getEstimatingUnit() == null ? ""
				: diseaseRecord.getEstimatingUnit());
		estimatedAmount.setText(diseaseRecord.getEstimatingCost() + "");
		reportTime.setText(diseaseRecord.getReportTime() == null ? ""
				: diseaseRecord.getReportTime());
		reportPeople.setText(diseaseRecord.getReportorName() == null ? ""
				: diseaseRecord.getReportorName());
		reportEnterpris.setText(diseaseRecord.getOrgName() == null ? ""
				: diseaseRecord.getOrgName());
		phoneNumber.setText(diseaseRecord.getReportorPhone() == null ? ""
				: diseaseRecord.getReportorPhone());
	}
}
