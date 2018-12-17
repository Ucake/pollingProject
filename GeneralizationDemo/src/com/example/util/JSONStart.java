package com.example.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.wechatsample.MainActivity;
import com.example.wechatsample.PeoplePage;
import com.example.wechatsample.TaskPage;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * @author lson
 *杩欎釜JSON鏄敤鏉ヨВ鏋愭湰鍛ㄥ叏閮ㄤ汉鍛樺拰浠诲姟
 */
public class JSONStart {

	private String resulta;
	private String worker_name;
	private String team;
	private String post;
	private String flag;
	private String colorflage;
	private String set_worker_time;
	private String mission_name;
	private String mission_condition;
	private String set_mission_time;
	private String errMsge;
	private String mission_id;
	public void peopleArrange(String rjson){
		try {
			JSONObject toor = new JSONObject(rjson);
//			JSONObject result = toor.getJSONObject("result");
//			resulta = result.getString("result");
//			errMsge = result.getString("errMsge");
			if(toor.getJSONArray("data") == null){
				return;
			}
			JSONArray data = toor.getJSONArray("data");
			for(int i = 0;i < data.length();i ++){
				JSONObject content = data.getJSONObject(i);
				worker_name = content.getString("worker_name");
				team = content.getString("team");
				post = content.getString("post");
				flag = content.getString("flag");
				colorflage = content.getString("colorflage");
				set_worker_time = content.getString("set_worker_time");
//				mission_name = content.getString("mission_name");
//				mission_condition = content.getString("mission_condition");
//				set_mission_time = content.getString("set_mission_time");
//				mission_id = content.getString("mission_id");
				
				
				insertdata(PeoplePage.dbHelper.getWritableDatabase(), worker_name, flag, colorflage, set_worker_time,team,post);
				
				
			System.out.println("JSONStart涓殑浜哄憳鍒楄〃锛�"+worker_name+":"+flag);
				
			
					
					
				
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void taskArrange(String rjson){
		JSONObject toor;
		try {
			toor = new JSONObject(rjson);
			JSONObject result = toor.getJSONObject("result");
			resulta = result.getString("result");
//			errMsge = result.getString("errMsge");
			JSONArray data = toor.getJSONArray("data");
			System.out.println("jsonstart:鍚庡彴杩斿洖鐨勬暟鎹細"+data);
			for(int i = 0;i < data.length();i ++){
				JSONObject content = data.getJSONObject(i);
//				worker_name = content.getString("worker_name");
//				team = content.getString("team");
//				post = content.getString("post");
//				flag = content.getString("flag");
//				colorflage = content.getString("corolflage");
//				set_worker_time = content.getString("set_worker_time");
				mission_name = content.getString("mission_name");
				mission_condition = content.getString("mission_condition");
				set_mission_time = content.getString("set_mission_time");
				mission_id = content.getString("mission_id");
				
				Log.e("浠诲姟寮�濮嬬嚎绋嬩腑鐨刯son瑙ｆ瀽", mission_name+":"+set_mission_time+":"+mission_condition);
				if(2 < Integer.parseInt(mission_condition)){
					
					insertTask(TaskPage.dbHelperTask.getWritableDatabase(), mission_name, ""+mission_condition, set_mission_time,mission_id);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
}
	
	private static void insertdata(SQLiteDatabase db, String name, String flag,
			String colorflage, String time,String team,String post){
		
		db.execSQL(
				"insert into people values(null, ?, ?, ?, ?, ?, ?)",
				new String[] { name, flag, colorflage,time,team , post });
	}
	private static void insertTask(SQLiteDatabase db, String name, String mission_conditon,
			String time,String mission_id){
		db.execSQL(
				"insert into task values(null, ?, ?, ?,?)",
				new String[] { name,mission_conditon,time,mission_id});
	}
	
}
