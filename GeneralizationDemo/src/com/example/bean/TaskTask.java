package com.example.bean;

public class TaskTask {
	private String name;
	private int mission_id;
	
	private int mission_condition;
	public TaskTask() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TaskTask(String name,int mission_id,int mission_condition) {
		super();
		this.name = name;
		
		
		this.mission_id = mission_id;
		this.mission_condition = mission_condition;
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public int getMission_id() {
		return mission_id;
	}
	public void setMission_id(int mission_id) {
		this.mission_id = mission_id;
	}
	public int getMission_condition() {
		return mission_condition;
	}
	public void setMission_condition(int mission_condition) {
		this.mission_condition = mission_condition;
	}
	
}
