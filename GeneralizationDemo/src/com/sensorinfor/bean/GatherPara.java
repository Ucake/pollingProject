package com.sensorinfor.bean;

public class GatherPara {
	private String parameterName;
	private String parameterValue;
	private String parameterTime;
	public GatherPara(String parameterName, String parameterValue,
			String parameterTime) {
		super();
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
		this.parameterTime = parameterTime;
	}
	public GatherPara() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getParameterTime() {
		return parameterTime;
	}
	public void setParameterTime(String parameterTime) {
		this.parameterTime = parameterTime;
	}
	

}
