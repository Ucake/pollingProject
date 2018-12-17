package com.sensorinfor.bean;

import java.util.List;

public class EquipInfo {
	private String equName;
	private String specificName;
	private String sensorId;
	private String specificVaule;
	private String specificTime;
	private List<GatherPara> parameterList;
	
	public EquipInfo(String equName, String specificName, String sensorId,
			String specificVaule, String specificTime,
			List<GatherPara> parameterList) {
		super();
		this.equName = equName;
		this.specificName = specificName;
		this.sensorId = sensorId;
		this.specificVaule = specificVaule;
		this.specificTime = specificTime;
		this.parameterList = parameterList;
	}
	
	public EquipInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getEquName() {
		return equName;
	}

	public void setEquName(String equName) {
		this.equName = equName;
	}

	public String getSpecificName() {
		return specificName;
	}

	public void setSpecificName(String specificName) {
		this.specificName = specificName;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public String getSpecificVaule() {
		return specificVaule;
	}

	public void setSpecificVaule(String specificVaule) {
		this.specificVaule = specificVaule;
	}

	public String getSpecificTime() {
		return specificTime;
	}

	public void setSpecificTime(String specificTime) {
		this.specificTime = specificTime;
	}

	public List<GatherPara> getParameterList() {
		return parameterList;
	}

	public void setParameterList(List<GatherPara> parameterList) {
		this.parameterList = parameterList;
	}
	
	
	
	
}
