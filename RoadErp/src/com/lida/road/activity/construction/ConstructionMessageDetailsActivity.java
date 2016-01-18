package com.lida.road.activity.construction;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.entity.BaseEntity;
import com.jun.android_frame.http.base.BaseConnectTemplet;
import com.jun.android_frame.inter.HttpConnectReciver;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.entity.Construction;
import com.lida.road.entity.ConstructionAndAttachment;
import com.lida.road.fragment.ConstructionMessageFragment;
import com.lida.road.fragment.DiseaseMessageFragment;
import com.loopj.android.http.RequestParams;

/**
 * 这是所有人都能看到的病害详情页面
 * 
 * @author Administrator
 * 
 */
public class ConstructionMessageDetailsActivity extends MainBaseActivity {
	/**
	 * 从哪个页面来的标志
	 */
	public static final String BUNDLE_MARK = "bundle_mark";
	/**
	 * 表示从所有施工列表页面来
	 */
	public static final int BUNDLE_FROM_ALL_CONSTRUCTION = 1;
	public static final String BUNDLE_VALUE_CONSTRUCTION = "bundle_value_construction";
	private Construction construction;
	private int mark;
	private DiseaseMessageFragment diseaseMessageFragment;
	private ConstructionMessageFragment constructionMessageFragment;
	private TextView supervisionMarkTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_construction_message_details);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "施工任务详情");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(ConstructionMessageDetailsActivity.this);
		supervisionMarkTextView = (TextView) findViewById(R.id.tv_supervision_mark);
		Bundle bundle = getIntent().getExtras();
		mark = bundle.getInt(BUNDLE_MARK);
		if (mark == BUNDLE_FROM_ALL_CONSTRUCTION) {
			construction = (Construction) bundle
					.getSerializable(BUNDLE_VALUE_CONSTRUCTION);
		}
		getData();
	}

	private void getData() {
		RequestParams requestParams = new RequestParams();
		requestParams.add("construction.id", construction.getId());
		BaseConnectTemplet<ConstructionAndAttachment> baseConnectTemplet = new BaseConnectTemplet<>(
				ConstructionMessageDetailsActivity.this, "提示", "正在获取数据",
				httpConnectReciver, requestParams,
				HTTPConstant.GET_CONSTRUCTION_DETAILS_BY_ID,
				new TypeToken<ConstructionAndAttachment>() {
				}.getType());
		baseConnectTemplet.setProgressViewCanCancel();
		baseConnectTemplet.getData();
	}

	HttpConnectReciver<ConstructionAndAttachment> httpConnectReciver = new HttpConnectReciver<ConstructionAndAttachment>() {

		@Override
		public void onSuccess(ConstructionAndAttachment t, BaseEntity baseEntity) {
			int status = baseEntity.getStatus();
			if (status != 1) {
				SystemUtils.MToast("获取数据失败！",
						ConstructionMessageDetailsActivity.this);
				return;
			}
			diseaseMessageFragment = new DiseaseMessageFragment();
			Bundle bundle = new Bundle();
			bundle.putSerializable(
					DiseaseMessageFragment.BUNDLE_DISEASE_MESSAGE, t
							.getConstruction().getDiseaseRecord());
			diseaseMessageFragment.setArguments(bundle);
			addFragment(diseaseMessageFragment, DiseaseMessageFragment.TAG,
					R.id.fragment_construction_disease_details);
			constructionMessageFragment = new ConstructionMessageFragment(
					t.getConstruction(), t.getAffixConstructionList(),
					t.getAffixSupervisorList());
			addFragment(constructionMessageFragment,
					ConstructionMessageFragment.TAG,
					R.id.fragment_construction_details);
			String supervisionMarkString = t.getConstruction()
					.getSupervisorRemark();
			supervisionMarkTextView.setText(supervisionMarkString == null ? ""
					: supervisionMarkString);
			diseaseMessageFragment.openDiseaseMessage();
			constructionMessageFragment.openConstructionMessage();
		}

		@Override
		public void onFail(String string) {
			SystemUtils.MToast("连接服务器失败！",
					ConstructionMessageDetailsActivity.this);

		}
	};

	private void addFragment(Fragment fragment, String tag, int id) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(id, fragment, tag);
		transaction.commit();
	}
}
