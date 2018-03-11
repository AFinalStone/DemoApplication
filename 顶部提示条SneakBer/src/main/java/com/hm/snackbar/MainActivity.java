package com.hm.snackbar;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.hm.snackbar.view.SneakBer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SneakBer.setDisplayConfigs(getApplicationContext(),
                R.dimen.edge, R.color.normal, R.dimen.normal, R.dimen.title_height);
    }


    public void onClick02(View view) {
        SneakBer.with(this).setMessage("我是一条提示信息")
                .setIcon(R.mipmap.qrh)
                .setAutoHide(true)
                .create().show();
    }

    public void onClick01(View view) {


        SneakBer.with(this).setMessage("我是一个进度条")
                .setLoading(true)
                .setBackgroundColor(Color.BLACK)
                .setAutoHide(true)
                .create()
                .show();

    }
}
