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
 * ����Fragment�Ľ���
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
//		v.setText("�������");
//		v.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, dm));
//		fl.addView(v);
		
		View view = inflater.inflate(R.layout.barchart, container, false);
		mBarChart = (BarChart)view.findViewById(R.id.spread_bar_chart);  
        mBarData = getBarData(31, 100);  
        showBarChart(mBarChart, mBarData); 
		return view;
  
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
//            xValues.add("��" + (i + 1) + "����");
            xValues.add( (i + 1)+"" );
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

