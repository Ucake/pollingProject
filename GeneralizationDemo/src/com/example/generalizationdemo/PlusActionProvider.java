package com.example.generalizationdemo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.cement.check.CheckActivity;
import com.cement.check.CheckListActivity;
import com.cement.data.CheckJson;
import com.cement.thread.CheckThread;
import com.cement.thread.UpdateThread;
import com.example.generalizationdemo.R;
import com.zijunlin.Zxing.Demo.CaptureActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class PlusActionProvider extends ActionProvider {

	private ProgressDialog pd;
	private String TAG = "zouhuorumo0";
	public static double oldversion = 1.9;
	private Handler handler_update;
	long fileLength = 0;//下载的文件长度
	long contentLength;//文件的总长度
	private String filePath;//文件的保存路径
	int len;
	private Handler handler;
	private String version;
	private String path;
	private Context context;
	private static final String DATA_TYPE_APK = "application/vnd.android.package-archive";

	public PlusActionProvider(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	public View onCreateActionView() {
		return null;
	}

	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		subMenu.clear();
		subMenu.add(context.getString(R.string.plus_scan))
				.setIcon(R.drawable.qr_code_64px)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						
						Intent intent = new Intent();
						intent.setClass(context, CaptureActivity.class);
						context.startActivity(intent);						
						return true;
					}
				});
		
		//修改密码先给注释
//		subMenu.add(context.getString(R.string.password_change))
//				.setIcon(R.drawable.password_63)
//				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//					@Override
//					public boolean onMenuItemClick(MenuItem item) {
//						Intent intent = new Intent();
//						intent.setClass(context, ForgetActivity.class);
//						context.startActivity(intent);						
//						return true;
//					}
//				});
		//版本更新啊喂
		subMenu.add(context.getString(R.string.update))
		.setIcon(R.drawable.update_64px)
		.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				handler_update = new Handler(){
					
					public void handleMessage(Message msg){
						super.handleMessage(msg);
						if (msg.what == 12138) {
							
						String backMsg = msg.obj.toString();
						
						Log.v("sanciwoshou", backMsg);
						try{
							
							JSONObject toor = new JSONObject(backMsg);
							version = toor.getString("version");
							Log.v("yishangan", version +"="+oldversion);
							if(!version.equals(String.valueOf(oldversion))){
								
								new AlertDialog.Builder(context)
						        .setTitle("版本更新")
						        .setMessage("系统检测到有新的版本，此次要更新嘛？")
						        .setPositiveButton("忽略", new DialogInterface.OnClickListener() {
						            @Override
						            public void onClick(DialogInterface dialog, int which) {
						            	
						            }
						        
						        })
						        .setNegativeButton("更新", new DialogInterface.OnClickListener() {
						            public void onClick(DialogInterface dialog, int which) {
					
						            	pd = new ProgressDialog(context);
								    	pd.setTitle("版本更新");
								    	pd.setIndeterminate(false);
								    	pd.setCancelable(false);
								    	pd.setMessage("正在下载新版本，请耐心等待");
								    	pd.show();
								    	new Thread(new Runnable() {
						                    @Override
						                    public void run() {
						                        download();
						                        
						                        String filepath = "/sdcard/apk/GeneralizationDemo.apk";
								            	File file = new File(filepath);
								            	pd.dismiss();
								            	openFile(file);
						                        
						                    }
						                }).start();
						            	
						            	}	
						            })
						       
						        .show();
							
							}else{
								new AlertDialog.Builder(context)
						        .setTitle("版本更新")
						        .setMessage("系统检测到已为当前最新版本")
						        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
						            @Override
						            public void onClick(DialogInterface dialog, int which) {
						            	
						            }
						        
						        })
						        .show();
							}
							
						} catch (JSONException e) {
					        e.printStackTrace();
					    
						}
					}
				}
			};
				
				String value = "";
				UpdateThread thread = new UpdateThread(handler_update, value);
				new Thread(thread).start();
			
				
				
						
				return true;
			}
		});
		
		subMenu.add(context.getString(R.string.config_set))
				.setIcon(R.drawable.config_set_64px)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
//						((HomePageya)context).startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS),0);  
						
						Intent intent = new Intent();
						intent.setAction(android.provider.Settings.ACTION_SETTINGS);
						context.startActivity(intent);
						return false;
					}
				});
//		subMenu.add(context.getString(R.string.plus_scan))
//				.setIcon(R.drawable.ofm_qrcode_icon)
//				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//					@Override
//					public boolean onMenuItemClick(MenuItem item) {
//						return false;
//					}
//				});
//		subMenu.add(context.getString(R.string.plus_take_photo))
//				.setIcon(R.drawable.ofm_camera_icon)
//				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//					@Override
//					public boolean onMenuItemClick(MenuItem item) {
//						return false;
//					}
//				});
	}

	@Override
	public boolean hasSubMenu() {
		return true;
	}
	
    private void download() {
        try {
        	String downloadUrl = "http://123.206.16.157:8080/water/imageDownload.req?action=apkdownload&version="+version;
            URL url = new URL(downloadUrl);
            //打开连接
            URLConnection conn = url.openConnection();
            //打开输入流
            InputStream is = conn.getInputStream();
            //获得长度
            int contentLength = conn.getContentLength();
            Log.e(TAG, "contentLength = " + contentLength);
            //创建文件夹 MyDownLoad，在存储卡下
            String dirName = Environment.getExternalStorageDirectory() + "/apk/";
            File file = new File(dirName);
            //不存在创建
            if (!file.exists()) {
                file.mkdir();
            }
            //下载后的文件名
            String fileName = dirName + "GeneralizationDemo.apk";
            File file1 = new File(fileName);
            if (file1.exists()) {
                file1.delete();
            }
            //创建字节流
            byte[] bs = new byte[1024];
            int len;
            OutputStream os = new FileOutputStream(fileName);
            Log.v("xinghaomeibei", "keyidaozheliya");
            //写数据
            while ((len = is.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
            //完成后关闭流
            Log.e(TAG, "download-finish");
            os.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void openFile(File file){  
        
        Intent intent = new Intent();  
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
        //设置intent的Action属性  
        intent.setAction(Intent.ACTION_VIEW);  
        //获取文件file的MIME类型  
//        String type = getMIMEType(file);  
        //设置intent的data和Type属性。  
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive"); 
        //跳转  
        context.startActivity(intent);    
          
    }  

}