package com.sensorinfor.bean;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	private  Map<String,String> mHashMap_name = new HashMap<String, String>();
	private  Map<String,String> mHashMap_unit = new HashMap<String, String>();
	private  Map<String,String> mHashMap = new HashMap<String, String>();
	private  Map<String,String> mHaMap_name_history = new HashMap<String, String>();
	private  Map<String,String> mHaMap_name_unit = new HashMap<String, String>();
	
	
	
	
	public Constant() {
		setmHashMap_name();
		 setmHashMap_unit();
		 setmHashMap();
		 setmHaMap_name_history();
		 setmHaMap_name_unit();
	}
	public Map<String, String> getmHashMap_name() {
		return mHashMap_name;
	}
	public void setmHashMap_name() {
		
		
		mHashMap_name.put("uA", "A相电压");
	    mHashMap_name.put("uB", "B相电压");
	    mHashMap_name.put("uC", "C相电压");
	    mHashMap_name.put("dxa", "A相连接状态");
	    mHashMap_name.put("dxb", "B相连接状态");
	    mHashMap_name.put("dxc", "C相连接状态");
	    mHashMap_name.put("freq", "频率");
	    mHashMap_name.put("iA", "A相电流");
	    mHashMap_name.put("iB", "B相电流");
	    mHashMap_name.put("iC", "C相电流");
	    mHashMap_name.put("pt", "总有功功率");
	    mHashMap_name.put("qt", "总无功功率");
	    mHashMap_name.put("st", "总视功功率");
	    mHashMap_name.put("wpt", "总有供电能");
	    mHashMap_name.put("wqt", "总无功电能");
	    mHashMap_name.put("X", "X轴加速度");
	    mHashMap_name.put("Y", "Y轴加速度");
	    mHashMap_name.put("Z", "Z轴加速度");
	}
	public Map<String, String> getmHashMap_unit() {
		return mHashMap_unit;
	}
	public void setmHashMap_unit() {
		
		 	mHashMap_unit.put("uA", "A相电压/v");
		    mHashMap_unit.put("uB", "B相电压/v");
		    mHashMap_unit.put("uC", "C相电压/v");
		    mHashMap_unit.put("dxa", "A相连接状态/1:连接;0:未连接");
		    mHashMap_unit.put("dxb", "B相连接状态/1:连接;0:未连接");
		    mHashMap_unit.put("dxc", "C相连接状态/1:连接;0:未连接");
		    mHashMap_unit.put("freq", "频率/hz");
		    mHashMap_unit.put("iA", "A相电流/A");
		    mHashMap_unit.put("iB", "B相电流/A");
		    mHashMap_unit.put("iC", "C相电流/A");
		    mHashMap_unit.put("pt", "总有功功率/KW");
		    mHashMap_unit.put("qt", "总无功功率/KVar");
		    mHashMap_unit.put("st", "总视功功率/KVA");
		    mHashMap_unit.put("wpt", "总有供电能/KVh");
		    mHashMap_unit.put("wqt", "总无功电能/KVarh");
		    mHashMap_unit.put("X", "X轴加速度/m/s2");
		    mHashMap_unit.put("Y", "总无功电能/m/s2");
		    mHashMap_unit.put("Z", "总无功电能/m/s2");
		
	}
	public Map<String, String> getmHashMap() {
		return mHashMap;
	}
	public void setmHashMap() {
		
		mHashMap.put("温度", "tem");
		mHashMap.put("气压", "pre");
		mHashMap.put("油压", "oil");
		mHashMap.put("加速度", "acc");
		mHashMap.put("三相电", "three");
	}
	public Map<String, String> getmHaMap_name_history() {
		return mHaMap_name_history;
	}
	public void setmHaMap_name_history() {
		mHaMap_name_history.put("A相电压","ua");
		mHaMap_name_history.put("B相电压","ub");
		mHaMap_name_history.put("C相电压","uc");
		mHaMap_name_history.put( "A相连接状态","dxa");
		mHaMap_name_history.put("B相连接状态","dxb");
		mHaMap_name_history.put( "C相连接状态","dxc");
		mHaMap_name_history.put("频率","freq");
		mHaMap_name_history.put( "A相电流","ia");
		mHaMap_name_history.put( "B相电流","ib");
		mHaMap_name_history.put( "C相电流","ic");
		mHaMap_name_history.put( "总有功功率","pt");
		mHaMap_name_history.put( "总无功功率","qt");
		mHaMap_name_history.put( "总视功功率","st");
		mHaMap_name_history.put( "总有供电能","wpt");
		mHaMap_name_history.put( "总无功电能","wqt");
		mHaMap_name_history.put( "X轴加速度","ax");
		mHaMap_name_history.put( "Y轴加速度","ay");
		mHaMap_name_history.put( "Z轴加速度","az");
	}
	public Map<String, String> getmHaMap_name_unit() {
		return mHaMap_name_unit;
	}
	public void setmHaMap_name_unit() {
		mHaMap_name_unit.put("uA", "V");
		mHaMap_name_unit.put("uB", "V");
		mHaMap_name_unit.put("uC", "V");
		mHaMap_name_unit.put("dxa", "1:连接;0:未连接");
		mHaMap_name_unit.put("dxb", "1:连接;0:未连接");
		mHaMap_name_unit.put("dxc", "1:连接;0:未连接");
		mHaMap_name_unit.put("freq", "HZ");
		mHaMap_name_unit.put("iA", "A");
		mHaMap_name_unit.put("iB", "A");
		mHaMap_name_unit.put("iC", "A");
		mHaMap_name_unit.put("pt", "KW");
		mHaMap_name_unit.put("qt", "KVar");
		mHaMap_name_unit.put("st", "KVA");
		mHaMap_name_unit.put("wpt", "KVh");
		mHaMap_name_unit.put("wqt", "KVarh");
		mHaMap_name_unit.put("X", "M/S2");
		mHaMap_name_unit.put("Y", "M/S2");
		mHaMap_name_unit.put("Z", "M/S2");
	}
	
	
	
   
}
