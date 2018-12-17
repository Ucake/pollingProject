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
	 * ��״ͼ��Fragment
	 */
	
private BarBarActivity chatFragment;

	
/**
	 * ����ͼ��Fragment
	 */
	
private LineChartActivity foundFragment;

	
/**
	 * ��ͼ��Fragment
	 */
	
private PieChartActivity contactsFragment;

	
/**
	 * PagerSlidingTabStrip��ʵ��
	 */
	
private PagerSlidingTabStrip tabs;

	
/**
	 * ��ȡ��ǰ��Ļ���ܶ�
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
	 * ��PagerSlidingTabStrip�ĸ������Խ��и�ֵ��
	 */
	
private void setTabsValue() {
		
// ����Tab���Զ��������Ļ��
		
tabs.setShouldExpand(true);
		
// ����Tab�ķָ�����͸����
		
tabs.setDividerColor(Color.TRANSPARENT);
		
// ����Tab�ײ��ߵĸ߶�
		
tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				
TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		
// ����Tab Indicator�ĸ߶�
		
tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				
TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		
// ����Tab�������ֵĴ�С
		
tabs.setTextSize((int) TypedValue.applyDimension(
				
TypedValue.COMPLEX_UNIT_SP, 16, dm));
		
// ����Tab Indicator����ɫ
		
tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		
// ����ѡ��Tab���ֵ���ɫ (�������Զ����һ������)
		
tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		
// ȡ�����Tabʱ�ı���ɫ
		
tabs.setTabBackground(0);
	}

	
public class MyPagerAdapter extends FragmentPagerAdapter {

		
public MyPagerAdapter(FragmentManager fm) {
			
super(fm);
		}

		
private final String[] titles = { "��״ͼ", "����ͼ", "��ͼ" };

		
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