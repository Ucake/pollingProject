package com.example.generalizationdemo;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.model.LatLng;
import com.cement.SQLite.EventDBHelper;
import com.cement.SQLite.FriendDBHelper;
import com.example.generalizationdemo.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MapActivity extends Activity implements BDLocationListener {
	
	private BaiduMap mBaiduMap;
    private MapView mMapView;
    public LocationClient mLocationClient = null;
    private boolean isFirstLoc = true;
    public static double longitude, latitude;
    private LocationMode mCurrentMode;
    private BitmapDescriptor mCurrentMarker;
    
    private Button friends;
    private Button events;
    
    Marker marker1;
    Marker marker2;
    Marker marker3;
    Marker marker4;
    Marker marker_friend;
    Marker marker_event;
    private SharedPreferences preferences;
    
    private Cursor cursor_friend1;
    private Cursor cursor_friend2;
    private Cursor cursor_event3;
    private Cursor cursor_event4;
    private Cursor cursor;
    
    private String TEL;
    
    public static EventDBHelper eventdbhelper;
    public static FriendDBHelper frienddbhelper;
    
    private boolean isShowFre = true;
    private boolean isShowEvent = true;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //��ʹ��SDK�����֮ǰ��ʼ��context��Ϣ������ApplicationContext
        //ע��÷���Ҫ��setContentView����֮ǰʵ��
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map);
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        
        eventdbhelper = new EventDBHelper(this, "event4", 1);
        frienddbhelper = new FriendDBHelper(this, "friend4", 1);
        
        mMapView = (MapView) findViewById(R.id.bmapView);
        mLocationClient = new LocationClient(getApplicationContext()); //����LocationClient��
        mLocationClient.registerLocationListener(this);//ע���������
        initLocation();
        // ������λͼ��
        mBaiduMap = mMapView.getMap();
        
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mCurrentMode = LocationMode.FOLLOWING;
		MyLocationConfiguration config = new MyLocationConfiguration(
				mCurrentMode, true, mCurrentMarker);
		mBaiduMap.setMyLocationConfigeration(config);
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory
				.newMapStatus(new MapStatus.Builder().zoom(14).build()));
        
        
        mBaiduMap.setMyLocationEnabled(true);//��ʾ��λ�㲢�ҿ��Դ�����λ,Ĭ����flase
        mLocationClient.start();//������λ
        
        /************************���marker1********************************
        LatLng point1 = new LatLng(39.963175, 116.400244);  
        // ����markͼ��  
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory.fromResource(R.drawable.male_man);  
        // ����markeroption,�����ڵ�ͼ�����marker  
        final OverlayOptions options1 = new MarkerOptions().icon(bitmap1).title("��С��\n����A11230").position(point1);  
      
//       marker1 =  (Marker)mBaiduMap.addOverlay(options1);
       // ****************************************************************/
       
       /************************���marker2*********************************
       LatLng point2 = new LatLng(40.003600, 116.326970);  
       // ����markͼ��  
       BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.male_man);  
       // ����markeroption,�����ڵ�ͼ�����marker  
       final OverlayOptions options2 = new MarkerOptions().icon(bitmap2).title("��С��\n��������").position(point2);  
     
//      marker2 = (Marker) mBaiduMap.addOverlay(options2); 
      // ****************************************************************/ 
      
      /************************���marker3*********************************
      LatLng point3 = new LatLng(39.916623, 116.319866);  
      // ����markͼ��  
      BitmapDescriptor bitmap3 = BitmapDescriptorFactory.fromResource(R.drawable.mail_mark_task_24px);  
      // ����markeroption,�����ڵ�ͼ�����marker  
      final OverlayOptions options3 = new MarkerOptions().icon(bitmap3).title("������\n״̬�����쳣").position(point3);  
    
//     marker3 = (Marker) mBaiduMap.addOverlay(options3); 
      // ****************************************************************/
     
     /************************���marker4*********************************
     LatLng point4 = new LatLng(39.928944, 116.469719);  
     // ����markͼ��  
     BitmapDescriptor bitmap4 = BitmapDescriptorFactory.fromResource(R.drawable.mail_mark_task_24px);  
     // ����markeroption,�����ڵ�ͼ�����marker  
     final OverlayOptions options4 = new MarkerOptions().icon(bitmap4).title("����\n״̬����ά��").position(point4);  
   
//     marker4 = (Marker) mBaiduMap.addOverlay(options4);
     // ****************************************************************/
        
        friends = (Button) findViewById(R.id.friends);
//        events = (Button) findViewById(R.id.events);
        friends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if( friends.getText().equals("����ͬ��λ��")){
//					marker2.remove();  
//		            marker1.remove();
					mBaiduMap.clear();
		            cursor_friend1 = frienddbhelper.getReadableDatabase().rawQuery("select * from mapfriend where state=?", new String[]{"00"});
		            while (cursor_friend1.moveToNext()) {
		            	double latitude = cursor_friend1.getDouble(cursor_friend1.getColumnIndex("friend_latitude"));
		            	double longitude = cursor_friend1.getDouble(cursor_friend1.getColumnIndex("friend_longitude"));
		            	String friend_name = cursor_friend1.getString(cursor_friend1.getColumnIndex("friend_name"));
		            	String friend_phone = cursor_friend1.getString(cursor_friend1.getColumnIndex("friend_phone"));
		            	
		            	Log.v("NDYJBPLNDXXXXXXXXXXX", friend_name+friend_phone);
		            	
		            	LatLng point = new LatLng(latitude, longitude);  
			            // ����markͼ��  
			            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.male_man);  
			            // ����markeroption,�����ڵ�ͼ�����marker  
			            final OverlayOptions option = new MarkerOptions().icon(bitmap).title("ͬ��λ��").position(point);
			            marker1 = (Marker) mBaiduMap.addOverlay(option);
			            marker1.remove();
		            }
					
					friends.setText("�鿴ͬ��λ��");
				}else {
					
		            
//		            marker2 = (Marker) mBaiduMap.addOverlay(options2);
//		            marker1= (Marker) mBaiduMap.addOverlay(options1);
					
					cursor_friend2 = frienddbhelper.getReadableDatabase().rawQuery("select * from mapfriend where state=?", new String[]{"00"});
		            while (cursor_friend2.moveToNext()) {
		            	double latitude = cursor_friend2.getDouble(cursor_friend2.getColumnIndex("friend_latitude"));
		            	double longitude = cursor_friend2.getDouble(cursor_friend2.getColumnIndex("friend_longitude"));
		            	String friend_name = cursor_friend2.getString(cursor_friend2.getColumnIndex("friend_name"));
		            	String friend_phone = cursor_friend2.getString(cursor_friend2.getColumnIndex("friend_phone"));
		            	
		            	LatLng point = new LatLng(latitude, longitude);  
			            // ����markͼ��  
			            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.male_man);  
			            // ����markeroption,�����ڵ�ͼ�����marker  
			            final OverlayOptions options = new MarkerOptions().icon(bitmap).title("ͬ��λ��").position(point);
			            marker2 = (Marker) mBaiduMap.addOverlay(options);
			            
		            }
					
		            friends.setText("����ͬ��λ��");
				}
			}
		});
        
//        events.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(events.getText().equals("�����¼�λ��")){
////					marker4.remove();
////					marker3.remove();
//					mBaiduMap.clear();
//					cursor_event3 = eventdbhelper.getReadableDatabase().rawQuery("select * from mapevent where state=?", new String[]{"00"});
//		            while (cursor_event3.moveToNext()) {
//		            	double latitude = cursor_event3.getDouble(cursor_event3.getColumnIndex("event_latitude"));
//		            	double longitude = cursor_event3.getDouble(cursor_event3.getColumnIndex("event_longitude"));
//		            	String event_name = cursor_event3.getString(cursor_event3.getColumnIndex("event_name"));
//		            	String event_id = cursor_event3.getString(cursor_event3.getColumnIndex("event_id"));
//		            	
//		            	LatLng point = new LatLng(latitude, longitude);  
//			            // ����markͼ��  
//			            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mail_mark_task_24px);  
//			            // ����markeroption,�����ڵ�ͼ�����marker  
//			            final OverlayOptions options = new MarkerOptions().icon(bitmap).title("�¼�λ��").position(point);
//			            marker_event = (Marker) mBaiduMap.addOverlay(options);
//			            marker_event.remove();
//		            }
//					events.setText("�鿴�¼�λ��");
//				}else{
////					marker3 = (Marker) mBaiduMap.addOverlay(options3);
////					marker4= (Marker) mBaiduMap.addOverlay(options4);
//					
//					cursor_event4 = eventdbhelper.getReadableDatabase().rawQuery("select * from mapevent where state=?", new String[]{"00"});
//		            while (cursor_event4.moveToNext()) {
//		            	double latitude = cursor_event4.getDouble(cursor_event4.getColumnIndex("event_latitude"));
//		            	double longitude = cursor_event4.getDouble(cursor_event4.getColumnIndex("event_longitude"));
//		            	String event_name = cursor_event4.getString(cursor_event4.getColumnIndex("event_name"));
//		            	String event_id = cursor_event4.getString(cursor_event4.getColumnIndex("event_id"));
//		            	
//		            	Log.v("YDDDDDDDDDDDDDD", event_id+event_name);
//		            	
//		            	LatLng point = new LatLng(latitude, longitude);  
//			            // ����markͼ��  
//			            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mail_mark_task_24px);  
//			            // ����markeroption,�����ڵ�ͼ�����marker  
//			            final OverlayOptions options = new MarkerOptions().icon(bitmap).title("�¼�λ��").position(point);
//			            marker_event = (Marker) mBaiduMap.addOverlay(options);
//		            }
//					
//					events.setText("�����¼�λ��");
//				}
//			}
//		});
        
        	mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
        				@Override
        			public boolean onMarkerClick(Marker marker) {
                //��marker�л�ȡinfo��Ϣ
        					Log.v("YTTTTTTTTTTTTTT", marker.getTitle());
        				
        				
        				String marker_info =  marker.getPosition().toString();
        				int length = marker_info.length();
        	         	String str = marker_info.substring(marker_info.length()-10);
        	         	Log.v("WHZNNNNNNNNNNNNNN", str);
        	         	Log.v("YSHQQQQQQQQQQQQQQ", marker.getPosition().toString());
        	         	Log.v("DELICHANG", String.valueOf(marker_info.length()));
        	         	int count = 0;
        	         	for(; marker_info.charAt(length-1) != '.';length--){
        	         		count++;
        	         	}
        	         	Log.v("KUDAOBAOA", String.valueOf(count));
        	         	str = marker_info.substring(marker_info.length()-count-4);
        	         	Log.v("ZHENDEZECHENG", str);
        	         	for(int i = count;i < 6;i++){
        	         		str = str + "0";
        	         	}
        	         	Log.v("TAIWEIXIANLE", str);
        	         	
        	         	if(marker.getTitle().equals("ͬ��λ��")){
        	         		View linlayout1 = MapActivity.this.getLayoutInflater().inflate(R.layout.infowindow_layout, null);
        	         		cursor = frienddbhelper.getReadableDatabase().rawQuery("select * from mapfriend where friend_longitude=?", new String[]{str});
        	         		while (cursor.moveToNext()) {
        	               	  String friend_name = cursor.getString(cursor.getColumnIndex("friend_name"));
        	               	  String friend_phone = cursor.getString(cursor.getColumnIndex("friend_phone"));
        	         		TextView name = (TextView) linlayout1.findViewById(R.id.name);
        	         		TextView info = (TextView) linlayout1.findViewById(R.id.info);
        	         		name.setText("������"+friend_name);
        	         		info.setText("�绰��"+friend_phone);
        	         		
//        	         		Button trace = (Button) linlayout1.findViewById(R.id.trace);
//            	         	trace.setOnClickListener(new OnClickListener() {
//    							
//    							@Override
//    							public void onClick(View v) {
//    								// TODO Auto-generated method stub
//    								LinearLayout loginDialog = (LinearLayout)LayoutInflater.from(MapActivity.this).inflate(R.layout.alertdialog_layout, null);
//    								new AlertDialog.Builder(MapActivity.this)
//    						        .setTitle("Ѳ��켣")
//    						        .setMessage("")
//    						        .setView(loginDialog)
//    						        .setPositiveButton("�ر�", new DialogInterface.OnClickListener() {
//    						            @Override
//    						            public void onClick(DialogInterface dialog, int which) {
//    						            	
//    						            }
//    						        
//    						        })
//    						       
//    						        .show();
//    								
//    							}
//    						});
        	         		
        	         		}
        	         		
        	         		final LatLng ll = marker.getPosition();
             				InfoWindow mInfoWindow=new InfoWindow(linlayout1, ll, -100);
             				if(isShowFre){
             					
             					mBaiduMap.showInfoWindow(mInfoWindow);
             					isShowFre = false;
             				}else{
             					mBaiduMap.hideInfoWindow();
             					isShowFre = true;
             					}
             				
        	         		
        	         	}else if(marker.getTitle().equals("�¼�λ��")){
        	         		View linlayout2 = MapActivity.this.getLayoutInflater().inflate(R.layout.infowindow_layout2, null);
        	         		
        	         		cursor = eventdbhelper.getReadableDatabase().rawQuery("select * from mapevent where event_longitude=?", new String[]{str});
        	         		while (cursor.moveToNext()) {
        	               	  String event_id = cursor.getString(cursor.getColumnIndex("event_id"));
        	               	  String event_name = cursor.getString(cursor.getColumnIndex("event_name"));
        	         		TextView name = (TextView) linlayout2.findViewById(R.id.event_name);
        	         		TextView info = (TextView) linlayout2.findViewById(R.id.event_info);
        	         		name.setText("�¼�id��"+event_id);
        	         		info.setText("�¼����ƣ�"+event_name);
        	         		
        	         		final LatLng ll = marker.getPosition();
             				InfoWindow mInfoWindow=new InfoWindow(linlayout2, ll, -100);
             				if(isShowEvent){
             					
             					mBaiduMap.showInfoWindow(mInfoWindow);
             					isShowEvent = false;
             				}else{
             					mBaiduMap.hideInfoWindow();
             					isShowEvent = true;
             					}
             				
        	         		
        	         	}
        	         	}
        	         	
        	         	
        	         	
        				
        					return false;
            	 
            }
    });
	}
	
	private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
        option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
        int span = 1000;
        option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
        option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
        option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
        option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
        option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
        option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
        option.setIgnoreKillProcess(false);//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��
        option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
        option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
        mMapView.onDestroy();
        mLocationClient.stop();//�رն�λ
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
        mMapView.onPause();
    }

    @Override
    public void onReceiveLocation(BDLocation location) {

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        // ���ö�λ����
        mBaiduMap.setMyLocationData(locData);
        
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        
//        if (isFirstLoc) {
//            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//            MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(ll, 16);//���õ�ͼ���ļ����ż���
//            mBaiduMap.animateMapStatus(update);
            isFirstLoc = false;
//            Toast.makeText(getApplicationContext(), location.getAddrStr(), Toast.LENGTH_SHORT).show();
//        }
        /*
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("\nerror code : ");
        sb.append(location.getLocType());
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());
        sb.append("\nradius : ");
        sb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS��λ���
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// ��λ������ÿСʱ
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// ��λ����
            sb.append("\ndirection : ");
            sb.append(location.getDirection());// ��λ��
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\ndescribe : ");
            sb.append("gps��λ�ɹ�");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ���綨λ���
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            //��Ӫ����Ϣ
            sb.append("\noperationers : ");
            sb.append(location.getOperators());
            sb.append("\ndescribe : ");
            sb.append("���綨λ�ɹ�");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
            sb.append("\ndescribe : ");
            sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
            sb.append("\ndescribe : ");
            sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ�");
        }
        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());// λ�����廯��Ϣ
        List<Poi> list = location.getPoiList();// POI����
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }
        Log.i("BaiduLocationApiDem", sb.toString());*/

    }
	
}
