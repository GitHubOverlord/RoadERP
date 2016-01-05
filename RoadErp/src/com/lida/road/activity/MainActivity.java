package com.lida.road.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.jun.android_frame.activity.MainBaseActivity;
import com.jun.android_frame.constant.ResourceConstant;
import com.jun.android_frame.view.BackImageView;
import com.jun.frame.utils.GPSUtils;
import com.jun.frame.utils.SystemUtils;
import com.lida.road.R;
import com.lida.road.constant.ViewIdConstant;

/**
 * 主界面
 * 
 * @author 郑成军
 * 
 */
public class MainActivity extends MainBaseActivity {
	private GridView gridView;
	private String items[] = { "巡查考勤", "病害信息", "施工任务", "验收任务" };
	private Class clazzs[] = { AttendanceActivity.class,
			DiseaseMessageActivity.class, ConstructionDutyActivity.class,
			CheckAndAcceptDutyActivity.class };

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
			if (position == 0) {// 点击第一个按钮，我们这里是考勤，也就是GPS定位
				GPSUtils gpsUtils = new GPSUtils(MainActivity.this);
				if (!gpsUtils.checkGpsIsOpen()) {// GPS没有打开的时候
					gpsUtils.forceOpenGps();// 强制用户打开GPS
				}

			} else {
				SystemUtils.intentToAnotherActivity(MainActivity.this,
						clazzs[position]);
			}
		}
	};

}
