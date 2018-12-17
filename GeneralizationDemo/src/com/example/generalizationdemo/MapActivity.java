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
      //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map);
        preferences = getSharedPreferences("user", MODE_PRIVATE);
        
        eventdbhelper = new EventDBHelper(this, "event4", 1);
        frienddbhelper = new FriendDBHelper(this, "friend4", 1);
        
        mMapView = (MapView) findViewById(R.id.bmapView);
        mLocationClient = new LocationClient(getApplicationContext()); //声明LocationClient类
        mLocationClient.registerLocationListener(this);//注册监听函数
        initLocation();
        // 开启定位图层
        mBaiduMap = mMapView.getMap();
        
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		mCurrentMode = LocationMode.FOLLOWING;
		MyLocationConfiguration config = new MyLocationConfiguration(
				mCurrentMode, true, mCurrentMarker);
		mBaiduMap.setMyLocationConfigeration(config);
		mBaiduMap.setMyLocationEnabled(true);
		mBaiduMap.setMapStatus(MapStatusUpdateFactory
				.newMapStatus(new MapStatus.Builder().zoom(14).build()));
        
        
        mBaiduMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        mLocationClient.start();//开启定位
        
        /************************添加marker1********************************
        LatLng point1 = new LatLng(39.963175, 116.400244);  
        // 构建mark图标  
        BitmapDescriptor bitmap1 = BitmapDescriptorFactory.fromResource(R.drawable.male_man);  
        // 构建markeroption,用于在地图上添加marker  
        final OverlayOptions options1 = new MarkerOptions().icon(bitmap1).title("王小量\n任务A11230").position(point1);  
      
//       marker1 =  (Marker)mBaiduMap.addOverlay(options1);
       // ****************************************************************/
       
       /************************添加marker2*********************************
       LatLng point2 = new LatLng(40.003600, 116.326970);  
       // 构建mark图标  
       BitmapDescriptor bitmap2 = BitmapDescriptorFactory.fromResource(R.drawable.male_man);  
       // 构建markeroption,用于在地图上添加marker  
       final OverlayOptions options2 = new MarkerOptions().icon(bitmap2).title("李小男\n暂无任务").position(point2);  
     
//      marker2 = (Marker) mBaiduMap.addOverlay(options2); 
      // ****************************************************************/ 
      
      /************************添加marker3*********************************
      LatLng point3 = new LatLng(39.916623, 116.319866);  
      // 构建mark图标  
      BitmapDescriptor bitmap3 = BitmapDescriptorFactory.fromResource(R.drawable.mail_mark_task_24px);  
      // 构建markeroption,用于在地图上添加marker  
      final OverlayOptions options3 = new MarkerOptions().icon(bitmap3).title("除氧器\n状态：无异常").position(point3);  
    
//     marker3 = (Marker) mBaiduMap.addOverlay(options3); 
      // ****************************************************************/
     
     /************************添加marker4*********************************
     LatLng point4 = new LatLng(39.928944, 116.469719);  
     // 构建mark图标  
     BitmapDescriptor bitmap4 = BitmapDescriptorFactory.fromResource(R.drawable.mail_mark_task_24px);  
     // 构建markeroption,用于在地图上添加marker  
     final OverlayOptions options4 = new MarkerOptions().icon(bitmap4).title("汽缸\n状态：待维修").position(point4);  
   
//     marker4 = (Marker) mBaiduMap.addOverlay(options4);
     // ****************************************************************/
        
        friends = (Button) findViewById(R.id.friends);
//        events = (Button) findViewById(R.id.events);
        friends.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if( friends.getText().equals("隐藏同伴位置")){
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
			            // 构建mark图标  
			            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.male_man);  
			            // 构建markeroption,用于在地图上添加marker  
			            final OverlayOptions option = new MarkerOptions().icon(bitmap).title("同伴位置").position(point);
			            marker1 = (Marker) mBaiduMap.addOverlay(option);
			            marker1.remove();
		            }
					
					friends.setText("查看同伴位置");
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
			            // 构建mark图标  
			            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.male_man);  
			            // 构建markeroption,用于在地图上添加marker  
			            final OverlayOptions options = new MarkerOptions().icon(bitmap).title("同伴位置").position(point);
			            marker2 = (Marker) mBaiduMap.addOverlay(options);
			            
		            }
					
		            friends.setText("隐藏同伴位置");
				}
			}
		});
        
//        events.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				if(events.getText().equals("隐藏事件位置")){
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
//			            // 构建mark图标  
//			            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mail_mark_task_24px);  
//			            // 构建markeroption,用于在地图上添加marker  
//			            final OverlayOptions options = new MarkerOptions().icon(bitmap).title("事件位置").position(point);
//			            marker_event = (Marker) mBaiduMap.addOverlay(options);
//			            marker_event.remove();
//		            }
//					events.setText("查看事件位置");
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
//			            // 构建mark图标  
//			            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mail_mark_task_24px);  
//			            // 构建markeroption,用于在地图上添加marker  
//			            final OverlayOptions options = new MarkerOptions().icon(bitmap).title("事件位置").position(point);
//			            marker_event = (Marker) mBaiduMap.addOverlay(options);
//		            }
//					
//					events.setText("隐藏事件位置");
//				}
//			}
//		});
        
        	mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
        				@Override
        			public boolean onMarkerClick(Marker marker) {
                //从marker中获取info信息
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
        	         	
        	         	if(marker.getTitle().equals("同伴位置")){
        	         		View linlayout1 = MapActivity.this.getLayoutInflater().inflate(R.layout.infowindow_layout, null);
        	         		cursor = frienddbhelper.getReadableDatabase().rawQuery("select * from mapfriend where friend_longitude=?", new String[]{str});
        	         		while (cursor.moveToNext()) {
        	               	  String friend_name = cursor.getString(cursor.getColumnIndex("friend_name"));
        	               	  String friend_phone = cursor.getString(cursor.getColumnIndex("friend_phone"));
        	         		TextView name = (TextView) linlayout1.findViewById(R.id.name);
        	         		TextView info = (TextView) linlayout1.findViewById(R.id.info);
        	         		name.setText("姓名："+friend_name);
        	         		info.setText("电话："+friend_phone);
        	         		
//        	         		Button trace = (Button) linlayout1.findViewById(R.id.trace);
//            	         	trace.setOnClickListener(new OnClickListener() {
//    							
//    							@Override
//    							public void onClick(View v) {
//    								// TODO Auto-generated method stub
//    								LinearLayout loginDialog = (LinearLayout)LayoutInflater.from(MapActivity.this).inflate(R.layout.alertdialog_layout, null);
//    								new AlertDialog.Builder(MapActivity.this)
//    						        .setTitle("巡检轨迹")
//    						        .setMessage("")
//    						        .setView(loginDialog)
//    						        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
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
             				
        	         		
        	         	}else if(marker.getTitle().equals("事件位置")){
        	         		View linlayout2 = MapActivity.this.getLayoutInflater().inflate(R.layout.infowindow_layout2, null);
        	         		
        	         		cursor = eventdbhelper.getReadableDatabase().rawQuery("select * from mapevent where event_longitude=?", new String[]{str});
        	         		while (cursor.moveToNext()) {
        	               	  String event_id = cursor.getString(cursor.getColumnIndex("event_id"));
        	               	  String event_name = cursor.getString(cursor.getColumnIndex("event_name"));
        	         		TextView name = (TextView) linlayout2.findViewById(R.id.event_name);
        	         		TextView info = (TextView) linlayout2.findViewById(R.id.event_info);
        	         		name.setText("事件id："+event_id);
        	         		info.setText("事件名称："+event_name);
        	         		
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
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.stop();//关闭定位
        mBaiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onReceiveLocation(BDLocation location) {

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(location.getRadius())
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);
        
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        
//        if (isFirstLoc) {
//            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//            MapStatusUpdate update = MapStatusUpdateFactory.newLatLngZoom(ll, 16);//设置地图中心及缩放级别
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
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());// 单位：公里每小时
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\nheight : ");
            sb.append(location.getAltitude());// 单位：米
            sb.append("\ndirection : ");
            sb.append(location.getDirection());// 单位度
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append("\ndescribe : ");
            sb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            //运营商信息
            sb.append("\noperationers : ");
            sb.append(location.getOperators());
            sb.append("\ndescribe : ");
            sb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());// 位置语义化信息
        List<Poi> list = location.getPoiList();// POI数据
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
