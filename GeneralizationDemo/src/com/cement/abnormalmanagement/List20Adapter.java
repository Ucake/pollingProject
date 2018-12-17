package com.cement.abnormalmanagement;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.generalizationdemo.R;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class List20Adapter extends BaseAdapter {
	public static String time;
	public static String worker;
	public static String set_start1;
	public static String mission_id1;
	
	public static String mFileName20;
	public static Bitmap mBitmap_recent2;
	public static Bitmap mBitmap_recent3;
	public static String mission_condition1;
	private final static String TAG = "LIANSHUDENGAILVRUSHUIQUTOUKAN";
	public static ProgressDialog pd_check;
	
	Context context;
	ArrayList<HashMap<String, Object>> listData;	
	
	public List20Adapter(Context context,	ArrayList<HashMap<String, Object>> listData) {
		this.context = context;
		this.listData = listData;	
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater mInflater = LayoutInflater.from(context);
		convertView = mInflater.inflate(R.layout.abnormal_manage_list, null);
		ImageView image = (ImageView) convertView.findViewById(R.id.img);
		image.setBackgroundResource((Integer) listData.get(position).get("friend_image"));
		TextView time = (TextView) convertView.findViewById(R.id.time);
		time.setText((String) listData.get(position).get("time")+"\n״̬:"+(String) listData.get(position).get("abnormal_statuss"));
		TextView event = (TextView) convertView.findViewById(R.id.event);
		event.setText((String) listData.get(position).get("person"));
		TextView worker = (TextView) convertView.findViewById(R.id.worker);
//		worker.setText("״̬:"+(String) listData.get(position).get("abnormal_statuss"));
		
		
		Button view = (Button) convertView.findViewById(R.id.view_btn);
		
//		convertView.setBackgroundColor(Color.parseColor((String) listData.get(position).get("color")));
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				
				Intent intent = new Intent();
				intent.putExtra("abnormal_id", (String) listData.get(position).get("id"));
				intent.setClass(context, AbnormalDetailActivity.class);
				context.startActivity(intent);
				    
			}
		});
		
		return convertView;
	}

	
}
