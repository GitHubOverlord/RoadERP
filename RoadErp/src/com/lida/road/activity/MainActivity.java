package com.lida.road.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.constant.ViewIdConstant;
import com.lida.road.service.AttendanceSerivice;

/**
 * 主界面
 * 
 * @author 郑成军
 * 
 */
public class MainActivity extends MainBaseActivity {
	private GridView gridView;
	private String items[] = { "巡查考勤", "病害信息", "施工任务", "验收任务", "测试" };
	private Class<?> clazzs[] = { AttendanceActivity.class,
			DiseaseMessageActivity.class, ConstructionDutyActivity.class,
			CheckAndAcceptDutyActivity.class, FragmentTestActivity.class };
	private static boolean isUploadingLocation = false;
	private Intent uploadLocatinServiceIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		setActionBar(R.layout.include_head_textbtn);
		setActionBarWidgetResource(ViewIdConstant.ACTIONBAR_TITLE,
				ResourceConstant.ACTIONBAR_TITLE, "首页");
		BackImageView backImageView = (BackImageView) getActionBarViewByMarkId(
				ViewIdConstant.ACTIONBAR_BACK_IAMGEVIEW,
				ResourceConstant.ACTIONBAR_BACK_IMAGEVIEW);
		backImageView.setVisibility(View.GONE);
		List<Map<String, Object>> maps = new ArrayList<>();
		for (String string : items) {
			HashMap<String, Object> map = new HashMap<>();
			map.put("name", string);
			maps.add(map);
		}
		gridView = (GridView) findViewById(R.id.main_gridView);
		gridView.setAdapter(new SimpleAdapter(MainActivity.this, maps,
				R.layout.gridview_main, new String[] { "name" },
				new int[] { R.id.gridview_main_name }));
		gridView.setOnItemClickListener(onItemClickListener);
	}

	OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			TextView textView = (TextView) view
					.findViewById(R.id.gridview_main_name);
			if (position == 0) {// 点击第一个按钮，我们这里是考勤，也就是GPS定位
				uploadLocatinServiceIntent = new Intent();
				uploadLocatinServiceIntent.setAction(AttendanceSerivice.TAG);
				uploadLocatinServiceIntent.setPackage(getPackageName());
				if (isUploadingLocation) {
					textView.setText("巡查考勤");
					isUploadingLocation = false;
					stopService(uploadLocatinServiceIntent);

				} else {
					textView.setText("正在考勤...");
					isUploadingLocation = true;
					startService(uploadLocatinServiceIntent);
				}

			} else {
				SystemUtils.intentToAnotherActivity(MainActivity.this,
						clazzs[position]);
			}
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (uploadLocatinServiceIntent != null) {
			stopService(uploadLocatinServiceIntent);
		}
	}
}
