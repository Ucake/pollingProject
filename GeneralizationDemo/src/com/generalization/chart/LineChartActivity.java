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
 * ����Fragment�Ľ���
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
//		v.setText("���ֽ���");
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
        lineChart.setDrawBorders(false);  //�Ƿ�������ͼ����ӱ߿�    
    
        // no description text    
        lineChart.setDescription("");// ��������    
        // ���û�����ݵ�ʱ�򣬻���ʾ���������listview��emtpyview    
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");    
            
        // enable / disable grid background    
        lineChart.setDrawGridBackground(false); // �Ƿ���ʾ�����ɫ    
        lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // ���ĵ���ɫ�����������Ǹ���ɫ����һ��͸����    
    
        // enable touch gestures    
        lineChart.setTouchEnabled(true); // �����Ƿ���Դ���    
    
        // enable scaling and dragging    
        lineChart.setDragEnabled(true);// �Ƿ������ק    
        lineChart.setScaleEnabled(true);// �Ƿ��������    
    
        // if disabled, scaling can be done on x- and y-axis separately    
        lineChart.setPinchZoom(false);//     
    
        lineChart.setBackgroundColor(color);// ���ñ���    
    
        // add data    
        lineChart.setData(lineData); // ��������    
    
        // get the legend (only possible after setting data)    
        Legend mLegend = lineChart.getLegend(); // ���ñ���ͼ��ʾ�������Ǹ�һ��y��value��    
    
        // modify the legend ...    
        // mLegend.setPosition(LegendPosition.LEFT_OF_CHART);    
        mLegend.setForm(LegendForm.CIRCLE);// ��ʽ    
        mLegend.setFormSize(6f);// ����    
        mLegend.setTextColor(Color.WHITE);// ��ɫ    
//      mLegend.setTypeface(mTf);// ����    
    
        lineChart.animateX(2500); // ����ִ�еĶ���,x��    
      }    
        
    /**  
     * ����һ������  
     * @param count ��ʾͼ�����ж��ٸ������  
     * @param range ��������range���ڵ������  
     * @return  
     */    
    private LineData getLineData(int count, float range) {    
        ArrayList<String> xValues = new ArrayList<String>(); 
        //20180423��֮ǰ��ʾ���·�ע�͵�
//        for (int i = 0; i < count; i++) {    
//            // x����ʾ�����ݣ�����Ĭ��ʹ�������±���ʾ    
//            xValues.add(i+"��");    
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
			 xValues.add(value+"��");
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
        //20180423 ��ע�͵���y�������
        // y�������    
//        ArrayList<Entry> yValues = new ArrayList<Entry>();    
//        for (int i = 0; i < count; i++) {    
//            float value = (float) (Math.random() * range) + 3;    
//            yValues.add(new Entry(value, i));    
//        }    
    
        // create a dataset and give it a type    
        // y������ݼ���    
        LineDataSet lineDataSet = new LineDataSet(yValues, "" /*��ʾ�ڱ���ͼ��*/);    
        // mLineDataSet.setFillAlpha(110);    
        // mLineDataSet.setFillColor(Color.RED);    
    
        //��y��ļ��������ò���    
        lineDataSet.setLineWidth(1.75f); // �߿�    
        lineDataSet.setCircleSize(3f);// ��ʾ��Բ�δ�С    
        lineDataSet.setColor(Color.WHITE);// ��ʾ��ɫ    
        lineDataSet.setCircleColor(Color.WHITE);// Բ�ε���ɫ    
        lineDataSet.setHighLightColor(Color.YELLOW); // �������ߵ���ɫ    
    
        List<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();    
        lineDataSets.add(lineDataSet); // add the datasets    
    
        // create a data object with the datasets    
        LineData lineData = new LineData(xValues, lineDataSets);    
    
        return lineData;    
    }   
    
    /**  
     * ����һ������  
     * @param count ��ʾͼ�����ж��ٸ������  
     * @param range ��������range���ڵ������  
     * @return  
     */    
    private LineData getLineData1(int count, float range) {    
        ArrayList<String> xValues = new ArrayList<String>(); 
        //20180423��֮ǰ��ʾ���·�ע�͵�
//        for (int i = 0; i < count; i++) {    
//            // x����ʾ�����ݣ�����Ĭ��ʹ�������±���ʾ    
//            xValues.add(i+"��");    
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
			 xValues.add(value+"��");
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
        //20180423 ��ע�͵���y�������
        // y�������    
//        ArrayList<Entry> yValues = new ArrayList<Entry>();    
//        for (int i = 0; i < count; i++) {    
//            float value = (float) (Math.random() * range) + 3;    
//            yValues.add(new Entry(value, i));    
//        }    
    
        // create a dataset and give it a type    
        // y������ݼ���    
        LineDataSet lineDataSet = new LineDataSet(yValues, "" /*��ʾ�ڱ���ͼ��*/);    
        // mLineDataSet.setFillAlpha(110);    
        // mLineDataSet.setFillColor(Color.RED);    
    
        //��y��ļ��������ò���    
        lineDataSet.setLineWidth(1.75f); // �߿�    
        lineDataSet.setCircleSize(3f);// ��ʾ��Բ�δ�С    
        lineDataSet.setColor(Color.WHITE);// ��ʾ��ɫ    
        lineDataSet.setCircleColor(Color.WHITE);// Բ�ε���ɫ    
        lineDataSet.setHighLightColor(Color.YELLOW); // �������ߵ���ɫ    
    
        List<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();    
        lineDataSets.add(lineDataSet); // add the datasets    
    
        // create a data object with the datasets    
        LineData lineData = new LineData(xValues, lineDataSets);    
    
        return lineData;    
    }
	
}
