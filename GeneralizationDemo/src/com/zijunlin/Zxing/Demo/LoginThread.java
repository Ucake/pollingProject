package com.zijunlin.Zxing.Demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LoginThread implements Runnable {

	private URL url;
	private String content;
	public static String backMsg = "";
	private Handler handler;
//	private BufferedReader in;
	
	public LoginThread(Handler handler, String content){
		this.content = content;
		this.handler = handler;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
		try{
//			url = new URL("http://59.110.157.159:8080/water/lock.req?action=readytodo");
			//url = new URL("http://121.41.72.157:8080/water/lock.req?action=loginss");
			url = new URL("http://59.110.157.159:8080/water/lock.req?action=readytodo");
//			http://112.74.171.141:8080/water/user.req?action=mission_upload
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
//			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//			conn.setRequestProperty("Content-Length", content.length() + "");
			
			OutputStream out = conn.getOutputStream();
			out.write(content.getBytes());
			
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			
			while((line = reader.readLine())!=null){
				backMsg = line;
			}
			if (backMsg.equals("success")){
			Log.e("READYYYYYYYYYYYYYY",backMsg);
			}
				Message msg = new Message();
				msg.what = 12138;
				msg.obj = backMsg;
				handler.sendMessage(msg);
//				in.close();
			
		
			
		
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
