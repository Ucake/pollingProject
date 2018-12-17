package com.example.bean.taskeditor;

import java.util.Comparator;

import android.R.integer;

public class Event implements Comparable<Event>{

	private String id;
	private String name;
	private boolean flage;
	private boolean tool;
	public Event(String id, String name, boolean flage,boolean tool) {
		super();
		this.id = id;
		this.name = name;
		this.flage = flage;
		this.tool = tool;
	}
	public Event() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isFlage() {
		return flage;
	}
	public void setFlage(boolean flage) {
		this.flage = flage;
	}
	public boolean isTool() {
		return tool;
	}
	public void setTool(boolean tool) {
		this.tool = tool;
	}
	
	@Override
	public int compareTo(Event another) {
		// TODO Auto-generated method stub
		return Integer.valueOf(another.id) - Integer.valueOf(this.id);
	}
	
}
