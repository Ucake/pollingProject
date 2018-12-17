package com.generalization.chart;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.generalizationdemo.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;

public class BarBarActivity extends Fragment{
	private BarChart3s mBarChart3s;
	private BarChart4s mBarChart4s;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		
		View view = inflater.inflate(R.layout.barchart_new, container, false);
	      
	      BarChart chart1 = (BarChart)view.findViewById(R.id.spread_bar_chart);
	      BarChart chart2 = (BarChart)view.findViewById(R.id.bar_chart_4s);
	      mBarChart3s = new BarChart3s(chart1);
	      BarData data1 = new BarData(mBarChart3s.getXAxisValues(), mBarChart3s.getDataSet());

	        // …Ë÷√ ˝æ›
	      chart1.setData(data1);
	        
	      mBarChart4s = new BarChart4s(chart2);
	      BarData data2 = new BarData(mBarChart4s.getXAxisValues(), mBarChart4s.getDataSet());
	      chart2.setData(data2);
	        
	        return view;
	        
	   }
}
