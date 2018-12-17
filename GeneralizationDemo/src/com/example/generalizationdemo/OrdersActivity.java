package com.example.generalizationdemo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
//import com.example.intfgenaralization.R.string;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrdersActivity extends ListActivity {

	private SharedPreferences preferences;
	private List<Map<String, Object>> mData;
	private JSONObject test;
	public static String backMsg;
	private String myurl = "http://123.206.16.157:8080/water/mission.req?action=seekwjs";;
	private String result;
	double lat;
	double lon;
	public LocationClient mLocationClient = null;
	private MyLocationListener myListener = new MyLocationListener();
	private String resultcode;
	private String msg;
	private String status;
	
	
	public static String TEL;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mData = getData();
		MyAdapter adapter = new MyAdapter(this);
		setListAdapter(adapter);
	}

//	@SuppressLint("HandlerLeak")
//	final Handler handler = new Handler() {
//		public void handleMessage(Message msg) {
//			switch (msg.what) {
//			case 1:
////				 Log.v("format", lat+" "+lon);	
//				 
//				 
//				 
//				 
//			}
//			super.handleMessage(msg);
//		}
//
//	};
//	Timer timer = new Timer();
//	TimerTask task = new TimerTask() {
//		public void run() {
//			Message message = new Message();
//			message.what = 1;
//			handler.sendMessage(message);
//		}
//	};

	private List<Map<String, Object>> getData() {
		final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		 //ǿ�����߳���ִ����������
		 if (android.os.Build.VERSION.SDK_INT > 9) {
		 StrictMode.ThreadPolicy policy = new
		 StrictMode.ThreadPolicy.Builder().permitAll().build();
		 StrictMode.setThreadPolicy(policy);
		 }

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
				try {
					preferences = getSharedPreferences("user", MODE_PRIVATE);
					status = preferences.getString("status", null);
					
					result = uploadparam(myurl);
					Log.v("format", result);
					test = new JSONObject(result);
					resultcode = test.getString("result");
//					msg = test.getString("essMsg");
					Log.v("format", resultcode);
					if(resultcode.equals("10002")){
						new AlertDialog.Builder(OrdersActivity.this).setTitle("��ǰ��û������")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent();
								if(status.equals("worker")){
									intent.setClass(OrdersActivity.this,SecondActivity.class);
								}else{
									intent.setClass(OrdersActivity.this,HomePageya.class);
								}
								
								startActivity(intent);
							}
						}).show();
						
					}else if(!resultcode.equals("10000")) {
						new AlertDialog.Builder(OrdersActivity.this).setTitle("����������Ժ�����")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent();
								if(status.equals("worker")){
									intent.setClass(OrdersActivity.this,SecondActivity.class);
								}else{
									intent.setClass(OrdersActivity.this,HomePageya.class);
								}
								startActivity(intent);
							}
						}).show();
					}else {
					Log.v("format2", result);
					JSONArray orders_array = test.getJSONArray("orders");
					Log.v("format", String.valueOf(orders_array.length()));
//					if(orders_array.length()==0){
//						new AlertDialog.Builder(OrdersActivity.this).setTitle("��û��δ��ɵĶ�����Ϣ")
//						.setIcon(android.R.drawable.ic_dialog_info)
//						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
//
//							@Override
//							public void onClick(DialogInterface dialog, int which) {
//								// �����ȷ�ϡ���Ĳ���
//
//							}
//						}).show();
//					}else {
						for (int i = 0; i < orders_array.length(); i++) {
						JSONObject order = orders_array.getJSONObject(i);
						String taskID = order.getString("taskID");
						String describe = order.getString("describe");
						String status = order.getString("status");
						if (status.equals("����")) {
							status = "����δִ��";
						}
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("img", R.drawable.tasks);
						map.put("title", "����" + taskID + "(" + status + ")");
						map.put("describe", describe);
						list.add(map);
					}
//					}
					
					}
					

//				    } 
					}catch (JSONException e) {
					e.printStackTrace();
				}
//			}
//		}).start();

		return list;

	}

	// ListView ��ĳ�ѡ�к���߼�
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		if (mData.get(position).get("title").toString().contains("δ����")) {
			Log.v("taskID", (String) mData.get(position)
					.get("title").toString()
					.substring(2).replace("(δ����)", ""));

			Intent i = new Intent(OrdersActivity.this, DetailActivity.class);
			i.putExtra("taskID", mData.get(position).get("title").toString()
					.substring(2).replace("(δ����)", ""));
			startActivity(i);

		} else {
			Log.v("taskID", (String) mData.get(position)
					.get("title").toString()
					.substring(2).replace("(����δִ��)", ""));
			Intent i = new Intent(OrdersActivity.this, ReceiveActivity.class);
			i.putExtra("taskID", mData.get(position).get("title").toString()
					.substring(2).replace("(����δִ��)", ""));
			startActivity(i);
		}

	}

	public final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView describe;

	}

	public class MyAdapter extends BaseAdapter {

		private LayoutInflater mInflater;

		public MyAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		public MyAdapter(Handler handler) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {

			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {

			return null;
		}

		@Override
		public long getItemId(int arg0) {

			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if (convertView == null) {

				holder = new ViewHolder();

				convertView = mInflater.inflate(R.layout.vlist2, null);
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.describe = (TextView) convertView
						.findViewById(R.id.describe);
				convertView.setTag(holder);

			} else {

				holder = (ViewHolder) convertView.getTag();
			}

			holder.img.setBackgroundResource((Integer) mData.get(position).get(
					"img"));
			holder.title.setText((String) mData.get(position).get("title"));
			holder.describe.setText((String) mData.get(position)
					.get("describe"));
			if (holder.title.getText().toString().contains("δ����")) {
				Log.v("color", "1");
				convertView.setBackgroundColor(Color.parseColor("#FFA500"));
			}
			if (holder.title.getText().toString().contains("����δִ��")) {
				convertView.setBackgroundColor(Color.parseColor("#AEEEEE"));
			}
			if (holder.title.getText().toString().contains("ִ����")) {
				convertView.setBackgroundColor(Color.parseColor("#CCFFCC"));
			}

			return convertView;

		}

	}

	private String uploadparam(String myurl) {
		String strUrl = myurl;
		URL url = null;
		String result = "";
		try {
			
			preferences = getSharedPreferences("user", MODE_PRIVATE);
	        TEL = preferences.getString("tel", null);
	        
	        
			url = new URL(strUrl);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setDoInput(true);// ������������ֽ���
			urlConn.setDoOutput(true);// ������������ֽ���
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// urlConn.setRequestProperty("Charset", "utf-8");

			// urlConn.connect();//���Ӽ�������˷�����Ϣ

			DataOutputStream dos = new DataOutputStream(
					urlConn.getOutputStream());
//			dos.writeBytes("phone=18810981379");
			dos.writeBytes("phone="+TEL);
			dos.flush();// ��ջ���
			dos.close();// �ر�
			// ���ղ���
			int res = urlConn.getResponseCode();
			Log.v("format", Integer.toString(res));
			if (res==200) {
				BufferedReader bReader = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));

			String readline = null;
			while ((readline = bReader.readLine()) != null) {
				result += readline;
			}
			bReader.close();
			}
			else {
				new AlertDialog.Builder(OrdersActivity.this).setTitle("����������Ժ�����")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// �����ȷ�ϡ���Ĳ���

					}
				}).show();
			}
			
			urlConn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}
	private String uploadlocation(String myurl,String phone,Double lat,Double lon,String time) {
		String strUrl = myurl;
		URL url = null;
		String result = "";
		try {
			url = new URL(strUrl);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setDoInput(true);// ������������ֽ���
			urlConn.setDoOutput(true);// ������������ֽ���
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// urlConn.setRequestProperty("Charset", "utf-8");

			// urlConn.connect();//���Ӽ�������˷�����Ϣ

			DataOutputStream dos = new DataOutputStream(
					urlConn.getOutputStream());
			dos.writeBytes("phone="+phone);
			dos.writeBytes("latitude="+lat);
			dos.writeBytes("longitude="+lon);
			dos.writeBytes("time="+time);			
			dos.flush();// ��ջ���
			dos.close();// �ر�
			// ���ղ���
			
			
			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			Log.v("format", "���ͳɹ�");
			String readline = null;
			while ((readline = bReader.readLine()) != null) {
				result += readline;
			
			bReader.close();
			
			
			urlConn.disconnect();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	public class MyLocationListener implements BDLocationListener{
	    public void onReceiveLocation(BDLocation location){
	        //�˴���BDLocationΪ��λ�����Ϣ�࣬ͨ�����ĸ���get�����ɻ�ȡ��λ��ص�ȫ�����
	        //����ֻ�оٲ��ֻ�ȡ��γ����أ����ã��Ľ����Ϣ
	        //��������Ϣ��ȡ˵�����������ο���BDLocation���е�˵��
				
	        lat = location.getLatitude();    //��ȡγ����Ϣ
	        lon = location.getLongitude();    //��ȡ������Ϣ
	        float radius = location.getRadius();    //��ȡ��λ���ȣ�Ĭ��ֵΪ0.0f
				
	        String coorType = location.getCoorType();
	        //��ȡ��γ���������ͣ���LocationClientOption�����ù�����������Ϊ׼
				
	        int errorCode = location.getLocType();
	        //��ȡ��λ���͡���λ���󷵻��룬������Ϣ�ɲ�����ο���BDLocation���е�˵��
	        
	    }
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent i = new Intent();
			if(status.equals("worker")){
				i.setClass(OrdersActivity.this,SecondActivity.class);
			}else{
				i.setClass(OrdersActivity.this,HomePageya.class);
			}
	        startActivity(i);
			
			
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
