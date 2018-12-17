package com.example.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class Pe_task_listThread implements Runnable{
	
	private URL url;
	private String content;
	public static String backMsg = "";
	private Handler handler;
	

	public Pe_task_listThread(String content, Handler handler) {
		super();
		this.content = content;
		this.handler = handler;
	}


	@Override
	public void run() {
		OutputStream out = null;
		BufferedReader reader = null;
		try {
			url = new URL("http://123.206.16.157:8080/water/arrange.req?action=arrangepermis");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			out = conn.getOutputStream();
			out.write(content.getBytes());
			 reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			
			while((line = reader.readLine())!=null){
				backMsg = line;
			}
			if (backMsg.equals("success")){
			
			}
			
			
				Message msg = new Message();
				msg.what = 12137;
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
