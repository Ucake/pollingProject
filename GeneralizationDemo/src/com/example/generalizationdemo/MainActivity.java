package com.example.generalizationdemo;

import org.json.JSONException;
import org.json.JSONObject;

import com.cement.thread.LoginThread;
import com.cement.thread.SendMessageThread;
import com.example.generalizationdemo.R;

import android.support.v4.content.LocalBroadcastManager;
//import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends Activity implements OnClickListener {
	
	public static MainActivity instance = null;
	private Button login;
	private String name;
	private String status;
	public static String statusya;

	private SharedPreferences preferences;
	private SharedPreferences.Editor editor;
	public static String TEL;
	public static String PW;
	public static EditText accounts;
	public static EditText password;
	public static String factory_id;
	private String workshop_id;
	private CheckBox autologin;
	private Handler handler;
	private Handler handler_sendmessage;
	private TextView sendmessage;
	public static EditText message;
	public static String heiheihei;
	
	public static boolean isForeground = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        JPushInterface.setDebugMode(true);//如果时正式版就改成false
        JPushInterface.init(this);
        
        instance = this;
        login = (Button) findViewById(R.id.login_generalization);
        accounts = (EditText) findViewById(R.id.accounts);
        password = (EditText) findViewById(R.id.password);
        autologin = (CheckBox) findViewById(R.id.checkbox);
        sendmessage = (TextView) findViewById(R.id.sendmessage);
        message = (EditText) findViewById(R.id.message);
        
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        
        statusya = preferences.getString("status", "worker");
        
        boolean isFirstRun = preferences.getBoolean("isFirstRun", true);
        editor = preferences.edit();
		boolean isAutoLogin = preferences.getBoolean("isAutoLogin",false);
		
		login.setOnClickListener(this);
		
		sendmessage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("点击获取验证码了吗？");
				
				handler_sendmessage = new Handler(){
					
					public void handleMessage(Message msg){
						super.handleMessage(msg);
						if (msg.what == 12138) {
							
						String backMsg = msg.obj.toString();
						
						Log.v("sanciwoshou", backMsg);
						try{
							
							JSONObject toor = new JSONObject(backMsg);
//							JSONObject result1 = new JSONObject(toor.getString("result"));
							String result = toor.getString("result");
							if(result.equals("10000")){
								Toast.makeText(MainActivity.this, "请求验证码成功！", Toast.LENGTH_SHORT).show();

							}else{
								Toast.makeText(MainActivity.this, "请求验证码失败，请稍后再试！", Toast.LENGTH_SHORT).show();

							}
							
						} catch (JSONException e) {
					        e.printStackTrace();
					    
						}
					}
				}
			};
				
				String value = "phone=" + accounts.getText().toString();
				SendMessageThread thread = new SendMessageThread(handler_sendmessage, value);
				new Thread(thread).start();
				
			}
		});
		
		if (isFirstRun) {
			Log.v("LLLLLLLLLLLLLLLL", "软件第一次运行");
			accounts.setText("");
			password.setText("");
//			password.setHint(R.string.importPas);

		} else {
			TEL = preferences.getString("tel", null);
			PW = preferences.getString("password",null);
			accounts.setText(TEL);
			password.setText(PW);
			Log.v("LLLLLLLLLLLLLLLL", "软件不是第一次运行");
		}
		
		if(isAutoLogin){
			Log.e("LLLLLLLLLLLLLLLL", "可以自动登录的啊！！");
				Intent intent = new Intent();
				if(statusya.equals("worker")){
					intent.setClass(MainActivity.this, SecondActivity.class);
				}else{
					intent.setClass(MainActivity.this, HomePageya.class);
				}
				
				startActivity(intent);
				
		}
	
        
    }
    
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		if (v.getId() == R.id.login_generalization){
			
			Log.v("SDDDDDDDDDDDDDDD", "DIYICINIPEIWOZUOZHEWODESHOUXINSHIKONGKONGDE");
			
  		if (accounts.getText().toString().trim().length() == 11&& !(password.getText().toString()).equals("") ){
  			
  			//到时候加上登录用户名和密码
  			handler = new Handler(){
				
				public void handleMessage(Message msg){
					super.handleMessage(msg);
					if (msg.what == 12138) {
						
						String backMsg = msg.obj.toString();	
						Log.v("READYYYYYYYYYYY", backMsg);
							
						try{
							JSONObject toor = new JSONObject(backMsg);
							String result = toor.getString("result");
							String errMsg = toor.getString("errMsg");
//							factory_id = toor.getString("factory_id");
							
							Log.v("QBYDWSSOORY", result+errMsg);
							
							if(result.equals("10000")){
								factory_id = toor.getString("factory_id");
								name =  toor.getString("name");
//								status = "worker";
								statusya = toor.getString("post");
								heiheihei = statusya;
								//20180925注释dei
								workshop_id = toor.getString("workshop_id");
								Log.v("aaaaaaaaaaaa", factory_id);
								editor.putString("factory_id", factory_id);
								editor.putString("workshop_id", workshop_id);
								
								editor.putString("status", statusya);
								editor.putString("name", name);
								if(autologin.isChecked()){                            
					                editor.putBoolean("isAutoLogin", true);                            
					            }else{
					                editor.putBoolean("isAutoLogin", false);
					            }
								editor.commit();
								Toast showToast=Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_SHORT);
						    	showToast.show();
								
								Intent intent1 = new Intent();
								if(statusya.equals("worker")){
									intent1.setClass(MainActivity.this, SecondActivity.class);
								}else{
									intent1.setClass(MainActivity.this, HomePageya.class);
								}
//								intent1.setClass(MainActivity.this, HomePageya.class);
								startActivity(intent1);
							}else if(result.equals("10003")){
								Toast showToast=Toast.makeText(MainActivity.this, errMsg, Toast.LENGTH_SHORT);
						    	showToast.show();
							}else if(result.equals("10002")){
								Toast showToast=Toast.makeText(MainActivity.this, errMsg, Toast.LENGTH_SHORT);
						    	showToast.show();
							}else if(result.equals("10005")){
								Toast showToast=Toast.makeText(MainActivity.this, errMsg, Toast.LENGTH_SHORT);
						    	showToast.show();
							}else {
								Toast showToast=Toast.makeText(MainActivity.this, "登录失败！请验证用户名后重试", Toast.LENGTH_SHORT);
						    	showToast.show();
							}
							
						} catch (JSONException e) {
					        e.printStackTrace();
					    }
					}
				}

			};
			
				
				String value ="phone="+accounts.getText().toString()+
						"&password="+password.getText().toString() +
						"&code=" + message.getText().toString();
				
				LoginThread thread1 = new LoginThread(handler, value);
				new Thread(thread1).start();
				
				
//  			if(autologin.isChecked()){                            
//                editor.putBoolean("isAutoLogin", true);                            
//            }else{
//                editor.putBoolean("isAutoLogin", false);
//            }

  			TEL = accounts.getText().toString();
  			PW = password.getText().toString();
  			
  			Log.e("LLLLLLLLLLLLLLLL", TEL);
  			editor.putBoolean("isFirstRun",false);
  			
  			editor.putString("tel", TEL);
  			editor.putString("password", PW);
  			editor.commit();
  			
  			//20171220这句注释了之后就不退了
//  			finish();
  			
  		}
  		else if(accounts.getText().toString().trim().length() != 11){
  			Toast.makeText(this, getString(R.string.phoNum), Toast.LENGTH_LONG).show();
  		}
  		else if(password.getText().toString().equals("")){
  			Toast.makeText(this, "请输入密码！", Toast.LENGTH_LONG).show();
  		}
  	}
	}
    
    private MessageReceiver mMessageReceiver;
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
	}

  
  public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			try {
				if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
					String messge = intent.getStringExtra(KEY_MESSAGE);
					String extras = intent.getStringExtra(KEY_EXTRAS);
					StringBuilder showMsg = new StringBuilder();
					showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
//					if (!ExampleUtil.isEmpty(extras)) {
//						showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
//					}
//					setCostomMsg(showMsg.toString());
				}
			} catch (Exception e){
			}
		}
	}
  
  @Override
 	protected void onResume() {
 		isForeground = true;
 		super.onResume();
 	}


 	@Override
 	protected void onPause() {
 		isForeground = false;
 		super.onPause();
 	}
 	@Override
 	protected void onDestroy() {
 		LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
 		super.onDestroy();
 	}

 	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			instance.finish();
//	    	HomePageya.instance.finish();

			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	

}
