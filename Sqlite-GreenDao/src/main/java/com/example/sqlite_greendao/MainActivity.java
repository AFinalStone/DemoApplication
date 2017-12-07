package com.example.sqlite_greendao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.sqlite_greendao.greenda.UserDao;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    UserDao mUserDao;
    private final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserDao = MyApplication.getInstances().getDaoSession().getUserDao();
    }

    public void onClick(View view) {
        long id;
        switch (view.getId()) {
            case R.id.singObjectAdd:
                User mUser = new User((long)2,"anye2");
                mUserDao.insert(mUser);//添加一个
                mUser = new User((long)3,"anye3");
                mUserDao.insert(mUser);//添加一个
                break;
            case R.id.singObjectUpdate:
                mUser = new User((long)2,"anye222222");
                mUserDao.update(mUser);
                break;
            case R.id.singObjectSelect:
                id = 2;
                User user = mUserDao.load(id);
                if(user == null){
                    Log.e(TAG, "User: 未查询到合适的数据");
                }else{
                    Log.e(TAG, "User: "+user.toString() );
                }
                break;
            case R.id.singObjectDelete:
                id = 2;
                mUserDao.deleteByKey(id);
                break;
            case R.id.mulObjectSelect:
                List<User> users = mUserDao.loadAll();
                String userName = "";
                for (int i = 0; i < users.size(); i++) {
                    userName += users.get(i).getName()+",";
                }
                Log.e(TAG, "查询全部数据"+userName);
                break;
            case R.id.mulObjectSelectByWhere:
                users = mUserDao.queryBuilder().where(UserDao.Properties.Id.eq("anye3")).list();
                for (int i = 0; i < users.size(); i++) {
                    userName = users.get(i).getName();
                    Log.e(TAG, "查询全部数据"+userName);
                }
                break;
        }
    }
}
