package com.cement.abnormalmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.generalizationdemo.AbnormalManagementActivity;
import com.example.generalizationdemo.R;

public class AbnormalFastActivity extends Activity {
	private EditText abnormal_time;//上报时间
	private EditText abnormal_worker;//上报人
	private EditText abnormal_workshop;//所属车间
	private EditText abnormal_region;//所属巡检区域
	private EditText abnormal_point;//所属巡检点
	private EditText abnormal_event;//异常巡检事件
	private EditText abnormal_position;//异常具体位置
	private EditText abnormal_comment;//异常工作内容
	private EditText abnormal_level;//异常级别
	private EditText abnormal_description;//异常描述
	private EditText abnormal_measures;//建议措施
	
	private Button assign_abnormal;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abnormal_detail);
        
        abnormal_time = (EditText) findViewById(R.id.abnormal_time);
        abnormal_worker = (EditText) findViewById(R.id.abnormal_worker);
        abnormal_workshop = (EditText) findViewById(R.id.abnormal_workshop);
//        abnormal_region = (EditText) findViewById(R.id.abnormal_region);
        abnormal_point = (EditText) findViewById(R.id.abnormal_point);
//        abnormal_event = (EditText) findViewById(R.id.abnormal_event);
//        abnormal_position = (EditText) findViewById(R.id.abnormal_position);
//        abnormal_comment = (EditText) findViewById(R.id.abnormal_comment);
        abnormal_level = (EditText) findViewById(R.id.abnormal_level);
        abnormal_description = (EditText) findViewById(R.id.abnormal_description);
        abnormal_measures = (EditText) findViewById(R.id.abnormal_measures);
        
        assign_abnormal = (Button) findViewById(R.id.assign_abnormal);
        
        abnormal_time.setText("9月1日17:56:23");
        abnormal_worker.setText("王小兰");
        abnormal_workshop.setText("第2车间");
        abnormal_region.setText("西门");
        abnormal_point.setText("第3厂房");
        abnormal_event.setText("除氧器");
        abnormal_position.setText("第6个焊接处");
        abnormal_comment.setText("无法检查设备");
        abnormal_level.setText("一级");
        abnormal_description.setText("出口处有裂痕");
        abnormal_measures.setText("");
        
        assign_abnormal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
//				intent.setClass(AbnormalDetailActivity.this, AbnormalAssignActivity.class);
				intent.setClass(AbnormalFastActivity.this, AssignAbnormalActivity.class);
				intent.putExtra("taskName", "1");
				intent.putExtra("tasknature", "维修任务");
				startActivityForResult(intent, 1);
				startActivity(intent);
			}
		});
        
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			Intent intent1 = new Intent(AbnormalFastActivity.this,AbnormalManagementActivity.class);
			startActivity(intent1);
		}
		return super.onKeyDown(keyCode, event);
	}
}
