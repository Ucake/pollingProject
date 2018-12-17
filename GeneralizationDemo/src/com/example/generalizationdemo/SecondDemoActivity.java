package com.example.generalizationdemo;

import com.example.generalizationdemo.R;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SecondDemoActivity extends Activity implements OnClickListener {
	 private RelativeLayout btn3;// “更多”按钮布局 
	 private RelativeLayout btn2;
	 private RelativeLayout btn1;
	 
	 private TextView text2;
	 
	 private LinearLayout childLayout3;// “更多”按钮的子菜单 
	 private boolean flag3 = false;// 子菜单显示状态标记  
	 private boolean open3 = true;// 子菜单填充状态标记  
	 private LayoutInflater inflater3; 
	 private View view3;  
	 private LinearLayout popLayout3;// 弹出的子菜单父框架布局  
	 private Button help2;
	 private Button help1;
	 private Button help3;
	 
	 private LinearLayout childLayout1;
	 private boolean flag1 = false;
	 private boolean open1 = true;
	 private LayoutInflater inflater1;
	 private View view1;
	 private LinearLayout popLayout1;
	 private Button question1;
	 
	@Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.bottom_layout);
       
       inflater3 = SecondDemoActivity.this.getLayoutInflater();
       
       
       popLayout3 = (LinearLayout) findViewById(R.id.pop_layout3);
       popLayout1 = (LinearLayout) findViewById(R.id.pop_layout1);
       
       btn3 = (RelativeLayout) findViewById(R.id.btn3);  
       btn2 = (RelativeLayout) findViewById(R.id.btn2);
       btn1 = (RelativeLayout) findViewById(R.id.btn1);
//       text2 = (TextView) findViewById(R.id.text2);
       
       btn3.setOnClickListener(this); 
       btn2.setOnClickListener(this);
       btn1.setOnClickListener(this);
//       text2.setOnClickListener(this);

       view1 = inflater3.inflate(R.layout.child_menu1, popLayout1, true);  
       childLayout1 = (LinearLayout) view1.findViewById(R.id.child_layout1);
       childLayout1.setVisibility(View.GONE);
       view3 = inflater3.inflate(R.layout.child_menu3, popLayout3, true);  
       childLayout3 = (LinearLayout) view3.findViewById(R.id.child_layout3); 
       childLayout3.setVisibility(View.GONE);
   }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();  
       switch (id) {  
       case R.id.btn3:  
           btn3Click(); 
    	   
           break; 
         //底部的三个按钮中的“扫一扫”
       case R.id.btn2:
    	   Intent intent2 = new Intent();
	        	intent2.setClass(SecondDemoActivity.this, CaptureActivity.class);
	        	startActivity(intent2);
    	
       	break;
       case R.id.btn1:
    	   btn1Click();
	        	break;
	        	
//       case R.id.test2:
//    	   
//    	   Intent intent3 = new Intent();
//	        	intent3.setClass(SecondDemoActivity.this, CaptureActivity.class);
//	        	startActivity(intent3);
//    	   
//       	break;
           default:
           	break;
       }
	}
	
	public void btn3Click() {// 更多  1.位置设置  2.网络设置  3.系统设置
       if (open3 == true) {  
//           view3 = inflater3.inflate(R.layout.child_menu3, popLayout3, true);  
//           childLayout3 = (LinearLayout) view3.findViewById(R.id.child_layout3); 
    	   
    	   //“更多”中的2.WLAN设置
    	   
    	   help1 = (Button)view3.findViewById(R.id.more1);
           help1.setOnClickListener(new OnClickListener() {
   			
   			@Override
   			public void onClick(View v) {
   				// TODO Auto-generated method stub
   				startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),0); 
   			}
   		});
    	   
           help2 = (Button)view3.findViewById(R.id.more2);
           help2.setOnClickListener(new OnClickListener() {
   			
   			@Override
   			public void onClick(View v) {
   				// TODO Auto-generated method stub
   				startActivityForResult(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS),0);
   			}
   		});
           
           help3 = (Button)view3.findViewById(R.id.more3);
           help3.setOnClickListener(new OnClickListener() {
   			
   			@Override
   			public void onClick(View v) {
   				// TODO Auto-generated method stub
   				startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS),0);  
   			}
   		});
           
           open3 = false;  
       }  
 
       if (flag3 == false) {  
           flag3 = true;  
           childLayout3.setVisibility(View.VISIBLE);  
           childLayout1.setVisibility(View.GONE);
           flag1 = false;
       } else {
    	   flag3 = false;
    	   childLayout3.setVisibility(View.GONE); 
       }
   }  
	
	public void btn1Click() {// 告警  
	       if (open1 == true) {  
//	           view1 = inflater3.inflate(R.layout.child_menu1, popLayout1, true);  
//	           childLayout1 = (LinearLayout) view1.findViewById(R.id.child_layout1); 
	           question1 = (Button)view1.findViewById(R.id.question1);
	           question1.setOnClickListener(new OnClickListener() {
	   			
	   			@Override
	   			public void onClick(View v) {
	   				// TODO Auto-generated method stub
	   				Intent intent = new Intent();
	   	        	intent.setClass(SecondDemoActivity.this, QuestionActivity.class);
	   	        	startActivity(intent);
	   			}
	   		});
	           open1 = false;  
	       }  
	 
	       if (flag1 == false) {  
	           flag1 = true;  
	           childLayout1.setVisibility(View.VISIBLE); 
	           childLayout3.setVisibility(View.GONE);
	           flag3=false;
	       } else {  
	           flag1 = false;  
	           childLayout1.setVisibility(View.GONE);  
	       }  
	   }
	
}
