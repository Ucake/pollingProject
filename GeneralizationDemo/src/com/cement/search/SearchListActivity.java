package com.cement.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.cement.SQLite.SearchListDBHelper;
import com.cement.data.MapJson;
import com.cement.thread.DetailThread;
import com.cement.thread.MapThread;
import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.MapActivity;
import com.example.generalizationdemo.R;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchListActivity extends ListActivity {
	public static String time;
	public static String worker;
	public static String house;
	private Cursor cursor;
	private Cursor cursor_search;
	public static String finish_time;
	public static String team;
	public static String address;
	private String mission_number;
	public static String mission_id;
	public static String worker_phone;
	public static String mission_condition;
	private String condition;
	private String condition1;
	private String mission_name;
	
	private String mFileName;
	public static Bitmap bitmap_search2;
	public static Bitmap bitmap_search3;
	private final static String TAG = "HUANLEJINXIAO";
	
    private List<Map<String, Object>> mData;
	public static ProgressDialog pd_search;
	public static SearchListDBHelper searchdbhelper;
	
	private Handler handler_search_detail;
     
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        searchdbhelper = new SearchListDBHelper(this, "search4", 1);
        
	mData = getData();
    MyAdapter adapter = new MyAdapter(this);
    setListAdapter(adapter);
}

private List<Map<String, Object>> getData() {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

//    Map<String, Object> map = new HashMap<String, Object>();
//    map.put("time", "2017年9月1日");
//    map.put("worker", "王小量");
//    map.put("mission_id", "任务单号：A11230");
//    map.put("image", R.drawable.task_128);
//    list.add(map);
//
//    map = new HashMap<String, Object>();
//    map.put("time", "2017年9月2日");
//    map.put("mission_id", "任务单号：B19730");
//    map.put("worker", "李小南");
//    map.put("image", R.drawable.task_128);
//    list.add(map);
//
//    map = new HashMap<String, Object>();
//    map.put("time", "2017年9月3日");
//    map.put("mission_id", "任务单号：B11350");
//    map.put("worker", "刘小婷");
//    map.put("image", R.drawable.task_128);
//    list.add(map);
     
    cursor = searchdbhelper.getReadableDatabase().rawQuery("select * from search where state =?", new String[]{"00"});
    while (cursor.moveToNext()) {
    	finish_time =  cursor.getString(cursor.getColumnIndex("finish_time"));
    	worker =  cursor.getString(cursor.getColumnIndex("worker"));
    	
    	Log.v("OOOOOOOOOOOOOOOOOO", finish_time+worker);
    	mission_condition = cursor.getString(cursor.getColumnIndex("mission_condition"));
    	mission_id = cursor.getString(cursor.getColumnIndex("mission_id"));
    	mission_name = cursor.getString(cursor.getColumnIndex("mission_name"));
    	
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("img", R.drawable.task_128);
		map.put("finish_time", finish_time);   		//房屋ID
		map.put("worker", worker); 		//房屋地址
		map.put("mission_id",mission_id);
		map.put("mission_name",mission_name);
		map.put("mission_condition", mission_condition);
		
		//娣诲姞鏁版嵁
		list.add(map);
	
	}   	
    
    ContentValues cv = new ContentValues();
	cv.put("state", "11");
	searchdbhelper.getReadableDatabase().update("search", cv, "taskNum!=0", null);
    
    return list;
    
}

// ListView 中某项被选中后的逻辑
@Override
protected void onListItemClick(ListView l, View v, int position, long id) {
//	time = (String)mData.get(position).get("title");
//	worker = (String)mData.get(position).get("info");
    Log.v("MyListView4-click", (String)mData.get(position).get("time"));
}
 
/**
 * listview中点击按键弹出对话框
 */
public void showInfo1(){
	
	pd_search = new ProgressDialog(SearchListActivity.this);
	   pd_search.setTitle("查询");
	   pd_search.setIndeterminate(false);
	   pd_search.setCancelable(false);
	   pd_search.setMessage("正在查询，请耐心等待");
	   pd_search.show();
	   
	   handler_search_detail = new Handler(){
			
			public void handleMessage(Message msg){
				super.handleMessage(msg);
				if (msg.what == 12138) {
					
				String backMsg = msg.obj.toString();
				
				Log.v("sanciwoshou", backMsg);
				try{
					
					JSONObject toor = new JSONObject(backMsg);
//					JSONObject result1 = new JSONObject(toor.getString("result"));
					String result = toor.getString("result");
					if(result.equals("10000")){
						
//						MapJson mapjson = new MapJson();
//						mapjson.mapsearch(backMsg);
						pd_search.dismiss();
						Intent intent = new Intent();
						intent.setClass(SearchListActivity.this, SearchActivity.class);
						intent.putExtra("backMsg", backMsg);
						intent.putExtra("condition", condition1);
						intent.putExtra("mission_id", mission_id);
						startActivity(intent);
						
					}else if(result.equals("10001")){
						pd_search.dismiss();
						Toast.makeText(getApplicationContext(), "请求有误！请稍后再试", Toast.LENGTH_SHORT).show();
					}
				
				} catch (JSONException e) {
			        e.printStackTrace();
			    
				}
		
		
				}
			}
		};
		
		String value = "MissionId="+mission_id;
		DetailThread thread = new DetailThread(handler_search_detail, value);
		new Thread(thread).start();
	
//	Intent intent = new Intent();
//	intent.setClass(SearchListActivity.this, SearchActivity.class);
//	startActivity(intent);
     
}
 
public final class ViewHolder{
    public ImageView image;
    public TextView time;
    public TextView worker;
    public TextView mission_id;
    public Button search;
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
             
            convertView = mInflater.inflate(R.layout.search_list, null);
            holder.image = (ImageView)convertView.findViewById(R.id.image_search);
            holder.time = (TextView)convertView.findViewById(R.id.time_search);
            holder.mission_id = (TextView)convertView.findViewById(R.id.mission_id_search);
            holder.worker = (TextView)convertView.findViewById(R.id.worker_search);
            holder.search = (Button)convertView.findViewById(R.id.search);
            convertView.setTag(holder);
             
        }else {
             
            holder = (ViewHolder)convertView.getTag();
        }
        
        condition = (String)mData.get(position).get("mission_condition");
        
        
        if(condition.equals("1")){
        	mission_condition = "任务下发";
        }else if(condition.equals("2")){
        	mission_condition = "任务被拒收";
        	holder.search.setText("处理");
        }else if(condition.equals("3")){
        	mission_condition = "任务接收";
        	holder.search.setText("查看");
        }else if(condition.equals("4")){
        	mission_condition = "任务执行中";
        }else if(condition.equals("5")){
        	mission_condition = "任务完成";
        }else if(condition.equals("6")){
        	mission_condition = "审核通过";
        }else if(condition.equals("7")){
        	mission_condition = "审核未通过";
        }else if(condition.equals("8")){
        	mission_condition = "拒收已处理";
        }
        String time1 =  (String)mData.get(position).get("finish_time");
        if(time1.equals("0") || time1.equals("")){
        	time1 = "-";
        }
        
        holder.image.setBackgroundResource((Integer)mData.get(position).get("img"));
        holder.time.setText(time1);
//        
        holder.mission_id.setText("任务号:"+(String)mData.get(position).get("mission_id")+"\n任务名称:"+(String)mData.get(position).get("mission_name")+"\n任务状态:"+mission_condition);
        holder.worker.setText("巡检人员:"+(String)mData.get(position).get("worker"));
        
        holder.search.setOnClickListener(new View.OnClickListener() {
             
            @Override
            public void onClick(View v) {
               
            	   time = (String)mData.get(position).get("time");
            	   worker = (String)mData.get(position).get("worker");
            	   mission_id = (String)mData.get(position).get("mission_id");
            	   condition1 = (String)mData.get(position).get("mission_condition");
            	   
//            	   pd_search = new ProgressDialog(SearchListActivity.this);
//            	   pd_search.setTitle("查询");
//            	   pd_search.setIndeterminate(false);
//            	   pd_search.setCancelable(false);
//            	   pd_search.setMessage("正在查询，请耐心等待");
//            	   pd_search.show();
            	   showInfo1();
            }
        });
         
        return convertView;
    }
}
}
