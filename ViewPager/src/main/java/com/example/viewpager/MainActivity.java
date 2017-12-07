package com.example.viewpager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shi.androidstudy.tool.PreferencesUtil;
import com.shi.androidstudy.tool.statebar.StateBarUtil;
import com.shi.androidstudy.widget.viewpagerIndicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    /**guide_page_01**/
    ViewPager viewPager;

    /**数据**/
    List<Integer> listData = new ArrayList<>();
    MyPagerAdapter myPagerAdapter;

    private int MessageEmpty = 1;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            listData.add(R.mipmap.guide_page_01);
            myPagerAdapter.notifyDataSetChanged();
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateBarUtil.with(this).transparentBar().fullScreen(true).init();
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        listData.add(R.mipmap.guide_page_01);
        listData.add(R.mipmap.guide_page_02);
        listData.add(R.mipmap.guide_page_03);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);
        indicator.setViewPager(viewPager);
        myPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());
        handler.sendEmptyMessageDelayed(MessageEmpty,3000);
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return listData.size();
        }
        /**
         * 1. 根据position获取对应的view，给view添加到container
         * 2. 返回一个view（Viewpaer的每个界面的内容）
         * 采用的是Viewpager+自定义的view（对外提供自己长什么样子：view）
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int currentImageViewId = listData.get(position);
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imageView.setImageResource(currentImageViewId);
            if(position == listData.size()-1){
                imageView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        PreferencesUtil.putBoolean("SP_IfFirstOpenApp", false);
                        finish();
                    }
                });
            }
            container.addView(imageView);
            return imageView;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

    }



    public void onClick(View view){
//        myPagerAdapter.addItem();
    }
}
