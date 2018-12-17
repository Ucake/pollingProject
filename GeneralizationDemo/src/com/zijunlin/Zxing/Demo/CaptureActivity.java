package com.zijunlin.Zxing.Demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.locks.Lock;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.Toast;


import com.example.generalizationdemo.ExecuteActivity;
import com.example.generalizationdemo.HomePageya;
import com.example.generalizationdemo.R;
import com.example.generalizationdemo.ReceiveActivity;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zijunlin.Zxing.Demo.camera.CameraManager;
import com.zijunlin.Zxing.Demo.decoding.CaptureActivityHandler;
import com.zijunlin.Zxing.Demo.decoding.InactivityTimer;
import com.zijunlin.Zxing.Demo.view.ViewfinderView;

public class CaptureActivity extends Activity implements Callback
{

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private Handler handlerF = new Handler();
	
	private Handler handler_;
	
	
	public static String res3;
	private JSONObject result;
//	private Integer taskID;
//	private String[] ID;
	private String taskID;
	private ArrayList<String> ID_Array = new ArrayList<String>();
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zxingmain);
		//接收ID数组
		Intent intent = getIntent();
//		ID = intent.getStringArrayExtra("ID");
		ID_Array = intent.getStringArrayListExtra("ID_Array");
		// 初始化 CameraManager
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		
		
		
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface)
		{
			initCamera(surfaceHolder);
		}
		else
		{
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
		{
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		if (handler != null)
		{
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy()
	{
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder)
	{
		try
		{
			CameraManager.get().openDriver(surfaceHolder);
		}
		catch (IOException ioe)
		{
			return;
		}
		catch (RuntimeException e)
		{
			return;
		}
		if (handler == null)
		{
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		if (!hasSurface)
		{
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView()
	{
		return viewfinderView;
	}

	public Handler getHandler()
	{
		return handler;
	}

	public void drawViewfinder()
	{
		viewfinderView.drawViewfinder();

	}

	public void handleDecode(final Result obj, Bitmap barcode)
	{
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		if (barcode == null)
		{
			dialog.setIcon(null);
		}
		else
		{
			
			res3 = obj.getText().toString();
			Log.e("wwwww", res3);
			String[] str = res3.split(":");
			for (int i = 0; i < str.length; i++) {
				Log.e("第"+i+"数组是", str[i]);
				if(i == 1){
					String str_str = str[i].trim();
					taskID = str_str.substring(0, 2);
					
					
							
							Log.e("事件ID是", ""+taskID)	;
						
					
				}
			}
//			try {
//				result = new JSONObject(res3);
//				Log.e("result", ""+result);
//				taskID = result.getInt("taskID");
//				taskID = result.getInt("事件ID");
				if(ID_Array.contains(taskID)){
					Intent intent = new Intent(CaptureActivity.this, ExecuteActivity.class);
					intent.putExtra("taskID", taskID);
					startActivity(intent);
				}
//				for(int i =0;i<ID.length;i++){
//					if(ID[i].equals(taskID)){
//						Intent intent = new Intent(CaptureActivity.this, ExecuteActivity.class);
//						intent.putExtra("taskID", taskID);
//						startActivity(intent);
//						
//					}
//				}
				new AlertDialog.Builder(this)
				.setTitle("该事件不属于此任务")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 点击确定后的操作
								Intent intent = new Intent(CaptureActivity.this, ReceiveActivity.class);
//								intent.putExtra("taskID", taskID);
								startActivity(intent);
							}
						}).show();
//			} catch (JSONException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			
//			handler_ = new Handler(){
//				
//				public void handleMessage(Message msg){
//					super.handleMessage(msg);
//					if (msg.what == 12138) {
//
//					String	backMsg = msg.obj.toString();
//					Log.v("Lockkkkkkkkkkkkkkkkkk", backMsg);
//					
//						try{
//							JSONObject toor = new JSONObject(backMsg);
//					           
//							JSONObject result_1 = new JSONObject(toor.getString("result"));
//							Log.v("SSSSSSSSSSSSSS", result_1.toString());
//						String	result1 = result_1.getString("result");
//							Log.e("SSSSSSSSSSSSSS", result1);
//				            if(result1.equals("10000")){
////				            	SweepJson swwpjson = new SweepJson();
////				            	swwpjson.sweeplist(backMsg);
////				            	Toast showToast=Toast.makeText(CaptureActivity.this, "验证成功！", Toast.LENGTH_SHORT);
////				            	showToast.show();
////				            	
////				            	Intent intent = new Intent(CaptureActivity.this,LockTestActivity.class);
////				    			startActivity(intent);
//				            	
//				            }else if(result1.equals("10001")){
//				            	Toast showToast=Toast.makeText(CaptureActivity.this, "操作有误！", Toast.LENGTH_SHORT);
//				            	Intent intent = new Intent(CaptureActivity.this,HomePage.class);
//				    			startActivity(intent);
//				            	showToast.show();
////				            	Intent intentm = new Intent(CaptureActivity.this,HomePage.class);
////				    			startActivity(intentm);
//				            }
//				         	
//						}catch (JSONException e) {
//				            e.printStackTrace();
//				        }
//						
//			            }
//						
//					}	
//				
//			};
//			
//			String value = "phone="+MainActivity.TEL+"&code="+res3;
//			Log.v("Lockkkkkkkkkkkkkkkkkk", value);
//			
//			SweepOneSweepThread thread1 = new SweepOneSweepThread(handler_, value);
//			new Thread(thread1).start();
			
//			Intent intentm = new Intent(CaptureActivity.this,ZXingMainActivity.class);
//			startActivity(intentm);
		
		}
		
		
		
//		Intent intentm = new Intent(CaptureActivity.this,MainActivity.class);
//		startActivity(intentm);
//		
//		dialog.setTitle("扫描结果");
//		dialog.setMessage(obj.getText());
//		dialog.setNegativeButton("确定", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				//用默认浏览器打开扫描得到的地址
//				Intent intent = new Intent();
//				intent.setAction("android.intent.action.VIEW");
//				Uri content_url = Uri.parse(obj.getText());
//				intent.setData(content_url);
//				startActivity(intent);
//				finish();
//			}
//		});
//		dialog.setPositiveButton("取消", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				finish();
//			}
//		});
//		dialog.create().show();
//		 handlerF = new Handler(){
//				
//				public void handleMessage(Message msg){
//					super.handleMessage(msg);
//					if (msg.what == 12138) {
//						
//						String backMsg = msg.obj.toString();	
//						
//						Log.e("qqqqq", backMsg);
//						if(!backMsg.equals(null)){
//							
//							ReadytoinstallJson readytointall = new ReadytoinstallJson();
//							readytointall.readytoinstall(backMsg);
////							System.out.println(backMsg);
//							if(readytointall.getResulta().equals("10000")){
////								System.out.println("%%%%%%%%%%%%%%%%%%%%%");
////								AlertDialog.Builder dialog = new AlertDialog.Builder(CaptureActivity.this);
//								dialog.setTitle("成功");
//								Intent intentm = new Intent(CaptureActivity.this,MainActivity.class);
//								startActivity(intentm);
//								System.out.println("########################################");
////								Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_LONG).show();
//							}
//							
//						}
//
//						
//					}
//				}
//
//			};
//			
//
//				String value = "phone="+ res3;
////			String value = "worker="+ "张三" + "&address=" + "朝阳区";
//				
//				LoginThread thread = new LoginThread(handlerF, value);
//			
//				new Thread(thread).start();
	}

	private void initBeepSound()
	{
		if (playBeep && mediaPlayer == null)
		{
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try
			{
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			}
			catch (IOException e)
			{
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate()
	{
		if (playBeep && mediaPlayer != null)
		{
			mediaPlayer.start();
		}
		if (vibrate)
		{
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener()
	{
		public void onCompletion(MediaPlayer mediaPlayer)
		{
			mediaPlayer.seekTo(0);
		}
	};
	
	/**
	 * 截取数字
	 * @param s 二维码中的字符
	 * @return
	 */
//	public String character (String s){
//				
//		String res1 = s.trim();
//		String res2 = null;
//		if(res1 != null){
//			for(int i = 0; i < res1.length();i++){
//				if(res1.charAt(i) >= 48 && res1.charAt(i) <= 57)
//					res2 += res1.charAt(i);
//			}
//		}
//		Log.e("rrrrr", res2);
//		return res2;
//		
//		
//	}

}