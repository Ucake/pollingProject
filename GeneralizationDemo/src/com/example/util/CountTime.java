package com.example.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.util.Log;
import android.widget.TextView;

/**
 * @author lson
 *这个类是计算周数和时间的
 */
public class CountTime {

	private String time;
	private String beginTime;
	private String endTime;

	public CountTime(String time) {
		super();
		this.time = time;
//		Log.e("tiem", time);
		convertWeekByDate();
	}
	public String getBeginTime() {

		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	/**
	 * 这个方法是计算给定时间后，这个时间所在周的周一的日期和周日的日期
	 */
	private  void convertWeekByDate() {  

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
        	Date date = null;
			try {
				date = sdf.parse(time);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        Calendar cal = Calendar.getInstance();  

        cal.setTime(date);  

        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  

        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  

        if(1 == dayWeek) {  

            cal.add(Calendar.DAY_OF_MONTH, -1);  

        }  

        System.out.println("要计算日期为:"+sdf.format(cal.getTime())); //输出要计算日期  

        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  

        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  

        cal.add(Calendar.DATE, cal.getFirstDayOfWeek()-day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   

        String imptimeBegin = sdf.format(cal.getTime());  
        
        System.out.println("所在周星期一的日期："+imptimeBegin);  
        this.setBeginTime(imptimeBegin);
        
        cal.add(Calendar.DATE, 6);  

        String imptimeEnd = sdf.format(cal.getTime());  

        System.out.println("所在周星期日的日期："+imptimeEnd);

        this.setEndTime(imptimeEnd);
        
}

	/**
	 * 这个方法是计算当前日期所在的周数
	 * @param inputdateF
	 * @return
	 */
	public static int inputWeek(TextView inputdateF){
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	     Date date = null;
		try {
			date = format.parse(inputdateF.getText().toString());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	     Calendar calendar = Calendar.getInstance();
	     calendar.setFirstDayOfWeek(Calendar.MONDAY);
	     calendar.setTime(date);
	     
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	
}
