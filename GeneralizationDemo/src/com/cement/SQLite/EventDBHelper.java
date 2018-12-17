package com.cement.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class EventDBHelper extends SQLiteOpenHelper {
	
	private final String CREATE_TABLE_SQL = "create table mapevent"
			+"(taskNum integer primary key, event_id, event_name, event_latitude, event_longitude,state)";

	public EventDBHelper(Context context, String name, 
			int version) {
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
