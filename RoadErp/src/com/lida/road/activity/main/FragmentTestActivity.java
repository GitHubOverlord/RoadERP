package com.lida.road.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jun.android_frame.activity.MainBaseActivity;
import com.lida.road.R;
import com.lida.road.fragment.AttachmentFragment;

public class FragmentTestActivity extends MainBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		AttachmentFragment attachmentFragment = new AttachmentFragment();
		addFragment(attachmentFragment, AttachmentFragment.TAG);
	}

	private void addFragment(Fragment fragment, String tag) {
		FragmentManager manager = getSupportFragmentManager();
		FragmentTransaction transaction = manager.beginTransaction();
		transaction.add(R.id.fragment_test, fragment, tag);
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
	}
}
