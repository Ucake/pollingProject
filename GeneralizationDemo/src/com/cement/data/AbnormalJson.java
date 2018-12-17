package com.cement.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.MainActivity;
import com.example.generalizationdemo.SecondActivity;

import android.database.sqlite.SQLiteDatabase;

public class AbnormalJson {
	
	private String status = MainActivity.statusya;
	
	public void abnormal(String abnormaljson){
		try{
			JSONObject toor1 = new JSONObject(abnormaljson);
			JSONArray data1 = toor1.getJSONArray("data");
			for (int i = 0; i < data1.length(); i++) {
	            JSONObject lan1 = data1.getJSONObject(i);
			
	            String abnormal_id = lan1.getString("abnormal_id");
	            String abnormal_check_point = lan1.getString("abnormal_check_point");
	            String abnormal_level = lan1.getString("abnormal_level");
	            String workshop = lan1.getString("workshop");
	            String abnormal_time = lan1.getString("abnormal_time");
	            String abnormal_person = lan1.getString("abnormal_person");
	            String description  = lan1.getString("abnormal_description");
	            String task_id = lan1.getString("taskname");
	            String abnormal_status = lan1.getString("status");
	            String suggestion = lan1.getString("suggestion");
	            
//	            insertabnormal(HomePageya.abnormaldbhelper.getReadableDatabase(), abnormal_id,abnormal_status, abnormal_check_point, abnormal_level, 
//	            		workshop, abnormal_time, abnormal_person, description, task_id, "00");
	            
	            if(status.equals("worker")){
	            	insertabnormal(SecondActivity.abnormaldbhelper.getReadableDatabase(), abnormal_id,abnormal_status, abnormal_check_point, abnormal_level, 
		            		workshop, abnormal_time, abnormal_person, description, task_id,suggestion, "00");

	            }else{
	            	insertabnormal(HomePageya.abnormaldbhelper.getReadableDatabase(), abnormal_id,abnormal_status, abnormal_check_point, abnormal_level, 
		            		workshop, abnormal_time, abnormal_person, description, task_id,suggestion, "00");

	            }
	            
			}
			
			
		}catch (JSONException e) {
	        e.printStackTrace();
	    }
	}

	private static void insertabnormal(SQLiteDatabase db, String abnormal_id, String abnormal_status, String abnormal_check_point,
			String abnormal_level, String workshop, String abnormal_time, String abnormal_person, 
			String description, String task_id, String suggestion, String state){
		db.execSQL(
				"insert into abnormal values(null, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?)",
				new String[] { abnormal_id,abnormal_status,abnormal_check_point,abnormal_level, workshop, abnormal_time, abnormal_person, description, task_id, suggestion,state});
	}
}
