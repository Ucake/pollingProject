package com.example.util.taskeditor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;

public class sendTaskThread implements Runnable {
	
	private String content;
	private Handler handler;
	
	public sendTaskThread(String content,Handler handler) {
		super();
		this.content = content;
		this.handler = handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		OutputStream out = null;
		BufferedReader reader = null;
		try {
			
			URL url = new URL("http://123.206.16.157:8080/water/arrange.req?action=set_mission");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			out = conn.getOutputStream();
			//+"&taskday="+edit_3.getText().toString().split(",")[0]
			out.write(content.getBytes());
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder builder = new StringBuilder();
			while((line = reader.readLine())!=null){
				builder.append(line);
			}
			System.out.println("任务下发后后台返回的数据"+builder.toString());
			JSONObject json = new JSONObject(builder.toString());
			Message msg = new Message();
			msg.what = 12137;
			msg.obj = json.get("result");
			handler.sendMessage(msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
