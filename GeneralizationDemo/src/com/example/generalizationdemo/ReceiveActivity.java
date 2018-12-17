package com.example.generalizationdemo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ASimpleCache.org.afinal.simplecache.ACache;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.drm.DrmStore.RightsStatus;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ReceiveActivity extends Activity {

	private Bitmap mIconExpand;
	private Bitmap mIconCollapse;
	private LinearLayout l;
	private String note;
	private String taskID = "";
	JSONObject test;
	private String myurl = "http://123.206.16.157:8080/water/mission.req?action=MissionReturn";;
	private String result;
	private JSONObject test1;
	private int resultcode;
	private String msg;
	private String doneID;
	private ACache mCache;
	private Button btn1;
	//扫码按钮
	private Button btn2;
	private boolean done = true;
	private String time;
	private String time1;
	//事件ID数组
//	private String[] ID;
	private ArrayList<String> ID_Array = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.receive);
		// 访问缓存
		mCache = ACache.get(this);
		// 接收taskID
		Intent intent = getIntent();
		taskID = intent.getStringExtra("taskID");
		// doneID = intent.getStringExtra("doneID");
		// 获取时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// HH:mm:ss
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(
				"yyyyMMddHHmmss");// HH:mm:ss
		// 获取当前时间
		Date date = new Date(System.currentTimeMillis());
		time = simpleDateFormat.format(date);
		time1 = simpleDateFormat1.format(date);
		Log.v("format", "Date获取当前日期时间" + time);
		// 完成按钮
		btn1 = (Button) findViewById(R.id.btn_finish);
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// new Thread(new Runnable() {
				//
				// @Override
				// public void run() {
				if (done == true) {
					String resultString = uploadcondition(
							"http://123.206.16.157:8080/water/mission.req?action=condition",
							"5", taskID);
					Log.v("format", "更改订单状态返回："+resultString);
					String timeresult = uploadtime("http://123.206.16.157:8080/water/mission.req?action=missionTime");
					Log.v("format", "上传结束时间返回："+timeresult);
					Intent i = new Intent(ReceiveActivity.this,
							OrdersActivity.class);
					startActivity(i);
				} else {
					new AlertDialog.Builder(ReceiveActivity.this)
							.setTitle("请完成全部任务再提交！")
							.setIcon(android.R.drawable.ic_dialog_info)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// 点击“确认”后的操作

										}
									}).show();
				}

				// }
				// });

			}
		});
		//扫码按钮
		btn2 = (Button) findViewById(R.id.btn_qrcode);
		btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
//				intent.putExtra("ID", ID);
				intent.putExtra("ID_Array", ID_Array);
				intent.setClass(ReceiveActivity.this, CaptureActivity.class);
				startActivity(intent);
			}
		});
		// 事件列表布局
		l = (LinearLayout) findViewById(R.id.l1);
		// 主线程中强制执行
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		// 解析json
		try {
			Log.v("format1", taskID);
			result = uploadparam(myurl);
			test1 = new JSONObject(result);
			resultcode = test1.getInt("result");
			msg = test1.getString("essMsg");
			if (resultcode != 10000) {
				new AlertDialog.Builder(this)
						.setTitle("请求错误，请稍后重试")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 点击“确认”后的操作

									}
								}).show();
			} else {
				// 提示
				new AlertDialog.Builder(this)
						.setTitle("您已接单，请尽快执行任务")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 点击“确认”后的操作

									}
								}).show();
				// 事件数组
				test = new JSONObject(test1.getString("data"));
				JSONArray event_array = test.getJSONArray("event");
				// mCache.put(taskID+"1",event_array.length());
				mIconExpand = BitmapFactory.decodeResource(this.getResources(),
						R.drawable.jian);
				mIconCollapse = BitmapFactory.decodeResource(
						this.getResources(), R.drawable.plus);
				// 解析event数组
				for (int j = 0; j < event_array.length(); j++) {
					JSONObject event = event_array.getJSONObject(j);
					String event_ID = event.getString("event_ID");
//					ID[j] = event_ID;
					ID_Array.add(event_ID);
					if (mCache.getAsString(taskID + event_ID) == null) {
						done = false;
					} else if (!mCache.getAsString(taskID + event_ID).equals(
							"done")) {
						done = false;
					}
					String event_name = event.getString("event_name");
					// final ImageView icon =
					add_event(event_name, event.getString("font_color"),
							event.getInt("font_size"),
							event.getString("event_ID"));
					// 解析work数组
					// work字符串转数组
					// String work_string = event.getString("work");
					// work_string.replace("\\n", "");
					// work_string.replace("\\", "");
					// JSONArray work_array = new JSONArray(work_string);
					// StringBuilder sb = new StringBuilder("\u3000");
					// for (int i = 0; i < work_array.length(); i++) {
					// JSONObject work = work_array.getJSONObject(i);
					// String work_name = sb.append(
					// work.getString("work_name") + "\n\u3000")
					// .toString();
					// final TextView tv = add_work(work_name,
					// work.getString("font_color"),
					// work.getInt("font_size"));
					// // 点击按钮折叠展开work
					// icon.setOnClickListener(new OnClickListener() {
					// @Override
					// public void onClick(View arg0) {
					// TextView tv1 = tv;
					// if (tv.getVisibility() == 0) {
					// tv.setVisibility(View.GONE);
					// icon.setImageBitmap(mIconCollapse);
					// } else {
					// tv.setVisibility(View.VISIBLE);
					// icon.setImageBitmap(mIconExpand);
					// }
					// }
					// });
					// }
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// 添加一级事件
	private void add_event(final String title, String color, int size,
			final String event_ID) {
		// 添加水平布局
		LinearLayout l1 = new LinearLayout(this);
		l1.setOrientation(LinearLayout.HORIZONTAL);
		// 图标
		// final ImageView icon = new ImageView(this);
		// icon.setImageBitmap(mIconCollapse);
		// icon.setPadding(2, 2, 2, 5);
		// 事件名
		final TextView tv = new TextView(this);
		tv.setText(title);
		tv.setTextColor(Color.parseColor(color));
		tv.setTextSize(size);
		tv.setPadding(5, 2, 2, 5);
		// 执行按钮
		Button btn = new Button(this);
		btn.setText("执行");
		btn.setBackgroundResource(R.drawable.selector);
		// btn.setAlpha(1);
		btn.setGravity(Gravity.RIGHT);

		if (mCache.getAsString(taskID + event_ID) != null) {
			btn.setVisibility(View.GONE);
			tv.setTextColor(Color.parseColor("#CCCCCC"));
		}
		// 点击执行后传入event_ID到下一界面
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ReceiveActivity.this,
						ExecuteActivity.class);
				intent.putExtra("event_ID", event_ID);
				intent.putExtra("event_name", title);
				intent.putExtra("taskID", taskID);

				startActivity(intent);
			}
		});
		// 添加布局
		// l1.addView(icon);
		l1.addView(tv);
		l1.addView(btn);
		l.addView(l1);

		// return icon;
	}

	// 添加二级工作
	private TextView add_work(String work, String color, int size) {
		// 工作内容
		final TextView tv = new TextView(this);
		tv.setText(work);
		tv.setTextColor(Color.parseColor(color));
		tv.setTextSize(size);
		tv.setVisibility(View.GONE);
		l.addView(tv);
		return tv;

	}

	// 发送post请求
	private String uploadparam(String myurl) {
		String strUrl = myurl;
		URL url = null;
		String result = "";
		try {
			url = new URL(strUrl);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setDoInput(true);// 设置输入采用字节流
			urlConn.setDoOutput(true);// 设置输出采用字节流
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// urlConn.setRequestProperty("Charset", "utf-8");

			// urlConn.connect();//连接既往服务端发送消息

			DataOutputStream dos = new DataOutputStream(
					urlConn.getOutputStream());
			dos.writeBytes("MissionId=" + taskID);
			dos.flush();// 清空缓存
			dos.close();// 关闭
			// 接收部分
			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));

			String readline = null;
			while ((readline = bReader.readLine()) != null) {
				result += readline;
			}
			bReader.close();
			urlConn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}
	private String uploadtime(String myurl) {
		String strUrl = myurl;
		URL url = null;
		String result = "";
		try {
			url = new URL(strUrl);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setDoInput(true);// 设置输入采用字节流
			urlConn.setDoOutput(true);// 设置输出采用字节流
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// urlConn.setRequestProperty("Charset", "utf-8");

			// urlConn.connect();//连接既往服务端发送消息

			DataOutputStream dos = new DataOutputStream(
					urlConn.getOutputStream());
			dos.writeBytes("time=" + time+"&mission_id="+taskID+"&type=2");
			dos.flush();// 清空缓存
			dos.close();// 关闭
			// 接收部分
			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));

			String readline = null;
			while ((readline = bReader.readLine()) != null) {
				result += readline;
			}
			bReader.close();
			urlConn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}
	private String uploadcondition(String myurl, String condition,
			String mission_id) {
		String strUrl = myurl;
		URL url = null;
		String result = "";
		try {
			url = new URL(strUrl);
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();
			urlConn.setDoInput(true);// 设置输入采用字节流
			urlConn.setDoOutput(true);// 设置输出采用字节流
			urlConn.setRequestMethod("POST");
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			// urlConn.setRequestProperty("Charset", "utf-8");

			// urlConn.connect();//连接既往服务端发送消息

			DataOutputStream dos = new DataOutputStream(
					urlConn.getOutputStream());
			dos.writeBytes("mission_id=" + mission_id + "&condition="
					+ condition);
			// dos.writeBytes("condition="+condition);

			dos.flush();// 清空缓存
			dos.close();// 关闭
			// 接收部分
			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));

			String readline = null;
			while ((readline = bReader.readLine()) != null) {
				result += readline;
			}
			bReader.close();
			urlConn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent i = new Intent(ReceiveActivity.this, OrdersActivity.class);
			startActivity(i);

		}
		return super.onKeyDown(keyCode, event);
	}

}
