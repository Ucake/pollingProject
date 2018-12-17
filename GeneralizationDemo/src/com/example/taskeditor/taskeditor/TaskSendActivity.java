package com.example.taskeditor.taskeditor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.cement.check.CheckActivity;
import com.cement.check.CheckListActivity;
import com.cement.data.CheckJson;
import com.cement.thread.ChangeConditionThread;
import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.R;
import com.example.util.taskeditor.DateTimePickerDialogUtilTaskEditor;
import com.example.util.taskeditor.GeneralLayout;
import com.example.util.taskeditor.sendTaskThread;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class TaskSendActivity extends Activity {
	private GeneralLayout generalLayout;
	private Button yes;
	private Button no;
	private JSONObject json;
	private LinearLayout l;
	private String taskName = null;
	//private String[] taskTime = {"閺冿拷","mo_1","mo_2","mo_3","mo_4","mo_5","mo_6","mo_7","af_1","af_2","af_3","af_4","af_5","af_6","af_7"};
	private String taskTimeStr = null;
	private ArrayList<String> nameList ;
	public static Spinner spinner_2;
	private String activity_name;
//	private String[] nameCopy = null;
	private int selectPostion;
	public  String factory_id = com.example.generalizationdemo.HomePageya.factory_id;
	private String mission_id = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 setContentView(R.layout.taskeditor_test_testformat);
		  l = (LinearLayout) findViewById(R.id.l);
		  StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	        .detectNetwork()
	        .penaltyLog()
	        .build());
		  
		 yes = (Button) findViewById(R.id.task_details_button_yes);
	     no = (Button) findViewById(R.id.task_details_button_no);
	     nameList = new ArrayList<String>();
	     
//	     try {
//				InputStreamReader is = new InputStreamReader(getAssets().open(
//						"format.json"), "UTF-8");
//				BufferedReader br = new BufferedReader(is);
//				String line;
//				StringBuilder builder = new StringBuilder();
//				while ((line = br.readLine()) != null) {
//					builder.append(line);
//				}
//				json = new JSONObject(builder.toString());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
	     
//			}
	     Intent intent = getIntent();
	     Bundle b = new Bundle();
	        b = intent.getExtras();
	      activity_name = b.getString("activity_name");
	      if(activity_name.equals("CheckActivity")){
	    	  json = CheckActivity.json;
	    	  mission_id = b.getString("mission_id");
	      }else{
	    	  json = MainActivity.json; 
	    	 
	      }
	    
	     generalLayout = new GeneralLayout(l, json, this);
	     taskName =  generalLayout.partView();
	     TextView textSelPeo = new TextView(this);
	     textSelPeo.setText("选择人员：");
	     textSelPeo.setTextSize(20);
	     textSelPeo.setTextColor(android.graphics.Color.BLUE);
	     LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
	     textSelPeo.setLayoutParams(params1);
	     l.addView(textSelPeo);
	     
	     	View view = getLayoutInflater().inflate(R.layout.taskeditor_tasksendactivity, null);
			//final Spinner spinner_1 = (Spinner) view.findViewById(R.id.taskeditor_tasksendactivity_spinner1);
			final EditText edit_3 = (EditText) view.findViewById(R.id.taskeditor_tasksendactivity_edittext3);
			 spinner_2 = (Spinner) view.findViewById(R.id.taskeditor_tasksendactivity_spinner2);
//			final EditText edit_1 = (EditText) view.findViewById(R.id.taskeditor_tasksendactivity_edittext1);
//			final EditText edit_2 = (EditText) view.findViewById(R.id.taskeditor_tasksendactivity_edittext2);
			//com.example.adapter.SpinnerAdapter adapter_spinner_1 = new com.example.adapter.SpinnerAdapter(TaskSendActivity.this,getResources().getStringArray(R.array.time), 20, "#080808"); 
			final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    	final Date curDate = new Date(System.currentTimeMillis());
//			edit_1.setText(formatter.format(curDate));
//			edit_2.setText(formatter.format(curDate));
			
//			edit_3.setText(formatter.format(curDate)+ ","+"閻ц棄銇�");
			final String day = formatter.format(curDate);
			//			spinner_1.setAdapter(adapter_spinner_1);
//			spinner_1.setSelection(0, true);
//			spinner_1.setOnTouchListener(new OnTouchListener() {
//				
//				@Override
//				public boolean onTouch(View v, MotionEvent event) {
//					// TODO Auto-generated method stub
//					Field field;
//					try {
//						field = AdapterView.class.getDeclaredField("mOldSelectedPosition");
//						field.setAccessible(true);
//						field.setInt(spinner_1, AdapterView.INVALID_POSITION);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					return false;
//				}
//
//				
//			});
			edit_3.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Handler handler = new Handler(){

						@Override
						public void handleMessage(Message msg) {
							// TODO Auto-generated method stub
//							super.handleMessage(msg);
							if(msg.what == 1){
								edit_3.setText(""+msg.obj);
								System.out.println("鐟曚浇顔曠純顔炬畱閺冨爼妫块張澶婃偋閿涳拷"+msg);
							}
						}

						
						
					};
					DateTimePickerDialogUtilTaskEditor dateTimePickDialogUtil = new DateTimePickerDialogUtilTaskEditor(TaskSendActivity.this, edit_3.getText().toString().split(",")[0]);
					dateTimePickDialogUtil.dateTimePicKDialog(edit_3,true,day,nameList,spinner_2);
//					nameCopy = dateTimePickDialogUtil.getNameStrCopy();
					//nameList = dateTimePickDialogUtil.nameList;
					//String[] nameStr = new String[nameList.size()];
					//nameList.size()
//					new Thread(dateTimePickDialogUtil.timeRunnable).start();
					
//					String[] nameStr = new String[5];
//					
//					for (int i = 0; i < 5; i++) {
//						//nameStr[i] = nameList.get(i);
//						nameStr[i] = "鐏忓繐鐤�"+i;
//					}
//					com.example.adapter.SpinnerAdapter adapter = new com.example.adapter.SpinnerAdapter(TaskSendActivity.this, nameStr, 20, "#080808"); 
//					spinner_2.setAdapter(adapter);
					
				}
			});
			

				
			spinner_2.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(final View v, MotionEvent event) {
					// TODO Auto-generated method stub
					if(event.getAction() == event.ACTION_DOWN){
						
//						if(nameList.size() == 0){
//						System.out.println("娌￠敓鏂ゆ嫹閿熸枻鎷烽敓鏂ゆ嫹鏃堕敓鏂ゆ嫹閿熻В寮归敓鏂ゆ嫹閿熸枻鎷风ず閿熸枻鎷�");
//							AlertDialog.Builder builder = new AlertDialog.Builder(TaskSendActivity.this);
//							builder.setTitle("濞夈劍鍓伴敍锟�");
//							builder.setMessage("鐠囧嘲鍘涢柅澶嬪娴犺濮熺�瑰甯撻弮鍫曟？");
//							builder.setPositiveButton("鏉╂柨娲�",new DialogInterface.OnClickListener() {
//								
//								@Override
//								public void onClick(DialogInterface dialog, int which) {
//									// TODO Auto-generated method stub
//									v.clearFocus();
//									
//								}
//							} );
//							Dialog dialog = builder.create();
//							dialog.show();
//					}
//
//							
//
//
					}
					return false;
				}
			});
			spinner_2.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent,
						View view, int position, long id) {
					// TODO Auto-generated method stub
					selectPostion = position;
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
				
			});

			
			
			
			
//			
//			edit_1.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					DateTimePickerDialogUtilTaskEditor dateTimePickDialogUtil = new DateTimePickerDialogUtilTaskEditor(TaskSendActivity.this, edit_1.getText().toString());
//					dateTimePickDialogUtil.dateTimePicKDialog(edit_1,false,day);
//				}
//			});
//			edit_2.setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					DateTimePickerDialogUtilTaskEditor dateTimePickDialogUtil = new DateTimePickerDialogUtilTaskEditor(TaskSendActivity.this, edit_2.getText().toString());
//					dateTimePickDialogUtil.dateTimePicKDialog(edit_2,false,edit_1.getText().toString());
//				}
//			});
//			edit_1.setInputType(InputType.TYPE_NULL);
//			edit_2.setInputType(InputType.TYPE_NULL);
			edit_3.setInputType(InputType.TYPE_NULL);
			l.addView(view);
	     
	     no.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					AlertDialog.Builder builder = new AlertDialog.Builder(TaskSendActivity.this);
					
					
					builder.setTitle("注意：");
					builder.setMessage("是否放弃下发任务？");
					builder.setNegativeButton("否", null);
					builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(TaskSendActivity.this, MainActivity.class);
						intent.putExtra("change", "nochange");
						setResult(10, intent);
						finish();
						}
					});
					Dialog dialog = builder.create();
					dialog.show();
					
			}
		});
	    
	     yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				AlertDialog.Builder builder = new AlertDialog.Builder(TaskSendActivity.this);
				
				builder.setTitle("注意：");
				builder.setMessage("是否下发任务？");
				builder.setNegativeButton("否", null);
				builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					// TODO Auto-generated method stub
					//瑜版捇锟藉瀚ㄦ禍鍝勬喅閺堝鏆熼幑顔芥閹讹拷1閺�瑙勫灇0
					if(nameList.size() < 1){
						AlertDialog.Builder builder_people = new AlertDialog.Builder(TaskSendActivity.this);
						
						builder_people.setTitle("注意：");
						builder_people.setMessage("请先选择任务安排人员？");
						builder_people.setNegativeButton("返回", null);
						Dialog dialog_people = builder_people.create();
						dialog_people.show();
					}else{
						System.out.println("任务下发时发送的参数："+ "taskname="+taskName+"&tasktime="+DateTimePickerDialogUtilTaskEditor.sendtaskTime+"&time1="+edit_3.getText().toString().substring(0,10)+"&taskpeople="+nameList.get(selectPostion)+"&time2="+"888"
								+"&factory_id="+factory_id);
						Handler mhandler = new Handler(){

							@Override
							public void handleMessage(Message msg) {
								// TODO Auto-generated method stub
								super.handleMessage(msg);
								if(msg.what == 12137){
									if("10000".equals(msg.obj)){
										Handler	handler_changecondition = new Handler(){
											
											public void handleMessage(Message msg){
												super.handleMessage(msg);
												if (msg.what == 12138) {
													
												String backMsg = msg.obj.toString();
												
												Log.v("sanciwoshou", backMsg);
												try{
													
													JSONObject toor = new JSONObject(backMsg);
//													JSONObject result1 = new JSONObject(toor.getString("result"));
													String result = toor.getString("result");
													if(result.equals("10000")){
													
														Intent intent = new Intent(TaskSendActivity.this, HomePageya.class);
														startActivity(intent);
//														setResult(20, intent);
//														finish();
																	
																
															
														}
												}catch (Exception e) {
														// TODO: handle exception
													}
												}
											}
													};
													if(mission_id != null){
														
														String value = "mission_id=" + mission_id +"&condition=7";
														Log.e("mission_id", mission_id);
														ChangeConditionThread thread = new ChangeConditionThread(handler_changecondition, value);
														new Thread(thread).start();
													}else{
														Intent intent = new Intent(TaskSendActivity.this, MainActivity.class);
														intent.putExtra("taskname", taskName);
														intent.putExtra("tasktime", DateTimePickerDialogUtilTaskEditor.sendtaskTime);
														intent.putExtra("time1", edit_3.getText().toString());
														intent.putExtra("taskpeople", nameList.get(selectPostion));
														intent.putExtra("time2", taskName);
														intent.putExtra("set_name", taskName);
														
														startActivity(intent);
														setResult(20, intent);
														finish();
													}
									}
								}
							}
							
						};
									
						String value = "taskname="+taskName+"&tasktime="+DateTimePickerDialogUtilTaskEditor.sendtaskTime+"&time1="+edit_3.getText().toString().substring(0,10)+"&taskpeople="+nameList.get(selectPostion)+"&time2="+"888"
								+"&set_name="+""+"&factory_id="+factory_id;
						sendTaskThread thread = new sendTaskThread(value, mhandler);
						new Thread(thread).start();
									
									
									
								
						
					}
				}
			});

				Dialog dialog = builder.create();
				dialog.show();

				}
		});
		
	}
	

	
}
