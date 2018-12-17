package com.example.bean;



public class Task implements Comparable<Task>{
	private  String task_item_text;
	 private int mission_condition;
	private int mission_id;

	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Task(String task_item_text,int mission_condition,int mission_id) {
		super();
		this.task_item_text = task_item_text;
		this.mission_condition = mission_condition;
		this.mission_id = mission_id;
	}

	public String getTask_item_text() {
		return task_item_text;
	}

	public void setTask_item_text(String task_item_text) {
		this.task_item_text = task_item_text;
	}

	public int getMission_condition() {
		return mission_condition;
	}

	public void setMission_condition(int mission_condition) {
		this.mission_condition = mission_condition;
	}

	@Override
	public int compareTo(Task t) {
		// TODO Auto-generated method stub
		
		
		 
		return t.mission_condition - this.mission_condition;
	}

	public int getMission_id() {
		return mission_id;
	}

	public void setMission_id(int mission_id) {
		this.mission_id = mission_id;
	}

	
	
}
