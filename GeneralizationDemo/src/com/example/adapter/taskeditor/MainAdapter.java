package com.example.adapter.taskeditor;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;





import com.example.bean.taskeditor.Task;
import com.example.generalizationdemo.R;




import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<Task> list;
//	private TreeMap<Integer, String> treeMap;
	private LayoutInflater inflater;
	public InnerItemOnclickListener mListener;
	//�����ж�ÿ����ť�Ƿ�ɵ��
	public  HashMap<Integer, Boolean> isClick;
	
	public interface InnerItemOnclickListener {
		void itemClic(View v,String name,String num,String task_num);
	}
	public MainAdapter(Context context,ArrayList<Task> list) {
		super();
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		isClick = new HashMap<Integer,Boolean>();
//		initData();
	}

//	 private void initData() {
//	        for(int i = 0;i< list.size();i++){
//	        	
//	        	isClick.put(i,list.get(i).isFlag());
//	        }
//	    }
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
//		Log.e("position",""+position);
		Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.taskeditor_mainadapter_task_item, parent,false);
			holder.id_task = (TextView) convertView.findViewById(R.id.id_text);
			holder.name_task = (TextView) convertView.findViewById(R.id.name_text);
			holder.itembtn = (Button) convertView.findViewById(R.id.editor_text);
//			holder.sendBtn = (Button) convertView.findViewById(R.id.send_text);
			
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
		}
//		holder.id_task.setText(""+list.get(position).getId());
		holder.id_task.setText(list.get(position).getTasknum());
		holder.name_task.setText(list.get(position).getTaskName());
		holder.id_task.setTextSize(20);
		holder.name_task.setTextSize(20);
		holder.itembtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mListener.itemClic(v,list.get(position).getTaskName(),list.get(position).getTasknum(),list.get(position).getTask_num());
			}
		
		});
//		holder.sendBtn.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				mListener.itemClic(v,list.get(position).getTaskName(),list.get(position).getTasknum(),list.get(position).getTask_num());
//			}
//			
//		});
		holder.itembtn.setTextSize(20);
//		holder.sendBtn.setTextSize(20);
		return convertView;
	}
	protected class Holder {
		TextView id_task;
		TextView name_task;
		Button itembtn;
//		Button sendBtn;
	}
	public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener) {
		this.mListener = listener;
	}
}
