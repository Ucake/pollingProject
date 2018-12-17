package com.generalization.chart;

import java.util.ArrayList;
import java.util.List;

import com.cement.chart.LineChartActivity;
import com.example.generalizationdemo.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;

/**
 * 聊天Fragment的界面
 * 
 * 
 * http://blog.csdn.net/guolin_blog/article/details/26365683
 * 
 * @author guolin
 */
public class BarChartActivity extends Fragment {
	
	private BarChart mBarChart;  
    private BarData mBarData;  
    private Button linechart;
    
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
//		v.setText("聊天界面");
//		v.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));
//		fl.addView(v);
		
		View view = inflater.inflate(R.layout.barchart, container, false);
		mBarChart = (BarChart)view.findViewById(R.id.spread_bar_chart);  
        mBarData = getBarData(31, 100);  
        showBarChart(mBarChart, mBarData); 
		return view;
  
    }  
      
	private void showBarChart(BarChart barChart, BarData barData) {  
        barChart.setDrawBorders(false);  ////是否在折线图上添加边框   
            
        barChart.setDescription("");// 数据描述      
          
        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView      
        barChart.setNoDataTextDescription("You need to provide data for the chart.");      
                 
        barChart.setDrawGridBackground(false); // 是否显示表格颜色      
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度      
        
        barChart.setTouchEnabled(true); // 设置是否可以触摸      
       
        barChart.setDragEnabled(true);// 是否可以拖拽      
        barChart.setScaleEnabled(true);// 是否可以缩放      
      
        barChart.setPinchZoom(false);//       
      
//      barChart.setBackgroundColor();// 设置背景      
          
        barChart.setDrawBarShadow(true);  
         
        barChart.setData(barData); // 设置数据      
  
        Legend mLegend = barChart.getLegend(); // 设置比例图标示  
      
        mLegend.setForm(LegendForm.CIRCLE);// 样式      
        mLegend.setFormSize(6f);// 字体      
        mLegend.setTextColor(Color.BLACK);// 颜色      
          
//      X轴设定  
      XAxis xAxis = barChart.getXAxis();  
      xAxis.setPosition(XAxisPosition.BOTTOM);  
      
        barChart.animateX(2500); // 立即执行的动画,x轴    
    }  
  
    private BarData getBarData(int count, float range) {  
        ArrayList<String> xValues = new ArrayList<String>();  
        for (int i = 0; i < count; i++) {  
//            xValues.add("第" + (i + 1) + "季度");
            xValues.add( (i + 1)+"" );
        }  
          
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();  
          
        for (int i = 0; i < count; i++) {      
            float value = (float) (Math.random() * range/*100以内的随机数*/) + 3;  
            yValues.add(new BarEntry(value, i));      
        }  
          
        // y轴的数据集合      
        BarDataSet barDataSet = new BarDataSet(yValues, "柱状图");   
          
        barDataSet.setColor(Color.rgb(114, 188, 223));  
      
        List<IBarDataSet> barDataSets = new ArrayList<IBarDataSet>();      
        barDataSets.add(barDataSet); // add the datasets      
      
        BarData barData = new BarData(xValues, barDataSets);
       
      
        return barData;  
    }  
		
	}

