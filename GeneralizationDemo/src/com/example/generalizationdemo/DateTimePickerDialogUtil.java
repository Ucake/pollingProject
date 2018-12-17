package com.example.generalizationdemo;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.example.generalizationdemo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 * 日期时间选择控件 使用方法： private EditText inputDate;//需要设置的日期时间文本编辑框 private String
 * initDateTime="2012年9月3日 14:44",//初始日期时间值 在点击事件中使用：
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
public class DateTimePickerDialogUtil implements OnDateChangedListener,
		OnTimeChangedListener {
	private DatePicker datePicker;
	private TimePicker timePicker;
	private AlertDialog ad;
	private String dateTime;
	private String initDateTime;
	private Activity activity;
	//2018-07-12号破芸填写，因为在任务管理模块中结束时间如果是选择的那一天则大橙子师兄是按着这一天的00:00:00:返回的
	//姑这一天的内容查不到，大橙子师兄不改那就只能我改了，我就改成了选择的这一天加一天，这个参数是判断是不是结束时间的那个编辑框
	//如果是就加一天如果不是就不管
	private boolean isAddOne;
	/**
	 * 日期时间弹出选择框构造函数
	 * 
	 * @param activity
	 *            ：调用的父activity
	 * @param initDateTime
	 *            初始日期时间值，作为弹出窗口的标题和日期时间初始值
	 */
	public DateTimePickerDialogUtil(Activity activity, String initDateTime,boolean isAddOne) {
		this.activity = activity;
		this.initDateTime = initDateTime;
		this.isAddOne = isAddOne;
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
	 * 弹出日期时间选择框方法
	 * 
	 * @param inputDate
	 *            :为需要设置的日期时间文本编辑框
	 * @return
	 */
	public AlertDialog dateTimePicKDialog(final EditText inputDate) {
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
				.setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

						datePicker.clearFocus();
						onDateChanged(null, 0, 0, 0);
                        if(inputDate.getText().toString().equals("")){
                        	inputDate.setText("");
						}
						
					}
				})
				.setPositiveButton(activity.getString(R.string.sure), new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						datePicker.clearFocus();
						onDateChanged(null, 0, 0, 0);
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
		// 获得日历实例
		Calendar calendar = Calendar.getInstance();

		calendar.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(isAddOne)
			
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		dateTime = sdf.format(calendar.getTime());
		ad.setTitle(dateTime);
	}

	/**
	 * 实现将初始日期时间2012-07-02 拆分成2012 07 02,并赋值给calendar
	 * 
	 * @param initDateTime
	 *            初始日期时间值 字符串型
	 * @return Calendar
	 */
	private Calendar getCalendarByInintData(String initDateTime) {
		Calendar calendar = Calendar.getInstance();
		int currentYear = Integer.parseInt(initDateTime.substring(0, 4));
		int currentMonth = Integer.parseInt(initDateTime.substring(5, 7)) - 1;
		int currentDay = Integer.parseInt(initDateTime.substring(8,
				initDateTime.length()));

		calendar.set(currentYear, currentMonth, currentDay);
		// 今天+1天  
 
		return calendar;
	}

}
