package com.lida.road.entity;


/***
 * 日常养护常量类
 * @author linniantai
 *
 */
public class MainTainFlowContent {
	/**角色-养护站<巡查员>*/
	public static final String ROLE_STATION_PATROL="stationPatrol";
	/**角色-养护站<站长>*/
	public static final String ROLE_STATION_MANAGER="stationManager";
	/**角色-县局<巡查员>*/
	public static final String ROLE_COUNTY_WORK_PATROL="countyWorkPatrol";
	/**角色-县局<工务科科长>*/
	public static final String ROLE_COUNTY_WORK_BRANCH="countyWorkBranch";
	/**角色-县局<主管领导>*/
	public static final String ROLE_COUNTY_WORK_MANAGER="countyWorkManager";
	/**角色-市局<巡查员>*/
	public static final String ROLE_CITY_PATROL="cityPatrol";
	/**角色-市局<工务科>*/
	public static final String ROLE_CITY_BRANCH="cityBranch";
	/**角色-市局<主管领导>*/
	public static final String ROLE_CITY_MANAGER="cityManager";
	/**角色-施工*/
	public static final String ROLE_CONSTRUCTION="construction";
	/**角色-监理*/
	public static final String ROLE_SUPERVISION="supervision";
	
	/**养护病害流程状态-未上报*/
	public static final String DISEASE_FLOWSTATUS_NOREPORT="0";
	/**养护病害流程状态-站长审批*/
	public static final String DISEASE_FLOWSTATUS_STATION_MASTER="1";
	/**养护病害流程状态-市局<工务科>*/
	public static final String DISEASE_FLOWSTATUS_CITY_BRANCH="3";
	/**养护病害流程状态-市局<主管领导>*/
	public static final String DISEASE_FLOWSTATUS_CITY_MASTER="4";
	/**养护病害流程状态-县局<工务科>审批*/
	public static final String DISEASE_FLOWSTATUS_WORK_BRANCH="8";
	/**养护病害流程状态-县局<主管领导>审批*/
	public static final String DISEASE_FLOWSTATUS_WORK_MASTER="9";
	/**养护病害流程状态-审批结束*/
	public static final String DISEASE_FLOWSTATUS_FINISH="999";
	
	/***
	 * 根据病害审批状态返回审批环节名称
	 * @param status
	 * @return
	 */
	public static String getDiseaseFlowStatusNameByStr(String status){
		switch (status) {
		case DISEASE_FLOWSTATUS_NOREPORT:
			return "未上报";
		case DISEASE_FLOWSTATUS_STATION_MASTER:
			return "养护站<站长>审批";
		case DISEASE_FLOWSTATUS_CITY_BRANCH:
			return "市局<工务科科长>审批";
		case DISEASE_FLOWSTATUS_CITY_MASTER:
			return "市局<主管领导>审批";
		case DISEASE_FLOWSTATUS_WORK_BRANCH:
			return "县局<工务科科长>审批";
		case DISEASE_FLOWSTATUS_WORK_MASTER:
			return "县局<主管领导>审批";
		case DISEASE_FLOWSTATUS_FINISH:
			return "审批结束";
		default:
			return "未上报";
		}
	}
	
	/***
	 * 根据当前病害流程状态，返回下一步环节的流程状态
	 * 1、养护站<巡查员>  -->   养护站<站长>   -->   县局<工务科科长>审批   -->   县局<主管领导>审批
	 * 2、县局<巡查员>   -->   县局<工务科科长>审批   -->   县局<主管领导>审批
	 * 3、市局工务科<巡查员>   -->   市局工务科<科长>   -->   市局<主管领导>   -->    县局<工务科科长>审批（只能同意）   -->   县局<主管领导>审批
	 */
	public static final String getNextDiseaseFlowStatusByStatusAndFrom(boolean yesOrNo,String status,String from){
		//1、养护站<巡查员>  -->   养护站<站长>   -->   县局<工务科科长>审批   -->   县局<主管领导>审批
		//2、县局<巡查员>   -->   县局<工务科科长>审批   -->   县局<主管领导>审批
		//3、市局工务科<巡查员>   -->   市局工务科<科长>   -->   市局<主管领导>   -->    县局<工务科科长>审批（只能同意）   -->   县局<主管领导>审批
		if (DISEASE_FROM_STATION.equals(from)){
			switch (status) {
			//养护站<巡查员>
			case DISEASE_FLOWSTATUS_NOREPORT:
				if (yesOrNo){
					//下一环节为养护站<站长>
					return DISEASE_FLOWSTATUS_STATION_MASTER;
				} else {
					//此环节没有退回
					return DISEASE_FLOWSTATUS_NOREPORT;
				}
			//养护站<站长>
			case DISEASE_FLOWSTATUS_STATION_MASTER:
				if (yesOrNo){
					return DISEASE_FLOWSTATUS_WORK_BRANCH;
				} else {
					return DISEASE_FLOWSTATUS_NOREPORT;
				}
			//县局<工务科科长>审批
			case DISEASE_FLOWSTATUS_WORK_BRANCH:
				if (yesOrNo){
					return DISEASE_FLOWSTATUS_WORK_MASTER;
				} else {
					return DISEASE_FLOWSTATUS_STATION_MASTER;
				}
			//县局<主管领导>审批
			case DISEASE_FLOWSTATUS_WORK_MASTER:
				if (yesOrNo){
					return DISEASE_FLOWSTATUS_FINISH;
				} else {
					return DISEASE_FLOWSTATUS_WORK_BRANCH;
				}
			default:
				return DISEASE_FLOWSTATUS_NOREPORT;
			}
		} else if (DISEASE_FROM_COUNTY.equals(from)){
			//2、县局<巡查员>   -->   县局<工务科科长>审批   -->   县局<主管领导>审批
			switch (status) {
			//县局<巡查员>
			case DISEASE_FLOWSTATUS_NOREPORT:
				if (yesOrNo){
					//下一环节为县局<工务科科长>审批
					return DISEASE_FLOWSTATUS_WORK_BRANCH;
				} else {
					//此环节没有退回
					return DISEASE_FLOWSTATUS_NOREPORT;
				}
			//县局<工务科科长>审批
			case DISEASE_FLOWSTATUS_WORK_BRANCH:
				if (yesOrNo){
					return DISEASE_FLOWSTATUS_WORK_MASTER;
				} else {
					return DISEASE_FLOWSTATUS_NOREPORT;
				}
			//县局<主管领导>审批
			case DISEASE_FLOWSTATUS_WORK_MASTER:
				if (yesOrNo){
					return DISEASE_FLOWSTATUS_FINISH;
				} else {
					return DISEASE_FLOWSTATUS_WORK_BRANCH;
				}
			default:
				return DISEASE_FLOWSTATUS_NOREPORT;
			}
		} else if (DISEASE_FROM_CITY.equals(from)){
			//3、市局工务科<巡查员>   -->   市局工务科<科长>   -->   市局<主管领导>   -->    县局<工务科科长>审批   -->   县局<主管领导>审批
			switch (status) {
			//市局工务科<巡查员>
			case DISEASE_FLOWSTATUS_NOREPORT:
				if (yesOrNo){
					return DISEASE_FLOWSTATUS_CITY_BRANCH;
				} else {
					return DISEASE_FLOWSTATUS_NOREPORT;
				}
			//市局工务科<科长>
			case DISEASE_FLOWSTATUS_CITY_BRANCH:
				if (yesOrNo){
					return DISEASE_FLOWSTATUS_CITY_MASTER;
				} else {
					return DISEASE_FLOWSTATUS_NOREPORT;
				}
			case DISEASE_FLOWSTATUS_CITY_MASTER:
				// 市局<主管领导>;
				if (yesOrNo){
					return DISEASE_FLOWSTATUS_WORK_BRANCH;
				} else {
					return DISEASE_FLOWSTATUS_CITY_BRANCH;
				}
			case DISEASE_FLOWSTATUS_WORK_BRANCH:
				//县局<工务科科长>审批
				if (yesOrNo){
					return DISEASE_FLOWSTATUS_WORK_MASTER;
				} else {
					//市局的不能拒绝不能返回
					return DISEASE_FLOWSTATUS_WORK_BRANCH;
				}
				//return "县局<工务科科长>审批";
			case DISEASE_FLOWSTATUS_WORK_MASTER:
				//"县局<主管领导>审批";
				if (yesOrNo){
					return DISEASE_FLOWSTATUS_FINISH;
				} else {
					//市局的不能拒绝不能返回
					return DISEASE_FLOWSTATUS_WORK_BRANCH;
				}
			case DISEASE_FLOWSTATUS_FINISH:
				return DISEASE_FLOWSTATUS_FINISH;
			default:
				return DISEASE_FLOWSTATUS_NOREPORT;
			}
		} else {
			return DISEASE_FLOWSTATUS_NOREPORT;
		}
	}
	
	/**病害来源-养护站*/
	public static final String DISEASE_FROM_STATION="station";
	/**病害来源-市局<工务科>*/
	public static final String DISEASE_FROM_CITY="city";
	/**病害来源-县局<工务科>*/
	public static final String DISEASE_FROM_COUNTY="county";
	
	/**施工单状态-未上报*/
	public static final String CONSTRUCTION_FLOWSTATUS_NOREPORT="0";
	/**施工单状态-县局<工务科>审批*/
	public static final String CONSTRUCTION_FLOWSTATUS_WORK_MASTER="1";
	/**施工单状态-施工中*/
	public static final String CONSTRUCTION_FLOWSTATUS_WORKING="2";
	/**施工单状态-已完成*/
	public static final String CONSTRUCTION_FLOWSTATUS_WORKED="999";
	
	/***
	 * 根据当前病害流程状态，返回下一步环节的流程状态
	 * 1、县局<工务科科长>新增   -->   县局<主管领导>审批 --> 施工单位结束
	 */
	public static final String getNextConstructionFlowStatusByStatusAndFrom(boolean yesOrNo,String status){
		switch (status) {
		case CONSTRUCTION_FLOWSTATUS_NOREPORT:
			if (yesOrNo){
				return CONSTRUCTION_FLOWSTATUS_WORK_MASTER;
			} else {
				return CONSTRUCTION_FLOWSTATUS_NOREPORT;
			}
		case CONSTRUCTION_FLOWSTATUS_WORK_MASTER:
			if (yesOrNo){
				return CONSTRUCTION_FLOWSTATUS_WORKING;
			} else {
				return CONSTRUCTION_FLOWSTATUS_NOREPORT;
			}
		case CONSTRUCTION_FLOWSTATUS_WORKING:
			if (yesOrNo){
				return CONSTRUCTION_FLOWSTATUS_WORKED;
			} else {
				return CONSTRUCTION_FLOWSTATUS_WORK_MASTER;
			}
		default:
			return CONSTRUCTION_FLOWSTATUS_NOREPORT;
		}
	}
	
	/**验收流程状态-未上报*/
	public static final String ACCEPTANCE_FLOWSTATUS_NOREPORT="0";
	/**验收流程状态-县局<工务科>审批*/
	public static final String ACCEPTANCE_FLOWSTATUS_WORK_BRANCH="1";
	/**验收流程状态-县局<主管领导>审批*/
	public static final String ACCEPTANCE_FLOWSTATUS_WORK_MASTER="2";
	/**验收流程状态-审批结束*/
	public static final String ACCEPTANCE_FLOWSTATUS_FINISH="999";

	/***
	 * 验收单审批流程
	 * @param yesOrNo 发送下一步还是退回
	 * @param flowStatus 当前审批状态
	 * @return
	 */
	public static String getNextAcceptanceFlowStatusByStatusAndFrom(boolean yesOrNo,
			String status) {
		switch (status) {
		case ACCEPTANCE_FLOWSTATUS_NOREPORT:
			if (yesOrNo){
				return ACCEPTANCE_FLOWSTATUS_WORK_BRANCH;
			} else {
				return ACCEPTANCE_FLOWSTATUS_NOREPORT;
			}
		case ACCEPTANCE_FLOWSTATUS_WORK_BRANCH:
			if (yesOrNo){
				return ACCEPTANCE_FLOWSTATUS_WORK_MASTER;
			} else {
				return ACCEPTANCE_FLOWSTATUS_NOREPORT;
			}
		case ACCEPTANCE_FLOWSTATUS_WORK_MASTER:
			if (yesOrNo){
				return ACCEPTANCE_FLOWSTATUS_FINISH;
			} else {
				return ACCEPTANCE_FLOWSTATUS_WORK_BRANCH;
			}
		default:
			return CONSTRUCTION_FLOWSTATUS_NOREPORT;
		}
	}
	
	
}
