package com.cement.chart;

import java.util.ArrayList;
import java.util.List;

import com.example.generalizationdemo.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;

import com.github.mikephil.charting.components.XAxis;  
import com.github.mikephil.charting.components.XAxis.XAxisPosition; 
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;


public class BarChartActivity extends Activity  {
	private BarChart mBarChart;  
    private BarData mBarData;  
    private Button linechart;
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.barchart);  
        
//        linechart = (Button) findViewById(R.id.linechart);
//        linechart.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(BarChartActivity.this, LineChartActivity.class);
//				startActivity(intent);
//			}
//		});
          
        mBarChart = (BarChart) findViewById(R.id.spread_bar_chart);  
        mBarData = getBarData(4, 100);  
        showBarChart(mBarChart, mBarData);  
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
            xValues.add("第" + (i + 1) + "季度");  
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
