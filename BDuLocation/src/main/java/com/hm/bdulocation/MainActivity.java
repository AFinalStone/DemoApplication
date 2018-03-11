package com.hm.bdulocation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getAddressInfo:
                startActivity(new Intent(this, LocationGetAddressInfoActivity.class));
                break;
            case R.id.btn_getAddressXY:
                startActivity(new Intent(this, LocationGetXYActivity.class));
                break;
        }
    }
}
