package com.sensorinfor.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.SimpleFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sensorinfo.adapter.EquipmentAdapter;

import com.sensorinfor.bean.EquipmentInfo;
import com.sensorinfor.thread.EmergencyThread;
import com.sensorinfor.thread.GraphThread;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


 public class MainActivity extends Activity{
	private LinearLayout mLinearLayout;
	private JSONObject json_urgency = null;
	private JSONObject json_run = null;
	private ArrayList<EquipmentInfo> list_urgency = null;
	private ArrayList<EquipmentInfo> list = null;
	private int number = 0;
	private int number_run = 0;
//	public String[] color_array = {"#1E90FF","#00BFFF","#87CEEB","#4682B4"};
	public String[] color_array = {"#FFBBFF","#FF0000","#F0F0F0","#FFEFD5"};
	private Button bt_ur;
//	private MyListView myListView ;
	private Handler mHandler;
//	private int count = 0;

	public static JSONObject sendjson = null;
	
	//更新用的东西
	private PieChart mPieChart = null;
	private BarChart mBarChart = null;
	private LineChart mLineChart = null;
	private EquipmentAdapter adapter_ur = null;
	private EquipmentAdapter adapter = null;
	
	//定时器
	private Timer timer  = null;
	private int count = 1;
	
	
	
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectNetwork().penaltyLog().build());
        mLinearLayout = (LinearLayout) findViewById(R.id.l);
        SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final String time = form.format(new Date());
        timer = new Timer();
        bt_ur = (Button) findViewById(R.id.sensor_main_button);
//        bt_ur.setTypeface(Typeface.createFromAsset(getAssets(), "noto-cjk-master/NotoSansCJKjp-Light.otf"));
        bt_ur.setBackgroundColor(Color.parseColor("#00BFFF"));
//        bt_ur.setBackgroundResource(R.drawable.urgency_b);
//        myListView = new MyListView(this);
        json_urgency = HomePageya.json_urgency;
        JSONArray data_array_u;
//        list_urgency = new ArrayList<EquipmentInfo>();
//        list_urgency.add(new EquipmentInfo("1600T", "87","#FF0000","重庆胶管"));
//        final View vi = add_myListview_urgency(list_urgency);
//	add_view();
//        bt_ur.setTypeface(Typeface.createFromAsset(getAssets(), "NotoSansCJKjp-Light.otf"));
//	bt_ur.setOnClickListener(new OnClickListener() {
//		
//		@Override
//		public void onClick(View v) {
//			// TODO Auto-generated method stub
//			if(vi.getVisibility() == 0){
//				vi.setVisibility(View.GONE);
////				bt_ur.setVisibility(View.VISIBLE);
//				
//			}else{
//				vi.setVisibility(View.VISIBLE);
////				bt_ur.setVisibility(View.VISIBLE);
//				
//			}
//		}
//	});
        
		try {
			if("10000".equals(json_urgency.get("result"))){
			data_array_u = json_urgency.getJSONArray("data");
			list_urgency = new ArrayList<EquipmentInfo>();
		      list = new ArrayList<EquipmentInfo>();
			for (int i = 0; i < data_array_u.length(); i++) {
				JSONObject data = data_array_u.getJSONObject(i);
				String name = data.getString("name");
				String health = data.getString("health");
//				String color = data.getString("color");
				String isUrgency = data.getString("isUrgency");
				String factory = data.getString("factory");
				if(isUrgency.equals("true")){
					list.add(new EquipmentInfo(name, health,"#FF0000",factory));
					list_urgency.add(new EquipmentInfo(name, health,"#FF0000",factory));
			}else{
				list_urgency.add(new EquipmentInfo(name, health,"#FF0000",factory));
				
				list.add(new EquipmentInfo(name, health,"#4682B4",factory));
			}
			}
			for (int i = 0; i < list.size(); i++) {
				System.out.println("设备列表："+list.get(i).getEquipmentName());
			}
			if(list_urgency.size() > 0){
				bt_ur.setBackgroundColor(android.graphics.Color.RED);
//				 bt_ur.setBackgroundResource(R.drawable.urgency_r);
				final View vi = add_myListview_urgency(list_urgency);
//				add_view();
				bt_ur.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(vi.getVisibility() == 0){
							vi.setVisibility(View.GONE);
//							bt_ur.setVisibility(View.VISIBLE);
							
						}else{
							vi.setVisibility(View.VISIBLE);
//							bt_ur.setVisibility(View.VISIBLE);
							
						}
					}
				});
			}
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 
		try {
			
			json_run = HomePageya.json_run;
//			String result;
//			
//				result = json_run.getString("result");
//				if(result.equals("10000")){
					add_view();
					add_text();
					add_view();
					JSONArray map_info_array = json_run.getJSONArray("map_info");
					for (int i = 0; i < map_info_array.length(); i++) {
						JSONObject map_info = map_info_array.getJSONObject(i);
						JSONArray data_array = map_info.getJSONArray("data");
						String type = map_info.getString("type");
						String name = map_info.getString("name");
						
						if(type.equals("pie")){
							
							mPieChart = add_pie(name,data_array);
							add_view();
							
						}
//							else if(type.equals("bar")){
//							mBarChart = add_bar(name,data_array);
//							add_view();
//						}else if(type.equals("line")){
//							mLineChart = add_line(name,data_array);
//							add_view();
//						}
					}
					if(list != null){
						
						final View bt = add_button();
						final View ve =  add_myListview(list);
//						add_view();
						final View v_view = add_view_list();
						bt.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if(ve.getVisibility() == 0){
									ve.setVisibility(View.GONE);
									bt.setBackgroundColor(Color.parseColor("#1E90FF"));
//									bt.setBackgroundResource(R.drawable.normao_b);
//									bt.setVisibility(View.VISIBLE);
//									v_view .setVisibility(View.GONE);
								}else{
									ve.setVisibility(View.VISIBLE);
//									bt.setBackgroundResource(R.drawable.normal_y);
									bt.setBackgroundColor(Color.parseColor("#00BFFF"));
//									v_view.setVisibility(View.VISIBLE);
//									bt.setVisibility(View.VISIBLE);
									
								}
							}
						});
					}
//				}else if(result.equals("10001")){
//					Toast.makeText(MainActivity.this, "请求失败，请重试！！！", Toast.LENGTH_SHORT).show();
//				}
//			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 	
       mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if(msg.what == 2){
//				count++;
//				System.out.println("这个数是："+count);
				String backMsg = msg.obj.toString();
				System.out.println("这是图表返回内容"+backMsg);
				try {
					
					json_run = new JSONObject(backMsg);
					String result;
//					
						result = json_run.getString("result");
						if(result.equals("10000")){
//							add_text();
							JSONArray map_info_array = json_run.getJSONArray("map_info");
							for (int i = 0; i < map_info_array.length(); i++) {
								JSONObject map_info = map_info_array.getJSONObject(i);
								JSONArray data_array = map_info.getJSONArray("data");
								String type = map_info.getString("type");
								String name = map_info.getString("name");
								
								if(type.equals("pie")){
									
//								mPieChart = add_pie(name,data_array);
//									add_view();
									updatePieChar(mPieChart, name, data_array);
									
								}
//								else if(type.equals("bar")){
////									mBarChart = add_bar(name,data_array);
////									add_view();
//									updateBarChar(mBarChart, name, data_array);
//								}else if(type.equals("line")){
////									mLineChart = add_line(name,data_array);
////									add_view();
//									updateLineChar(mLineChart, name, data_array);
//								}
							}
							if(adapter != null)
							adapter.notifyDataSetChanged();
//							final Button bt = add_button();
//						   	 final View ve =  add_myListview(list);
//						   	  add_view();
//						   	  bt.setOnClickListener(new OnClickListener() {
//									
//									@Override
//									public void onClick(View v) {
//										// TODO Auto-generated method stub
//										if(ve.getVisibility() == 0){
//											ve.setVisibility(View.GONE);
//											bt.setBackgroundColor(android.graphics.Color.GREEN);
////											bt.setVisibility(View.VISIBLE);
//											
//										}else{
//											ve.setVisibility(View.VISIBLE);
//											bt.setBackgroundColor(android.graphics.Color.RED);
////											bt.setVisibility(View.VISIBLE);
//											
//										}
//									}
//								});
						}
//						else if(result.equals("10001")){
//							Toast.makeText(MainActivity.this, "请求失败，请重试！！！", Toast.LENGTH_SHORT).show();
//						}
//					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(msg.what == 1){
				String backMsg = msg.obj.toString();
				System.out.println("这是设备列表返回内容"+backMsg);
				try {
					json_urgency = new JSONObject(backMsg);
					
					String result;

						result = json_urgency.getString("result");
						if(result.equals("10000")){
							JSONArray data_array = json_urgency.getJSONArray("data");
							 list_urgency.clear();
						      list .clear();
							for (int i = 0; i < data_array.length(); i++) {
								JSONObject data = data_array.getJSONObject(i);
								String name = data.getString("name");
								String health = data.getString("health");
								String factory = data.getString("factory");
								String isUrgency = data.getString("isUrgency");
								
								if(isUrgency.equals("true")){
									list.add(new EquipmentInfo(name, health,"#FF0000",factory));
									list_urgency.add(new EquipmentInfo(name, health,"#FF0000",factory));
							}else{
								list_urgency.add(new EquipmentInfo(name, health,"#FF0000",factory));
								
								list.add(new EquipmentInfo(name, health,"#4682B4",factory));
							}
							}
							for (int i = 0; i < list.size(); i++) {
								System.out.println("设备列表："+list.get(i).getEquipmentName());
							}
							if(list_urgency.size() > 0){
								bt_ur.setBackgroundColor(android.graphics.Color.RED);
//								 bt_ur.setBackgroundResource(R.drawable.urgency_r);
								adapter_ur.notifyDataSetChanged();
//								final View vi = add_myListview_urgency(list_urgency);
//								add_view();
//								bt_ur.setOnClickListener(new OnClickListener() {
//									
//									@Override
//									public void onClick(View v) {
//										// TODO Auto-generated method stub
//										if(vi.getVisibility() == 0){
//											vi.setVisibility(View.GONE);
////											bt_ur.setVisibility(View.VISIBLE);
//											
//										}else{
//											vi.setVisibility(View.VISIBLE);
////											bt_ur.setVisibility(View.VISIBLE);
//											
//										}
//									}
//								});
							}
						}
//						else if(result.equals("10001")){
//							Toast.makeText(MainActivity.this, "请求失败，请重试！！！", Toast.LENGTH_SHORT).show();
//						}
//					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
    	   
       };
       timer.schedule(new TimerTask() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			count++;
			Log.e("$$$", "这是第"+count+"次执行定时器！！");
	       String sendStr_u = "sensorinfo="+"传感器信息"+"&time="+time+"&number="+number+"&startTime="+""+"&endTime="+""+"&tel="+"123"+"&info="+"urgency_info";
	       String urlStr_u = "http://193.112.62.125:80/water/app.req?action=emergency";
	       EmergencyThread e = new EmergencyThread(sendStr_u, mHandler, urlStr_u);
	       new Thread(e).start();
	       String sendStr = "sensorinfo="+"传感器信息"+"&time="+time+"&number="+number_run+"&startTime="+""+"&endTime="+""+"&tel="+"123"+"&info="+"map_info";
			String urlStr = "http://193.112.62.125:80/water/app.req?action=graph";
	       GraphThread g = new GraphThread(sendStr, mHandler, urlStr);
	       new Thread(g).start();
	       
		}
	}, 10000 ,1000*10000);
//       String sendStr = "sensorinfo="+"传感器信息"+"&time="+time+"&number="+number_run+"&startTime="+""+"&endTime="+""+"&tel="+"123"+"&info="+"map_info";
//		String urlStr = "http://193.112.62.125:80/water/app.req?action=graph";
//       GraphThread g = new GraphThread(sendStr, mHandler, urlStr);
//       if(json_urgency != null){
    	   
//       }
       
//       String sendStr_u = "sensorinfo="+"传感器信息"+"&time="+time+"&number="+number+"&startTime="+""+"&endTime="+""+"&tel="+"123"+"&info="+"urgency_info";
//		String urlStr_u = "http://193.112.62.125:80/water/app.req?action=emergency";
//       EmergencyThread e = new EmergencyThread(sendStr_u, mHandler, urlStr_u);
//       new Thread(e).start();
//       new Thread(g).start();
       

    }
	public void add_text(){
		TextView  tv = new TextView(MainActivity.this);
		tv.setText("设备运行概况");
		tv.setTextSize(20);
		
//		tv.setTypeface(Typeface.createFromAsset(getAssets(), "NotoSansCJKjp-Light.otf"));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,100);
		tv.setGravity(Gravity.CENTER);
		tv.setBackgroundColor(Color.parseColor("#4169E1"));
		tv.setLayoutParams(params);
		
		mLinearLayout.addView(tv);
	}
    public View add_button_urgency(boolean flag){
    	Button bt = new Button(MainActivity.this);
    	bt.setText("紧急处理信息");
    	bt.setTextSize(20);
    	if(flag)
    	bt.setBackgroundColor(android.graphics.Color.RED);
    	else
    		bt.setBackgroundColor(android.graphics.Color.CYAN);
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT); 
		bt.setLayoutParams(params);
		mLinearLayout.addView(bt);
    	return bt;
    	
    }
    public View add_button(){
    	Button bt = new Button(MainActivity.this);
    	bt.setText("设备列表");
    	bt.setTextSize(20);
//    	bt.setTypeface(Typeface.createFromAsset(getAssets(), "NotoSansCJKjp-Light.otf"));
    	bt.setBackgroundColor(Color.parseColor("#00BFFF"));
//    	ImageButton bt = new ImageButton(MainActivity.this);
//    	bt.setBackgroundResource(R.drawable.normao_b);
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    	mLinearLayout.addView(bt);
    	return bt;
    	
    }
	public void add_view(){
		View v = new View(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,70); 
		v.setBackgroundColor(Color.parseColor("#FFFFF0"));
		mLinearLayout.addView(v,params);
	}
	public View add_view_list(){
		View v = new View(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,200); 
		v.setBackgroundColor(Color.parseColor("#FFFFF0"));
		mLinearLayout.addView(v,params);
		return v;
	}
	public MyListView add_myListview_urgency(ArrayList<EquipmentInfo> list_eq){
		MyListView mMyListView = new MyListView(MainActivity.this);
		 adapter_ur = new EquipmentAdapter(list_eq,MainActivity.this);
		mMyListView.setAdapter(adapter_ur);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
//		mMyListView.addView(adapter);
//		mMyListView.setVisibility(View.GONE);
		mLinearLayout.addView(mMyListView,params);
		return mMyListView;
	}
	public MyListView add_myListview(ArrayList<EquipmentInfo> list_eq){
		MyListView mMyListView = new MyListView(MainActivity.this);
		adapter = new EquipmentAdapter(list_eq,MainActivity.this);
		mMyListView.setAdapter(adapter);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT); 
//		mMyListView.setVisibility(View.GONE);
		mLinearLayout.addView(mMyListView,params);
		return mMyListView;
	}
    public PieChart add_pie(String pie_name,JSONArray data){
    	PieChart mPieChart = new PieChart(MainActivity.this);
    	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 450);
    	mPieChart.setLayoutParams(params);
    	PieData mPieData = getPieData(data);    
        showChart(mPieChart, mPieData,pie_name); 
        mLinearLayout.addView(mPieChart);
		return mPieChart;
    }
    public BarChart add_bar(String name,JSONArray data){
    	
         BarChart mBarChart = new BarChart(MainActivity.this);
         LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 450);
         mBarChart.setLayoutParams(params);
         BarChart3s mBarChart3s = new BarChart3s(mBarChart,data);
         BarData data1 = new BarData(mBarChart3s.getXAxisValues(), mBarChart3s.getDataSet());
         mBarChart.setData(data1);
         mLinearLayout.addView(mBarChart);
		return mBarChart;
         
    }
    public LineChart add_line(String name,JSONArray data){
    	LineChart mLineChart = new LineChart(MainActivity.this);
    	 LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 450);
    	 mLineChart.setLayoutParams(params);
    	 LineData mLineData = getLineData(6, 40, data);
    	 showChart(mLineChart, mLineData);
    	 mLinearLayout.addView(mLineChart);
		return mLineChart;
    }
    public void updatePieChar(PieChart mPieChart,String pie_name,JSONArray data){
    	PieData mPieData = getPieData(data);    
        showChart(mPieChart, mPieData,pie_name); 
        
    }
    public void updateBarChar(BarChart mBarChart,String name,JSONArray data){
         BarChart3s mBarChart3s = new BarChart3s(mBarChart,data);
         BarData data1 = new BarData(mBarChart3s.getXAxisValues(), mBarChart3s.getDataSet());
         mBarChart.setData(data1);
    }
    public void updateLineChar(LineChart mLineChart,String name,JSONArray data){
   	 LineData mLineData = getLineData(6, 40, data);
   	 showChart(mLineChart, mLineData);
    }
    public void showChart(PieChart pieChart, PieData pieData,String name) {    
//    	pieChart.setHoleColor(true);  
    
        pieChart.setHoleRadius(20f);  //半径    
        pieChart.setTransparentCircleRadius(64f); // 半透明圈    
        pieChart.setHoleRadius(0);  //实心圆    
    
//        pieChart.setDescription("每日全厂异常类型分布");    
        pieChart.setDescription("");    
       
        // mChart.setDrawYValues(true);    
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字    
    
        pieChart.setDrawHoleEnabled(true);    
    
        pieChart.setRotationAngle(90); // 初始旋转角度    
    
        // draws the corresponding description value into the slice    
        // mChart.setDrawXValues(true);    
    
        // enable rotation of the chart by touch    
        pieChart.setRotationEnabled(true); // 可以手动旋转    
    
        // display percentage values    
        pieChart.setUsePercentValues(true);  //显示成百分比    
        // mChart.setUnit(" ");    
        // mChart.setDrawUnitsInChart(true);    
    
        // add a selection listener    
//      mChart.setOnChartValueSelectedListener(this);    
        // mChart.setTouchEnabled(false);    
    
//      mChart.setOnAnimationListener(this);    
    
        pieChart.setCenterText("我 ");  //饼状图中间的文字    
    
        //设置数据    
        pieChart.setData(pieData);     
            
        // undo all highlights    
//      pieChart.highlightValues(null);    
//      pieChart.invalidate();    
    
        Legend mLegend = pieChart.getLegend();  //设置比例图    
        mLegend.setPosition(LegendPosition.RIGHT_OF_CHART);  //最右边显示    
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形    
        mLegend.setXEntrySpace(7f);   // 表示图旁边类型中间隔的大小
        mLegend.setYEntrySpace(5f);    
         
            
        pieChart.animateXY( 1000, 1000);  //设置动画    
         
        // mChart.spin(2000, 0, 360);    
    }    
    
    /**  
     *   
     * @param count 分成几部分  
     * @param range  
     */    
    public PieData getPieData(JSONArray data) { 
    	ArrayList<String> xValues = new ArrayList<String>();
    	ArrayList<Entry> yValues = new ArrayList<Entry>();
    	 ArrayList<Integer> colors = new ArrayList<Integer>(); 
    	 int color_count = 0;
    	 if(data.length() > 0){
    		 for (int i = 0; i < data.length(); i++) {
				
				try {
					JSONObject pie_detailsObj = data.getJSONObject(i);
					String type = pie_detailsObj.getString("name");
					String value = pie_detailsObj.getString("value");
					String color = color_array[color_count];
					xValues.add(type);
					float quarterly = Float.parseFloat(value);  
					yValues.add(new Entry(quarterly, i));
					colors.add(Color.parseColor(color));  
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				color_count++;
				if(color_count >= color_array.length)
					color_count = 0;
			}
    	 }
//        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容    
    
//        for (int i = 0; i < count; i++) {    
//            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4  
//        	xValues.add("问题型");
//        	xValues.add("隐患型");
//        	xValues.add("报警型");
//        }    
    
//        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据    
    
        // 饼图数据    
        /**  
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38  
         * 所以 14代表的百分比就是14%   
         */    
//        float quarterly1 = 4;    
//        float quarterly2 = 5;    
//        float quarterly3 = 2;    
//        float quarterly4 = 16;    
    
//        yValues.add(new Entry(quarterly1, 0));    
//        yValues.add(new Entry(quarterly2, 1));    
//        yValues.add(new Entry(quarterly3, 2));    
//        yValues.add(new Entry(quarterly4, 3));    
    
        //y轴的集合    
//        PieDataSet pieDataSet = new PieDataSet(yValues, "产量占比"/*显示在比例图上*/);    
        PieDataSet pieDataSet = new PieDataSet(yValues, ""/*显示在比例图上*/);    
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离    
    
//        ArrayList<Integer> colors = new ArrayList<Integer>();    
    
        // 饼图颜色    
//        colors.add(Color.rgb(205, 205, 205));    
//        colors.add(Color.rgb(114, 188, 223));    
//        colors.add(Color.rgb(255, 123, 124));    
//        colors.add(Color.rgb(57, 135, 200));    
    
        pieDataSet.setColors(colors);    
    
        DisplayMetrics metrics = getResources().getDisplayMetrics();    
//        float px = 5 * (metrics.densityDpi / 160f);    
        float px = 5 * (metrics.densityDpi / 160f);  // 表示图的大小 
        pieDataSet.setSelectionShift(px); // 选中态多出的长度    
    
        PieData pieData = new PieData(xValues, pieDataSet);    
            
        return pieData;    
    }
    
    private void showChart(LineChart lineChart, LineData lineData) {    
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框    
    
        // no description text    
        lineChart.setDescription("");// 数据描述    
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview    
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");    
            
        // enable / disable grid background    
        lineChart.setDrawGridBackground(false); // 是否显示表格颜色    
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度    
    
        // enable touch gestures    
        lineChart.setTouchEnabled(true); // 设置是否可以触摸    
    
        // enable scaling and dragging    
        lineChart.setDragEnabled(true);// 是否可以拖拽    
        lineChart.setScaleEnabled(true);// 是否可以缩放    
    
        // if disabled, scaling can be done on x- and y-axis separately    
        lineChart.setPinchZoom(false);//     
    
        lineChart.setBackgroundColor(android.graphics.Color.WHITE);// 设置背景    
    
        // add data    
        lineChart.setData(lineData); // 设置数据    
    
        // get the legend (only possible after setting data)    
        Legend mLegend = lineChart.getLegend(); // 设置比例图标示，就是那个一组y的value的    
    
        // modify the legend ...    
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);    
        mLegend.setForm(LegendForm.CIRCLE);// 样式    
        mLegend.setFormSize(6f);// 字体    
        mLegend.setTextColor(Color.WHITE);// 颜色    
//      mLegend.setTypeface(mTf);// 字体    
    
        lineChart.animateX(2500); // 立即执行的动画,x轴    
      }    
    /**  
     * 生成一个数据  
     * @param count 表示图表中有多少个坐标点  
     * @param range 用来生成range以内的随机数  
     * @return  
     */    
    private LineData getLineData(int count, float range,JSONArray data) {    
        ArrayList<String> xValues = new ArrayList<String>();    
//        for (int i = 0; i < count; i++) {    
//            // x轴显示的数据，这里默认使用数字下标显示    
//            xValues.add(i+"月");    
//        }    
    
        // y轴的数据    
        ArrayList<Entry> yValues = new ArrayList<Entry>();    
//        for (int i = 0; i < count; i++) {    
//            float value = (float) (Math.random() * range) + 3;    
//            yValues.add(new Entry(value, i));    
//        }    
        for (int i = 0; i < data.length(); i++) {
			try {
				JSONObject dataO = data.getJSONObject(i);
				String time = dataO.getString("time");
				String problem = dataO.getString("problem");
				String danger = dataO.getString("danger");
				String warning = dataO.getString("warning");
				xValues.add(time);
				yValues.add(new Entry(Float.parseFloat(problem), i));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
        // create a dataset and give it a type    
        // y轴的数据集合    
        LineDataSet lineDataSet = new LineDataSet(yValues, "" /*显示在比例图上*/);    
        // mLineDataSet.setFillAlpha(110);    
        // mLineDataSet.setFillColor(Color.RED);    
    
        //用y轴的集合来设置参数    
        lineDataSet.setLineWidth(1.75f); // 线宽    
        lineDataSet.setCircleSize(3f);// 显示的圆形大小    
        lineDataSet.setColor(Color.WHITE);// 显示颜色    
        lineDataSet.setCircleColor(Color.WHITE);// 圆形的颜色    
        lineDataSet.setHighLightColor(Color.YELLOW); // 高亮的线的颜色    
    
        List<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();    
        lineDataSets.add(lineDataSet); // add the datasets    
    
        // create a data object with the datasets    
        LineData lineData = new LineData(xValues, lineDataSets);    
    
        return lineData;    
    }   
    
    
	private JSONObject runContent(String sendStr,String urlStr) {
		OutputStream out = null;
		BufferedReader reader = null;
		JSONObject json = null;
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
			json = new JSONObject(builder.toString());
			
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
		return json;
	}
}

