package com.example.snackbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.snackbar.view.Sneaker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sneaker.setDisplayConfigs(getApplicationContext(),
                R.dimen.edge, R.color.normal, R.dimen.normal, R.dimen.title_height);
    }

    public void onClick(View view) {
        Sneaker.with(this).setMessage("相机所需全部权限已经允许.")
                .setIcon(R.mipmap.qrh).setAutoHide(true).create().show();
    }
}
