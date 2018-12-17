package com.example.testtransmit;

import java.util.ArrayList;

import com.example.generalizationdemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class RankdeEventAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<EventRank> list;
	private LayoutInflater inflater;
	
	public RankdeEventAdapter(Context context, ArrayList<EventRank> list) {
		super();
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.ranked_evevt,parent,false);
			holder.id = (TextView) convertView.findViewById(R.id.ranked_event_id);
			holder.name = (TextView) convertView.findViewById(R.id.ranked_event_name);
			
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
		holder.id.setText(""+list.get(position).getId());
		holder.name.setText(list.get(position).getName());
		return convertView;
	}
	
	class Holder{
		TextView id;
		TextView name;
	}

}
