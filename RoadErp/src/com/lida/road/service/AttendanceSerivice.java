package com.lida.road.service;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import com.google.gson.reflect.TypeToken;
import com.jun.android_frame.entity.BaseEntity;
import com.jun.android_frame.http.utils.HttpConnectByJson;
import com.jun.android_frame.inter.HttpConnectReciver;
import com.jun.frame.utils.GPSUtils;
import com.lida.road.constant.HTTPConstant;
import com.lida.road.entity.DiseaseRecord;
import com.loopj.android.http.RequestParams;

public class AttendanceSerivice extends Service {
	public static final String TAG = "com.lida.road.service.AttendanceSerivice";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		System.out.println(" service destory");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		System.out.println("start location service");
		GPSUtils gpsUtils = new GPSUtils(AttendanceSerivice.this);
		if (!gpsUtils.checkGpsIsOpen()) {// GPS没有打开的时候
			gpsUtils.forceOpenGps();// 强制用户打开GPS
		}
		final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		uploadLocation(locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER));
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0, new LocationListener() {

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						uploadLocation(locationManager
								.getLastKnownLocation(provider));
					}

					@Override
					public void onProviderEnabled(String provider) {
						uploadLocation(locationManager
								.getLastKnownLocation(provider));
					}

					@Override
					public void onProviderDisabled(String provider) {
						System.out.println("locatin provider disable");
					}

					@Override
					public void onLocationChanged(Location location) {
						uploadLocation(location);

					}
				});
	}

	private void uploadLocation(Location location) {
		if (location == null) {
			System.out.println("拿到的空的location");
			return;
		}
		System.out.println("update location");
		RequestParams requestParams = new RequestParams();
		requestParams.add("locationModel.longitude", location.getLongitude()
				+ "");
		requestParams
				.add("locationModel.latitude", location.getLatitude() + "");
		HttpConnectReciver<String> httpConnectReciver = new HttpConnectReciver<String>() {

			@Override
			public void onSuccess(String t, BaseEntity baseEntity) {
				int status = baseEntity.getStatus();
				if (status == 0) {
					System.out.println("上传地理信息位置失败");
				} else if (status == 1) {
					System.out.println("上传地理位置成功");
				} else if (status == 2) {
					System.out.println("没有上传地理信息位置的权限");
				} else if (status == 3) {
					System.out.println("登录过期");
				}

			}

			@Override
			public void onFail(String string) {

				System.out.println("上传地理信息位置失败");
			}

		};
		HttpConnectByJson<String> httpConnectByJson = new HttpConnectByJson<>(
				httpConnectReciver, HTTPConstant.UPLOAD_MY_LOCATION,
				requestParams, new TypeToken<List<DiseaseRecord>>() {
				}.getType(), AttendanceSerivice.this);
		httpConnectByJson.excute();
	}

}
