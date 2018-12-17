package com.cement.abnormalmanagement;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import com.cement.SQLite.AbnormalDBHelper;
import com.cement.search.SearchActivity;
import com.example.generalizationdemo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class AbnormalDetailActivity extends Activity {
	
	public static AbnormalDBHelper abnormaldbhelper;
	
	private EditText abnormal_time;//上报时间
	private EditText abnormal_worker;//上报人
	private EditText abnormal_workshop;//所属车间
//	private EditText abnormal_region;//所属巡检区域
	private EditText abnormal_point;//所属巡检点
//	private EditText abnormal_event;//异常巡检事件
//	private EditText abnormal_position;//异常具体位置
//	private EditText abnormal_comment;//异常工作内容
	private EditText abnormal_level;//异常级别
	private EditText abnormal_description;//异常描述
	private EditText abnormal_measures;//建议措施
	private String task_id;
	
	private Button assign_abnormal;
	private Cursor cursor;
	private String abnormal_id;
	public static ImageView abnormal_pic;
	private Bitmap bitmap;
	private Button presstopicture;
	private ImageView picture;
	private SharedPreferences preferences;
	
	private String TAG = "niganbuganwushiwozhibuzhudeyanlei";
	public static JSONObject json;//这个是破芸用于生成维修任务的json
	private String factory_id;
	public static JSONObject peoJSON;
	private String workshop_id;
	private String level;
	private String status;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abnormal_detail);
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        factory_id = preferences.getString("factory_id", null);
        workshop_id = preferences.getString("workshop_id", null);
        status = preferences.getString("status", null);
        
        presstopicture = (Button) findViewById(R.id.presstopicture);
        
//        abnormal_pic = (ImageView) findViewById(R.id.abnormal_PIC);
        abnormaldbhelper = new AbnormalDBHelper(this, "abnormal7", 1);
        abnormal_id = getIntent().getStringExtra("abnormal_id");
        
        abnormal_time = (EditText) findViewById(R.id.abnormal_time);
        abnormal_worker = (EditText) findViewById(R.id.abnormal_worker);
        abnormal_workshop = (EditText) findViewById(R.id.abnormal_workshop);
//        abnormal_region = (EditText) findViewById(R.id.abnormal_region);
        abnormal_point = (EditText) findViewById(R.id.abnormal_point);
//        abnormal_event = (EditText) findViewById(R.id.abnormal_event);
//        abnormal_position = (EditText) findViewById(R.id.abnormal_position);
//        abnormal_comment = (EditText) findViewById(R.id.abnormal_comment);
        abnormal_level = (EditText) findViewById(R.id.abnormal_level);
        abnormal_description = (EditText) findViewById(R.id.abnormal_description);
        abnormal_measures = (EditText) findViewById(R.id.abnormal_measures);
        
        assign_abnormal = (Button) findViewById(R.id.assign_abnormal);
        assign_abnormal.setVisibility(View.INVISIBLE);
        
        cursor = abnormaldbhelper.getReadableDatabase().rawQuery("select * from abnormal where abnormal_id=?", new String[]{abnormal_id});
        while (cursor.moveToNext()) {
        
        	abnormal_time.setText(cursor.getString(cursor.getColumnIndex("abnormal_time")));
        	abnormal_worker.setText(cursor.getString(cursor.getColumnIndex("abnormal_person")));
        	abnormal_workshop.setText(cursor.getString(cursor.getColumnIndex("workshop")));
//       	abnormal_region.setText("西门");
        	abnormal_point.setText(cursor.getString(cursor.getColumnIndex("abnormal_check_point")));
//        	abnormal_event.setText("除氧器");
//        	abnormal_position.setText("第6个焊接处");
//        	abnormal_comment.setText("无法检查设备");
        	level = cursor.getString(cursor.getColumnIndex("abnormal_level"));
        	
        	abnormal_measures.setText(cursor.getString(cursor.getColumnIndex("suggestion")));
        	
        	if(level.equals("1")){
        		level = "问题型";
        	}else if(level.equals("2")){
        		level = "隐患型";
        	}else{
        		level = "报警型";
        	}
        	abnormal_level.setText(level);
        	abnormal_description.setText(cursor.getString(cursor.getColumnIndex("description")));
        	task_id = cursor.getString(cursor.getColumnIndex("task_id"));
        }
        
       
        
        presstopicture.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new Thread(connectNet).start();
				LinearLayout loginDialog = (LinearLayout)LayoutInflater.from(AbnormalDetailActivity.this).inflate(R.layout.alertdialog_layout, null);
				picture = (ImageView) loginDialog.findViewById(R.id.picture);
				new AlertDialog.Builder(AbnormalDetailActivity.this)
		        .setTitle("照片")
		        .setMessage("")
		        .setView(loginDialog)
		        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
		            @Override
		            public void onClick(DialogInterface dialog, int which) {
//		            	picture.setImageBitmap(bitmap);
		            	
		            }
		        
		        })
		       
		        .show();
			}
		});
        
        assign_abnormal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				final String sendtaskTime = format.format(date);
				final Thread t1 = new Thread(new Runnable() {					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						OutputStream out = null;
						BufferedReader reader = null;
						try {
							URL url = new URL("http://123.206.16.157:8080/water/arrange.req?action=arrangelist");
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("POST");
							conn.setDoOutput(true);
							conn.setDoInput(true);
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							out = conn.getOutputStream();
							out.write(("taskcreate="+"任务生成"+"&tasktime="+sendtaskTime+"&taskdate="+"mo_1"
									+"&factory_id="+factory_id
									+"&workshop_id="+workshop_id).getBytes());
							reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							String line;
							StringBuilder builder = new StringBuilder();
							while((line = reader.readLine())!=null){
								builder.append(line);
							}
							System.out.println("要人员时返回的人员"+builder.toString());
							peoJSON  = new JSONObject(builder.toString());
//							JSONArray data_array = json.getJSONArray("data");
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						t1.start();
						String urlStr = "http://123.206.16.157:8080/water/mission.req?action=exceptionInfo";
//						runContent(urlStr,"taskcreate=任务生成"+"&tasknature="+s[which] , true, null,String.valueOf(count + 1) , 1);
						OutputStream out = null;
						BufferedReader reader = null;
						try {
//							URL url = new URL("http://59.110.157.159:8080/water/mission.req?action=setMission");
							URL url = new URL(urlStr);
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("POST");
							conn.setDoOutput(true);
							conn.setDoInput(true);
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							out = conn.getOutputStream();

							out.write(("id="+abnormal_id).getBytes());
							reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							String line;
							StringBuilder builder = new StringBuilder();
							while((line = reader.readLine())!=null){
								builder.append(line);
							}
							
							json = new JSONObject(builder.toString());
								Intent intent = new Intent(AbnormalDetailActivity.this, com.example.taskeditor.taskeditor.MaintainActivity.class);	 
								intent.putExtra("abnormalId",abnormal_id);
								startActivityForResult(intent, 1);
							
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
        
	}
	private Runnable connectNet = new Runnable(){  
        @Override  
        public void run() {  
            try {  
            	String filepath = "http://123.206.16.157:8080/water/imageDownload.req?action=exceptiondownload&id="+abnormal_id;
//            	http://img.my.csdn.net/uploads/201402/24/1393242467_3999.jpg
//            	http://123.206.16.157:8080/water/imageDownload.req?action=filedownload&abnormal_id="+abnormal_id
                //以下是取得图片的两种方法  
                //////////////// 方法1：取得的是byte数组, 从byte数组生成bitmap  
//                byte[] data2 = getImage(filePath2);  
//                byte[] data3 = getImage(filePath3);
//                if(data2!=null){  
//                	bitmap_check2 = BitmapFactory.decodeByteArray(data2, 0, data2.length);// bitmap  
//                	bitmap_check3 = BitmapFactory.decodeByteArray(data3, 0, data3.length);

//                	bitmap_check2 = BitmapFactory.decodeStream(getImageStream(filePath2));                    
//                }else{  
//                    Toast.makeText(CheckListActivity.this, "Image error!", 1).show();  
//                }  
                ////////////////////////////////////////////////////////  
  
                //******** 方法2：取得的是InputStream，直接从InputStream生成bitmap ***********/
                
                bitmap = BitmapFactory.decodeStream(getImageStream(filepath));
//                bitmap_check2 = BitmapFactory.decodeStream(getImageStream(filePath2));
//                bitmap_check3 = BitmapFactory.decodeStream(getImageStream(filePath3));
//                picture.setImageBitmap(bitmap);
                Log.v("tomorrow", "quandoushiwodebudui;");
                
               
                //********************************************************************/  
  
                // 发送消息，通知handler在主线程中更新UI  
                connectHanlder.sendEmptyMessage(0);
                
                Log.d(TAG, "set image ...");  
            } catch (Exception e) {  
//                Toast.makeText(SearchListActivity.this,"无法链接网络！", 1).show();  
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
//        conn.setRequestMethod("POST");
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
//        conn.setRequestMethod("POST");
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
            	picture.setImageBitmap(bitmap);
            	
            } else{
            	picture.setBackgroundResource(R.drawable.buffering);
            }  
            
        }  
    };   

}
