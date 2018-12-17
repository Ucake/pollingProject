package com.example.bean.taskeditor;

public class Task implements Comparable<Task>{
	private String tasknum;
	private String taskName;
	private String task_num;
	
	
	public Task(String tasknum, String taskName, String task_num) {
		super();
		this.tasknum = tasknum;
		this.taskName = taskName;
		this.task_num = task_num;
	}

	public String getTasknum() {
		return tasknum;
	}

	public void setTasknum(String tasknum) {
		this.tasknum = tasknum;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTask_num() {
		return task_num;
	}

	public void setTask_num(String task_num) {
		this.task_num = task_num;
	}

	@Override
	public int compareTo(Task another) {
		// TODO Auto-generated method stub
		return Integer.parseInt(this.tasknum)- Integer.parseInt(another.tasknum);
	}
	
	
}
