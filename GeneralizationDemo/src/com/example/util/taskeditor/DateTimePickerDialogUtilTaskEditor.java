package com.example.util.taskeditor;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.generalizationdemo.R;
import com.example.taskeditor.taskeditor.TaskSendActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
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
public class DateTimePickerDialogUtilTaskEditor implements OnDateChangedListener,
		OnTimeChangedListener {
	private DatePicker datePicker;
	private TimePicker timePicker;
	private AlertDialog ad;
	private String dateTime;
	private String initDateTime;
	private Activity activity;
	private int week_index = 0;
	public static ArrayList<String> nameList ;
	public static String sendtaskTime = "";
	public  String arrang_time = "";
	public Runnable timeRunnable = null;
	private  Handler handler;
	public  String factory_id = com.example.generalizationdemo.HomePageya.factory_id;
	
	

	/**
	 * ����ʱ�䵯��ѡ����캯��
	 * 
	 * @param activity
	 *            �����õĸ�activity
	 * @param initDateTime
	 *            ��ʼ����ʱ��ֵ����Ϊ�������ڵı��������ʱ���ʼֵ
	 */
	public DateTimePickerDialogUtilTaskEditor(Activity activity, String initDateTime) {
		this.activity = activity;
		this.initDateTime = initDateTime;
		nameList = new ArrayList<String>();

	}
	public DateTimePickerDialogUtilTaskEditor(Activity activity, String initDateTime , Handler handler) {
		this.activity = activity;
		this.initDateTime = initDateTime;
		nameList = new ArrayList<String>();
		this.handler = handler;

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
	public AlertDialog dateTimePicKDialog(final EditText inputDate,final boolean isMore,final String formTime,final ArrayList<String> list,final Spinner spinner) {
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
				.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						datePicker.clearFocus();
						onDateChanged(null, 0, 0, 0);
                        if(inputDate.getText().toString().equals("")){
                        	inputDate.setText("");
						}
						
					}
				})
				.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						datePicker.clearFocus();
						onDateChanged(null, 0, 0, 0);
						
						if(dateTime.compareTo(formTime) < 0){
							
							AlertDialog.Builder builder_day = new AlertDialog.Builder(activity);
							builder_day.setTitle("注意：");
							builder_day.setMessage("请选择从今以后的时间");
							builder_day.setPositiveButton("YES", null);
							builder_day.create().show();
						}
						else if(isMore){
							final String[] taskTime = {"mo_7","mo_1","mo_2","mo_3","mo_4","mo_5","mo_6","af_7","af_1","af_2","af_3","af_4","af_5","af_6"};
							final String[] time = {"白天","晚上"};
							AlertDialog.Builder builder_time = new AlertDialog.Builder(activity);
							builder_time.setTitle("选择时间段：");
							builder_time.setSingleChoiceItems(time, 0,new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(final DialogInterface dialog, final int which) {
									// TODO Auto-generated method stub
									
									
									timeRunnable  = new Runnable(){
										@Override
										public void run() {
											// TODO Auto-generated method stub
									OutputStream out = null;
									BufferedReader reader = null;
									try {
											
											if(which == 0){
												sendtaskTime = taskTime[week_index];
											}else{
												 sendtaskTime = taskTime[week_index + 7];
											}
										
										 
										URL url = new URL("http://123.206.16.157:8080/water/arrange.req?action=arrangelist");
										HttpURLConnection conn = (HttpURLConnection) url.openConnection();
										conn.setRequestMethod("POST");
										conn.setDoOutput(true);
										conn.setDoInput(true);
										conn.setConnectTimeout(5000);
										conn.setReadTimeout(5000);
										out = conn.getOutputStream();
										out.write(("taskcreate="+"任务生成"+"&tasktime="+sendtaskTime+"&taskdate="+dateTime
												+"&factory_id="+factory_id).getBytes());
										reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
										String line;
										StringBuilder builder = new StringBuilder();
										while((line = reader.readLine())!=null){
											builder.append(line);
										}
										System.out.println("要人员时返回的人员"+builder.toString());
										JSONObject json = new JSONObject(builder.toString());
										//有数据时要这几段
//										String result = json.getString("name");
//										String[] result_array = result.split(";");
//										for (int i = 0; i < result_array.length; i++) {
//											nameList.add(result_array[i]);
//										}
											
											//taskTimeStr = taskTime[which];
										arrang_time = dateTime+","+time[which];
										Toast.makeText(activity, "选择的时间段为："+arrang_time, Toast.LENGTH_LONG).show();
										
										dialog.dismiss();
										inputDate.setText(arrang_time);
										System.out.println("选择的时间段："+arrang_time);
										JSONArray data_array = json.getJSONArray("data");
										String[] nameStr = new String[data_array.length()];
										
//										ArrayList<String> listPeople = new ArrayList<String>();
										for (int i = 0; i < data_array.length(); i++) {
											JSONObject data = data_array.getJSONObject(i);
											String flag = data.getString("flag");
											String name = data.getString("name");
											list.add(name);
											if("0".equals(flag)){
//												listPeople.add(name);
												nameStr[i] = name;
											}else{
												nameStr[i] = name+"(排班)";
//												listPeople.add(name+"(排班)");
											}
										}
										
//										for (int i = 0; i < 5; i++) {
//											//nameStr[i] = nameList.get(i);
//											nameStr[i] = "小宝"+i;
//										}
										
										com.example.adapter.taskeditor.SpinnerAdapter adapter = new com.example.adapter.taskeditor.SpinnerAdapter(activity, nameStr, 20, "#080808"); 
										spinner.setAdapter(adapter);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
										}
									};
									activity.runOnUiThread(timeRunnable);
								}
							});
							
							builder_time.create().show();
							
						}
						if(!isMore)
							inputDate.setText(dateTime);
						
						
						

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
		week_index = calendar.get(Calendar.DAY_OF_WEEK)- 1;
		if(week_index < 0)
			week_index = 0;
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
