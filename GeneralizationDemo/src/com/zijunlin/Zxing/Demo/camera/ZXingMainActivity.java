package com.zijunlin.Zxing.Demo.camera;



import com.example.generalizationdemo.R;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ZXingMainActivity extends Activity {
	
	
	private TextView text1;
	private TextView text2;
	private EditText edit1;
	private EditText edit2;
	private Button button;
	private Handler handler; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_page);
        Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_LONG).show();
        
        
        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        edit1 = (EditText) findViewById(R.id.edit1);
        edit2 = (EditText) findViewById(R.id.edit2);
        button = (Button) findViewById(R.id.button);
        text1.setText("第一个1");
//        edit1.setText(CaptureActivity.res3.substring(1));
        edit1.setText(CaptureActivity.res3);
        text2.setText("最后的数");
//        edit2.setText(CaptureActivity.res3.subSequence(2, 11));
        
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				 handler = new Handler(){
						
						public void handleMessage(Message msg){
							super.handleMessage(msg);
							if (msg.what == 12138) {
								
								String backMsg = msg.obj.toString();	
								
								Log.e("qqqqq", backMsg);
								if(!backMsg.equals(null)){
									
//									ReadytoinstallJson readytointall = new ReadytoinstallJson();
//									readytointall.readytoinstall(backMsg);
//									System.out.println(backMsg);
//									if(readytointall.getResulta().equals("10000")){
//										System.out.println("%%%%%%%%");
//									}
//									
								}

								
							}
						}

					};
					

						String value = "phone="+ "18810464523";
//					String value = "worker="+ "张三" + "&address=" + "朝阳区";
						
//						LoginThread thread = new LoginThread(handler, value);
//					
//						new Thread(thread).start();
//					
			    }
			
				
				
			
		});
        
        
       
}
   
  
}
