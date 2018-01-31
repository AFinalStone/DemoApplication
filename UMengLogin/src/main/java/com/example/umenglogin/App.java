package com.example.umenglogin;

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

        PlatformConfig.setWeixin("weixin123","weixin123secret");
        PlatformConfig.setQQZone("qq123","qq123secret");
        PlatformConfig.setSinaWeibo("weibo123","weibo123secret","http://www.baidu.com");
    }
}
