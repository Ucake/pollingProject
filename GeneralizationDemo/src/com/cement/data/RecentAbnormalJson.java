package com.cement.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.database.sqlite.SQLiteDatabase;

import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.MainActivity;
import com.example.generalizationdemo.SecondActivity;

public class RecentAbnormalJson {
	
	private String status = MainActivity.statusya;
	
	public void recentabnormal(String recentabnormaljson){
		try{
			JSONObject toor1 = new JSONObject(recentabnormaljson);
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
	            
	            if(status.equals("worker")){
	            	insertrecentabnormal(SecondActivity.recentabnormaldbhelper.getReadableDatabase(), abnormal_id, abnormal_check_point, abnormal_level, 
		            		workshop, abnormal_time, abnormal_person, description, "00");
	            }else{
	            	insertrecentabnormal(HomePageya.recentabnormaldbhelper.getReadableDatabase(), abnormal_id, abnormal_check_point, abnormal_level, 
		            		workshop, abnormal_time, abnormal_person, description, "00");
	            }
	            
	            
			}
			
			
		}catch (JSONException e) {
	        e.printStackTrace();
	    }
	}

	private static void insertrecentabnormal(SQLiteDatabase db, String abnormal_id, String abnormal_check_point,
			String abnormal_level, String workshop, String abnormal_time, String abnormal_person, String description, String state){
		db.execSQL(
				"insert into recentabnormal values(null, ?, ?, ?, ?, ?, ?, ?, ?)",
				new String[] { abnormal_id,abnormal_check_point,abnormal_level, workshop, abnormal_time, abnormal_person, description,state});
	}
}
