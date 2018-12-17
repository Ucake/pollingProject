package com.sensorinfor.main;

import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.generalizationdemo.R;
import com.google.gson.JsonArray;
import com.sensorinfo.adapter.ExAdapter;
import com.sensorinfor.bean.EquipInfo;
import com.sensorinfor.bean.GatherPara;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EquActivity extends Activity {
	private LinearLayout l;

	private JSONObject json;
	private Intent intent;
	private Bitmap mIconExpand;
	private Bitmap mIconCollapse;
	private String equName;
	
	private View.OnClickListener myOnClickListener = null;;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.equipmentdetails_main);
		
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		l = (LinearLayout) findViewById(R.id.equipmentdetails_l);
		
		intent = getIntent();
		try {
			json = new JSONObject(intent.getStringExtra("json"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mIconExpand = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.ups);
		mIconCollapse = BitmapFactory.decodeResource(this.getResources(),
				R.drawable.downs);
		
		equName = intent.getStringExtra("name");
		this.setTitle(equName+"具体信息");
		
		getLayout();
	}

	private void getLayout() {
		// TODO Auto-generated method stub
		try {
			JSONObject jsonObject = json.getJSONObject("equipment");
			String equipmentName = jsonObject.getString("equipment_name");
			JSONArray projectArray = jsonObject.getJSONArray("project");
			for (int i = 0; i < projectArray.length(); i++) {
				JSONObject proJson = projectArray.getJSONObject(i);
				String projectName = proJson.getString("project_name");
				final ImageView proView = addEventFirst(projectName);
				JSONArray speArray = proJson.getJSONArray("specific_project");
				ArrayList<EquipInfo> equList = new ArrayList<>();
				for (int j = 0; j < speArray.length(); j++) {
					proView.setImageBitmap(mIconExpand);
					JSONObject speJson = speArray.getJSONObject(j);
					String sensorId = speJson.getString("sensor_id");
					String speName = speJson.getString("specific_name");
					String speValue = speJson.getString("specific_value");
					String speTime = speJson.getString("time");
					ArrayList<GatherPara> parList = new ArrayList<>();
//					JSONArray parArray = speJson.getJSONArray("parameter_project");
//					for (int k = 0; k < parArray.length(); k++) {
//					for (int k = 0; k < 5; k++) {
//						JSONObject parJson = parArray.getJSONObject(k);
//						String parName = parJson.getString("parameter_name");
//						String parValue = parJson.getString("parameter_value");
//						String parTime = parJson.getString("time");
//						GatherPara gatherPara = new GatherPara();
//						gatherPara.setParameterName("women");
//						gatherPara.setParameterTime("2018-10-18 15:49:32.0");
//						gatherPara.setParameterValue("65");
//						parList.add(gatherPara);
//					}
					EquipInfo equipInfo = new EquipInfo();
					equipInfo.setParameterList(parList);
					equipInfo.setSensorId(sensorId);
					equipInfo.setSpecificName(speName);
					equipInfo.setSpecificTime(speTime);
					equipInfo.setSpecificVaule(speValue);
					equipInfo.setEquName(equipmentName);
					equList.add(equipInfo);
				}
				if(equList == null){
					proView.setImageBitmap(mIconCollapse);
				}else{
					final MyExpandableList expList = addExpandableList(equipmentName,equList);
				
				proView.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(expList.getVisibility() == 0){
							expList.setVisibility(View.GONE);
							proView.setImageBitmap(mIconCollapse);
						}else{
							expList.setVisibility(View.VISIBLE);
							proView.setImageBitmap(mIconExpand);
						}
					}
				});
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void addView(){
		View v = new View(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,70); 
		v.setBackgroundColor(Color.parseColor("#FFFFF0"));
		l.addView(v,params);
	}
	private ImageView addEventFirst(String projectName) {
			
			LinearLayout l1 = new LinearLayout(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.FILL_PARENT, 80);
			l1.setOrientation(LinearLayout.HORIZONTAL);
			l1.setBackgroundColor(Color.parseColor("#FFFAF0"));
			l1.setLayoutParams(params);
			ImageView icon = new ImageView(this);
			final TextView tv = new TextView(this);
			// tv.setBackgroundColor(Color.parseColor("#00BFFF"));
	
			// 让textview占满剩下的空间
			LinearLayout.LayoutParams paramsText = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT);
			paramsText.weight = 1.0f;
			tv.setLayoutParams(paramsText);
	
			icon.setPadding(2, 2, 2, 5);
			tv.setGravity(Gravity.CENTER_VERTICAL);
			tv.setTypeface(Typeface.MONOSPACE);
			// tv.setTextColor(Color.parseColor(color));
			tv.setTextSize(20);
			l1.addView(icon);
			l1.addView(tv);
			l.addView(l1);
	
			tv.setText(projectName);
			icon.setImageBitmap(mIconCollapse);
				
				return icon;		
			
			}
	private MyExpandableList addExpandableList(String equipmentName,final ArrayList<EquipInfo> list){
		final MyExpandableList myExpandableList = new MyExpandableList(this);
//		myOnClickListener = new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Map<String, Object> map = (Map<String, Object>) v.getTag();
//                int groupPosition = (int) map.get("groupPosition");
////                boolean isExpand = (boolean) map.get("isExpanded");   //这种是通过tag传值
//                boolean isExpand = myExpandableList.isGroupExpanded(groupPosition);    //判断分组是否展开
//
//                if (isExpand) {
//                	myExpandableList.collapseGroup(groupPosition);
//                } else {
//                	myExpandableList.expandGroup(groupPosition);
//                }
//                
//			}
//			
//		};
//		,myOnClickListener
		ExAdapter adapter = new ExAdapter(list, this);
		myExpandableList.setAdapter(adapter);
		myExpandableList.setGroupIndicator(null);
		myExpandableList.setDivider(null);
		for (int i = 0; i < list.size(); i++) {
			
			if(0 < list.get(i).getParameterList().size()){
				myExpandableList.expandGroup(i);
				break;
			}
		}

		myExpandableList.setOnGroupClickListener(new OnGroupClickListener() {
			
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				// TODO Auto-generated method stub
				if(0 == list.get(groupPosition).getParameterList().size()){
					myExpandableList.collapseGroup(groupPosition);
					Toast.makeText(EquActivity.this, "组被点击了，跳转到具体的Activity", Toast.LENGTH_SHORT).show();
					return true;
				}else{
					return false;
				}
				
			}
		});
		myExpandableList.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				System.out.println(list.get(groupPosition).getParameterList().get(childPosition).getParameterName());
				
				return false;
			}
		});
		l.addView(myExpandableList);
		return myExpandableList;
	}

}
