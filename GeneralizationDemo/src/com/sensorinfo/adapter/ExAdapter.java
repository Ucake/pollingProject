package com.sensorinfo.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.generalizationdemo.R;
import com.sensorinfor.bean.Attribute;
import com.sensorinfor.bean.Constant;
import com.sensorinfor.bean.EquipInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ExAdapter extends BaseExpandableListAdapter {

	private ArrayList<EquipInfo> mArrayList;
	private Context mContext;
	private LayoutInflater inflater;
//	private View.OnClickListener myOnClickListener;
	
	
	//,View.OnClickListener myOnClickListener
	public ExAdapter(ArrayList<EquipInfo> mArrayList, Context mContext) {
		super();
		this.mArrayList = mArrayList;
		this.mContext = mContext;
//		this.myOnClickListener = myOnClickListener;
		
		inflater = LayoutInflater.from(mContext);
		
		
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mArrayList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return mArrayList.get(groupPosition).getParameterList().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mArrayList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mArrayList.get(groupPosition).getParameterList().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(null == convertView){
			convertView = inflater.inflate(R.layout.ex_first_item ,parent, false);
			holder = new Holder();
			holder.name = (TextView) convertView.findViewById(R.id.name_tv);
			holder.time = (TextView) convertView.findViewById(R.id.time_tv);
			holder.value = (TextView) convertView.findViewById(R.id.value_tv);
			holder.imageBut = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(holder);
			
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.name.setText(mArrayList.get(groupPosition).getSpecificName());
		holder.time.setText(mArrayList.get(groupPosition).getSpecificTime());
		holder.value.setText(mArrayList.get(groupPosition).getSpecificVaule());
		if(isExpanded){
			holder.imageBut.setBackgroundResource(R.drawable.jian);
		}else{
			holder.imageBut.setBackgroundResource(R.drawable.plus);
		}
		
//		Map<String,Object> tagMap = new HashMap<>();
//		tagMap.put("groupPosition", groupPosition);
//		tagMap.put("isExpanded", isExpanded);
//		holder.imageBut.setTag(tagMap);
//		holder.imageBut.setOnClickListener(myOnClickListener);
//		
		
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(null == convertView){
			convertView = inflater.inflate(R.layout.ex_second_item, parent, false);
			holder = new ViewHolder();
			holder.name = (TextView) convertView.findViewById(R.id.name_tv);
			holder.time = (TextView) convertView.findViewById(R.id.time_tv);
			holder.value = (TextView) convertView.findViewById(R.id.value_tv);		
			convertView.setTag(holder);
			
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(mArrayList.get(groupPosition).getParameterList().get(childPosition).getParameterName());
		holder.time.setText(mArrayList.get(groupPosition).getParameterList().get(childPosition).getParameterTime());
		holder.value.setText(mArrayList.get(groupPosition).getParameterList().get(childPosition).getParameterValue());
		
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	class Holder{
		ImageView imageBut;
		TextView name;
		TextView value;
		TextView time;
		
	}
	class ViewHolder{
		
		TextView name;
		TextView value;
		TextView time;
		
	}
	
}
