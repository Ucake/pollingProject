package com.sensorinfor.main;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class MyListView extends ListView {
	
	public MyListView(Context context){
		 this(context, null);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
//	private float lastY;

//    @TargetApi(Build.VERSION_CODES.KITKAT)
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            getParent().getParent().requestDisallowInterceptTouchEvent(true);
//        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//            if (lastY > ev.getY()) {
//                // 如果是向上滑动，且不能滑动了，则让ScrollView处理
//                if (!canScrollList(1)) {
//                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
//                    return false;
//                }
//            } else if (ev.getY() > lastY) {
//                // 如果是向下滑动，且不能滑动了，则让ScrollView处理
//                if (!canScrollList(-1)) {
//                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
//                    return false;
//                }
//            }
//        }
//        lastY = ev.getY();
//        return super.dispatchTouchEvent(ev);
//    }
	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

	@Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
