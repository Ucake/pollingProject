package com.example.testtransmit;




import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;


import com.example.aql.People_DBHelper;
import com.example.generalizationdemo.R;
import com.example.wechatsample.MainActivity;
import com.google.gson.JsonArray;

import android.R.string;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class SendActivity extends Activity {
	private EditText id;
	private EditText name;
	private EditText level;
	private EditText source;
	private EditText start;
	private EditText end;
	private EditText time;
	private EditText place;
	private EditText describe;
	private EditText identity;
	private Button peopleButton;
	private MyListView myListView;
	private ArrayList<EventRank> list;
	private Button buttonSend;
	private Button buttonNo;
	private EditText check;
	
	private SQLiteDatabase db;
	private People_DBHelper helper;
	private int people_count = 0;
	private int people_time = 2;
	private ArrayList<SelectsPeople>  listPeople;
	private String[] people = null;
	private String taskNature = "";
	private  String tim = "";
	private String taskname = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        helper = new People_DBHelper(this, "event", null, 6);
        db = helper.getWritableDatabase();
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
        
//        String taskNature = bundle.getString("task_nature");
//        final String tim = bundle.getString("time");
//        String taskname = bundle.getString("name");
         tim = "mo_1";
         taskname = "巡检任务3";
         taskNature = "临时任务";	
        
        if(taskNature.equals("固定任务"))setContentView(R.layout.test_fix_main);
        else setContentView(R.layout.test_tem_main);
        findId();
        assignmentValue(taskname);
//       list = AlertDialogUse.list;
        list = new ArrayList<EventRank>();
        for (int i = 0; i < 10; i++) {
			list.add(new EventRank(""+i,"事件"+i));
		}
        RankdeEventAdapter adapter = new RankdeEventAdapter(this, list);
        myListView.setAdapter(adapter);
        listPeople = new ArrayList<SelectsPeople>();
        peopleButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(people_time == 1){
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						OutputStream out = null;
						BufferedReader reader = null;
						String backMsg = "";
						try {
							 URL url = new URL("");
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("POST");
							conn.setDoOutput(true);
							conn.setDoInput(true);
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							out = conn.getOutputStream();
							out.write(tim.getBytes());
							reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
							String line;
							
							while((line = reader.readLine())!=null){
								backMsg = line;
							}
							
							if(backMsg != ""){
								JSONObject json = new JSONObject(backMsg);
								JSONArray data = json.getJSONArray("data");
								for (int i = 0; i < data.length(); i++) {
									JSONObject group = data.getJSONObject(i);
									listPeople.add(new SelectsPeople(group.getString("peoplename"), group.getBoolean("flag")));
								}
								
							}
							 people = new String[listPeople.size()];
							for (int i = 0; i < people.length; i++) {
								if(listPeople.get(i).isFlag())
								people[i] = listPeople.get(i).getPeopleName()+"(排班)";
							}
							people_time = 2;
							selectPeopleDialong(people);
							
						}catch(Exception e){
							e.printStackTrace();
						}
					}

					

					
				}).start();
			}else{
				people = new String[10];
				for (int i = 0; i < 5; i++) {
					people[i] = "小宝"+i+"(排班)";
				}
				for (int i = 5; i < 10; i++) {
					people[i] = "小宝"+i;
				}
				selectPeopleDialong(people);
			}
			}
		});
        buttonSend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						OutputStream out = null;
						InputStream is = null;
						try {
							URL url = new URL("");
							HttpURLConnection conn = (HttpURLConnection) url.openConnection();
							conn.setRequestMethod("POST");
							conn.setDoOutput(true);
							conn.setDoInput(true);
							conn.setConnectTimeout(5000);
							conn.setReadTimeout(5000);
							out = conn.getOutputStream();
							String str = name.getText().toString()+peopleButton.getText().toString();
							out.write(str.getBytes());
							is = conn.getInputStream();
							int len;
							String backMsg = "";
							while ((len = is.read()) != -1) {
								backMsg =  String.valueOf(len);
								
							}
							if(backMsg != ""){
								JSONObject json = new JSONObject(backMsg);
								if(json.getString("result").equals("10000")){
									Toast.makeText(SendActivity.this, "成功", Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(SendActivity.this, json.getString(""), Toast.LENGTH_SHORT).show();
								}
							}
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
        buttonNo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SendActivity.this,MainActivity.class);
				startActivity(intent);
			}
		});
    }

    private void selectPeopleDialong(
			final String[] people) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("请选择执行人");
		builder.setSingleChoiceItems(people, people_count, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				peopleButton.setText(people[which]);
				people_count = which;
				dialog.dismiss();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	private void assignmentValue(String taskname) {
		// TODO Auto-generated method stub
		Cursor cursor = db.query("event", null, "task_name = ?", new String[]{taskname}, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				id.setText("" + cursor.getInt(cursor.getColumnIndex("task_id")));

				name.setText(taskname);

				place.setText(cursor.getString(cursor
						.getColumnIndex("task_place")));

				describe.setText(cursor.getString(cursor
						.getColumnIndex("task_describe")));

				level.setText(cursor.getString(cursor
						.getColumnIndex("task_level")));

				source.setText(cursor.getString(cursor
						.getColumnIndex("task_source")));

				identity.setText(cursor.getString(cursor
						.getColumnIndex("identiry_authentication")));
				if(taskNature.equals("固定任务")){

				time.setText(cursor.getString(cursor
						.getColumnIndex("task_priod")));
				}else{
				start.setText(cursor.getString(cursor
						.getColumnIndex("task_start")));

				end.setText(cursor.getString(cursor.getColumnIndex("task_end")));
				if (cursor.getInt(cursor.getColumnIndex("task_check1")) == 1) {
					check.append("一级审核，");
				}
				if (cursor.getInt(cursor.getColumnIndex("task_check2")) == 1) {
					check.append("二级审核，");
				}
				if (cursor.getInt(cursor.getColumnIndex("task_check3")) == 1) {
					check.append("三级审核，");
				}
				}

			} while (cursor.moveToNext());
		}
	}

	private void findId() {
		// TODO Auto-generated method stub
		id = (EditText) findViewById(R.id.task_details_textview_id_value);
		name = (EditText) findViewById(R.id.task_details_EditText_name);
		level = (EditText) findViewById(R.id.task_details_Spinner_level);
		source = (EditText) findViewById(R.id.task_details_Spinner_source);
		start = (EditText) findViewById(R.id.task_details_Spinner_nature_start);
		end = (EditText) findViewById(R.id.task_details_Spinner_nature_end);
		time = (EditText) findViewById(R.id.task_details_Spinner_nature_time);
		place = (EditText) findViewById(R.id.task_details_EditText_place);
		describe = (EditText) findViewById(R.id.task_details_EditText_describe);
		identity = (EditText) findViewById(R.id.task_details_Spinner_identiry_authentication);
		peopleButton = (Button) findViewById(R.id.button_people);
		myListView = (MyListView) findViewById(R.id.task_details_listview);
		check = (EditText) findViewById(R.id.task_details_Spinner_check);
		buttonSend = (Button) findViewById(R.id.task_details_button_yes);
		buttonNo = (Button) findViewById(R.id.task_details_button_no);
		
	}

   
}
