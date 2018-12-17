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
        barChart.setDrawBorders(false);  ////�Ƿ�������ͼ����ӱ߿�   
            
        barChart.setDescription("");// ��������      
          
        // ���û�����ݵ�ʱ�򣬻���ʾ���������ListView��EmptyView      
        barChart.setNoDataTextDescription("You need to provide data for the chart.");      
                 
        barChart.setDrawGridBackground(false); // �Ƿ���ʾ�����ɫ      
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // ���ĵ���ɫ�����������Ǹ���ɫ����һ��͸����      
        
        barChart.setTouchEnabled(true); // �����Ƿ���Դ���      
       
        barChart.setDragEnabled(true);// �Ƿ������ק      
        barChart.setScaleEnabled(true);// �Ƿ��������      
      
        barChart.setPinchZoom(false);//       
      
//      barChart.setBackgroundColor();// ���ñ���      
          
        barChart.setDrawBarShadow(true);  
         
        barChart.setData(barData); // ��������      
  
        Legend mLegend = barChart.getLegend(); // ���ñ���ͼ��ʾ  
      
        mLegend.setForm(LegendForm.CIRCLE);// ��ʽ      
        mLegend.setFormSize(6f);// ����      
        mLegend.setTextColor(Color.BLACK);// ��ɫ      
          
//      X���趨  
      XAxis xAxis = barChart.getXAxis();  
      xAxis.setPosition(XAxisPosition.BOTTOM);  
      
        barChart.animateX(2500); // ����ִ�еĶ���,x��    
    }  
  
    private BarData getBarData(int count, float range) {  
        ArrayList<String> xValues = new ArrayList<String>();  
        for (int i = 0; i < count; i++) {  
            xValues.add("��" + (i + 1) + "����");  
        }  
          
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();  
          
        for (int i = 0; i < count; i++) {      
            float value = (float) (Math.random() * range/*100���ڵ������*/) + 3;  
            yValues.add(new BarEntry(value, i));      
        }  
          
        // y������ݼ���      
        BarDataSet barDataSet = new BarDataSet(yValues, "��״ͼ");   
          
        barDataSet.setColor(Color.rgb(114, 188, 223));  
      
        List<IBarDataSet> barDataSets = new ArrayList<IBarDataSet>();      
        barDataSets.add(barDataSet); // add the datasets      
      
        BarData barData = new BarData(xValues, barDataSets);
       
      
       
          
        return barData;  
    }  
	 
}
