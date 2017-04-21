package com.example.sqlite;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private String 	insert ="insert into person (name,age) values ('小话','100000000');";
    private String 	select ="select  * from person;";
    private String 	update ="update account set money = '200000000' where name='lf';";
    private String 	delete ="delete from account where name='lf';";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db = new DatabaseHelper(this);
        db.getWritableDatabase().execSQL(insert);

        ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据cv.put("username","Jack Johnson");//添加用户名
        cv.put("password","iLovePopMusic"); //添加密码
        db.getWritableDatabase().insert("user",null,cv);
    }
}
