package com.generalization.chart;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.MainActivity;
import com.example.generalizationdemo.R;
import com.example.generalizationdemo.SecondActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

/**
 * 通讯录Fragment的界面
 * 
 * http://blog.csdn.net/guolin_blog/article/details/26365683
 * 
 * @author guolin
 */
public class PieChartActivity extends Fragment {
	
	private PieChart mChart;  
	private String calendar;
	private int question;
	private int hidden_danger;
	private int alarm;
	
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
//		v.setText("通讯录界面");
//		
//		v.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));
//		fl.addView(v);
		
		View view = inflater.inflate(R.layout.piechart, container, false);
		mChart = (PieChart)view.findViewById(R.id.spread_pie_chart);    
        PieData mPieData = getPieData(4, 100);    
        showChart(mChart, mPieData); 
		return view;
	}
	
	private void showChart(PieChart pieChart, PieData pieData) {    
//    	pieChart.setHoleColor(true);  
    
        pieChart.setHoleRadius(20f);  //半径    
        pieChart.setTransparentCircleRadius(64f); // 半透明圈    
        pieChart.setHoleRadius(0);  //实心圆    
    
        pieChart.setDescription("本月全厂异常类型分布");    
    
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
        // mChart.setUnit(" €");    
        // mChart.setDrawUnitsInChart(true);    
    
        // add a selection listener    
//      mChart.setOnChartValueSelectedListener(this);    
        // mChart.setTouchEnabled(false);    
    
//      mChart.setOnAnimationListener(this);    
    
        pieChart.setCenterText(" ");  //饼状图中间的文字    
    
        //设置数据    
        pieChart.setData(pieData);     
            
        // undo all highlights    
//      pieChart.highlightValues(null);    
//      pieChart.invalidate();    
    
        Legend mLegend = pieChart.getLegend();  //设置比例图    
        mLegend.setPosition(LegendPosition.RIGHT_OF_CHART);  //最右边显示    
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形    
        mLegend.setXEntrySpace(7f);    
        mLegend.setYEntrySpace(5f);    
            
        pieChart.animateXY(1000, 1000);  //设置动画    
        // mChart.spin(2000, 0, 360);    
    }    
    
    /**  
     *   
     * @param count 分成几部分  
     * @param range  
     */    
    private PieData getPieData(int count, float range) {    
            
        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容    
    
//        for (int i = 0; i < count; i++) {    
//            xValues.add("Quarterly" + (i + 1));  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4  
        	xValues.add("问题型");
        	xValues.add("隐患型");
        	xValues.add("报警型");
//        }    
    
        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据    
    
        // 饼图数据    
        /**  
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38  
         * 所以 14代表的百分比就是14%   
         */    
        
        
        if(status.equals("worker")){
        	calendar = SecondActivity.statistics;
        }else{
        	calendar = HomePageya.statistics;
        }
        
        try{
        JSONObject staobject = new JSONObject(calendar);
        org.json.JSONArray staarray = staobject.getJSONArray("data");
        int len = staarray.length() - 1;
        JSONObject note = staarray.getJSONObject(len);
        question = note.getInt("question");
        alarm = note.getInt("alarm");
        hidden_danger = note.getInt("hidden_danger");
        
        
        }catch (JSONException e) {
	        e.printStackTrace();
		    
        }
//        float quarterly1 = 4;    
//        float quarterly2 = 5;    
//        float quarterly3 = 2;    
//        float quarterly4 = 16;    
    
        yValues.add(new Entry(question, 0));
        yValues.add(new Entry(hidden_danger, 1));
        yValues.add(new Entry(alarm, 2));
        
//        yValues.add(new Entry(quarterly1, 0));    
//        yValues.add(new Entry(quarterly2, 1));    
//        yValues.add(new Entry(quarterly3, 2));    
//        yValues.add(new Entry(quarterly4, 3));    
    
        //y轴的集合    
        PieDataSet pieDataSet = new PieDataSet(yValues, "数量占比"/*显示在比例图上*/);    
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离    
    
        ArrayList<Integer> colors = new ArrayList<Integer>();    
    
        // 饼图颜色    
        colors.add(Color.rgb(205, 205, 205));    
        colors.add(Color.rgb(114, 188, 223));    
        colors.add(Color.rgb(255, 123, 124));    
        colors.add(Color.rgb(57, 135, 200));    
    
        pieDataSet.setColors(colors);    
    
        DisplayMetrics metrics = getResources().getDisplayMetrics();    
        float px = 5 * (metrics.densityDpi / 160f);    
        pieDataSet.setSelectionShift(px); // 选中态多出的长度    
    
        PieData pieData = new PieData(xValues, pieDataSet);    
            
        return pieData;    
    }
	
}
