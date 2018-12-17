package com.example.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.generalizationdemo.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ComLayout {
	private LinearLayout l;
	private JSONObject json;
	private Activity activity;
	private int mission_id;
	private Button[] btn_picture = new Button[1000];
	int count = 0;
	private String filepath;
	private Bitmap bitmap;
	private ImageView picture;
	private String pic_event_id;
	private String pic_work_name;
	
	public ComLayout(LinearLayout l, JSONObject json, Activity activity,int mission_id) {
		super();
		this.l = l;
		this.json = json;
		this.activity = activity;
		this.mission_id = mission_id;
	}
	public void addView(){
		 try {
				
				JSONObject data = json.getJSONObject("data");
				
				// 添加任务号
				addtexttitle("任务ID：" + mission_id,
						data.getString("font_color"), data.getInt("font_size"));

				
				// 添加任务说明
				JSONArray note_array = data.getJSONArray("note");
				for (int j = 0; j < note_array.length(); j++) {
					JSONObject note = note_array.getJSONObject(j);
					String note_name = note.getString("note_name");
					String note_content = note.getString("note_content");
					addtext(note_name + ":" + note_content,
							note.getString("font_color"), note.getInt("font_size"));
				
				}
				
				JSONArray event_array = data.getJSONArray("event");
				for (int j = 0; j < event_array.length(); j++) {
					JSONObject event = event_array.getJSONObject(j);
					String event_id = event.getString("event_ID");
//					String event_name = event.getString("event_name");
					addtexttitle(event_id, "#080808", 20);
					
				
					JSONArray workcontent = event.getJSONArray("workcontent");
					for (int k = 0; k < workcontent.length(); k++) {
						JSONObject work = workcontent.getJSONObject(k);
						
//						String work_id = work.getString("work_ID");
						String work_name = work.getString("work_name");
						addtexttitle(work_name, "#080808", 25);
						
					JSONArray view_content = work.getJSONArray("view_content");
					for (int m = 0; m < view_content.length(); m++) {
						JSONObject view = view_content.getJSONObject(m);
						String view_class = view.getString("view_class");
						String view_name = view.getString("view_name");
						
//						addtext(view_name, "#080808", 20);
						if(view_class.equals("拍照")){
							addtext(view_class, "#080808", 20);
//							addbutton(event_id, work_name);
							
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
//								filepath = "http://img.my.csdn.net/uploads/201402/24/1393242467_3999.jpg";
									Log.v("yiyangdeyueguang", filepath);
									new Thread(connectNet).start();
									
									
									LinearLayout loginDialog = (LinearLayout)LayoutInflater.from(activity).inflate(R.layout.alertdialog_layout, null);
									picture = (ImageView) loginDialog.findViewById(R.id.picture);
									new AlertDialog.Builder(activity)
							        .setTitle("照片")
							        .setMessage("")
							        .setView(loginDialog)
							        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
							            @Override
							            public void onClick(DialogInterface dialog, int which) {
//							            	picture.setImageBitmap(bitmap);
							            	
							            }
							        
							        })
							       
							        .show();
									
								}
							});
							count = count + 1;
						}else if(view_class.contains("输入框")){
							Iterator keys = view.keys();
							while(keys.hasNext()){
								String key = String.valueOf(keys.next());
								if(key.equals("view_data")){
									String view_data = view.getString("view_data");
									addtext(view_name+":"+view_data, "#080808", 20);
								}
								
							}
							
						}
					}
				}
				}
//				addbutton("拍照", "除氧器");
				}catch (JSONException e) {
					e.printStackTrace();
				} 
	}

	//添加查看图片按钮
	private Button addbutton(String event_id, String work_name){
		Log.v("huoxuzhiyounidongdeww", event_id+work_name);
		
		pic_event_id = event_id;
		pic_work_name = work_name;
		
		Button btn = new Button(activity);
//		btn.setBackgroundResource(R.drawable.waiting);
		btn.setText("查看照片");
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(300,  
                100); 
		btn.setLayoutParams(params);
		btn.setBackgroundColor(Color.parseColor("#29b6f6"));
		
		btn.setTag(R.id.tag_count, count);
		btn.setTag(R.id.tag_event_id, event_id);
		btn.setTag(R.id.tag_work_name, work_name);
		l.addView(btn);
		return btn;
	}
	//添加文本
			private void addtext(String content,String color,int size){
				TextView tView = new TextView(activity);
				tView.setText(content);
				tView.setTextColor(Color.parseColor(color));
				tView.setTextSize(size);
				l.addView(tView);
			}
			//添加居中文本
			private void addtexttitle(String content,String color,int size){
				TextView tView = new TextView(activity);
				tView.setText(content);
				tView.setTextColor(Color.parseColor(color));
				tView.setTextSize(size);
				tView.setGravity(Gravity.CENTER);
				l.addView(tView);
			}
			//添加拍照布局
			private ImageView addview_picture(){
				final ImageView btn_picture = new ImageButton(activity);
				btn_picture.setImageResource(R.drawable.mario);
				btn_picture.setBackgroundColor(Color.parseColor("#FFFFFF"));
				//设置图片大小
//				btn_capture.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)); 
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400,  
		                400);  
				btn_picture.setLayoutParams(params); 
		       
		        l.addView(btn_picture);
				
				
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
//			public boolean dispatchTouchEvent(MotionEvent ev) {  
//		        if (ev.getAction() == MotionEvent.ACTION_DOWN) {  
//		            View v = activity.getCurrentFocus();  
//		            if (isShouldHideInput(v, ev)) {  
//		                if(hideInputMethod(activity, v)) {  
//		                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件  
//		                }  
//		            }  
//		        }  
//		        return   
//		    }  

			private Runnable connectNet = new Runnable(){  
		        @Override  
		        public void run() {  
		            try {  
		            	 bitmap = BitmapFactory.decodeStream(getImageStream(filepath));
		            	 Log.v("tomorrow", "whaoithposin;");
		            	 connectHanlder.sendEmptyMessage(0);  
			                Log.d("comlayout", "set image ...");  
			            } catch (Exception e) {  
//			                Toast.makeText(SearchListActivity.this,"无法链接网络！", 1).show();  
			                e.printStackTrace();  
			            }  
			  
			        }  
			  
			    };  
			    public byte[] getImage(String path) throws Exception{  
			        URL url = new URL(path);  
			        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
			        conn.setConnectTimeout(5 * 1000);  
			        conn.setRequestMethod("GET");  
//			        conn.setRequestMethod("POST");
			        InputStream inStream = conn.getInputStream();  
			        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
			            return readStream(inStream);  
			        }  
			        return null;  
			    }  
			    public InputStream getImageStream(String path) throws Exception{  
			        URL url = new URL(path);  
			        HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
			        conn.setConnectTimeout(5*1000);  
			        conn.setRequestMethod("GET"); 
//			        conn.setRequestMethod("POST");
			        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
			            return conn.getInputStream();  
			        }else{
			        	Toast.makeText(activity, "暂无图片！", Toast.LENGTH_LONG).show();
			        }
			        return null;  
			    }  
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
			            Log.d("comlayout", "display image");  
			            // 更新UI，显示图片  
			            if (bitmap != null) {  
			            	picture.setImageBitmap(bitmap);
			            	Log.d("fanzhengqowoihegoiaw", "jishiyaowogennizaihaogeshinian"); 
			            }else{
			            	picture.setBackgroundResource(R.drawable.buffering);
			            }
			        }  
			    };  
		            
}
