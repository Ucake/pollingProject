package com.example.bean.taskeditor;

public class Tool {
	private String toolName;
	private String toolNum;
	private String content_id;
	public Tool(String toolName, String toolNum,String content_id) {
		super();
		this.toolName = toolName;
		this.toolNum = toolNum;
		this.content_id = content_id;
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
	public String getContent_id() {
		return content_id;
	}
	public void setContent_id(String content_id) {
		this.content_id = content_id;
	}
	
}
