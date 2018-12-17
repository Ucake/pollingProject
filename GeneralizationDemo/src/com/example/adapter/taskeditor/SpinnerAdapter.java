package com.example.adapter.taskeditor;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {
	private Context context;
	private String[] array;
	private int size;
	private String color;

	public SpinnerAdapter(Context context, String[] array,int size,String color) {
		super(context, android.R.layout.simple_dropdown_item_1line, array);
		// TODO Auto-generated constructor stub
		this.array = array;
		this.context = context;
		this.size = size;
		this.color = color;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {  
            LayoutInflater inflater = LayoutInflater.from(context);  
            convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);  
        }  
  
        //此处text1是Spinner默认的用来显示文字的TextView  
        TextView tv = (TextView) convertView.findViewById(android.R.id.text1);  
        tv.setText(array[position]);  
        tv.setTextSize(size);  
        tv.setTextColor(Color.parseColor(color));  
        return convertView;  
		
		
	}

	

	

	

}
