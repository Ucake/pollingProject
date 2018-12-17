package com.example.testtransmit;

public class SelectsPeople {
	
	private String peopleName;
	private boolean flag;
	public SelectsPeople(String peopleName, boolean flag) {
		super();
		this.peopleName = peopleName;
		this.flag = flag;
	}
	public SelectsPeople() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPeopleName() {
		return peopleName;
	}
	public void setPeopleName(String peopleName) {
		this.peopleName = peopleName;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
