package com.example.adapter.taskeditor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;



import com.example.bean.taskeditor.Tool;
import com.example.generalizationdemo.R;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;

public class ToolEventAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Tool> list;
	private HashMap<Integer,Boolean> isSelect;
	
	private ArrayList<Tool> listRank;
	
	public ToolEventAdapter(Context context, ArrayList<Tool> list) {
		super();
		this.context = context;
		this.list = list;
		
		inflater = LayoutInflater.from(context);
		listRank = new ArrayList<Tool>();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		isSelect = new HashMap<Integer, Boolean>();
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getToolNum().equals("true")) {
				listRank.add(list.get(i));
				isSelect.put(i, true);
			}else{
				isSelect.put(i, false);
			}
		}
		
			
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Tool getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Holder holder;
		final int pos = position;
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.taskeditor_tooleventdapter_tooleventitem, parent,false);
			
			holder.name = (TextView) convertView.findViewById(R.id.tooleventitem_text);
			holder.check = (CheckBox) convertView.findViewById(R.id.tooleventsitem_checkbox);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		
		holder.name.setText(list.get(position).getToolName());
		holder.name.setTextSize(20);
//		if(isSelect.get(position)){
//			holder.check.setChecked(true);
////			holder.check.setBackgroundResource(android.graphics.Color.GRAY);
//			
//		}else{
//			holder.check.setChecked(false);
//			
//		}
		holder.check.setOnCheckedChangeListener(null);
		holder.check.setChecked(isSelect.get(position));
		holder.check.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					System.out.println("���¼�����������"+pos+":"+list.get(pos).getToolName());
					isSelect.put(pos,false);
					holder.check.setChecked(true);
					if(list.get(pos).getToolNum().equals("false"))
					list.get(pos).setToolNum("true");
					if (!listRank.contains(list.get(pos).getToolName())) {
						listRank.add( list.get(pos));
					}
				}else{
					isSelect.put(pos,true);
					holder.check.setChecked(false);
					for (int i = 0; i < listRank.size(); i++) {
						
						if(listRank.get(i).getContent_id().equals(list.get(pos).getContent_id()))
							listRank.remove(i);
					}
					if (list.get(pos).getToolNum().equals("true")) {
						list.get(pos).setToolNum("false");
						
					}
				}
			}
		});
			
		
		return convertView;
	}

	class Holder{
		
		TextView name;
		CheckBox check;
		
	}

	public ArrayList<Tool> getListRank() {
		
		
		return listRank;
	}

	
	
}
