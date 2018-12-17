package com.example.taskeditor.taskeditor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cement.abnormalmanagement.AbnormalDetailActivity;
import com.cement.thread.ChangeConditionThread;
import com.cement.thread.ExceptionStatus;
import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.R;
import com.example.generalizationdemo.SecondActivity;
import com.example.util.SendDateTimePickerDialogUtil;
import com.example.util.taskeditor.DateTimePickerDialogUtilTaskEditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class MaintainActivity extends Activity implements OnClickListener {
	private EditText nameEdit;
	private EditText sourceEdit;
	private EditText startEdit;
	private EditText endEdit;
	private EditText remarkEidt;
	private EditText eventEdit;
	private EditText placeEdit;
	private EditText reportEdit;
	private EditText reportTimeEdit;
	private EditText levelEdit;
	private EditText desceditEdit;
	
	private Spinner levelSpin;
	private Spinner peopleSp;
	
	private Button picBut;
	private Button sendBut;
	private Button cancelBut;
	
	private String abnormalId;
	private String abnormalEvent;
	private String place;
	private String people;
	private String time;
	private String level;
	private String description;
	private String eventId;
	private JSONObject json;
	private JSONObject peoJSON;
	
	private Intent intent;
	
	private ImageView image;
	private Bitmap bitmap;
	private JSONObject sendJson;
	private ArrayList<String> nameList ;
	private SimpleDateFormat formatter;
	private Date curDate;
	private String day;
	private SendDateTimePickerDialogUtil dataUtil;
	private SharedPreferences preferences;
	public String workshop_id;
	private String status;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintaintask);
		
		myFindViewById();
		
		initView();
		
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectNetwork()
        .penaltyLog()
        .build());
		
		picBut.setOnClickListener(this);
		sendBut.setOnClickListener(this);
		cancelBut.setOnClickListener(this);
		startEdit.setOnClickListener(this);
		endEdit.setOnClickListener(this);
		
	}



	private void initView() {
		// TODO Auto-generated method stub
		preferences = getSharedPreferences("user", MODE_PRIVATE);
		workshop_id = preferences.getString("workshop_id", null);
		intent = getIntent();
		abnormalId = intent.getExtras().getString("abnormalId");
		peoJSON = AbnormalDetailActivity.peoJSON;
		json = AbnormalDetailActivity.json;
		JSONObject data;
		String[] peopStr;
		try {
			if("10000".equals(json.get("result"))){
			data = json.getJSONObject("data");
			abnormalEvent = data.getString("event");
			place = data.getString("location");
			people = data.getString("reporter");
			time = data.getString("time");
			description = data.getString("description");
			level = data.getString("level");
			eventId = data.getString("event_id");
			}else{
				MaintainActivity.this.finish();
//				onDestroy();
			}
			JSONArray array = peoJSON.getJSONArray("data");
			peopStr = new String[array.length()];
			for (int i = 0; i < array.length(); i++) {
				JSONObject d = array.getJSONObject(i);
				String flag = d.getString("flag");
				String name = d.getString("name");
				peopStr[i] = name;
				
			}
			com.example.adapter.taskeditor.SpinnerAdapter adapter = new com.example.adapter.taskeditor.SpinnerAdapter(MaintainActivity.this, peopStr, 20, "#080808"); 
			peopleSp.setAdapter(adapter);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		eventEdit.setText(abnormalEvent);
		placeEdit.setText(place);
		reportEdit.setText(people);
		reportTimeEdit.setText(time);
		levelEdit.setText(level);
		desceditEdit.setText(description);
		
		image = new ImageView(MaintainActivity.this);
		
		nameList = new ArrayList<String>();
		
		formatter = new SimpleDateFormat("yyyy-MM-dd");
    	curDate = new Date(System.currentTimeMillis());
		day = formatter.format(curDate);
//		timeEdit.setText(formatter.format(curDate)+ ","+"白天");
//		timeEdit.setInputType(InputType.TYPE_NULL);
		String[] level = {"1级","2级","3级"};
		
		com.example.adapter.taskeditor.SpinnerAdapter adapter = new com.example.adapter.taskeditor.SpinnerAdapter(MaintainActivity.this, level, 20, "#080808"); 
		levelSpin.setAdapter(adapter);
		 
	}



	private void myFindViewById() {
		// TODO Auto-generated method stub
		nameEdit = (EditText) findViewById(R.id.nameedit);
		sourceEdit = (EditText) findViewById(R.id.sourceedit);
		startEdit = (EditText) findViewById(R.id.startedit);
		endEdit = (EditText) findViewById(R.id.endedit);
		remarkEidt = (EditText) findViewById(R.id.remarkeidt);
		eventEdit = (EditText) findViewById(R.id.eventedit);
		placeEdit = (EditText) findViewById(R.id.placeedit);
		reportEdit = (EditText) findViewById(R.id.reportedit);
		reportTimeEdit = (EditText) findViewById(R.id.reporttimeedit);
		levelEdit = (EditText) findViewById(R.id.leveledit);
		desceditEdit = (EditText) findViewById(R.id.desceditedit);
		
		peopleSp = (Spinner) findViewById(R.id.peoplesp);
		levelSpin = (Spinner) findViewById(R.id.levelsp);
		
		picBut = (Button) findViewById(R.id.picbutton);
		sendBut = (Button) findViewById(R.id.send);
		cancelBut = (Button) findViewById(R.id.cancel);
		
		startEdit.setInputType(InputType.TYPE_NULL);
		endEdit.setInputType(InputType.TYPE_NULL);
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.picbutton:
			new Thread(connectNet).start();
			LinearLayout loginDialog = (LinearLayout)LayoutInflater.from(MaintainActivity.this).inflate(R.layout.alertdialog_layout, null);
			image = (ImageView) loginDialog.findViewById(R.id.picture);
			new AlertDialog.Builder(MaintainActivity.this)
	        .setTitle("照片")
	        .setMessage("")
	        .setView(loginDialog)
	        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
	            @Override
	            public void onClick(DialogInterface dialog, int which) {
//	            	picture.setImageBitmap(bitmap);
	            	
	            }
	        
	        })
	       
	        .show();
			break;
		case R.id.send:
			new Thread(sendRun).start();
			break;
		case R.id.cancel:
			Intent cancelInt = new Intent(MaintainActivity.this, AbnormalDetailActivity.class);
			startActivity(cancelInt);
			
			break;
		case R.id.startedit:
			dataUtil = new SendDateTimePickerDialogUtil(MaintainActivity.this, day);
			dataUtil.dateTimePicKDialog(startEdit);
			break;
		case R.id.endedit:
			dataUtil = new SendDateTimePickerDialogUtil(MaintainActivity.this, day);
			dataUtil.dateTimePicKDialog(endEdit);
			break;

		default:
			break;
		}
	}
	private Runnable connectNet = new Runnable(){  
        @Override  
        public void run() {  
            try {                 
            	String filepath = "http://123.206.16.157:8080/water/imageDownload.req?action=exceptiondownload&id="+abnormalId;
            	 bitmap = BitmapFactory.decodeStream(getImageStream(filepath));
            	 Message mes = new Message();
            	 mes.what = 1;
            	 connectHanlder.sendMessage(mes);  
             } catch (Exception e) {  
//                 Toast.makeText(SearchListActivity.this,"无法链接网络！", 1).show();  
                 e.printStackTrace();  
             }  
   
         }  
   
     };  
     public InputStream getImageStream(String path) throws Exception{  
         URL url = new URL(path);  
         HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
         conn.setConnectTimeout(5*1000);  
         conn.setRequestMethod("GET"); 
//         conn.setRequestMethod("POST");
         if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
             return conn.getInputStream();  
         }else{
         	Toast.makeText(getApplicationContext(), "暂无图片！", Toast.LENGTH_LONG).show();
         }
         return null;  
     }  
	
	private Handler connectHanlder = new Handler() {  
        @Override  
        public void handleMessage(Message msg) { 
        	switch (msg.what) {
			case 1:
				
				if (bitmap != null) {  
					image.setImageBitmap(bitmap);
				} else{
					image.setImageResource(R.drawable.buffering);
				}  
				break;
			case 2:
				
				if(msg.obj != null){
					JSONObject resultJson;
					try {
						resultJson = new JSONObject(msg.obj.toString());
						if("10000".equals(resultJson.get("result"))){
//							String value = "mission_id=" + mission_id +"&condition=7";
							String value = "exception_id=" + abnormalId+"&status=2";
							ExceptionStatus thread = new ExceptionStatus(connectHanlder, value);
							new Thread(thread).start();
							
						}else{
							Toast.makeText(MaintainActivity.this, "下发失败，请稍后重试！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				break;
			case 12138:
				System.out.println("下发维修任务修改异常id：：：："+ msg.obj);
				if(msg.obj != null){
					JSONObject resultJson;
					try {
						status = preferences.getString("status", null);
						resultJson = new JSONObject(msg.obj.toString());
						if("10000".equals(resultJson.get("result"))){
							
							Intent intent = new Intent();
							if(status.equals("worker")){
								intent.setClass(MaintainActivity.this,SecondActivity.class);
							}else{
								intent.setClass(MaintainActivity.this,HomePageya.class);
							}
							startActivity(intent);
						}else{
							Toast.makeText(MaintainActivity.this, "下发失败，请稍后重试！", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				break;
			default:
				break;
			}
            // 更新UI，显示图片  
        }  
    };  
    private Runnable sendRun = new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			String strUrl = "http://123.206.16.157:8080/water/mission.req?action=insertRepair";
			URL url = null;
			String result = "";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");			
			String sendStr = "taskname="+nameEdit.getText().toString()
							+"&tasktype="+"维修任务"
							+"&tasklevel="+levelSpin.getSelectedItem().toString()
							+"&tasksource="+sourceEdit.getText().toString()
							+"&start_time="+startEdit.getText().toString()
							+"&end_time="+endEdit.getText().toString()
							+"&people="+peopleSp.getSelectedItem().toString()
							+"&remark="+remarkEidt.getText().toString()
							+"&abnormalId="+abnormalId
							+"&eventId="+eventId
							+"&send_time="+format.format(new Date())
							+"&workshop_id="+workshop_id
							+"&set_start_time_code="+"mo_1";
			System.out.println("sendStr:::"+sendStr);
							
			try {
				url = new URL(strUrl);
				HttpURLConnection urlConn = (HttpURLConnection) url
						.openConnection();
				urlConn.setDoInput(true);// 设置输入采用字节流
				urlConn.setDoOutput(true);// 设置输出采用字节流
				urlConn.setRequestMethod("POST");
				urlConn.setUseCaches(false);
				urlConn.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				urlConn.setRequestProperty("Charset", "UTF-8");

				urlConn.connect();// 连接既往服务端发送消息
				DataOutputStream dos = new DataOutputStream(
						urlConn.getOutputStream());
//				dos.writeBytes(params);
				dos.write(sendStr.getBytes());
				dos.flush();// 清空缓存
				dos.close();// 关闭
				// 接收部分
				BufferedReader bReader = new BufferedReader(new InputStreamReader(
						urlConn.getInputStream()));

				String readline = null;
				while ((readline = bReader.readLine()) != null) {
					result += readline;
				}
				bReader.close();
				urlConn.disconnect();
				System.out.println("下发维修任务返回的内容："+ result);
				Message mes = new Message();
				mes.obj = result;
				mes.what = 2;
				connectHanlder.sendMessage(mes);	

			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
					
	};
	
	private void insertJson(){
		sendJson = new JSONObject();
		
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			sendJson.put("taskname", nameEdit.getText().toString());
			sendJson.put("tasktype", "维修任务");
			sendJson.put("tasklevel",levelSpin.getSelectedItem().toString());
			sendJson.put("tasksource",sourceEdit.getText().toString());
			sendJson.put("start_time", startEdit.getText().toString());			
			sendJson.put("end_time", endEdit.getText().toString());			
			sendJson.put("people", peopleSp.getSelectedItem().toString());
			sendJson.put("remark", remarkEidt.getText().toString());			
			sendJson.put("abnormalId", abnormalId);			
			sendJson.put("eventId", eventId);
			sendJson.put("send_time", format.format(new Date()));
			sendJson.put("workshop_id",workshop_id);
			sendJson.put("set_start_time_code", "mo_1");
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	 public static StringBuffer getRequestData(Map<String, String> params, String encode) {
	      StringBuffer stringBuffer = new StringBuffer();        //存储封装好的请求体信息
	      try {
	            for(Map.Entry<String, String> entry : params.entrySet()) {
	                stringBuffer.append(entry.getKey())
	                      .append("=")
	                      .append(URLEncoder.encode(entry.getValue(), encode))
	                      .append("&");
	            }
	           stringBuffer.deleteCharAt(stringBuffer.length() - 1);    //删除最后的一个"&"
	        } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return stringBuffer;
	    }
}
