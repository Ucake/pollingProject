package com.example.taskeditor.taskeditor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.util.taskeditor.DateTimePickerDialogUtilTaskEditor;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

public class SendTaskThread implements Runnable {
	private Handler handler;
	private String values;
	
	public SendTaskThread(Handler handler, String values) {
		super();
		this.handler = handler;
		this.values = values;
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
//			out.write(("taskname="+taskName+"&tasktime="+DateTimePickerDialogUtilTaskEditor.sendtaskTime+"&time1="+edit_3.getText().toString().substring(0,10)+"&taskpeople="+nameList.get(selectPostion)+"&time2="+"888"
//			+"&set_name="+"").getBytes());
			out.write(values.getBytes());
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			StringBuilder builder = new StringBuilder();
			while((line = reader.readLine())!=null){
				builder.append(line);
			}
			System.out.println("任务下发后后台返回的数据"+builder.toString());
			
			Message msg = new Message();
			msg.obj = builder.toString();
			msg.what = 123;
			handler.sendMessage(msg);
//			Toast.makeText(TaskSendActivity.this, "这是任务下发返回的："+new JSONObject(builder.toString()).getString("result"), Toast.LENGTH_LONG).show();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
