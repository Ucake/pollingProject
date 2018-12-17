 package com.example.adpter;

import java.util.ArrayList;

import com.example.bean.Task;
import com.example.generalizationdemo.R;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class TaskAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Task> task;
	private LayoutInflater inflater;

	public TaskAdapter(Context context, ArrayList<Task> task) {
		super();
		this.context = context;
		this.task = task;
		inflater = inflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return task.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return task.get(position);
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
//		int mission_condition = task.get(position).getMission_condition();
				
			if (convertView == null) {
			holder = new Holder();
			convertView = inflater.inflate(R.layout.task_all_item, parent,false);
			holder.task_item_text = (TextView) convertView
					.findViewById(R.id.task_all_item_text1);
//			holder.task_item_time =  (TextView) convertView
//					.findViewById(R.id.task_all_item_text2);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}

		holder.task_item_text.setText(task.get(position).getTask_item_text());
		holder.task_item_text.setTextColor(android.graphics.Color.BLACK);
		
//		if(mission_condition == 1){
//			
////			holder.task_item_time.setText("未下发");
//			holder.task_item_time.setTextColor(android.graphics.Color.YELLOW);
//			
//		}else{
////			holder.task_item_time.setText("已下发");
//			holder.task_item_time.setTextColor(android.graphics.Color.YELLOW);
//		}
//			else if(mission_condition == 2){
//			
//			holder.task_item_time.setText("任务拒收");
//			holder.task_item_time.setTextColor(android.graphics.Color.GRAY);
//		}else if(mission_condition == 3){
//			
//			holder.task_item_time.setText("任务接受");
//			holder.task_item_time.setTextColor(android.graphics.Color.BLUE);
//		
//	}else if(mission_condition == 4){
//		
//		holder.task_item_time.setText("任务执行中");
//		holder.task_item_time.setTextColor(android.graphics.Color.BLACK);
//	}else if(mission_condition == 5){
//		
//		holder.task_item_time.setText("任务完成");
//		holder.task_item_time.setTextColor(android.graphics.Color.CYAN);
//	}else if(mission_condition == 6){
//		
//		holder.task_item_time.setText("审核通过");
//		holder.task_item_time.setTextColor(android.graphics.Color.GREEN);
//	}else if(mission_condition == 7){
//		
//		holder.task_item_time.setText("审核未通过");
//		holder.task_item_time.setTextColor(android.graphics.Color.MAGENTA);
//	}
		holder.task_item_text.setTextSize(18);
//		holder.task_item_time.setTextSize(20);
		return convertView;
	}

	protected class Holder {
		TextView task_item_text;
//		TextView task_item_time;
	}

}
