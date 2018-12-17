package com.sensorinfor.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

	private Paint mPaint; 
	private String color;
	
	
    public MyTextView(Context context, Paint mPaint, String color) {
		super(context);
		this.mPaint = mPaint;
		this.color = color;
	}

	public MyTextView(Context context) {  
        this(context,null);  
    }  
  
    public MyTextView(Context context, AttributeSet attrs) {  
        this(context, attrs,0);  
  
    }  
    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {  
        super(context, attrs, defStyleAttr);  
        init();  
    }  
    private void init() {  
        mPaint = new Paint();  
    }  
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
        RectF rectF = new RectF();  
        //鏍规嵁鏂囧瓧鐨勯鑹叉潵缁樼敾妗�  
        mPaint.setColor(getPaint().getColor());  
//        mPaint.setColor(Color.parseColor(color));  
        //璁剧疆鐢荤瑪鐨勭敾鍑烘槸绌哄績  
        mPaint.setStyle(Paint.Style.STROKE);  
        //璁剧疆鎶楅敮榻�  
        mPaint.setAntiAlias(true);  
        //璁剧疆鐢诲緱涓�涓崐寰勶紝鐒跺悗姣旇緝闀垮拰瀹斤紝纭畾鍗婂緞  
        int r = getMeasuredWidth()>getMeasuredHeight()?getMeasuredWidth():getMeasuredHeight();  
        rectF.set(getPaddingLeft(),getPaddingTop(),r-getPaddingRight(),r-getPaddingBottom());  
        canvas.drawArc(rectF,0,360,false,mPaint);  
    }  

}
