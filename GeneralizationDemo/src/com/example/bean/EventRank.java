package com.example.bean;

import java.util.Comparator;

public class EventRank implements Comparable<EventRank> {

	private String id;
	private String name;
	
	public EventRank() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public EventRank(String id, String name) {
		super();
		this.id = id;
		this.name = name;
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


	


	@Override
	public int compareTo(EventRank another) {
		// TODO Auto-generated method stub
		return  Integer.valueOf(another.id) - Integer.valueOf(this.id);
	}
	
}
