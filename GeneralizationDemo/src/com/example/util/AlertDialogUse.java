package com.example.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.sax.StartElementListener;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import com.example.adpter.TaskAdapter;
import com.example.aql.People_DBHelper;
import com.example.bean.EventRank;

import com.example.bean.Task;
import com.example.bean.TaskTask;
import com.example.generalizationdemo.R;


/**
 * @author lson
 *���ǵ��������
 */

public class AlertDialogUse {
	
	private FragmentActivity activity;
	private SQLiteDatabase db;
	private People_DBHelper dbHelper;
	
	public static ArrayList<EventRank> list;
	
	


	public AlertDialogUse() {
		super();
		// TODO Auto-generated constructor stub
	}



	public AlertDialogUse(FragmentActivity activity,People_DBHelper dbHelper) {
		super();
		this.activity = activity;
		
		this.dbHelper = dbHelper;
		this.db = dbHelper.getWritableDatabase();
	}

	

	/**
	 * �����������ʾ��Աά����ʱ����е���Ա�����б�
	 * @param task_list ����Ǹ���Ա�������б�����
	 */
//	public  void actionTaskAlertDilog(People_DBHelper dbHelper) {
	public  void actionTaskAlertDilog(final ArrayList<Task> task ) {
//		final ArrayList<Task> task = new JSONP_Task().getArrTask();
//		final ArrayList<Task> task = Data.initData_peoTask(dbHelper);
		
//		task = Data.initData_peoTask(dbHelper);
//		for(int i = 0;i < task.size();i ++){
//			Log.e("taskpeople", task.get(i).task_item_text);
//		}
		AlertDialog alerDialog_item_birth;
		
		AlertDialog.Builder builder_task = new AlertDialog.Builder(activity);;

		builder_task.setTitle("�����б�");
		View layout_item_birth = LayoutInflater.from(activity).inflate(
				R.layout.all_listview, null);
		ListView task_listview = (ListView) layout_item_birth
				.findViewById(R.id.all_listview);

		TaskAdapter taskAdapter = new TaskAdapter(activity, task);
		task_listview.setAdapter(taskAdapter);
		task_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					 final int position, long id) {
				
				
				
//				new Thread(new Runnable() {
					
//					@Override
//					public void run() {
						// TODO Auto-generated method stub
//						String sendS = "mission_name="+task.get(position).getTask_item_text();
						String sendS = "MissionId="+task.get(position).getMission_id();
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
							json = new JSONObject(backMsg);
							System.out.println("������Աά���е���������"+backMsg);
							if("10000".equals(json.getString("result")))
								actionTaskDetailsAlertDialog(json,task.get(position).getMission_id());
							else if("10001".equals(json.getString("result"))){
								AlertDialog.Builder builder_event = new AlertDialog.Builder(activity);
								
								builder_event.setTitle("ע�⣺");
								builder_event.setMessage("��������ִ�У�����鿴.......");
								builder_event.setNegativeButton("YES", null);
								builder_event.create().show();
							}
								
						}catch(Exception e){
							e.printStackTrace();
						}  
//					}
//				}).start();	
						
				
			}
		});
		 
		builder_task.setView(layout_item_birth);
		builder_task.setPositiveButton("����",null);
				
		alerDialog_item_birth = builder_task.create();
		alerDialog_item_birth.show();

		dbHelper.deletepeopletask();

	}

	/**
	 * �������Ա���������
	 */
	public void actionTaskDetailsAlertDialog(JSONObject json,int mission_id) {
		

			

		AlertDialog.Builder builder_task_details = new AlertDialog.Builder(
				activity);

		View layout_task_details = LayoutInflater.from(activity).inflate(
				R.layout.task_details, null);

//		new ShowTaskDatil(layout_task_details);
		LinearLayout l = (LinearLayout) layout_task_details.findViewById(R.id.fix_main_l);
		
		ComLayout layout = new ComLayout(l, json, activity, mission_id);
		layout.addView();
		builder_task_details.setTitle("��������");
		builder_task_details.setView(layout_task_details);

		builder_task_details.setNegativeButton("����", null);
		AlertDialog alertDialog_task_details = builder_task_details.create();

		alertDialog_task_details.show(); 

	}
	/**
	 * ���������ά���е���������
	 * @param position ʱ����������б��ж�Ӧ������λ��
	 * @param list ʱ����������б������
	 * @param inputdate �����е�ʱ��
	 * @param s ʱ��ζ�Ӧ���ַ���
	 * @param taskname ��������
	 */
	public void actionTaskDayAlertDialog(JSONObject json, int mission_id,final String s, final String taskname,final String taskday) {

		System.out.println("����������б���������");

		AlertDialog.Builder builder_workday_task = new AlertDialog.Builder(activity);
		View layout_workday = null;

		layout_workday = LayoutInflater.from(activity).inflate(
				R.layout.task_details, null);
		LinearLayout l = (LinearLayout) layout_workday.findViewById(R.id.fix_main_l);
		ComLayout layout = new ComLayout(l, json, activity, mission_id);
		layout.addView();
//		final String level = ComLayout.addLayout(json, l, activity);
		
							
						
			 
			builder_workday_task.setNegativeButton("����", null);
		
		
		
//		final ShowTaskDatil showTaskDatil = new ShowTaskDatil(layout_workday);

		builder_workday_task.setView(layout_workday);
		builder_workday_task.setTitle("��������");


		AlertDialog alertDialog_task_details = builder_workday_task.create();
		alertDialog_task_details.show();

	}

	
	/**
	 * �������Աά���е�ÿ��ʱ�������Ա�����ܵ��Ű����
	 * @param name ��ʾ�����ĸ�ʱ���
	 * @param db ��Ա�����ݿ�
	 */
	public void actionWorkDayAlertDialog(String name,SQLiteDatabase db) {

		AlertDialog.Builder builder_workday = new AlertDialog.Builder(activity);
		builder_workday.setTitle("�Ű�ʱ��");
		View layout_workday = LayoutInflater.from(activity).inflate(
				R.layout.workday, null);
		TextView textView_1_mo = (TextView) layout_workday.findViewById(R.id.workday_mo_1);
		TextView textView_2_mo = (TextView) layout_workday.findViewById(R.id.workday_mo_2);
		TextView textView_3_mo = (TextView) layout_workday.findViewById(R.id.workday_mo_3);
		TextView textView_4_mo = (TextView) layout_workday.findViewById(R.id.workday_mo_4);
		TextView textView_5_mo = (TextView) layout_workday.findViewById(R.id.workday_mo_5);
		TextView textView_6_mo = (TextView) layout_workday.findViewById(R.id.workday_mo_6);
		TextView textView_7_mo = (TextView) layout_workday.findViewById(R.id.workday_mo_7);
		TextView textView_1_af = (TextView) layout_workday.findViewById(R.id.workday_af_1);
		TextView textView_2_af = (TextView) layout_workday.findViewById(R.id.workday_af_2);
		TextView textView_3_af = (TextView) layout_workday.findViewById(R.id.workday_af_3);
		TextView textView_4_af = (TextView) layout_workday.findViewById(R.id.workday_af_4);
		TextView textView_5_af = (TextView) layout_workday.findViewById(R.id.workday_af_5);
		TextView textView_6_af = (TextView) layout_workday.findViewById(R.id.workday_af_6);
		TextView textView_7_af = (TextView) layout_workday.findViewById(R.id.workday_af_7);
		Cursor cursor = db.query("people", null,"name = ? and flag = ?", new String[]{name,"1"}, null, null, null);
		if(cursor.moveToFirst()){
			do {
				String time = cursor.getString(cursor.getColumnIndex("time"));
				if(time.equals("mo_1")) textView_1_mo.setText("�ϰ�");
				if(time.equals("mo_2")) textView_2_mo.setText("�ϰ�");
				if(time.equals("mo_3")) textView_3_mo.setText("�ϰ�");
				if(time.equals("mo_4")) textView_4_mo.setText("�ϰ�");
				if(time.equals("mo_5")) textView_5_mo.setText("�ϰ�");
				if(time.equals("mo_6")) textView_6_mo.setText("�ϰ�");
				if(time.equals("mo_7")) textView_7_mo.setText("�ϰ�");
				if(time.equals("af_1")) textView_1_af.setText("�ϰ�");
				if(time.equals("af_2")) textView_2_af.setText("�ϰ�");
				if(time.equals("af_3")) textView_3_af.setText("�ϰ�");
				if(time.equals("af_4")) textView_4_af.setText("�ϰ�");
				if(time.equals("af_5")) textView_5_af.setText("�ϰ�");
				if(time.equals("af_6")) textView_6_af.setText("�ϰ�");
				if(time.equals("af_7")) textView_7_af.setText("�ϰ�");
				Log.e("time", time);
				
			} while(cursor.moveToNext());
		}
		
		
		builder_workday.setView(layout_workday);
		builder_workday.setNegativeButton("����",null);
		AlertDialog alertDialog_task_details = builder_workday.create();
		alertDialog_task_details.show();

	}
	
/**
 * �����������ѡ�������·�ʱ��ѡ����Ա
 * @param listPeople ��Ա�б�
 * @param level �������������ѡ�������
 */
private void sendTask(ArrayList<String> listPeople,final String tasktime,final String taskname){
		AlertDialog.Builder builder =  new AlertDialog.Builder(activity);
		builder.setTitle("ѡ��ִ����Ա");
		
		
		View layout = LinearLayout.inflate(activity, R.layout.selpeople, null);
		final Spinner people_spinner = (Spinner) layout.findViewById(R.id.selepeople_people_spinn);
		final EditText edit_1 = (EditText) layout.findViewById(R.id. selepeople_people_edittext1);
//		final EditText edit_2 = (EditText) layout.findViewById(R.id.selepeople_people_edittext2);
		ArrayAdapter<String> people_spinner_adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, listPeople);
		people_spinner.setAdapter(people_spinner_adapter);
		
		
		
		people_spinner.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Field field;
				try {
					field = AdapterView.class.getDeclaredField("mOldSelectedPosition");
					field.setAccessible(true);
					field.setInt(people_spinner, AdapterView.INVALID_POSITION);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}

			
		});
		people_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	final Date curDate = new Date(System.currentTimeMillis());
//		edit_1.setText(formatter.format(curDate));
//		edit_2.setText(formatter.format(curDate));
//		edit_1.setOnClickListener(new View.OnClickListener() {
			
//			@Override
//			public void onClick(View v) {
				// TODO Auto-generated method stub
//				SendDateTimePickerDialogUtil dateTimePickDialogUtil = new SendDateTimePickerDialogUtil(activity, edit_1.getText().toString());
//				dateTimePickDialogUtil.dateTimePicKDialog(edit_1);
//			}

			
//		});
//		edit_2.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				SendDateTimePickerDialogUtil dateTimePickDialogUtil = new SendDateTimePickerDialogUtil(activity, edit_2.getText().toString());
//				dateTimePickDialogUtil.dateTimePicKDialog(edit_2);
//			}
//		});
//		edit_1.setInputType(InputType.TYPE_NULL);
//		edit_2.setInputType(InputType.TYPE_NULL);
		builder.setView(layout);
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						String sendS = "taskname="+taskname+"&taskpeople="+people_spinner.getSelectedItem()+"$time1"+edit_1.getText().toString()+"&tasktime"+tasktime;
						OutputStream out = null;
						BufferedReader reader = null;
						String backMsg = "";
						try {
							 URL url = new URL("http://123.206.16.157:8080/water/arrange.req?action=set_mission");
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
							System.out.println("�����·����ص����ݣ�"+ backMsg);
//						Intent intent = new Intent(activity, );
//						activity.startActivity(intent);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		builder.setNegativeButton("ȡ��", null);
		Dialog dialog = builder.create();
		dialog.show();
		
	}

}
