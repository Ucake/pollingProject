package com.example.generalizationdemo;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class UpdateActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage3);
        
	}
	
	public static void openApk(Context context,File file) {
	    Intent intent = new Intent(Intent.ACTION_VIEW);
	    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
	    context.startActivity(intent);
	}
}

