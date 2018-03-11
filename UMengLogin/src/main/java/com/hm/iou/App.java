package com.hm.iou;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;

/**
 * Created by AFinalStone on 2018/1/31.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initUMengData();
    }

    private void initUMengData() {
        PlatformConfig.setWeixin("wx54a8a6252c69ea7c","fbecfb41d780a864653fd03ca1faa550");
    }
}
