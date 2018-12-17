package com.example.generalizationdemo;

//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import ASimpleCache.org.afinal.simplecache.ACache;
import org.json.*;

import com.zijunlin.Zxing.Demo.CaptureActivity;


//import com.example.intfgenaralization.R.string;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder.DeathRecipient;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailActivity extends Activity {
	private Button btn1;
	private Button btn2;
	private Bitmap mIconExpand;
	private Bitmap mIconCollapse;
	private LinearLayout l;
	// ������Data���json
	JSONObject test;
	// post���յ�json
	private JSONObject test1;
	private String taskID;
	private String backMsg;
	// ���Ӹ���jsonȫ��
	private String myurl;
	private String result;
	// �����¼�����
	private ACache mCache;
	//��ȡʱ��
	private String time;
	private String time1;

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.orderdetail);
		l = (LinearLayout) findViewById(R.id.l1);
		l.setBackgroundColor(Color.parseColor("#EEE9E9"));
		// �����ϸ����洫������taskID
		Intent intent = getIntent();
		taskID = intent.getStringExtra("taskID");
		Log.v("taskID", taskID);
		// ��ȡʱ��
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");// HH:mm:ss
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(
				"yyyyMMddHHmmss");// HH:mm:ss
		// ��ȡ��ǰʱ��
		Date date = new Date(System.currentTimeMillis());
		time = simpleDateFormat.format(date);
		time1 = simpleDateFormat1.format(date);
		Log.v("format", "Date��ȡ��ǰ����ʱ��" + time);
		// ����json����
		try {
			// ǿ�����߳���ִ����������
			if (android.os.Build.VERSION.SDK_INT > 9) {
				StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
						.permitAll().build();
				StrictMode.setThreadPolicy(policy);
			}

			myurl = "http://123.206.16.157:8080/water/mission.req?action=MissionReturn";
//			result = uploadparam(myurl);
			result = uploadparam(myurl);

			test1 = new JSONObject(result);
			String resultcode = test1.getString("result");
			if (resultcode.equals("10001")) {
//				String msg = test1.getString("essMsg");
				

				new AlertDialog.Builder(DetailActivity.this)
				.setTitle("��ʾ")
				.setMessage("����ʧ�ܣ��뷵�غ�����")
				.setNegativeButton("ȡ��", null)
				.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								Intent i = new Intent(DetailActivity.this,OrdersActivity.class);
								startActivity(i);

							}
						}).show();
				
				
			} else {
				test = new JSONObject(test1.getString("data"));
//			}
			// ��ӱ�������
			addtext("����" + taskID, "#000000", 25, 0);
			// �������˵��
			// ���˵���ͱ����ķֽ���
			View v = new View(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					2000, 10);
			v.setBackgroundColor(Color.parseColor("#FCFCFC"));
			l.addView(v, params);
			JSONArray note_array = test.getJSONArray("note");
			for (int j = 0; j < note_array.length(); j++) {
				// �������ּ��
				View v1 = new View(this);
				LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
						2000, 15);
				v1.setBackgroundColor(Color.parseColor("#EEE9E9"));
				l.addView(v1, params1);
				JSONObject note = note_array.getJSONObject(j);
				String note_name = note.getString("note_name");
				String note_content = note.getString("note_content");
				addtext(note_name + ":" + note_content,
						note.getString("font_color"), note.getInt("font_size"),
						2);
			}
			// ���event��work
			JSONArray event_array = test.getJSONArray("event");
			mCache = ACache.get(this);
			// mCache.put(taskID+"1",event_array.length());
			// ���event��˵����ķֽ���
			View v1 = new View(this);
			LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(
					2000, 10);
			v1.setBackgroundColor(Color.parseColor("#FCFCFC"));
			l.addView(v1, params1);
			mIconExpand = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.jian);
			mIconCollapse = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.plus);
			for (int j = 0; j < event_array.length(); j++) {
				JSONObject event = event_array.getJSONObject(j);
				String event_ID = event.getString("event_ID");
				String event_name = event.getString("event_name");
				final ImageView icon = add_event(event_name,
						event.getString("font_color"),
						event.getInt("font_size"));
				Log.v("Paul Frank", event_ID + event.getString("font_color"));
				// ����work����
				// JSONArray work_array = event.getJSONArray("work");

				String work_string = event.getString("work");
				work_string.replace("\\n", "");
				work_string.replace("\\", "");

				Log.v("format1", work_string);
				JSONArray work_array = new JSONArray(work_string);
				StringBuilder sb = new StringBuilder("\u3000");
				for (int i = 0; i < work_array.length(); i++) {
					JSONObject work = work_array.getJSONObject(i);
					String work_name = sb.append(
							work.getString("work_name") + "\n\u3000")
							.toString();
					final TextView tv = add_work(work_name,
							work.getString("font_color"),
							work.getInt("font_size"));
					// �����ť�۵�չ��work
					icon.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {

							if (tv.getVisibility() == 0) {
								tv.setVisibility(View.GONE);
								icon.setImageBitmap(mIconCollapse);
							} else {
								tv.setVisibility(View.VISIBLE);
								icon.setImageBitmap(mIconExpand);
							}
						}
					});
				}
			}
		}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		// �ӵ����ܵ���ť
		btn1 = (Button) findViewById(R.id.btn_receive);
		btn2 = (Button) findViewById(R.id.btn_reject);
		btn2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(DetailActivity.this).setTitle("ȷ��Ҫ�ܵ���")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						
						new Thread(new Runnable() {

							@Override
							public void run() {
								try {
									String resultString = uploadcondition(
											"http://123.206.16.157:8080/water/mission.req?action=condition",
											"2", taskID);
									Log.v("format", "���Ķ���״̬�ܾ����أ�"+resultString);
									Intent i = new Intent(DetailActivity.this,
											OrdersActivity.class);
									startActivity(i);
									// ���뵭���л�
									overridePendingTransition(android.R.anim.fade_in,
											android.R.anim.fade_out);
								} catch (NumberFormatException e) {
									e.printStackTrace();
								}
							}
						}).start();				
			     }
				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
					}
				}).show();
			}
		});
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// new Thread(new Runnable() {
				//
				// @Override
				// public void run() {
				String resultString = uploadcondition(
						"http://123.206.16.157:8080/water/mission.req?action=condition",
						"3", taskID);
				Log.v("format", "���Ķ���״̬���շ��أ�"+resultString);
				String timeresult = uploadtime("http://123.206.16.157:8080/water/mission.req?action=missionTime");
				Log.v("format", "�ϴ���ʼʱ�䷵�أ�"+timeresult);
				// }
				// });

				Intent i = new Intent(DetailActivity.this,
						ReceiveActivity.class);
				i.putExtra("taskID", taskID);
				startActivity(i);
				// ���뵭���л�
				overridePendingTransition(android.R.anim.fade_in,
						android.R.anim.fade_out);
			}
		});

	}

	// ���һ���¼�
	private ImageView add_event(String title, String color, int size) {
		// ���ˮƽ����
		LinearLayout l1 = new LinearLayout(this);
		l1.setOrientation(LinearLayout.HORIZONTAL);
		// ͼ��
		ImageView icon = new ImageView(this);
		icon.setImageBitmap(mIconCollapse);
		icon.setPadding(2, 2, 2, 5);
		// �¼���
		final TextView tv = new TextView(this);
		tv.setText(title);
		tv.setTextColor(Color.parseColor(color));
		tv.setTextSize(size);
		l1.addView(icon);
		l1.addView(tv);
		l.addView(l1);

		return icon;
	}

	// ��Ӷ�������
	private TextView add_work(String work, String color, int size) {
		// ��������
		final TextView tv = new TextView(this);
		tv.setText(work);
		tv.setTextSize(size);
		tv.setTextColor(Color.parseColor(color));
		tv.setVisibility(View.GONE);
		l.addView(tv);
		return tv;

	}

	// ����ı� �ж��Ƿ� 0������ 1������
	private void addtext(String content, String color, int size, int position) {
		TextView tView = new TextView(this);
		tView.setText(content);
		tView.setTextColor(Color.parseColor(color));
		tView.setTextSize(size);
		int num = position;
		if (num == 0) {
			tView.setGravity(Gravity.CENTER);
		} else if (num == 1) {
			tView.setGravity(Gravity.RIGHT);
		} else {
			tView.setGravity(Gravity.LEFT);
		}
		// tView.setBackgroundResource(R.drawable.star);
		l.addView(tView);
	}

	private String uploadparam(String myurl) {
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
			dos.writeBytes("MissionId=" + taskID);
			dos.flush();// ��ջ���
			dos.close();// �ر�
			// ���ղ���
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
			dos.writeBytes("time=" + time+"&mission_id="+taskID+"&type=1");
			dos.flush();// ��ջ���
			dos.close();// �ر�
			// ���ղ���
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
			dos.writeBytes("mission_id=" + mission_id + "&condition="
					+ condition);
			// dos.writeBytes("condition="+condition);

			dos.flush();// ��ջ���
			dos.close();// �ر�
			// ���ղ���
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
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.sweep, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.sweep) {
	        	
	        	Intent intent = new Intent();
				intent.setClass(DetailActivity.this, CaptureActivity.class);
				startActivity(intent);						
				return true;
	        	
	        }
	        return super.onOptionsItemSelected(item);
	    }

}