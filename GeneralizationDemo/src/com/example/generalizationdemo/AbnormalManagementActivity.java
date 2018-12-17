package com.example.generalizationdemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.cement.SQLite.AbnormalDBHelper;
import com.cement.SQLite.RecentAbnormalDBHelper;
import com.cement.abnormalmanagement.AbnormalDetailActivity;
import com.cement.abnormalmanagement.AbnormalListActivity;
import com.cement.abnormalmanagement.List20Adapter;
import com.cement.data.AbnormalJson;
import com.cement.thread.AbnormalThread;
import com.example.generalizationdemo.R;
import com.example.generalizationdemo.DateTimePickerDialogUtil;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class AbnormalManagementActivity extends Activity implements OnClickListener{
	
	private EditText start_time_abnormal;
	private EditText end_time_abnormal;
	private Button check_abnormal;
	
	private SharedPreferences preferences;
	private String TEL;
	private String abnormal;
	private Cursor cursor;
	
	List20Adapter listItemAdapter;
	private Spinner abnormal_status;
	private ArrayAdapter<String> arr_adapter_abnormal_status;
	private List<String> data_adapter_abnormal_status;
	private String string_abnormal_status;
	
	private Handler handler_abnormal;
	public static AbnormalDBHelper abnormaldbhelper;
	private RecentAbnormalDBHelper recentabnormaldbhelper;
	private String abnormal_time;
	private String abnormal_person;
	private String abnormal_check_point;
	private String abnormal_id;
	private String factory_id;
	private String abnormal_statuss;
	private String workshop_id;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abnormal_page);
        
        abnormaldbhelper = new AbnormalDBHelper(this, "abnormal7", 1);
        recentabnormaldbhelper = new RecentAbnormalDBHelper(this, "recentabnormal7", 1);
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        TEL = preferences.getString("tel", null);
        factory_id = preferences.getString("factory_id", null);
        workshop_id = preferences.getString("workshop_id", null);
        
        start_time_abnormal = (EditText) findViewById(R.id.start_time_abnormal);
        end_time_abnormal = (EditText) findViewById(R.id.end_time_abnormal);
        check_abnormal = (Button) findViewById(R.id.check_abnormal);
        
        abnormal_status = (Spinner) findViewById(R.id.abnormal_status);
        
        
        start_time_abnormal.setOnClickListener(this);
	    end_time_abnormal.setOnClickListener(this);
	    check_abnormal.setOnClickListener(this);
	    
	    
	    ListView list20 = (ListView) findViewById(R.id.list20);
		ArrayList<HashMap<String, Object>> listData=new ArrayList<HashMap<String,Object>>();
//		String []time={"9.2","9.3","9.4","9.5","9.6","9.6","9.6"};
//		String []event={"除氧器","汽缸","发电机","除氧器","给水泵","疏水泵","给水泵"};
//		String []worker={"王明","李方","郑宏","刘伟","张华","李方","刘伟"};
//		String []color={"#66ffff","#ccff66","#ff0033","#ccff66","#66ffff","#ff0033","#66ffff"};
		
		cursor = abnormaldbhelper.getReadableDatabase().rawQuery("select * from abnormal where state =?", new String[]{"00"});
        while (cursor.moveToNext()) {
        	abnormal_time =  cursor.getString(cursor.getColumnIndex("abnormal_time"));
        	abnormal_person =  cursor.getString(cursor.getColumnIndex("abnormal_person"));
        	abnormal_check_point = cursor.getString(cursor.getColumnIndex("abnormal_check_point"));
        	abnormal_id = cursor.getString(cursor.getColumnIndex("abnormal_id"));
        	abnormal_statuss = cursor.getString(cursor.getColumnIndex("abnormal_status"));
        	if(abnormal_statuss.equals("1")){
        		abnormal_statuss = "未处理";
        	}else{
        		abnormal_statuss = "已处理";
        	}
        	
        	Log.v("OOOOOOOOOOOOOOOOOO", abnormal_time + abnormal_person);
		
	
			HashMap<String, Object> map = new HashMap<String, Object>();
    		map.put("friend_image", R.drawable.warning_triangle_128px);
    		map.put("time",abnormal_time);
    		map.put("abnormal_statuss", abnormal_statuss);
    		map.put("id", abnormal_id);
    		map.put("person", "上报人:"+abnormal_person);
    		
    		
    		//20180426 注释了一下
    		listData.add(map);
		}
		listItemAdapter = new List20Adapter(AbnormalManagementActivity.this, listData);
    	list20.setAdapter(listItemAdapter);
        
        
        ContentValues cv = new ContentValues();
		cv.put("state", "11");
		abnormaldbhelper.getReadableDatabase().update("abnormal", cv, "taskNum!=0", null);
        
	    data_adapter_abnormal_status = new ArrayList<String>();
	    data_adapter_abnormal_status.add("问题型");
	    data_adapter_abnormal_status.add("隐患型");
	    data_adapter_abnormal_status.add("报警型");
	    arr_adapter_abnormal_status= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_adapter_abnormal_status);
	        
	        //设置样式
	    arr_adapter_abnormal_status.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        //加载适配器
	    abnormal_status.setAdapter(arr_adapter_abnormal_status);
	    abnormal_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){    
	             public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {    
	                 // TODO Auto-generated method stub    
	                 /* 将所选mySpinner 的值带入myTextView 中*/   
	            	 string_abnormal_status = arr_adapter_abnormal_status.getItem(arg2);
	             }    
	             public void onNothingSelected(AdapterView<?> arg0) {    
	                 // TODO Auto-generated method stub    
	            	 string_abnormal_status ="NONE";    
	                 arg0.setVisibility(View.VISIBLE);    
	             }    
	         }); 
	       
	    abnormal_status.setOnTouchListener(new Spinner.OnTouchListener(){    
	              public boolean onTouch(View v, MotionEvent event) {    
	                  // TODO Auto-generated method stub    
	                  /** 
	                   *  
	                   */  
	                  return false;    
	              }  
	          });    
	          /*下拉菜单弹出的内容选项焦点改变事件处理*/    
	    abnormal_status.setOnFocusChangeListener(new Spinner.OnFocusChangeListener(){    
	          public void onFocusChange(View v, boolean hasFocus) {    
	              // TODO Auto-generated method stub    

	          }    
	          });
	    
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.start_time_abnormal){
			String t1 = "";
			if (start_time_abnormal.getText().toString().equals("")){
				t1 = getTime();
			}
			else{
				t1 = start_time_abnormal.getText().toString();
			}
			DateTimePickerDialogUtil dialog1 = new DateTimePickerDialogUtil(this,t1,false);
			dialog1.dateTimePicKDialog(start_time_abnormal);
		}
		if(v.getId() == R.id.end_time_abnormal){
			String t2 = "";
			if(end_time_abnormal.getText().toString().equals("")){
				t2 = getTime();
			}
			else{
				t2 = end_time_abnormal.getText().toString();
			}
			DateTimePickerDialogUtil dialog2 = new DateTimePickerDialogUtil(this,t2,false);
			dialog2.dateTimePicKDialog(end_time_abnormal);
		}
		if(v.getId() == R.id.check_abnormal){
			if (start_time_abnormal.getText().toString().equals("")) {
				Calendar c = Calendar.getInstance();  
//				 year = c.get(Calendar.YEAR); 
//				 month = c.get(Calendar.MONTH) ; 
//				 day = c.get(Calendar.DAY_OF_MONTH)-1;
				
				Date as = new Date(new Date().getTime()-24*60*60*1000);
				  SimpleDateFormat matter1 = new SimpleDateFormat("yyyy-MM-dd");
				  String time = matter1.format(as);
				
				  start_time_abnormal.setText(time);

			}
			if (end_time_abnormal.getText().toString().equals("")) {				
				SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd");       
				Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间   
				String    str    =    formatter.format(curDate);  
				end_time_abnormal.setText(str);

			}else{
				
//				Toast.makeText(getApplicationContext(), "This is a Toast", Toast.LENGTH_SHORT).show();
				
				handler_abnormal = new Handler(){
					
					public void handleMessage(Message msg){
						super.handleMessage(msg);
						if (msg.what == 12138) {
							
						String backMsg = msg.obj.toString();
						
						Log.v("nidexiangqi", backMsg);
						try{
							
							JSONObject toor = new JSONObject(backMsg);
							String result = toor.getString("result");
							if(result.equals("10000")){
								
								AbnormalJson abnormaljson = new AbnormalJson();
								abnormaljson.abnormal(backMsg);
								
								Intent intent = new Intent();
								intent.setClass(AbnormalManagementActivity.this, AbnormalListActivity.class);
								
								startActivity(intent);
								
							}else if(result.equals("10001")){
//								Toast.makeText(getApplicationContext(), "输入参数有误！", Toast.LENGTH_LONG).show();
								Toast.makeText(AbnormalManagementActivity.this, "没有数据！", Toast.LENGTH_SHORT).show();
							}
							
						} catch (JSONException e) {
					        e.printStackTrace();
					    
						}
				
				
						}
					}
				};
				
				if(string_abnormal_status.equals("问题型")){
					abnormal = "1";
				}else if(string_abnormal_status.equals("隐患型")){
					abnormal = "2";
				}else if(string_abnormal_status.equals("报警型")){
					abnormal = "3";
				}
//				else if(string_abnormal_status.equals("请选择")){
//					abnormal = "1";
//				}
				
//				String value = "phone=13811173356&time1=2017-01-01&time2=2018-12-31&abnormal_status=1";
				String value = "phone="+TEL + "&time1="+ start_time_abnormal.getText()+"&time2="
						+ end_time_abnormal.getText()+"&abnormal_status="+abnormal+
						"&factory_id=" + factory_id + "&workshop_id=" + workshop_id; 
				Log.v("nizenmelianhuadoushuobuqingchu", value);
				AbnormalThread thread = new AbnormalThread(handler_abnormal, value);
				new Thread(thread).start();
				
			
			}
		}
	}
		
		public String getTime(){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(new Date());
		}
		
//		@Override
//		public boolean onKeyDown(int keyCode, KeyEvent event) {
//			// TODO Auto-generated method stub
//			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//				Intent intent2 = new Intent(AbnormalManagementActivity.this,HomePageya.class);
//				startActivity(intent2);
//			}
//			return super.onKeyDown(keyCode, event);
//		}
	
}
