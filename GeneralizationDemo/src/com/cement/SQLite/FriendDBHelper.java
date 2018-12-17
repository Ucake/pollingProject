package com.cement.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class FriendDBHelper extends SQLiteOpenHelper {
	
	private final String CREATE_TABLE_SQL = "create table mapfriend"
			+"(taskNum integer primary key, friend_name, friend_phone, friend_latitude, friend_longitude,state)";

	public FriendDBHelper(Context context, String name,
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
