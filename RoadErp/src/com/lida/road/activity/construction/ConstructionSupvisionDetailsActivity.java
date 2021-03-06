package com.lida.road.activity.construction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.entity.BaseEntity;
import com.jun.android_frame.http.utils.AsyncUploadFiles;
import com.jun.android_frame.inter.HttpConnectReciver;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.entity.AffixFile;
import com.lida.road.entity.Construction;
import com.lida.road.entity.ConstructionAndAttachment;
import com.lida.road.fragment.AttachmentFragment;
import com.lida.road.fragment.DiseaseMessageFragment;
import com.loopj.android.http.RequestParams;

/**
 * 施工详情页面-監理
 * 
 * @author Administrator
 * 
 */
public class ConstructionSupvisionDetailsActivity extends MainBaseActivity {
	private DiseaseMessageFragment diseaseMessageFragment;
	private AttachmentFragment attachmentFragment;
	private ConstructionAndAttachment constructionAndAttachment;
	public static final String BUNDLE_DISEASE_MESSAGE = "bundle_disease_message";
	private Button bcbtn;
	private EditText cons_jlyj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_construction_supvision_details);
		initVie();
	}

	private void initVie() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "施工管理-监理意见填写");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setNormalBack(ConstructionSupvisionDetailsActivity.this);
		Bundle bundle = getIntent().getExtras();
		constructionAndAttachment = (ConstructionAndAttachment) bundle
				.getSerializable(BUNDLE_DISEASE_MESSAGE);
		cons_jlyj = (EditText) findViewById(R.id.cons_jlyj);
		String supervisionRemark = constructionAndAttachment.getConstruction()
				.getSupervisorRemark();
		cons_jlyj.setText(supervisionRemark == null ? "" : supervisionRemark);
		bcbtn = (Button) findViewById(R.id.bc_btn);
		diseaseMessageFragment = new DiseaseMessageFragment();
		Bundle diseaseBundle = new Bundle();
		diseaseBundle.putSerializable(
				DiseaseMessageFragment.BUNDLE_DISEASE_MESSAGE,
				constructionAndAttachment.getConstruction().getDiseaseRecord());
		diseaseBundle.putSerializable(
				DiseaseMessageFragment.BUNDLE_DISEASE_ATTACHMENT,
				(Serializable) constructionAndAttachment
						.getAffixDiseaseRecordList());
		diseaseMessageFragment.setArguments(diseaseBundle);
		addFragment(diseaseMessageFragment, DiseaseMessageFragment.TAG,
				R.id.fragment_construction_disease_details);
		Bundle attachBundle = new Bundle();
		attachBundle.putSerializable(AttachmentFragment.BUNDLE_IMG,
				(Serializable) constructionAndAttachment
						.getAffixSupervisorList());
		attachmentFragment = new AttachmentFragment();
		attachmentFragment.setArguments(attachBundle);
		addFragment(attachmentFragment, AttachmentFragment.TAG,
				R.id.fragment_attendance1);
		bcbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("执行保存");
				save();
			}
		});
	}

	private void save() {
		List<AffixFile> list = attachmentFragment.getImgUrls();
		if (null == list || list.size() <= 0) {
			SystemUtils.MToast("您还没选择施工附件！",
					ConstructionSupvisionDetailsActivity.this);
			return;
		}
		try {
			RequestParams params = new RequestParams();// 剔除服务器文件的url
			for (int i = 0; i < list.size(); i++) {
				String id = list.get(i).getId();
				if (id != null && !id.equals("")) {
					list.remove(i);
				}
			}
			if (null != list && list.size() > 0) {
				File[] files = new File[list.size()];
				for (int i = 0; i < list.size(); i++) {
					files[i] = new File(list.get(i).getPath());
				}
				params.put("files", files);
				String fileNames = "";
				for (int i = 0; i < files.length; i++) {
					fileNames = fileNames + files[i].getName() + ";";
				}
				params.put("fileNames", fileNames);
			}
			String deleteFileId = "";
			for (int i = 0; i < attachmentFragment.getRemoveList().size(); i++) {
				deleteFileId = deleteFileId
						+ attachmentFragment.getRemoveList().get(i).getId()
						+ ";";
			}
			params.put("deleteFilesId", deleteFileId);
			params.put("construction.id", constructionAndAttachment
					.getConstruction().getId());
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
		Fragment f = fragmentManager.findFragmentByTag(AttachmentFragment.TAG);

		/* 然后在碎片中调用重写的onActivityResult方法 */
		f.onActivityResult(requestCode, resultCode, data);
		// f2.onActivityResult(requestCode, resultCode, data);
	}
}
