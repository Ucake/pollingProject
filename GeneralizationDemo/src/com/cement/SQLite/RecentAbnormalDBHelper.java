package com.cement.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class RecentAbnormalDBHelper extends SQLiteOpenHelper {
	private final String CREATE_TABLE_SQL = "create table recentabnormal"
			+"(taskNum integer primary key, abnormal_id, abnormal_check_point, abnormal_level, workshop,abnormal_time,abnormal_person,description,state)";

	public RecentAbnormalDBHelper(Context context, String name, int version) {
		super(context, name, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
