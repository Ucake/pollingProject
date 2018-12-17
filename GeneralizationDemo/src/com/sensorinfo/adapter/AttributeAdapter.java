package com.sensorinfo.adapter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.github.mikephil.charting.components.YAxis;
import com.example.adpter.MyAdapter.InnerItemOnclickListener;
import com.example.generalizationdemo.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sensorinfo.adapter.EquipmentAdapter.Holder;
import com.sensorinfor.bean.Attribute;
import com.sensorinfor.bean.Constant;
import com.sensorinfor.bean.EquipmentInfo;
import com.sensorinfor.thread.HistoryThread;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AttributeAdapter extends BaseAdapter {
	private ArrayList<Attribute> mArrayList;
	private Context mContext;
	private LayoutInflater inflater;
	private String butStr;
	private String sendTimeStr;
	private Constant mConstant;
	private  Map<String,String> mHaMap_name_history;
	
	public String getSendTimeStr() {
		return sendTimeStr;
	}

	public void setSendTimeStr(String sendTimeStr) {
		this.sendTimeStr = sendTimeStr;
	}

	public String getButStr() {
		return butStr;
	}

	public void setButStr(String butStr) {
		this.butStr = butStr;
	}

	public AttributeAdapter(ArrayList<Attribute> mArrayList,
			Context mContext) {
		super();
		this.mArrayList = mArrayList;
		this.mContext = mContext;
		
		inflater = LayoutInflater.from(mContext);
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	     String time = format.format(new Date());
	     setButStr(time);
	     setSendTimeStr(time);
	     mConstant = new Constant();
	     mHaMap_name_history = mConstant.getmHaMap_name_history();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mArrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.attribute_item, parent, false);
			holder.r = (RelativeLayout) convertView.findViewById(R.id.attribute_item_r);
			holder.name = (TextView) convertView.findViewById(R.id.attribute_item_name);
			holder.time = (TextView) convertView.findViewById(R.id.attribute_item_time);
			holder.value = (TextView) convertView.findViewById(R.id.attribute_item_value);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 80);
//		params.setMargins(35, 0, 0, 0);
//		holder.r.setLayoutParams(params);
//		if(position % 2 == 0){
//			holder.r.setBackgroundColor(Color.parseColor("#B0E0E6"));
//		}else{
//			holder.r.setBackgroundColor(Color.parseColor("#00BFFF"));
//		}
		
		holder.name.setText(mArrayList.get(position).getItem_name());
//		holder.name.setText()
		holder.name.setTextSize(16);
		holder.value.setTextSize(16);
		holder.time.setTextSize(16);
		holder.value.setText(mArrayList.get(position).getValue());
		holder.time.setText(mArrayList.get(position).getItem_time());
		final ProgressDialog pro = new ProgressDialog(mContext);
		pro.setTitle("��ʾ��");
		pro.setMessage("���ڼ���...");
		final Handler  mHandler_history = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.what == 3){
					String backMsg = msg.obj.toString();
					pro.dismiss();
//					System.out.println("������ʷ���߷�������"+backMsg);
					JSONObject json;
					try {
						json = new JSONObject(backMsg);
						String result = json.getString("result");
						 if(result.equals("10000")){
							 newDialog(position, msg, json);
						 }else if(result.equals("10001")){
							 Toast.makeText(mContext, "����ʧ�ܣ������ԣ�����", Toast.LENGTH_SHORT).show();
						 }
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				}
			}
		};
			convertView.setClickable(true);
			
		     convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("AttributeAdapter.java����¼�ִ������");
				
//				if(mArrayList.get(position).getProject_name().equals("three")){
//					final String[] name = {"A���ѹ","B���ѹ","C���ѹ","A������״̬","B������״̬","C������״̬","Ƶ��","A�����","B�����","C�����","���й�����","���޹�����","���ӹ�����","���й�����","���޹�����"};
//					 AlertDialog.Builder builderAlert = new AlertDialog.Builder(mContext);
//					 builderAlert.setTitle("ѡ������:");
//					 builderAlert.setSingleChoiceItems(name, 0, new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							 String sendStr = "sensor_id="+mArrayList.get(position).getSensor_id()+"&project_name="+"three"+"&equipment_name="+mArrayList.get(position).getEquipment_name()+"&time="+sendTimeStr;
//						     System.out.println("���Ƿ��͵����ݣ�"+sendStr);
//						     String urlStr = "http://193.112.62.125:80/water/app.req?action=history";
//						     HistoryThread h = new HistoryThread(sendStr, mHandler_history, urlStr, which);
//						     new Thread(h).start();
//							 Toast.makeText(mContext, name[which] + "�������", Toast.LENGTH_SHORT).show();  
//						}
//				  });
////					 
//					  AlertDialog l = builderAlert.create();
//				 
//				 l.show();
//					
//				}else{
				pro.show();
					 String sendStr = "sensor_id="+mArrayList.get(position).getSensor_id()+"&project_name="+mArrayList.get(position).getProject_name()+"&equipment_name="+mArrayList.get(position).getEquipment_name()
							 +"&time="+sendTimeStr+"&item_id="+mArrayList.get(position).getItem_id();
				     System.out.println("���Ƿ��͵����ݣ�"+sendStr);
				     String urlStr = "http://193.112.62.125:80/water/app.req?action=history";
				     HistoryThread h = new HistoryThread(sendStr, mHandler_history, urlStr);
				     new Thread(h).start();
//				}

			    
			     
			    
				
				
			}
		});
		
		     
		return convertView;
	}
	class Holder{
		TextView name;
		TextView value;
		TextView time;
		RelativeLayout r;
		
	}
	
	
    
   
    
    private void showChart(LineChart lineChart, LineData lineData, int color) {    
        lineChart.setDrawBorders(false);  //�Ƿ�������ͼ����ӱ߿�    
    
        // no description text    
        lineChart.setDescription("");// ��������    
        // ���û�����ݵ�ʱ�򣬻���ʾ���������listview��emtpyview    
        lineChart.setNoDataTextDescription("You need to provide data for the chart.");    
            
        // enable / disable grid background    
//        lineChart.setDrawGridBackground(false); // �Ƿ���ʾ�����ɫ    
        lineChart.setDrawGridBackground(true); // ����ʾ�����ɫ    
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
//        mLegend.setForm(LegendForm.CIRCLE);// ��ʽ    
        mLegend.setForm(LegendForm.CIRCLE);// ��ʽ    
        mLegend.setFormSize(6f);// ����    
        mLegend.setTextColor(Color.BLACK);// ��ɫ    
//      mLegend.setTypeface(mTf);// ����    
    
        lineChart.animateX(2500); // ����ִ�еĶ���,x��    
      //����x�����ɼ���Χ�����������������֮��������ã�
//        System.out.println("���ͼ������������������");
        lineChart.setVisibleXRangeMaximum(10);
      }    
        
    /**  
     * ����һ������  
     * @param count ��ʾͼ�����ж��ٸ������  
     * @param range ��������range���ڵ������  
     * @return  
     */  
    private LineData getLineData(int count, float range,JSONArray data,String item_unit,String item_name,String flag) {    
        ArrayList<String> xValues = new ArrayList<String>();    
        
        // y�������    
        ArrayList<Entry> yValues = new ArrayList<Entry>();    
        try {
        	for (int i = 0; i < data.length(); i++) {
        		JSONObject dataO = data.getJSONObject(i);
        		if(i == data.length() - 1){
        			setButStr(dataO.getString("time").substring(0, 11));
        			setSendTimeStr(dataO.getString("time"));
        		} 
        		String time = dataO.getString("time").substring(11,dataO.getString("time").length()-2);
        		String value = "";
//        		System.out.println("�ж��Ƿ�Ϊ���������"+item_name);
//        		if("three".equals(flag) || "acc".equals(flag)){
        			value = dataO.getString(mHaMap_name_history.get(item_name));
        			
//        			System.out.println("�ж��Ƿ�Ϊ���������"+value);
//        		}
//        		else
//				 value = dataO.getString("value");
//				System.out.println("�����ʷ����ͼ��ֵ�ǣ�"+value);
				yValues.add(new Entry(Float.parseFloat(value), i));
				xValues.add(time);
        	}
        	}catch (Exception e) {
				// TODO: handle exception
			}
        
    
        // create a dataset and give it a type    
        // y������ݼ���    
        LineDataSet lineDataSet = new LineDataSet(yValues, item_unit /*��ʾ�ڱ���ͼ��*/);    
        // mLineDataSet.setFillAlpha(110);    
        // mLineDataSet.setFillColor(Color.RED);    
    
        //��y��ļ��������ò���    
        lineDataSet.setLineWidth(1.75f); // �߿�    
        lineDataSet.setCircleSize(3f);// ��ʾ��Բ�δ�С    
        lineDataSet.setColor(Color.BLACK);// ��ʾ��ɫ    
        lineDataSet.setCircleColor(Color.WHITE);// Բ�ε���ɫ    
        lineDataSet.setHighLightColor(Color.YELLOW); // �������ߵ���ɫ    
    
//        List<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();    
//        lineDataSets.add(lineDataSet); // add the datasets    
    
        // create a data object with the datasets    
        LineData lineData = new LineData(xValues, lineDataSet);    
        
        return lineData;    
    }   
//    private LineData getLineData(int count, float range,JSONArray data,String flag ,int findYvalues) {  
//    	ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//        ArrayList<String> xValues = new ArrayList<String>();    
//        Map<Integer,LineDataSet> yValuesMap = new HashMap<Integer, LineDataSet>();
//        
//        
//         if(flag.equals("three")){
//        	ArrayList<Entry> yValues_1 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_2 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_3 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_4 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_5 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_6 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_7 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_8 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_9 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_10 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_11= new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_12= new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_13= new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_14= new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_15= new ArrayList<Entry>();
//        	 for (int i = 0; i < data.length(); i++) {
//       		 try {
//    				JSONObject dataO = data.getJSONObject(i);
//    				if(i == data.length() - 1){
//    					setButStr(dataO.getString("time").substring(0, 11));
//    					setSendTimeStr(dataO.getString("time"));
//    				}
//    				String time = dataO.getString("time").substring(11,dataO.getString("time").length()-2);
//    				String ua = dataO.getString("ua");
//					String ub = dataO.getString("ub");
//					String uc = dataO.getString("uc");
//					String dxa = dataO.getString("dxa");
//					String dxb = dataO.getString("dxb");
//					String dxc = dataO.getString("dxc");
//					String freq = dataO.getString("freq");
//					String ia = dataO.getString("ia");
//					String ib = dataO.getString("ib");
//					String ic = dataO.getString("ic");
//					String pt = dataO.getString("pt");
//					String qt = dataO.getString("qt");
//					String st = dataO.getString("st");
//					String wpt = dataO.getString("wpt");
//					String wqt = dataO.getString("wqt");
//					yValues_1.add(new Entry(Float.parseFloat(ua), i));
//					yValues_2.add(new Entry(Float.parseFloat(ub), i));
//					yValues_3.add(new Entry(Float.parseFloat(uc), i));
//					yValues_4.add(new Entry(Float.parseFloat(dxa), i));
//					yValues_5.add(new Entry(Float.parseFloat(dxb), i));
//					yValues_6.add(new Entry(Float.parseFloat(dxc), i));
//					yValues_7.add(new Entry(Float.parseFloat(freq), i));
//					yValues_8.add(new Entry(Float.parseFloat(ia), i));
//					yValues_9.add(new Entry(Float.parseFloat(ib), i));
//					yValues_10.add(new Entry(Float.parseFloat(ic), i));
//					yValues_11.add(new Entry(Float.parseFloat(pt), i));
//					yValues_12.add(new Entry(Float.parseFloat(qt), i));
//					yValues_13.add(new Entry(Float.parseFloat(st), i));
//					yValues_14.add(new Entry(Float.parseFloat(wpt), i));
//					yValues_15.add(new Entry(Float.parseFloat(wqt), i));
//					xValues.add(time);
//       		 }catch (JSONException e) {
//    				// TODO Auto-generated catch block
//    				e.printStackTrace();
//    			}
//       	 }
//        	 // create a dataset and give it a type    
//            // y������ݼ���    
//            LineDataSet lineDataSet_1 = new LineDataSet(yValues_1, " " /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_2 = new LineDataSet(yValues_2, "B���ѹ/v" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_3 = new LineDataSet(yValues_3, "C���ѹ/v" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_4 = new LineDataSet(yValues_4, "A������״̬" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_5 = new LineDataSet(yValues_5, "B������״̬/v" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_6 = new LineDataSet(yValues_6, "C������״̬" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_7 = new LineDataSet(yValues_7, "Ƶ��/hz" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_8 = new LineDataSet(yValues_8, "A�����/A" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_9 = new LineDataSet(yValues_9, "B�����/A" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_10 = new LineDataSet(yValues_10, "C�����/A" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_11= new LineDataSet(yValues_11, "���й�����/KW" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_12= new LineDataSet(yValues_12, "���޹�����/KVar" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_13= new LineDataSet(yValues_13, "���ӹ�����/KVA" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_14= new LineDataSet(yValues_14, "���й�����/KVh" /*��ʾ�ڱ���ͼ��*/);    
//            LineDataSet lineDataSet_15= new LineDataSet(yValues_15, "���޹�����/KVarh" /*��ʾ�ڱ���ͼ��*/);   
//            setParam(lineDataSet_15);
//            setParam(lineDataSet_14);
//            setParam(lineDataSet_13);
//            setParam(lineDataSet_12);
//            setParam(lineDataSet_11);
//            setParam(lineDataSet_10);
//            setParam(lineDataSet_9);
//            setParam(lineDataSet_8);
//            setParam(lineDataSet_7);
//            setParam(lineDataSet_6);
//            setParam(lineDataSet_5);
//            setParam(lineDataSet_4);
//            setParam(lineDataSet_3);
//            setParam(lineDataSet_2);
//            setParam(lineDataSet_1);
//            // mLineDataSet.setFillAlpha(110);    
//            // mLineDataSet.setFillColor(Color.RED);    
//            yValuesMap.put(0, lineDataSet_1);
//            yValuesMap.put(1, lineDataSet_2);
//            yValuesMap.put(2, lineDataSet_3);
//            yValuesMap.put(3, lineDataSet_4);
//            yValuesMap.put(4, lineDataSet_5);
//            yValuesMap.put(5, lineDataSet_6);
//            yValuesMap.put(6, lineDataSet_7);
//            yValuesMap.put(7, lineDataSet_8);
//            yValuesMap.put(8, lineDataSet_9);
//            yValuesMap.put(9, lineDataSet_10);
//            yValuesMap.put(10, lineDataSet_11);
//            yValuesMap.put(11, lineDataSet_12);
//            yValuesMap.put(12, lineDataSet_13);
//            yValuesMap.put(13 ,lineDataSet_14);
//            yValuesMap.put(14, lineDataSet_15);
//            System.out.println("��ѡ������"+findYvalues);
//            //��y��ļ��������ò���    
//            dataSets.add(yValuesMap.get(findYvalues));
//        }
//        else if(flag.equals("acc")){
//        	ArrayList<Entry> yValues_1 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_2 = new ArrayList<Entry>();
//        	ArrayList<Entry> yValues_3 = new ArrayList<Entry>();
//        	for (int i = 0; i < data.length(); i++) {
//        		try {
//        			JSONObject dataO = data.getJSONObject(i);
//        			if(i == data.length() - 1){
//        				setButStr(dataO.getString("time").substring(0, 11));
//        				setSendTimeStr(dataO.getString("time"));
//    				}
//        			String time = dataO.getString("time").substring(11,dataO.getString("time").length()-2);
//        			String ax = dataO.getString("ax");
//					String ay = dataO.getString("ay");
//					String az = dataO.getString("az");
//        			yValues_1.add(new Entry(Float.parseFloat(ax), i));
//        			yValues_2.add(new Entry(Float.parseFloat(ay), i));
//        			yValues_3.add(new Entry(Float.parseFloat(az), i));
//        			xValues.add(time);
//        		}catch (JSONException e) {
//        			// TODO Auto-generated catch block
//        			e.printStackTrace();
//        		}
//        	}
//        	// create a dataset and give it a type    
//        	// y������ݼ���    
//        	LineDataSet lineDataSet_1 = new LineDataSet(yValues_1, "" /*��ʾ�ڱ���ͼ��*/);    
//        	LineDataSet lineDataSet_2 = new LineDataSet(yValues_2, "" /*��ʾ�ڱ���ͼ��*/);    
//        	LineDataSet lineDataSet_3 = new LineDataSet(yValues_3, "" /*��ʾ�ڱ���ͼ��*/);    
//        	// mLineDataSet.setFillAlpha(110);    
//        	// mLineDataSet.setFillColor(Color.RED);    
//        	
//        	//��y��ļ��������ò���    
//        	lineDataSet_1.setLineWidth(1.75f); // �߿�    
//        	lineDataSet_1.setCircleSize(3f);// ��ʾ��Բ�δ�С    
//        	lineDataSet_1.setColor(Color.BLACK);// ����ɫ        
//        	lineDataSet_1.setCircleColor(Color.BLACK);// Բ�ε���ɫ    
//        	dataSets.add(lineDataSet_1);
//        	lineDataSet_2.setLineWidth(1.75f); // �߿�    
//        	lineDataSet_2.setCircleSize(3f);// ��ʾ��Բ�δ�С    
//        	lineDataSet_2.setColor(Color.WHITE);// ����ɫ        
//        	lineDataSet_2.setCircleColor(Color.WHITE);// Բ�ε���ɫ    
//        	
//        	dataSets.add(lineDataSet_2);
//        	lineDataSet_3.setLineWidth(1.75f); // �߿�    
//        	lineDataSet_3.setCircleSize(3f);// ��ʾ��Բ�δ�С    
//        	lineDataSet_3.setColor(Color.BLUE);// ����ɫ        
//        	lineDataSet_3.setCircleColor(Color.BLUE);// Բ�ε���ɫ    
//        	dataSets.add(lineDataSet_3);
//        } else {
//        	ArrayList<Entry> yValues = new ArrayList<Entry>();
//        	 for (int i = 0; i < data.length(); i++) {
//       		 try {
//    				JSONObject dataO = data.getJSONObject(i);
//    				if(i == data.length() - 1){
//    					setButStr(dataO.getString("time").substring(0, 11));
//    					setSendTimeStr(dataO.getString("time"));
//    				}
//    				String time = dataO.getString("time").substring(11,dataO.getString("time").length()-2);
//    				String value = dataO.getString("value");
////					System.out.println("�����ʷ����ͼ��ֵ�ǣ�"+value);
//					yValues.add(new Entry(Float.parseFloat(value), i));
//					xValues.add(time);
//       		 }catch (JSONException e) {
//    				// TODO Auto-generated catch block
//    				e.printStackTrace();
//    			}
//       	 }
//        	 // create a dataset and give it a type    
//            // y������ݼ���    
//        	 LineDataSet lineDataSet = null;    
//        	 if(flag.equals("tem")){
//        		 lineDataSet = new LineDataSet(yValues, "�¶�" /*��ʾ�ڱ���ͼ��*/);
//        	 }else if(flag.equals("oil")){
//        		 lineDataSet = new LineDataSet(yValues, "��ѹ" /*��ʾ�ڱ���ͼ��*/);
//        		 
//        	 }else if(flag.equals("pre")){
//        		 lineDataSet = new LineDataSet(yValues, "��ѹ" /*��ʾ�ڱ���ͼ��*/);
//        		 
//        	 }
//            // mLineDataSet.setFillAlpha(110);    
//            // mLineDataSet.setFillColor(Color.RED);    
//        
//            //��y��ļ��������ò���    
//           setParam(lineDataSet) ;
//            dataSets.add(lineDataSet);
//        
////            List<ILineDataSet> lineDataSets = new ArrayList<ILineDataSet>();    
////            lineDataSets.add(lineDataSet); // add the datasets    
//        
//            // create a data object with the datasets    
//           
//       	
//       }
//       
//        
//        LineData lineData = new LineData(xValues, dataSets); 
//        return lineData;    
//    }   
    private void setParam(LineDataSet lineDataSet){
    	lineDataSet.setLineWidth(1.75f); // �߿�    
    	lineDataSet.setCircleSize(3f);// ��ʾ��Բ�δ�С    
    	lineDataSet.setColor(Color.BLACK);// ����ɫ        
    	lineDataSet.setCircleColor(Color.BLACK);// Բ�ε���ɫ  
    }

	private void newDialog(final int position, Message msg, JSONObject json)
			throws JSONException {
		
		 AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		 
		 builder.setTitle("��ʷ���ߣ�");
		 
			View view = LayoutInflater.from(mContext).inflate(
					R.layout.history_curve, null);
			final LineChart mLineChart = (LineChart) view.findViewById(R.id.history_curve_linechar);
//			TextView state = (TextView) view.findViewById(R.id.history_curve_state);
			 LineData mLineData2 = getLineData(6, 10,json.getJSONArray("data"),mArrayList.get(position).getItem_unit(),mArrayList.get(position).getItem_name(),mArrayList.get(position).getProject_name());
		     showChart(mLineChart, mLineData2, Color.rgb(114, 188, 223));
//		     TextView maxvalue = (TextView) view.findViewById(R.id.history_curve_maxvalue);
//		     TextView minvalue = (TextView) view.findViewById(R.id.history_curve_minvalue);
//		     TextView avgvalue = (TextView) view.findViewById(R.id.history_curve_avgvalue);
//		     TextView value = (TextView) view.findViewById(R.id.history_curve_value);
//										    state.setText(json.getString("status"));
//										    maxvalue.setText(json.getString("max"));
//										    minvalue.setText(json.getString("min"));
//										    avgvalue.setText(json.getString("avg"));
//										    value.setText(json.getString("rightNow"));
		    
//										    System.out.println("�����ʷ������ʾ����");
		    
		    
		    
		     builder.setPositiveButton("��ʾ"+butStr+"֮ǰ������", null);
		     
		     builder.setView(view);
		     final AlertDialog  dialog = builder.create();
		     dialog .show();
		     
		     dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
//					dialog.getButton(AlertDialog.BUTTON_POSITIVE).setText(addEntry(mLineChart,mArrayList.get(position).getProject_name(),position));
					final Handler  mHandler_history = new Handler(){

						@Override
						public void handleMessage(Message msg) {
							// TODO Auto-generated method stub
							super.handleMessage(msg);
							dialog.dismiss();
							if(msg.what == 3){
								String backMsg = msg.obj.toString();
//								System.out.println("������ʷ���߷�������"+backMsg);
								JSONObject json;
								try {
									json = new JSONObject(backMsg);
									String result = json.getString("result");
									 if(result.equals("10000")){
										 newDialog(position, msg, json);
									 }else if(result.equals("10001")){
										 Toast.makeText(mContext, "����ʧ�ܣ������ԣ�����", Toast.LENGTH_SHORT).show();
									 }
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								 
							}
						}
					};
					 String sendStr = "sensor_id="+mArrayList.get(position).getSensor_id()+"&project_name="+mArrayList.get(position).getProject_name()+"&equipment_name="+mArrayList.get(position).getEquipment_name()
							 +"&time="+sendTimeStr+"&item_id="+mArrayList.get(position).getItem_id();
				     System.out.println("���Ƿ��͵����ݣ�"+sendStr);
				     String urlStr = "http://193.112.62.125:80/water/app.req?action=history";
				     HistoryThread h = new HistoryThread(sendStr, mHandler_history, urlStr);
				     new Thread(h).start();
				}
			});
	}
	public String addEntry(final LineChart mLineChart,final String flag,int position) {
		
		Handler handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.what == 3){
					String backMsg = msg.obj.toString();
					int which = msg.arg1;
					System.out.println("������ʷ���߷�������"+backMsg);
					JSONObject json;
					try {
						json = new JSONObject(backMsg);
						String result = json.getString("result");
						 if("10000".equals(result)){
							 LineData lineData = mLineChart.getLineData();
							
							 ArrayList<String> xValues = (ArrayList<String>) lineData.getXVals();
							
								
							        if (lineData != null) {
							            int indexLast = getLastDataSetIndex(lineData);
							            LineDataSet lastSet = (LineDataSet) lineData.getDataSetByIndex(indexLast);
							            // set.addEntry(...); // can be called as well

							         
							            JSONArray data_array = json.getJSONArray("data");
//							            ArrayList<Entry> yValues = new ArrayList<Entry>();    
							            try {
							            	for (int i = 0; i < data_array.length(); i++) {
							            		JSONObject dataO = data_array.getJSONObject(i);
							            		if(i == data_array.length() - 1){
							            			setButStr(dataO.getString("time").substring(0, 11));
							            			setSendTimeStr(dataO.getString("time"));
							            		} 
							            		String time = dataO.getString("time").substring(11,dataO.getString("time").length()-2);
							    				String value = dataO.getString("value");
//							    				System.out.println("�����ʷ����ͼ��ֵ�ǣ�"+value);
//							    				yValues.add(new Entry(Float.parseFloat(value), i));
//							    				xValues.add(time);
							    				 lineData.addXValue(time);
							    				lineData.addEntry(new Entry(Float.parseFloat(value), lastSet.getEntryCount()+i),indexLast+i);
							            	}
							            	}catch (Exception e) {
							    				// TODO: handle exception
							    			}
							            
										
							            // ����Ҫע�⣬x���index�Ǵ��㿪ʼ��
//							            // ����index=2����ôgetEntryCount()�͵���3��
//							            int count = lastSet.getEntryCount();
//							            // add a new x-value first ���д��벻����
//							            lineData.addXValue(count + "");
//							            
//							            float yValues = (float) (Math.random() * 100);
//							            // λ���һ��DataSet���entry
//							            lineData.addEntry(new Entry(yValues, count), indexLast);
							            
							            lineData.notifyDataChanged();
							            mLineChart.notifyDataSetChanged();
							            mLineChart.invalidate();
							            mLineChart.moveViewToX(lineData.getXValCount() - 5);
							        }
							 
						 }else if("10001".equals(result)){
							 Toast.makeText(mContext, "����ʧ�ܣ������ԣ�����", Toast.LENGTH_SHORT).show();
						 }
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
        
		 String sendStr = "sensor_id="+mArrayList.get(position).getSensor_id()+"&project_name="+mArrayList.get(position).getProject_name()+"&equipment_name="+mArrayList.get(position).getEquipment_name()
				 +"&time="+sendTimeStr+"&item_id="+mArrayList.get(position).getItem_id();
	     System.out.println("���Ƿ��͵����ݣ�"+sendStr);
	     String urlStr = "http://193.112.62.125:80/water/app.req?action=history";
	     HistoryThread h = new HistoryThread(sendStr, handler, urlStr);
	     new Thread(h).start();
		return butStr;

           

       
    }
	 private int getLastDataSetIndex(LineData lineData) {
	        int dataSetCount = lineData.getDataSetCount();
	        return dataSetCount > 0 ? (dataSetCount - 1) : 0;
	    }
	 private LineDataSet createLineDataSet() {
	        LineDataSet dataSet = new LineDataSet(null, "DataSet 1");
	        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
	        dataSet.setValueTextSize(12f);

	        return dataSet;
	    }
}
