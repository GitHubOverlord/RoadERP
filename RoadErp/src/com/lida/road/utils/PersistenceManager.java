package com.lida.road.utils;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.jun.frame.utils.Persistence;
import com.lida.road.entity.Admin;
import com.lida.road.entity.DiseaseCategory;
import com.lida.road.entity.Construction;
import com.lida.road.entity.DiseaseRecord;
import com.lida.road.entity.DiseaseType;

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
	public static final String HOST_IP = "host_ip";
	public static final String HOST_PORT = "host_port";
	public static final String DISEASE_CACHE = "disease_cache";
	public static final String DISEASE_CATOGORY = "disease_catogory";
	public static final String DISEASE_TYPE = "disease_type";
	public static final String CONSTRUCTION_CACHE = "construction_cache";
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

	/**
	 * 保存Admin
	 * 
	 * @return
	 */
	public Admin getAdmin() {
		Admin admin = appPersistence.get(ADMIN, Admin.class);
		return admin;
	}

	public void putAdmin(Admin admin) {
		appPersistence.put(ADMIN, admin);
	}

	/**
	 * 保存HostIP
	 * 
	 * @return
	 */
	public String getHostIp() {
		String hostIp = appPersistence.get(HOST_IP, String.class);
		return hostIp == null ? "" : hostIp;
	}

	public void putHostIp(String hostIp) {
		appPersistence.put(HOST_IP, hostIp);

	}

	/**
	 * 保存端口号
	 * 
	 * @return
	 */
	public String getHostPort() {
		String hostPort = appPersistence.get(HOST_PORT, String.class);
		return hostPort == null ? "" : hostPort;
	}

	public void putHostPort(String hostPort) {
		appPersistence.put(HOST_PORT, hostPort);

	}

	/**
	 * 保存DiseaseRecord信息
	 */
	public List<DiseaseRecord> getDiseaseRecords() {
		List<DiseaseRecord> diseaseRecords = appPersistence.get(DISEASE_CACHE,
				new TypeToken<List<DiseaseRecord>>() {
				}.getType());
		return diseaseRecords == null ? new ArrayList<DiseaseRecord>()
				: diseaseRecords;

	}
	public void updateOrSaveDiseaseRecord(DiseaseRecord diseaseRecord) {
		List<DiseaseRecord> diseaseRecords = getDiseaseRecords();
		for (DiseaseRecord diseaseRecord2 : diseaseRecords) {
			if (diseaseRecord2.getLocalCacheId().equals(
					diseaseRecord.getLocalCacheId())) {
				diseaseRecords.remove(diseaseRecord2);
			}
		}
		diseaseRecords.add(diseaseRecord);
		appPersistence.remove(DISEASE_CACHE);
		appPersistence.put(DISEASE_CACHE, diseaseRecords);
	}
	/**
	 * 保存病害部位
	 */
	public void putDiseaseCatogory(List<DiseaseCategory> list) {
		appPersistence.put(DISEASE_CATOGORY,
				list);
	}
	public List<DiseaseCategory> getDiseaseCatogory(){
		List<DiseaseCategory> diseaseCategories = appPersistence.get(DISEASE_CATOGORY,
				new TypeToken<List<DiseaseCategory>>() {
				}.getType());
		return diseaseCategories == null ? new ArrayList<DiseaseCategory>()
				: diseaseCategories;
	}
/**
 * 保存病害类型
 */
	public void putDiseaseType(List<DiseaseType> list) {
		appPersistence.put(DISEASE_TYPE,
				list);
	}
	public List<DiseaseType> getDiseaseType(){
		List<DiseaseType> diseaseTypes = appPersistence.get(DISEASE_TYPE,
				new TypeToken<List<DiseaseType>>() {
				}.getType());
		return diseaseTypes == null ? new ArrayList<DiseaseType>()
				: diseaseTypes;
	}
	public Construction getConstruction(){
		Construction construction= appPersistence.get(CONSTRUCTION_CACHE, Construction.class);
		return construction;
	}
	public void putConstruction(Construction construction){
		appPersistence.remove(CONSTRUCTION_CACHE);
		appPersistence.put(CONSTRUCTION_CACHE, construction);
	}

	public DiseaseRecord getDiseaseByCacheId(String localCacheId) {
		DiseaseRecord diseaseRecord = null;
		List<DiseaseRecord> diseaseRecords = getDiseaseRecords();
		for (DiseaseRecord diseaseRecord2 : diseaseRecords) {
			if (diseaseRecord2.getLocalCacheId().equals(localCacheId)) {
				diseaseRecord = diseaseRecord2;
			}
		}
		return diseaseRecord;
	}
}
