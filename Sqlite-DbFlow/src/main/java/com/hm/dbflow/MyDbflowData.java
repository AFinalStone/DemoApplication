package com.hm.dbflow;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by afinalstone on 17-4-24.
 */
@Database(name = MyDbflowData.NAME, version = MyDbflowData.VERSION)
public class MyDbflowData {
    //数据库名称
    public static final String NAME = "MyDbflowData";
    //数据库版本号
    public static final int VERSION = 1;
}
