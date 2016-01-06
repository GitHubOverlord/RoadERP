package com.lida.road.utils;

import android.content.Context;

import com.jun.frame.utils.Persistence;
import com.lida.road.entity.Admin;

/**
 * 这个类用于持久化数据的保存。
 * 
 * @author Administrator
 * 
 */
public class PersistenceManager {
	private static PersistenceManager instance;
	private static Persistence appPersistence;
	/**
	 * 保存的用户账号信息
	 */
	public static final String USER_NAME = "user_name";
	public static final String USER_PASSOWRD = "user_password";
	public static final String ADMIN = "admin";

	/**
	 * 用来保存用户是否登录
	 */
	public static final String PERSISTENCE_LOGIN_STATUS = "login_status";

	/**
	 * 
	 *
	 */

	public static synchronized PersistenceManager getInstance(Context context) {
		if (instance == null) {
			instance = new PersistenceManager();
			appPersistence = new Persistence(context, "ROAD_ERP_PERSISTENCE");
		}
		return instance;
	}

	/**
	 * 获取账号
	 * 
	 * @return
	 */
	public String getUserName() {
		String userName = appPersistence.get(USER_NAME, String.class);
		return userName == null ? "" : userName;
	}

	public void putUserName(String userName) {
		appPersistence.put(USER_NAME, userName);
	}

	/**
	 * 获取密码
	 * 
	 * @return
	 */
	public String getPassword() {
		String passsowrd = appPersistence.get(USER_PASSOWRD, String.class);
		return passsowrd == null ? "" : passsowrd;

	}

	public void putPassorw(String passWord) {
		appPersistence.put(USER_PASSOWRD, passWord);
	}

	public Admin getAdmin() {
		Admin admin = appPersistence.get(ADMIN, Admin.class);
		return admin;
	}

	public void putAdmin(Admin admin) {
		appPersistence.put(ADMIN, admin);
	}
}
