package com.hm.iou;

import android.app.Application;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

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
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

        PlatformConfig.setWeixin("wx54a8a6252c69ea7c", "fbecfb41d780a864653fd03ca1faa550");
        PlatformConfig.setQQZone("qq123", "qq123secret");
        PlatformConfig.setSinaWeibo("22876744", "fbecfb41d780a864653fd03ca1faa550", "http://www.baidu.com");

    }
}
