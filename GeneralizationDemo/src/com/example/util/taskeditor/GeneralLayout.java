package com.example.util.taskeditor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.taskeditor.RankdeEventAdapter;
import com.example.adapter.taskeditor.ToolEventAdapter;
import com.example.bean.taskeditor.MyListView;
import com.example.bean.taskeditor.Tool;
import com.example.generalizationdemo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton.OnCheckedChangeListener;



public class GeneralLayout {
	private HashMap<String,ArrayList<String>> content_note;
	private ArrayList<String> content_note_list;
	private int currentPosition = -1;
	private ArrayList<Tool> eventRank ;
	private RankdeEventAdapter adapter;
	private MyListView mylistview;
	private LinearLayout l;
	private JSONObject json;
	private Activity activity;
	//private Callback mcaCallback;
	private String numIntent;
	private ArrayList<String> textContent;
//	private JSONObject sendJson;
	private String task_num;
	private String task_name;
	private String task_dec;
	private String task_loca;
	private String task_level;
	private String task_sou;
	private String task_check;
	private String task_tool;
	private String task_parets;
	private String task_nature;
	private String addTaskName = null;
	
	public GeneralLayout(LinearLayout l, JSONObject json, Activity activity,
			String numIntent,String addTaskName,ArrayList<Tool> eventRank,RankdeEventAdapter adapter,MyListView myListView) {
		super();
		this.l = l;
		this.json = json;
		this.activity = activity;
//		this.mcaCallback = mcaCallback;
		this.numIntent = numIntent;
		this.addTaskName = addTaskName;
		this.eventRank = eventRank;
		this.mylistview = myListView;
		this.adapter = adapter;
		content_note = new HashMap<String, ArrayList<String>>();
//		sendJson = new JSONObject();

	}
	
	public GeneralLayout(LinearLayout l, JSONObject json, Activity activity) {
		super();
		this.l = l;
		this.json = json;
		this.activity = activity;
	}

	public void allView(){
		try {
        	
        	

	        	JSONObject	test = json.getJSONObject("format");
	        	

					JSONArray editor_array = test.getJSONArray("addedittext");
					for (int i = 0; i < editor_array.length(); i++) {
						JSONObject editor = editor_array.getJSONObject(i);
						String edittext_name = editor.getString("edittext_name");
						String edittext_content = editor.getString("edittext_content");
						String font_color = editor.getString("font_color");
						int font_size = editor.getInt("font_size");
//						String ischange = editor.getString("ischange");
						addEditor(edittext_name,edittext_content,font_color,font_size,"true",i);
						
					}
					

					JSONArray spinner_array = test.getJSONArray("addspinner");
					for (int i = 0; i < spinner_array.length(); i++) {
						JSONObject spinner = spinner_array.getJSONObject(i);
						String spinner_name = spinner.getString("spinner_name");
						String spinner_content = spinner.getString("spinner_content");
						String font_color = spinner.getString("font_color");
						int font_size = spinner.getInt("font_size");
						String spinner_sor = spinner.getString("spinner_array");
						
						String[] spiSor = spinner_sor.split(";");
						

						if("任务性质".equals(spinner_name) || "身份验证".equals(spinner_name))
							continue;
						addSpinner(spinner_name,spinner_content,font_color,font_size,spiSor,i);
						
					}
					
					
					
//					JSONArray check_array = format.getJSONArray("addcheck");
//					for (int i = 0; i < check_array.length(); i++) {
//						JSONObject check = check_array.getJSONObject(i);
//						String check_name = check.getString("check_name");
//						String check_content = check.getString("check_content");
//						System.out.println("checkbox鏄惁琚�夋嫨"+check_content);
//						String font_color = check.getString("font_color");
//						int font_size = check.getInt("font_size");
//						subComm.put(check_name, check_content);
//						addCheck(check_name,check_content,font_color,font_size);
//					}
					

					JSONArray button_array = test.getJSONArray("addbutton");

					System.out.println("添加按钮时的数组长度"+button_array.length());
					for (int i = 0; i < button_array.length(); i++) {
						ArrayList<Tool> contentList = new ArrayList<Tool>();
						JSONObject button = button_array.getJSONObject(i);
						String button_name = button.getString("button_name");
						String button_content = button.getString("button_content");
						String font_color = button.getString("font_color");
						String needtextview = button.getString("needtextview");
						String needshownum = button.getString("needshownum");
						
						int font_size = button.getInt("font_size");
						if(!"".equals(addTaskName)){
							System.out.println("有任务名字吗？"+addTaskName);
							JSONArray chosen_array = button.getJSONArray("chosen");
							for (int j = 0; j < chosen_array.length(); j++) {
								JSONObject chose = chosen_array.getJSONObject(j);
								String content_name = chose.getString("content_name");
								String content_id = chose.getString("content_id");
								contentList.add(new Tool(content_name,"true",content_id));
								if(needtextview.equals("false")&& needshownum.equals("false")){
//								System.out.println("鍦ㄥぇjson涓�夋嫨鐨勪簨浠舵墽琛屼簡鍚�");
//								eventRank.add(content_name);
									eventRank.add(new Tool(content_name, "true", content_id));
								}
							}
						}
						JSONArray content_array = button.getJSONArray("content");
						for (int k = 0; k < content_array.length(); k++) {
							JSONObject content = content_array.getJSONObject(k);
							String content_name = content.getString("content_name");
//							String content_isselect = content.getString("content_isselect");
							String content_id = content.getString("content_id");
							if(!contentList.contains(content_id))
							contentList.add(new Tool(content_name,"false",content_id));
							if(needtextview.equals("false")&& needshownum.equals("false") ){
							
//							String content_note_spi = content.getString("content_note");
//							String content_note_1 = content_note_spi.replaceAll("\\n","");
//							System.out.println("鍘绘帀\n鍚庣殑瀛楃涓�"+content_note_1);
//							String content_note_2 = content_note_1.replace("[", "");
//							String content_note_3 = content_note_2.replace("]", "");
							
//							String[] content_note_array = content_note_spi.split(";");

//							content_note_list = new ArrayList<String>();
//							for (int l = 0; l < content_note_array.length; l++) {
//								content_note_list.add(content_note_array[l]);
								
//							}

							
//							content_note.put(content_name, content_note_list);
							}
//						
							
						
						}
//						addTextView(button_name, button_content, font_color, font_size, true);
						addButton(button_name,button_content,font_color,font_size,contentList,needtextview,needshownum);
						
						
						
						
					}
					
					
//					for (int i = 0; i < eventRank.size(); i++) {
//						System.out.println("杩欐槸澶son涓殑琚�夋嫨鐨勪簨浠�"+eventRank.get(i));
//					}
					
					addListview(eventRank);
					
					View view = new View(activity);
					view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 50));
					l.addView(view);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public String partView(){
		String taskName = null;
		try {
        	
        	

        	JSONObject	test = json.getJSONObject("format");
        	

				JSONArray editor_array = test.getJSONArray("addedittext");
				for (int i = 0; i < editor_array.length(); i++) {
					JSONObject editor = editor_array.getJSONObject(i);
					String edittext_name = editor.getString("edittext_name");
					String edittext_content = editor.getString("edittext_content");
					String font_color = editor.getString("font_color");
					int font_size = editor.getInt("font_size");
//					String ischange = editor.getString("ischange");
					addTextView(edittext_name, edittext_content, font_color, font_size, false,false);
					if(edittext_name.equals("任务名称"))
						taskName = edittext_content;
				}
				

				JSONArray spinner_array = test.getJSONArray("addspinner");
				for (int i = 0; i < spinner_array.length(); i++) {
					JSONObject spinner = spinner_array.getJSONObject(i);
					String spinner_name = spinner.getString("spinner_name");
					String spinner_content = spinner.getString("spinner_content");
					String font_color = spinner.getString("font_color");
					int font_size = spinner.getInt("font_size");
					String spinner_sor = spinner.getString("spinner_array");
					
					String[] spiSor = spinner_sor.split(";");
					
					if("任务性质".equals(spinner_name))
						continue;
					addTextView(spinner_name, spinner_content, font_color, font_size, false,false);
					
				}
				
				
				
//				JSONArray check_array = format.getJSONArray("addcheck");
//				for (int i = 0; i < check_array.length(); i++) {
//					JSONObject check = check_array.getJSONObject(i);
//					String check_name = check.getString("check_name");
//					String check_content = check.getString("check_content");
//					System.out.println("checkbox鏄惁琚�夋嫨"+check_content);
//					String font_color = check.getString("font_color");
//					int font_size = check.getInt("font_size");
//					subComm.put(check_name, check_content);
//					addCheck(check_name,check_content,font_color,font_size);
//				}
				

				JSONArray button_array = test.getJSONArray("addbutton");

				System.out.println("添加按钮的数组长度"+button_array.length());
				for (int i = 0; i < button_array.length(); i++) {
					ArrayList<Tool> contentList = new ArrayList<Tool>();
					JSONObject button = button_array.getJSONObject(i);
					String button_name = button.getString("button_name");
					String button_content = button.getString("button_content");
					String font_color = button.getString("font_color");
					String needtextview = button.getString("needtextview");
					String needshownum = button.getString("needshownum");
					
					int font_size = button.getInt("font_size");
					JSONArray chosen_array = button.getJSONArray("chosen");
					textContent = new ArrayList<String>();
					for (int j = 0; j < chosen_array.length(); j++) {
						JSONObject chose = chosen_array.getJSONObject(j);
						String content_name = chose.getString("content_name");
						String content_id = chose.getString("content_id");
						contentList.add(new Tool(content_name,"true",content_id));
						textContent.add(content_name);
					}
					JSONArray content_array = button.getJSONArray("content");
					for (int k = 0; k < content_array.length(); k++) {
						JSONObject content = content_array.getJSONObject(k);
						String content_name = content.getString("content_name");
						String content_isselect = content.getString("content_isselect");
						String content_id = content.getString("content_id");
						if(!contentList.contains(content_id))
						contentList.add(new Tool(content_name,"false",content_id));
//						if (content_isselect.equals("true")) {
//							
//							textContent.add(content_name);
//						}else if(!content_isselect.equals("false")){
//							if(!content_isselect.equals("0")){
//								textContent.add(content_name+"("+content_isselect+")");
//						}
//						}
						
					
						
					
					}
					if(needtextview.equals("false")&& needshownum.equals("false")){
						
						addTextView(button_name, button_content, font_color, font_size, true,true);
						
					}else{
						addTextView(button_name, button_content, font_color, font_size, true,false);
					}

					
				}
				
				
				
				

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return taskName;
		
	}
	private void addButton(final String button_name, final String button_content,
			String font_color, int font_size,final ArrayList<Tool> contentList,final String needtextview,final String needshownum) {
		// TODO Auto-generated method stub
		RelativeLayout addRela = new RelativeLayout(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		addRela.setLayoutParams(params);
		 
		 
		final TextView tv = new TextView(activity);
		final Button b = new Button(activity);
		tv.setId(1);
		b.setId(2);
		RelativeLayout.LayoutParams params1 = null;
		
		RelativeLayout.LayoutParams params2 = null;

		if(needtextview.equals("false")&& needshownum.equals("false")){
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
		
//			JSONObject json = null;
//			int tool_count = 0;
//			int parets_count = 0;
			StringBuilder builderTool = new StringBuilder();
			StringBuilder builderParets = new StringBuilder();
			for (int j = 0; j < contentList.size(); j++) {
				if(contentList.get(j).getToolNum().equals("true") ) {
					tv.append(contentList.get(j).getToolName()+" ");
//					try {
//						json = new JSONObject();
//						json.put("name", contentList.get(j).getToolName());
//						json.put("content", "true");
//						array_tool.put(tool_count, json);
//						//sendJson.put("tool", array_tool);
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					tool_count ++;
//					builderTool = new StringBuilder();
//					builderTool.append("\""+contentList.get(j).getToolName()+"\""+"||");
					if( needtextview.equals("true")&& needshownum.equals("false"))
					builderTool.append(contentList.get(j).getContent_id()+",");
					if(needshownum.equals("true")&&needtextview.equals("true"))
					builderParets.append(contentList.get(j).getContent_id()+",");
					System.out.println("添加到工具中的TextView中的工具"+contentList.get(j).getToolName());
				}
//				else if(!contentList.get(j).getToolNum().equals("false")){
//					tv.append(contentList.get(j).getToolName());
//					if(!contentList.get(j).getToolNum().equals("0")){
						
//					tv.append(contentList.get(j).getToolName()+"("+contentList.get(j).getToolNum()+")");
					
//					try {
//						json = new JSONObject();
//						json.put("name", contentList.get(j).getToolName());
//						json.put("content", contentList.get(j).getToolNum());
//						array_parets.put(parets_count, json);
//						//sendJson.put("parets", array_parets);
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					parets_count++;
//					builderTool = new StringBuilder();
//					builderTool.append("\""+contentList.get(j).getToolNum()+"\""+"||");
//					System.out.println(contentList.get(j).getToolName()+"配件所需要的数量："+contentList.get(j).getToolNum());
//					}
//					builderParets.append("\""+contentList.get(j).getToolNum()+"\""+"||");
//				}
			}
			
			
			
			if(needshownum.equals("true")&&needtextview.equals("true")){
//				try {
//					sendJson.put("parets", array_parets);
//					sendJson.put("parets", builderParets.toString());
//				} catch (JSONException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				if(builderParets.length() >= 1)
					task_parets	= builderParets.substring(0, builderParets.length() - 1);
			}else if( needtextview.equals("true")&& needshownum.equals("false")){
//				try {
//					sendJson.put("tool", array_tool);
//					sendJson.put("工具列表", builderTool.toString());
//				} catch (JSONException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				if( builderTool.length() >= 1)
					task_tool = builderTool.substring(0, builderTool.length() - 1);
			}
			
		}
		
		tv.setLayoutParams(params1);
		
		tv.setTextSize(font_size);
		tv.setTextColor(Color.parseColor(font_color));
		
		b.setLayoutParams(params2);
		b.setText(button_content);
		b.setTextSize(font_size);
		b.setTextColor(Color.parseColor(font_color));
		b.setBackgroundResource(R.drawable.button_inner_form);
		addRela.addView(b);
		addRela.addView(tv);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder  builder = new AlertDialog.Builder(activity);
				builder.setTitle(button_name);
				LinearLayout view;
				
//				if(needshownum.equals("true")){
//					view = (LinearLayout)activity.getLayoutInflater().inflate(R.layout.taskeditor_test_partslistview, null);
//					ListView listview = (ListView) view.findViewById(R.id.partslistview);
//					PartsAdapter adapter = new PartsAdapter(activity, contentList);
//					listview.setAdapter(adapter);
//					builder.setView(view);
//					builder.setNegativeButton("NO",null);
//					builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							StringBuilder s = new StringBuilder();
//							StringBuilder sendParets = new StringBuilder();
//							 int count = 0;
////							 JSONObject json = null;
////							 array_parets = new JSONArray();
//							for (int i = 0; i < contentList.size(); i++) {
////								if(!contentList.get(i).getToolNum().isEmpty()){
//									if(contentList.get(i).getToolNum() != null){
////									tv.append(contentList.get(i).getToolName()+"("+contentList.get(i).getToolNum()+")");
//									if(!contentList.get(i).getToolNum().equals("0")){
//									s.append(contentList.get(i).getToolName()+"("+contentList.get(i).getToolNum()+")");
////									System.out.println("閰嶄欢淇敼鏁伴噺"+contentList.get(i).getToolName());
////									json = new JSONObject();
////									try {
////										json.put("name", contentList.get(i).getToolName());
////										json.put("content", contentList.get(i).getToolNum());
////										array_parets.put(count, json);
////										
////										
////									} catch (JSONException e) {
////										// TODO Auto-generated catch block
////										e.printStackTrace();
////									}
//									sendParets.append("\""+contentList.get(i).getToolNum()+"\""+"||");
//									}else{
//										sendParets.append("\""+"0"+"\""+"||");
//									}
//								
//								}else{
//									sendParets.append("\""+"0"+"\""+"||");
//								}
//										
//							}
////							try {
////									sendJson.put("parets", array_parets);
////								sendJson.put("parets", sendParets.toString());
////							} catch (JSONException e) {
//								// TODO Auto-generated catch block
////								e.printStackTrace();
////							}
//							if(sendParets.length() > 1)
//								sendParets.substring(0,sendParets.length() - 1);
//							task_parets = sendParets.toString();
//							tv.setText(s.toString());
//
//							
//							
//						}
//					});
//					
//					
//				}else{
					 view = (LinearLayout)activity. getLayoutInflater().inflate(R.layout.taskeditor_test_tooleventlistview, null);

					 ListView listView = (ListView) view.findViewById(R.id.tooleventlistview);

					 final ToolEventAdapter adapterEvent = new ToolEventAdapter(activity,contentList);
					 
					listView.setAdapter(adapterEvent);
					if(needtextview.equals("false")&& needshownum.equals("false")){
					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							AlertDialog.Builder builder=new AlertDialog.Builder(activity);
						       
						        builder.setTitle(contentList.get(position).getToolName()+"");
						         
						        
						         
						        final String[] Items= new String[content_note.get(contentList.get(position).getToolName()).size()];
						        for (int i = 0; i < Items.length; i++) {
									Items[i] = content_note.get(contentList.get(position).getToolName()).get(i);
								}
						        builder.setItems(Items, null);
						        builder.setPositiveButton("YES", null);
						       
						        AlertDialog dialog_event = builder.create();
						        dialog_event.show();
						}
					});
					}
					builder.setView(view);
					builder.setNegativeButton("NO", null);
					builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if(needtextview.equals("false")&& needshownum.equals("false")){
								//eventRank = adapterEvent.getListRank();
								eventRank.clear();
								for(int k = 0;k < adapterEvent.getListRank().size();k++)
									eventRank.add( adapterEvent.getListRank().get(k));
								adapter.setmCurPosition(-1);
								adapter.notifyDataSetChanged();
								//currentPosition = -1;
								//adapter = new RankdeEventAdapter(activity,eventRank,currentPosition,mcaCallback);
								mylistview.setAdapter(adapter);
							}else if(needtextview.equals("true")&& needshownum.equals("false")){
								StringBuilder tool = new StringBuilder(); 
								StringBuilder sendTool = new StringBuilder();
//								JSONObject json = null;
								//array_tool = new JSONArray();
								for (int i = 0; i < adapterEvent.getListRank().size(); i++){
//									if(contentList.get(i).getToolNum().equals("true"))
										tool.append(adapterEvent.getListRank().get(i).getToolName()+" ");
//										tv.append(contentList.get(i).getToolName()+" ");
//										try {
//											json = new JSONObject();
//											json.put("name", adapterEvent.getListRank().get(i));
//									System.out.println("閫夋嫨鍚庡墿涓嬪嚑涓伐鍏�:"+adapterEvent.getListRank().get(i));
//											json.put("content", "true");
//											array_tool.put(i, json);
//											
//										} catch (JSONException e) {
//											// TODO Auto-generated catch block
//											e.printStackTrace();
//										}
//										sendTool.append("\""+adapterEvent.getListRank().get(i)+"\""+"||");
										sendTool.append(adapterEvent.getListRank().get(i).getContent_id()+",");
										
								}
								
									
								
//								try {
//									sendJson.put("tool", array_tool);
//									sendJson.put("工具列表", sendTool.toString());
//								} catch (JSONException e1) {
									// TODO Auto-generated catch block
//									e1.printStackTrace();
//								}
								if(sendTool.length() >= 1)
									
								task_tool = sendTool.substring(0, sendTool.length() - 1);
								else
									task_tool = null;
//									task_tool = "";
								System.out.println("要传输的工具列表是："+task_tool);
								tv.setText(tool.toString());
								

							}else if(needshownum.equals("true")){
								StringBuilder s = new StringBuilder();
								StringBuilder sendParets = new StringBuilder();
								for (int k = 0; k < adapterEvent.getListRank().size(); k++){
										s.append(contentList.get(k).getToolName()+" ");
									
										sendParets.append(contentList.get(k).getContent_id()+",");
								}
//										
////								
								if(sendParets.length() >= 1)
									
								task_parets = sendParets.substring(0,sendParets.length() - 1);
								else
									task_parets = null;
//								task_parets = "";
								System.out.println("要传输的配件列表是："+task_parets);
								tv.setText(s.toString());
	//
							}
						}
						
					});
				
					
//				}
		
				
				AlertDialog dialog = builder.create();
				dialog.show();
			
				dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
						
						
			
			}

			
						});
		
		
		
		l.addView(addRela);
		}
		
	
	
	
	private void addListview(ArrayList<Tool> list) {
		// TODO Auto-generated method stub
//		mylistview = new MyListView(activity);
		//adapter = new RankdeEventAdapter(activity, list, currentPosition,mcaCallback);
		mylistview.setAdapter(adapter);
		
		l.addView(mylistview);
	}
	private void addCheck(final String check_name, String check_content,String font_color,int font_size) {
		// TODO Auto-generated method stub
		RelativeLayout addRela = new RelativeLayout(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		addRela.setLayoutParams(params);
		 
		 
		final TextView tv = new TextView(activity);
		final CheckBox cb = new CheckBox(activity);
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
//				subComm.put(check_name, String.valueOf(isChecked));
			}
		});
		addRela.addView(cb);
		addRela.addView(tv);
		l.addView(addRela);
		
		
	}
	private void addSpinner(final String spinner_name, final String spinner_content,
			String font_color, int font_size, final String[] spiSor,final int size) {
		// TODO Auto-generated method stub
		RelativeLayout addRela = new RelativeLayout(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		addRela.setLayoutParams(params);
			
			final TextView tv = new TextView(activity);
			final Spinner s = new Spinner(activity);
			RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			tv.setLayoutParams(params1);
			tv.setText(spinner_name);
			tv.setTextSize(font_size);
			tv.setTextColor(android.graphics.Color.BLACK);
			
			
//			try {
				//JSONObject json = new JSONObject();
				//json.put(spinner_name, spinner_content);
//				json.put("name", spinner_name);
//				json.put("content",spinner_content);
				//array_spinner.put(size,json );
				//sendJson.put("spinner",array_spinner);
//				sendJson.put(spinner_name, spinner_content);
//			} catch (JSONException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if(spinner_name.equals("任务级别"))
				task_level = spinner_content;
			if(spinner_name.equals("任务来源"))
				task_sou = spinner_content;
			if(spinner_name.equals("身份验证"))
				task_check = spinner_content;
			if(spinner_name.equals("任务性质"))
				task_nature = spinner_content;
			
			s.setLayoutParams(params2);
			SpinnerAdapter adapter = new com.example.adapter.taskeditor.SpinnerAdapter(activity, spiSor, font_size, font_color);
			s.setAdapter(adapter);
			for (int i = 0; i < spiSor.length; i++) {
//				System.out.println("鍦ㄦ坊鍔爏pinner涓璼pinner鐨勮祫婧�"+spiSor[i]);
				if (spiSor[i].equals(spinner_content)) {
//					System.out.println("璁剧疆spinner榛樿鍊艰繍琛屼簡鍚楋紵");
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

//					try {
//						JSONObject json = new JSONObject();
//						json.put(spinner_name, spiSor[position]);
//						json.put("name", spinner_name);
//						json.put("content", spiSor[position]);
//						array_spinner.put(size,json );
//						sendJson.put("spinner",array_spinner);
//						sendJson.put(spinner_name, spiSor[position]);
						
//					} catch (JSONException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					if(spinner_name.equals("任务级别"))
						task_level = spiSor[position];
					if(spinner_name.equals("任务来源"))
						task_sou = spiSor[position];
					if(spinner_name.equals("身份验证"))
						task_check = spiSor[position];
					if(spinner_name.equals("任务性质"))
						task_nature = spiSor[position];
					if(!spinner_content.equals(spiSor[position]) && position != 0){
						tv.setTextColor(android.graphics.Color.RED);
					}else{
						tv.setTextColor(android.graphics.Color.BLACK);
					}
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
	
	
	
	
	private void addEditor(final String edittext_name, final String edittext_content,
			String font_color, int font_size,String ischange,final int size) {
		// TODO Auto-generated method stub
		
		
//		
		RelativeLayout addRela = new RelativeLayout(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		addRela.setLayoutParams(params);
		
		final TextView tv = new TextView(activity);
		final EditText et = new EditText(activity);
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		tv.setLayoutParams(params1);
		
		tv.setText(edittext_name);
		tv.setTextSize(font_size);
		tv.setTextColor(android.graphics.Color.BLACK);
		et.setLayoutParams(params2);
		et.setTextSize(font_size);
		et.setTextColor(Color.parseColor(font_color));
		
//		try {
			
			//JSONObject json = new JSONObject();
			//json.put(edittext_name, edittext_content);
			//json.put("name", edittext_name);
			//json.put("content", edittext_content);
			//array_edit.put(size,json );
			//sendJson.put("edit",array_edit);
//			sendJson.put(edittext_name,edittext_content);
//		} catch (JSONException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			if(edittext_name.equals("任务序号"))
				task_num = numIntent;
			if(edittext_name.equals("任务名称"))
				task_name = edittext_content;
			if(edittext_name.equals("任务描述"))
				task_dec = edittext_content;
			if(edittext_name.equals("所涵盖地点"))
				task_loca = edittext_content;
		if(edittext_name.equals("任务序号")){
			
			et.setText(numIntent);
			et.setFocusable(false);
			et.setFocusableInTouchMode(false);
		}
//		if(ischange.equals("false")){
//			if(edittext_content.equals("")){
//				
//				et.setText(numIntent);
//			}else{
//				et.setText(edittext_content);
//				
//			}
//			
//		}else{
		else {
			et.setText(edittext_content);
			if(edittext_name.equals("任务名称") && !"".equals(addTaskName)){
				
					
					et.setFocusable(false);
					et.setFocusableInTouchMode(false);
				
			}
		}
//		}
		
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
				if(edittext_name.equals("任务名称")){
					addTaskName = s.toString();
				}
				if(edittext_name.equals("任务描述"))
					task_dec = edittext_content;
				if(edittext_name.equals("所涵盖地点"))
					task_loca = edittext_content;
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
//				try {
					
					//JSONObject json = new JSONObject();
					//json.put(edittext_name, s.toString());
//					json.put("name", edittext_name);
//					json.put("content", s.toString());
					//array_edit.put(size,json );
					//sendJson.put("edit",array_edit);
//					sendJson.put(edittext_name,s.toString());
//				} catch (JSONException e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				if(edittext_name.equals("任务名称"))
					task_name = s.toString();
				if(edittext_name.equals("任务描述"))
					task_dec = s.toString();
				if(edittext_name.equals("所涵盖地点"))
					task_loca = s.toString();
				if(!edittext_content.equals(s.toString())){
					
					tv.setTextColor(android.graphics.Color.RED);
					
				}else{
					tv.setTextColor(android.graphics.Color.BLACK);
				}
				
				if(edittext_name.equals("任务名称")){
					addTaskName = s.toString();
				}
				
			}
		});
	}
	public ArrayList<Tool> getEventRank() {
		return eventRank;
	}
	public void setEventRank(ArrayList<Tool> eventRank) {
		this.eventRank = eventRank;
	}
	
	
	
	
	private  void addTextView(final String edittext_name, String edittext_content,
			String font_color, int font_size,boolean flag,boolean isList) {
		// TODO Auto-generated method stub
//		
		RelativeLayout addRela = new RelativeLayout(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		addRela.setLayoutParams(params);
		
		final TextView tv_name = new TextView(activity);
		final TextView tv_content = new TextView(activity);
		tv_name.setId(1);
		tv_content.setId(2);
		RelativeLayout.LayoutParams params1 = null;
		RelativeLayout.LayoutParams params2 = null;
		if(flag){
			params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params2.addRule(RelativeLayout.BELOW,1);
		}else{
		 params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
		 params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		}
		tv_name.setLayoutParams(params1);
		tv_name.setText(edittext_name+":");
		tv_name.setTextSize(font_size);
		tv_name.setTextColor(Color.parseColor(font_color));
		if(flag){
			if(isList){
				tv_content.setGravity(Gravity.CENTER);
					
					for (int i = 0; i < textContent.size(); i++) {
						tv_content.append(textContent.get(i));
						tv_content.append("\n");
						
					}	
				
			}else{
				
				StringBuilder builder = new StringBuilder();
//				if(textContent.size()>1){
					
					for (int i = 0; i < textContent.size(); i++) {
						builder.append(textContent.get(i)+" ");
					}
//					builder.append(textContent.get(textContent.size()-1));
//				}else{
//					builder.append(textContent.get(textContent.size()));
					
//				}
				tv_content.setText(builder.toString());
			}
		}else{
			
			tv_content.setText(edittext_content);
		}
		tv_content.setLayoutParams(params2);
		tv_content.setTextSize(font_size);
		tv_content.setTextColor(Color.parseColor(font_color));
//		tv_content.setSingleLine();
		addRela.addView(tv_name);
		addRela.addView(tv_content);
		l.addView(addRela);
		
	}

//	public JSONObject getSendJson() {
//		return sendJson;
//	}
//
//	public void setSendJson(JSONObject sendJson) {
//		this.sendJson = sendJson;
//	}
	
	public String getAddTaskName() {
		return addTaskName;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}

	public void setAddTaskName(String addTaskName) {
		this.addTaskName = addTaskName;
	}

	public String getTask_num() {
		return task_num;
	}

	public void setTask_num(String task_num) {
		this.task_num = task_num;
	}

	public String getTask_name() {
		return task_name;
	}

	public void setTask_name(String task_name) {
		this.task_name = task_name;
	}

	public String getTask_dec() {
		return task_dec;
	}

	public void setTask_dec(String task_dec) {
		if(null == task_dec)
			task_dec = "";
		this.task_dec = task_dec;
	}

	public String getTask_loca() {
		return task_loca;
	}

	public void setTask_loca(String task_loca) {
		if(null == task_loca)
			task_loca = "";
		this.task_loca = task_loca;
	}

	public String getTask_level() {
		return task_level;
	}

	public void setTask_level(String task_level) {
		if(null == task_level)
			task_level = "";
		this.task_level = task_level;
	}

	public String getTask_sou() {
		return task_sou;
	}

	public void setTask_sou(String task_sou) {
		if(null == task_sou)
			task_sou = "";
		this.task_sou = task_sou;
	}

	public String getTask_check() {
		return task_check;
	}

	public void setTask_check(String task_check) {
		this.task_check = task_check;
	}

	public String getTask_tool() {
		return task_tool;
	}

	public void setTask_tool(String task_tool) {
		this.task_tool = task_tool;
	}

	public String getTask_parets() {
		return task_parets;
	}

	public void setTask_parets(String task_parets) {
		this.task_parets = task_parets;
	}

	public String getTask_nature() {
		return task_nature;
	}

	public void setTask_nature(String task_nature) {
		this.task_nature = task_nature;
	}
	
	
	}

