package com.example.taskeditor.taskeditor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.taskeditor.RankdeEventAdapter;
import com.example.adapter.taskeditor.RankdeEventAdapter.Callback;
import com.example.bean.taskeditor.MyListView;
import com.example.bean.taskeditor.Tool;
import com.example.generalizationdemo.R;
import com.example.util.taskeditor.GeneralLayout;
import com.google.gson.JsonArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class TaskDetailsActivity extends Activity implements Callback {
	
	
	private JSONObject json;
	
	private LinearLayout l;
	private int currentPosition;
	private ArrayList<Tool> eventRank ;
	private RankdeEventAdapter adapter;
	private MyListView mylistview;
	private Button yes;
	private Button no;
	private GeneralLayout generalLayout;
	
	private Intent intent;
	private String nameIntent;
	
	private String numIntent;
	private String activity_name;
	
	private JSONObject sendJSON;
	
	public static JSONObject resultFirst;
	private String sendResult = "";
	private String taskID = "";
	public  String factory_id = com.example.generalizationdemo.HomePageya.factory_id;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskeditor_test_testformat);
        l = (LinearLayout) findViewById(R.id.l);
        intent = getIntent();
        Bundle b = new Bundle();
        b = intent.getExtras();
        nameIntent = b.getString("taskName");
        
        numIntent = b.getString("taskNum");

		taskID = b.getString("task_num");
		 
		
	

        eventRank = new ArrayList<Tool>();
        adapter = new RankdeEventAdapter(this,eventRank , -1, TaskDetailsActivity.this);
        mylistview = new MyListView(this);
		
		activity_name = b.getString("activity_name");
//	      if(activity_name.equals("CheckActivity")){
//	    	  json = CheckActivity.json;
//	      }else{
	    	  json = MainActivity.json; 
//	      }
        generalLayout = new GeneralLayout(l, json, TaskDetailsActivity.this, numIntent, nameIntent, eventRank, adapter, mylistview);
        generalLayout.allView();
        
       
       // mylistview = generalLayout.getMylistview();
        //adapter = generalLayout.getAdapter();
        currentPosition = generalLayout.getCurrentPosition();
        
//        sendJSON = new JSONObject();
        yes = (Button) findViewById(R.id.task_details_button_yes);
        no = (Button) findViewById(R.id.task_details_button_no);
       
//        if(!nameIntent.equals("1"))
        	numIntent = b.getString("taskNum");
        
        yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(TaskDetailsActivity.this);
				
				builder.setTitle("注意：");
				builder.setMessage("是否完成编辑任务");
				builder.setNegativeButton("否", null);
				builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(final DialogInterface dialog, int which) {
					
					if("".equals(generalLayout.getAddTaskName())){
						dialog.dismiss();
						AlertDialog.Builder builder_name = new AlertDialog.Builder(TaskDetailsActivity.this);
						
						builder_name.setTitle("注意：");
						builder_name.setMessage("任务名称不能为空！！！！！");
						builder_name.setNegativeButton("确定", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						});
						builder_name.create().show();
					}
					else{
						dialog.dismiss();
						
						
					final Thread t1 = new Thread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
//							sendJSON = generalLayout.getSendJson();
					
							StringBuilder builderEvent = new StringBuilder();
							String sendEven = "";
							for (int i = 0; i < eventRank.size(); i++) {
								
								builderEvent.append(eventRank.get(i).getContent_id()+",");
								System.out.println("要发送的事件为："+eventRank.get(i).getContent_id());
							}
							if(builderEvent.length() > 1)
								sendEven = builderEvent.substring(0, builderEvent.length() - 1);
							System.out.println("编辑任务完成后返给后台的json："+ builderEvent.toString());
//							try {
//								sendJSON.put("任务周期", "每一天");
//								sendJSON.put("event", event);
//								sendJSON.put("任务事件", builderEvent.toString());
//							} catch (JSONException e) {
								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
							OutputStream out = null;
							BufferedReader reader = null;
							try {
								
//								//"http://59.110.157.159:8080/water/mission.req?action=poyunupload"
								URL url = new URL("http://123.206.16.157:8080/water/arrange.req?action=feedback");
								HttpURLConnection conn = (HttpURLConnection) url.openConnection();
								conn.setRequestMethod("POST");
								conn.setDoOutput(true);
								conn.setDoInput(true);
								conn.setConnectTimeout(5000);
								conn.setReadTimeout(5000);
								out = conn.getOutputStream();
//								sendJSON.toString()
								SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								String set_time = formate.format(new Date());
								if("" == taskID || null == taskID){
									
									out.write(("任务事件="+sendEven+"&任务名称="+generalLayout.getAddTaskName()+
											"&任务序号="+generalLayout.getTask_num()+
											"&任务描述="+generalLayout.getTask_dec()+"&任务级别="+generalLayout.getTask_level()+
											"&任务来源="+generalLayout.getTask_sou()+"&所涵盖地点="+generalLayout.getTask_loca()+
											"&身份验证="+generalLayout.getTask_check()+"&工具列表="+generalLayout.getTask_tool()+
											"&配件列表="+generalLayout.getTask_parets()+"&type="+"insert"
											+"&任务性质="+generalLayout.getTask_nature()+"&mission_type="+"日常巡检"
											
											+"&set_time="+set_time+"&factory_id="+factory_id).getBytes());
									
								}else{
									out.write(("任务事件="+sendEven+"&任务名称="+generalLayout.getAddTaskName()+
											"&任务序号="+generalLayout.getTask_num()+
											"&任务描述="+generalLayout.getTask_dec()+"&任务级别="+generalLayout.getTask_level()+
											"&任务来源="+generalLayout.getTask_sou()+"&所涵盖地点="+generalLayout.getTask_loca()+
											"&身份验证="+generalLayout.getTask_check()+"&工具列表="+generalLayout.getTask_tool()+
											"&配件列表="+generalLayout.getTask_parets()+"&type="+"update"+"&id="+taskID+"&任务性质="+generalLayout.getTask_nature()
											+"&mission_type="+"日常巡检"+"&set_time="+set_time+"&factory_id="+factory_id).getBytes());
									
									System.out.println("任务生成我给后台的数据："+"任务事件="+sendEven+"&任务名称="+generalLayout.getAddTaskName()+
											"&任务序号="+generalLayout.getTask_num()+
											"&任务描述="+generalLayout.getTask_dec()+"&任务级别="+generalLayout.getTask_level()+
											"&任务来源="+generalLayout.getTask_sou()+"&所涵盖地点="+generalLayout.getTask_loca()+
											"&身份验证="+generalLayout.getTask_check()+"&工具列表="+generalLayout.getTask_tool()+
											"&配件列表="+generalLayout.getTask_parets()+"&type="+"update"+"&id="+taskID+"&任务性质="+generalLayout.getTask_nature()
											+"&factory_id="+factory_id);
								}
								
								
//								Log.e("result","&任务描述="+generalLayout.getTask_dec()+"任务事件="+builderEvent.toString()+ "&所涵盖地点="+generalLayout.getTask_loca());
							
								
								
								
								
								
								System.out.println("编辑任务完成后返给后台的json"+out.toString());
								reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
								String line;
								StringBuilder builder = new StringBuilder();
								while((line = reader.readLine())!=null){
									builder.append(line);
								}
								
								System.out.println("提交标记任务的数据后后台返给我的数据"+builder);
								JSONObject j_builder = new JSONObject(builder.toString());
								
//								Intent intent = new Intent(TaskDetailsActivity.this, MainActivity.class);
								if(nameIntent == null){
									intent.putExtra("tasknamechang", generalLayout.getAddTaskName());
									System.out.println("添加任务时得到的任务名称"+generalLayout.getAddTaskName());
								}
								
								sendResult = j_builder.getString("result");
//								if(j_builder.getString("result").equals("10000")){
//									
////									setResult(20,intent);
////									finish();
//								}else{
//									 Toast.makeText(TaskDetailsActivity.this, "请选择任务事件", Toast.LENGTH_LONG).show();
//								}
								
								
								
							} catch (Exception e) {
//								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					t1.start();
					
					final Thread t2 = new Thread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							OutputStream out = null;
							BufferedReader reader = null;
							try {
								t1.join();
//								URL url = new URL("http://59.110.157.159:8080/water/mission.req?action=setMission");
								URL url = new URL("http://123.206.16.157:8080/water/arrange.req?action=arrsetmis");
								HttpURLConnection conn = (HttpURLConnection) url.openConnection();
								conn.setRequestMethod("POST");
								conn.setDoOutput(true);
								conn.setDoInput(true);
								conn.setConnectTimeout(5000);
								conn.setReadTimeout(5000);
								out = conn.getOutputStream();

								out.write(("factory_id="+factory_id).getBytes());
								reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
								String line;
								StringBuilder builder = new StringBuilder();
								while((line = reader.readLine())!=null){
									builder.append(line);
								}
								resultFirst = new JSONObject(builder.toString());
								if("10000".equals(sendResult)){
									Intent intent = new Intent(TaskDetailsActivity.this, MainActivity.class);
									setResult(20,intent);
									finish();
								}else {
									//不加这句这个dialog框不显示
									Looper.prepare();
									AlertDialog.Builder builder_event = new AlertDialog.Builder(TaskDetailsActivity.this);
									
									builder_event.setTitle("注意：");
									builder_event.setMessage("任务事件不能为空！！！！！");
									builder_event.setNegativeButton("确定", new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface dialog, int which) {
											// TODO Auto-generated method stub
											dialog.dismiss();
										}
									});
									builder_event.create().show();
									Looper.loop();
								}
								
							}catch(Exception e){
								e.printStackTrace();
							}
						}
					});
					t2.start();
					}

					
				}
				});
				Dialog dialog = builder.create();
				dialog.show();
				
				
				
			}
		});
        no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(TaskDetailsActivity.this);
				
				builder.setTitle("注意：");
				builder.setMessage("是否放弃编辑任务？");
				builder.setNegativeButton("否", null);
				builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					
					Intent intent = new Intent(TaskDetailsActivity.this, MainActivity.class);
					intent.putExtra("change", "nochange");
					setResult(10, intent);
					finish();
					}
				});
				Dialog dialog = builder.create();
				dialog.show();
			}
		});
	}

	@Override
	public void click(View v) {
		// TODO Auto-generated method stub
		int curPosition;
		int mCurPosition;
		//eventRank = generalLayout.getEventRank();
		switch (v.getId()) {
		case R.id.item:
			System.out.println("事件安排的回调函数listview回调成功了吗？");
			mCurPosition = (Integer) v.getTag(R.id.tag_item_click);
			currentPosition = mCurPosition;
			System.out.println("我点击的是第几个item:"+mCurPosition);
			adapter.refresh(currentPosition); 
            break;
		case R.id.ranked_event_imagedown:
			curPosition = (Integer) v.getTag();
			if(curPosition != eventRank.size() - 1){
				Tool downFirst = eventRank.get(curPosition);
//				String downFirst = eventRank.get(curPosition).getToolName();
				Tool downSecond = eventRank.get(curPosition + 1);
				eventRank.remove(curPosition + 1);
				eventRank.remove(curPosition);
				eventRank.add(curPosition,downSecond);
				eventRank.add(curPosition + 1,downFirst);
				currentPosition = curPosition + 1;
				
				adapter.refresh(currentPosition);
				
			}
			break;
		case R.id.ranked_event_imageup:
			curPosition = (Integer) v.getTag();
			if(curPosition != 0){
			Tool upFirst = eventRank.get(curPosition);
			Tool upSecond = eventRank.get(curPosition - 1);
			eventRank.remove(curPosition);
			eventRank.remove(curPosition - 1);
			eventRank.add(curPosition - 1,upFirst);
			eventRank.add(curPosition,upSecond);
			currentPosition = curPosition - 1;
			adapter.refresh(currentPosition);
			}
			break;

		default:
			break;
		
	}
	}
}
