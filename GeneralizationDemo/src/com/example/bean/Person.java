package com.example.bean;

public class Person {

	private String name;
	private String birth;
	private int flag;
	private int colorflag;
	private boolean f;
	

	public Person(String name, String birth,int flag,int colorflag,boolean f) {
		super();
		this.name = name;
		this.birth = birth;
		this.flag = flag;
		this.colorflag = colorflag;
		this.f = f;
		
	}

	public Person() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public int getColorflag() {
		return colorflag;
	}

	public void setColorflag(int colorflag) {
		this.colorflag = colorflag;
	}

	public boolean isF() {
		return f;
	}

	public void setF(boolean f) {
		this.f = f;
	}

	
	
	
	
}
