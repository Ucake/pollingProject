package com.cement.chart;

import java.util.ArrayList;
import java.util.List;

import com.example.generalizationdemo.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LineChartActivity extends Activity {
	private Button piechart;
	 private LineChart mLineChart;    
	//  private Typeface mTf;    
	    
	    @Override    
	    protected void onCreate(Bundle savedInstanceState) {    
	        super.onCreate(savedInstanceState);    
	        setContentView(R.layout.linechart);    
	            
//	        piechart = (Button) findViewById(R.id.piechart);
//	        piechart.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Intent intent = new Intent();
//					intent.setClass(LineChartActivity.this, PieChartActivity.class);
//					startActivity(intent);
//				}
//			});
	        mLineChart = (LineChart) findViewById(R.id.spread_line_chart);    
	            
//	      mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Bold.ttf");    
	    
	        LineData mLineData = getLineData(36, 100);    
	        showChart(mLineChart, mLineData, Color.rgb(114, 188, 223));    
	    }    
	        
	    // ������ʾ����ʽ    
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
//	      mLegend.setTypeface(mTf);// ����    
	    
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
	        for (int i = 0; i < count; i++) {    
	            // x����ʾ�����ݣ�����Ĭ��ʹ�������±���ʾ    
	            xValues.add("" + i);    
	        }    
	    
	        // y�������    
	        ArrayList<Entry> yValues = new ArrayList<Entry>();    
	        for (int i = 0; i < count; i++) {    
	            float value = (float) (Math.random() * range) + 3;    
	            yValues.add(new Entry(value, i));    
	        }    
	    
	        // create a dataset and give it a type    
	        // y������ݼ���    
	        LineDataSet lineDataSet = new LineDataSet(yValues, "��ͳ����" /*��ʾ�ڱ���ͼ��*/);    
	        // mLineDataSet.setFillAlpha(110);    
	        // mLineDataSet.setFillColor(Color.RED);    
	    
	        //��y��ļ��������ò���    
	        lineDataSet.setLineWidth(1.75f); // �߿�    
	        lineDataSet.setCircleSize(3f);// ��ʾ��Բ�δ�С    
	        lineDataSet.setColor(Color.WHITE);// ��ʾ��ɫ    
	        lineDataSet.setCircleColor(Color.WHITE);// Բ�ε���ɫ    
	        lineDataSet.setHighLightColor(Color.WHITE); // �������ߵ���ɫ    
	    
	        List<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();    
	        lineDataSets.add(lineDataSet); // add the datasets    
	    
	        // create a data object with the datasets    
	        LineData lineData = new LineData(xValues, lineDataSets);    
	    
	        return lineData;    
	    }   
}
