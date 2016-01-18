package com.lida.road.activity.construction;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import u.aly.bu;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.entity.BaseEntity;
import com.jun.android_frame.http.utils.AsyncUploadFiles;
import com.jun.android_frame.inter.HttpConnectReciver;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.activity.disease.DiseaseReportActivity;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.entity.Construction;
import com.lida.road.entity.DiseaseRecord;
import com.lida.road.fragment.AttachmentFragment;
import com.lida.road.utils.DataUtil;
import com.lida.road.utils.StringAdapter;
import com.loopj.android.http.RequestParams;

/**
 * 施工详情页面-監理
 * 
 * @author Administrator
 * 
 */
public class ConstructionSupvisionDetailsActivity extends MainBaseActivity {

	private AttachmentFragment attachmentFragment;
	private LinearLayout diseaseMessageLayout;
	private Construction construction;
	public static final String BUNDLE_DISEASE_MESSAGE = "bundle_disease_message";
	private TextView expandDiseaseMessageLayout;
	private TextView diseaseNumber, routeNumber, stakeNumber, diseaseLevel,

	diseaseCatogory, diseaseType, diseasePosition, suggestFix, workDate,
			measurementUnit, estimatedAmount, reportTime, reportPeople,
			reportEnterpris, phoneNumber;

	AttachmentFragment attachmentFragment1, attachmentFragment2;
	private Button bcbtn;
	private EditText cons_jlyj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_construction_supvision_details);
		initVie();

		initView();

	}

	private void initVie() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "监理意见填写");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(ConstructionSupvisionDetailsActivity.this);
		attachmentFragment = new AttachmentFragment();
		addFragment(attachmentFragment, AttachmentFragment.TAG,
				R.id.fragment_attendance1);
		Bundle bundle = getIntent().getExtras();
		construction = (Construction) bundle
				.getSerializable(BUNDLE_DISEASE_MESSAGE);
		diseaseMessageLayout = (LinearLayout) findViewById(R.id.lv_disease_message);
		expandDiseaseMessageLayout = (TextView) findViewById(R.id.construction_details_spread);
		expandDiseaseMessageLayout.setOnClickListener(listener);

		diseaseNumber = (TextView) findViewById(R.id.disease_number);
		routeNumber = (TextView) findViewById(R.id.route_number);
		stakeNumber = (TextView) findViewById(R.id.stake_number);
		diseaseLevel = (TextView) findViewById(R.id.disease_level);
		diseaseCatogory = (TextView) findViewById(R.id.disease_catogory);
		diseasePosition = (TextView) findViewById(R.id.disease_position);
		diseaseType = (TextView) findViewById(R.id.disease_type);
		suggestFix = (TextView) findViewById(R.id.suggest_fix);
		workDate = (TextView) findViewById(R.id.work_date);
		measurementUnit = (TextView) findViewById(R.id.measurement_unit);
		estimatedAmount = (TextView) findViewById(R.id.estimated_amount);
		reportTime = (TextView) findViewById(R.id.report_time);
		reportPeople = (TextView) findViewById(R.id.report_people);
		reportEnterpris = (TextView) findViewById(R.id.report_enterpris);
		phoneNumber = (TextView) findViewById(R.id.report_number);
		cons_jlyj = (EditText) findViewById(R.id.cons_jlyj);
		bcbtn = (Button) findViewById(R.id.bc_btn);
		bcbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("执行保存");
				save();
			}
		});
		setDateToView(construction.getDiseaseRecord());
	}

	private void save() {

		try {
			RequestParams params = new RequestParams();

			List list = attachmentFragment1.getImgUrls();
			if (null != list && list.size() > 0) {
				File[] files = new File[list.size()];
				for (int i = 0; i < list.size(); i++) {
					files[i] = new File(list.get(i).toString());
				}
				params.put("files", files);
				String fileNames = "";
				for (int i = 0; i < files.length; i++) {
					fileNames = fileNames + files[i].getName() + ";";
				}
				params.put("fileNames", fileNames);
			}
			if (DataUtil.isNull(cons_jlyj.getText().toString())) {
				SystemUtils.MToast("请输入监理意见！",
						ConstructionSupvisionDetailsActivity.this);
				return;
			}
			params.put("construction.id", construction.getId());
			params.put("construction.supervisorRemark", cons_jlyj.getText()
					.toString().trim());
			params.put("stepType", "finish");
			AsyncUploadFiles<Construction> asyncUploadFiles = new AsyncUploadFiles<Construction>(
					ConstructionSupvisionDetailsActivity.this, "提示",
					"正在上传施工信息和附件", httpConnectReciver, params,
					HTTPConstant.submitSupvisionConstruction,
					new TypeToken<Construction>() {
					}.getType());
			asyncUploadFiles.setProgressViewCanCancel();
			try {
				asyncUploadFiles.uploadFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	HttpConnectReciver<Construction> httpConnectReciver = new HttpConnectReciver<Construction>() {

		@Override
		public void onSuccess(Construction t, BaseEntity baseEntity) {
			int status = baseEntity.getStatus();
			String remind = "";
			if (status == 0) {
				remind = "上报失败";
			} else if (status == 1) {
				remind = "上报成功";
				finish();
			} else if (status == 2) {
				remind = "权限不足";
			} else if (status == 3) {
				remind = "未登录，或者登录过期";
			}
			SystemUtils.MToast(remind,
					ConstructionSupvisionDetailsActivity.this);

		}

		@Override
		public void onFail(String string) {

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
				diseaseRecord.getDiseasePart() == null ? "" : diseaseRecord
						.getDiseasePart(),
				ConstructionSupvisionDetailsActivity.this));
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

	/**
	 * 增加一个fragment的方法
	 * 
	 * @param fragment
	 * @param tag
	 *            加载fragment 用的标记，我们可以通过findFragmentByTag这个方法找到fragment
	 */
	/**
	 * 这里是把result结果映射到图片选择的fragment里面去
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

	private void initView() {
		attachmentFragment1 = new AttachmentFragment();
		attachmentFragment2 = new AttachmentFragment();
		addFragment(attachmentFragment1, AttachmentFragment.TAG + "1",
				R.id.fragment_attendance1);
		// addFragment(attachmentFragment2, AttachmentFragment.TAG + "2",
		// R.id.fragment_attendance2);
	}

	private void addFragment(Fragment fragment, String tag, int id) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(id, fragment, tag);
		transaction.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		FragmentManager fragmentManager;
		fragmentManager = getSupportFragmentManager();
		Fragment f = fragmentManager.findFragmentByTag(AttachmentFragment.TAG
				+ "1");
		Fragment f2 = fragmentManager.findFragmentByTag(AttachmentFragment.TAG
				+ "2");
		/* 然后在碎片中调用重写的onActivityResult方法 */
		f.onActivityResult(requestCode, resultCode, data);
		// f2.onActivityResult(requestCode, resultCode, data);
	}
}
