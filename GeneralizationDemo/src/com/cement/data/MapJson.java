package com.cement.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.MainActivity;
import com.example.generalizationdemo.SecondActivity;

import android.database.sqlite.SQLiteDatabase;


public class MapJson {
	
	private String status = MainActivity.statusya;
	
	public void mapsearch(String mapjson){
		try{
			
			JSONObject toor1 = new JSONObject(mapjson);
//			JSONArray data1 = toor1.getJSONArray("event_data");
//			for (int i = 0; i < data1.length(); i++) {
//	            JSONObject lan1 = data1.getJSONObject(i);
//			
//	            String address = lan1.getString("event_name");
//	            String addressID = lan1.getString("event_id");
//	            String wd = lan1.getString("event_latitude");
//	            String jd = lan1.getString("event_longitude");
//	            
//	            if(status.equals("worker")){
//	            	insertevent(SecondActivity.eventdbhelper.getReadableDatabase(), addressID, address, wd, jd,"00");
//	            }else{
//	            	insertevent(HomePageya.eventdbhelper.getReadableDatabase(), addressID, address, wd, jd,"00");
//	            }
//	            
//	            
//			}
			
			JSONObject toor2 = new JSONObject(mapjson);
			JSONArray data2 = toor2.getJSONArray("human_data");
			for (int i = 0; i < data2.length(); i++) {
	            JSONObject lan2 = data2.getJSONObject(i);
			
	            String friend_name = lan2.getString("friend_name");
	            String friend_phone = lan2.getString("friend_phone");
	            String friend_latitude = lan2.getString("friend_latitude");
	            String friend_longitude = lan2.getString("friend_longitude");
	            
	            if(status.equals("worker")){
		            insertfriend(SecondActivity.frienddbhelper.getReadableDatabase(), friend_name, friend_phone, friend_latitude, friend_longitude, "00");
	            }else{
		            insertfriend(HomePageya.frienddbhelper.getReadableDatabase(), friend_name, friend_phone, friend_latitude, friend_longitude, "00");
	            }
	            
			}
			}catch (JSONException e) {
		        e.printStackTrace();
		    }
	}
			private static void insertevent(SQLiteDatabase db, String event_id, String event_name,
					String event_latitude,String event_longitude, String state){
				db.execSQL(
						"insert into mapevent values(null, ?, ?, ?, ?, ?)",
						new String[] { event_id,event_name,event_latitude,event_longitude,state});
			}
			
			private static void insertfriend(SQLiteDatabase db, String friend_name, String friend_phone,
					String friend_latitude,String friend_longitude, String state){
				db.execSQL(
						"insert into mapfriend values(null, ?, ?, ?, ?, ?)",
						new String[] { friend_name,friend_phone,friend_latitude,friend_longitude,state});
			}
}
