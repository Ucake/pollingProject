package com.example.bean;

public class Tool {
	private String toolName;
	private String toolNum;
	public Tool(String toolName, String toolNum) {
		super();
		this.toolName = toolName;
		this.toolNum = toolNum;
	}
	public Tool() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	public String getToolNum() {
		return toolNum;
	}
	public void setToolNum(String toolNum) {
		this.toolNum = toolNum;
	}
	
}
