package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.logging.LogRecord;

import com.example.aql.People_DBHelper;
import com.example.generalizationdemo.R;
import com.example.thread.PeopleStartThread;
import com.example.thread.TaskStartThread;


import com.example.wechatsample.PeoplePage;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 * ����ʱ��ѡ��ؼ� ʹ�÷����� private EditText inputDate;//��Ҫ���õ�����ʱ���ı��༭�� private String
 * initDateTime="2012��9��3�� 14:44",//��ʼ����ʱ��ֵ �ڵ���¼���ʹ�ã�
 * inputDate.setOnClickListener(new OnClickListener() {
 * 
 * @Override public void onClick(View v) { DateTimePickDialogUtil
 *           dateTimePicKDialog=new
 *           DateTimePickDialogUtil(SinvestigateActivity.this,initDateTime);
 *           dateTimePicKDialog.dateTimePicKDialog(inputDate);
 * 
 *           } });
 * 
 * @author
 */
public class DateTimePickDialogUtil implements OnDateChangedListener,
		OnTimeChangedListener {
	private DatePicker datePicker;
	private TimePicker timePicker;
	private AlertDialog ad;
	private String dateTime;
	private String initDateTime;
	private FragmentActivity activity;
	private int dateWeek;
	private String flag;
	private People_DBHelper dbHelper;
	private ShowTextview showTextview;
	public  String factory_id = com.example.generalizationdemo.HomePageya.factory_id;
	/**
	 * ����ʱ�䵯��ѡ����캯��
	 * 
	 * @param activity
	 *            �����õĸ�activity
	 * @param initDateTime
	 *            ��ʼ����ʱ��ֵ����Ϊ�������ڵı��������ʱ���ʼֵ
	 */
	public DateTimePickDialogUtil(FragmentActivity activity, String initDateTime,String flag,People_DBHelper dbHelper,ShowTextview showTextview) {
		this.activity = activity;
		this.initDateTime = initDateTime;
		this.flag = flag;
		this.dbHelper = dbHelper;
		this.showTextview = showTextview;
	}

	public void init(DatePicker datePicker, TimePicker timePicker) {
		Calendar calendar = Calendar.getInstance();
		if (!(null == initDateTime || "".equals(initDateTime))) {
			calendar = this.getCalendarByInintData(initDateTime);
		} else {
			initDateTime = calendar.get(Calendar.YEAR) + "-"
					+ calendar.get(Calendar.MONTH) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
		}
		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), this);
	}

	/**
	 * ��������ʱ��ѡ��򷽷�
	 * 
	 * @param inputDate
	 *            :Ϊ��Ҫ���õ�����ʱ���ı��༭��
	 * @return
	 */
	public AlertDialog dateTimePicKDialog(final TextView inputDate,final TextView inputWeek) {
//	public AlertDialog onDateChanged( final TextView inputDate,final TextView inputWeek) {
		  final String removeWeek = inputWeek.getText().toString();
		LinearLayout dateTimeLayout = (LinearLayout) activity
				.getLayoutInflater().inflate(R.layout.common_datetime, null);
		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
		timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
		init(datePicker, timePicker);
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(this);

		ad = new AlertDialog.Builder(activity)
				.setTitle(initDateTime)
				.setView(dateTimeLayout)
				.setPositiveButton("ȡ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						datePicker.clearFocus();
						onDateChanged(null, 0, 0, 0);
                        if(inputDate.getText().toString().equals("")){

                        	
                        	inputDate.setText("");
						}
						
					}
				})
				.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						datePicker.clearFocus();
						onDateChanged(null, 0, 0, 0);
						inputDate.setText(dateTime);
						inputWeek.setText("��"+dateWeek+"��");
						if(!removeWeek.equals(inputWeek.getText().toString())){
						//���Ǹı�ʱ��õ�������
//						if(flag.equals("peoplePage")){
//							dbHelper.deletepeople();
//							SQLiteDatabase	db = dbHelper.getWritableDatabase();
//							ContentValues values = new ContentValues();
//							values.put("name", "С��3");
//							values.put("flag", 1);
//							values.put("colorflage", 0);
//							values.put("time", "mo_1");
////							
//							db.insert("people", null, values);
//							values.clear();
//							values.put("name", "С��1");
//							values.put("flag", 1);
//							values.put("colorflage", 1);
//							values.put("time", "mo_1");
//							db.insert("people", null, values);
//							values.clear();
//							values.put("name", "С��2");
//							values.put("flag", 0);
//							values.put("colorflage", 0);
//							values.put("time", "mo_1");
//							db.insert("people", null, values);
//							Log.e("DateTimePickDialogUtil������������", "Yes");
//							showTextview.showText();
//							}
//							else{
//								dbHelper.deletetask();
//								SQLiteDatabase	db = dbHelper.getWritableDatabase();
//								ContentValues values = new ContentValues();
//								values.put("name", "Ѳ������3");
//								values.put("mission_condition", 1);
//								values.put("mission_id", 0);
//								values.put("time", "mo_1");
////								
//								db.insert("task", null, values);
//								values.clear();
//								values.put("name", "Ѳ������1");
//								values.put("mission_condition", 2);
//								values.put("mission_id", 1);
//								values.put("time", "mo_1");
//								db.insert("task", null, values);
//								values.clear();
//								values.put("name", "Ѳ������2");
//								values.put("mission_condition", 0);
//								values.put("mission_id", 0);
//								values.put("time", "mo_1");
//								db.insert("task", null, values);
//								Log.e("DateTimePickDialogUtil������������", "Yes");
//								showTextview.showText();
//							}	
//						
						
//					Handler	handlerStart = new Handler(){
//							public void handleMessage(Message msg){
//								if(msg.what == 12138){
//									String backMsg = msg.obj.toString();
////									Log.e("startDate", backMsg);
//									if(!backMsg.equals(null)){
//										JSONStart jsonStart = new JSONStart();
//						if(flag.equals("peoplePage"))
//										jsonStart.peopleArrange(backMsg);
//						else	jsonStart.taskArrange(backMsg);
//									}
//								}
//							}
//
//						
//						};
//						CountTime countTime = new CountTime(dateWeek+"");
						CountTime countTime = new CountTime(dateTime);
						String value = null;
						
						if(flag.equals("peoplePage")){
						 value ="time1=" +countTime.getBeginTime()+ "&time2="+ countTime.getEndTime()
								 +"&factory_id="+factory_id;
						 PeopleStartThread thread = new PeopleStartThread(value);
						  new Thread(thread).start();
						}else{
						 value ="time1=" +countTime.getBeginTime()+ "&time2="+ countTime.getEndTime()
								 +"&factory_id="+factory_id;
						 TaskStartThread thread = new TaskStartThread(value);
						 new Thread(thread).start();
						}
//						PeopleStartThread thread = new PeopleStartThread(value, handlerStart);
						}

					}
				}).show();

		return ad;
	}

	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		onDateChanged(null, 0, 0, 0);
	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// �������ʵ��
		Calendar calendar = Calendar.getInstance();

		calendar.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		dateTime = sdf.format(calendar.getTime());
		ad.setTitle(dateTime);
		Date date = null;
		try {
			 date = sdf.parse(dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		dateWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		
	}

	/**
	 * ʵ�ֽ���ʼ����ʱ��2012-07-02 ��ֳ�2012 07 02,����ֵ��calendar
	 * 
	 * @param initDateTime
	 *            ��ʼ����ʱ��ֵ �ַ�����
	 * @return Calendar
	 */
	private Calendar getCalendarByInintData(String initDateTime) {
		Calendar calendar = Calendar.getInstance();
		int currentYear = Integer.parseInt(initDateTime.substring(0, 4));
		int currentMonth = Integer.parseInt(initDateTime.substring(5, 7)) - 1;
		int currentDay = Integer.parseInt(initDateTime.substring(8,
				initDateTime.length()));

		calendar.set(currentYear, currentMonth, currentDay);

		return calendar;
	}
	

}
