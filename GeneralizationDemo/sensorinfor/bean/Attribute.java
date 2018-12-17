package com.sensorinfor.bean;

public class Attribute {
	private String equipment_name;
	private String project_name_str;
	private String specific_name;
	private String item_name;
	private String value;
	private String sensor_id;
	private String item_time;
	private String item_id;
	private String item_unit;
	private String project_name;
	public Attribute() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Attribute(String equipment_name, String project_name_str,
			String specific_name, String item_name, String value,
			String sensor_id, String item_time, String item_id,String item_unit,String project_name) {
		super();
		this.equipment_name = equipment_name;
		this.project_name_str = project_name_str;
		this.project_name = project_name;
		this.specific_name = specific_name;
		this.item_name = item_name;
		this.value = value;
		this.sensor_id = sensor_id;
		this.item_time = item_time;
		this.item_id = item_id;
		this.item_unit = item_unit;
	}

	public String getEquipment_name() {
		return equipment_name;
	}
	public void setEquipment_name(String equipment_name) {
		this.equipment_name = equipment_name;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getSpecific_name() {
		return specific_name;
	}
	public void setSpecific_name(String specific_name) {
		this.specific_name = specific_name;
	}
	public String getItem_name() {
		return item_name;
	}
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getSensor_id() {
		return sensor_id;
	}
	public void setSensor_id(String sensor_id) {
		this.sensor_id = sensor_id;
	}
	public String getItem_time() {
		return item_time;
	}
	public void setItem_time(String item_time) {
		this.item_time = item_time;
	}
	public String getItem_id() {
		return item_id;
	}
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	public String getItem_unit() {
		return item_unit;
	}

	public void setItem_unit(String item_unit) {
		this.item_unit = item_unit;
	}
	
	
}
