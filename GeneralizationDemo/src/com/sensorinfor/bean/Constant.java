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
		
		
		mHashMap_name.put("uA", "A���ѹ");
	    mHashMap_name.put("uB", "B���ѹ");
	    mHashMap_name.put("uC", "C���ѹ");
	    mHashMap_name.put("dxa", "A������״̬");
	    mHashMap_name.put("dxb", "B������״̬");
	    mHashMap_name.put("dxc", "C������״̬");
	    mHashMap_name.put("freq", "Ƶ��");
	    mHashMap_name.put("iA", "A�����");
	    mHashMap_name.put("iB", "B�����");
	    mHashMap_name.put("iC", "C�����");
	    mHashMap_name.put("pt", "���й�����");
	    mHashMap_name.put("qt", "���޹�����");
	    mHashMap_name.put("st", "���ӹ�����");
	    mHashMap_name.put("wpt", "���й�����");
	    mHashMap_name.put("wqt", "���޹�����");
	    mHashMap_name.put("X", "X����ٶ�");
	    mHashMap_name.put("Y", "Y����ٶ�");
	    mHashMap_name.put("Z", "Z����ٶ�");
	}
	public Map<String, String> getmHashMap_unit() {
		return mHashMap_unit;
	}
	public void setmHashMap_unit() {
		
		 	mHashMap_unit.put("uA", "A���ѹ/v");
		    mHashMap_unit.put("uB", "B���ѹ/v");
		    mHashMap_unit.put("uC", "C���ѹ/v");
		    mHashMap_unit.put("dxa", "A������״̬/1:����;0:δ����");
		    mHashMap_unit.put("dxb", "B������״̬/1:����;0:δ����");
		    mHashMap_unit.put("dxc", "C������״̬/1:����;0:δ����");
		    mHashMap_unit.put("freq", "Ƶ��/hz");
		    mHashMap_unit.put("iA", "A�����/A");
		    mHashMap_unit.put("iB", "B�����/A");
		    mHashMap_unit.put("iC", "C�����/A");
		    mHashMap_unit.put("pt", "���й�����/KW");
		    mHashMap_unit.put("qt", "���޹�����/KVar");
		    mHashMap_unit.put("st", "���ӹ�����/KVA");
		    mHashMap_unit.put("wpt", "���й�����/KVh");
		    mHashMap_unit.put("wqt", "���޹�����/KVarh");
		    mHashMap_unit.put("X", "X����ٶ�/m/s2");
		    mHashMap_unit.put("Y", "���޹�����/m/s2");
		    mHashMap_unit.put("Z", "���޹�����/m/s2");
		
	}
	public Map<String, String> getmHashMap() {
		return mHashMap;
	}
	public void setmHashMap() {
		
		mHashMap.put("�¶�", "tem");
		mHashMap.put("��ѹ", "pre");
		mHashMap.put("��ѹ", "oil");
		mHashMap.put("���ٶ�", "acc");
		mHashMap.put("�����", "three");
	}
	public Map<String, String> getmHaMap_name_history() {
		return mHaMap_name_history;
	}
	public void setmHaMap_name_history() {
		mHaMap_name_history.put("A���ѹ","ua");
		mHaMap_name_history.put("B���ѹ","ub");
		mHaMap_name_history.put("C���ѹ","uc");
		mHaMap_name_history.put( "A������״̬","dxa");
		mHaMap_name_history.put("B������״̬","dxb");
		mHaMap_name_history.put( "C������״̬","dxc");
		mHaMap_name_history.put("Ƶ��","freq");
		mHaMap_name_history.put( "A�����","ia");
		mHaMap_name_history.put( "B�����","ib");
		mHaMap_name_history.put( "C�����","ic");
		mHaMap_name_history.put( "���й�����","pt");
		mHaMap_name_history.put( "���޹�����","qt");
		mHaMap_name_history.put( "���ӹ�����","st");
		mHaMap_name_history.put( "���й�����","wpt");
		mHaMap_name_history.put( "���޹�����","wqt");
		mHaMap_name_history.put( "X����ٶ�","ax");
		mHaMap_name_history.put( "Y����ٶ�","ay");
		mHaMap_name_history.put( "Z����ٶ�","az");
	}
	public Map<String, String> getmHaMap_name_unit() {
		return mHaMap_name_unit;
	}
	public void setmHaMap_name_unit() {
		mHaMap_name_unit.put("uA", "V");
		mHaMap_name_unit.put("uB", "V");
		mHaMap_name_unit.put("uC", "V");
		mHaMap_name_unit.put("dxa", "1:����;0:δ����");
		mHaMap_name_unit.put("dxb", "1:����;0:δ����");
		mHaMap_name_unit.put("dxc", "1:����;0:δ����");
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
