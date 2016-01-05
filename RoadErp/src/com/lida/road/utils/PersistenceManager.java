package com.lida.road.utils;

import android.content.Context;

import com.jun.frame.utils.Persistence;

/**
 * 这个类用于持久化数据的保存。
 * 
 * @author Administrator
 * 
 */
public class PersistenceManager {
	private static PersistenceManager instance;
	private Context context;
	private Persistence appPersistence;
	/**
	 * 保存的用户信息
	 */
	public static final String PERSISTENCE_USER = "user_message";
	/**
	 * 用来保存用户是否登录
	 */
	public static final String PERSISTENCE_LOGIN_STATUS = "login_status";

	/**
	 * 
	 *
	 */
	private PersistenceManager(Context context) {
		this.context = context;
	}

	public static synchronized PersistenceManager getInstance(Context context) {
		if (instance == null) {
			instance = new PersistenceManager(context);
		}
		return instance;
	}

}
