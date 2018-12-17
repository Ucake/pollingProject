package com.example.adapter.taskeditor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

import com.example.bean.taskeditor.Tool;
import com.example.generalizationdemo.R;



import android.content.Context;
import android.renderscript.ScriptGroup.Input;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class PartsAdapter extends BaseAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<Tool> list;
//	private HashMap<Integer,Boolean> isSelect;
	
	private ArrayList<Tool> selectList;
	
	public PartsAdapter(Context context, ArrayList<Tool> list) {
		super();
		this.context = context;
		this.list = list;
		selectList = new ArrayList<Tool>();
		inflater = LayoutInflater.from(context);
//		mStringSparseArray = new HashMap<Integer, String>();
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
//		isSelect = new HashMap<Integer, Boolean>();
		
		for (int i = 0; i < list.size(); i++) 
			
			if(!list.get(i).getToolNum().equals("0")){
				selectList.add(new Tool(list.get(i).getToolName(), list.get(i).getToolNum(),list.get(i).getContent_id()));
			}
			
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final Holder holder;
		if(convertView == null){
			holder = new Holder();
			convertView = inflater.inflate(R.layout.taskeditor_partsadapter_partsitem, parent, false);
			holder.name = (TextView) convertView.findViewById(R.id.partsitem_text);
			holder.edit = (EditText) convertView.findViewById(R.id.partsitem_edittext);
			holder.mMyTextWatcher = new MyTextWatcher(position, list);
			holder.edit.addTextChangedListener(holder.mMyTextWatcher);
			convertView.setTag(holder);
		}else{
			holder = (Holder) convertView.getTag();
			holder.updatePosition(position);
		}
		holder.name.setText(list.get(position).getToolName());
		holder.edit.setText(list.get(position).getToolNum());
		holder.edit.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				holder.edit.setInputType(InputType.TYPE_CLASS_NUMBER);
				return false;
			}
		});
		holder.name.setTextSize(20);
		holder.edit.setTextSize(20);
		return convertView;
	}
	class Holder{
		
		TextView name;
		EditText edit;
		MyTextWatcher mMyTextWatcher;
		
		public void updatePosition(int position){
			mMyTextWatcher.updatePosition(position);
		}
	}
//	public ArrayList<String> getSelectList() {
//		return selectList;
//	}

public class MyTextWatcher implements TextWatcher {
		
		private int position;
		private ArrayList<Tool> sparseArrayList;
		
		
		
		public MyTextWatcher(int position, ArrayList<Tool> sparseArrayList) {
			super();
			this.position = position;
			this.sparseArrayList = sparseArrayList;
		}

		public void updatePosition(int position){
			this.position = position;
		}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
			sparseArrayList.get(position).setToolNum(s.toString());
//			System.out.println("�����Ϊ��ʱsΪʲô:"+s.toString());
		}

	}
	
	

}
