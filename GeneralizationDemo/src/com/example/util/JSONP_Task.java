package com.example.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.sqlite.SQLiteDatabase;

import com.example.bean.Task;
import com.example.wechatsample.PeoplePage;

/**
 * @author lson
 *这个JSON是用来解析人员维度中的任务列表的
 */
public class JSONP_Task {

	private String resulta;
	private String errMsg;
	private String mission_name;
//	private String mission_condition;
	private int mission_condition;
	private ArrayList<Task> arrTask ;
	private int mission_id;
	
	public void p_Task(String rjson){
		try {
			 ArrayList<Task> task = new ArrayList<Task>() ;
			JSONObject toor = new JSONObject(rjson);
			JSONObject result = toor.getJSONObject("result");
			resulta = result.getString("result");
			errMsg = result.getString("errMsg");
			JSONArray data = toor.getJSONArray("data");
			for(int i = 0;i < data.length();i ++){
				JSONObject content = data.getJSONObject(i);
				mission_name = content.getString("mission_name");
//				mission_condition = content.getString("mission_condition");
				mission_condition = content.getInt("mission_condition");
//				insertTask(PeoplePage.dbHelper.getWritableDatabase(), mission_name, mission_condition);
				mission_id = content.getInt("mission_id");
//				insertArrayTask(mission_name, mission_condition,mission_id);
				task.add(new Task(mission_name, mission_condition, mission_id));
				
			}
			setArrTask(task);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public  ArrayList<Task> getArrTask() {
		//这是测试用的数据，自己添加
//				Task t1 = new Task();
//				t1.setTask_item_text("巡检任务");
//				t1.setMission_condition(1);
//				t1.setMission_id(123456);
//				arrTask.add(t1);
//				Task t2 = new Task();
//				t2.setTask_item_text("巡检任务");
//				t2.setMission_condition(2);
//				t2.setMission_id(123789);
//				arrTask.add(t2);
//		Collections.sort(arrTask);
		return arrTask;
	}
	public void setArrTask(ArrayList<Task> arrTask) {
		this.arrTask = arrTask;
	}

	public String getResulta() {
		return resulta;
	}

	public void setResulta(String resulta) {
		this.resulta = resulta;
	}
	
	
	}
	

