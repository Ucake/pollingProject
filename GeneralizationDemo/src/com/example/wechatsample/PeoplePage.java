 package com.example.wechatsample;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import com.example.adpter.MyAdapter;
import com.example.adpter.MyAdapter.InnerItemOnclickListener;
import com.example.adpter.TaskAdapter;

import com.example.aql.People_DBHelper;

import com.example.bean.Person;
import com.example.bean.Task;
import com.example.generalizationdemo.R;
import com.example.thread.Pe_task_listThread;
import com.example.thread.PeopleStartThread;
import com.example.thread.SpeTimePeopleThread;
import com.example.util.AlertDialogUse;
import com.example.util.CountTime;
import com.example.util.Data;
import com.example.util.DateTimePickDialogUtil;
import com.example.util.JSONP_Task;
import com.example.util.JSONStart;
import com.example.util.ShowTextview;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;

/**
 * ����ԱΪ�߶ȵĽ���
 * 
 * 
 * http://blog.csdn.net/guolin_blog/article/details/26365683
 * 
 * @author guolin
 */
public class PeoplePage extends Fragment implements InnerItemOnclickListener {
	//�����е�����textview����������
	 TextView inputdateF;
	 TextView inputweekF;
	 DateTimePickDialogUtil dateTimePickDialogUtil;
	 
	 public  String factory_id = com.example.generalizationdemo.HomePageya.factory_id;
	 
	//��¼ʱ��仯�ı���
//	 String timeChange = "";
	 
	 //ʮ�ĸ�textView������ʾʱ��Σ����һ��textViewʮ�ĸ�����һ����������ֵ
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
	 String publicStr = "";
	 
	 //ʱ��εĵ��������ú͵�������listview������Դ
	AlertDialog alertDialog;
	ArrayList<Person> list;
	//��������������洢ѡ���˵�����
	ArrayList<String> selectName;
	//������������洢ԭ��ѡ�����ڲ�ѡ�˵��˵�����
	ArrayList<String> noSelectName;
	//����������ǵ����󼸸��������
	AlertDialogUse alertDialogUse;
		
	//����������ʱ�õ���ʱ���
	String set_worker_time;
	//������������ܶ����ڵ����򣬺���ѡ�˺�ɾ����ʱ�����������
	MyAdapter adapter;
	//����ǳ���һ��ʼ���߳�
	Handler handlerStart;
	//��������������������������ڵ�
	CountTime countTime;
	//�������ݿ������
	public static People_DBHelper dbHelper;
	SQLiteDatabase db;
	ContentValues values;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {


		View letterLayout = inflater.inflate(R.layout.arrange_main,
				container, false);
		values = new ContentValues();
		Log.e("factory_id", factory_id);
		//����ʱ��Ĳ���
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    	final Date curDate = new Date(System.currentTimeMillis());
		inputdateF = (TextView) letterLayout.findViewById(R.id.inputdate);
		inputweekF = (TextView) letterLayout.findViewById(R.id.inputweek);
		inputdateF.setText(formatter.format(curDate));
//		timeChange = inputdateF.getText().toString();
		
		inputweekF.setText("��"+CountTime.inputWeek(inputdateF)+"��");
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
        .detectNetwork()
        .penaltyLog()
        .build());
		
		//�������ݿ�Ĳ���
				dbHelper = new People_DBHelper(getActivity(), "people", null, 5);
				db = dbHelper.getWritableDatabase();
		
		
		//һ��ʼ��������
				
				countTime = new CountTime(inputdateF.getText().toString());
				String value ="time1=" +countTime.getBeginTime()+ "&time2="+ countTime.getEndTime()
						+"&factory_id="+factory_id;
				System.out.println("wochuandeshu"+value);
//				String value ="time1=" +"2017-06-25";
				PeopleStartThread thread = new PeopleStartThread(value);
				thread.run();
//				Toast.makeText(getActivity(), "������Աά��", Toast.LENGTH_SHORT).show();
				
		
				mo_1 = (TextView) letterLayout.findViewById(R.id.mo_1);
				mo_2 = (TextView) letterLayout.findViewById(R.id.mo_2);
				mo_3 = (TextView) letterLayout.findViewById(R.id.mo_3);
				mo_4 = (TextView) letterLayout.findViewById(R.id.mo_4);
				mo_5 = (TextView) letterLayout.findViewById(R.id.mo_5);
				mo_6 = (TextView) letterLayout.findViewById(R.id.mo_6);
				mo_7 = (TextView) letterLayout.findViewById(R.id.mo_7);
				af_1 = (TextView) letterLayout.findViewById(R.id.af_1);
				af_2 = (TextView) letterLayout.findViewById(R.id.af_2);
				af_3 = (TextView) letterLayout.findViewById(R.id.af_3);
				af_4 = (TextView) letterLayout.findViewById(R.id.af_4);
				af_5 = (TextView) letterLayout.findViewById(R.id.af_5);
				af_6 = (TextView) letterLayout.findViewById(R.id.af_6);
				af_7 = (TextView) letterLayout.findViewById(R.id.af_7);
				showTextview = new ShowTextview(mo_1, mo_2, mo_3, mo_4, mo_5, mo_6, mo_7, af_1, af_2, af_3, af_4, af_5, af_6, af_7, db, 0);
				showTextview.showTextPeople();
		
	inputdateF.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
	//				dateTimePickDialogUtil = new DateTimePickDialogUtil(getActivity(), formatter.format(curDate));
					dateTimePickDialogUtil = new DateTimePickDialogUtil(getActivity(), inputdateF.getText().toString(),"peoplePage",dbHelper,showTextview);
					dateTimePickDialogUtil.dateTimePicKDialog(inputdateF,inputweekF);
					
					
				}
			} );
		
		
//		values.put("name", "С��3");
//		values.put("flag", 0);
//		values.put("colorflage", 0);
//		values.put("time", "mo_1");
//		
//		db.insert("people", null, values);
//		values.clear();
//		values.put("name", "С��4");
//		values.put("flag", 0);
//		values.put("colorflage", 0);
//		values.put("time", "mo_1");
//		db.insert("people", null, values);
		
		
		
		alertDialogUse = new AlertDialogUse(getActivity(),dbHelper);
		list = new ArrayList<Person>();
		
		
		final Handler mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				if(msg.what == 1){
					actionAlertDialog(list,publicStr);
				}
			}
			
			
			
			
		};
		mo_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_1;
				publicStr = "mo_1";
//				list = Data.initData_mo_1("mo_1", db);
//				actionAlertDialog(list,"mo_1");
				
				new Thread(speTimePeo(mHandler)).start();
				

			}
		});
		mo_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_2;
				publicStr = "mo_2";
//				list = Data.initData_mo_1("mo_2", db);
//				actionAlertDialog(list,"mo_2");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		mo_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_3;
				publicStr = "mo_3";
//				list = Data.initData_mo_1("mo_3", db);
//				actionAlertDialog(list,"mo_3");
				new Thread(speTimePeo(mHandler)).start();
			}
		});
		mo_4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_4;
				publicStr = "mo_4";
//				list = Data.initData_mo_1("mo_4", db);
//				actionAlertDialog(list,"mo_4");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		mo_5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_5;
				publicStr = "mo_5";
//				list = Data.initData_mo_1("mo_5", db);
//				actionAlertDialog(list,"mo_5");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		mo_6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_6;
				publicStr = "mo_6";
//				list = Data.initData_mo_1("mo_6", db);
//				actionAlertDialog(list,"mo_6");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		mo_7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_7;
				publicStr = "mo_7";
//				list = Data.initData_mo_1("mo_7", db);
//				actionAlertDialog(list,"mo_7");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		af_1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_1;
				publicStr = "af_1";
//				list = Data.initData_mo_1("af_1", db);
//				actionAlertDialog(list,"af_1");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		af_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_2;
				publicStr = "af_2";
//				list = Data.initData_mo_1("af_2", db);
//				actionAlertDialog(list,"af_2");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		af_3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_3;
				publicStr = "af_3";
//				list = Data.initData_mo_1("af_3", db);
//				actionAlertDialog(list,"af_3");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		af_4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_4;
				publicStr = "af_4";
//				list = Data.initData_mo_1("af_4", db);
//				actionAlertDialog(list,"af_4");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		af_5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_5;
				publicStr = "af_5";
//				list = Data.initData_mo_1("af_5", db);
//				actionAlertDialog(list,"af_5");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		af_6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = mo_6;
				publicStr = "af_6";
//				list = Data.initData_mo_1("af_6", db);
//				actionAlertDialog(list,"af_6");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		af_7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publicText = af_7;
				publicStr = "af_7";
//				list = Data.initData_mo_1("af_7", db);
//				actionAlertDialog(list,"af_7");
				new Thread(speTimePeo(mHandler)).start();

			}
		});
		
		
		

		
		
		

		return letterLayout;
	}

	

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
		dbHelper.deletepeople();
		Log.e("dddddddd", "!!!!!!!!!!!");
	}






	/**
	 * �����������Աά����ÿ��ʱ��ε�����alertDialog
	 * @param list ��ʾlistview�е�����Դ
	 * @param s ��ʾʱ��ε��ַ���
	 * @return 
	 */
	public String actionAlertDialog(ArrayList<Person> list, final String s) {

		set_worker_time = s;
		selectName = new ArrayList<String>();
		noSelectName = new ArrayList<String>();
		
//		
//		for (int i = 0; i < list.size(); i++) {
//			Log.e("222222", list.get(i).name);
//		}
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		View layout = LayoutInflater.from(getActivity()).inflate(
				R.layout.all_listview,null);
		
		ListView myListView = (ListView) layout.findViewById(R.id.all_listview);
		 
		  adapter = new MyAdapter(getActivity(), list);
		adapter.setOnInnerItemOnClickListener(this);
		myListView.setAdapter(adapter);

		builder.setView(layout);
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				JSONArray jsonArray = sendJsonArray(s);
				setTextofRmove(s);
				
				JSONObject json = new JSONObject();
				try {
					json.put("data", jsonArray);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.e("peopleRmove", json.toString());
				try {
					String backMsg = null;
					URL url = new URL("http://123.206.16.157:8080/water/arrange.req?action=arrangeinsert");
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
					conn.setRequestMethod("POST");
					conn.setDoOutput(true);
					conn.setDoInput(true);
					conn.setConnectTimeout(5000);
					conn.setReadTimeout(5000);
					
					OutputStream out = conn.getOutputStream();
//					BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
//					String sendStr = java.net.URLDecoder.decode(json.toString());
					Log.d("jietangzixin", json.toString());
					out.write(json.toString().getBytes("gbk"));
					out.flush();
//					bw.flush();
					out.close();
//					bw.close();
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String line;
					
					while((line = reader.readLine())!=null){
						backMsg = line;
					}
			
					if(!backMsg.equals(null)){
						
							JSONObject toor = new JSONObject(backMsg);
							Log.v("chaoyinsulieche", toor.toString());
							String result = toor.getString("result");
							if("10000".equals(result))
							Toast.makeText(getActivity(), "�޸ĳɹ�����", Toast.LENGTH_LONG).show();
							else
								Toast.makeText(getActivity(), "�޸�ʧ�ܣ���", Toast.LENGTH_LONG).show();
								
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
				
			

		});
		
		builder.setNegativeButton("ȡ��", null);
		alertDialog = builder.create();
		alertDialog.show();
		return s;
	

	}
	

	

	






	/* (non-Javadoc)
	 * @see com.example.adpter.MyAdapter.InnerItemOnclickListener#itemClic(int, android.view.View, android.widget.Button)
	 */
	@Override
	public int itemClic(int position, View v, Button button) {
		

		switch (v.getId()) {
		case R.id.item_name:


			alertDialogUse.actionWorkDayAlertDialog(list.get(position).getName(),db);
			break;

		case R.id.item_birth:
//			final ArrayList<Task> task = new ArrayList<Task>();
			
			Handler handler = new Handler(){
				public void handleMessage(Message msg){
					if(msg.what == 12137){
						String backMsg = msg.obj.toString();
						System.out.println("������Ա�����б�ķ�������"+backMsg);
						//Log.e("startDate", backMsg);
						if(!backMsg.equals(null)){
//							
//							
							JSONP_Task jsonP_task = new JSONP_Task();
							jsonP_task.p_Task(backMsg);
							if("10000".equals(jsonP_task.getResulta())){
								
								ArrayList<Task> task = jsonP_task.getArrTask();
								alertDialogUse.actionTaskAlertDilog(task);					
							}else{
								Toast.makeText(getActivity(), "����������Ժ�~~~", Toast.LENGTH_LONG).show();
							}

						}
					}
				}
			};
			countTime = new CountTime(inputdateF.getText().toString());
			String value ="time1=" +countTime.getBeginTime()+"&time2="+ countTime.getEndTime()+"&set_worker_time="+set_worker_time+"&worker_name="+list.get(position).getName()
					+"&factory_id="+factory_id;
			System.out.println("peoplepage��Ա�б�Ҫ���͵����ݣ�"+value);
			Pe_task_listThread thread = new Pe_task_listThread(value, handler);
			new Thread(thread).start();
			


//			alertDialogUse.actionTaskAlertDilog(dbHelper);

			break;
		case R.id.item_btn:
			

			if(MyAdapter.isSelect.get(position)){
				button.setText("ѡ��");
				MyAdapter.isSelect.put(position, false);
				button.setTextColor(android.graphics.Color.BLUE);
				
				adapter.notifyDataSetChanged();
				if(list.get(position).isF())
				noSelectName.add(list.get(position).getName());
				if (!list.get(position).isF()) {
					if (selectName.contains(list.get(position).getName())) {
						selectName.remove(list.get(position).getName());

					}
				}
				
			} else {
				button.setText("��ѡ");

				MyAdapter.isSelect.put(position, true);
				button.setTextColor(android.graphics.Color.RED);
                adapter.notifyDataSetChanged();
                if (!list.get(position).isF()) {
                	selectName.add(list.get(position).getName());
				}
                
				if (list.get(position).isF()) {
					if (noSelectName.contains(list.get(position).getName())) {
						noSelectName.remove(list.get(position).getName());

					}
				}
            }
//			 for(int i = 0;i < selectName.size();i++){
//				 Log.e("select",selectName.get(i));
//			 }
			
			break;
		default:
			break;
		
	}

		return position;

	}



	/**
	 * �����������Ա����ʱ��ѡ���ŵ��˺�ɾ�����˷�װ��jsonarray����
	 * @param s ���������sʱ��ε���Ա����
	 * @return
	 */
	private JSONArray sendJsonArray(final String s) {
		//				countTime = new CountTime(inputdateF.getText().toString());
						JSONArray jsonArray = new JSONArray();
						JSONObject jsonObject = null;
						for(int j = 0;j < noSelectName.size();j ++){
		//					Log.e("nosele", noSelectName.get(j));
							jsonObject = new JSONObject();
							try {
								jsonObject.put("startTime", countTime.getBeginTime());
								jsonObject.put("endTime", countTime.getEndTime());
								jsonObject.put("set_worker_time",s);
								jsonObject.put("flag", ""+0);
//								jsonObject.put("name", noSelectName.get(j));
//								jsonObject.put("flag", 0);
//								jsonObject.put("startTime", URLEncoder.encode(countTime.getBeginTime(), "UTF-8"));
//								jsonObject.put("endTime", URLEncoder.encode(countTime.getEndTime(),"UTF-8"));
//								jsonObject.put("set_worker_time",URLEncoder.encode(s, "UTF-8"));
//								jsonObject.put("flag", URLEncoder.encode(""+0, "UTF-8"));
//								jsonObject.put("worker_name", URLEncoder.encode(noSelectName.get(j), "utf-8"));
								jsonObject.put("worker_name",noSelectName.get(j));
								jsonObject.put("factory_id", factory_id);
								
								jsonArray.put(jsonObject);
								jsonObject = null;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
		//					
//							values.put("flag", 0);
//							values.put("colorflage", 0);
		
//							db.update("people", values, "name = ? and time = ?",new String[] { noSelectName.get(j), s });
							db.delete("people", "name = ? and time = ?", new String[] { noSelectName.get(j), s });
							System.out.println("ԭ��ѡ�Ϻ�����ѡ���˲嵽���ݿ�û��");
						}
						noSelectName.clear();
						for (int i = 0; i < selectName.size(); i++) {
							jsonObject = new JSONObject();
//							Log.e("sss", selectName.get(i));
							
							try {
								jsonObject.put("startTime", URLEncoder.encode(countTime.getBeginTime(), "UTF-8"));
								jsonObject.put("endTime", URLEncoder.encode(countTime.getEndTime(),"UTF-8"));
								jsonObject.put("set_worker_time",URLEncoder.encode(s, "UTF-8"));
								jsonObject.put("flag", URLEncoder.encode(""+1, "UTF-8"));
//								jsonObject.put("worker_name", URLEncoder.encode(selectName.get(i), "utf-8"));
								jsonObject.put("worker_name", selectName.get(i));
								jsonObject.put("factory_id", factory_id);
		//						jsonObject.put("name", selectName.get(i));
								jsonArray.put(jsonObject);
								jsonObject = null;
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} 
							
							values.put("flag", 1);
							values.put("colorflage", 0);
							values.put("name", selectName.get(i));
							values.put("time", s);
		
//							db.update("people", values, "name = ? and time = ?",new String[] { selectName.get(i), s });
							db.insert("people", null, values);
							Log.e("ѡ����˲嵽���ݿ�û", "yes");
//							if(cursorId.moveToFirst()){
//								do {
//									publicText.setText(cursorselect.getString(cursorselect.getColumnIndex("name")));
//									System.out.println("���ݿ�������");
//								} while (cursorId.moveToNext());
//								}
		
						}
						
						selectName.clear();
		//				Log.e("objectarray", jsonArray.toString());
		return jsonArray;
	}



	/**
	 * ����Ա��������TextView����ʾ���ʱ��ΰ��ŵ�һ����Ա������
	 * @param s ��ʾ��Ա������ʱ���
	 */
	private void setTextofRmove(final String s) {
//		String publicTextString = publicText.getText().toString();
//		Cursor cursor = db.query("people", null, "flag = ?and time = ?", new String []{"1",s}, null, null, null);
//		if(cursor.moveToFirst()){
//			do {
//				String name = cursor.getString(cursor.getColumnIndex("name"));
//				
//				publicText.setText(name+"...");
//				//��������жϣ��������if�����жϣ��߳̾ͻᱻɱ������֪��Ϊʲô
//				if(publicTextString.equals(name)) publicTextString = publicTextString + "1";
//			} while (cursor.moveToNext());
//		}
		int flagNub = 0;
		int idNub = 0;
		Cursor cursorNull = db.query("people", null, "flag = ?and time = ?", new String []{"0",s}, null, null, null);
		if(cursorNull.moveToFirst()){
		do {
			flagNub++;
				
		} while (cursorNull.moveToNext());
}
		
		Cursor cursorId = db.query("people", null, " time = ?", new String []{s}, null, null, null);
		if(cursorId.moveToFirst()){
		do {
			idNub++;
		} while (cursorId.moveToNext());
}
		if(flagNub == idNub) publicText.setText("�밲����Ա");
		
		else if (flagNub != idNub){
			Cursor cursorselect = db.query("people", null, "flag = ?and time = ?", new String []{"1",s}, null, null, null);
			if(cursorselect.moveToFirst()){
			do {
				publicText.setText(cursorselect.getString(cursorselect.getColumnIndex("name"))+"...");
				
			} while (cursorselect.moveToNext());
			}
		}
//		if(publicText.getText().toString().equals(publicTextString) ){
//			
//			publicText.setText("�밲����Ա");}
	}



	private SpeTimePeopleThread speTimePeo(final Handler mHandler) {
		list.clear();
		String sendStr = "tasktime="+publicStr+"&taskdate="+countTime.getBeginTime()
				+"&factory_id="+factory_id;
		SpeTimePeopleThread thread = new SpeTimePeopleThread(sendStr, mHandler, list);
		return thread;
	}




	

}
