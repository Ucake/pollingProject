package com.zijunlin.Zxing.Demo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.example.denglu.HomePage;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ReadytoinstallJson {
	
	private String addressID;
	private String address;
	private String landlord;
	private String cellphone;
	
	public static String number;
	
	private String resulta;
	
//
//
//
//	public String getErrMsg() {
//		return errMsg;
//	}
//
//
//
//	private String errMsg;
	
	
	
	public String getResulta() {
		return resulta;
	}



	public void readytoinstall(String rjson){
		try{
		JSONObject toor = new JSONObject(rjson);
        
       
        
        number = toor.getString("number");
        System.out.println(number);
        
        JSONObject result = toor.getJSONObject("result");
//        JSONObject result2 = result.getJSONObject(1);
         resulta = result.getString("result");
        String errMsga = result.getString("errMsg");
        
        //读取多个数据
        JSONArray yaar = toor.getJSONArray("data");
        for (int i = 0; i < yaar.length(); i++) {
            JSONObject lan = yaar.getJSONObject(i);
            System.out.println("-----------------");
//            System.out.println("addressID= " + lan.getInt("addressID"));
//            System.out.println("address= " + lan.getString("address"));
//            System.out.println("landlord= " + lan.getString("landlord"));
//            System.out.println("cellphone= " + lan.getString("cellphone"));
//		result = toor.getString("result");
//		errMsg = toor.getString("errMsg");

            
            
            

           
            addressID = lan.getString("addressID");
            address = lan.getString("address");
            landlord = lan.getString("landlord");
            cellphone = lan.getString("cellphone");
            Log.e("rrrrrrrrrrrrrrrrrr", address+"************"+addressID);
//            insertdata(HomePage.readydbHelper.getReadableDatabase(), addressID, address, landlord, cellphone);
            
        
        }
        

    } catch (JSONException e) {
        e.printStackTrace();
    }
    
	}

	

	private static void insertdata(SQLiteDatabase db, String addressID, String address,
			String landlord, String cellphone){
		db.execSQL(
				"insert into readytoinstall values(null, ?, ?, ?, ?)",
				new String[] { addressID, address, landlord,cellphone });
	}
	
}
