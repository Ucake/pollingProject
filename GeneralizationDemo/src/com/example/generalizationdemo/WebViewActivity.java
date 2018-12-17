package com.example.generalizationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewActivity extends Activity {
	
	private String path;
	private WebView webview;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
	
        path= "http://www.baidu.com";
    	webview = (WebView) findViewById(R.id.webview);
    	webview.getSettings().setJavaScriptEnabled(true);
    	webview.setWebViewClient(new WebViewClient());
    	webview.loadUrl(path);
        
	}

}
