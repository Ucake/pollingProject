package com.example.adapter.taskeditor;

import java.util.ArrayList;



import com.example.bean.taskeditor.Tool;
import com.example.generalizationdemo.R;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

public class RankdeEventAdapter extends BaseAdapter {

	

	public interface Callback {
		public void click(View v);
	}

	private Context context;
	private ArrayList<Tool> list;
	private LayoutInflater inflater;
	private int mCurPosition;
	public int getmCurPosition() {
		return mCurPosition;
	}

	public void setmCurPosition(int mCurPosition) {
		this.mCurPosition = mCurPosition;
	}
	private Callback mCallback;
	
	
	public RankdeEventAdapter(Context context, ArrayList<Tool> list,int mCurPosition,Callback mcaCallback) {
		super();
		this.context = context;
		this.list = list;
		this.mCurPosition = mCurPosition;
		this.mCallback = mcaCallback;
		inflater = LayoutInflater.from(context);
//		System.out.println("�������¼�������������������е�list"+ list.toString());
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
		System.out.println("事件排序的适配器更新后再次调用这个函数没？");
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.taskeditor_test_ranked_event, parent, false);
			holder.imageButton_down =(ImageButton) convertView.findViewById(R.id.ranked_event_imagedown);
			
			holder.imageButton_up = (ImageButton) convertView.findViewById(R.id.ranked_event_imageup);
			holder.textView = (TextView) convertView.findViewById(R.id.ranked_event_text);
			convertView.setTag(R.id.tag_holder,holder);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag(R.id.tag_holder);
//			holder = (Holder) arg1.getTag();
		}
		
		holder.textView.setText(list.get(position).getToolName());
		
		if(  mCurPosition == position && mCurPosition == 0)
		{
			
			holder.imageButton_down.setVisibility(View.VISIBLE);
			System.out.println("事件排序适配器中向下的图片显示没？");
		}

		else if( mCurPosition == position && mCurPosition == list.size() - 1){
			
			holder.imageButton_up.setVisibility(View.VISIBLE);
			System.out.println("事件排序适配器中向上的图片显示没？");
		}
		else if(mCurPosition == position && mCurPosition != 0 && mCurPosition != list.size() - 1){
			holder.imageButton_down.setVisibility(View.VISIBLE);
			holder.imageButton_up.setVisibility(View.VISIBLE);
			System.out.println("事件排序适配器中向下的图片显示没？");
			System.out.println("事件排序适配器中向上的图片显示没？");
		}else{
			holder.imageButton_down.setVisibility(View.INVISIBLE);
			holder.imageButton_up.setVisibility(View.INVISIBLE);
			
		}
		holder.imageButton_down.setTag(position);
		holder.imageButton_down.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mCallback.click(arg0);
			}
		});
		holder.imageButton_up.setTag(position);
		holder.imageButton_up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mCallback.click(arg0);
			}
		});
		convertView.setTag(R.id.tag_item_click,position);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				System.out.println("事件排序适配器中listview点击事件执行了吗？");
				mCallback.click(arg0);
			}
		});
		
		holder.textView.setTextSize(20);
		return convertView;
	}
	
	class Holder{
		ImageButton imageButton_up;
		ImageButton imageButton_down;
		TextView textView;
	}
	public void refresh(int currentPosition){
		mCurPosition = currentPosition;
		notifyDataSetChanged();
		System.out.println("事件排序适配器中适配器更新的当前位置为："+mCurPosition);
//		System.out.println("�������и����������ķ�����Сͼ����ʾ��λ��"+mCurPosition);
	}
}
