package com.example.generalizationdemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.cement.data.SearchJson;
import com.cement.search.SearchListActivity;
import com.cement.thread.SearchThread;
import com.example.generalizationdemo.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InquireActivity extends Activity implements OnClickListener {
	
	private Spinner mission_condition;
	private Spinner mission_type;
	private ArrayAdapter<String> arr_adapter_condition;
	private List<String> data_adapter_condition;
	private String string_mission_condition;
	private String string_condition = "";
	
	private ArrayAdapter<String> arr_adapter_type;
	private List<String> data_adapter_type;
	private String string_mission_type;
	
	private EditText start_time;
	private EditText end_time;
	private Button check_inquire;
	
	private EditText worker_input;
	private EditText mission_name;
	
	private SharedPreferences preferences;
	private Handler handler_search;
	private String factory_id;
	private String workshop_id;
	
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.inquire_page);
	       
	       start_time = (EditText) findViewById(R.id.start_time);
	       end_time = (EditText) findViewById(R.id.end_time);
	       check_inquire = (Button) findViewById(R.id.check_inquire);
	       start_time.setOnClickListener(this);
	       end_time.setOnClickListener(this);
	       check_inquire.setOnClickListener(this);
	       
	       mission_condition = (Spinner) findViewById(R.id.mission_condition);
	       worker_input = (EditText) findViewById(R.id.worker_input);
	       mission_name = (EditText) findViewById(R.id.mission_name);
	       
	       preferences = getSharedPreferences("user", MODE_PRIVATE);
	       factory_id = preferences.getString("factory_id", null);
	       workshop_id = preferences.getString("workshop_id", null);
	       
	       data_adapter_condition = new ArrayList<String>();
	       data_adapter_condition.add("请选择");
	       data_adapter_condition.add("已完成");
	       data_adapter_condition.add("审核通过");
	       data_adapter_condition.add("审核未通过");
//	       data_adapter_condition.add("拒收");
//	       data_adapter_condition.add("拒收已处理");
	       arr_adapter_condition= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_adapter_condition);
//	        arr_adapter2= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list2);
	        
	        //设置样式
	       arr_adapter_condition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        //加载适配器
	       mission_condition.setAdapter(arr_adapter_condition);
	       mission_condition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){    
	             public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {    
	                 // TODO Auto-generated method stub    
	                 /* 将所选mySpinner 的值带入myTextView 中*/   
	             	string_mission_condition = arr_adapter_condition.getItem(arg2);
	             }    
	             public void onNothingSelected(AdapterView<?> arg0) {    
	                 // TODO Auto-generated method stub    
	            	 string_mission_condition ="NONE";    
	                 arg0.setVisibility(View.VISIBLE);    
	             }    
	         }); 
	       
	       mission_condition.setOnTouchListener(new Spinner.OnTouchListener(){    
	              public boolean onTouch(View v, MotionEvent event) {    
	                  // TODO Auto-generated method stub    
	                  /** 
	                   *  
	                   */  
	                  return false;    
	              }  
	          });    
	          /*下拉菜单弹出的内容选项焦点改变事件处理*/    
	       mission_condition.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){    
	          public void onFocusChange(View v, boolean hasFocus) {    
	              // TODO Auto-generated method stub    

	          }    
	          });
	       
	       mission_type = (Spinner) findViewById(R.id.mission_type);
	       data_adapter_type = new ArrayList<String>();
	       data_adapter_type.add("请选择");
	       data_adapter_type.add("日常巡检任务");
	       data_adapter_type.add("临时巡检任务");
	       data_adapter_type.add("维修任务");
	       arr_adapter_type= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_adapter_type);
	        
	        //设置样式
	       arr_adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        //加载适配器
	       mission_type.setAdapter(arr_adapter_type);
	       mission_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){    
	             public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {    
	                 // TODO Auto-generated method stub    
	                 /* 将所选mySpinner 的值带入myTextView 中*/   
	             	string_mission_type = arr_adapter_type.getItem(arg2);
	             }    
	             public void onNothingSelected(AdapterView<?> arg0) {    
	                 // TODO Auto-generated method stub    
	            	 string_mission_type ="NONE";    
	                 arg0.setVisibility(View.VISIBLE);    
	             }    
	         }); 
	       
	       mission_type.setOnTouchListener(new Spinner.OnTouchListener(){    
	              public boolean onTouch(View v, MotionEvent event) {    
	                  // TODO Auto-generated method stub    
	                  /** 
	                   *  
	                   */  
	                  return false;    
	              }  
	          });    
	          /*下拉菜单弹出的内容选项焦点改变事件处理*/    
	       mission_type.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){    
	          public void onFocusChange(View v, boolean hasFocus) {    
	              // TODO Auto-generated method stub    

	          }    
	          });
	       
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.start_time){
			String t1 = "";
			if (start_time.getText().toString().equals("")){
				t1 = getTime();
			}
			else{
				t1 = start_time.getText().toString();
			}
			DateTimePickerDialogUtil dialog1 = new DateTimePickerDialogUtil(this,t1,false);
			dialog1.dateTimePicKDialog(start_time);
		}
		if(v.getId() == R.id.end_time){
			String t2 = "";
			if(end_time.getText().toString().equals("")){
				t2 = getTime();
			}
			else{
				t2 = end_time.getText().toString();
			}
			DateTimePickerDialogUtil dialog2 = new DateTimePickerDialogUtil(this,t2,true);
			dialog2.dateTimePicKDialog(end_time);
			System.out.println("结束时间为："+end_time.getText());
		}
		if(v.getId() == R.id.check_inquire){
			if (start_time.getText().toString().equals("")) {
				Calendar c = Calendar.getInstance();  
//				 year = c.get(Calendar.YEAR); 
//				 month = c.get(Calendar.MONTH) ; 
//				 day = c.get(Calendar.DAY_OF_MONTH)-1;
				
				Date as = new Date(new Date().getTime()-24*60*60*1000);
				  SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
				  String time = matter1.format(as);
				
				  start_time.setText(time);

			}
			if (end_time.getText().toString().equals("")) {				
				SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd");       
				Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间   
				Calendar mCalendar = Calendar.getInstance();
				mCalendar.setTime(curDate);
				mCalendar.add(Calendar.DAY_OF_MONTH, 1);
				String    str    =    formatter.format(mCalendar.getTime());  
				end_time.setText(str);
				System.out.println("结束时间是："+str);

			}else{
				
				handler_search = new Handler(){
					
					public void handleMessage(Message msg){
						super.handleMessage(msg);
						if (msg.what == 12138) {
							
						String backMsg = msg.obj.toString();
						
						Log.v("sanciwoshou", backMsg);
						try{
							
							JSONObject toor = new JSONObject(backMsg);
//							JSONObject result1 = new JSONObject(toor.getString("result"));
							String result = toor.getString("result");
							if(result.equals("10000")){
								
								SearchJson searchjson = new SearchJson();
								searchjson.searchjson(backMsg);
								
								
								Intent intent = new Intent();
								intent.setClass(InquireActivity.this, SearchListActivity.class);
								startActivity(intent);
								
							}else if(result.equals("10002")){
								Toast.makeText(getApplicationContext(), "没有数据！", Toast.LENGTH_SHORT).show();
							}
						} catch (JSONException e) {
					        e.printStackTrace();
					    
						}
					}
				}
			};
				
				
//				Toast.makeText(getApplicationContext(), "This is a Toast", Toast.LENGTH_SHORT).show();
//				Intent intent = new Intent(InquireActivity.this,com.cement.search.SearchListActivity.class);
//				startActivity(intent);	
			
			if(string_mission_condition.equals("请选择")){
				string_condition = "";
			}else if(string_mission_condition.equals("已完成")){
				string_condition = "5";
			}else if(string_mission_condition.equals("审核通过")){
				string_condition = "6";
			}else if(string_mission_condition.equals("审核未通过")){
				string_condition = "7";
			}
//			else if(string_mission_condition.equals("拒收")){
//				string_condition = "2";
//			}else if(string_mission_condition.equals("拒收已处理")){
//				string_condition = "8";
//			}
			if(string_mission_type.equals("请选择")){
				string_mission_type = "";
			}
			
			String value = "time1=" + start_time.getText() + "&time2=" + end_time.getText() + "&worker=" + worker_input.getText()
					+"&mission_name=" + mission_name.getText()+"&mission_condition=" + string_condition+ "&mission_type=" +
					string_mission_type + "&factory_id=" + factory_id + "&workshop_id=" + workshop_id;
			
//			String value = "time1=" + start_time.getText() + "&time2=" + end_time.getText() + "&worker=" + worker_input.getText()
//					+"&mission_name=" + mission_name.getText()+"&mission_condition=" + string_condition+ "&mission_type=" +
//					string_mission_type + "&factory_id=" + factory_id;
			
//			String value = "time1=" + "2017-01-01" + "&time2=" + "2019-01-01" + "&worker=" + worker_input.getText()
//					+"&mission_name=" + mission_name.getText()+"&mission_level=" + string_mission_level + "&mission_type=" +
//					string_mission_type;
			
			Log.v("Last Christmas", value);
			
			SearchThread thread = new SearchThread(handler_search, value);
			new Thread(thread).start();
			
			}
		}
	}
		
		public String getTime(){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(new Date());
		}
		
}
