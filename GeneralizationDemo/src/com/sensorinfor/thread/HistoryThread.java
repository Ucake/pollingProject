package com.sensorinfor.thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class HistoryThread implements Runnable {

	private String sendStr;
	private Handler mHandler;
//	private JSONObject json = null;
	private String urlStr;
//	private int findYvalues;
	public HistoryThread(String sendStr, Handler mHandler,String urlStr) {
		super();
		this.sendStr = sendStr;
		this.mHandler = mHandler;
		this.urlStr = urlStr;
//		this.findYvalues = findYvalues;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		OutputStream out = null;
		BufferedReader reader = null;
//		JSONObject j = null;
		try {
			
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(5000);
			out = conn.getOutputStream();
			out.write(sendStr.getBytes());
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder builder = new StringBuilder();
			while((line = reader.readLine())!=null){
				builder.append(line);
			}	
			System.out.println("sensorinfo_MainActivity:"+builder.toString());
//			j = new JSONObject(builder.toString());
//			setJson(j);
			System.out.println(builder.toString());
			Message msg = new Message();
			msg.what = 3;
			msg.obj = builder.toString();
//			msg.arg1= findYvalues;
			mHandler.sendMessage(msg);
//			Looper.loop();
			
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
