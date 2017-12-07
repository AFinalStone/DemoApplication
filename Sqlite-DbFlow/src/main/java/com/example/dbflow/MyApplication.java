package com.example.dbflow;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by afinalstone on 17-4-24.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
