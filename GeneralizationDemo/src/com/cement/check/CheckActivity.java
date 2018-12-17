package com.cement.check;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cement.data.CheckJson;
import com.cement.thread.ChangeConditionThread;
import com.cement.thread.CheckOpinionThread;
import com.cement.thread.CheckThread;
import com.cement.thread.ExceptionChartThread;
import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.MainActivity;
import com.example.generalizationdemo.R;
import com.generalization.chart.ChartActivity;
import com.google.gson.JsonObject;

public class CheckActivity extends Activity {
	
	private Button examine;
	private Button anewtask;
	private LinearLayout linearlayout_check;
	private JSONObject test;
	private Handler handler_checkopinion;
	private Handler handler_listagain;
	private EditText check_opinion;
	private EditText check_person;
	private String TEL;
	
	private Button[] btn_picture = new Button[10000];
	
	private Handler handler_changecondition;
	private Bitmap bitmap;
	private ImageView picture;
	private ProgressDialog pd;
	
	private final String TAG  = "ZOUGOU";
	private String mission_id;
	private String task_id;
	private String workshop_id;
	
	private SharedPreferences preferences;
	private String pic_event_id;
	private String pic_work_name;
//	private String filepath = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&mission_id=1&event_id=1&work_name=B2";
	private String filepath;
	int count = 0;
	public static JSONObject json;//这个是破芸用到重新下发页面的json
	private String name;
	private String factory_id ;
	
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.check);
	       
	       getWindow().setSoftInputMode(
	    		    WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	   linearlayout_check = (LinearLayout) findViewById(R.id.linearlayout_check); 
	   
	   preferences = getSharedPreferences("user", MODE_PRIVATE);
	   factory_id = preferences.getString("factory_id", null);
	   TEL = preferences.getString("tel", null);
	   name = preferences.getString("name", null);
	   workshop_id = preferences.getString("workshop_id", null);
	   
	   check_opinion = (EditText) findViewById(R.id.check_opinion);
//	   check_person = (EditText) findViewById(R.id.check_person);
	   
	   examine = (Button) findViewById(R.id.examine);
	   anewtask = (Button) findViewById(R.id.anewtask);
	   examine.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		
			handler_checkopinion = new Handler(){
				
				public void handleMessage(Message msg){
					super.handleMessage(msg);
					if (msg.what == 12138) {
						
						String backMsg = msg.obj.toString();
					
					Log.v("ruguoniyetingshuo", backMsg);
					try{
						
						JSONObject toor = new JSONObject(backMsg);
						String result = toor.getString("result");
						if(result.equals("10000")){
							Toast.makeText(getApplicationContext(), "审核成功！", Toast.LENGTH_LONG).show();
							
							handler_changecondition = new Handler(){
								
								public void handleMessage(Message msg){
									super.handleMessage(msg);
									if (msg.what == 12138) {
										
									String backMsg = msg.obj.toString();
									
									Log.v("sanciwoshou", backMsg);
									try{
										
										JSONObject toor = new JSONObject(backMsg);
//										JSONObject result1 = new JSONObject(toor.getString("result"));
										String result = toor.getString("result");
										if(result.equals("10000")){
										
											handler_listagain = new Handler(){
												
												public void handleMessage(Message msg){
													super.handleMessage(msg);
													if (msg.what == 12138) {
														
													String backMsg = msg.obj.toString();
													
													Log.v("sanciwoshou", backMsg);
													try{
														
														JSONObject toor = new JSONObject(backMsg);
														JSONObject result1 = new JSONObject(toor.getString("result"));
														String result = result1.getString("result");
														if(result.equals("10000")){
															
															CheckJson checkjson = new CheckJson();
															checkjson.checkjson(backMsg);
															
															Intent intent = new Intent();
															intent.setClass(CheckActivity.this, CheckListActivity.class);
															startActivity(intent);
															
														}else if(result.equals("10001")){
															Toast.makeText(CheckActivity.this, "请完整填写“审核意见”和“审核人”!", 1).show();
														}else{
															Toast.makeText(CheckActivity.this, "暂时没有数据！", 1).show();
//															Intent intent = new Intent();
//															intent.setClass(CheckActivity.this, HomePageya.class);
//															startActivity(intent);
														}
														
													} catch (JSONException e) {
												        e.printStackTrace();
												    
													}
												}
											}
										};
											
										String value = "phone=" + TEL + "&factory_id="+factory_id +"&workshop_id=" + workshop_id;
										Log.v("ganggangdefenshoubuxiangshizhende", value);
											CheckThread thread = new CheckThread(handler_listagain, value);
											new Thread(thread).start();
											
											
//											Intent intent = new Intent();
//											intent.setClass(CheckActivity.this, CheckListActivity.class);
//											startActivity(intent);
										}
										
									} catch (JSONException e) {
								        e.printStackTrace();
								    
									}
								}
							}
						};
							
							String value = "mission_id=" + mission_id +"&condition=6";
							ChangeConditionThread thread = new ChangeConditionThread(handler_changecondition, value);
							new Thread(thread).start();
							
						}else if(result.equals("10001")){
							Toast.makeText(CheckActivity.this, "请完整填写“审核意见”和“审核人”!", 1).show();
						}
						
					} catch (JSONException e) {
				        e.printStackTrace();
				    
					}
			
			
					}
				}
			};
			
			SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd HH:mm:ss");       
	        Date    curDate    =   new    Date(System.currentTimeMillis());//获取当前时间       
	        String time  =    formatter.format(curDate);
			 
			
			String value = "phone=" + TEL + "&mission_id=" + mission_id + "&audit_opinion=" +
			check_opinion.getText().toString() + "&audit_time=" + time + "&auditor=" + name;
			
			Log.v("gaosuzijiyaowanglta", value);
			
			CheckOpinionThread thread = new CheckOpinionThread(handler_checkopinion, value);
			new Thread(thread).start();
			
			
//			Intent intent = new Intent(CheckActivity.this,HomePageya.class);
//			startActivity(intent);
			
		}
	});
	   
	   
	   anewtask.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					String urlStr =  "http://123.206.16.157:8080/water/mission.req?action=setMission";
					
//						runContent(urlStr, "taskname="+name+"&taskcreate="+"任务生成", true, name, num,  3);
					OutputStream out = null;
					BufferedReader reader = null;
					try {
//						
						URL url = new URL(urlStr);
						HttpURLConnection conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						conn.setDoOutput(true);
						conn.setDoInput(true);
						conn.setConnectTimeout(5000);
						conn.setReadTimeout(5000);
						out = conn.getOutputStream();
						//task_id
						out.write(("taskname="+task_id+"&taskcreate="+"任务生成"+"&factory_id="+factory_id).getBytes());
						reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						String line;
						StringBuilder builder = new StringBuilder();
						while((line = reader.readLine())!=null){
							builder.append(line);
						}
						System.out.println("第一个页面返回的builder"+builder.toString());
						JSONObject  test = new JSONObject(builder.toString());
						json = test.getJSONObject("data");
						
						Intent intent = new Intent(CheckActivity.this,com.example.taskeditor.taskeditor.TaskSendActivity.class);
						intent.putExtra("activity_name", "CheckActivity");
						intent.putExtra("mission_id",mission_id);
						
						startActivityForResult(intent, 3);
						
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
	       
	   try {
//			InputStreamReader isr = new InputStreamReader(getAssets().open(
//					"check.json"), "UTF-8");
//			BufferedReader br = new BufferedReader(isr);
//			String line;
//			StringBuilder builder = new StringBuilder();
//			while ((line = br.readLine()) != null) {
//				builder.append(line);
//			}
//			br.close();
//			isr.close();
//			Log.v("check", builder.toString());
		   
		   String backMsg = getIntent().getStringExtra("backMsg");
    	   mission_id = getIntent().getStringExtra("mission_id");
			test = new JSONObject(backMsg);
			
			JSONObject data = test.getJSONObject("data");
			
			// 添加任务号
			addtexttitle("任务ID：" + mission_id,
					data.getString("font_color"), data.getInt("font_size"));

			task_id = data.getString("taskname");
			Log.v("bieliwowofanzhena!", task_id);
			// 添加任务说明
			JSONArray note_array = data.getJSONArray("note");
			for (int j = 0; j < note_array.length(); j++) {
				JSONObject note = note_array.getJSONObject(j);
				String note_name = note.getString("note_name");
				String note_content = note.getString("note_content");
				addtext(note_name + ":" + note_content,
						note.getString("font_color"), note.getInt("font_size"));
			
			}
			/*******************0509到时候乱码好了******************8
			 *
			//20180509我自己造的个json
			InputStreamReader isr = new InputStreamReader(getAssets().open(
					"mission_json.json"), "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			br.close();
			isr.close();
			Log.v("haizaizhuandongdeshoulapei", builder.toString());
			
			JSONObject event1 = new JSONObject(builder.toString());
			JSONArray event2 = event1.getJSONArray("event");
	 
		 */
			JSONArray event_array = data.getJSONArray("event");
			for (int j = 0; j < event_array.length(); j++) {
				JSONObject event = event_array.getJSONObject(j);
				String event_id = event.getString("event_ID");
				String event_name = event.getString("event_name");
				addtexttitle(event_id + event_name, "#6699FF", 25);
				
			
//			for (int j = 0; j < event2.length(); j++) {
//				JSONObject event = event2.getJSONObject(j);
//				String event_id = event.getString("event_ID");
////				String event_name = event.getString("event_name");
//				addtexttitle(event_id, "#080808", 30);
				JSONArray workcontent = event.getJSONArray("workcontent");
				for (int k = 0; k < workcontent.length(); k++) {
					JSONObject work = workcontent.getJSONObject(k);
					
//					String work_id = work.getString("work_ID");
					String work_name = work.getString("work_name");
					addtexttitle(work_name, "#080808", 20);
					
				JSONArray view_content = work.getJSONArray("view_content");
				for (int m = 0; m < view_content.length(); m++) {
					JSONObject view = view_content.getJSONObject(m);
					String view_class = view.getString("view_class");
					String view_name = view.getString("view_name");
					
//					addtext(view_name, "#080808", 20);
					if(view_class.equals("拍照")){
						addtext(view_name, "#080808", 20);
//						addbutton(event_id, work_name);
						
						btn_picture[count] = addbutton(event_id, work_name);
						btn_picture[count].setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								count = (Integer)v.getTag(R.id.tag_count);
								pic_event_id = (String)v.getTag(R.id.tag_event_id);
								pic_work_name = (String)v.getTag(R.id.tag_work_name);
								Log.v("yiyangdeyueguang", pic_event_id+pic_work_name);
								
								try {
									pic_work_name = java.net.URLEncoder.encode(pic_work_name,"utf-8");
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								Log.v("yiyangdeyueguang", pic_event_id+pic_work_name);
							filepath = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&mission_id="+mission_id+"&event_id="+pic_event_id+"&work_name="+pic_work_name;
//							filepath = "http://img.my.csdn.net/uploads/201402/24/1393242467_3999.jpg";
								Log.v("yiyangdeyueguang", filepath);
								new Thread(connectNet).start();
								
								pd = new ProgressDialog(CheckActivity.this);
				            	pd.setTitle("图片");
				            	pd.setIndeterminate(false);
				            	pd.setCancelable(false);
				            	pd.setMessage("正在下载，请耐心等待");
				            	pd.show();
								
//								LinearLayout loginDialog = (LinearLayout)LayoutInflater.from(CheckActivity.this).inflate(R.layout.alertdialog_layout, null);
//								picture = (ImageView) loginDialog.findViewById(R.id.picture);
//								new AlertDialog.Builder(CheckActivity.this)
//						        .setTitle("照片")
//						        .setMessage("")
//						        .setView(loginDialog)
//						        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
//						            @Override
//						            public void onClick(DialogInterface dialog, int which) {
////						            	picture.setImageBitmap(bitmap);
//						            	
//						            }
//						        
//						        })
//						       
//						        .show();
								
							}
						});
						count = count + 1;
					}else if(view_class.contains("输入框")){
						Iterator keys = view.keys();
						while(keys.hasNext()){
							String key = String.valueOf(keys.next());
							if(key.equals("view_data")){
								String view_data = view.getString("view_data");
								addtext(work_name+":"+view_data, "#080808", 20);
							}
							
						}
						
					}
				}
			}
			}
//			addbutton("拍照", "除氧器");
			}catch (JSONException e) {
				e.printStackTrace();
			} 
//	   catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	   
	}
		//添加查看图片按钮
		private Button addbutton(String event_id, String work_name){
			Log.v("huoxuzhiyounidongdeww", event_id+work_name);
			
			pic_event_id = event_id;
			pic_work_name = work_name;
			
			Button btn = new Button(this);
//			btn.setBackgroundResource(R.drawable.waiting);
			btn.setText("查看照片");
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			btn.setLayoutParams(params);
			btn.setBackgroundColor(Color.parseColor("#29b6f6"));
			
			btn.setTag(R.id.tag_count, count);
			btn.setTag(R.id.tag_event_id, event_id);
			btn.setTag(R.id.tag_work_name, work_name);
			
//			btn.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Log.v("huoxuzhiyounidongdeww", pic_event_id + pic_work_name);
//					
////					filepath = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&mission_id="+mission_id+"&event_id="+pic_event_id+"&work_name="+pic_work_name;
////					filepath = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&mission_id=20&event_id=9&work_name=设备清洁度";
//					filepath = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&mission_id=20&event_id=9&work_name=生产线是否启动";
////					filepath = "http://img.my.csdn.net/uploads/201402/24/1393242467_3999.jpg";
//					
////					Log.v("wobushinidetangguo", filepath);
//					new Thread(connectNet).start();
//					
//					
//					
//					LinearLayout loginDialog = (LinearLayout)LayoutInflater.from(CheckActivity.this).inflate(R.layout.alertdialog_layout, null);
//					picture = (ImageView) loginDialog.findViewById(R.id.picture);
//					new AlertDialog.Builder(CheckActivity.this)
//			        .setTitle("照片")
//			        .setMessage("")
//			        .setView(loginDialog)
//			        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
//			            @Override
//			            public void onClick(DialogInterface dialog, int which) {
////			            	picture.setImageBitmap(bitmap);
//			            	
//			            }
//			        
//			        })
//			       
//			        .show();
//				}
//			});
			linearlayout_check.addView(btn);
			return btn;
		}
	
	//添加文本
		private void addtext(String content,String color,int size){
			TextView tView = new TextView(this);
			tView.setText(content);
			tView.setTextColor(Color.parseColor(color));
			tView.setTextSize(size);
			linearlayout_check.addView(tView);
		}
		
		//添加居中文本
		private void addtexttitle(String content,String color,int size){
			TextView tView = new TextView(this);
			tView.setText(content);
			tView.setTextColor(Color.parseColor(color));
			tView.setTextSize(size);
			tView.setGravity(Gravity.CENTER);
			linearlayout_check.addView(tView);
		}
		
		//添加拍照布局
		private ImageView addview_picture(){
			final ImageView btn_picture = new ImageButton(this);
			btn_picture.setImageResource(R.drawable.mario);
			btn_picture.setBackgroundColor(Color.parseColor("#FFFFFF"));
			//设置图片大小
//			btn_capture.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)); 
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400,  
	                400);  
			btn_picture.setLayoutParams(params); 
	       
	        linearlayout_check.addView(btn_picture);
			
			
			return btn_picture;

		}
	//点击非软键盘处使软键盘隐藏
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
	            int left = leftTop[0], top = leftTop[1], bottom = top + v.getHeight(), right = left  
	                    + v.getWidth();  
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
	                if(hideInputMethod(this, v)) {  
	                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件  
	                }  
	            }  
	        }  
	        return super.dispatchTouchEvent(ev);  
	    }  
		
//		@Override
//		public boolean onKeyDown(int keyCode, KeyEvent event) {
//			// TODO Auto-generated method stub
//			if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//				Intent intent1 = new Intent(CheckActivity.this,CheckListActivity.class);
//				startActivity(intent1);
//			}
//			return super.onKeyDown(keyCode, event);
//		}
		
		private Runnable connectNet = new Runnable(){  
	        @Override  
	        public void run() {  
	            try {  
	            	
	            	
	            	
//	            	filepath = "http://img.my.csdn.net/uploads/201402/24/1393242467_3999.jpg";
//	            	String filepath = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&mission_id=1&event_id=1&work_name=B2";
//	                String filePath2 = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&pic_class=2&mission_id="+mission_id;
//	                String filePath3 = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&pic_class=3&mission_id="+mission_id;
//	                http://img.my.csdn.net/uploads/201402/24/1393242467_3999.jpg
//	                http://59.110.157.159:8080/water/imageDownload.req?action=filedownload&mission_id=19930610&pic_class=1
//	                mFileName = "xyyzz.jpg";

	                //以下是取得图片的两种方法  
	                //////////////// 方法1：取得的是byte数组, 从byte数组生成bitmap  
//	                byte[] data2 = getImage(filePath2);  
//	                byte[] data3 = getImage(filePath3);
//	                if(data2!=null){  
//	                	bitmap_check2 = BitmapFactory.decodeByteArray(data2, 0, data2.length);// bitmap  
//	                	bitmap_check3 = BitmapFactory.decodeByteArray(data3, 0, data3.length);
//	                	
//	                	
//	                	bitmap_check2 = BitmapFactory.decodeStream(getImageStream(filePath2));
//	                	
//	                	Intent intent = new Intent();
//	                	intent.setClass(CheckListActivity.this, CheckActivity.class);
//	                	startActivity(intent);
//	                    
//	                }else{  
//	                    Toast.makeText(CheckListActivity.this, "Image error!", 1).show();  
//	                }  
	                ////////////////////////////////////////////////////////  
	  
	                //******** 方法2：取得的是InputStream，直接从InputStream生成bitmap ***********/
	                
	                bitmap = BitmapFactory.decodeStream(getImageStream(filepath));
	                String sdStatus = Environment.getExternalStorageState();
					if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 锟斤拷锟絪d锟角凤拷锟斤拷锟�
						Log.i("TestFile",
								"SD card is not avaiable/writeable right now.");
						return;
					}
					new DateFormat();
			
					// 获取相机返回的0数据，并转换为Bitmap图片格式

					FileOutputStream b = null;
					File file = new File("/sdcard/Image/20180628/");
					
					file.mkdirs();// 创建文件夹
					Log.v("sdcard", "创建成功");
					String fileName = "/sdcard/Image/20180628/"+"1541.jpg";

					try {
						b = new FileOutputStream(fileName);
						bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 锟斤拷锟斤拷锟斤拷写锟斤拷锟侥硷拷
						//加入图库广播
						Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
						Uri uri1 = Uri.fromFile(new File(fileName));
						intent.setData(uri1);
						CheckActivity.this.sendBroadcast(intent);

					} catch(FileNotFoundException e){
						e.printStackTrace();
					}
					finally {
						try {
							b.flush();
							b.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
	                Log.v("tomorrow", "whaoithposin;");
	                
	               
	                //********************************************************************/  
	  
	                // 发送消息，通知handler在主线程中更新UI  
	                connectHanlder.sendEmptyMessage(0);  
	                Log.d(TAG, "set image ...");  
	            } catch (Exception e) {  
//	                Toast.makeText(SearchListActivity.this,"无法链接网络！", 1).show();  
	                e.printStackTrace();  
	            }  
	  
	        }  
	  
	    };  
	    
	    /** 
	     * Get image from network 
	     * @param path The path of image 
	     * @return byte[] 
	     * @throws Exception 
	     */  
	    public byte[] getImage(String path) throws Exception{  
	        URL url = new URL(path);  
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5 * 1000);  
	        conn.setRequestMethod("GET");  
//	        conn.setRequestMethod("POST");
	        InputStream inStream = conn.getInputStream();  
	        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
	            return readStream(inStream);  
	        }  
	        return null;  
	    }  
	    
	    /** 
	     * Get image from network 
	     * @param path The path of image 
	     * @return InputStream 
	     * @throws Exception 
	     */  
	    public InputStream getImageStream(String path) throws Exception{  
	        URL url = new URL(path);  
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
	        conn.setConnectTimeout(5*1000);  
	        conn.setRequestMethod("GET"); 
//	        conn.setRequestMethod("POST");
	        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
	            return conn.getInputStream();  
	        }else{
	        	Toast.makeText(getApplicationContext(), "暂无图片！", Toast.LENGTH_LONG).show();
	        }
	        return null;  
	    }  
	    
	    /** 
	     * Get data from stream 
	     * @param inStream 
	     * @return byte[] 
	     * @throws Exception 
	     */  
	    public static byte[] readStream(InputStream inStream) throws Exception{  
	        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
	        byte[] buffer = new byte[1024];  
	        int len = 0;  
	        while( (len=inStream.read(buffer)) != -1){  
	            outStream.write(buffer, 0, len);  
	        }  
	        outStream.close();  
	        inStream.close();  
	        return outStream.toByteArray();  
	    }  
	    
	    private Handler connectHanlder = new Handler() {  
	        @Override  
	        public void handleMessage(Message msg) {  
	            Log.d(TAG, "display image");  
	            // 更新UI，显示图片  
	            if (bitmap != null) {  
	            	pd.dismiss();
	            	LinearLayout loginDialog = (LinearLayout)LayoutInflater.from(CheckActivity.this).inflate(R.layout.alertdialog_layout, null);
					picture = (ImageView) loginDialog.findViewById(R.id.picture);
					new AlertDialog.Builder(CheckActivity.this)
			        .setTitle("照片")
			        .setMessage("")
			        .setView(loginDialog)
			        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
			            @Override
			            public void onClick(DialogInterface dialog, int which) {
//			            	picture.setImageBitmap(bitmap);
			            	
			            }
			        
			        })
			       
			        .show();
	            	picture.setImageBitmap(bitmap);
	            	pd.dismiss();
	            	Log.d("fanzhengqowoihegoiaw", "jishiyaowogennizaihaogeshinian"); 
	            }else{
	            	pd.dismiss();
	            	picture.setBackgroundResource(R.drawable.buffering);
	            }
	        }  
	    };  
		
}
