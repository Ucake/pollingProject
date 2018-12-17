
package com.generalization.chart;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
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

public class BarChart3s {
	
	private String stat_x;
	private String stttcs;
	private String status = MainActivity.statusya;
	
	public BarChart3s(BarChart chart) {
		
		
        // ��������
        chart.setDescription("");
        //����
        chart.setBackgroundColor(0xffffffff);
        //��������������λ��
//        chart.setDescriptionPosition(2,100);
        // �����������ֵ���ɫ
        // chart.setDescriptionColor(0xffededed);
//        chart.setDescriptionTextSize(30);
        // ����
        chart.animateY(1000);
        //�����ޱ߿�
        chart.setDrawBorders(false);
        // �����Ƿ���Դ���
        chart.setTouchEnabled(true);
        // �Ƿ������ק
        chart.setDragEnabled(true);
        // �Ƿ��������
        chart.setScaleEnabled(true);
        //�������񱳾�
        chart.setGridBackgroundColor(0xffffffff);
        //���ñ��߿��
        chart.setBorderWidth(0);
        //���ñ�����ɫ
        chart.setBorderColor(0xffffffff);
        // ��˫ָ����
        chart.setPinchZoom(false);
        // �����ұߵ�������
        chart.getAxisRight().setEnabled(false);
        // ������ߵ������
        chart.getAxisLeft().setEnabled(true);
        
//        chart.setTextDirection("���ô");

        Legend mLegend = chart.getLegend(); // ���ñ���ͼ��ʾ
        // ���ô�����ʽ
        mLegend.setForm(Legend.LegendForm.SQUARE);
        //����ͼ��λ��
        mLegend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);
        // ����
        mLegend.setFormSize(4f);
        //�Ƿ���ʾע��
        mLegend.setEnabled(true);
        // ������ɫ
//        mLegend.setTextColor(Color.parseColor("#7e7e7e"));
        
        
        
        
        
        //����X��λ��
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        // ǰ��xAxis.setEnabled(false);��������Ƶ�Grid������"������"����X���йأ�
        // �����һ�д���������false,���������һ�м�ʹ����ΪtrueҲ�������AxisLine
        //�������ߵ���ɫ
        xAxis.setAxisLineColor(0xffffffff);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        //����Y��
        YAxis leftAxis = chart.getAxisLeft();
        //Y����ɫ
        leftAxis.setAxisLineColor(0xffffffff);
        //Y���������ɫ
        leftAxis.setGridColor(0xfff3f3f3);
        //�����߳���
        leftAxis.setAxisLineWidth(5f);
        // ���������ֵվ����ռ��
        leftAxis.setSpaceTop(20f);

        chart.invalidate();
    }

    public List<IBarDataSet> getDataSet() {
        List<IBarDataSet> dataSets = null;
        //������
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
//        for (int i = 0; i < 4; i++) {
//            int value = (int) (Math.random() * 10/*100���ڵ������*/) ;
//            valueSet1.add(new BarEntry(value,i));}
        
        if(status.equals("worker")){
        	stttcs = SecondActivity.statistics;
        }else{
        	stttcs = HomePageya.statistics;
        }
        
        
        try{
        JSONObject stttcs_object = new JSONObject(stttcs);
        org.json.JSONArray data = stttcs_object.getJSONArray("data");
        System.out.print(data.length());
        for (int j = 0; j < data.length(); j++) {
        	JSONObject note = data.getJSONObject(j);
        	int count = Integer.parseInt(note.getString("count"));
//        	int alarm = Integer.parseInt(note.getString("alarm"));
//        	int hidden_danger = Integer.parseInt(note.getString("hidden_danger"));
        	int question = Integer.parseInt(note.getString("question"));
        	Log.v("aiyuchou", String.valueOf(question));
        	valueSet1.add(new BarEntry(question, j));
        }
        }catch (JSONException e) {
	        e.printStackTrace();
		    
		}

        //������
        ArrayList<BarEntry> valueSet2 = new ArrayList<BarEntry>();
//        for (int j = 0; j < 4; j++) {
//            int value1 = (int) (Math.random() * 10/*100���ڵ������*/) ;
//            valueSet2.add(new BarEntry(value1,j));}
        
        try{
            JSONObject stttcs_object = new JSONObject(stttcs);
            org.json.JSONArray data = stttcs_object.getJSONArray("data");
            System.out.print(data.length());
            for (int j = 0; j < data.length(); j++) {
            	JSONObject note = data.getJSONObject(j);
//            	int count = Integer.parseInt(note.getString("count"));
//            	int alarm = Integer.parseInt(note.getString("alarm"));
            	int hidden_danger = Integer.parseInt(note.getString("hidden_danger"));
//            	int question = Integer.parseInt(note.getString("question"));
            	Log.v("leidi", String.valueOf(hidden_danger));
            	valueSet2.add(new BarEntry(hidden_danger, j));
            }
            }catch (JSONException e) {
    	        e.printStackTrace();
    		    
    		}
        
        
        //������
        ArrayList<BarEntry> valueSet3 = new ArrayList<BarEntry>();
//        for (int k = 0; k < 4; k++) {
//            int value1 = (int) (Math.random() * 10/*100���ڵ������*/) ;
//            valueSet3.add(new BarEntry(value1,k));}
        
        
        try{
            JSONObject stttcs_object = new JSONObject(stttcs);
            org.json.JSONArray data = stttcs_object.getJSONArray("data");
            System.out.print(data.length());
            for (int j = 0; j < data.length(); j++) {
            	JSONObject note = data.getJSONObject(j);
//            	int count = Integer.parseInt(note.getString("count"));
            	int alarm = Integer.parseInt(note.getString("alarm"));
//            	int hidden_danger = Integer.parseInt(note.getString("hidden_danger"));
//            	int question = Integer.parseInt(note.getString("question"));
            	Log.v("wuchuduo", String.valueOf(alarm));
            	valueSet3.add(new BarEntry(alarm, j));
            }
            }catch (JSONException e) {
    	        e.printStackTrace();
    		    
    		}

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "������");
        barDataSet1.setColor(Color.parseColor("#cc0033"));
        barDataSet1.setBarShadowColor(Color.parseColor("#01000000"));

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "������");
        barDataSet2.setColor(Color.parseColor("#6faae7"));
        barDataSet2.setBarShadowColor(Color.parseColor("#01000000"));
        
        BarDataSet barDataSet3 = new BarDataSet(valueSet3, "������");
        barDataSet3.setColor(Color.parseColor("#cc0033"));
        barDataSet3.setBarShadowColor(Color.parseColor("#01000000"));

        dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(barDataSet1);
        //20180416����3��ͼ��һ��
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);

        return dataSets;
    }

    public ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<String>();
//        for (int j = 0; j < 4; j++){
//            xAxis.add((j+1)+"��");
//        }
        
        if(status.equals("worker")){
        	 stat_x = SecondActivity.statistics;
        }else{
        	 stat_x = HomePageya.statistics;
        }
        
        
        try{
            JSONObject stttcs_object = new JSONObject(stat_x);
            org.json.JSONArray month = stttcs_object.getJSONArray("data");
            
            for (int j = 0; j < month.length(); j++) {
            	JSONObject note = month.getJSONObject(j);
//            	int value = Integer.parseInt(note.getString("time").substring(5));
            	String value = note.getString("time").substring(5);
            	xAxis.add(value+"��");
            }
            }catch (JSONException e) {
    	        e.printStackTrace();
    		    
    		}
        
        return xAxis;
    }
}
