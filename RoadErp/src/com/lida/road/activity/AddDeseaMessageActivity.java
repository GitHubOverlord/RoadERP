package com.lida.road.activity;

import android.os.Bundle;
import android.widget.GridView;

import com.jun.android_frame.activity.MainBaseActivity;
import com.lida.road.R;
import com.lida.road.adapter.AttachmentAdapater;

public class AddDeseaMessageActivity extends MainBaseActivity {
	private GridView gridView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_disease_message);
		initView();
	}

	private void initView() {
		gridView = (GridView) findViewById(R.id.add_disease_message_attachment);
		gridView.setAdapter(new AttachmentAdapater(AddDeseaMessageActivity.this));
	}
}
