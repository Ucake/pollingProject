package com.sensorinfor.bean;

public class EquipmentInfo {
	private String factory;
	private String equipmentName;
	private String equipmentValue;
	private String color;
	public EquipmentInfo(String equipmentName, String equipmentValue,String color,String factory) {
		super();
		this.equipmentName = equipmentName;
		this.equipmentValue = equipmentValue;
		this.color = color;
		this.factory = factory;
	}
	public EquipmentInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getEquipmentName() {
		return equipmentName;
	}
	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}
	public String getEquipmentValue() {
		return equipmentValue;
	}
	public void setEquipmentValue(String equipmentValue) {
		this.equipmentValue = equipmentValue;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getFactory() {
		return factory;
	}
	public void setFactory(String factory) {
		this.factory = factory;
	}
	
	

}
