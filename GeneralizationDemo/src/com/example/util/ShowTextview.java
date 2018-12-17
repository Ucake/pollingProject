package com.example.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;

/*
 * 这个方法是为了让TextView显示相应的内容的
 * */
public class ShowTextview {

	private TextView mo_1;
	private TextView mo_2;
	private TextView mo_3;
	private TextView mo_4;
	private TextView mo_5;
	private TextView mo_6;
	private TextView mo_7;
	private TextView af_1;
	private TextView af_2;
	private TextView af_3;
	private TextView af_4;
	private TextView af_5;
	private TextView af_6;
	private TextView af_7;
	private SQLiteDatabase db;
	private int flagTable;
	public ShowTextview(TextView mo_1, TextView mo_2, TextView mo_3,
			TextView mo_4, TextView mo_5, TextView mo_6, TextView mo_7,
			TextView af_1, TextView af_2, TextView af_3, TextView af_4,
			TextView af_5, TextView af_6, TextView af_7, SQLiteDatabase db
			,int flagTable) {
		super();
		this.mo_1 = mo_1;
		this.mo_2 = mo_2;
		this.mo_3 = mo_3;
		this.mo_4 = mo_4;
		this.mo_5 = mo_5;
		this.mo_6 = mo_6;
		this.mo_7 = mo_7;
		this.af_1 = af_1;
		this.af_2 = af_2;
		this.af_3 = af_3;
		this.af_4 = af_4;
		this.af_5 = af_5;
		this.af_6 = af_6;
		this.af_7 = af_7;
		this.db = db;
		this.flagTable = flagTable;
		
	}
	public void showTextPeople(){
		Cursor cursor = null;
//		if(flagTable == 0)
		 cursor = db.query("people", null, "flag = ?", new String []{"1"}, null, null, null);
		
		deleteText();
		if(cursor.moveToFirst()){
			do {
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String time = cursor.getString(cursor.getColumnIndex("time"));
				if(time.equals("mo_1"))mo_1.setText(name+"...");
				if(time.equals("mo_2"))mo_2.setText(name+"...");
				if(time.equals("mo_3"))mo_3.setText(name+"...");
				if(time.equals("mo_4"))mo_4.setText(name+"...");
				if(time.equals("mo_5"))mo_5.setText(name+"...");
				if(time.equals("mo_6"))mo_6.setText(name+"...");
				if(time.equals("mo_7"))mo_7.setText(name+"...");
				if(time.equals("af_1"))af_1.setText(name+"...");
				if(time.equals("af_2"))af_2.setText(name+"...");
				if(time.equals("af_3"))af_3.setText(name+"...");
				if(time.equals("af_4"))af_4.setText(name+"...");
				if(time.equals("af_5"))af_5.setText(name+"...");
				if(time.equals("af_6"))af_6.setText(name+"...");
				if(time.equals("af_7"))af_7.setText(name+"...");
//				System.out.println("这个周一白天有数吗？"+name+"..........."+time);
				
				Log.e("ShowTextview_name", name);
			} while (cursor.moveToNext());
		}
	}
	public void showTextTask(){
		Cursor cursor = null;
//		String sql = "select * from task where mission_condition > 2";
//		cursor = db.rawQuery(sql, null);
			cursor = db.query("task", null, null, null, null, null, null);
		deleteTextTask();
		if(cursor.moveToFirst()){
			do {
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String time = cursor.getString(cursor.getColumnIndex("time"));
				String mission_condition = cursor.getString(cursor.getColumnIndex("mission_condition"));
				if(time.equals("mo_1"))mo_1.setText(name+"...");
				if(time.equals("mo_2"))mo_2.setText(name+"...");
				if(time.equals("mo_3"))mo_3.setText(name+"...");
				if(time.equals("mo_4"))mo_4.setText(name+"...");
				if(time.equals("mo_5"))mo_5.setText(name+"...");
				if(time.equals("mo_6"))mo_6.setText(name+"...");
				if(time.equals("mo_7"))mo_7.setText(name+"...");
				if(time.equals("af_1"))af_1.setText(name+"...");
				if(time.equals("af_2"))af_2.setText(name+"...");
				if(time.equals("af_3"))af_3.setText(name+"...");
				if(time.equals("af_4"))af_4.setText(name+"...");
				if(time.equals("af_5"))af_5.setText(name+"...");
				if(time.equals("af_6"))af_6.setText(name+"...");
				if(time.equals("af_7"))af_7.setText(name+"...");
//				System.out.println("这个周一白天有数吗？"+name+"..........."+time);
				
				Log.e("ShowTextview_name", name+":"+time+":"+mission_condition);
			} while (cursor.moveToNext());
		}
	}
	private void deleteText(){
		mo_1.setText("请安排");
		mo_2.setText("请安排");
		mo_3.setText("请安排");
		mo_4.setText("请安排");
		mo_5.setText("请安排");
		mo_6.setText("请安排");
		mo_7.setText("请安排");
		af_1.setText("请安排");
		af_2.setText("请安排");
		af_3.setText("请安排");
		af_4.setText("请安排");
		af_5.setText("请安排");
		af_6.setText("请安排");
		af_7.setText("请安排");
	}
	private void deleteTextTask(){
		mo_1.setText("无任务");
		mo_2.setText("无任务");
		mo_3.setText("无任务");
		mo_4.setText("无任务");
		mo_5.setText("无任务");
		mo_6.setText("无任务");
		mo_7.setText("无任务");
		af_1.setText("无任务");
		af_2.setText("无任务");
		af_3.setText("无任务");
		af_4.setText("无任务");
		af_5.setText("无任务");
		af_6.setText("无任务");
		af_7.setText("无任务");
	}
}

