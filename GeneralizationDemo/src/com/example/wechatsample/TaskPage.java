package com.example.wechatsample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONObject;
import com.example.adpter.MyAdapterTask;
import com.example.adpter.TaskAdapter;
import com.example.aql.People_DBHelper;
import com.example.bean.Person;
import com.example.bean.Task;
import com.example.bean.TaskTask;
import com.example.generalizationdemo.R;
import com.example.thread.PeopleStartThread;
import com.example.thread.TaskStartThread;
import com.example.util.AlertDialogUse;
import com.example.util.CountTime;
import com.example.util.Data;
import com.example.util.DateTimePickDialogUtil;
import com.example.util.JSONStart;

import com.example.util.ShowTextview;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;

/**
 * 发现Fragment的界面
 * 
 * http://blog.csdn.net/guolin_blog/article/details/26365683
 * 
 * @author guolin
 */
public class TaskPage extends Fragment   {
	


	TextView inputdateF;
	 TextView inputweekF;
	 DateTimePickDialogUtil dateTimePickDialogUtil;
	
	 public  String factory_id = com.example.generalizationdemo.HomePageya.factory_id;
	 
	 TextView mo_1;
	 TextView mo_2;
	 TextView mo_3;
	 TextView mo_4;
	 TextView mo_5;
	 TextView mo_6;
	 TextView mo_7;
	 TextView af_1;
	 TextView af_2;
	 TextView af_3;
	 TextView af_4;
	 TextView af_5;
	 TextView af_6;
	 TextView af_7;
	 TextView publicText;
	ShowTextview showTextview;
	
	AlertDialog alertDialog_task;
	ArrayList<TaskTask> list;
	
	
	public static People_DBHelper dbHelperTask;
	SQLiteDatabase dbTask;
	ContentValues values;
	
	
	Handler handlerStart;
	CountTime countTime;
	
//	Cursor cursor;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.arrange_main, container, false);
//		Toast.makeText(getActivity(), "这是任务维度", Toast.LENGTH_SHORT).show();
		
		//关于时间的部分
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	final Date curDate = new Date(System.currentTimeMillis());
		
		
		inputdateF = (TextView) view.findViewById(R.id.inputdate);
		inputweekF = (TextView) view.findViewById(R.id.inputweek);
		inputdateF.setText(formatter.format(curDate));
		inputweekF.setText("第"+CountTime.inputWeek(inputdateF)+"周");
		
		
		dbHelperTask = new People_DBHelper(getActivity(), "task", null, 5);
		dbTask = dbHelperTask.getWritableDatabase();
		values = new ContentValues();
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectNetwork()
        .penaltyLog()
        .build());
		//一开始导入数据
//		handlerStart = new Handler(){
//			public void handleMessage(Message msg){
//				if(msg.what == 12138){
//					String backMsg = msg.obj.toString();
//					Log.e("startDate", backMsg);
//					if(!backMsg.equals(null)){
//						JSONStart jsonStart = new JSONStart();
//						jsonStart.taskArrange(backMsg);
//					}
//				}
//			}
//		};
		
		countTime = new CountTime(inputdateF.getText().toString());
//		String value ="time1=" +"2018-05-28"+ "&time2="+ "2018-06-03";
		String value ="time1=" +countTime.getBeginTime()+ "&time2="+ countTime.getEndTime()
				+"&factory_id="+factory_id;
		TaskStartThread thread = new TaskStartThread(value);
		thread.run();
		mo_1 = (TextView) view.findViewById(R.id.mo_1);
		mo_2= (TextView) view.findViewById(R.id.mo_2);
		mo_3 = (TextView) view.findViewById(R.id.mo_3);
		mo_4 = (TextView) view.findViewById(R.id.mo_4);
		mo_5 = (TextView) view.findViewById(R.id.mo_5);
		mo_6 = (TextView) view.findViewById(R.id.mo_6);
		mo_7 = (TextView) view.findViewById(R.id.mo_7);
		af_1 = (TextView) view.findViewById(R.id.af_1);
		af_2 = (TextView) view.findViewById(R.id.af_2);
		af_3 = (TextView) view.findViewById(R.id.af_3);
		af_4 = (TextView) view.findViewById(R.id.af_4);
		af_5 = (TextView) view.findViewById(R.id.af_5);
		af_6 = (TextView) view.findViewById(R.id.af_6);
		af_7 = (TextView) view.findViewById(R.id.af_7);
		showTextview = new ShowTextview(mo_1, mo_2, mo_3, mo_4, mo_5, mo_6, mo_7, af_1, af_2, af_3, af_4, af_5, af_6, af_7, dbTask, 1);
		showTextview.showTextTask();
//		new Thread(thread);
	inputdateF.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dateTimePickDialogUtil = new DateTimePickDialogUtil(getActivity(), inputdateF.getText().toString(),"taskPage",dbHelperTask,showTextview);
					dateTimePickDialogUtil.dateTimePicKDialog(inputdateF,inputweekF);
					
				}
			} );
//		values.put("name", "任务1");
//		values.put("mission_condition", 1);
//		values.put("time", "mo_1");
//		dbTask.insert("task", null, values);
//		values.clear();
//		values.put("name", "任务2");
//		values.put("mission_condition", 0);
//		values.put("time", "mo_1");
//		dbTask.insert("task", null, values);
		
		list = new ArrayList<TaskTask>();
		mo_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_1 ;
				list = Data.initData("mo_1", dbTask);
				if(list.size() == 0)
				Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
				else
				actionAlertDialog(list,"mo_1");

			}

		});
		mo_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_2;
				list = Data.initData("mo_2", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
					
				actionAlertDialog(list,"mo_2");

			}
		});
		mo_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_3;
				list = Data.initData("mo_3", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"mo_3");

			}
		});
		mo_4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_4;
				list = Data.initData("mo_4", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"mo_4");

			}
		});
		mo_5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_5;
				list = Data.initData("mo_5", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"mo_5");

			}
		});
		mo_6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_6;
				list = Data.initData("mo_6", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"mo_6");

			}
		});
		mo_7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_7;
				list = Data.initData("mo_7", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"mo_7");

			}
		});
		af_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_1;
				list = Data.initData("af_1", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"af_1");

			}
		});
		af_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_2;
				list = Data.initData("af_2", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"af_2");

			}
		});
		af_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_3;
				list = Data.initData("af_3", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"af_3");

			}
		});
		af_4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_4;
				list = Data.initData("af_4", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"af_4");

			}
		});
		af_5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_5;
				list = Data.initData("af_5", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"af_5");

			}
		});
		af_6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_6;
				list = Data.initData("af_6", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"af_6");

			}
		});
		af_7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_7;
				list = Data.initData("af_7", dbTask);
				if(list.size() == 0)
					Toast.makeText(getActivity(), "无任务", Toast.LENGTH_SHORT).show();
					else
				actionAlertDialog(list,"af_7");

			}
		});
		
		

		return view;
		
		
		
	}


	protected  void actionAlertDialog(final ArrayList<TaskTask> list_task,final String s) {
		
		
		
//		for(int i = 0; i < list_task.size(); i ++){
//			Log.e("222222", list_task.get(i).name);
//		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("任务列表");
		View layout = LayoutInflater.from(getActivity()).inflate(
				R.layout.all_listview, null);
		ListView myListView_task = (ListView) layout.findViewById(R.id.all_listview);
		MyAdapterTask adapter = new MyAdapterTask(getActivity(), list_task);
		myListView_task.setAdapter(adapter);

		myListView_task.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int arg2,
					long arg3) {
				// TODO Auto-generated method stub


//				new Thread(new Runnable() {
					
//					@Override
//					public void run() {
						// TODO Auto-generated method stub

						String sendS = "MissionId="+list_task.get(arg2).getMission_id();
//						String sendS = "MissionId="+"39";
						OutputStream out = null;
						BufferedReader reader = null;
						String backMsg = "";
						JSONObject json = null;
						try {
							 URL url = new URL("http://123.206.16.157:8080/water/mission.req?action=missiondetail");
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("POST");
							conn.setDoOutput(true);
							conn.setDoInput(true);
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							out = conn.getOutputStream();
							out.write(sendS.getBytes());
							reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							String line;
							
							while((line = reader.readLine())!=null){
								backMsg = line;
							}
							System.out.println("这是任务维度中的任务详情"+backMsg);
							json = new JSONObject(backMsg);
							System.out.println("任务的id是："+list_task.get(arg2).getMission_id()+"任务的condition是："+list_task.get(arg2).getMission_condition());
							if("10000".equals(json.getString("result"))){
								AlertDialogUse alertDialogUse = new AlertDialogUse(getActivity(), dbHelperTask);
								alertDialogUse.actionTaskDayAlertDialog(json,list_task.get(arg2).getMission_id(),s,list_task.get(arg2).getName(),inputdateF.getText().toString());
							}
								
							else if("10001".equals(json.getString("result"))){
								AlertDialog.Builder builder_event = new AlertDialog.Builder(getActivity());
								
								builder_event.setTitle("注意：");
								builder_event.setMessage("任务正在执行，请勿查看.......");
								builder_event.setNegativeButton("YES", null);
								builder_event.create().show();
							}
								
							
						}catch(Exception e){
							e.printStackTrace();
						}  
//					}
//				}).start();	

				
				
			}
		}
			
		);
		
		
		builder.setView(layout);
		builder.setPositiveButton("返回", null);
//		builder.setNegativeButton("取消", null);
		alertDialog_task = builder.create();
		alertDialog_task.show();
		
		
	}


	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
		dbHelperTask.deletetask();
	}

	




	


	

	

	

	
		
	}


	
	
	
	

