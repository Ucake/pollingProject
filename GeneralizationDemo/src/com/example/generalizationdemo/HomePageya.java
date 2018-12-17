package com.example.generalizationdemo;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;


import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import com.alibaba.fastjson.JSONArray;
import com.cement.SQLite.AbnormalDBHelper;
import com.cement.SQLite.CheckListDBHelper;
import com.cement.SQLite.EventDBHelper;
import com.cement.SQLite.FriendDBHelper;
import com.cement.SQLite.RecentAbnormalDBHelper;
import com.cement.SQLite.SearchListDBHelper;
import com.cement.chart.BarChartActivity;
import com.cement.check.CheckListActivity;
import com.cement.data.AbnormalJson;
import com.cement.data.CheckJson;
import com.cement.data.MapJson;
import com.cement.data.RecentAbnormalJson;
import com.cement.thread.AbnormalThread;
import com.cement.thread.CheckThread;
import com.cement.thread.FactoryChartThread;
import com.cement.thread.MapThread;
import com.cement.thread.MissionChartThread;
import com.cement.thread.SearchThread;
import com.cement.thread.ExceptionChartThread;
//import com.example.denglu.MainActivity;
import com.example.generalizationdemo.R;
import com.generalization.chart.ChartActivity;
import com.google.gson.JsonObject;
import com.sensorinfor.thread.EmergencyThread;
import com.sensorinfor.thread.GraphThread;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class HomePageya extends Activity{
	
	public static HomePageya instance = null;
	
//	private Button schedule;
	private Button mission_implement;
	private Button mission_born;
	private Button map;
	private Button search;
	private Button check;
	private Button staticstics;
	private Button abnormal;
	private Button logout;
	private Button sensor;
	
	private Handler handler_check;
	private Handler handler_search;
	private Handler handler_map;
	private Handler handler_abnormal;
	private Handler handler_statistics;
	private Handler handler_mission;
	private Handler handler_factory;
	
	public static SearchListDBHelper searchdbhelper;
	public static CheckListDBHelper checkdbhelper;
	public static EventDBHelper eventdbhelper;
	public static FriendDBHelper frienddbhelper;
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	public static AbnormalDBHelper abnormaldbhelper;
	public static RecentAbnormalDBHelper recentabnormaldbhelper;
	
	public static String statistics;
	public static String mission_statistics;
	public static String factory_statistics;
	private String TEL;
	public static String factory_id;
	public static String workshop_id;
	
	//用于传感器页面的json
	public static JSONObject json_urgency;
	public static JSONObject json_run;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage3);
        
        JPushInterface.setDebugMode(true);//如果时正式版就改成false
        JPushInterface.init(this);
        
        instance = this;
        
        
        preferences = getSharedPreferences("user", MODE_PRIVATE);
       
        factory_id = preferences.getString("factory_id", null);
        workshop_id = preferences.getString("workshop_id", null);
        Log.v("zahaimeyoune", factory_id);
        
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        TEL = preferences.getString("tel", null);
        eventdbhelper = new EventDBHelper(this, "event4", 1);
        frienddbhelper = new FriendDBHelper(this, "friend4", 1);
        checkdbhelper = new CheckListDBHelper(this, "check4", 1);
        searchdbhelper = new SearchListDBHelper(this, "search4", 1);
        abnormaldbhelper = new AbnormalDBHelper(this, "abnormal7", 1);
        recentabnormaldbhelper = new RecentAbnormalDBHelper(this, "recentabnormal7", 1);
        
        //排班
//        schedule = (Button) findViewById(R.id.schedule);
//        schedule.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//   	        	intent.setClass(HomePageya.this, com.example.wechatsample.MainActivity.class);
//   	        	startActivity(intent);
//			}
//		});
        
        //任务生成
        mission_born = (Button) findViewById(R.id.mission_born);
        search = (Button) findViewById(R.id.search);
        mission_born.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
   	        	intent.setClass(HomePageya.this, com.example.taskeditor.taskeditor.MainActivity.class);
   	        	startActivity(intent);
			}
		});
        //任务执行
        mission_implement = (Button) findViewById(R.id.mission_implement);
        mission_implement.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
   	        	intent.setClass(HomePageya.this, OrdersActivity.class);
   	        	startActivity(intent);
			}
		});
        //任务查询
        search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				handler_search = new Handler(){
//					
//					public void handleMessage(Message msg){
//						super.handleMessage(msg);
//						if (msg.what == 12138) {
//							
//						String backMsg = msg.obj.toString();
//						
//						Log.v("sanciwoshou", backMsg);
//						try{
//							
//							JSONObject toor = new JSONObject(backMsg);
//							JSONObject result1 = new JSONObject(toor.getString("result"));
//							String result = result1.getString("result");
//							if(result.equals("10000")){
//								
//								MapJson mapjson = new MapJson();
//								mapjson.mapsearch(backMsg);
								
								Intent intent = new Intent();
								intent.setClass(HomePageya.this, InquireActivity.class);
								startActivity(intent);
								
//							}
//							
//						} catch (JSONException e) {
//					        e.printStackTrace();
//					    
//						}
//					}
//				}
//			};
				
//				String value = "phone=13811173356";
//				SearchThread thread = new SearchThread(handler_search, value);
//				new Thread(thread).start();
				
			}
		});
        //地图
        map = (Button) findViewById(R.id.map);
//        map.setVisibility(View.INVISIBLE);
        map.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				handler_map = new Handler(){
					
					public void handleMessage(Message msg){
						super.handleMessage(msg);
						if (msg.what == 12138) {
							
						String backMsg = msg.obj.toString();
						
						Log.v("sanciwoshou", backMsg);
						try{
							
							JSONObject toor = new JSONObject(backMsg);
							JSONObject result1 = new JSONObject(toor.getString("result"));
							String result = result1.getString("result");
							if(result.equals("10000")){
								
								MapJson mapjson = new MapJson();
								mapjson.mapsearch(backMsg);
								
								Intent intent = new Intent();
								intent.setClass(HomePageya.this, MapActivity.class);
								startActivity(intent);
								
							}
							
						} catch (JSONException e) {
					        e.printStackTrace();
					    
						}
				
				
						}
					}
				};
				
				String value = "phone=" + TEL;
				MapThread thread = new MapThread(handler_map, value);
				new Thread(thread).start();
				
			}
		});
        //统计图表
        staticstics = (Button) findViewById(R.id.statistics);
        staticstics.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				handler_statistics = new Handler(){
					
					public void handleMessage(Message msg){
						super.handleMessage(msg);
						if (msg.what == 12138) {
							
							statistics = msg.obj.toString();
						
						Log.v("ruguoniyetingshuo", statistics);
						try{
							
							JSONObject toor = new JSONObject(statistics);
							String result = toor.getString("result");
							if(result.equals("10000")){
								
								handler_mission = new Handler(){
									
									public void handleMessage(Message msg){
										super.handleMessage(msg);
										if (msg.what == 12138) {
											
										mission_statistics = msg.obj.toString();
										
										Log.v("yexunijiuyaoyudaoleiyushandiandebaofengquan", mission_statistics);
										try{
											
											JSONObject toor = new JSONObject(mission_statistics);
//											JSONObject result1 = new JSONObject(toor.getString("result"));
											String result = toor.getString("result");
											if(result.equals("10000")){
												
												
												handler_factory = new Handler(){
													
													public void handleMessage(Message msg){
														super.handleMessage(msg);
														if (msg.what == 12138) {
															
														factory_statistics = msg.obj.toString();
														
														Log.v("bieshuohuiyizhishihaishishenlou", factory_statistics);
														try{
															
															JSONObject toor = new JSONObject(factory_statistics);
//															JSONObject result1 = new JSONObject(toor.getString("result"));
															String result = toor.getString("result");
//															if(result.equals("10000")){
																
																Intent intent = new Intent();
																intent.setClass(HomePageya.this, ChartActivity.class);
																startActivity(intent);
																
//															}
															
														} catch (JSONException e) {
													        e.printStackTrace();
													    
														}
												
												
														}
													}
												};
												
												String value = "workshop=车间2";
												FactoryChartThread thread = new FactoryChartThread(handler_factory, value);
												new Thread(thread).start();
												
												
												
//												Intent intent = new Intent();
//												intent.setClass(HomePageya.this, ChartActivity.class);
//												startActivity(intent);
												
											}
											
										} catch (JSONException e) {
									        e.printStackTrace();
									    
										}
								
								
										}
									}
								};
								
								String value = "";
								MissionChartThread thread = new MissionChartThread(handler_mission, value);
								new Thread(thread).start();
								
								
//								Intent intent = new Intent();
//								intent.setClass(HomePageya.this, ChartActivity.class);
//								startActivity(intent);
								
							}
							
						} catch (JSONException e) {
					        e.printStackTrace();
					    
						}
				
				
						}
					}
				};
				
				String value = "";
				ExceptionChartThread thread = new ExceptionChartThread(handler_statistics, value);
				new Thread(thread).start();
				
//				Intent intent = new Intent();
//				intent.setClass(HomePageya.this, ChartActivity.class);
//				startActivity(intent);
			}
		});
        //审核
        check = (Button) findViewById(R.id.check);
        check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				handler_check = new Handler(){
					
					public void handleMessage(Message msg){
						super.handleMessage(msg);
						if (msg.what == 12138) {
							
						String backMsg = msg.obj.toString();
						
						Log.v("sanciwoshou", backMsg);
						try{
							
							JSONObject toor = new JSONObject(backMsg);
							JSONObject result1 = new JSONObject(toor.getString("result"));
							String result = result1.getString("result");
							if(result.equals("10000")){
								
								CheckJson checkjson = new CheckJson();
								checkjson.checkjson(backMsg);
								
								Intent intent = new Intent();
								intent.setClass(HomePageya.this, CheckListActivity.class);
								startActivity(intent);
								
							}else if(result.equals("10002")){
								Toast.makeText(HomePageya.this, "没有数据！", Toast.LENGTH_SHORT).show();
							}
							
						} catch (JSONException e) {
					        e.printStackTrace();
					    
						}
					}
				}
			};
				
				String value = "phone=" + TEL + "&factory_id="+factory_id +"&workshop_id=" + workshop_id;
				Log.v("ganggangdefenshoubuxiangshizhende", value);
				CheckThread thread = new CheckThread(handler_check, value);
				new Thread(thread).start();
			}
		});
        //异常管理
        abnormal = (Button) findViewById(R.id.abnormal);
        abnormal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				handler_abnormal = new Handler(){
					
					public void handleMessage(Message msg){
						super.handleMessage(msg);
						if (msg.what == 12138) {
							
						String backMsg = msg.obj.toString();
						
						Log.v("jieleyanwobuxiguan", backMsg);
						try{
							
							JSONObject toor = new JSONObject(backMsg);
							String result = toor.getString("result");
							if(result.equals("10000")){
								
								AbnormalJson abnormaljson = new AbnormalJson();
								abnormaljson.abnormal(backMsg);
								
								Intent intent = new Intent();
								intent.setClass(HomePageya.this, AbnormalManagementActivity.class);
								startActivity(intent);
								
							}
							
						} catch (JSONException e) {
					        e.printStackTrace();
					    
						}
					}
				}
			};
				
				String value = "phone="+TEL+"&type=first" + "&factory_id=" + factory_id + "&workshop_id=" + workshop_id;
				Log.v("yuhezhishibiaomian", value);
				AbnormalThread thread = new AbnormalThread(handler_abnormal, value);
				new Thread(thread).start();
				
			}
		});
        
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				HomePageya.instance.finish();
				MainActivity.instance.finish();
				
				MainActivity.accounts.setText("");
				MainActivity.password.setText("");
				MainActivity.message.setText("");
				
				 editor = preferences.edit();
				 editor.clear();
				 editor.putBoolean("isFirstRun", false);
				 editor.commit();
				 Toast.makeText(HomePageya.this, "请重新登录！", Toast.LENGTH_SHORT).show();
			
				 Intent intent2 = new Intent();
				 intent2.setClass(HomePageya.this, MainActivity.class);
				 startActivity(intent2);
			}
		});
        //传感器
        sensor = (Button) findViewById(R.id.sensor);
        sensor.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				
				Handler mHandler = new Handler(){

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						super.handleMessage(msg);
						if(msg.arg1 != HttpURLConnection.HTTP_OK){
							Toast.makeText(HomePageya.this, "连接失败，请重试。", 2);
						}
						if(msg.what == 1){
							String backMsg = msg.obj.toString();
							System.out.println("这是设备列表返回内容"+backMsg);
							try {
								json_urgency = new JSONObject(backMsg);
								
								String result;

									result = json_urgency.getString("result");
									if(result.equals("10000")){
										Handler handler = new Handler(){

											@Override
											public void handleMessage(
													Message msg) {
												// TODO Auto-generated method stub
												super.handleMessage(msg);
												if(msg.what == 2){
													String backMsg = msg.obj.toString();
													System.out.println("这是图表返回内容"+backMsg);
													try {
														json_run = new JSONObject(backMsg);
													
													String result;
						
														result = json_run.getString("result");
														if(result.equals("10000")){
															
																
																Intent intent = new Intent(HomePageya.this,com.sensorinfor.main.MainActivity.class);
																startActivity(intent);
															
														}else {
															Toast.makeText(HomePageya.this, "请求失败，请重试！！！", Toast.LENGTH_SHORT).show();
															
														}						
													} catch (JSONException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
												}
											}
											
//											
											
												
											
										};	
//										String sendStr = "sensorinfo="+"传感器信息"+"&startTime="+""+"&endTime="+""+"&tel="+"123"+"&info="+"map_info";
										String sendStr = "";
//										String urlStr = "http://193.112.62.125:80/water/app.req?action=graph";
										String urlStr = "http://193.112.62.125:80/userselect/graph.do";
								       GraphThread g = new GraphThread(sendStr, handler, urlStr);
								       new Thread(g).start();
										
									}else if("10002".equals(result)){
										Toast.makeText(HomePageya.this, "没有数据！", Toast.LENGTH_SHORT).show();
									}else {
										Toast.makeText(HomePageya.this, "请求失败，请重试！！！", Toast.LENGTH_SHORT).show();
									}
//								
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
//					
				};
//				// TODO Auto-generated method stub
//				
//			       String sendStr_u = "sensorinfo="+"传感器信息"+"&time="+""+"&number="+""+"&startTime="+""+"&endTime="+""+"&tel="+"123"+"&info="+"urgency_info";
			       String sendStr_u = "";
//					String urlStr_u = "http://193.112.62.125:80/water/app.req?action=emergency";
					String urlStr_u = "http://193.112.62.125:80/userselect/emergency.do";
			       EmergencyThread e = new EmergencyThread(sendStr_u, mHandler, urlStr_u);
			       new Thread(e).start();
				
			}
		});
        
}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			
			quit();
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void quit() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("退出");
		builder.setIcon(R.drawable.quit);
		builder.setMessage("确认要退出智能巡检平台？");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				
		    	MainActivity.instance.finish();
		    	HomePageya.instance.finish();

				finish();

			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_plus) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	
			}

		

	  
