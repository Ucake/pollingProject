package com.sensorinfor.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.ChartTouchListener.ChartGesture;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sensorinfo.adapter.AttributeAdapter;
import com.sensorinfor.bean.Attribute;
import com.sensorinfor.bean.Constant;
import com.sensorinfor.thread.HistoryThread;
import com.example.generalizationdemo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EquipmentDetails extends Activity  {
	private LinearLayout l;
	private JSONObject json;
	private Bitmap mIconExpand;
	private Bitmap mIconCollapse;
	private Bitmap mIconUp;
	private Bitmap mIconDown;
	private Intent intent;
	private String EquipmentName;
	private String project_name;
	
	private String butStr;
	private String sendTimeStr;
	private Constant mConstant;
	private  Map<String,String> mHashMap_name; 
	private  Map<String,String> mHashMap_unit;
	private  Map<String,String> mHashMap;
	private  Map<String,String> mHaMap_name_unit;
	
	private ArrayList<String> allTime = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.equipmentdetails_main);
		
		 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().penaltyLog().build());
		l = (LinearLayout) findViewById(R.id.equipmentdetails_l);
		intent = getIntent();
		Bundle b = new Bundle();
		b = intent.getExtras();
		EquipmentName = b.getString("e");
		this.setTitle(EquipmentName);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String time = format.format(new Date());
	     butStr = time;
	     sendTimeStr = time;
//	     threeMap.put("freq", "频率")
	     mConstant = new Constant();
	     mHashMap_name = mConstant.getmHashMap_name();
	     mHashMap_unit = mConstant.getmHashMap_unit();
	     mHashMap = mConstant.getmHashMap();
	     mHaMap_name_unit = mConstant.getmHaMap_name_unit();
	     System.out.println("mHashMap_unit:");
		try {
//			json = new JSONObject(b.getString("json"));
			json = MainActivity.sendjson;
			System.out.println("传过来的json："+json);
//			String result = json.getString("result");
//			if(result.equals("10000")){
//			JSONObject equipment = json.getJSONObject("equipment");
			StringBuilder builder = new StringBuilder();
			
			String equipment_name = json.getString("equipment_name");
//			String remark_1 = equipment.getString("remark_1");
//			String remark_2 = equipment.getString("remark_2");
//			builder.append(equipment_name+"/n");
//			builder.append(remark_1+"/n");
//			builder.append(remark_1+"/n");
//			int font_size = json.getInt("font_size");
//			String font_color = json.getString("font_color");
//			TextView title = new TextView(this);
//			title.setText(builder.toString());
//			title.setTextSize(font_size);
//			title.setTextColor(Color.parseColor(font_color));
//			l.addView(title);
//			setHalvingLine();
			JSONArray project_array = json.getJSONArray("project");
			//这是第一级的图片
//			mIconExpand = BitmapFactory.decodeResource(this.getResources(),
//					R.drawable.jian);
//			mIconCollapse = BitmapFactory.decodeResource(this.getResources(),
//					R.drawable.plus);
			mIconExpand = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.ups);
			mIconCollapse = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.downs);
			
			
			for(int i = 0; i < project_array.length(); i ++){
				JSONObject attribute = project_array.getJSONObject(i);
				String project_name = attribute.getString("project_name");
				int attribute_font_size = 28;

//				add_view();
				final View first = (View) add_event_first("  "+project_name,attribute_font_size);
				final ImageView icon_first = (ImageView) first.findViewById(R.id.icon_first);
				View v_first = add_viewList();
				//这是二级的图片
//				mIconUp = BitmapFactory.decodeResource(this.getResources(), R.drawable.ups);
//				mIconDown = BitmapFactory.decodeResource(this.getResources(), R.drawable.downs);
				mIconUp = BitmapFactory.decodeResource(this.getResources(), R.drawable.jian);
				mIconDown = BitmapFactory.decodeResource(this.getResources(), R.drawable.plus);
				JSONArray specific_project_array = attribute.getJSONArray("specific_project");
				ArrayList<Attribute> list = new ArrayList<Attribute>();
//				ArrayList<ImageView> list_icon = new ArrayList<ImageView>();
//				ArrayList<View> list_view = new ArrayList<View>();
				for(int j = 0; j < specific_project_array.length(); j ++){
					JSONObject specific_project = specific_project_array.getJSONObject(j);
					String specific_name = specific_project.getString("specific_name");
					String specific_value = specific_project.getString("specific_value");
					String specific_time = specific_project.getString("time").substring(5,19);
					String sensor_id = specific_project.getString("sensor_id");
					
//					JSONArray item_array = specific_project.getJSONArray("item");
					
//					add_view();
//					add_viewList();
					if("三相电".equals(project_name)|| "加速度".equals(project_name)){
						String[] first_spilt = specific_value.split(",");
						for (int k = 0; k < first_spilt.length; k++) {
							String[] sencond_spilt = first_spilt[k].split(":");
//							for (int l = 0; l < sencond_spilt.length; l++) {
//							if(sencond_spilt.length >= 2)
								list.add(new Attribute(equipment_name, mHashMap.get(project_name), "  "+specific_name,mHashMap_name.get(sencond_spilt[0]),sencond_spilt[1]+mHaMap_name_unit.get(sencond_spilt[0]) , sensor_id, specific_time,"0",mHashMap_unit.get(sencond_spilt[0]),specific_name));
								System.out.println("第三级的名字是啥："+mHashMap_name.get(sencond_spilt[0])+"对应的值是："+sencond_spilt[1]);
//							}
						}
						specific_value = "";
						specific_time = "";
					}
					
//						list_view.add(sencodeItem);
//					int location = 0;
//					if(project_name.equals("温度"))
//						location = 0;
//					else if(project_name.equals("气压"))
//						location = 1;
//					else if(project_name.equals("油压"))
//						location = 2;
//					else if(project_name.equals("三相电"))
//						location = 3;
//					else if(project_name.equals("加速度"))
//						location = 4;
					final View sencodeItem = add_event_second(specific_name,18,specific_time,sensor_id,project_name,equipment_name,specific_value,mHashMap.get(project_name));
					final ImageView icon_second = (ImageView) sencodeItem.findViewById(R.id.icon_second); 
//						final ImageView icon_second = (ImageView) sencodeItem.findViewById(R.id.equipment_sec_view); 
//						list_icon.add(icon_second);
						final View v_second = add_viewList();
						 
					
					
//					for (int k = 0; k < item_array.length(); k++) {
//						JSONObject item = item_array.getJSONObject(k);
//						String item_name = item.getString("item_name");
//						String item_value = item.getString("item_value");
//						String item_time = item.getString("item_time");
//						String item_id = item.getString("item_id");
//						if(project_name.equals("温度"))
//							list.add(new Attribute(equipment_name, "tem", "  "+specific_name, item_name, "0", sensor_id, item_time, item_id));
//						else if(project_name.equals("气压"))
//							list.add(new Attribute(equipment_name, "pre", "  "+specific_name, item_name, "0",sensor_id, item_time, item_id));
//						else if(project_name.equals("油压"))
//							list.add(new Attribute(equipment_name, "oil", "  "+specific_name, item_name, "0",sensor_id, item_time, item_id));
//						else if(project_name.equals("三相电"))
//							list.add(new Attribute(equipment_name, "three", "  "+specific_name, item_name, "0",sensor_id, item_time, item_id));
//						else if(project_name.equals("加速度"))
//							list.add(new Attribute(equipment_name, "acc", "  "+specific_name, item_name, "0",sensor_id, item_time, item_id));
//					}
					
					
					
					
//					add_view();
//					add_viewList();
					final View myListView = addMyListView(list);
					sencodeItem.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if(myListView.getVisibility() == 0){
								myListView.setVisibility(View.GONE);
								icon_second.setImageBitmap(mIconDown);
							}else {
								myListView.setVisibility(View.VISIBLE);
								icon_second.setImageBitmap(mIconUp);
							}
						}
					});
					
					first.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if(sencodeItem.getVisibility() == 0){
								sencodeItem.setVisibility(View.GONE);
								
								myListView.setVisibility(View.GONE);
								icon_first.setImageBitmap(mIconCollapse);
								v_second.setVisibility(View.GONE);
							}else {
								sencodeItem.setVisibility(View.VISIBLE);
								icon_first.setImageBitmap(mIconExpand);
								v_second.setVisibility(View.VISIBLE);
								icon_second.setImageBitmap(mIconDown);
							}
						}
					});
				}
				
			}
//		}
		add_view();
		} catch (Exception e) {
//			 TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void add_view(){
		View v = new View(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,70); 
		v.setBackgroundColor(Color.parseColor("#FFFFF0"));
		l.addView(v,params);
	}
	private View addMyListView(ArrayList<Attribute> list) {
		// TODO Auto-generated method stub
		MyListView myListView = new MyListView(this);
		AttributeAdapter adapter = new AttributeAdapter(list, EquipmentDetails.this,EquipmentName);
//		adapter.setOnInnerItemOnClickListener(this);
		myListView.setAdapter(adapter);
//		myListView.setVisibility(View.GONE);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 450);
//		myListView.setPadding(70, 0, 0, 0);
		myListView.setDivider(new ColorDrawable(Color.parseColor("#F0F0F0")));
		myListView.setDividerHeight(3);
		myListView.setVisibility(View.GONE);
		l.addView(myListView);
		return myListView;
		
	}
	
	
	public View add_viewList(){
		View v = new View(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,3); 
		v.setBackgroundColor(Color.parseColor("#F0F0F0"));
		l.addView(v,params);
		return v;
	}
	private View add_event_first(String title, int size) {
		
		LinearLayout l1 = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,100); 
		l1.setOrientation(LinearLayout.HORIZONTAL);
		l1.setBackgroundColor(Color.parseColor("#FFFAF0"));
		l1.setLayoutParams(params);
		ImageView icon = new ImageView(this);
		icon.setId(R.id.icon_first);
		final TextView tv = new TextView(this);
//		tv.setBackgroundColor(Color.parseColor("#00BFFF"));
		
		//让textview占满剩下的空间
		LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT); 
		paramsText.weight = 1.0f;
		tv.setLayoutParams(paramsText);
		
		icon.setPadding(2, 2, 2, 5);
		tv.setGravity(Gravity.CENTER_VERTICAL);
		tv.setTypeface(Typeface.MONOSPACE);
//		tv.setTextColor(Color.parseColor(color));
		tv.setTextSize(20);
		l1.addView(icon);
		l1.addView(tv);
		l.addView(l1);
		
			tv.setText(title);
			icon.setImageBitmap(mIconExpand);
			
			return l1;
			
		
		
			
		
		
		}
	private View add_event_second(final String specific_name, int size,String time,final String sensor_id,final String project_name,final String equipment_name,String specific_value,final String sendP) {
		
		LinearLayout l1 = new LinearLayout(this);
		LinearLayout l2 = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,80); 
		LinearLayout.LayoutParams params_1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT); 
		params_1.setMargins(40, 0, 0, 0);
		l2.setLayoutParams(params);
		l2.setBackgroundColor(Color.parseColor("#FFFAFA"));
		l1.setOrientation(LinearLayout.HORIZONTAL);
		l1.setLayoutParams(params_1);
//		RelativeLayout r = (RelativeLayout) this.findViewById(R.id.equtipment_sec_r);
//		ImageView icon = (ImageView) this.findViewById(R.id.equipment_sec_view);
//		TextView tv_name = (TextView) this.findViewById(R.id.name);
//		TextView tv_time = (TextView) this.findViewById(R.id.time);
//		TextView tv_value = (TextView) this.findViewById(R.id.value);
		ImageView icon = new ImageView(this);
		final TextView tv = new TextView(this);
//		tv.setBackgroundColor(Color.parseColor("#00BFFF"));
		
		//让textview占满剩下的空间
		LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT); 
		paramsText.weight = 1.0f;
		tv.setLayoutParams(paramsText);
		
		icon.setPadding(2, 2, 2, 5);
		
		
//		tv.setTextColor(Color.parseColor(color));
		tv.setTextSize(18);
		l1.addView(icon);
////		tv_name.setText(specific_name);
////		tv_name.setText("123");
//		tv_time.setText(time);
//		tv_value.setText(specific_value);
		l1.addView(tv);
		
//		l1.addView(r);
		l2.addView(l1);
		l.addView(l2);
		
			tv.setText("  "+specific_name+"    "+time+"  "+specific_value);
//			tv.setTypeface(Typeface.createFromAsset(getAssets(), "NotoSansCJKjp-Light.otf"));
			icon.setImageBitmap(mIconDown);
			icon.setId(R.id.icon_second);
//			icon.setId(R.id.equipment_sec_view);
			final ProgressDialog pro = new ProgressDialog(EquipmentDetails.this);
			pro.setTitle("提示：");
			pro.setMessage("正在加载...");
			final Handler  mHandler_history = new Handler(){

				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					if(msg.what == 3){
						String backMsg = msg.obj.toString();
//						System.out.println("这是历史曲线返回内容"+backMsg);
						pro.dismiss();
						JSONObject json;
						try {
							json = new JSONObject(backMsg);
							String result = json.getString("result");
							 if(result.equals("10000")){
//								 Toast.makeText(EquipmentDetails.this, sendP, Toast.LENGTH_LONG).show();
							String tile = EquipmentName + project_name + specific_name;
								 newDialog(json,mHashMap_unit.get(sendP),sensor_id,sendP,tile);
							 }else if(result.equals("10001")){
								 Toast.makeText(EquipmentDetails.this, "请求失败，请重试！！！", Toast.LENGTH_SHORT).show();
							 }
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
					}
				}
			}; 
			 boolean flag = false;
//			 final String[] sendP = {"tem","pre","oil","three","acc"};
//			 String sendProject_name = "";
			if(project_name.equals("温度")){
//				sendProject_name = "tem";
				flag = true;
			}
				
			else if(project_name.equals("气压")){
//				sendProject_name = "pre";
				flag = true;
			}
				
			else if(project_name.equals("油压")){
//				sendProject_name = "oil";
				flag = true;
			}
				
			else if(project_name.equals("三相电")){
//				sendProject_name = "three";
				flag = false;
			}
				
			else if(project_name.equals("加速度")){
//				sendProject_name = "acc";
				flag = false;
			}
//				l1.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						Toast.makeText(EquipmentDetails.this, project_name, Toast.LENGTH_LONG).show();
//					}
//				});
			if(flag){
			l1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
						pro.show();
					
					 String sendStr = "sensor_id="+sensor_id+"&project_name="+sendP+"&equipment_name="+equipment_name
							 +"&time="+sendTimeStr+"&item_id="+"";
				     System.out.println("这是发送的内容："+sendStr);
				     String urlStr = "http://193.112.62.125:80/water/app.req?action=history";
				     HistoryThread h = new HistoryThread(sendStr, mHandler_history, urlStr);
				     new Thread(h).start();
				}
			});
			}
			
			return l2;
		
		
		
		
	}

	private void newDialog(JSONObject json,String unit,String sensor_id,String sendP,String tile)
			throws JSONException {
		
		 AlertDialog.Builder builder = new AlertDialog.Builder(EquipmentDetails.this);
		 
		 builder.setTitle(tile+":");
			View view = LayoutInflater.from(EquipmentDetails.this).inflate(
					R.layout.history_curve, null);
			final LineChart mLineChart = (LineChart) view.findViewById(R.id.history_curve_linechar);
			TextView timeText = (TextView) view.findViewById(R.id.history_curve_time);
			 LineData mLineData2 = getLineData(json.getJSONArray("data"),unit,null,null);
		     showChart(mLineChart, mLineData2, timeText,json.getJSONArray("data"),unit,sensor_id,sendP);
		

		     
		     builder.setView(view);
		     final AlertDialog  dialog = builder.create();
		     dialog .show();

	}
	


	private void showChart(final LineChart lineChart, LineData lineData, final TextView time,JSONArray data,final String unit,final String sensor_id,final String sendP) {    
        setLineChar(time,lineChart, lineData, data.length(),0);
       System.out.println("alltime的长度是："+allTime.size()+"lineChart.getLowestVisibleXIndex():"+lineChart.getLowestVisibleXIndex()+"lineChart.getHighestVisibleXIndex():"+lineChart.getHighestVisibleXIndex());
       
    	
    lineChart.setOnChartGestureListener(new OnChartGestureListener() {
    	 
		
		@Override
		public void onChartTranslate(MotionEvent me, float dX, float dY) {
			// TODO Auto-generated method stub
			String time_str = "";
			if(allTime.size() > lineChart.getLowestVisibleXIndex()){
				if(lineChart.getLowestVisibleXIndex() < 0){
					
			    	time_str = allTime.get(0);
			    	time_str = time_str + "~";
			    	time_str += allTime.get(lineChart.getHighestVisibleXIndex());
			    	time.setTextSize(14);
			    	time.setText(time_str);
				}else if(lineChart.getHighestVisibleXIndex() > allTime.size() - 1){
					
					time_str = allTime.get(lineChart.getLowestVisibleXIndex());
					time_str = time_str + "~";
					time_str += allTime.get(allTime.size() - 1);
					time.setTextSize(14);
					time.setText(time_str);
				}else{
					time_str = allTime.get(lineChart.getLowestVisibleXIndex());
					time_str = time_str + "~";
					time_str += allTime.get(lineChart.getHighestVisibleXIndex());
					time.setTextSize(14);
					time.setText(time_str);
				}
			}
//	    	System.out.println("lineChart.getLowestVisibleXIndex():"+lineChart.getLowestVisibleXIndex());
//	    	System.out.println("lineChart.getHighestVisibleXIndex():"+lineChart.getHighestVisibleXIndex());
			
		}
		
		@Override
		public void onChartSingleTapped(MotionEvent me) {
			// TODO Auto-generated method stub
			String time_str = "";
			if(allTime.size() > lineChart.getLowestVisibleXIndex()){
				if(lineChart.getLowestVisibleXIndex() < 0){
					
			    	time_str = allTime.get(0);
			    	time_str = time_str + "~";
			    	time_str += allTime.get(lineChart.getHighestVisibleXIndex());
			    	time.setTextSize(14);
			    	time.setText(time_str);
				}else if(lineChart.getHighestVisibleXIndex() > allTime.size() - 1){
					
					time_str = allTime.get(lineChart.getLowestVisibleXIndex());
					time_str = time_str + "~";
					time_str += allTime.get(allTime.size() - 1);
					time.setTextSize(14);
					time.setText(time_str);
				}else{
					time_str = allTime.get(lineChart.getLowestVisibleXIndex());
					time_str = time_str + "~";
					time_str += allTime.get(lineChart.getHighestVisibleXIndex());
					time.setTextSize(14);
					time.setText(time_str);
				}
			}
		}
		
		@Override
		public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
			// TODO Auto-generated method stub
			
			String time_str = "";
			if(allTime.size() > lineChart.getLowestVisibleXIndex()){
				if(lineChart.getLowestVisibleXIndex() < 0){
					
			    	time_str = allTime.get(0);
			    	time_str = time_str + "~";
			    	time_str += allTime.get(lineChart.getHighestVisibleXIndex());
			    	time.setTextSize(14);
			    	time.setText(time_str);
				}else if(lineChart.getHighestVisibleXIndex() > allTime.size() - 1){
					
					time_str = allTime.get(lineChart.getLowestVisibleXIndex());
					time_str = time_str + "~";
					time_str += allTime.get(allTime.size() - 1);
					time.setTextSize(14);
					time.setText(time_str);
				}else{
					time_str = allTime.get(lineChart.getLowestVisibleXIndex());
					time_str = time_str + "~";
					time_str += allTime.get(lineChart.getHighestVisibleXIndex());
					time.setTextSize(14);
					time.setText(time_str);
				}
			}
//	    	System.out.println("lineChart.getLowestVisibleXIndex():"+lineChart.getLowestVisibleXIndex());
//	    	System.out.println("lineChart.getHighestVisibleXIndex():"+lineChart.getHighestVisibleXIndex());
		}
		
		@Override
		public void onChartLongPressed(MotionEvent me) {
			// TODO Auto-generated method stub
			String time_str = "";
			if(allTime.size() > lineChart.getLowestVisibleXIndex()){
				if(lineChart.getLowestVisibleXIndex() < 0){
					
			    	time_str = allTime.get(0);
			    	time_str = time_str + "~";
			    	time_str += allTime.get(lineChart.getHighestVisibleXIndex());
			    	time.setTextSize(14);
			    	time.setText(time_str);
				}else if(lineChart.getHighestVisibleXIndex() > allTime.size() - 1){
					
					time_str = allTime.get(lineChart.getLowestVisibleXIndex());
					time_str = time_str + "~";
					time_str += allTime.get(allTime.size() - 1);
					time.setTextSize(14);
					time.setText(time_str);
				}else{
					time_str = allTime.get(lineChart.getLowestVisibleXIndex());
					time_str = time_str + "~";
					time_str += allTime.get(lineChart.getHighestVisibleXIndex());
					time.setTextSize(14);
					time.setText(time_str);
				}
			}
		}
		
		@Override
		public void onChartGestureStart(MotionEvent me,
				ChartGesture lastPerformedGesture) {
			// TODO Auto-generated method stub
			
			String time_str = "";
			if(allTime.size() > lineChart.getLowestVisibleXIndex()){
				if(lineChart.getLowestVisibleXIndex() < 0){
					
			    	time_str = allTime.get(0);
			    	time_str = time_str + "~";
			    	time_str += allTime.get(lineChart.getHighestVisibleXIndex());
			    	time.setTextSize(14);
			    	time.setText(time_str);
				}else if(lineChart.getHighestVisibleXIndex() > allTime.size() - 1){
					
					time_str = allTime.get(lineChart.getLowestVisibleXIndex());
					time_str = time_str + "~";
					time_str += allTime.get(allTime.size() - 1);
					time.setTextSize(14);
					time.setText(time_str);
				}else{
					time_str = allTime.get(lineChart.getLowestVisibleXIndex());
					time_str = time_str + "~";
					time_str += allTime.get(lineChart.getHighestVisibleXIndex());
					time.setTextSize(14);
					time.setText(time_str);
				}
			}
	        
	        
	   
	    
		}
		
		@Override
		public void onChartGestureEnd(MotionEvent me,
				ChartGesture lastPerformedGesture) {
			// TODO Auto-generated method stub
			
//			String time_str = "";
//	    	time_str = allTime.get(lineChart.getLowestVisibleXIndex());
//	    	time_str = time_str + "~";
//	    	time_str += allTime.get(lineChart.getHighestVisibleXIndex());
//	    	time.setTextSize(14);
//	    	time.setText(time_str);
			int size = lineChart.getLineData().getXVals().size();
			
			final int rightXIndex = lineChart.getHighestVisibleXIndex();
			final LineData l8 = lineChart.getData();
			
			
			final LineDataSet mLineDataSet = (LineDataSet) l8.getDataSetByIndex(l8.getDataSetCount() - 1);
			
			if(lastPerformedGesture == ChartGesture.DRAG){
				String time_str = "";
				if(allTime.size() > lineChart.getLowestVisibleXIndex()){
					if(lineChart.getLowestVisibleXIndex() < 0){
						
				    	time_str = allTime.get(0);
				    	time_str = time_str + "~";
				    	time_str += allTime.get(lineChart.getHighestVisibleXIndex());
				    	time.setTextSize(14);
				    	time.setText(time_str);
					}else if(lineChart.getHighestVisibleXIndex() > allTime.size() - 1){
						
						time_str = allTime.get(lineChart.getLowestVisibleXIndex());
						time_str = time_str + "~";
						time_str += allTime.get(allTime.size() - 1);
						time.setTextSize(14);
						time.setText(time_str);
					}else{
						time_str = allTime.get(lineChart.getLowestVisibleXIndex());
						time_str = time_str + "~";
						time_str += allTime.get(lineChart.getHighestVisibleXIndex());
						time.setTextSize(14);
						time.setText(time_str);
					}
				}
				if(rightXIndex == size || size - 1 == rightXIndex){
//					System.out.println("该添加线了");
					
						final ProgressDialog pro = new ProgressDialog(EquipmentDetails.this);
						pro.setTitle("注意：");
						pro.setMessage("正在加载，请稍后.......");
						pro.show();
						final Handler  mHandler_history = new Handler(){
	
							@Override
							public void handleMessage(Message msg) {
								// TODO Auto-generated method stub
								super.handleMessage(msg);
								if(msg.what == 3){
									String backMsg = msg.obj.toString();
									pro.dismiss();
	//								System.out.println("这是历史曲线返回内容"+backMsg);
									JSONObject json;
									try {
										LineData mlineData = null;
								    	 
										json = new JSONObject(backMsg);
										String result = json.getString("result");
										 if(result.equals("10000")){
											 JSONArray data = json.getJSONArray("data");
											 if(null == data|| data.length() < 1){
												 Toast.makeText(EquipmentDetails.this, "没有更多数据了。", Toast.LENGTH_SHORT).show();
											 }else{
												 System.out.println("运行了吗？");	
												 int xOffset = l8.getXVals().size();
												 mlineData = getLineData(data,unit, l8.getXVals(),mLineDataSet.getYVals());
												 lineChart.clear();
												 lineChart.setData(mlineData);
												 
												 System.out
														.println("mlineData.getXValCount()是："+mlineData.getXValCount()+"mlineData.getYValCount()"+mlineData.getYValCount());
												 
												 setLineChar(time,lineChart, mlineData, mlineData.getYValCount(),rightXIndex); 
												 lineChart.notifyDataSetChanged();
												 lineChart.invalidate();
//												 	String time_str = "";
//											    	time_str = allTime.get(lineChart.getLowestVisibleXIndex());
//											    	time_str = time_str + "~";
//											    	time_str += allTime.get(lineChart.getHighestVisibleXIndex());
//											    	time.setTextSize(14);
//											    	time.setText(time_str);
//											        System.out.println("lineChart.getLowestVisibleXIndex():"+lineChart.getLowestVisibleXIndex()+"~对应的值："+allTime.get(lineChart.getLowestVisibleXIndex()));
											 }
										 }else if(result.equals("10001")){
											 Toast.makeText(EquipmentDetails.this, "请求失败，请重试！！！", Toast.LENGTH_SHORT).show();
										 }
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									 
								}
							}
						};
						
						String sendStr = "sensor_id="+sensor_id+"&project_name="+sendP
								 +"&time="+sendTimeStr;
					     System.out.println("这是发送的内容："+sendStr);
					     String urlStr = "http://193.112.62.125:80/water/app.req?action=history";
					     HistoryThread h = new HistoryThread(sendStr, mHandler_history, urlStr);
					     new Thread(h).start();
						
				}
			
			}
			
		}
		
		@Override
		public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onChartDoubleTapped(MotionEvent me) {
			// TODO Auto-generated method stub
			
		}
	});
      }    
        
	        
	    /**  
	     * 生成一个数据  
	     * @param count 表示图表中有多少个坐标点  
	     * @param range 用来生成range以内的随机数  
	     * @return  
	     */  
	    private LineData getLineData(JSONArray data,String unit,List<String> xValues_be,List<Entry> yValues_be) {    
	    	
	        ArrayList<String> xValues = new ArrayList<String>();       
	        ArrayList<Entry> yValues = new ArrayList<Entry>(); 
	        if(yValues_be != null){
	        	
	        	try {
	        		for (int i = 0; i < data.length() + yValues_be.size(); i++) {
	        			if(i < yValues_be.size()){
	        				yValues.add(yValues_be.get(i));
	        				xValues.add(xValues_be.get(i));
	        			}else{
	        				JSONObject dataO = data.getJSONObject(i);
	        				allTime.add(dataO.getString("time").substring(0, dataO.getString("time").length()- 2));
	        				String time = dataO.getString("time").substring(11,dataO.getString("time").length()-2);
	        				String value = dataO.getString("value");
	        				yValues.add(new Entry(Float.parseFloat(value), i));
	        				xValues.add(time);
	        			}
	        		}
	        	}catch (Exception e) {
	        		// TODO: handle exception
	        	}
	        }else{
	        	try{
		        	for (int i = 0; i < data.length(); i++) {
	        			
	        				JSONObject dataO = data.getJSONObject(i);
	        				allTime.add(dataO.getString("time").substring(0, dataO.getString("time").length()- 2));
	        				String time = dataO.getString("time").substring(11,dataO.getString("time").length()-2);
	        				String value = dataO.getString("value");
	        				yValues.add(new Entry(Float.parseFloat(value), i));
	        				xValues.add(time);
	        			
	        		}
	        	}catch (Exception e) {
	        		// TODO: handle exception
	        	}
	        }
	        
//	    System.out.println("allTime的长度："+allTime.size()+"xValues的长度："+xValues.size()+"yValues的长度："+yValues.size() );
	        // create a dataset and give it a type    
	        // y轴的数据集合    
	        LineDataSet lineDataSet = new LineDataSet(yValues, unit /*显示在比例图上*/);    
	        // mLineDataSet.setFillAlpha(110);    
	        // mLineDataSet.setFillColor(Color.RED);    
	    
	        //用y轴的集合来设置参数    
	        lineDataSet.setLineWidth(1.75f); // 线宽    
	        lineDataSet.setCircleSize(3f);// 显示的圆形大小    
	        lineDataSet.setColor(Color.parseColor("#BBFFFF"));// 显示颜色    
	        lineDataSet.setCircleColor(Color.parseColor("#BBFFFF"));// 圆形的颜色    
	        lineDataSet.setCircleColorHole(Color.parseColor("#BBFFFF"));
	        lineDataSet.setHighLightColor(Color.YELLOW); // 高亮的线的颜色    
	        lineDataSet.setValueFormatter(new ValueFormatter() {
				
				@Override
				public String getFormattedValue(float value, Entry entry, int dataSetIndex,
						ViewPortHandler viewPortHandler) {
					// TODO Auto-generated method stub
					return ""+value; 
				}
			});
//	        List<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();    
//	        lineDataSets.add(lineDataSet); // add the datasets    
	    
	        // create a data object with the datasets    
	        LineData lineData = new LineData(xValues, lineDataSet);    
	        
	        return lineData;    
	    }   
	    private void setLineChar(TextView time,LineChart lineChart,LineData lineData,int data,int xOffset){
			lineChart.setDrawBorders(false);  //是否在折线图上添加边框    
		    
	        // no description text    
	        lineChart.setDescription("");// 数据描述    
	        // 如果没有数据的时候，会显示这个，类似listview的emtpyview    
	        lineChart.setNoDataTextDescription("You need to provide data for the chart.");    
	            
	        // enable / disable grid background    
//	        lineChart.setDrawGridBackground(false); // 是否显示表格颜色    
	        lineChart.setDrawGridBackground(true); // 是显示表格颜色    
	        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度    
	           
//	        lineChart.setGridBackgroundColor(Color.parseColor("#BBFFFF"));
	        // enable touch gestures    
	        lineChart.setTouchEnabled(true); // 设置是否可以触摸    
	    
	        // enable scaling and dragging    
	        lineChart.setDragEnabled(true);// 是否可以拖拽    
	        lineChart.setScaleEnabled(true);// 是否可以缩放    
	    
	        // if disabled, scaling can be done on x- and y-axis separately    
	        lineChart.setPinchZoom(false);//     
	    
	        lineChart.setBackgroundColor(Color.parseColor("#F8F8FF"));// 设置背景    
	        lineChart.getAxisRight().setEnabled(false);//不显示y轴右边的值
	    
	        // add data    
	        lineChart.setData(lineData); // 设置数据    
	    
	        // get the legend (only possible after setting data)    
	        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的    
	    
	        // modify the legend ...    
	        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);    
//	        mLegend.setForm(LegendForm.CIRCLE);// 样式    
	        mLegend.setForm(LegendForm.CIRCLE);// 样式    
	        mLegend.setFormSize(6f);// 字体    
	        mLegend.setTextColor(Color.BLACK);// 颜色    
//	      mLegend.setTypeface(mTf);// 字体
	        Matrix matrix = new Matrix();
//	        float f = data;
//	        System.out.println("data的float类型是："+f);
	        matrix.postScale(data / 10f, 1f);
	        System.out.println("data / 10f是："+data / 10f);
//	        System.out.println("datafloat类型除完10后是："+(data / 2f));
	        lineChart.getViewPortHandler().refresh(matrix, lineChart, false);
	        lineChart.animateX(2500); // 立即执行的动画,x轴    
	        XAxis xAxis = lineChart.getXAxis();
	        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置x轴在底部
	        lineChart.moveViewToX(xOffset);
	        Legend legend = lineChart.getLegend();
	        legend.setTextSize(16);
	        
//	        lineChart.setBackgroundColor(Color.BLACK);
//	        lineChart.setDragDecelerationEnabled(false);
	        String time_str = "";
	        System.out.println("开始的偏移量xOffset是"+xOffset);
	        if(xOffset > 0)
	        	xOffset = xOffset - 1;
	        System.out.println("lineChart.getLowestVisibleXIndex()+xOffset是："+lineChart.getLowestVisibleXIndex()+xOffset);
	        int num = lineChart.getLowestVisibleXIndex()+xOffset;
	        System.out.println(num);
	    	time_str = allTime.get(lineChart.getLowestVisibleXIndex()+xOffset);
	    	time_str = time_str + "~";
	    	if(lineChart.getHighestVisibleXIndex()+xOffset >= allTime.size())
	    		time_str += allTime.get(allTime.size() - 1);
	    	else
	    	time_str += allTime.get(lineChart.getHighestVisibleXIndex()+xOffset);
	    	time.setTextSize(14);
	    	time.setText(time_str);
	       System.out.println(allTime.get(lineChart.getLowestVisibleXIndex()+xOffset));
		}
}

