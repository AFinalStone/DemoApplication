package com.hm.dbflow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ModelContainer;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 必须继承BaseModel，BaseModel包含了基本的数据库操作
 * （save、delete、update、insert、exists），可以发现这个表是关联上面定义的数据库
 * ，UserModel 的id是自增的id（autoincrement ）。
 * Created by afinalstone on 17-4-24.
 */
@ModelContainer
@Table(database = MyDbflowData.class)
public class UserBean extends BaseModel {
    //用户ID编号
    @Column
    @PrimaryKey(autoincrement = true)
    public long userId;
    //用户姓名
    @Column
    public String userName;
    //用户年龄
    @Column
    public int age;
    //用户性别,男为true
    @Column
    public boolean sex;

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                '}';
    }
}
//@ModelContainer
//@Table(databaseName = DBFlowDatabase.NAME)
//public class UserModel extends BaseModel {
//    //自增ID
//    @Column
//    @PrimaryKey(autoincrement = true)
//    public Long id;
//    @Column
//    public String name;
//    @Column
//    public int sex;
//}