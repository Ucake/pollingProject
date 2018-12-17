package com.cement.abnormalmanagement;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.cement.SQLite.AbnormalDBHelper;
import com.cement.check.CheckActivity;
import com.cement.check.CheckListActivity;
import com.cement.check.CheckListActivity.MyAdapter;
import com.cement.check.CheckListActivity.ViewHolder;
import com.cement.data.AbnormalJson;
import com.cement.data.MapJson;
import com.cement.thread.AbnormalThread;
import com.cement.thread.MapThread;
import com.example.generalizationdemo.AbnormalManagementActivity;
import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.MapActivity;
import com.example.generalizationdemo.R;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class AbnormalListActivity extends ListActivity {
	
	public static String time;
	public static String abnormal_person;
	public static String house;
	private Cursor cursor;
	private Cursor cursor_search;
	public static String abnormal_time;
	public static String team;
	public static String address;
	private String mission_number;
	public static String mission_id;
	public static String worker_phone;
	private String abnormal_id;
	
	private String mFileName;
	public static Bitmap bitmap_search2;
	public static Bitmap bitmap_search3;
	private final static String TAG = "HUANLEJINXIAO";
	
    private List<Map<String, Object>> mData;
	public static ProgressDialog pd_search;
	public static AbnormalDBHelper abnormaldbhelper;
	private String abnormal_check_point;
	private String abnormal_status;
	private Handler handler_abnormal;
	private Bitmap bitmap;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        abnormaldbhelper = new AbnormalDBHelper(this, "abnormal7", 1);
        
        mData = getData();
        MyAdapter adapter = new MyAdapter(this);
        setListAdapter(adapter);
    }
 
    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
 
//        Map<String, Object> map = new HashMap<String, Object>();
//        map.put("time", "上报时间：9月1日");
//        map.put("worker", "上报人：王小量");
//        map.put("event", "所属巡检点：除氧器");
//        map.put("image", R.drawable.warning_triangle_128px);
//        list.add(map);
// 
//        map = new HashMap<String, Object>();
//        map.put("time", "上报时间：9月2日");
//        map.put("event", "所属巡检点：发电机");
//        map.put("worker", "上报人：李小南");
//        map.put("image", R.drawable.warning_triangle_128px);
//        list.add(map);
// 
//        map = new HashMap<String, Object>();
//        map.put("time", "上报时间：9月3日");
//        map.put("event", "所属巡检点：给水泵");
//        map.put("worker", "上报人：刘小婷");
//        map.put("image", R.drawable.warning_triangle_128px);
//        list.add(map);
         
        cursor = abnormaldbhelper.getReadableDatabase().rawQuery("select * from abnormal where state =?", new String[]{"00"});
        while (cursor.moveToNext()) {
        	abnormal_time =  cursor.getString(cursor.getColumnIndex("abnormal_time"));
        	abnormal_person =  cursor.getString(cursor.getColumnIndex("abnormal_person"));
        	abnormal_check_point = cursor.getString(cursor.getColumnIndex("abnormal_check_point"));
        	abnormal_id = cursor.getString(cursor.getColumnIndex("abnormal_id"));
        	abnormal_status = cursor.getString(cursor.getColumnIndex("abnormal_status"));
        	if(abnormal_status.equals("1")){
        		abnormal_status = "未处理";
        	}else{
        		abnormal_status = "已处理";
        	}
        	
        	
        	Log.v("OOOOOOOOOOOOOOOOOO", abnormal_time + abnormal_person);
//        	team = cursor.getString(cursor.getColumnIndex("team"));
//        	address = cursor.getString(cursor.getColumnIndex("address"));
//        	mission_number = cursor.getString(cursor.getColumnIndex("mission_id"));
//        	worker_phone = cursor.getString(cursor.getColumnIndex("worker_phone"));
        	

    		HashMap<String, Object> map=new HashMap<String, Object>();
    		map.put("img", R.drawable.warning_triangle_128px);
    		map.put("time", abnormal_time);   		//房屋ID
    		map.put("worker", abnormal_person); 		//房屋地址
    		map.put("point",abnormal_check_point);
    		map.put("id", abnormal_id);
    		map.put("abnormal_status", abnormal_status);
    		
    		//娣诲姞鏁版嵁
    		list.add(map);
    	
    		
    	}   	
        ContentValues cv = new ContentValues();
		cv.put("state", "11");
		abnormaldbhelper.getReadableDatabase().update("abnormal", cv, "taskNum!=0", null);
		
        return list;
        
    }
    
    // ListView 中某项被选中后的逻辑
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
//    	time = (String)mData.get(position).get("title");
//    	worker = (String)mData.get(position).get("info");
        Log.v("MyListView4-click", (String)mData.get(position).get("time"));
    }
     
    /**
     * listview中点击按键弹出对话框
     */
    public void showInfo1(){
    	
    	
    	Intent intent = new Intent();
    	intent.setClass(AbnormalListActivity.this, AbnormalDetailActivity.class);
    	intent.putExtra("abnormal_id", abnormal_id);
    	startActivity(intent);
         
    }
     
    public final class ViewHolder{
        public ImageView image;
        public TextView time;
        public TextView worker;
        public TextView point;
        public Button detail_abnormal;
    }
     
    public class MyAdapter extends BaseAdapter{
 
        private LayoutInflater mInflater;
         
         
        public MyAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mData.size();
        }
 
        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }
 
        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }
 
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
             
          ViewHolder holder = null;
            if (convertView == null) {
                 
                holder=new ViewHolder();  
                 
                convertView = mInflater.inflate(R.layout.abnormal_list, null);
                holder.image = (ImageView)convertView.findViewById(R.id.image_abnormal);
                holder.time = (TextView)convertView.findViewById(R.id.time_abnormal);
//                holder.point = (TextView)convertView.findViewById(R.id.event_abnormal);
                holder.worker = (TextView)convertView.findViewById(R.id.worker_abnormal);
                holder.detail_abnormal = (Button)convertView.findViewById(R.id.detail_abnormal);
                convertView.setTag(holder);
                 
            }else {
                 
                holder = (ViewHolder)convertView.getTag();
            }
             
            holder.image.setBackgroundResource((Integer)mData.get(position).get("img"));
            holder.time.setText((String)mData.get(position).get("time")+"\n状态:"+(String)mData.get(position).get("abnormal_status"));
//            holder.point.setText("异常巡检点："+(String)mData.get(position).get("point"));
            holder.worker.setText("异常上报人："+(String)mData.get(position).get("worker"));
            
            holder.detail_abnormal.setOnClickListener(new View.OnClickListener() {
                 
                @Override
                public void onClick(View v) {
                   
                	   time = (String)mData.get(position).get("time");
                	   abnormal_person = (String)mData.get(position).get("worker");
                	   abnormal_id = (String)mData.get(position).get("id");
                	   
//                	   pd_search = new ProgressDialog(CheckListActivity.this);
//                	   pd_search.setTitle("审核查询");
//                	   pd_search.setIndeterminate(false);
//                	   pd_search.setCancelable(false);
//                	   pd_search.setMessage("正在查询，请耐心等待");
//                	   pd_search.show();
                	   showInfo1();
                }
            });
             
            return convertView;
        }
    }
    
//    @Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//			Intent intent1 = new Intent(AbnormalListActivity.this,AbnormalManagementActivity.class);
//			startActivity(intent1);
//		}
//		return super.onKeyDown(keyCode, event);
//	}

    
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
            	AbnormalDetailActivity.abnormal_pic.setImageBitmap(bitmap);
            } else{
            	AbnormalDetailActivity.abnormal_pic.setBackgroundResource(R.drawable.buffering);
            }  
            
            
            
        }  
    };  

    
    
}
