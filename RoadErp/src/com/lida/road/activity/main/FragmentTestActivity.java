package com.lida.road.activity.main;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.view.MyListView;
import com.lida.road.R;
import com.lida.road.view.CheckListAdapter;

public class FragmentTestActivity extends MainBaseActivity {
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		// 构造数据
				final List<String> dataList = new ArrayList<String>();
				for (int i = 0; i < 10; i++) {
					dataList.add("xxx"+i);
				}
				// 构造Adapter
				CheckListAdapter adapter = new CheckListAdapter(FragmentTestActivity.this,
						dataList);
				final MyListView listView = (MyListView) findViewById(R.id.list);
				listView.setAdapter(adapter);

				// 全部选中按钮的处理
				Button all_sel = (Button) findViewById(R.id.all_sel);
				Button all_unsel = (Button) findViewById(R.id.all_unsel);
				listView.setItemChecked(1, true);
				all_sel.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						for (int i = 0; i < dataList.size(); i++) {
							listView.setItemChecked(i, true);
						}
					}
				});

				// 全部取消按钮处理
				all_unsel.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						for (int i = 0; i < dataList.size(); i++) {
							listView.setItemChecked(i, false);
						}
					}
				});
		
	}

	
}
