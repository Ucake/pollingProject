package com.example.bean;

public class SelectPeople {
	String people_name;
	String team;
	String isselect;
	String tel;
	public SelectPeople(String people_name, String team, String isselect,
			String tel) {
		super();
		this.people_name = people_name;
		this.team = team;
		this.isselect = isselect;
		this.tel = tel;
	}
	public SelectPeople() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPeople_name() {
		return people_name;
	}
	public void setPeople_name(String people_name) {
		this.people_name = people_name;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getIsselect() {
		return isselect;
	}
	public void setIsselect(String isselect) {
		this.isselect = isselect;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	

}
