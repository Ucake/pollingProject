package com.example.taskeditor.taskeditor;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.taskeditor.MainAdapter;
import com.example.adapter.taskeditor.MainAdapter.InnerItemOnclickListener;
import com.example.bean.taskeditor.Task;
import com.example.generalizationdemo.R;

import android.text.style.BulletSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends Activity implements InnerItemOnclickListener {
	private static String TAG = "MainActivity.java";
	private ListView listView;
	private MainAdapter adapter;
	private ArrayList<Task> list;
	private Cursor cursor;
	public static int count = 1;
	private Button button;
	private JSONObject test;
	public static JSONObject json;
	private String taskID = "";
	public  String factory_id = com.example.generalizationdemo.HomePageya.factory_id;
	public String workshop_id = com.example.generalizationdemo.HomePageya.workshop_id;
    
    @SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.GINGERBREAD) protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskeditor_main);
        listView = (ListView) findViewById(R.id.list_task);
        button = (Button) findViewById(R.id.add_task);
        list = new ArrayList<Task>();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().penaltyLog().build());
        Log.e("factory_id是", factory_id);
        
        
        //一开始第一张页面中的任务list的线程
//        runContent("http://123.206.16.157:8080/water/mission.req?action=poyuntasklist","taskcreate=任务生成" , false, null, null,  0);
        runContent("http://123.206.16.157:8080/water/arrange.req?action=arrsetmis","factory_id="+factory_id +"&workshop_id="+workshop_id, false, null, null,0,"");
//        list = intiData();
        intiData(list);
        adapter = new MainAdapter(MainActivity.this, list);
        adapter.setOnInnerItemOnClickListener(this);
        listView.setAdapter(adapter); 
        //这个按钮的的监听实现的是添加任务
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ArrayList<String> array = new ArrayList<String>();

//				try {
//
//					JSONArray tasknature = test.getJSONArray("tasknature");
//
//					String[] tasklist_array = new String[tasknature.length()];
//					for (int i = 0; i < tasklist_array.length; i++) {
//						tasklist_array[i] = tasknature.getString(i);
//					}
//					for (int i = 0; i < tasklist_array.length; i++) {
//						array.add(tasklist_array[i]);
//						
//						
//					}
//					
//					
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				final String[] s = new String[array.size()];
//				final String[] s ={"巡检任务","维修任务"};
				final String[] s ={"巡检任务"};
				
//				for (int i = 0; i < s.length; i++) {
//					s[i] = array.get(i);
//				}
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("选择任务类型");
				builder.setSingleChoiceItems(s, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(final DialogInterface dialog, final int which) {
						// TODO Auto-generated method stub
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									try{
									String urlStr = "http://123.206.16.157:8080/water/mission.req?action=createformat";
									runContent(urlStr,"taskcreate=任务生成"+"&tasknature="+s[which]+"&factory_id="+factory_id+"&workshop_id="+workshop_id, true, "",String.valueOf(1) , 1,"");
									
										dialog.dismiss();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
							}
							
							}).start();
							  
//					 Intent	intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
					
//					intent.putExtra("taskName","1" );
//					intent.putExtra("tasknature", s[which]);
//					intent.putExtra("taskNum", String.valueOf(count + 1));
//					startActivityForResult(intent, 1);
					}
				});

				AlertDialog dialog = builder.create();
				dialog.show();
			}
		});
        
    }





	/**
	 * 这个类是这个页面中开启线程调用的
	 * @param urlStr 线程的URL
	 * @param sendStr 线程的发给后台的内容
	 * @param flag 是否需要跳转页面
	 * @param taskName 页面跳转时给下个页面的任务名字
	 * @param taskNum	页面跳转时给下个页面的任务序号
	 * @param num	这个是指是任务生成还是任务编辑还是任务下发，当num=1是任务生成，当num=2是任务编辑，当num=3是任务下发
	 */
	private void runContent(String urlStr,String sendStr,boolean flag,String taskName,String taskNum,int num,String task_num) {
		OutputStream out = null;
		BufferedReader reader = null;
		try {
//			URL url = new URL("http://59.110.157.159:8080/water/mission.req?action=setMission");
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
			System.out.println("第一个页面返回的builder"+builder.toString());
			if (!flag) {
				
//				JSONObject o = new JSONObject(builder.toString());
//					test = new JSONObject(builder.toString());
//				String essMsg = o.getString("essMsg");
//				String result = o.getString("result");
//				test = o.getJSONObject("data");
				test = new JSONObject(builder.toString());
				System.out.println("要传给任务类型弹出框的testjson"+test);
			}else{
				if(num == 2 || num == 3){
				JSONObject  test = new JSONObject(builder.toString());
				json = test.getJSONObject("data");
				}else{
					
				json = new JSONObject(builder.toString());
				}
				System.out.println("要传给第二个页面的json:"+ json);
//				String essMsg = json.getString("essMsg");
//				String result = json.getString("result");
				Intent intent ;
				if(num == 3){
					
					intent = new Intent(MainActivity.this, TaskSendActivity.class);
				}else{
					
					 intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
				}
				
					intent.putExtra("task_num", task_num);
				
				intent.putExtra("taskName", taskName);
				intent.putExtra("activity_name", "MainActivity");
				
				intent.putExtra("taskNum", taskNum);
				taskID = "";
				startActivityForResult(intent, num);
				
				
			}
			
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
    

   


	@Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
    	super.onActivityResult(requestCode, resultCode, data);
    	System.out.println("第二个页面返回来执行的onactivityresult方法");
    	switch (requestCode) {
		case 1:
			
			switch (resultCode) {
			case 20:
				System.out.println("这是第二个页面按确定按钮返回的");
//				list.add(new Task(String.valueOf(count + 1),data.getExtras().getString("tasknamechang"),""));
//				new Thread(new Runnable() {
//					
//					@Override
//					public void run() {
//						// TODO Auto-generated method stub
//						
//					}
//				}).start();
//				adapter = new MainAdapter(MainActivity.this, list);
//				adapter.setOnInnerItemOnClickListener(this);
//				listView.setAdapter(adapter);
//				adapter.notifyDataSetChanged();
//				count = list.size();
				test = TaskDetailsActivity.resultFirst;
				System.out.println("返回后的json："+test);
				list.clear();
				intiData(list);
				adapter.notifyDataSetChanged();
				listView.setAdapter(adapter);
				
				
				break;
			case 10:
				
				System.out.println("这是按返回按钮");
				break;
			default:
				break;
			}
			break;
		case 2:
			switch (resultCode){
			case 20:

				test = TaskDetailsActivity.resultFirst;
				System.out.println("返回后的json："+test);
				list.clear();
				intiData(list);
				adapter.notifyDataSetChanged();
				listView.setAdapter(adapter);
				break;
			case 10:
				

				break;
			default:
				break;
			}
			break;
		case 3:
			switch (resultCode){
			case 20:
				Intent intent = getIntent();
				Bundle bundler = intent.getExtras();
				final String taskname = bundler.getString("taskname");
				String tasktime = bundler.getString("tasktime");
				String time1 = bundler.getString("time1");
				final String taskpeople = bundler.getString("taskpeople");
				String time2 = bundler.getString("time2");
				String set_name = bundler.getString("set_name");
				
				String realTime = time1.substring(0, 10);
				final String realTimeDuan = time1.substring(10, time1.length());
				final String[] baitian = {"","mo_7","mo_1","mo_2","mo_3","mo_4","mo_5","mo_6"};
				final String[] wanshang = {"","af_7","af_1","af_2","af_3","af_4","af_5","af_6"};
				Timer mTimer = new Timer();
				SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
//				java.util.Date date = formate.parse(realTime);
				mTimer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Handler mHander = new Handler(){

							@Override
							public void handleMessage(Message msg) {
								// TODO Auto-generated method stub
								super.handleMessage(msg);
								if(msg.what == 123){
									String info = msg.obj.toString();
									
								}
							}
							
						};
						SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
			        	Date date = new Date();
			        	String sendTime  = sdf.format(date);
			        	 Calendar cal = Calendar.getInstance();  

			             cal.setTime(date);    

			             int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
			             String sendTimeDuan = "";
			             if("白天".equals(realTimeDuan)){
			            	 sendTimeDuan = baitian[dayWeek];
			             }else{
			            	 sendTimeDuan = wanshang[dayWeek];
			             }
			             
 
						String values = "taskname="+taskname+"&tasktime="+sendTimeDuan+"&time1="+sendTime+"&taskpeople="+taskpeople+"&time2="+"888"+"&set_name="+"";
						SendTaskThread thread = new SendTaskThread(mHander, values);
						new Thread(thread).start();
					}
				}, 24*60*60*1000, 24*60*60*1000);
				break;
			case 10:
				

				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
    	
    }

	/**
	 * 这个方法是第一个页面中的listview中的数据
	 * @return
	 */
	private  void intiData(ArrayList<Task> list) {
		// TODO Auto-generated method stub
//		ArrayList<Task> list = new ArrayList<Task>();

		try {

			JSONArray tasklist_array = test.getJSONArray("tasklist");
			
			for (int i = 0; i < tasklist_array.length(); i++) {
				JSONObject tasklist = tasklist_array.getJSONObject(i);
				String task_name = tasklist.getString("task_name");
				String task_num = tasklist.getString("task_num");
				list.add(new Task(""+(i + 1), task_name,task_num));
			}
			count = tasklist_array.length();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collections.sort(list);
		System.out.println("list的长度:"+list.size());
//		return list;
	}

	@Override
	public void itemClic(View v,final String name,final String num,final String task_num) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.editor_text:
			System.out.println("任务的id为："+task_num);
			new Thread(new Runnable() {
			
				@Override
				public void run() {
//					taskID = task_num;
					String urlStr = "http://123.206.16.157:8080/water/mission.req?action=setMission";
					try {
						runContent(urlStr, "taskname="+task_num+"&taskcreate="+"任务生成"+"&factory_id="+factory_id+"&workshop_id="+workshop_id, true, name, num,2,task_num);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
			}).start();
//			Intent	intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
//			intent.putExtra("taskName", name);
//			intent.putExtra("tasknature", "");
//			intent.putExtra("taskNum", num);
//			startActivityForResult(intent, 2);
			
			break;
//		case R.id.send_text:
//			new Thread(new Runnable() {
//				
//				@Override
//				public void run() {
//					String urlStr =  "http://123.206.16.157:8080/water/mission.req?action=setMission";
//					try {
//						runContent(urlStr, "taskname="+task_num+"&taskcreate="+"任务生成"+"&factory_id="+factory_id, true, name, num, 3,"");
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} 
//				}
//			}).start();
//			Intent	intent_3 = new Intent(MainActivity.this, TaskSendActivity.class);
//			intent_3.putExtra("taskName", name);
//			intent_3.putExtra("tasknature", "");
//			intent_3.putExtra("taskNum", num);
//			startActivityForResult(intent_3, 3);
//			break;

		default:
			break;
		}
		
	
	}

	

	
    
}
