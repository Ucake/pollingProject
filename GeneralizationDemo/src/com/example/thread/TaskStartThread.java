package com.example.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

import com.example.bean.Person;
import com.example.bean.TaskTask;
import com.example.util.JSONStart;

public class TaskStartThread implements Runnable {
	
	private String senStr;
	private Handler handler;
	private ArrayList<TaskTask> list;
	public static String backMsg = "";
	
	

	public TaskStartThread(String senStr) {
		super();
		this.senStr = senStr;
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub

		OutputStream out = null;
		BufferedReader reader = null;
		try {
			URL url = new URL("http://123.206.16.157:8080/water/arrange.req?action=arrangeseek");
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
//			if (backMsg.equals("success")){
				System.out.println("任务维度中返回的数据："+backMsg);
//			}
			JSONStart jsonStart = new JSONStart();
			jsonStart.taskArrange(backMsg);
			
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
