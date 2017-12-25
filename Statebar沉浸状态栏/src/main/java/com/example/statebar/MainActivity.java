package com.example.statebar;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.shi.androidstudy.tool.statebar.StateBarUtil;

public class MainActivity extends BaseActivity {

//    Toolbar toolbar;
    SeekBar seekBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateBarUtil.with(this).transparentBar().init();
        setContentView(R.layout.activity_main);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float alpha = (float) progress / 100;
                StateBarUtil.with(MainActivity.this)
                        .barColorTransform(R.color.orange)
                        .navigationBarColorTransform(R.color.tans)

//                        .addViewSupportTransformColor(toolbar)
                        .barAlpha(alpha)
                        .init();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_status_color:
                StateBarUtil.with(this).statusBarColor(R.color.colorAccent).removeSupportView().init();
                break;
            case R.id.btn_navigation_color:
                StateBarUtil.with(this).navigationBarColor(R.color.colorAccent).init();
                break;
            case R.id.btn_color:
                StateBarUtil.with(this)
                        .transparentStatusBar()
                        .navigationBarColor(R.color.colorPrimary)
                        .init();
                break;
        }
    }
}
