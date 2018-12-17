package com.cement.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;

public class UpdateThread implements Runnable {
	private URL url;
	private String content;
	public static String backMsg = "";
	private Handler handler;
	private BufferedReader in;
	
	public UpdateThread(Handler handler, String content){
		this.content = content;
		this.handler = handler;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
			url = new URL("http://123.206.16.157:8080/water/mission.req?action=version");
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
			
				Message msg = new Message();
				msg.what = 12138;
				msg.obj = backMsg;
				handler.sendMessage(msg);
				in.close();
			
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
