package com.cement.abnormalmanagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.adapter.taskeditor.PartsAdapter;
import com.example.adapter.taskeditor.RankdeEventAdapter;
import com.example.adapter.taskeditor.ToolEventAdapter;
import com.example.adapter.taskeditor.RankdeEventAdapter.Callback;
import com.example.bean.taskeditor.MyListView;
import com.example.bean.taskeditor.Tool;
import com.example.generalizationdemo.R;
import com.example.taskeditor.taskeditor.MainActivity;


public class AssignAbnormalActivity extends Activity implements Callback{
	private static String TAG = "test.java";
	private JSONObject test;
	private HashMap<String, String> subComm;
	private LinearLayout l;
	private int currentPosition = -1;
	private ArrayList<Tool> eventRank ;
	private RankdeEventAdapter adapter;
	private MyListView mylistview;
	private Button yes;
	private Button no;
	private ArrayList<String> toolList;
	
	private Intent intent;
	private String nameIntent;
	private String tasknature;
	private String numIntent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testformat);
        subComm = new HashMap<String, String>();
        eventRank = new ArrayList<Tool>();
        toolList = new ArrayList<String>();
        l = (LinearLayout) findViewById(R.id.l);
        yes = (Button) findViewById(R.id.task_details_button_yes);
        no = (Button) findViewById(R.id.task_details_button_no);
        
        intent = getIntent();
        Bundle b = new Bundle();
        b = intent.getExtras();
        nameIntent = b.getString("taskName");
        tasknature = b.getString("tasknature");
        if(!nameIntent.equals("1"))
        	numIntent = b.getString("taskNum");
        
        yes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
					System.out.println(TAG + "yesbutton:"+subComm.toString());
				Intent intent = new Intent(AssignAbnormalActivity.this, MainActivity.class);
				if(!nameIntent.equals("1")){
				intent.putExtra("tasknamechangbefor", nameIntent);
//				System.out.println("nameIntent发过来要修改的任务名字"+nameIntent);
				intent.putExtra("taskNum", numIntent);
				
				}
				intent.putExtra("tasknamechang", subComm.get("任务名称"));
				setResult(20,intent);
				finish();
				
			}
		});
        
        
        try {
//        	StringBuilder builder = new StringBuilder();
//        	AssetManager s = this.getAssets();
//        	InputStream is = s.open("tadeeditor.json.txt");
//        	byte[] buffer = new byte[1024];
//        	while(is.read(buffer)!= -1){
//        		builder.append(new String(buffer,"utf-8"));
//        	}
//        	is.read(buffer);
//        	String builder = new String(buffer, "utf-8");
//        	is.close();
        	
        	InputStreamReader isr = new InputStreamReader(getAssets().open(
					"tadeeditor.json.txt"), "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			Log.e(TAG+"全部的", builder.toString());
			try {
				test = new JSONObject(builder.toString());
				JSONArray format_array;
				if(nameIntent.equals("1")){
	        		if(tasknature.equals("巡检任务")){
					format_array = test.getJSONArray("formatinspection");
	        		}else{
	        			format_array = test.getJSONArray("formatrepair");
	        			
	        		}
	        	}else{
				 format_array = test.getJSONArray("format");
	        	}
				System.out.println("这个数组多长"+ format_array.length());
				for(int j = 0; j < format_array.length(); j ++){
					
					JSONObject format = format_array.getJSONObject(j);
					
					JSONArray editor_array = format.getJSONArray("addedittext");
					for (int i = 0; i < editor_array.length(); i++) {
						JSONObject editor = editor_array.getJSONObject(i);
						String edittext_name = editor.getString("edittext_name");
						String edittext_content = editor.getString("edittext_content");
						String font_color = editor.getString("font_color");
						int font_size = editor.getInt("font_size");
						subComm.put(edittext_name, edittext_content);
						addEditor(edittext_name,edittext_content,font_color,font_size);
					}
					
					JSONArray spinner_array = format.getJSONArray("addspinner");
					for (int i = 0; i < spinner_array.length(); i++) {
						JSONObject spinner = spinner_array.getJSONObject(i);
						String spinner_name = spinner.getString("spinner_name");
						String spinner_content = spinner.getString("spinner_content");
						String font_color = spinner.getString("font_color");
						int font_size = spinner.getInt("font_size");
						
						JSONArray spinnerSou = spinner.getJSONArray("spinner_array");
						String[] spiSor = new String[spinnerSou.length()];
						for (int k = 0; k < spinnerSou.length(); k++) {
							spiSor[k] = spinnerSou.getString(k);
						}
//						System.out.println("spinner的默认值"+spinner_content);
						if("任务性质".equals(spinner_name) || "身份验证".equals(spinner_name))
							continue;
						subComm.put(spinner_name, spinner_content);
						addSpinner(spinner_name,spinner_content,font_color,font_size,spiSor);
					}
					
					
					
					JSONArray check_array = format.getJSONArray("addcheck");
					for (int i = 0; i < check_array.length(); i++) {
						JSONObject check = check_array.getJSONObject(i);
						String check_name = check.getString("check_name");
						String check_content = check.getString("check_content");
//						System.out.println("checkbox是否被选择"+check_content);
						String font_color = check.getString("font_color");
						int font_size = check.getInt("font_size");
						subComm.put(check_name, check_content);
						addCheck(check_name,check_content,font_color,font_size);
					}
					
					JSONArray button_array = format.getJSONArray("addbutton");
//					ArrayList<Tool> tool = new ArrayList<Tool>();
//					ArrayList<Tool> parts = new ArrayList<Tool>();
					System.out.println("添加addbutton时的数组长度："+button_array.length());
					for (int i = 0; i < button_array.length(); i++) {
						ArrayList<Tool> contentList = new ArrayList<Tool>();
						JSONObject button = button_array.getJSONObject(i);
						String button_name = button.getString("button_name");
						String button_content = button.getString("button_content");
						String font_color = button.getString("font_color");
						String needtextview = button.getString("needtextview");
						String needshownum = button.getString("needshownum");
						int font_size = button.getInt("font_size");
						JSONArray content_array = button.getJSONArray("content");
						JSONArray chosen_array = button.getJSONArray("chosen");
						for (int x = 0; x < chosen_array.length(); x++) {
							JSONObject chose = chosen_array.getJSONObject(j);
							String content_name = chose.getString("content_name");
							String content_id = chose.getString("content_id");
							contentList.add(new Tool(content_name,"true",content_id));
							if(needtextview.equals("false")&& needshownum.equals("false")){
//							System.out.println("鍦ㄥぇjson涓�夋嫨鐨勪簨浠舵墽琛屼簡鍚�");
//							eventRank.add(content_name);
								eventRank.add(new Tool(content_name, "true", content_id));
							}
						}
						for (int k = 0; k < content_array.length(); k++) {
							JSONObject content = content_array.getJSONObject(k);
							String content_name = content.getString("content_name");
							String content_isselect = content.getString("content_isselect");
							String content_id = content.getString("content_id");
							if(!contentList.contains(content_id))
							contentList.add(new Tool(content_name,content_isselect,content_id));
						}
						
						addButton(button_name,button_content,font_color,font_size,contentList);
						
					}
					
					
					
					for (int i = 0; i < eventRank.size(); i++) {
						System.out.println("这是大json中的被选择的事件"+eventRank.get(i));
					}
					
					addListview(eventRank);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
    }
	private void addToolButton() {
		// TODO Auto-generated method stub
		RelativeLayout addRela = new RelativeLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		addRela.setLayoutParams(params);
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		final Button toolB = new Button(this);
		final Button b = new Button(this);
		
		
	}
	private void addButton(final String button_name, final String button_content,
			String font_color, int font_size,final ArrayList<Tool> contentList) {
		// TODO Auto-generated method stub
		RelativeLayout addRela = new RelativeLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		addRela.setLayoutParams(params);
		 
		 
		final TextView tv = new TextView(this);
		final Button b = new Button(this);
		tv.setId(1);
		b.setId(2);
		RelativeLayout.LayoutParams params1 = null;
		
		RelativeLayout.LayoutParams params2 = null;

		if(button_name.equals("任务事件")){
			tv.setText(button_name);
			 params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			 params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			
		}else{
			params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params1.addRule(RelativeLayout.BELOW,2);
			params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		
			if(nameIntent.equals("1")){
				tv.setText(button_name+":");
			}else{
			for (int j = 0; j < contentList.size(); j++) {
				if(contentList.get(j).getToolNum().equals("true") || (!(contentList.get(j).getToolNum().equals("0")))){
					tv.append(button_name+":"+contentList.get(j).getToolName());
				}
			}
		}
		}
		tv.setLayoutParams(params1);
		
		tv.setTextSize(font_size);
		tv.setTextColor(Color.parseColor(font_color));
		
		b.setLayoutParams(params2);
		b.setText(button_content);
		b.setTextSize(font_size);
		b.setTextColor(Color.parseColor(font_color));
		
		addRela.addView(b);
		addRela.addView(tv);
b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder  builder = new AlertDialog.Builder(AssignAbnormalActivity.this);
				builder.setTitle(button_name);
				LinearLayout view;
//				for (int i = 0; i < contentList.size(); i++) {
					
//					System.out.println("这是点击按钮时出现的list"+contentList.get(i).getToolName()+":"+contentList.get(i).getToolNum());
//				}
				if(button_content.equals("选择配件")){
					view = (LinearLayout) getLayoutInflater().inflate(R.layout.partslistview, null);
					ListView listview = (ListView) view.findViewById(R.id.partslistview);
					PartsAdapter adapter = new PartsAdapter(AssignAbnormalActivity.this, contentList);
					listview.setAdapter(adapter);
					builder.setView(view);
					builder.setNegativeButton("取消",null);
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							for (int i = 0; i < contentList.size(); i++) {
								if((Integer.parseInt(contentList.get(i).getToolNum()) > 0)){
									tv.append(contentList.get(i).getToolName()+"("+contentList.get(i).getToolNum()+")");
								}
										
							}
						}
					});
					
					
				}else{
					 view = (LinearLayout) getLayoutInflater().inflate(R.layout.tooleventlistview, null);

					ListView listView = (ListView) view.findViewById(R.id.tooleventlistview);

					 final ToolEventAdapter adapterEvent = new ToolEventAdapter(AssignAbnormalActivity.this,contentList);

					listView.setAdapter(adapterEvent);
					builder.setView(view);
					builder.setNegativeButton("返回", null);
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if(button_content.equals("选择事件")){
								eventRank = adapterEvent.getListRank();
								currentPosition = -1;
								adapter = new RankdeEventAdapter(AssignAbnormalActivity.this,eventRank,currentPosition,AssignAbnormalActivity.this);
								mylistview.setAdapter(adapter);
							}else if(button_content.equals("选择工具")){
								for (int i = 0; i < contentList.size(); i++){
									if(contentList.get(i).getToolNum().equals("true"))
										
										tv.append(contentList.get(i).getToolName()+" ");
								}
							}
						}
						
					});
				
					
				}
		
				
				AlertDialog dialog = builder.create();
				dialog.show();
			
				dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
						
						
			
			}

			
						});
		
		
		
		l.addView(addRela);
		}
		
	
	
	
	private void addListview(ArrayList<Tool> list) {
		// TODO Auto-generated method stub
		 mylistview = new MyListView(this);
		adapter = new RankdeEventAdapter(AssignAbnormalActivity.this, list, currentPosition, AssignAbnormalActivity.this);
		mylistview.setAdapter(adapter);
		l.addView(mylistview);
	}
	private void addCheck(final String check_name, String check_content,String font_color,int font_size) {
		// TODO Auto-generated method stub
		RelativeLayout addRela = new RelativeLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		addRela.setLayoutParams(params);
		 
		 
		final TextView tv = new TextView(this);
		final CheckBox cb = new CheckBox(this);
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		tv.setLayoutParams(params1);
		tv.setText(check_name);
		tv.setTextSize(font_size);
		tv.setTextColor(Color.parseColor(font_color));
		
		cb.setLayoutParams(params2);
		
		if(check_content.equals("true")){
			
			cb.setChecked(true);
			
		}
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				subComm.put(check_name, String.valueOf(isChecked));
			}
		});
		addRela.addView(cb);
		addRela.addView(tv);
		l.addView(addRela);
		
		
	}
	private void addSpinner(final String spinner_name, String spinner_content,
			String font_color, int font_size, final String[] spiSor) {
		// TODO Auto-generated method stub
		RelativeLayout addRela = new RelativeLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		addRela.setLayoutParams(params);
			
			final TextView tv = new TextView(this);
			final Spinner s = new Spinner(this);
			RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			tv.setLayoutParams(params1);
			tv.setText(spinner_name);
			tv.setTextSize(font_size);
			tv.setTextColor(Color.parseColor(font_color));
			
			s.setLayoutParams(params2);
			SpinnerAdapter adapter = new com.example.adapter.taskeditor.SpinnerAdapter(AssignAbnormalActivity.this, spiSor, font_size, font_color);
			s.setAdapter(adapter);
			for (int i = 0; i < spiSor.length; i++) {
//				System.out.println("在添加spinner中spinner的资源"+spiSor[i]);
				if (spiSor[i].equals(spinner_content)) {
//					System.out.println("设置spinner默认值运行了吗？");
					s.setSelection(i,true);
					
				}
			}
			s.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					// TODO Auto-generated method stub
					Field field;
					try {
						field = AdapterView.class.getDeclaredField("mOldSelectedPosition");
						field.setAccessible(true);
						field.setInt(s, AdapterView.INVALID_POSITION);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return false;
				}

				
			});
			s.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
//					System.out.println("这是下拉框改变方法"+spiSor[position]);
					subComm.put(spinner_name, spiSor[position]);
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub
					
				}
			});
			
			addRela.addView(s);
			addRela.addView(tv);
			
			l.addView(addRela);
			
		
	}
	
	
	
	
	private void addEditor(final String edittext_name, String edittext_content,
			String font_color, int font_size) {
		// TODO Auto-generated method stub
//		
		RelativeLayout addRela = new RelativeLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		addRela.setLayoutParams(params);
		
		final TextView tv = new TextView(this);
		final EditText et = new EditText(this);
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		tv.setLayoutParams(params1);
		tv.setText(edittext_name);
		tv.setTextSize(font_size);
		tv.setTextColor(Color.parseColor(font_color));
		et.setLayoutParams(params2);
		et.setTextSize(font_size);
		et.setTextColor(Color.parseColor(font_color));
		if(!edittext_content.equals(null))
			et.setText(edittext_content);
//		
		addRela.addView(et);
		addRela.addView(tv);
		l.addView(addRela);
		et.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
				subComm.put(edittext_name, et.getText().toString());
			}
		});
	}
	@Override
	public void click(View v) {
		// TODO Auto-generated method stub
		int curPosition;
		int mCurPosition;
		switch (v.getId()) {
		case R.id.item:
			mCurPosition = (Integer) v.getTag(R.id.tag_item_click);
			currentPosition = mCurPosition;
			
			adapter.refresh(currentPosition); 
            break;
		case R.id.ranked_event_imagedown:
			curPosition = (Integer) v.getTag();
			if(curPosition != eventRank.size() - 1){
				Tool downFirst = eventRank.get(curPosition);
				Tool downSecond = eventRank.get(curPosition + 1);
				eventRank.remove(curPosition + 1);
				eventRank.remove(curPosition);
				eventRank.add(curPosition,downSecond);
				eventRank.add(curPosition + 1,downFirst);
				currentPosition = curPosition + 1;
				
				adapter.refresh(currentPosition);
				
			}
			break;
		case R.id.ranked_event_imageup:
			curPosition = (Integer) v.getTag();
			if(curPosition != 0){
			Tool upFirst = eventRank.get(curPosition);
			Tool upSecond = eventRank.get(curPosition - 1);
			eventRank.remove(curPosition);
			eventRank.remove(curPosition - 1);
			eventRank.add(curPosition - 1,upFirst);
			eventRank.add(curPosition,upSecond);
			currentPosition = curPosition - 1;
			adapter.refresh(currentPosition);
			}
			break;

		default:
			break;
		
	}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent1 = new Intent(AssignAbnormalActivity.this,AbnormalDetailActivity.class);
			startActivity(intent1);
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
