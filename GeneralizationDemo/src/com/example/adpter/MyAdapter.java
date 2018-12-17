package com.example.adpter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.bean.Person;
import com.example.generalizationdemo.R;




import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter {

	public Context context;
	public ArrayList<Person> list;
	public LayoutInflater inflater;
	public InnerItemOnclickListener mListener;
//	public TextView publicText;
	
	//用来存储每个按钮的状态
//	public static HashMap<Integer, Boolean> isCheck;
	public static HashMap<Integer, Boolean> isSelect;
//,TextView textView
	public interface InnerItemOnclickListener {
		int itemClic(int position,View v,Button button);
	}
	
//,TextView publicText
	public MyAdapter(Context context, ArrayList<Person> list) {
		super();
		this.context = context;

		this.list = list;
//		this.publicText = publicText;

		inflater = LayoutInflater.from(context);
		

        isSelect = new HashMap<Integer,Boolean>();
        initData();

	}
	
	 private void initData() {
	        for(int i = 0;i< list.size();i++){

	            isSelect.put(i,list.get(i).isF());
	        }
	    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Person getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		
		
		final Holder holder;

		int colorflag = list.get(position).getColorflag();
//		int flag = list.get(position).flag;
		if (convertView == null)
		
			{
			holder = new Holder();
			convertView = inflater.inflate(R.layout.people_item, parent, false);

			holder.name = (TextView) convertView.findViewById(R.id.item_name);
			holder.birth = (TextView) convertView.findViewById(R.id.item_birth);
			holder.itembtn = (Button) convertView.findViewById(R.id.item_btn);

			

			convertView.setTag(holder);

		} else {


			holder = (Holder) convertView.getTag();

		}
		holder.name.setText(list.get(position).getName());
		holder.name.setTextSize(20);
		
		holder.name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.itemClic(position,v, null);
			}
		});
		holder.birth.setText(list.get(position).getBirth());
		holder.birth.setTextSize(20);
		if(colorflag == 1){

			holder.birth.setTextColor(android.graphics.Color.GREEN);
		
		holder.birth.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.itemClic(position,v, null);
			}
		});
		}else{
			holder.birth.setTextColor(android.graphics.Color.GRAY);
			
		}

		holder.itembtn.setTextSize(20);
		holder.itembtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				mListener.itemClic(position,v,holder.itembtn);
				
			}
		});
		if(isSelect.get(position)) {
            holder.itembtn.setText("已选");
            holder.itembtn.setTextColor(android.graphics.Color.RED);//还是不行
        }else{
            holder.itembtn.setText("选我");
            holder.itembtn.setTextColor(android.graphics.Color.BLUE);
        }

		return convertView;
	}

	protected class Holder {
		TextView name;
		TextView birth;
		Button itembtn;
	}

	public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener) {
		this.mListener = listener;
	}

	
	

}
