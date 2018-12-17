package com.cement.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.MainActivity;
import com.example.generalizationdemo.SecondActivity;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SearchJson {
	
	private String status = MainActivity.statusya;
	
	public void searchjson(String searchjson){
		
		try{
			JSONObject toor1 = new JSONObject(searchjson);
			JSONArray data1 = toor1.getJSONArray("data");
			for (int i = 0; i < data1.length(); i++) {
	            JSONObject lan1 = data1.getJSONObject(i);
			
	            String mission_id = lan1.getString("mission_id");
	            String worker = lan1.getString("worker");
	            String finish_time = lan1.getString("finish_time");
	            String mission_condition = lan1.getString("mission_condition");
	            String mission_name = lan1.getString("mission_name");
	            
	            Log.v("ruguozhejiuszuooasdfhjietuo", status);
	            if(status.equals("worker")){
	            	insertsearch(SecondActivity.searchdbhelper.getReadableDatabase(), mission_id, worker, finish_time,
		            		mission_condition, mission_name,"00");
	            }else{
	            	insertsearch(HomePageya.searchdbhelper.getReadableDatabase(), mission_id, worker, finish_time,
		            		mission_condition,mission_name,"00");
	            }
	            
			}
			
		}catch (JSONException e) {
	        e.printStackTrace();
	    }
		
	}
	
	private static void insertsearch(SQLiteDatabase db, String mission_id, String worker,
			String finish_time, String mission_condition, String mission_name, String state){
		db.execSQL(
				"insert into search values(null, ?, ?, ?, ?, ?, ?)",
				new String[] { mission_id,worker,finish_time, mission_condition ,mission_name ,state});
	}

}
