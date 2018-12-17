package com.cement.search;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cement.check.CheckActivity;
import com.example.generalizationdemo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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

public class SearchActivity extends Activity {
	
	private LinearLayout linearlayout_search;
	private JSONObject test;
	
	private Button[] btn_picture = new Button[10000];
	private ProgressDialog pd;
	
	private String condition;
	private String mission_id;
	private String pic_event_id;
	private String pic_work_name;
	private Bitmap bitmap;
	private ImageView picture;
	private final String TAG  = "weiyizhanglianqulianyishenshang";
	
	private String filepath = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&mission_id="+mission_id+"&event_id="+pic_event_id+"&work_name="+pic_work_name;
	int count = 0;
	
	@Override
	   protected void onCreate(Bundle savedInstanceState) {
	       super.onCreate(savedInstanceState);
	       setContentView(R.layout.search);
	       
	       linearlayout_search = (LinearLayout) findViewById(R.id.linearlayout_search);
	       try {
//				InputStreamReader isr = new InputStreamReader(getAssets().open(
//						"search.json"), "UTF-8");
//				BufferedReader br = new BufferedReader(isr);
//				String line;
//				StringBuilder builder = new StringBuilder();
//				while ((line = br.readLine()) != null) {
//					builder.append(line);
//				}
//				br.close();
//				isr.close();
//				Log.v("search", builder.toString());

	    	   condition = getIntent().getStringExtra("condition");
	    	   String backMsg = getIntent().getStringExtra("backMsg");
	    	   mission_id = getIntent().getStringExtra("mission_id");
	    	   
	    	   Log.v("detail", backMsg);
	    	   
				test = new JSONObject(backMsg);
				
				//第一层先拿出data这个jsonobject
				JSONObject data = test.getJSONObject("data");
				// 添加任务号
				addtexttitle("任务：" + mission_id,
						data.getString("font_color"), data.getInt("font_size"));
				
				//解析"审核结果"这一数组
				JSONArray audit = data.getJSONArray("auditor");
//				for (int i = 0; i < audit.length(); i++) {
				int x = 0;
					JSONObject auditor = audit.getJSONObject(x);
				//审核人
				String mission_auditor = auditor.getString("auditor");
				//审核结果
				String mission_auditor_opinion = auditor.getString("auditor_opinion");
				//审核时间
				String mission_auditor_time = auditor.getString("auditor_time");
//				}
				
				Log.v("wojianjiandiziwocuimian", condition);
				if(condition.equals("7")||condition.equals("6")){
					addtext("审核人："+mission_auditor,"#6699FF",20);
					addtext("审核意见："+mission_auditor_opinion,"#6699FF",20);
					addtext("审核时间："+mission_auditor_time,"#6699FF",20);
				}
				
				// 添加任务说明20180409
				JSONArray note_array = data.getJSONArray("note");
				for (int i = 0; i < note_array.length(); i++) {
					JSONObject note = note_array.getJSONObject(i);
					String note_name = note.getString("note_name");
					String note_content = note.getString("note_content");
					addtext(note_name + ":" + note_content,
							note.getString("font_color"), note.getInt("font_size"));
				
				}
				
				
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
				/*******************0509到时候乱码好了******************8
				/************************************************/	
				//添加事件内容
				JSONArray event_array = data.getJSONArray("event");
				for (int j = 0; j < event_array.length(); j++) {
					JSONObject event = event_array.getJSONObject(j);
					String event_id = event.getString("event_ID");
					String event_name = event.getString("event_name");
					addtexttitle(event_id + event_name, "#6699FF", 25);
				
//				for (int j = 0; j < event2.length(); j++) {
//					JSONObject event = event2.getJSONObject(j);
//					String event_id = event.getString("event_ID");
////					String event_name = event.getString("event_name");
//					addtexttitle(event_id, "#080808", 30);
					JSONArray workcontent = event.getJSONArray("workcontent");
					for (int k = 0; k < workcontent.length(); k++) {
						JSONObject work = workcontent.getJSONObject(k);
						
//						String work_id = work.getString("work_ID");
						String work_name = work.getString("work_name");
						addtexttitle(work_name, "#080808", 20);
						
					JSONArray view_content = work.getJSONArray("view_content");
					for (int m = 0; m < view_content.length(); m++) {
						JSONObject view = view_content.getJSONObject(m);
						String view_class = view.getString("view_class");
						String view_name = view.getString("view_name");
//						addtext(view_name, "#080808", 20);
						if(view_class.equals("拍照")){
//							addtext(view_class+":"+view_name, "#080808", 20);
//							addbutton(event_id, work_name);
							addtext(view_name, "#080808", 20);
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
//									filepath = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&mission_id=20&event_id=9&work_name=%E7%94%9F%E4%BA%A7%E7%BA%BF%E6%98%AF%E5%90%A6%E5%90%AF%E5%8A%A8";
									Log.v("yiyangdeyueguang", filepath);
									new Thread(connectNet).start();
									
									pd = new ProgressDialog(SearchActivity.this);
					            	pd.setTitle("图片");
					            	pd.setIndeterminate(false);
					            	pd.setCancelable(false);
					            	pd.setMessage("正在下载，请耐心等待");
					            	pd.show();
									
									
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
//				addbutton("拍照", "除氧器");
				}catch (JSONException e) {
					e.printStackTrace();
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
	//添加查看图片按钮
			private Button addbutton(String event_id, String work_name){
				pic_event_id = event_id;
				pic_work_name = work_name;
				Button btn = new Button(this);
				btn.setText("查看照片");
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.WRAP_CONTENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				params.gravity = Gravity.LEFT;
				btn.setLayoutParams(params);
				btn.setBackgroundColor(Color.parseColor("#29b6f6"));
				
				btn.setTag(R.id.tag_count, count);
				btn.setTag(R.id.tag_event_id, event_id);
				btn.setTag(R.id.tag_work_name, work_name);
//				btn.setOnClickListener(new OnClickListener() {
//					
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						
//						new Thread(connectNet).start();
//						LinearLayout loginDialog = (LinearLayout)LayoutInflater.from(SearchActivity.this).inflate(R.layout.alertdialog_layout, null);
//						picture = (ImageView) loginDialog.findViewById(R.id.picture);
//						new AlertDialog.Builder(SearchActivity.this)
//				        .setTitle("照片")
//				        .setMessage("")
//				        .setView(loginDialog)
//				        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
//				            @Override
//				            public void onClick(DialogInterface dialog, int which) {
////				            	picture.setImageBitmap(bitmap);
//				            	
//				            }
//				        
//				        })
//				       
//				        .show();
//					}
//				});
				linearlayout_search.addView(btn);
				return btn;
			}
	
	
	//添加文本
			private void addtext(String content,String color,int size){
				TextView tView = new TextView(this);
				tView.setText(content);
				tView.setTextColor(Color.parseColor(color));
				tView.setTextSize(size);
				linearlayout_search.addView(tView);
			}
			//添加居中文本
			private void addtexttitle(String content,String color,int size){
				TextView tView = new TextView(this);
				tView.setText(content);
				tView.setTextColor(Color.parseColor(color));
				tView.setTextSize(size);
				tView.setGravity(Gravity.CENTER);
				linearlayout_search.addView(tView);
			}
			
			private ImageView addview_picture(){
				final ImageView btn_picture = new ImageButton(this);
				btn_picture.setImageResource(R.drawable.mario);
				btn_picture.setBackgroundColor(Color.parseColor("#FFFFFF"));
				//设置图片大小
//				btn_capture.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)); 
//				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400,  
//		                400);  
//				btn_picture.setLayoutParams(params); 
		       
		        linearlayout_search.addView(btn_picture);
				
				
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
			
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//					Intent intent1 = new Intent(CheckActivity.this,CheckListActivity.class);
//					startActivity(intent1);
				}
				return super.onKeyDown(keyCode, event);
			}
			
			private Runnable connectNet = new Runnable(){  
		        @Override  
		        public void run() {  
		            try {  
//		            	String filepath = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&mission_id=1&event_id=1&work_name=B2";
//		                String filePath2 = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&pic_class=2&mission_id="+mission_id;
//		                String filePath3 = "http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&pic_class=3&mission_id="+mission_id;
//		                http://img.my.csdn.net/uploads/201402/24/1393242467_3999.jpg
//		                http://59.110.157.159:8080/water/imageDownload.req?action=filedownload&mission_id=19930610&pic_class=1
//		                mFileName = "xyyzz.jpg";

		                //以下是取得图片的两种方法  
		                //////////////// 方法1：取得的是byte数组, 从byte数组生成bitmap  
//		                byte[] data2 = getImage(filePath2);  
//		                byte[] data3 = getImage(filePath3);
//		                if(data2!=null){  
//		                	bitmap_check2 = BitmapFactory.decodeByteArray(data2, 0, data2.length);// bitmap  
//		                	bitmap_check3 = BitmapFactory.decodeByteArray(data3, 0, data3.length);
//		                	
//		                	
//		                	bitmap_check2 = BitmapFactory.decodeStream(getImageStream(filePath2));
//		                	
//		                	Intent intent = new Intent();
//		                	intent.setClass(CheckListActivity.this, CheckActivity.class);
//		                	startActivity(intent);
//		                    
//		                }else{  
//		                    Toast.makeText(CheckListActivity.this, "Image error!", 1).show();  
//		                }  
		                ////////////////////////////////////////////////////////  
		  
		                //******** 方法2：取得的是InputStream，直接从InputStream生成bitmap ***********/
		                
		                bitmap = BitmapFactory.decodeStream(getImageStream(filepath));
//		                bitmap_check2 = BitmapFactory.decodeStream(getImageStream(filePath2));
//		                bitmap_check3 = BitmapFactory.decodeStream(getImageStream(filePath3));
//		                picture.setImageBitmap(bitmap);
		                Log.v("tomorrow", "whaoithposin;");
		                
		               
		                //********************************************************************/  
		  
		                // 发送消息，通知handler在主线程中更新UI  
		                connectHanlder.sendEmptyMessage(0);  
		                Log.d(TAG, "set image ...");  
		            } catch (Exception e) {  
//		                Toast.makeText(SearchListActivity.this,"无法链接网络！", 1).show();  
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
//		        conn.setRequestMethod("POST");
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
//		        conn.setRequestMethod("POST");
		        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){  
		            return conn.getInputStream();  
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
		            	LinearLayout loginDialog = (LinearLayout)LayoutInflater.from(SearchActivity.this).inflate(R.layout.alertdialog_layout, null);
						picture = (ImageView) loginDialog.findViewById(R.id.picture);
						new AlertDialog.Builder(SearchActivity.this)
				        .setTitle("照片")
				        .setMessage("")
				        .setView(loginDialog)
				        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
				            @Override
				            public void onClick(DialogInterface dialog, int which) {
//				            	picture.setImageBitmap(bitmap);
				            	
				            }
				        
				        })
				       
				        .show();
		            	picture.setImageBitmap(bitmap);
		            	Log.d("fanzhengqowoihegoiaw", "jishiyaowogennizaihaogeshinian"); 
		            }else{
		            	pd.dismiss();
		            	picture.setBackgroundResource(R.drawable.buffering);
		            }  
		        }  
		    };
			
			
}

