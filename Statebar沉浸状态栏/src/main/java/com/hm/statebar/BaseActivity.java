package com.hm.statebar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.shi.androidstudy.tool.statebar.StateBarUtil;


/**
 * Created by geyifeng on 2017/5/9.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StateBarUtil.with(this).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StateBarUtil.with(this).destroy();
    }
}
