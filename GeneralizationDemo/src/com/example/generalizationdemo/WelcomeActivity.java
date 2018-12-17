package com.example.generalizationdemo;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class WelcomeActivity extends Activity {
	private static final int TIME = 5000;
    private static final int GO_MAIN = 100;
    private static final int GO_GUIDE = 101;
    
    private SharedPreferences preferences;
	private SharedPreferences.Editor editor;

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_MAIN:
                    goMain();
                    break;
                case GO_GUIDE:
                    goGuide();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();//璋冪敤
    }

    private void init() {
//        SharedPreferences sf = getSharedPreferences("data", MODE_PRIVATE);//鍒ゆ柇鏄惁鏄涓�娆¤繘鍏�
//        boolean isFirstIn = sf.getBoolean("isFirstIn", true);
    	
    	preferences = getSharedPreferences("user", MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("isFirstRun", true);
        editor = preferences.edit();
    	
//        SharedPreferences.Editor editor = sf.edit();
        if (isFirstRun) {     //鑻ヤ负true锛屽垯鏄涓�娆¤繘鍏�
            editor.putBoolean("isFirstIn", false);
            mhandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);//灏嗘杩庨〉鍋滅暀5绉掞紝骞朵笖灏唌essage璁剧疆涓鸿烦杞埌
            //   寮曞椤礢plashActivity锛岃烦杞湪goGuide涓疄鐜�
        } else {
            mhandler.sendEmptyMessageDelayed(GO_MAIN, TIME);//灏嗘杩庨〉鍋滅暀5绉掞紝骞朵笖灏唌essage璁剧疆鏂囪烦杞埌                                                                   MainActivity锛岃烦杞姛鑳藉湪goMain涓疄鐜�
        }
        editor.commit();


    }

    private void goMain() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    private void goGuide() {
        Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
        startActivity(intent);
        finish();
    }
}
