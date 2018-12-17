package com.sensorinfor.main;

import java.lang.reflect.Field;

import android.app.Application;
import android.graphics.Typeface;

public class MyTypeface extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Typeface mTypeface = Typeface.createFromAsset(getAssets(), "NotoSansCJKjp-Light.otf");

        try {
            Field field = Typeface.class.getDeclaredField("MONOSPACE");
            field.setAccessible(true);
            field.set(null, mTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
	}
	
}
