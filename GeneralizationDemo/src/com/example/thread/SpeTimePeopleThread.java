package com.example.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.bean.Person;

import android.os.Handler;
import android.os.Message;

public class SpeTimePeopleThread implements Runnable {
	private String senStr;
	private Handler handler;
	private ArrayList<Person> list;
	public static String backMsg = "";

	


	public SpeTimePeopleThread(String senStr, Handler handler,
			ArrayList<Person> list) {
		super();
		this.senStr = senStr;
		this.handler = handler;
		this.list = list;
	}





	@Override
	public void run() {
		// TODO Auto-generated method stub
		OutputStream out = null;
		BufferedReader reader = null;
		try {
			URL url = new URL("http://123.206.16.157:8080/water/arrange.req?action=arrangelist");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			out = conn.getOutputStream();
			out.write(senStr.getBytes());
			 reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			
			while((line = reader.readLine())!=null){
				backMsg = line;
			}
			if (backMsg.equals("success")){
			
			}
			JSONObject json = new JSONObject(backMsg);
			JSONArray data_array = json.getJSONArray("data");
			for(int i = 0;i < data_array.length();i ++){
				JSONObject content = data_array.getJSONObject(i);
				String worker_name = content.getString("name");
//				String team = content.getString("team");
//				String post = content.getString("post");
				int flag = Integer.parseInt(content.getString("flag"));
				int colorflage = Integer.parseInt(content.getString("colorflage"));
//				String set_worker_time = content.getString("set_worker_time");
				boolean f;
				if (flag == 1)
					f = true;
				else
					f = false;
				list.add(new Person(worker_name, "ÈÎÎñ", flag, colorflage, f));
			}
				Message msg = new Message();
				msg.what = 1;
				msg.obj = backMsg;
				handler.sendMessage(msg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(out != null)
				try {
					out.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			if(reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}

}
