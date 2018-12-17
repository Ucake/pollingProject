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
	private EditText abnormal_time;//�ϱ�ʱ��
	private EditText abnormal_worker;//�ϱ���
	private EditText abnormal_workshop;//��������
	private EditText abnormal_region;//����Ѳ������
	private EditText abnormal_point;//����Ѳ���
	private EditText abnormal_event;//�쳣Ѳ���¼�
	private EditText abnormal_position;//�쳣����λ��
	private EditText abnormal_comment;//�쳣��������
	private EditText abnormal_level;//�쳣����
	private EditText abnormal_description;//�쳣����
	private EditText abnormal_measures;//�����ʩ
	
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
        
        abnormal_time.setText("9��1��17:56:23");
        abnormal_worker.setText("��С��");
        abnormal_workshop.setText("��2����");
        abnormal_region.setText("����");
        abnormal_point.setText("��3����");
        abnormal_event.setText("������");
        abnormal_position.setText("��6�����Ӵ�");
        abnormal_comment.setText("�޷�����豸");
        abnormal_level.setText("һ��");
        abnormal_description.setText("���ڴ����Ѻ�");
        abnormal_measures.setText("");
        
        assign_abnormal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
//				intent.setClass(AbnormalDetailActivity.this, AbnormalAssignActivity.class);
				intent.setClass(AbnormalFastActivity.this, AssignAbnormalActivity.class);
				intent.putExtra("taskName", "1");
				intent.putExtra("tasknature", "ά������");
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
