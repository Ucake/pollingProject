package com.example.generalizationdemo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import ASimpleCache.org.afinal.simplecache.ACache;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.example.generalizationdemo.OrdersActivity.MyLocationListener;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
//import com.example.intfgenaralization.R;
//import com.example.intfgenaralization.HttpMultipartPost;
import com.google.gson.JsonObject;
import com.zijunlin.Zxing.Demo.CaptureActivity;

public class ExecuteActivity extends Activity implements OnClickListener {
	Context mContext;
	private LinearLayout l;
	// 异常组件
	private ImageButton abnormalButton1;
	private ImageButton abnormalButton2;
	private ImageButton abnormalButton3;
	private String abnormalphotoString;
	private String worker;
	private SharedPreferences preferences;
	private String TEL;
	private String workshop;
	private String check_point;
	// 拍照组件
	public static File cameraFile;
//	
	public static final String[] addPhoto = new String[] { "现在拍摄", 
			"取消" };
	Bitmap bitmap = null;
//	ImageView[] btn_capture = new ImageView[100];
	ArrayList<ImageView> list = new ArrayList<ImageView>();
	String photoname;
	// 输入文字组件
	private String number_left;
	private int maxNum = 140;
	JSONObject test;
	// 添加文字组件
	private String event_ID;
	private String event_name;
	private String taskID;
	int count = 0;
	private String factory_id;
	private String workshop_id;

	// post上传组件
	JSONObject test1;
	private static final String TAG = "uploadFile";
	private static final int TIME_OUT = 10 * 1000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	public static ProgressBar bar;// 上传进度条
	static String path;
	// 经纬度
	private double lat;
	private double lon;
	private MyLocationListener myListener = new MyLocationListener();
	public LocationClient mLocationClient = null;
	// 底部长条
	private View vv;
	// 返回提交的json
	private JSONObject eventJsonObject;
	private JSONArray workcontent;
	// anran写的
	private RelativeLayout btn3;// “更多”按钮布局
	private RelativeLayout btn2;
	private RelativeLayout btn1;
	private LinearLayout childLayout3;// “更多”按钮的子菜单
	private boolean flag3 = false;// 子菜单显示状态标记
	private boolean open3 = true;// 子菜单填充状态标记
	private LayoutInflater inflater3;
	private View view3;
	private LinearLayout popLayout3;// 弹出的子菜单父框架布局
	private Button help2;
	private Button help1;
	private Button help3;
	private LinearLayout childLayout1;
	private boolean flag1 = false;
	private boolean open1 = true;
	private View view1;
	private LinearLayout popLayout1;
	private Button question1;
	private Button question2;
	private Button question3;
	private String myurl = "http://123.206.16.157:8080/water/mission.req?action=MissionReturn";;
	private String result;
	Timer timer = new Timer();
	private int resultcode;
	private String msg;
	private ACache mCache;
	private String time;
	private String time1;//异常图片命名专用

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//20180927
//		getWindow().setSoftInputMode
//		(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN|
//		WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		setContentView(R.layout.bottom_layout);
		// 进度条组件
//		bar = (ProgressBar) findViewById(R.id.ProgressBar01);
		// 返回给后台的json
		eventJsonObject = new JSONObject();
		workcontent = new JSONArray();
		preferences = getSharedPreferences("user", MODE_PRIVATE);
        TEL = preferences.getString("tel", null);
        factory_id = preferences.getString("factory_id", null);
        workshop_id = preferences.getString("workshop_id", null);
        
		// 获取时间
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd_HH:mm:ss");// HH:mm:ss
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(
				"yyyyMMddHHmmss");// HH:mm:ss
		// 获取当前时间
		Date date = new Date(System.currentTimeMillis());
		time = simpleDateFormat.format(date);
		time1 = simpleDateFormat1.format(date);
		Log.v("format", "Date获取当前日期时间" + time);
		// 缓存
		mCache = ACache.get(this);
		// 获取经纬度
		mLocationClient = new LocationClient(getApplicationContext());
		// 声明LocationClient类
		mLocationClient.registerLocationListener(myListener);
		// 注册监听函数
		LocationClientOption option = new LocationClientOption();

		option.setLocationMode(LocationMode.Hight_Accuracy);
		// 可选，设置定位模式，默认高精度
		// LocationMode.Hight_Accuracy：高精度；
		// LocationMode. Battery_Saving：低功耗；
		// LocationMode. Device_Sensors：仅使用设备；

		option.setCoorType("bd09ll");
		// 可选，设置返回经纬度坐标类型，默认gcj02
		// gcj02：国测局坐标；
		// bd09ll：百度经纬度坐标；
		// bd09：百度墨卡托坐标；
		// 海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

		option.setScanSpan(1000);
		// 可选，设置发起定位请求的间隔，int类型，单位ms
		// 如果设置为0，则代表单次定位，即仅定位一次，默认为0
		// 如果设置非0，需设置1000ms以上才有效

		option.setOpenGps(true);
		// 可选，设置是否使用gps，默认false
		// 使用高精度和仅用设备两种定位模式的，参数必须设置为true

		option.setLocationNotify(true);
		// 可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

		option.setIgnoreKillProcess(false);
		// 可选，定位SDK内部是一个service，并放到了独立进程。
		// 设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

		option.SetIgnoreCacheException(false);
		// 可选，设置是否收集Crash信息，默认收集，即参数为false

		// option.setWifiCacheTimeOut(5*60*1000);
		// 可选，7.2版本新增能力
		// 如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

		option.setEnableSimulateGps(false);
		// 可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

		mLocationClient.setLocOption(option);
		// mLocationClient为第二步初始化过的LocationClient对象
		// 需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
		// 更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
		mLocationClient.start();
		// 底部长条
		vv = findViewById(R.id.vv);
		vv.setBackgroundColor(Color.parseColor("#EEE9E9"));
		// anran
		inflater3 = ExecuteActivity.this.getLayoutInflater();

		popLayout3 = (LinearLayout) findViewById(R.id.pop_layout3);
		popLayout1 = (LinearLayout) findViewById(R.id.pop_layout1);

		btn3 = (RelativeLayout) findViewById(R.id.btn3);
		btn2 = (RelativeLayout) findViewById(R.id.btn2);
		btn1 = (RelativeLayout) findViewById(R.id.btn1);
		// text2 = (TextView) findViewById(R.id.text2);

		btn3.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn1.setOnClickListener(this);
		// text2.setOnClickListener(this);

		view1 = inflater3.inflate(R.layout.child_menu1, popLayout1, true);
		childLayout1 = (LinearLayout) view1.findViewById(R.id.child_layout1);
		childLayout1.setVisibility(View.GONE);
		view3 = inflater3.inflate(R.layout.child_menu3, popLayout3, true);
		childLayout3 = (LinearLayout) view3.findViewById(R.id.child_layout3);
		childLayout3.setVisibility(View.GONE);

		// 返回按钮
		Button btn_finish = (Button) findViewById(R.id.btn_finish);
		btn_finish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				new AlertDialog.Builder(ExecuteActivity.this)
						.setTitle("请确认您是否完成全部工作")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 确认后的操作，关闭位置上传
										stopTimer();
										// 上传json
										new Thread(new Runnable() {

											@Override
											public void run() {
												try {

													eventJsonObject.put(
															"event_ID",
															event_ID);
													eventJsonObject.put(
															"weidu",
															lat);
													eventJsonObject.put(
															"jingdu",
															lon);
													eventJsonObject.put(
															"event_name",
															event_name);
													eventJsonObject.put(
															"workcontent",
															workcontent);
													Log.v("format",
															eventJsonObject
																	.toString());

//													String result = uploadparam(
//															"http://123.206.16.157:8080/water/mission.req?action=missionJson",
//															"mission_id="
//																	+ taskID
//																	+ "&event_id="
//																	+ event_ID
//																	+ "&big_json="
//																	+ eventJsonObject);
													//经纬度移植到big_json外
													
													String result = uploadparam(
															"http://123.206.16.157:8080/water/mission.req?action=missionJson",
															"mission_id="
																	+ taskID
																	+ "&event_id="
																	+ event_ID
																	+ "&big_json="
																	+ eventJsonObject
																	+ "&jingdu="+lon
																	+ "&weidu="+lat);
													// JsonObject
													// resultJsonObject = new
													// JsonObject(result);
													Log.v("format", "上传大json返回"
															+ result);
//													if(mCache.getAsString(taskID+"count")==null){
//														mCache.put(taskID+"count",1);
//													}else if (mCache.getAsString(taskID+"count")==mCache.getAsString(taskID+"1")) {
//														//改变状态
//														mCache.put(taskID+"count",0);
//														
//													}else {
//														int count = Integer.parseInt(mCache.getAsString(taskID+"count"));
//														mCache.put(taskID+"count",count+1);
//													}
//													Log.v("format","执行第"+mCache.getAsString(taskID+"count")+"共"+mCache.getAsString(taskID+"1"));

												} catch (JSONException e) {
													// TODO Auto-generated catch
													// block
													e.printStackTrace();
												} catch (NumberFormatException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										}).start();
										Intent intent = new Intent(
												ExecuteActivity.this,
												ReceiveActivity.class);
										intent.putExtra("taskID", taskID);
										mCache.put(taskID+event_ID,"done");
										Log.v("format", taskID);
										startActivity(intent);

									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										// 点击取消后的操作

									}
								}).show();

			}
		});

		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				 Log.v("format", "timer excute");
				 new Thread(new Runnable() {
				 @Override
				 public void run() {
				 try {
				 String reString =
				 uploadlocation("http://123.206.16.157:8080/water/mission.req?action=location",
				 TEL, lat, lon, time);
				 Log.v("format", reString);
				 } catch (Exception e) {
				
				 }
				
				 }
				 }).start();
			}
		}, 1000, 1000 * 10);

		// 接单页面传进来的数据
		Intent intent = getIntent();
		taskID = intent.getStringExtra("taskID");
		event_ID = intent.getStringExtra("event_ID");
		event_name = intent.getStringExtra("event_name");

		l = (LinearLayout) findViewById(R.id.l);
		l.setBackgroundColor(Color.parseColor("#EEE9E9"));
		try {

			result = uploadparam(myurl);
			test1 = new JSONObject(result);
			test = new JSONObject(test1.getString("data"));
			resultcode = test1.getInt("result");
			msg = test1.getString("essMsg");
			if (result == null) {
				new AlertDialog.Builder(this)
						.setTitle("请检查您的网络，稍后重试")
						.setIcon(android.R.drawable.ic_dialog_info)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// 点击确定后的操作

									}
								}).show();
			} else {
				if (resultcode != 10000) {

				} else {
					new AlertDialog.Builder(this)
							.setTitle("您已进入执行状态，请完成后再返回")
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
					// 添加事件号（任务号）
					addtext(event_ID + event_name + "(" + "任务" + taskID + ")",
							test.getString("font_color"),
							test.getInt("font_size"), 0);

					// 添加任务说明
					JSONArray note_array = test.getJSONArray("note");
					for (int j = 0; j < note_array.length(); j++) {
						JSONObject note = note_array.getJSONObject(j);
						String note_name = note.getString("note_name");
						String note_content = note.getString("note_content");
						addtext(note_name + ":" + note_content,
								note.getString("font_color"),
								note.getInt("font_size"), 2);
						if (note_name.equals("检修人员")) {
							worker = note_content;
						}
					}

					// 添加布局
					JSONArray event_array = test.getJSONArray("event");
					for (int i = 0; i < event_array.length(); i++) {
						JSONObject event = event_array.getJSONObject(i);
						if (event.getString("event_ID").toString()
								.equals(event_ID)) {
							workshop = event.getString("workshop");
							check_point = event.getString("check_point");
							// work字符串转数组
//							String work_string = event.getString("work");
//							work_string.replace("\\n", "");
//							work_string.replace("\\", "");
							JSONArray work_array = event.getJSONArray("work");
							for (int j = 0; j < work_array.length(); j++) {
								// 添加work间的分界线

								View v = new View(this);
								LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
										2000, 15);
								v.setBackgroundColor(Color
										.parseColor("#FCFCFC"));
//								v.setBackgroundColor(Color
//										.parseColor("#6699FF"));
								
								l.addView(v, params);
								JSONObject work = work_array.getJSONObject(j);
								// final String work_ID =
								// work.getString("work_ID");
								final String work_name = work
										.getString("work_name");
								// 添加返回json的workID和workName,view
								JSONObject workcontent1 = new JSONObject();
								// workcontent1.put("work_ID", work_ID);
								workcontent1.put("work_name", work_name);
								// workcontent1.put("work_name", "我是中文");
								workcontent.put(j, workcontent1);
								JSONArray viewJsonArray = new JSONArray();
								workcontent1.put("view_content", viewJsonArray);

								// addtext(work_ID + work_name,
								// work.getString("font_color"),
								// work.getInt("font_size"), 0);
								addtext(work_name,
										work.getString("font_color"),
										work.getInt("font_size"), 0);
								JSONArray work_note_array = work
										.getJSONArray("work_note");
								for (int k = 0; k < work_note_array.length(); k++) {

									JSONObject work_note = work_note_array
											.getJSONObject(k);
									String note_name = work_note
											.getString("note_name");
									String note_content = work_note
											.getString("note_content");
									addtext(note_name + ":" + note_content,
											work_note.getString("font_color"),
											work_note.getInt("font_size"), 2);

								}
								JSONArray view_array = work
										.getJSONArray("view");
								for (int k = 0; k < view_array.length(); k++) {
									JSONObject view = view_array
											.getJSONObject(k);
									String view_name = view
											.getString("view_name");
									String view_class = view
											.getString("view_class");
									// 返回json中的view数组

									JSONObject vieww = new JSONObject();
									vieww.put("view_name", view_name);
									vieww.put("view_class", view_class);
									viewJsonArray.put(k, vieww);

									addtext(view_name,
											view.getString("font_color"),
											view.getInt("font_size"), 2);
									if (view_class.equals("拍照")) {
										// Log.v("format", taskID + event_ID +
										// work_ID);
										Log.v("format", taskID + event_ID
												+ work_name);
										// btn_capture[count] =
										// addview_capture(taskID
										// + event_ID + work_ID,j,k);
//										btn_capture[count] = addview_capture(
//												taskID + event_ID + work_name,
//												j, k);
										list.add(addview_capture(
												taskID + event_ID + work_name,
												j, k));
										list.get(count)
												.setOnClickListener(new OnClickListener() {
													@Override
													public void onClick(View v) {

														count = (Integer) v
																.getTag(R.id.tag_first);
														// photoname = work_ID;
														photoname = work_name;
														new AlertDialog.Builder(
																ExecuteActivity.this)
																.setTitle(
																		"添加图片")
																.setItems(
																		addPhoto,

																		new DialogInterface.OnClickListener() {
																			@Override
																			public void onClick(
																					DialogInterface dialog,
																					int which) {

																				if (which == 0) {

																					Intent cameraIntent = new Intent(
																							android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
																					int CAMERA_REQUEST = 2;
																					startActivityForResult(
																							cameraIntent,
																							CAMERA_REQUEST);
																					boolean isFocus = list.get(count).isFocused();
																					Log.e("这是焦点吗？", ""+isFocus);

																				}
																				if (which == 1) {
																					Intent intent = new Intent();
																					intent.setType("image/*");// 可选择图片视频
																					// 修改为以下两句代码
																					intent.setAction(Intent.ACTION_PICK);
																					intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
																					startActivityForResult(
																							intent,
																							1);
																				}
																			}
																		})
																.show();
														
													}
												});
										
										count = count + 1;
										
									} else if (view_class.equals("输入框")) {
										// addview_et2(work_ID,j,k);
										addview_et2(work_name, j, k);
										
									} else if (view_class.equals("多选框")) {
										JSONArray cb_content = view
												.getJSONArray("view_content");
										// addview_cb(cb_content, work_ID,j,k);
										addview_cb(cb_content, work_name, j, k);
										

									} else if (view_class.equals("单选框")) {
										JSONArray rb_content = view
												.getJSONArray("view_content");
										// addview_rb(rb_content, work_ID,j,k);
										addview_rb(rb_content, work_name, j, k);
										

									}
								}
							}
						}
					}
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	// 停止定时器
	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			// 一定设置为null，否则定时器不会被回收
			timer = null;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

			stopTimer();
			
		}
		return super.onKeyDown(keyCode, event);
	}

	// 添加单选框
	private void addview_rb(final JSONArray string, final String ID,
			final int j, final int k) throws JSONException {
		final RadioGroup rGroup = new RadioGroup(this);
		l.addView(rGroup);
		final RadioButton[] rb = new RadioButton[100];
		for (int i = 0; i < string.length(); i++) {
			rb[i] = new RadioButton(this);
			rb[i].setText(string.getString(i));
			rb[i].setTextColor(Color.parseColor("#080808"));
			Log.v("danxuankuang", string.getString(i));
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					100, 100);
			params.setMargins(8, 0, 0, 0);
			rGroup.addView(rb[i]);
		}
		OnCheckedChangeListener listener = new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
			}
		};
		rGroup.setOnCheckedChangeListener(listener);
		Button button = new Button(this);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params2.gravity = Gravity.RIGHT;
		button.setLayoutParams(params2);
		button.setText("保存");
		button.setBackgroundResource(R.drawable.selector);
		l.addView(button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String result = "";
				for (int j = 0; j < string.length(); j++) {
					if (rb[j].isChecked()) {
						result = rb[j].getText().toString();
					}
				}
				if (result == " ") {
					new AlertDialog.Builder(ExecuteActivity.this)
							.setTitle("提示")
							.setMessage("请您选择一个选项！")
							.setNegativeButton("取消", null)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									}).show();
				} else {

					// new AlertDialog.Builder(ExecuteActivity.this)
					// .setTitle("提示")
					// .setMessage("确认选择" + result + "吗？")
					// .setNegativeButton("取消", null)
					// .setPositiveButton("确定",
					// new DialogInterface.OnClickListener() {
					// @Override
					// public void onClick(
					// DialogInterface dialog,
					// int which) {
					//
					//
					// }
					// }).show();
					String resultdata = uploadparam(
							"http://123.206.16.157:8080/water/mission.req?action=mission_feedback",
							"MissionId=" + taskID + "&EventId=" + event_ID
									+ "&WorkId=" + ID + "&type=1" + "&Data="
									+ result + "&Time=" + time);
					Log.v("format", "单选框返回数据" + resultdata);
					try {
						JSONObject resultJsonObject = new JSONObject(resultdata);
						String resultcodeString = resultJsonObject
								.getString("result");
						if (resultcodeString.equals("10000")) {
							Toast.makeText(getApplicationContext(), "提交成功",
									Toast.LENGTH_LONG).show();
							JSONObject work = workcontent.getJSONObject(j);
							JSONArray view = work.getJSONArray("view_content");
							JSONObject vieww = view.getJSONObject(k);
							vieww.put("view_data", result);
						} else {
							Toast.makeText(ExecuteActivity.this, "提交失败",
									Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		});

	}

	// 添加多选框
	private void addview_cb(final JSONArray string, final String ID,
			final int j, final int k) throws JSONException {
		final CheckBox[] cbBoxs = new CheckBox[100];
		for (int i = 0; i < string.length(); i++) {
			cbBoxs[i] = new CheckBox(this);
			cbBoxs[i].setText(string.getString(i));
			cbBoxs[i].setTextColor(Color.parseColor("#080808"));
			;
			Log.v("duoxuankuang", string.getString(i));
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					100, 100);
			params.setMargins(8, 0, 0, 0);
			l.addView(cbBoxs[i], params);
		}
		Button button = new Button(this);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params2.gravity = Gravity.RIGHT;
		button.setLayoutParams(params2);
		button.setText("提交");
		button.setBackgroundResource(R.drawable.selector);
		l.addView(button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String result = " ";
				for (int j = 0; j < string.length(); j++) {
					if (cbBoxs[j].isChecked()) {
						result = result + " " + cbBoxs[j].getText().toString();
					}
				}
				if (result == " ") {
					new AlertDialog.Builder(ExecuteActivity.this)
							.setTitle("提示")
							.setMessage("请您选择至少一个选项！")
							.setNegativeButton("取消", null)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									}).show();
				} else {

					new AlertDialog.Builder(ExecuteActivity.this)
							.setTitle("提示")
							.setMessage("确认选择" + result + "吗？")
							.setNegativeButton("取消", null)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									}).show();
					// String resultdata =
					// uploadparam("http://59.110.157.159:8080/water/mission.req?action=mission_feedback",
					// "MissionId="+taskID+
					// "&EventId="+event_ID+"&WorkId="+ID+"&type=1"+"&Data="+result+"&Time="+time);
					// Log.v("format", "多选框返回参数"+resultdata);
					try {
						// JSONObject resultJsonObject = new
						// JSONObject(resultdata);
						// String resultcodeString =
						// resultJsonObject.getString("result");
						// if (resultcodeString.equals("10000")) {

						JSONObject work = workcontent.getJSONObject(j);
						JSONArray view = work.getJSONArray("view_content");
						JSONObject vieww = view.getJSONObject(k);
						vieww.put("view_data", result);
						Toast.makeText(ExecuteActivity.this, "保存成功",
								Toast.LENGTH_LONG).show();
						// }else {
						// Toast.makeText(ExecuteActivity.this, "保存失败",
						// Toast.LENGTH_LONG).show();
						// }
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
	}

	// 添加文本
	private void addtext(String content, String color, int size, int position) {
		TextView tView = new TextView(this);
		tView.setText(content);
		tView.setTextColor(Color.parseColor(color));
		tView.setTextSize(size);
		l.addView(tView);
		int num = position;
		if (num == 0) {
			tView.setGravity(Gravity.CENTER);
		} else if (num == 1) {
			tView.setGravity(Gravity.RIGHT);
		} else {
			tView.setGravity(Gravity.LEFT);
		}
	}

	// 添加文字输入框布局
	private void addview_et2(final String ID, final int j, final int k) {

		// final TextView tv_numleft = new TextView(this);
		final EditText et = new EditText(this);
//		et.setInputType(InputType.TYPE_CLASS_NUMBER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				200,
				LinearLayout.LayoutParams.WRAP_CONTENT
				);
		final Button button = new Button(this);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params2.gravity = Gravity.RIGHT;
		button.setLayoutParams(params2);
		button.setText("保存");
		button.setBackgroundResource(R.drawable.selector);
		
		l.addView(et, params);
		
		l.addView(button);
		et.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				// number_left = "剩余字数：" + (maxNum - s.length());
				// tv_numleft.setText(number_left);
				// Log.v("YBY", number_left);
				// if ((maxNum - s.length() < 0)) {
				// tv_numleft.setTextColor(ExecuteActivity.this.getResources()
				// .getColor(R.color.red));
				// } else {
				// tv_numleft.setTextColor(ExecuteActivity.this.getResources()
				// .getColor(R.color.black));
				// }
			}
		});

		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (et.getText().toString() == null) {
					new AlertDialog.Builder(ExecuteActivity.this)
							.setTitle("提示")
							.setMessage(
									"请填写数据再保存！" + et.getText().toString()
											+ "吗？")
							.setNegativeButton("取消", null)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {

										}
									}).show();
				} else {
					new AlertDialog.Builder(ExecuteActivity.this)
							.setTitle("提示")
							.setMessage("确认填写" + et.getText().toString() + "吗？")
							.setNegativeButton("取消", null)
							.setPositiveButton("确定",
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											 String resultdata
											 =uploadparam("http://123.206.16.157:8080/water/mission.req?action=mission_feedback",
											 "MissionId="+taskID+
											 "&EventId="+event_ID+"&WorkId="+ID+"&type=1"+"&Data="+et.getText().toString()+"&Time="+time);
											 Log.v("format",
											 "输入框返回数据"+resultdata);
											try {
												 JSONObject resultJsonObject =
												 new JSONObject(resultdata);
												 String resultcodeString =
												 resultJsonObject.getString("result");
												 if
												 (resultcodeString.equals("10000"))
												 {
													 button.setText("已保存");
												JSONObject work = workcontent
														.getJSONObject(j);
												JSONArray view = work
														.getJSONArray("view_content");
												JSONObject vieww = view
														.getJSONObject(k);
												vieww.put("view_data", et
														.getText().toString());
												Toast.makeText(
														ExecuteActivity.this,
														"保存成功",
														Toast.LENGTH_LONG)
														.show();
												
												 }else {
												 Toast.makeText(ExecuteActivity.this,
												 "提交失败",
												 Toast.LENGTH_LONG).show();
												 }
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}

										}
									}).show();

				}

			}
		});

	}

	// 添加拍照布局
	private ImageView addview_capture(final String ID, final int j, final int k) {
		final ImageView btn_capture = new ImageButton(this);
		btn_capture.setImageResource(R.drawable.capture);
		btn_capture.setBackgroundColor(Color.parseColor("#EEE9E9"));
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400,
				400);
		btn_capture.setLayoutParams(params);
		// 添加时settag当前的count值

		btn_capture.setTag(R.id.tag_first, count);
		btn_capture.setTag(R.id.tag_second, ID);

		final Button button = new Button(this);
		LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		params2.gravity = Gravity.RIGHT;
		button.setLayoutParams(params2);
		button.setText("保存");
		//进度条
		final ProgressDialog progressDialog = new ProgressDialog(ExecuteActivity.this);
		progressDialog.setTitle("保存中");
		progressDialog.setMessage("Loading");
		progressDialog.setCancelable(true);
		
		//提示框
		final Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("提示");
		builder.setMessage("图片提交成功");
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {
					}
				});
		final Builder builder1 = new AlertDialog.Builder(this);
		builder.setTitle("提示");
		builder.setMessage("图片提交失败");
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							DialogInterface dialog,
							int which) {
					}
				});
		
		button.setBackgroundResource(R.drawable.selector);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				
//				progressDialog.show();  //将进度条显示出来
				
				final Map<String, File> files = new HashMap<String, File>();
				// 修改path值
				path = "/sdcard/Image/DemoDemo/" + ID + ".jpg";
				Log.v("format", "当前path" + path);
				final File file = new File(path);
				// 若文件存在
				if (file.exists()) {
//					new Thread(new Runnable() {
//
//						@Override
//						public void run() {
							// Log.v("format", "新线程");
							String resultdata = uploadparam(
									"http://123.206.16.157:8080/water/mission.req?action=mission_feedback",
									"MissionId=" + taskID + "&EventId="
											+event_ID + "&WorkId=" + photoname
											+ "&type=2" + "&Pic=" + ID
											+ "&Time=" + time);
							Log.v("format", "图片参数返回数据" + resultdata);
							try {
								JSONObject resultJsonObject = new JSONObject(
										resultdata);
								String resultcodeString = resultJsonObject
										.getString("result");
								if (resultcodeString.equals("10000")) {

//									Toast.makeText(ExecuteActivity.this,
//											"提交成功", Toast.LENGTH_LONG).show();
									int resultdata1 = uploadFile(file,
											"http://123.206.16.157:8080/water/imageUpload.req?action=upload");
									// HttpMultipartPost task = new
									// HttpMultipartPost(ExecuteActivity.this);
									// task.execute();
									try {
										// JSONObject resultJsonObject1 = new
										// JSONObject(resultdata1);
										// String resultcodeString1 =
										// resultJsonObject1.getString("result");

										if (resultdata1 == 200) {
											Log.v("format", "图片文件返回数据"
													+ resultdata1);
//											builder.show();
											button.setText("已保存");
											//取消提示
//											progressDialog.dismiss();
											
											JSONObject work = workcontent
													.getJSONObject(j);
											JSONArray view = work
													.getJSONArray("view_content");
											JSONObject vieww = view
													.getJSONObject(k);
											vieww.put("view_data", event_ID
													+ photoname);
										} else {
											//取消提示
//											progressDialog.dismiss();
											builder1.show();
											
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else {
									//取消提示
//									progressDialog.dismiss();
									builder1.show();
									
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

//						}
//					}).start();
					
				} else {
					Toast.makeText(ExecuteActivity.this, "找不到图片！", 1000).show();
					Log.v("format", "文件不存在");
				}

			}
		});
		l.addView(btn_capture);
		l.addView(button);
		return btn_capture;

	}

	@SuppressLint({ "ShowToast", "SdCardPath" })
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 相册图片
		case 1:
			if (resultCode == Activity.RESULT_OK) {
				/**
				 * 当选择的图片不为空的话，在获取到图片的途径
				 */
				Uri uri = data.getData();
				try {
					String[] pojo = { MediaStore.Images.Media.DATA };

					Cursor cursor = getContentResolver().query(uri, pojo, null,
							null, null);
					if (cursor != null) {

						ContentResolver cr = this.getContentResolver();
						int colunm_index = cursor
								.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
						cursor.moveToFirst();
						String path = cursor.getString(colunm_index);
						/***
						 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，
						 * 你选择的文件就不一定是图片了， 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
						 */
						String picPath = path;
						// Toast.makeText(ExecuteActivity.this, picPath, 1000)
						// .show();
						Bitmap bitmap = BitmapFactory.decodeStream(cr
								.openInputStream(uri));
						// 把相册图片重新压缩存到DemoDemo里边
						String sdStatus = Environment.getExternalStorageState();
						if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 锟斤拷锟絪d锟角凤拷锟斤拷锟�
							Log.i("TestFile",
									"SD card is not avaiable/writeable right now.");
							return;
						}
						
//						list.get(count).requestFocusFromTouch();
						new DateFormat();
						// Toast.makeText(this, photoname, Toast.LENGTH_LONG)
						// .show();
						Bundle bundle = data.getExtras();
						// Bitmap bitmap = (Bitmap) bundle.get("data");//
						// 获取相机返回的0数据，并转换为Bitmap图片格式

						FileOutputStream b = null;
						File file = new File("/sdcard/Image/DemoDemo/");
						
						file.mkdirs();// 创建文件夹
						Log.v("sdcard", "创建成功");
						String fileName = "/sdcard/Image/DemoDemo/" + taskID
								+ event_ID + photoname + ".jpg";

						try {
							b = new FileOutputStream(fileName);
							bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 锟斤拷锟斤拷锟斤拷写锟斤拷锟侥硷拷
							//加入图库广播
							Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
							Uri uri1 = Uri.fromFile(new File(fileName));
							intent.setData(uri1);
							ExecuteActivity.this.sendBroadcast(intent);

						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} finally {
							try {
								b.flush();
								b.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}

						Log.v("capture", "1");
//						btn_capture[count].setImageBitmap(bitmap);
						list.get(count).setFocusable(true);
						list.get(count).setFocusableInTouchMode(true);
						list.get(count).requestFocus();
						list.get(count).requestFocusFromTouch();
						boolean isF = list.get(count).isFocused();
						Log.e("在onresult选择相册方法中是焦点吗", ""+isF);
						list.get(count).setImageBitmap(bitmap);
						Log.v("capture", bitmap.toString());
//						btn_capture[count]
//								.setScaleType(ImageView.ScaleType.FIT_CENTER);
						list.get(count)
								.setScaleType(ImageView.ScaleType.FIT_CENTER);
						// btn_capture[y].getBackground().setAlpha(0);
					} else {
					}

				} catch (Exception e) {
				}
			}
			break;
		// 相机拍照
		case 2:
			if (resultCode == RESULT_OK) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 锟斤拷锟絪d锟角凤拷锟斤拷锟�
					Log.i("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}
				// 获取当前时间
				new DateFormat();
				String time = DateFormat.format("yyyyMMdd_hhmmss",
						Calendar.getInstance(Locale.CHINA))
						+ ".jpg";
				Toast.makeText(this, time, Toast.LENGTH_LONG).show();
				// 获取相机返回的数据，并转换为Bitmap图片格式
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");

				FileOutputStream b = null;
				File file = new File("/sdcard/Image/DemoDemo/");
				file.mkdirs();// 创建文件夹
				Log.v("sdcard", "创建成功");
				String fileName = "/sdcard/Image/DemoDemo/" + taskID + event_ID
						+ photoname + ".jpg";
				// 保存到文件地址，并压缩到bitmap
				try {
					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
					//加入图库广播
					Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
					Uri uri2 = Uri.fromFile(new File(fileName));
					intent.setData(uri2);
					ExecuteActivity.this.sendBroadcast(intent);
					// uploadparam("http://123.206.16.157:8080/water/mission.req?action=mission_feedback",
					// "MissionId="+taskID+
					// "&EventId="+event_ID+"&WorkId="+photoname+"&type=2"+"&Pic="+taskID+event_ID+photoname+"&Time="+time);
					// uploadFile(file,
					// "http://123.206.16.157:8080/water/mission.req?action=upload");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
//					btn_capture[count].setImageBitmap(bitmap);//图片显示到 ImageView
					list.get(count).setFocusable(true);
					list.get(count).setFocusableInTouchMode(true);
					list.get(count).requestFocus();
					list.get(count).requestFocusFromTouch();
					boolean isF = list.get(count).isFocused();
					Log.e("在onresult拍摄方法中是焦点吗", ""+isF);
					list.get(count).setImageBitmap(bitmap);
				} catch (Exception e) {
					e.printStackTrace();
//					Log.e("error", e.getMessage());
					e.printStackTrace();
				}
			}
			break;
		case 3:
			if (resultCode == RESULT_OK) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 判断SD卡状态
					Log.i("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}

				// 获取相机返回的数据，并转换为Bitmap图片格式
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");

				FileOutputStream b = null;
				File file = new File("/sdcard/Image/DemoDemo/");
				file.mkdirs();// 创建文件夹
				Log.v("format", "异常图片创建成功");
				String fileName = "/sdcard/Image/DemoDemo/"
						+ abnormalphotoString + ".jpg";
				// 保存到文件地址，并压缩到bitmap
				try {
					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					abnormalButton1.setImageBitmap(bitmap);// 将图片显示在ImageView里
				} catch (Exception e) {
//					Log.e("error", e.getMessage());
				}
			}
			break;
		case 4:
			if (resultCode == RESULT_OK) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}

				// 获取相机返回的数据，并转换为Bitmap图片格式
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");

				FileOutputStream b = null;
				File file = new File("/sdcard/Image/DemoDemo/");
				Log.v("format", "异常图片创建成功");
				String fileName = "/sdcard/Image/DemoDemo/"
						+ abnormalphotoString + ".jpg";
				Log.v("format", "图片保存为" + fileName);
				// 保存到文件地址，并压缩到bitmap
				try {
					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					abnormalButton2.setImageBitmap(bitmap);// 将图片显示在ImageView里
				} catch (Exception e) {
//					Log.e("error", e.getMessage());
					e.printStackTrace();
				}
			}
			break;
		case 5:
			if (resultCode == RESULT_OK) {
				String sdStatus = Environment.getExternalStorageState();
				if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
					Log.i("TestFile",
							"SD card is not avaiable/writeable right now.");
					return;
				}

				// 获取相机返回的数据，并转换为Bitmap图片格式
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");

				FileOutputStream b = null;
				File file = new File("/sdcard/Image/DemoDemo/");
				file.mkdirs();// 创建文件夹
				Log.v("format", "异常图片创建成功");
				String fileName = "/sdcard/Image/DemoDemo/"
						+ abnormalphotoString + ".jpg";
				// 保存到文件地址，并压缩到bitmap

				try {
					b = new FileOutputStream(fileName);
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				try {
					abnormalButton3.setImageBitmap(bitmap);// 将图片显示在ImageView里
				} catch (Exception e) {
//					Log.e("error", e.getMessage());
					e.printStackTrace();
				}
				break;
			}

		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	// 上传参数函数
	private String uploadparam(String myurl, String params) {
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
			urlConn.setRequestProperty("Charset", "UTF-8");

			urlConn.connect();// 连接既往服务端发送消息
			DataOutputStream dos = new DataOutputStream(
					urlConn.getOutputStream());
//			dos.writeBytes(params);
			dos.write(params.getBytes());
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

	// 上传文件函数
	public static int uploadFile(File file, String RequestURL) {
		int result = 0;
		String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		String PREFIX = "--", LINE_END = "\r\n";
		String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);
			if (file != null) {
				/**
				 * 当文件不为空，把文件包装并且上传
				 */
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后缀名的 比如:abc.png
				 */
				sb.append("Content-Disposition: form-data; name=\"f_file[]\"; filename=\""
						+ file.getName() + "\"" + LINE_END);
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				int res = conn.getResponseCode();
				Log.v("format", "response code:" + res);
				if (res == 200) {
					InputStream input = conn.getInputStream();
				    StringBuffer sb1 = new StringBuffer();
				    int ss;
				    while ((ss = input.read()) != -1) {
				    	sb1.append((char) ss);
				}
				}else {
					InputStream input = conn.getErrorStream();
					StringBuffer sb1 = new StringBuffer();
				    int ss;
				    while ((ss = input.read()) != -1) {
				    	sb1.append((char) ss);
				}
				    Log.v("format", sb1.toString());
				}
				
				result = res;
				Log.e(TAG, "result : " + result);

			}else{
				Log.v("format", "图片不存在");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 上传文件和参数函数
	/**
	 * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
	 * 
	 * @param url
	 *            Service net address
	 * @param params
	 *            text content
	 * @param files
	 *            pictures
	 * @return String result of Service response
	 * @throws IOException
	 */
	public static String post(String url, Map<String, String> params,
			Map<String, File> files) throws IOException {
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		URL uri = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(10 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);
		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}
		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		// 发送文件数据
		if (files != null)
			for (Map.Entry<String, File> file : files.entrySet()) {
				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"uploadfile\"; filename=\""
						+ file.getValue().getName() + "\"" + LINEND);
				sb1.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());
				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}
				is.close();
				outStream.write(LINEND.getBytes());
			}
		// 请求结束标志
		byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
		outStream.write(end_data);
		outStream.flush();
		// 得到响应码
		int res = conn.getResponseCode();
		InputStream in = conn.getInputStream();
		StringBuilder sb2 = new StringBuilder();
		if (res == 200) {
			int ch;
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}
		}
		outStream.close();
		conn.disconnect();
		return sb2.toString();
	}

	// 点击非软键盘处使软键盘隐藏
	public static Boolean hideInputMethod(Context context, View v) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm != null) {
			return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
		return false;
	}

	public static boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			v.getLocationInWindow(leftTop);
			int left = leftTop[0], top = leftTop[1], bottom = top
					+ v.getHeight(), right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 保留点击EditText的事件
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {
				if (hideInputMethod(this, v)) {
					// 隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
					return true;
				}
			}
		}
		return super.dispatchTouchEvent(ev);
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
			 urlConn.setRequestProperty("Charset", "utf-8");

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

	private String uploadlocation(String myurl, String phone, Double lat,
			Double lon, String time) {
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
			dos.writeBytes("phone="+phone);
			dos.writeBytes("&latitude=" + lat.toString());
			dos.writeBytes("&longitude=" + lon.toString());
			dos.writeBytes("&time=" + time);
			dos.writeBytes("&mission_id=" + taskID);
			dos.flush();// 清空缓存
			dos.close();// 关闭
			// 接收部分
			BufferedReader bReader = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			Log.v("format", "发送位置成功");
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

	// 以下anran写的 底部菜单栏
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.btn3:
			btn3Click();

			break;
			// 底部的三个按钮中的“扫一扫”
		case R.id.btn2:
			Intent intent2 = new Intent();
			intent2.setClass(ExecuteActivity.this, CaptureActivity.class);
			startActivity(intent2);

			break;
		case R.id.btn1:
			btn1Click();
			break;

		// case R.id.test2:
		//
		// Intent intent3 = new Intent();
		// intent3.setClass(SecondDemoActivity.this, CaptureActivity.class);
		// startActivity(intent3);
		//
		// break;
		default:
			break;
		}
	}

	public void btn3Click() {// 更多 1.位置设置 2.网络设置 3.系统设置
		if (open3 == true) {
			// view3 = inflater3.inflate(R.layout.child_menu3, popLayout3,
			// true);
			// childLayout3 = (LinearLayout)
			// view3.findViewById(R.id.child_layout3);

			// “更多”中的2.WLAN设置

			help1 = (Button) view3.findViewById(R.id.more1);
			help1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivityForResult(
							new Intent(
									android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),
							0);
				}
			});

			help2 = (Button) view3.findViewById(R.id.more2);
			help2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivityForResult(new Intent(
							android.provider.Settings.ACTION_WIFI_SETTINGS), 0);
				}
			});

			help3 = (Button) view3.findViewById(R.id.more3);
			help3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivityForResult(new Intent(
							android.provider.Settings.ACTION_SETTINGS), 0);
				}
			});

			open3 = false;
		}

		if (flag3 == false) {
			flag3 = true;
			childLayout3.setVisibility(View.VISIBLE);
			childLayout1.setVisibility(View.GONE);
			flag1 = false;
		} else {
			flag3 = false;
			childLayout3.setVisibility(View.GONE);
		}
	}

	public void btn1Click() {// 告警
		if (open1 == true) {

			question1 = (Button) view1.findViewById(R.id.question1);
			question1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					final Dialog dialog = new Dialog(ExecuteActivity.this);
					dialog.setContentView(R.layout.dialog_test1);
					final EditText et1 = (EditText) dialog
							.findViewById(R.id.et1);
					final EditText et2 = (EditText) dialog
							.findViewById(R.id.et2);
					abnormalButton1 = (ImageButton) dialog
							.findViewById(R.id.abnormal_btn);
					abnormalButton1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							//异常拍照名字
							abnormalphotoString = time1 + event_ID;
							Intent cameraIntent = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

							startActivityForResult(cameraIntent, 3);
						}
					});
					Button btn = (Button) dialog.findViewById(R.id.btn1);
					btn.setOnClickListener(new OnClickListener() {

						@SuppressLint("SdCardPath")
						@Override
						public void onClick(View v) {
							path = "/sdcard/Image/DemoDemo/"
									+ abnormalphotoString + ".jpg";
							Log.v("format", "异常图片路径："+path);
							final File file = new File(path);
							String params = "abnormal_check_point="+check_point+"&abnormal_description="
									+ et1.getText().toString()
									+ "&abnormal_level=" + "1"
									+ "&abnormal_person=" + worker
									+ "&abnormal_time=" + time
									+ "&workshop="+workshop
									+ "&mission_id="+taskID
									+ "&factory_id=" + factory_id
									+ "&workshop_id=" + workshop_id
									+ "&suggestion="+ et2.getText().toString()
									+ "&pic="
									+ abnormalphotoString
									+"&jingdu="+lon
									+"&weidu="+lat
									+"&abnormal_status=1"
									+"&report_worker="+worker
									+"&event_id="+event_ID;
							        
							Log.v("yigenijiayigewolianggeren", params);
							String result = uploadparam(
									"http://123.206.16.157:8080/water/mission.req?action=insertException",
									params);
							
							Log.v("namalushangtiantain", factory_id+result);
							
							Log.v("format", "pic:" + abnormalphotoString);
							Log.v("format", "异常参数上传" + result);
							int photoresult = uploadFile(file,
									"http://123.206.16.157:8080/water/imageUpload.req?action=upload");
							Log.v("format", "异常上传图片" + photoresult);
							try {
								JSONObject resultJsonObject = new JSONObject(
										result);
								String resultcode = resultJsonObject
										.getString("result");
								if (resultcode.equals("10000")) {
									Toast.makeText(ExecuteActivity.this,
											"异常提交成功", Toast.LENGTH_SHORT)
											.show();
									dialog.cancel();
								} else {
									Toast.makeText(ExecuteActivity.this,
											"异常提交失败，请稍后重试",
											Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
					dialog.show();

				}
			});
			question2 = (Button) view1.findViewById(R.id.question2);
			question2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					final Dialog dialog = new Dialog(ExecuteActivity.this);
					dialog.setContentView(R.layout.dialog_test1);
					final EditText et1 = (EditText) dialog
							.findViewById(R.id.et1);
					final EditText et2 = (EditText) dialog
							.findViewById(R.id.et2);
					abnormalButton2 = (ImageButton) dialog
							.findViewById(R.id.abnormal_btn);
					abnormalButton2.setOnClickListener(new OnClickListener() {
						@SuppressLint("ShowToast")
						@Override
						public void onClick(View v) {
							//异常拍照名字
							abnormalphotoString = time1 + event_ID;
							Intent cameraIntent = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

							startActivityForResult(cameraIntent, 4);
						}
					});
					Button btn = (Button) dialog.findViewById(R.id.btn1);
					btn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							path = Environment.getExternalStorageDirectory()
									+ "/Image/DemoDemo/" + abnormalphotoString
									+ ".jpg";
							Log.v("format", path);
							final File file = new File(path);
							
							String result = uploadparam(
									"http://123.206.16.157:8080/water/mission.req?action=insertException",
									"abnormal_check_point="+check_point+"&abnormal_description="
											+ et1.getText().toString()
											+ "&abnormal_level=2"
											+ "&abnormal_person="+worker
											+ "&mission_id="+taskID
											+ "&abnormal_time="+time
											+ "&workshop="+workshop
											+ "&factory_id="+factory_id
											+ "&workshop_id=" + workshop_id
											+ "&suggestion="+et2.getText().toString()
											+ "&pic="
											+ abnormalphotoString
											+"&jingdu="+lon
											+"&weidu="+lat
											+"&abnormal_status=1"
											+"&report_worker="+worker
											+"&event_id="+event_ID);
							Log.v("format", "异常参数上传" + result);
							int photoresult = uploadFile(file,
									"http://123.206.16.157:8080/water/imageUpload.req?action=upload");
							Log.v("format", "异常图片上传" + photoresult);
							try {
								JSONObject resultJsonObject = new JSONObject(
										result);
								String resultcode = resultJsonObject
										.getString("result");
								if (resultcode.equals("10000")) {
									Toast.makeText(ExecuteActivity.this,
											"异常提交成功", Toast.LENGTH_SHORT)
											.show();
									dialog.cancel();
								} else {
									Toast.makeText(ExecuteActivity.this,
											"异常提交失败，请稍后重试",
											Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
					});
					dialog.show();

				}
			});
			question3 = (Button) view1.findViewById(R.id.question3);
			question3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

					final Dialog dialog = new Dialog(ExecuteActivity.this);
					dialog.setContentView(R.layout.dialog_test1);
					final EditText et1 = (EditText) dialog
							.findViewById(R.id.et1);
					final EditText et2 = (EditText) dialog
							.findViewById(R.id.et2);
					abnormalButton3 = (ImageButton) dialog
							.findViewById(R.id.abnormal_btn);
					abnormalButton3.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// 异常拍照名字
							abnormalphotoString = time1 + event_ID;
							Intent cameraIntent = new Intent(
									android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

							startActivityForResult(cameraIntent, 5);
						}
					});
					Button btn = (Button) dialog.findViewById(R.id.btn1);
					btn.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							path = Environment.getExternalStorageDirectory()
									+ "/Image/DemoDemo/" + abnormalphotoString
									+ ".jpg";
							Log.v("format", path);
							final File file = new File(path);
							
							String result = uploadparam(
									"http://123.206.16.157:8080/water/mission.req?action=insertException",
									"abnormal_check_point="+check_point+"&abnormal_description="
											+ et1.getText().toString()
											+ "&abnormal_level=" + "3"
											+ "&abnormal_person=" + worker
											+ "&abnormal_time=" + time
											+ "&workshop="+workshop
											+ "&mission_id="+taskID
											+ "&pic="
											+ abnormalphotoString
											+ "&factory_id="+factory_id
											+ "&workshop_id=" + workshop_id
											+ "&suggestion="+ et2.getText().toString()
											+ "&jingdu="+lon
											+ "&weidu="+lat
											+ "&abnormal_status=1"
											+ "&report_worker="+worker
											+ "&event_id="+event_ID);
							Log.v("format", "异常参数上传" + result);
							result = uploadparam(myurl);
							Log.v("format", result);
							int photoresult = uploadFile(file,
									"http://123.206.16.157:8080/water/imageUpload.req?action=upload");
							Log.v("format", "异常图片上传" + photoresult);
							try {
								JSONObject resultJsonObject = new JSONObject(
										result);
								String resultcode1 = resultJsonObject
										.getString("result");
								if (resultcode1.equals("10000")) {
									Toast.makeText(ExecuteActivity.this,
											"异常提交成功", Toast.LENGTH_SHORT)
											.show();
									dialog.cancel();
								} else {
									Toast.makeText(ExecuteActivity.this,
											"异常提交失败，请稍后重试",
											Toast.LENGTH_SHORT).show();
								}
							} catch (JSONException e) {
								e.printStackTrace();
							}

						}
					});
					dialog.show();

				}
			});
			open1 = false;
		}

		if (flag1 == false) {
			flag1 = true;
			childLayout1.setVisibility(View.VISIBLE);
			childLayout3.setVisibility(View.GONE);
			flag3 = false;
		} else {
			flag1 = false;
			childLayout1.setVisibility(View.GONE);
		}
	}

	public class MyLocationListener implements BDLocationListener{
	    public void onReceiveLocation(BDLocation location){
	        //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
	        //以下只列举部分获取经纬度相关（常用）的结果信息
	        //更多结果信息获取说明，请参照类参考中BDLocation类中的说明
				
	        lat = location.getLatitude();    //获取纬度信息
	        lon = location.getLongitude();    //获取经度信息
	        float radius = location.getRadius();    //获取定位精度，默认值为0.0f
				
	        String coorType = location.getCoorType();
	        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
				
	        int errorCode = location.getLocType();
	        //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
	        
	    }
	}
	

}
