package com.example.adpter;

import java.util.ArrayList;

import com.example.adpter.MyAdapter.Holder;
import com.example.adpter.MyAdapter.InnerItemOnclickListener;

import com.example.bean.TaskTask;
import com.example.generalizationdemo.R;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MyAdapterTask extends BaseAdapter{
	
	public Context context;
	public ArrayList<TaskTask> list;
	public LayoutInflater inflater;
//	public InnerItemOnclickListener mListener;
	
//	public interface InnerItemOnclickListener {
//		void itemClic(View v);
//	}
	
	public MyAdapterTask(Context context, ArrayList<TaskTask> list) {
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
		Holder holder;
		int flag = list.get(position).getMission_condition();
		if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.task_all_item, parent,false);
			holder.name = (TextView) convertView.findViewById(R.id.task_all_item_text1);
//			holder.time = (TextView) convertView.findViewById(R.id.task_all_item_text2);
			
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
//		 if(flag == 0){
			 holder.name.setText(list.get(position).getName());
//			holder.time.setText("未下发");
//			holder.time.setTextColor(android.graphics.Color.RED);
//			} 
//		 else if(flag == 1){
//			holder.name.setText(list.get(position).getName());
////			holder.name.setTextColor(android.graphics.Color.GREEN);
//			holder.time.setText("任务下发");
//			holder.time.setTextColor(android.graphics.Color.YELLOW);
//			
//		}else if(flag == 2){
//			holder.name.setText(list.get(position).getName());
//			holder.time.setText("任务拒收");
//			holder.time.setTextColor(android.graphics.Color.GRAY);
//		}else if(flag == 3){
//			holder.name.setText(list.get(position).getName());
//			holder.time.setText("任务接受");
//			holder.time.setTextColor(android.graphics.Color.BLUE);
//		
//	}else if(flag == 4){
//		holder.name.setText(list.get(position).getName());
//		holder.time.setText("任务执行中");
//		holder.time.setTextColor(android.graphics.Color.BLACK);
//	}else if(flag == 5){
//		holder.name.setText(list.get(position).getName());
//		holder.time.setText("任务完成");
//		holder.time.setTextColor(android.graphics.Color.CYAN);
//	}else if(flag == 6){
//		holder.name.setText(list.get(position).getName());
//		holder.time.setText("审核通过");
//		holder.time.setTextColor(android.graphics.Color.GREEN);
//	}else if(flag == 7){
//		holder.name.setText(list.get(position).getName());
//		holder.time.setText("审核未通过");
//		holder.time.setTextColor(android.graphics.Color.MAGENTA);
//	}
//		 else{
//			 holder.name.setText(list.get(position).getName());
//				holder.time.setText("已下发");
//				holder.time.setTextColor(android.graphics.Color.GREEN); 
//		 }
		holder.name.setTextSize(16);
		holder.name.setTextColor(android.graphics.Color.BLACK);
//		holder.time.setTextSize(18);

		return convertView;
	}
	
	protected class Holder {
		TextView name;
//		TextView time;
		
	}

//	public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener) {
//		this.mListener = listener;
//	}

}
