package com.generalization.chart;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;
import android.util.Log;

import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.MainActivity;
import com.example.generalizationdemo.SecondActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

public class BarChart4s {
	
	private String coffee;
	private String cup;
	private String status = MainActivity.statusya;
	
	public BarChart4s(BarChart chart) {
        // 数据描述
        chart.setDescription("");
        //背景
        chart.setBackgroundColor(0xffffffff);
        //定义数据描述得位置
//        chart.setDescriptionPosition(2,100);
        // 设置描述文字的颜色
        // chart.setDescriptionColor(0xffededed);
//        chart.setDescriptionTextSize(30);
        // 动画
        chart.animateY(1000);
        //设置无边框
        chart.setDrawBorders(false);
        // 设置是否可以触摸
        chart.setTouchEnabled(true);
        // 是否可以拖拽
        chart.setDragEnabled(true);
        // 是否可以缩放
        chart.setScaleEnabled(true);
        //设置网格背景
        chart.setGridBackgroundColor(0xffffffff);
        //设置边线宽度
        chart.setBorderWidth(0);
        //设置边线颜色
        chart.setBorderColor(0xffffffff);
        // 集双指缩放
        chart.setPinchZoom(false);
        // 隐藏右边的坐标轴
        chart.getAxisRight().setEnabled(false);
        // 隐藏左边的左边轴
        chart.getAxisLeft().setEnabled(true);
        
//        chart.setTextDirection("真的么");

        Legend mLegend = chart.getLegend(); // 设置比例图标示
        // 设置窗体样式
        mLegend.setForm(Legend.LegendForm.SQUARE);
        //设置图标位置
        mLegend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        // 字体
        mLegend.setFormSize(4f);
        //是否显示注释
        mLegend.setEnabled(true);
        // 字体颜色
//        mLegend.setTextColor(Color.parseColor("#7e7e7e"));
        
        
        
        
        
        //设置X轴位置
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // 前面xAxis.setEnabled(false);则下面绘制的Grid不会有"竖的线"（与X轴有关）
        // 上面第一行代码设置了false,所以下面第一行即使设置为true也不会绘制AxisLine
        //设置轴线得颜色
        xAxis.setAxisLineColor(0xffffffff);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        //设置Y轴
        YAxis leftAxis = chart.getAxisLeft();
        //Y轴颜色
        leftAxis.setAxisLineColor(0xffffffff);
        //Y轴参照线颜色
        leftAxis.setGridColor(0xfff3f3f3);
        //参照线长度
        leftAxis.setAxisLineWidth(5f);
        // 顶部居最大值站距离占比
        leftAxis.setSpaceTop(20f);

        chart.invalidate();
    }

    public List<IBarDataSet> getDataSet() {
    	
    	if(status.equals("worker")){
    		coffee = SecondActivity.mission_statistics;
    	}else{
    		coffee = HomePageya.mission_statistics;
    	}
    	
    	
        List<IBarDataSet> dataSets = null;
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
//        for (int i = 0; i < 4; i++) {
//            int value = (int) (Math.random() * 10/*100以内的随机数*/) ;
//            valueSet1.add(new BarEntry(value,i));}

        try{
            JSONObject stttcs_object = new JSONObject(coffee);
            org.json.JSONArray data = stttcs_object.getJSONArray("data");
            System.out.print(data.length());
            for (int j = 0; j < data.length(); j++) {
            	JSONObject note = data.getJSONObject(j);
            	int count = Integer.parseInt(note.getString("count"));
//            	int alarm = Integer.parseInt(note.getString("alarm"));
//            	int hidden_danger = Integer.parseInt(note.getString("hidden_danger"));
            	int daily = Integer.parseInt(note.getString("daily"));
            	Log.v("nliqunatianhuranqingpendayu", String.valueOf(daily));
            	valueSet1.add(new BarEntry(daily, j));
            }
            }catch (JSONException e) {
    	        e.printStackTrace();
    		    
    		}
        
        

        ArrayList<BarEntry> valueSet2 = new ArrayList<BarEntry>();
//        for (int j = 0; j < 4; j++) {
//            int value1 = (int) (Math.random() * 10/*100以内的随机数*/) ;
//            valueSet2.add(new BarEntry(value1,j));}
        
        try{
            JSONObject stttcs_object = new JSONObject(coffee);
            org.json.JSONArray data = stttcs_object.getJSONArray("data");
            System.out.print(data.length());
            for (int j = 0; j < data.length(); j++) {
            	JSONObject note = data.getJSONObject(j);
            	int count = Integer.parseInt(note.getString("count"));
//            	int alarm = Integer.parseInt(note.getString("alarm"));
//            	int hidden_danger = Integer.parseInt(note.getString("hidden_danger"));
            	int temp = Integer.parseInt(note.getString("temp"));
            	Log.v("nliqunatianhuranqingpendayu", String.valueOf(temp));
            	valueSet2.add(new BarEntry(temp, j));
            }
            }catch (JSONException e) {
    	        e.printStackTrace();
    		    
    		}
        
        
        
        ArrayList<BarEntry> valueSet3 = new ArrayList<BarEntry>();
//        for (int k = 0; k < 4; k++) {
//            int value1 = (int) (Math.random() * 10/*100以内的随机数*/) ;
//            valueSet3.add(new BarEntry(value1,k));}
        
        
        try{
            JSONObject stttcs_object = new JSONObject(coffee);
            org.json.JSONArray data = stttcs_object.getJSONArray("data");
            System.out.print(data.length());
            for (int j = 0; j < data.length(); j++) {
            	JSONObject note = data.getJSONObject(j);
            	int count = Integer.parseInt(note.getString("count"));
//            	int alarm = Integer.parseInt(note.getString("alarm"));
//            	int hidden_danger = Integer.parseInt(note.getString("hidden_danger"));
            	int repair = Integer.parseInt(note.getString("repair"));
            	Log.v("nliqunatianhuranqingpendayu", String.valueOf(repair));
            	valueSet3.add(new BarEntry(repair, j));
            }
            }catch (JSONException e) {
    	        e.printStackTrace();
    		    
    		}


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "日常任务");
        barDataSet1.setColor(Color.parseColor("#DEB887"));
        barDataSet1.setBarShadowColor(Color.parseColor("#01000000"));

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "临时任务");
        barDataSet2.setColor(Color.parseColor("#6faae7"));
        barDataSet2.setBarShadowColor(Color.parseColor("#01000000"));
        
        BarDataSet barDataSet3 = new BarDataSet(valueSet3, "维修任务");
        barDataSet3.setColor(Color.parseColor("#cc0033"));
        barDataSet3.setBarShadowColor(Color.parseColor("#01000000"));

        dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);

        return dataSets;
    }

    public ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<String>();
//        for (int j = 0; j < 4; j++){
//            xAxis.add((j+1)+"月");
//        }
        
        try{
        	
        	if(status.equals("worker")){
        		cup = SecondActivity.mission_statistics;
        	}else{
        		cup = HomePageya.mission_statistics;
        	}
        	
        	
            JSONObject stttcs_object = new JSONObject(cup);
            org.json.JSONArray month = stttcs_object.getJSONArray("data");
            
            for (int j = 0; j < month.length(); j++) {
            	JSONObject note = month.getJSONObject(j);
//            	int value = Integer.parseInt(note.getString("time").substring(5));
            	String value = note.getString("time").substring(5);
            	xAxis.add(value+"月");
            }
            }catch (JSONException e) {
    	        e.printStackTrace();
    		    
    		}
        
        return xAxis;
    }
}
