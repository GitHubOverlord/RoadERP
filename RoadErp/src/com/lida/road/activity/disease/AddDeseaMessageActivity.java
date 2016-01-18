package com.lida.road.activity.disease;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.StringsNotNull;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.constant.UserConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.entity.DiseaseRecord;
import com.lida.road.fragment.AttachmentFragment;
import com.lida.road.utils.PersistenceManager;
import com.lida.road.utils.StringAdapter;
import com.lida.road.view.CheckListAdapter;
import com.lida.road.view.ChoiceDiseaseTypeDialog;

/**
 * 通过这个页面，我们编辑，保存，新建一个病害信息
 * 
 * @author Administrator
 * 
 */
public class AddDeseaMessageActivity extends MainBaseActivity {
	/**
	 * 定义填写的表单EditText
	 */
	private EditText diseaseNumberEditText, roadNumberEditText,
			stakeNumberEditText, suggestFixEditText, workDataEditText,
			measurementUnitEditText, estimatedAmountEditText, phoneEditText,
			remarkEditText;
	/**
	 * 定义下拉选项
	 */
	private Spinner diseaseLevelSpinner, diseaseCatogegorySpinner,
			diseasePositionSpinner;
	/**
	 * 定义文本域
	 */
	private TextView peopleNameTextView, peopleBelongEnterprisTextView,showDiseaseTypeTextVeiw;
	/**
	 * 定义时间选择框
	 */
	private DatePicker datePicker;
	/**
	 * Spinner的值
	 */
	private String spinnerDiseaseLevelString[], spinnerDiseaseCatogoryString[],
			spinnerDiseasePositionString[];
	private List<String> spinnerDiseaseTypes;
	private Button saveButton, reportButton, choiceDiseaseTypeButton;
	/**
	 * 用户选择病害信息类型
	 */
	private AttachmentFragment attachmentFragment;

	/**
	 * 通过bundle传值，我们知道从哪个页面来的
	 */
	public static final String Bundle_FROM_MARK = "bundle_from_mark";
	/**
	 * 从新增页面来的,我们不需要管缓存，也不需要管服务器上面的值。所有的填写都是初始化的
	 * 
	 */
	public static final int BUNDLE_FROM_ADD = 1;
	/**
	 * 从缓存页面来的，我们需要设置一些初始值
	 */
	public static final int BUNDLE_FROM_CACHE = 2;
	/**
	 * 从服务器拿到数据，然后要更新的
	 */
	public static final int BUNDLE_FROM_SERVICE = 3;
	public static final String BUNDLE_DISEASA_MESSAGE = "diseaseMessage";
	private DiseaseRecord cacheDiseaseRecord;
	private CheckListAdapter checkListAdapter;
	private boolean isSelectFirstTimeSelect = true;
	private ChoiceDiseaseTypeDialog choiceDiseaseTypeDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_disease_message);
		initView();

	}

	/**
	 * 初始化控件
	 */
	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "添加病害信息");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(AddDeseaMessageActivity.this);
		showDiseaseTypeTextVeiw =(TextView)findViewById(R.id.tv_disease_type_value);
		choiceDiseaseTypeButton = (Button) findViewById(R.id.btn_choice_type);
		diseaseLevelSpinner = (Spinner) findViewById(R.id.disease_level);
		diseaseCatogegorySpinner = (Spinner) findViewById(R.id.disease_category);
		diseaseNumberEditText = (EditText) findViewById(R.id.et_disease_number);
		roadNumberEditText = (EditText) findViewById(R.id.disease_road_number);
		remarkEditText = (EditText) findViewById(R.id.remark);
		stakeNumberEditText = (EditText) findViewById(R.id.et_stake_number);
		diseasePositionSpinner = (Spinner) findViewById(R.id.disease_position);
		suggestFixEditText = (EditText) findViewById(R.id.suggestion_fix);
		workDataEditText = (EditText) findViewById(R.id.work_date);
		measurementUnitEditText = (EditText) findViewById(R.id.measurement_unit);
		estimatedAmountEditText = (EditText) findViewById(R.id.estimated_amount);
		datePicker = (DatePicker) findViewById(R.id.date_picker);
		peopleNameTextView = (TextView) findViewById(R.id.people_name);
		peopleBelongEnterprisTextView = (TextView) findViewById(R.id.people_belong_enterpris_textView);
		phoneEditText = (EditText) findViewById(R.id.phone_number);
		saveButton = (Button) findViewById(R.id.btn_keep);
		reportButton = (Button) findViewById(R.id.btn_report);
		saveButton.setOnClickListener(listener);
		reportButton.setOnClickListener(listener);
		choiceDiseaseTypeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String catogory = diseaseCatogegorySpinner.getSelectedItem()
						.toString();
				if (catogory == null || catogory.equals("")) {
					SystemUtils.MToast("您还没选择病害类型", AddDeseaMessageActivity.this);
					return;
				}
				choiceDiseaseTypeDialog = new ChoiceDiseaseTypeDialog(
						AddDeseaMessageActivity.this,showDiseaseTypeTextVeiw);
				choiceDiseaseTypeDialog.show(catogory,showDiseaseTypeTextVeiw.getText().toString());
				
			}
		});
		/**
		 * 初始化spinner数据
		 */
		spinnerDiseaseLevelString = new String[] { "严重", "轻" };
		spinnerDiseasePositionString = new String[] { "左", "右" };
		spinnerDiseaseCatogoryString = StringAdapter
				.getAllDiseaseCatogries(AddDeseaMessageActivity.this);
		/**
		 * 设置适配器
		 */
		setSpinnerStringAdapter(diseaseCatogegorySpinner,
				spinnerDiseaseCatogoryString);
		setSpinnerStringAdapter(diseaseLevelSpinner, spinnerDiseaseLevelString);
		setSpinnerStringAdapter(diseasePositionSpinner,
				spinnerDiseasePositionString);
		/**
		 * 设置初始化默认值
		 */
		peopleNameTextView.setText(UserConstant.getAdmin(
				AddDeseaMessageActivity.this).getName());// 设置用户名
		peopleBelongEnterprisTextView.setText(UserConstant.getAdmin(
				AddDeseaMessageActivity.this)// 设置用户所属的单位
				.getMaintainPost());
		/**
		 * 根据跳转的页面判断，是否要加载缓存数据
		 */
		attachmentFragment = new AttachmentFragment();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			int fromActivityMark = bundle.getInt(Bundle_FROM_MARK);
			if (fromActivityMark == BUNDLE_FROM_CACHE) {// 如果是从缓存页面来的话
				cacheDiseaseRecord = (DiseaseRecord) bundle
						.getSerializable(BUNDLE_DISEASA_MESSAGE);
				cacheDiseaseRecord = PersistenceManager.getInstance(
						AddDeseaMessageActivity.this).getDiseaseByCacheId(
						cacheDiseaseRecord.getLocalCacheId());
				if (cacheDiseaseRecord != null) {
					setDiseaseMessageToView(cacheDiseaseRecord);
					Bundle bundle2 = new Bundle();
					bundle2.putSerializable(AttachmentFragment.BUNDLE_IMG,
							(Serializable) cacheDiseaseRecord
									.getAttachmentUrls());
					attachmentFragment.setArguments(bundle2);
				}
			}
		}
		addFragment(attachmentFragment, AttachmentFragment.TAG);// 增加附件fragment
		diseaseCatogegorySpinner
				.setOnItemSelectedListener(onItemSelectedListener);
	}

	/**
	 * 根据String[]设置Spinner值的方法
	 * 
	 * @param spinner
	 * @param strings
	 */
	private void setSpinnerStringAdapter(Spinner spinner, String[] strings) {
		spinner.setAdapter(new ArrayAdapter<String>(
				AddDeseaMessageActivity.this,
				android.R.layout.simple_spinner_dropdown_item, strings));
	}

	/**
	 * 把缓存的值或者服务器的值放到这个页面上去
	 */
	private void setDiseaseMessageToView(DiseaseRecord diseaseRecord) {
		if (diseaseRecord == null) {
			return;
		}
		SystemUtils.ifEditTextSetNullString(diseaseNumberEditText,
				diseaseRecord.getSn());
		SystemUtils.ifEditTextSetNullString(roadNumberEditText,
				diseaseRecord.getRouteCode());
		SystemUtils.ifEditTextSetNullString(stakeNumberEditText,
				diseaseRecord.getStake() + "");
		SystemUtils.ifTextSetNullString(showDiseaseTypeTextVeiw, diseaseRecord.getDiseaseType());
		/**
		 * /设置病害等级Spinner
		 */
		String cacheDiseaseLevel = diseaseRecord.getDiseaseLevel();
		if (cacheDiseaseLevel != null && !cacheDiseaseLevel.equals("")) {
			int position = -1;
			for (int i = 0; i < spinnerDiseaseLevelString.length; i++) {
				if (cacheDiseaseLevel.equals(spinnerDiseaseLevelString[i])) {
					position = i;
				}
			}
			if (position != -1) {
				diseaseLevelSpinner.setSelection(position);
			}
		}
		/**
		 * 设置病害部位catogory
		 */
		String cacheDiseaseCatogory = diseaseRecord.getDiseasePart();
		if (cacheDiseaseCatogory != null && !cacheDiseaseCatogory.equals("")) {
			int position = -1;
			for (int i = 0; i < spinnerDiseaseCatogoryString.length; i++) {
				if (cacheDiseaseCatogory
						.equals(spinnerDiseaseCatogoryString[i])) {
					position = i;
				}
			}
			if (position != -1) {
				diseaseCatogegorySpinner.setSelection(position);
			}
		}
		/**
		 * 设置病害位置
		 */
		String cacheDiseasePostion = diseaseRecord.getDiseasePosition();
		if (cacheDiseasePostion != null && !cacheDiseasePostion.equals("")) {
			int position = -1;
			for (int i = 0; i < spinnerDiseasePositionString.length; i++) {
				if (cacheDiseaseCatogory
						.equals(spinnerDiseasePositionString[i])) {
					position = i;
				}
			}
			if (position != -1) {
				diseasePositionSpinner.setSelection(position);
			}
		}
		SystemUtils.ifEditTextSetNullString(suggestFixEditText,
				diseaseRecord.getEstimatingScheme());
		SystemUtils.ifEditTextSetNullString(workDataEditText,
				diseaseRecord.getEstimatingJob() + "");
		SystemUtils.ifEditTextSetNullString(measurementUnitEditText,
				diseaseRecord.getEstimatingUnit());
		SystemUtils.ifEditTextSetNullString(estimatedAmountEditText,
				diseaseRecord.getEstimatingCost() + "");
		SystemUtils.ifEditTextSetNullString(phoneEditText,
				diseaseRecord.getReportorPhone());
		SystemUtils.ifEditTextSetNullString(remarkEditText,
				diseaseRecord.getRemark());

	}

	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			String diseaseNumberString, routeNumberString, stakeNumberString, diseaseLevel, diseaseCatogory, diseasePosition, suggestFixString, workDateString, measurementUnitString, estimatedAmountString, date, phoneNumberString, remark, localId;
			diseaseNumberString = diseaseNumberEditText.getEditableText()
					.toString();
			routeNumberString = roadNumberEditText.getEditableText().toString();
			stakeNumberString = stakeNumberEditText.getEditableText()
					.toString();
			diseaseLevel = diseaseLevelSpinner.getSelectedItem().toString();
			diseaseCatogory = diseaseCatogegorySpinner.getSelectedItem()
					.toString();
			/**
			 * 被选中的checkListView
			 */
			diseasePosition = diseasePositionSpinner.getSelectedItem()
					.toString();
			suggestFixString = suggestFixEditText.getEditableText().toString();
			workDateString = workDataEditText.getEditableText().toString();
			measurementUnitString = measurementUnitEditText.getEditableText()
					.toString();
			estimatedAmountString = estimatedAmountEditText.getEditableText()
					.toString();
			date = datePicker.getYear() + "-" + datePicker.getMonth() + "-"
					+ datePicker.getDayOfMonth();
			phoneNumberString = phoneEditText.getEditableText().toString();
			remark = remarkEditText.getEditableText().toString();
			if (cacheDiseaseRecord == null) {
				localId = UUID.randomUUID().toString();
			} else {
				localId = cacheDiseaseRecord.getLocalCacheId();
			}
			String checkDiseaseType = showDiseaseTypeTextVeiw.getText().toString();
			System.out.println(checkDiseaseType.toString());
			boolean notNull = StringsNotNull.judje(checkDiseaseType,
					diseaseNumberString, routeNumberString, stakeNumberString,
					diseaseLevel, diseaseCatogory, diseasePosition,
					suggestFixString, workDateString, measurementUnitString,
					estimatedAmountString, date, phoneNumberString, remark,
					localId);
			if (!notNull) {
				SystemUtils.MToast("请填写完整，包括文字和图片",
						AddDeseaMessageActivity.this);
				return;
			}
			if (attachmentFragment.getImgUrls() == null
					|| attachmentFragment.getImgUrls().size() <= 0) {
				SystemUtils.MToast("请填写完整，包括文字和图片",
						AddDeseaMessageActivity.this);
				return;
			}
			DiseaseRecord upDiseaseRecord = new DiseaseRecord("",
					diseaseNumberString, routeNumberString,
					Double.valueOf(stakeNumberString), diseaseLevel,
					diseaseCatogory, checkDiseaseType, diseasePosition,
					suggestFixString, Double.valueOf(workDateString),
					measurementUnitString,
					Double.valueOf(estimatedAmountString), date, UserConstant
							.getAdmin(AddDeseaMessageActivity.this).getOrg()
							.getName(), UserConstant
							.getAdmin(AddDeseaMessageActivity.this).getOrg()
							.getId(), UserConstant.getAdmin(
							AddDeseaMessageActivity.this).getId(), UserConstant
							.getAdmin(AddDeseaMessageActivity.this)
							.getUsername(), phoneNumberString, "", "", remark,
					"", "", localId, attachmentFragment.getImgUrls());
			switch (arg0.getId()) {
			case R.id.btn_keep:
				PersistenceManager.getInstance(AddDeseaMessageActivity.this)
						.updateOrSaveDiseaseRecord(upDiseaseRecord);
				SystemUtils.MToast("保存成功", AddDeseaMessageActivity.this);
				break;
			case R.id.btn_report:
				intentToReportActivity(upDiseaseRecord);
				break;
			default:
				break;
			}

		}

	};

	/**
	 * 跳转到报告页面的方法
	 */
	private void intentToReportActivity(DiseaseRecord diseaseRecord) {
		Intent intent = new Intent();
		intent.setClass(AddDeseaMessageActivity.this,
				DiseaseReportActivity.class);
		Bundle bundle = new Bundle();
		bundle.putInt(DiseaseReportActivity.BUNDLE_MARK,
				DiseaseReportActivity.BUNDLE_FROM_DISEASE_EDIT);
		bundle.putSerializable(
				DiseaseReportActivity.BUNDLE_VALUE_DISEASE_MESSAGE,
				diseaseRecord);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	/**
	 * 增加一个fragment的方法
	 * 
	 * @param fragment
	 * @param tag
	 */
	private void addFragment(Fragment fragment, String tag) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.fragment_attendance, fragment, tag);
		transaction.commit();
	}

	OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			if (isSelectFirstTimeSelect) {
				isSelectFirstTimeSelect = false;
				return;
			}
			showDiseaseTypeTextVeiw.setText("");
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};


	/**
	 * 这里是把result结果映射到图片选择的fragment里面去
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		FragmentManager fragmentManager;
		fragmentManager = getSupportFragmentManager();
		Fragment f = fragmentManager.findFragmentByTag(AttachmentFragment.TAG);
		/* 然后在碎片中调用重写的onActivityResult方法 */
		f.onActivityResult(requestCode, resultCode, data);
	}
}
