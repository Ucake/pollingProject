package com.example.generalizationdemo;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.generalizationdemo.R;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ForgetActivity extends Activity{
	
	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	
	private Button submitMF;
	private EditText original_password;
	private EditText new_password1;
	private EditText new_password2;
	private Handler handler;
	
	private String string_original_password;
    private String string_new_password1;
    private String string_new_password2;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpage);
        submitMF = (Button) findViewById(R.id.submitM);
        original_password = (EditText) findViewById(R.id.original_password);
        new_password1 = (EditText) findViewById(R.id.new_password1);
        new_password2 = (EditText) findViewById(R.id.new_password2);
        
        string_original_password = original_password.getText().toString();
        string_new_password1 = new_password1.getText().toString();
        string_new_password2 = new_password2.getText().toString();
        
        
        submitMF.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
//				if(string_original_password.equals("123456")){
//					Toast showToast=Toast.makeText(ForgetActivity.this, "请输入原密码！", Toast.LENGTH_SHORT);
//			    	showToast.show();
//				
//				}else if(string_new_password1.equals(" ")||string_new_password2.equals(" ")){
//							Toast showToast=Toast.makeText(ForgetActivity.this, "请输入新密码！", Toast.LENGTH_SHORT);
//					    	showToast.show();
//				}else if(string_new_password1.equals(string_new_password2)){
//						Toast showToast=Toast.makeText(ForgetActivity.this, "请输入相同的新密码！", Toast.LENGTH_SHORT);
//				    	showToast.show();
//				}else{
				
				
				if(original_password.getText().toString().trim().equals("")){
					Toast showToast=Toast.makeText(ForgetActivity.this, "请输入原密码！", Toast.LENGTH_SHORT);
			    	showToast.show();
				}else if(new_password1.getText().toString().trim().equals("")){
					Toast showToast=Toast.makeText(ForgetActivity.this, "请输入新密码！", Toast.LENGTH_SHORT);
			    	showToast.show();
				}else if(new_password1.getText().toString().trim().equals(new_password2.getText().toString().trim())){
				
				
				handler = new Handler(){
					
					public void handleMessage(Message msg){
						super.handleMessage(msg);
						if (msg.what == 12138) {
							
							String backMsg = msg.obj.toString();	
							Log.v("READYYYYYYYYYYY", backMsg);
							try{
							JSONObject toor = new JSONObject(backMsg);
							
							String result = toor.getString("result");
							
							if(result.equals("10000")){
								Toast showToast=Toast.makeText(ForgetActivity.this, "密码修改成功！需要重新登录！", Toast.LENGTH_SHORT);
						    	showToast.show();
								
						    	MainActivity.accounts.setText("");
						    	MainActivity.password.setText("");
//						    	editor.putBoolean("isAutoLogin", true);
						    	
						    	 preferences = getSharedPreferences("user", MODE_PRIVATE);
								 editor = preferences.edit();
								 editor.clear();
								 editor.commit();
//								 Toast.makeText(ForgetActivity.this, "重新登录！", Toast.LENGTH_SHORT).show();
							
								 Intent intent2 = new Intent();
								 intent2.setClass(ForgetActivity.this, MainActivity.class);
								 startActivity(intent2);
						    	
						    	
//						    	Intent intent = new Intent();
//						    	intent.setClass(ForgetActivity.this, HomePage.class);
//						    	startActivity(intent);
						    	
							}else if (result.equals("10002")){
								Toast showToast=Toast.makeText(ForgetActivity.this, "用户名输入错误！", Toast.LENGTH_SHORT);
						    	showToast.show();
							}else if (result.equals("10003")){
								Toast showToast=Toast.makeText(ForgetActivity.this, "密码输入错误！", Toast.LENGTH_SHORT);
						    	showToast.show();
							}
							
							
							} catch (JSONException e) {
						        e.printStackTrace();
						        Toast showToast=Toast.makeText(ForgetActivity.this, "没有响应，请稍后重试！", Toast.LENGTH_SHORT);
						    	showToast.show();
						    }
						
							
						
						}
					}

				};
				
//				String value = "phone="+ MainActivity.TEL+"&password1="+original_password.getText()+"&password2="+new_password1.getText();
//				ChangePasswordThread thread = new ChangePasswordThread(handler, value);
//				new Thread(thread).start();
				}else{
					Toast showToast=Toast.makeText(ForgetActivity.this, "请两次输入相同的密码！", Toast.LENGTH_SHORT);
			    	showToast.show();
				}
				
			}
		});
	}
}
