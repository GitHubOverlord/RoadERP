package com.lida.road.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;

import com.jun.android_frame.activity.MainBaseActivity;
import com.lida.road.R;
import com.lida.road.fragment.AttachmentFragment;

public class AddDeseaMessageActivity extends MainBaseActivity {
	private Spinner diseaseCatogegorySpinner, diseaseLeverlSpinner;
	private ListView diseaseTypeListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_disease_message);
		initView();
		AttachmentFragment attachmentFragment = new AttachmentFragment();
		addFragment(attachmentFragment, AttachmentFragment.TAG);
	}

	private void initView() {
		diseaseLeverlSpinner = (Spinner) findViewById(R.id.disease_level);
		diseaseCatogegorySpinner = (Spinner) findViewById(R.id.disease_category);
		diseaseTypeListView = (ListView) findViewById(R.id.disease_type);
	}

	private void addFragment(Fragment fragment, String tag) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.fragment_attendance, fragment, tag);
		transaction.commit();
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			switch (view.getId()) {
			case R.id.disease_category:

				break;
			case R.id.disease_level:
				
				break;

			default:
				break;
			}

		}

	};

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
