package com.sensorinfor.main;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Color;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.google.gson.JsonObject;

public class BarChart3s {
//	public String[] color_array = {"#1E90FF","#00BFFF","#87CEEB","#4682B4"};
	public String[] color_array = {"#FFBBFF","#FF0000","#F0F0F0","#FFEFD5"};
	
	//private String stttcs;
	private JSONArray data;
	public BarChart3s(BarChart chart,JSONArray data) {
		this.data = data;
        // ��������
        chart.setDescription("");
        //����
//        chart.setBackgroundColor(0xffffffff);
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

    public List<IBarDataSet> getDataSet(){
        List<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valueSet2 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valueSet3 = new ArrayList<BarEntry>();
        ArrayList<BarEntry> valueSet4 = new ArrayList<BarEntry>();
        for (int i = 0; i < data.length(); i++) {
			try {
				JSONObject dataO = data.getJSONObject(i);
				String broken = dataO.getString("broken");
				String fixing = dataO.getString("fixing");
				String normal = dataO.getString("normal");
				String stop = dataO.getString("stop");
				valueSet1.add(new BarEntry(Integer.parseInt(broken), i));
				valueSet2.add(new BarEntry(Integer.parseInt(fixing), i));
				valueSet3.add(new BarEntry(Integer.parseInt(normal), i));
				valueSet4.add(new BarEntry(Integer.parseInt(stop), i));
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//        ArrayList<BarEntry> valueSet1 = new ArrayList<BarEntry>();
//        for (int i = 0; i < 4; i++) {
//            int value = (int) (Math.random() * 10/*100���ڵ������*/) ;
//            valueSet1.add(new BarEntry(value,i));}
        
       // stttcs = HomePageya.statistics;
//        try{
//        JSONObject stttcs_object = new JSONObject(stttcs);
//        org.json.JSONArray data = stttcs_object.getJSONArray("data");
//        System.out.print(data.length());
//        for (int j = 0; j < data.length(); j++) {
//        	JSONObject note = data.getJSONObject(j);
//        	int value = Integer.parseInt(note.getString("count"));
//        	Log.v("aiyuchou", String.valueOf(value));
//        	valueSet1.add(new BarEntry(value, j));
//        }
//        }catch (JSONException e) {
//	        e.printStackTrace();
//		    
//		}


//        ArrayList<BarEntry> valueSet2 = new ArrayList<BarEntry>();
//        for (int j = 0; j < 4; j++) {
//            int value1 = (int) (Math.random() * 10/*100���ڵ������*/) ;
//            valueSet2.add(new BarEntry(value1,j));}
        
//        ArrayList<BarEntry> valueSet3 = new ArrayList<BarEntry>();
//        for (int k = 0; k < 4; k++) {
//            int value1 = (int) (Math.random() * 10/*100���ڵ������*/) ;
//            valueSet3.add(new BarEntry(value1,k));}


        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "����");
        barDataSet1.setColor(Color.parseColor("#FFBBFF"));
        barDataSet1.setBarShadowColor(Color.parseColor("#01000000"));

        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "��ģ");
        barDataSet2.setColor(Color.parseColor("#FF0000"));
        barDataSet2.setBarShadowColor(Color.parseColor("#01000000"));
        
        BarDataSet barDataSet3 = new BarDataSet(valueSet3, "����");
        barDataSet3.setColor(Color.parseColor("#F0F0F0"));
        barDataSet3.setBarShadowColor(Color.parseColor("#01000000"));
        
        BarDataSet barDataSet4 = new BarDataSet(valueSet4, "ͣ��");
        barDataSet3.setColor(Color.parseColor("#FFEFD5"));
        barDataSet3.setBarShadowColor(Color.parseColor("#01000000"));
        
//         String[] color_array = {"#1E90FF","#00BFFF","#87CEEB","#4682B4"};
        dataSets.add(barDataSet1);
        //ֻ��һ��ͼ
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);
        dataSets.add(barDataSet4);

        return dataSets;
    }

    public ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<String>();
//        for (int j = 0; j < 4; j++){
//            xAxis.add((j+1)+"��");
//        }
        for (int i = 0; i < data.length(); i++) {
			  try {
				JSONObject datao = data.getJSONObject(i);
				String time = datao.getString("time");
				xAxis.add(time);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  
		}
//        String stat_x = HomePageya.statistics;
//        try{
//            JSONObject stttcs_object = new JSONObject(stat_x);
//            org.json.JSONArray month = stttcs_object.getJSONArray("data");
//            
//            for (int j = 0; j < month.length(); j++) {
//            	JSONObject note = month.getJSONObject(j);
//            	int value = Integer.parseInt(note.getString("time").substring(5));
//            	xAxis.add(value+"��");
//            }
//            }catch (JSONException e) {
//    	        e.printStackTrace();
//    		    
//    		}
        
        return xAxis;
    }
}
