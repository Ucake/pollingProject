package com.example.util;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.aql.People_DBHelper;
import com.example.bean.Person;
import com.example.bean.Task;
import com.example.bean.TaskTask;

/**
 * @author lson
 *这是个数据类，就是listview中的数据
 */
public class Data {

	/**
	 * 这个方法是人员维度中的每个时间段的数据
	 * @param s 这个是时间段对应的字符串，用来搜索数据库中对应时间段的数据
	 * @param db 数据库
	 * @return
	 */
	public static ArrayList<Person> initData_mo_1(String s, SQLiteDatabase db) {
		ArrayList<Person> list = new ArrayList<Person>();
		list.clear();

		Cursor cursor;

		cursor = db.query("people", null, "time = ?", new String[] { s }, null,
				null, "flag desc");

		if (cursor.moveToFirst()) {

			do {
				Person p = new Person();
				p.setName(cursor.getString(cursor.getColumnIndex("name")));

				p.setBirth("任务");
				p.setFlag(cursor.getInt(cursor.getColumnIndex("flag")));
				p.setColorflag(cursor.getInt(cursor
						.getColumnIndex("colorflage")));
				
				String name = p.getName();
				String task = p.getBirth();
				int flag = p.getFlag();
				int colorflag = p.getColorflag();
				
				boolean f;
				if (flag == 1)
					f = true;
				else
					f = false;
				Person info = new Person(name, task, flag, colorflag, f);
				list.add(info);
//				Log.e("人员排班", p.getName());
			} while (cursor.moveToNext());
		}

		return list;
	}
	/**
	 * 这是人员维度中的任务列表
	 * @param db 数据库
	 * @return
	 */
//	public static  ArrayList<Task> initData_peoTask(People_DBHelper db) {
//		ArrayList<Task> list = new ArrayList<Task>();
//		list.clear();
//		Task p = new Task();
//
//		Cursor cursor;
//
//		cursor = db.getWritableDatabase().query("peopletask", null, null, null, null, null,
//				"mission_condition desc");
//
//		if (cursor.moveToFirst()) {
//
//			do {
//				String name = p.task_item_text = cursor.getString(cursor
//						.getColumnIndex("name"));
//				
//				int mission_condition = p.mission_condition = cursor
//						.getInt(cursor.getColumnIndex("mission_condition"));
//				int mission_id = p.mission_id = cursor.getInt(cursor.getColumnIndex("mission_id"));
//				Task info = new Task(name,mission_condition,mission_id);
//				list.add(info);
////				Log.e("peopletask", name);
//				
//			} while (cursor.moveToNext());
//		}
//		
//		return list;
//	}

	
	/**
	 * 这个是任务维度中的时间段的数据
	 * @param s 这个是时间段对应的字符串，用来搜索数据库对应时间的数据
	 * @param db数据库
	 * @return
	 */
	public static ArrayList<TaskTask> initData(String s,SQLiteDatabase db) {
		ArrayList<TaskTask> list_task = new ArrayList<TaskTask>();
		
		
		Cursor cursor = db.query("task", null, "time = ? and mission_condition > ?", new String[] { s,"0" },
				null, null, "mission_condition desc");

		if (cursor.moveToFirst()) {

			do {
				
				TaskTask t = new TaskTask();
				t.setName(cursor.getString(cursor.getColumnIndex("name")));
				t.setMission_condition(cursor.getInt(cursor.getColumnIndex("mission_condition")));

				t.setMission_id(cursor.getInt(cursor.getColumnIndex("mission_id")));
				String name = t.getName();


				int mission_id = t.getMission_id();
				int mission_condition = t.getMission_condition();
				TaskTask info = new TaskTask(name,mission_id,mission_condition);
				list_task.add(info);
				
				
				
//				list_task.add(t);
				Log.e("任务安排", t.getName()+":"+t.getMission_condition()+":"+cursor.getString(cursor.getColumnIndex("time")));

			} while (cursor.moveToNext());
		}

		return list_task;
	}

}
