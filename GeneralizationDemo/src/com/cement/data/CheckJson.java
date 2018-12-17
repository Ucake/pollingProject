package com.cement.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cement.SQLite.CheckListDBHelper;
import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.MainActivity;
import com.example.generalizationdemo.SecondActivity;

import android.database.sqlite.SQLiteDatabase;

public class CheckJson {

	private String status = MainActivity.statusya;
	
	public void checkjson(String checkjson){
		try{
			JSONObject toor1 = new JSONObject(checkjson);
			JSONArray data1 = toor1.getJSONArray("data");
			for (int i = 0; i < data1.length(); i++) {
	            JSONObject lan1 = data1.getJSONObject(i);
			
	            String mission_id = lan1.getString("mission_id");
	            String worker = lan1.getString("worker");
	            String finish_time = lan1.getString("finish_time");
	            if(status.equals("worker")){
		            insertcheck(SecondActivity.checkdbhelper.getReadableDatabase(), mission_id, worker, finish_time,"00");

	            }else{
		            insertcheck(HomePageya.checkdbhelper.getReadableDatabase(), mission_id, worker, finish_time,"00");

	            }
	            
			}
			
			
		}catch (JSONException e) {
	        e.printStackTrace();
	    }
	}
	
	private static void insertcheck(SQLiteDatabase db, String mission_id, String worker,
			String finish_time,String state){
		db.execSQL(
				"insert into checklist values(null, ?, ?, ?, ?)",
				new String[] { mission_id,worker,finish_time,state});
	}

	
}
