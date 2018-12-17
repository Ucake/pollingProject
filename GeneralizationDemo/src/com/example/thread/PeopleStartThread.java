package com.example.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.example.util.JSONStart;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class PeopleStartThread implements Runnable{

	private URL url;
	private String content;
	public static String backMsg = "";
	
//	private Handler handler;
	public PeopleStartThread(String content) {
		super();
		this.content = content;
//		this.flag = flag;
//		this.handler = handler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
//			if(flag == 0)
			url = new URL("http://123.206.16.157:8080/water/arrange.req?action=arrangeseekall");
//			else
				
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			OutputStream out = conn.getOutputStream();
			out.write(content.getBytes());
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			
			while((line = reader.readLine())!=null){
				backMsg = line;
			}
			System.out.println("全部人员/任务后台返回的数据："+backMsg);
			JSONStart jsonStart = new JSONStart();
			
			jsonStart.peopleArrange(backMsg);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
}
