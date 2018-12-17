package com.generalization.chart;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.astuetz.PagerSlidingTabStrip;
import com.example.generalizationdemo.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.ViewConfiguration;
import android.view.Window;

public class ChartActivity extends FragmentActivity {

	
/**
	 * 柱状图的Fragment
	 */
	
private BarBarActivity chatFragment;

	
/**
	 * 折线图的Fragment
	 */
	
private LineChartActivity foundFragment;

	
/**
	 * 饼图的Fragment
	 */
	
private PieChartActivity contactsFragment;

	
/**
	 * PagerSlidingTabStrip的实例
	 */
	
private PagerSlidingTabStrip tabs;

	
/**
	 * 获取当前屏幕的密度
	 */
	
private DisplayMetrics dm;

	
@Override
	
protected void onCreate(Bundle savedInstanceState) {
		
super.onCreate(savedInstanceState);
		
setContentView(R.layout.main_activity_);
		
setOverflowShowingAlways();
		
dm = getResources().getDisplayMetrics();
		
ViewPager pager = (ViewPager) findViewById(R.id.pager);
		
tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		
pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		
tabs.setViewPager(pager);
		
setTabsValue();
	}

	/**
	 * 对PagerSlidingTabStrip的各项属性进行赋值。
	 */
	
private void setTabsValue() {
		
// 设置Tab是自动填充满屏幕的
		
tabs.setShouldExpand(true);
		
// 设置Tab的分割线是透明的
		
tabs.setDividerColor(Color.TRANSPARENT);
		
// 设置Tab底部线的高度
		
tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				
TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		
// 设置Tab Indicator的高度
		
tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				
TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		
// 设置Tab标题文字的大小
		
tabs.setTextSize((int) TypedValue.applyDimension(
				
TypedValue.COMPLEX_UNIT_SP, 16, dm));
		
// 设置Tab Indicator的颜色
		
tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		
// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		
tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		
// 取消点击Tab时的背景色
		
tabs.setTabBackground(0);
	}

	
public class MyPagerAdapter extends FragmentPagerAdapter {

		
public MyPagerAdapter(FragmentManager fm) {
			
super(fm);
		}

		
private final String[] titles = { "柱状图", "折线图", "饼图" };

		
@Override
		
public CharSequence getPageTitle(int position) {
			
return titles[position];
		}

		
@Override
		
public int getCount() {
			
return titles.length;
		
}

		
@Override
		
public Fragment getItem(int position) {
			
switch (position) {
			
case 0:
				
if (chatFragment == null) {
					
chatFragment = new BarBarActivity();
				
}
				
return chatFragment;
			
case 1:
				
	if (foundFragment == null) {
			
		foundFragment = new LineChartActivity();
		
		}
				
return foundFragment;
			
case 2:
				
if (contactsFragment == null) {
				
	contactsFragment = new PieChartActivity();
				
}
				
return contactsFragment;
	
		default:
		
		return null;
			
}
		
}

	
}

	
		private void setOverflowShowingAlways() {
		
				try {
			
					ViewConfiguration config = ViewConfiguration.get(this);
			
					Field menuKeyField = ViewConfiguration.class

							.getDeclaredField("sHasPermanentMenuKey");
			
					menuKeyField.setAccessible(true);
			
					menuKeyField.setBoolean(config, false);
		
				} catch (Exception e) {
			
					e.printStackTrace();
		

				}
	

}

}
