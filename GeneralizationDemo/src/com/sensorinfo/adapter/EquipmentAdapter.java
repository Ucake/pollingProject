package com.sensorinfo.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.Inflater;

import org.json.JSONObject;



import com.example.generalizationdemo.R;
import com.google.gson.Gson;
import com.sensorinfor.bean.EquipmentInfo;
import com.sensorinfor.main.EquActivity;
import com.sensorinfor.main.EquipmentDetails;
import com.sensorinfor.main.MainActivity;
import com.sensorinfor.main.MyTextView;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class EquipmentAdapter extends BaseAdapter {
	private ArrayList<EquipmentInfo> mArrayList;
	private Context mContext;
	private LayoutInflater inflater;
	private ProgressBar proBar = null;
	

	public EquipmentAdapter(ArrayList<EquipmentInfo> mArrayList,
			Context mContext) {
		super();
		this.mArrayList = mArrayList;
		this.mContext = mContext;
		
		init();
		
		
	}

	private void init() {
		// TODO Auto-generated method stub
		inflater = LayoutInflater.from(mContext);
		proBar = (ProgressBar) inflater.inflate(R.layout.probar, null).findViewById(R.id.probar);
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
	public View getView(final int position,  View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.equipment_item, parent, false);
			holder.factory = (TextView) convertView.findViewById(R.id.equipment_item_factory);
//			holder.mTextView1 = (TextView) convertView.findViewById(R.id.equipment_item_text1);
			holder.mMyTextView = (MyTextView) convertView.findViewById(R.id.equipment_item_mytextview);
			holder.mTextView2 = (TextView) convertView.findViewById(R.id.equipment_item_text2);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
			holder.mMyTextView.setTextColor(Color.parseColor(mArrayList.get(position).getColor()));
		
		holder.factory.setText(mArrayList.get(position).getEquipmentName()+" ("+mArrayList.get(position).getFactory()+")");	
//		holder.mTextView1.setText(mArrayList.get(position).getEquipmentName());
		holder.mMyTextView.setText(String.valueOf(mArrayList.get(position).getEquipmentValue()));
//		holder.mTextView1.setTextSize(20);
		holder.mMyTextView.setTextSize(20);
		holder.factory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle(mArrayList.get(position).getFactory()+"工厂");
				builder.setMessage(mArrayList.get(position).getEquipmentName()+"设备");
				builder.create().show(); 
			}
		});
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				proBar.setVisibility(View.VISIBLE);
				new Thread(new Runnable() {
//					
//					@Override
					public void run() {
//						// TODO Auto-generated method stub
						OutputStream out = null;
						BufferedReader reader = null;
						JSONObject json = null;
//						String sendStr = "sensorinfo="+"传感器信息"+"&time="+""+"&startTime="+""+"&endTime="+""+"&tel="+"123"+"&equipment_name="+mArrayList.get(position).getEquipmentName(); 
						String sendStr = "equipmentName="+mArrayList.get(position).getEquipmentName(); 
						System.out.println("设备的名称是：：："+sendStr);
						try {
							//http://193.112.62.125:80/water/app.req?action=detail
							URL url = new URL("http://193.112.62.125:80/HadoopWeb/userselect/detail.do");
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("POST");
							conn.setDoOutput(true);
							conn.setDoInput(true);
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							out = conn.getOutputStream();
							out.write(sendStr.getBytes());
							reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							String line;
							StringBuilder builder = new StringBuilder();
							while((line = reader.readLine())!=null){
								builder.append(line);
							}	
							System.out.println("com.sensorinfo.adapter.EquipmentAdapter:"+builder.toString());
							 json = new JSONObject(builder.toString());
							 String result = json.getString("result");
							 if(result.equals("10000")){

//					Intent intent = new Intent(mContext,EquipmentDetails.class);
					Intent intent = new Intent(mContext,EquActivity.class);
					intent.putExtra("factory", mArrayList.get(position).getFactory());
					intent.putExtra("name", mArrayList.get(position).getEquipmentName());
					intent.putExtra("json", builder.toString());
					MainActivity.sendjson = new JSONObject(builder.toString()).getJSONObject("equipment");
					mContext.startActivity(intent);
					proBar.setVisibility(View.GONE);
								
							 }else {
								 Toast.makeText(mContext, "请求失败，请重试！！！", Toast.LENGTH_SHORT).show();
							 }
//							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} finally{
							if(out != null)
								try {
									out.close();
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							if(reader != null)
								try {
									reader.close();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

						}
					}
				}).start();
				
				
			}
		});
		
		
		return convertView;
	}
	class Holder{
		TextView factory;
//		TextView mTextView1;
		MyTextView mMyTextView;
		TextView mTextView2;
	}
	
}
