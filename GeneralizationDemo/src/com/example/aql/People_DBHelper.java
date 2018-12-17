package com.example.aql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class People_DBHelper extends SQLiteOpenHelper{

	private SQLiteDatabase db;

	private final String CREATE_TABLE_PEOPLE = "create table people("+" id integer primary key autoincrement," +
			""+" name char,"+" flag integer,"+" colorflage integer,"+" time char,"+"team char,"+" post char)";

	private final String CREATE_TABLE_TASK = "create table task("+" id integer primary key autoincrement," +
			""+" name char,"+" mission_condition integer,"+" time char,"+" mission_id)";
	private final String CREATE_TABLE_PEOPLE_TASK = "create table peopletask("+" id integer primary key autoincrement," +
			" name char,"+" mission_condition integer,"+" mission_id integer)";
	private final String CREATE_EVENT = "create table event " +
			"("+"task_id integer primary key autoincrement," +
			"task_name char, " +
			"task_level char," +
			"task_source char,"+
			"task_nature char," +
			"task_place char," +
			"task_describe char," +
			"task_check1 integer," +
			"task_check2 integer," +
			"task_check3 integer," +
			"identiry_authentication char," +
			"task_priod char," +
			"task_start char," +
			"task_end char," +
			"task_number char," +
			"task_people char)";
	
	public People_DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		db = getWritableDatabase();
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		this.db = db;
		db.execSQL(CREATE_TABLE_PEOPLE);
		db.execSQL(CREATE_TABLE_TASK);
		db.execSQL(CREATE_TABLE_PEOPLE_TASK);
		db.execSQL(CREATE_EVENT);
		
		initData();
		insertData();
	}


	
	private void initData(){
		
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��1',1,1,'mo_1')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��2',0,0,'mo_1')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��3',1,0,'mo_1')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��1',1,0,'af_1')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��2',1,0,'mo_2')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��2',1,0,'af_2')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��3',1,0,'mo_3')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��3',1,0,'af_3')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��4',1,1,'mo_4')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��4',1,0,'af_4')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��5',1,0,'mo_5')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��5',1,0,'af_5')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��6',1,0,'mo_6')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��6',1,0,'af_6')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��7',1,0,'mo_7')");
//		db.execSQL("insert into people(name,flag,colorflage,time) Values('С��7',1,0,'af_7')");
//		db.execSQL("insert into peopletask(name,mission_condition) Values('����1',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����0',0,'mo_1',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����1',1,'mo_1',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����3',2,'mo_1',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����4',3,'mo_1',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����5',4,'mo_1',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����6',5,'mo_1',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����7',6,'mo_1',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����2',7,'mo_1',1)");
//		
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����1',3,'af_1',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����2',4,'mo_2',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����2',5,'af_2',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����3',6,'mo_3',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����3',7,'af_3',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����4',0,'mo_4',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����4',1,'af_4',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����5',2,'mo_5',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����5',3,'af_5',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����6',4,'mo_6',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����6',5,'af_6',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����7',6,'mo_7',1)");
//		db.execSQL("insert into task(name,mission_condition,time,mission_id) Values('����7',7,'af_7',1)");
		
		
		
	}
private void insertData(){
		
		db.execSQL("insert into event(task_name,task_level,task_source,task_nature,task_place,task_describe,task_check1,task_check2,task_check3,identiry_authentication,task_priod,task_start,task_end,task_number,task_people)" +
				"Values('Ѳ������1','����','ϵͳԭ��','�̶�����','һ¥����¥','���������ô��ô��.......',1,1,1,'����ʶ����֤','ÿ����Сʱ',null,null,'123','��')");
		db.execSQL("insert into event(task_name,task_level,task_source,task_nature,task_place,task_describe,task_check1,task_check2,task_check3,identiry_authentication,task_priod,task_start,task_end,task_number,task_people)" +
				"Values('Ѳ������2','����','ϵͳԭ��','�̶�����','һ¥����¥','���������ô��ô��.......',1,1,1,'ָ����֤','ÿ����Сʱ',null,null,'459','��')");
		db.execSQL("insert into event(task_name,task_level,task_source,task_nature,task_place,task_describe,task_check1,task_check2,task_check3,identiry_authentication,task_priod,task_start,task_end,task_number,task_people)" +
				"Values('Ѳ������3','��˾','ϵͳԭ��','��ʱ����','һ¥����¥','���������ô��ô��.......',1,1,1,'ָ����֤',null,'2018-01-09','2018-02-09','369','��')");
		db.execSQL("insert into event(task_name,task_level,task_source,task_nature,task_place,task_describe,task_check1,task_check2,task_check3,identiry_authentication,task_priod,task_start,task_end,task_number,task_people)" +
				"Values('Ѳ������4','����','ϵͳԭ��','�̶�����','һ¥����¥','���������ô��ô��.......',1,1,1,'ָ����֤','ÿ�ĸ�Сʱ',null,null,'258','��')");
		db.execSQL("insert into event(task_name,task_level,task_source,task_nature,task_place,task_describe,task_check1,task_check2,task_check3,identiry_authentication,task_priod,task_start,task_end,task_number,task_people)" +
				"Values('Ѳ������5','����','ϵͳԭ��','�̶�����','һ¥����¥','���������ô��ô��.......',1,1,1,'ָ����֤','ÿ�ĸ�Сʱ',null,null,'147','��')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		switch (newVersion) {
		case 1:
			db.execSQL(CREATE_TABLE_PEOPLE);
			db.execSQL(CREATE_TABLE_TASK);
			db.execSQL(CREATE_TABLE_PEOPLE_TASK);
			onCreate(db);
			break;
		case 2:
			db.execSQL(CREATE_TABLE_PEOPLE);
			db.execSQL(CREATE_TABLE_TASK);
			db.execSQL(CREATE_TABLE_PEOPLE_TASK);
			onCreate(db);
			break;
		case 3:
			db.execSQL(CREATE_TABLE_PEOPLE);
			db.execSQL(CREATE_TABLE_TASK);
			db.execSQL(CREATE_TABLE_PEOPLE_TASK);
			onCreate(db);
			break;
		case 4:
			db.execSQL(CREATE_TABLE_PEOPLE);
			db.execSQL(CREATE_TABLE_TASK);
			db.execSQL(CREATE_TABLE_PEOPLE_TASK);
			onCreate(db);
			break;
		case 5:
			db.execSQL(CREATE_TABLE_PEOPLE);
			db.execSQL(CREATE_TABLE_TASK);
			db.execSQL(CREATE_TABLE_PEOPLE_TASK);
			onCreate(db);
			break;
		case 6:
			db.execSQL(CREATE_TABLE_PEOPLE);
			db.execSQL(CREATE_TABLE_TASK);
			db.execSQL(CREATE_TABLE_PEOPLE_TASK);
			db.execSQL(CREATE_EVENT);
			onCreate(db);
			break;

		default:
			break;
		}
		
	}


	
  public void deletepeople(){
	  db.execSQL("delete from people where id != 0");
  }
  public void deletetask(){
	  db.execSQL("delete from task where id != 0");
  }
  public void deletepeopletask(){
	  db.execSQL("delete from peopletask where id != 0");
  }
}
