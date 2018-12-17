package com.example.generalizationdemo;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GuideActivity extends Activity {
	private ViewPager viewPager;//闇�瑕乂iewPaeger
    private PagerAdapter mAdapter;//闇�瑕丳agerAdapter閫傞厤鍣�
    private List<View> mViews=new ArrayList<>();//鍑嗗鏁版嵁婧�
    private Button bt_home;//鍦╒iewPager鐨勬渶鍚庝竴涓〉闈㈣缃竴涓寜閽紝鐢ㄤ簬鐐瑰嚮璺宠浆鍒癕ainActivity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();//璋冪敤
    }

    private void initView() {
        viewPager= (ViewPager) findViewById(R.id.view_pager);

        LayoutInflater inflater=LayoutInflater.from(this);//灏嗘瘡涓獂ml鏂囦欢杞寲涓篤iew
        View guideOne=inflater.inflate(R.layout.guidance01, null);//姣忎釜xml涓氨鏀剧疆涓�涓猧mageView
        View guideTwo=inflater.inflate(R.layout.guidance02,null);
        View guideThree=inflater.inflate(R.layout.guidance03,null);

        mViews.add(guideOne);//灏唙iew鍔犲叆鍒發ist涓�
        mViews.add(guideTwo);
        mViews.add(guideThree);

        mAdapter=new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view=mViews.get(position);//鍒濆鍖栭�傞厤鍣紝灏唙iew鍔犲埌container涓�
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                View view=mViews.get(position);
                container.removeView(view);//灏唙iew浠巆ontainer涓Щ闄�
            }

            @Override
            public int getCount() {
                return mViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;//鍒ゆ柇褰撳墠鐨剉iew鏄垜浠渶瑕佺殑瀵硅薄
            }
        };

        viewPager.setAdapter(mAdapter);

        bt_home= (Button) guideThree.findViewById(R.id.to_Main);
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
