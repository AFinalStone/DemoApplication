package com.example.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String insert = "insert into user (username,password) values ('小话','100000000');";
    private String select = "select * from user where username = 小话";
    private String update = "update user set password = '200000000' where username = '小话';";
    private String delete = "delete from user where username='小话';";
    DatabaseHelper dbHelper = new DatabaseHelper(this);
    SQLiteDatabase dbRead;
    SQLiteDatabase dbWrite;

    private ListView listView;
    List<UserBean> listData = new ArrayList<>();
    MyAdapter myAdapter = new MyAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //调用getReadableDatabase或者getWritableDatabase的时候会创建数据库
        dbRead = dbHelper.getReadableDatabase();
        dbWrite = dbHelper.getWritableDatabase();
        findView(R.id.btn_inster).setOnClickListener(this);
        findView(R.id.btn_update).setOnClickListener(this);
        findView(R.id.btn_select).setOnClickListener(this);
        findView(R.id.btn_delete).setOnClickListener(this);
        findView(R.id.btn_execSQL).setOnClickListener(this);
        listView = findView(R.id.listView);
        listView.setAdapter(myAdapter);
    }


    private void insert() {
        dbWrite.execSQL(insert);
    }


    private void update() {
        dbWrite.execSQL(update);
    }

    private void select() {

//        Cursor c = dbRead.rawQuery("select * from user where username = ?", new String[]{"小话"});
        Cursor c = dbRead.rawQuery("select * from user", null);
        while (c.moveToNext()){
            String userName = c.getString(c.getColumnIndex("username"));
            String password = c.getString(c.getColumnIndex("password"));
            listData.add(new UserBean(userName,password));
        }
        myAdapter.notifyDataSetChanged();
    }

    private void delete() {
        dbWrite.execSQL(delete);
    }

    private <T extends View> T findView(int res) {
        return (T) findViewById(res);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_inster:
                insert();
                break;

            case R.id.btn_update:
                update();
                break;

            case R.id.btn_select:
                select();
                break;

            case R.id.btn_delete:
                delete();
                break;

            case R.id.btn_execSQL:
                EditText editText = findView(R.id.et_Sql);
                String sqlMsg = editText.getText().toString();
                if(!TextUtils.isEmpty(sqlMsg)){
                    dbWrite.execSQL(sqlMsg);
                }
                break;
        }
    }

    private class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public Object getItem(int position) {
            return listData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(MainActivity.this);
            textView.setText(listData.get(position).toString());
            textView.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT,80));
            textView.setGravity(Gravity.CENTER);
            return textView;
        }
    }
}
