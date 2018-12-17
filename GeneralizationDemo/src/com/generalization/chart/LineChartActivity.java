package com.generalization.chart;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.MainActivity;
import com.example.generalizationdemo.R;
import com.example.generalizationdemo.SecondActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

/**
 * 发现Fragment的界面
 * 
 * http://blog.csdn.net/guolin_blog/article/details/26365683
 * 
 * @author guolin
 */
public class LineChartActivity extends Fragment {
	
	private String fac_statistics;
	private ArrayList<Entry> yValues;
	
	private JSONObject statistics_object;
	private JSONObject statistics_object1;
	
	 private Button piechart;
	 private LineChart mLineChart;    
	 private LineChart mLineChart2;
	 private String status = MainActivity.statusya;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		FrameLayout fl = new FrameLayout(getActivity());
//		fl.setLayoutParams(params);
//		DisplayMetrics dm = getResources().getDisplayMetrics();
//		final int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, dm);
//		TextView v = new TextView(getActivity());
//		params.setMargins(margin, margin, margin, margin);
//		v.setLayoutParams(params);
//		v.setLayoutParams(params);
//		v.setGravity(Gravity.CENTER);
//		v.setText("发现界面");
//		v.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));
//		fl.addView(v);
		 View view = inflater.inflate(R.layout.linechart, container, false);
		 
		 mLineChart = (LineChart)view.findViewById(R.id.spread_line_chart);    
		 
		 mLineChart2 = (LineChart)view.findViewById(R.id.line_chart2);
		 
		 if(status.equals("worker")){
			 fac_statistics = SecondActivity.factory_statistics;
		 }else{
			 fac_statistics = HomePageya.factory_statistics; 
		 }
		 
//		 try{
//			 JSONObject statistics_object = new JSONObject(fac_statistics);
//			 org.json.JSONArray data = statistics_object.getJSONArray("data");
//			 int len = data.length();
//		 }catch (JSONException e) {
// 	        e.printStackTrace();
//		    
// 		}
         
//	      mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");    
	    
	        LineData mLineData = getLineData(6, 40);    
	        showChart(mLineChart, mLineData, Color.rgb(114, 188, 223)); 
	        
	        LineData mLineData2 = getLineData1(6, 10);
	        showChart(mLineChart2, mLineData2, Color.rgb(114, 188, 223));
		
		return view;
		
	}
	
	private void showChart(LineChart lineChart, LineData lineData, int color) {    
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
    
        lineChart.setBackgroundColor(color);// 设置背景    
    
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
    private LineData getLineData(int count, float range) {    
        ArrayList<String> xValues = new ArrayList<String>(); 
        //20180423把之前显示的月份注释掉
//        for (int i = 0; i < count; i++) {    
//            // x轴显示的数据，这里默认使用数字下标显示    
//            xValues.add(i+"月");    
//        }  
        
        try{
//			 JSONObject statistics_object = new JSONObject(fac_statistics);
        	if(status.equals("worker")){
        		statistics_object = new JSONObject(SecondActivity.mission_statistics);
        	}else{
        		statistics_object = new JSONObject(HomePageya.mission_statistics);
        	}
//        	JSONObject statistics_object = new JSONObject(HomePageya.mission_statistics);
			 org.json.JSONArray data = statistics_object.getJSONArray("data");
			 int len = data.length();
			 for (int j = 0; j < len; j++) {
	            	JSONObject note = data.getJSONObject(j);
			 String value = note.getString("time").substring(5);
			 xValues.add(value+"月");
			 }
			 
			 
			yValues = new ArrayList<Entry>();    
		        for (int i = 0; i < len; i++) {    
//		            float value = (float) (Math.random() * range) + 3;
		        	JSONObject note = data.getJSONObject(i);
		        	int value = note.getInt("daily");
		            yValues.add(new Entry(value, i));    
		        }
		        
		        
		 }catch (JSONException e) {
	        e.printStackTrace();
		    
		}
        //20180423 给注释掉的y轴的数据
        // y轴的数据    
//        ArrayList<Entry> yValues = new ArrayList<Entry>();    
//        for (int i = 0; i < count; i++) {    
//            float value = (float) (Math.random() * range) + 3;    
//            yValues.add(new Entry(value, i));    
//        }    
    
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
    
    /**  
     * 生成一个数据  
     * @param count 表示图表中有多少个坐标点  
     * @param range 用来生成range以内的随机数  
     * @return  
     */    
    private LineData getLineData1(int count, float range) {    
        ArrayList<String> xValues = new ArrayList<String>(); 
        //20180423把之前显示的月份注释掉
//        for (int i = 0; i < count; i++) {    
//            // x轴显示的数据，这里默认使用数字下标显示    
//            xValues.add(i+"月");    
//        }  
        
        try{
        	
        	if(status.equals("worker")){
        		statistics_object1 = new JSONObject(SecondActivity.mission_statistics);
        	}else{
        		statistics_object1 = new JSONObject(HomePageya.mission_statistics);
        	}
        	
			 
			 org.json.JSONArray data = statistics_object1.getJSONArray("data");
			 int len = data.length();
			 for (int j = 0; j < len; j++) {
	            	JSONObject note = data.getJSONObject(j);
			 String value = note.getString("time").substring(5);
			 xValues.add(value+"月");
			 }
			 
			 
			yValues = new ArrayList<Entry>();    
		        for (int i = 0; i < len; i++) {    
//		            float value = (float) (Math.random() * range) + 3;
		        	JSONObject note = data.getJSONObject(i);
		        	int value = note.getInt("temp");
		            yValues.add(new Entry(value, i));    
		        }
		        
		        
		 }catch (JSONException e) {
	        e.printStackTrace();
		    
		}
        //20180423 给注释掉的y轴的数据
        // y轴的数据    
//        ArrayList<Entry> yValues = new ArrayList<Entry>();    
//        for (int i = 0; i < count; i++) {    
//            float value = (float) (Math.random() * range) + 3;    
//            yValues.add(new Entry(value, i));    
//        }    
    
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
	
}
