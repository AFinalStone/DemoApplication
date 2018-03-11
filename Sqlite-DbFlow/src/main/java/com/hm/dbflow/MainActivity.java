package com.hm.dbflow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.raizlabs.android.dbflow.sql.language.Condition;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    UserBean userBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.singObjectAdd:
                userBean = new UserBean();
                userBean.userName = "李白";
                userBean.age = (int) (Math.random() % 100);
                userBean.sex = true;
                userBean.save();
                break;
            case R.id.singObjectUpdate:
                if (userBean != null) {
                    userBean.userName = "李小画";
                    userBean.age = 22;
                    userBean.sex = false;
                    userBean.update();
                }
                break;
            case R.id.singObjectSelect:
                if (userBean != null) {
                    UserBean bean = new Select().from(UserBean.class).querySingle();
                    System.out.println(bean);
                }
                break;
            case R.id.singObjectDelete:
                if (userBean != null) {
                    userBean.delete();
                }
                break;
            case R.id.mulObjectSelect:
                List<UserBean> list = new Select().from(UserBean.class).queryList();
                System.out.println(list);
                break;
            case R.id.mulObjectSelectByWhere:
                List<UserBean> list2 = new Select().from(UserBean.class).where(
                        Condition.column(new NameAlias("userName")).is("李小画"),
                        Condition.column(new NameAlias("sex")).is(false)).queryList();
                System.out.println(list2);
                break;
        }
    }
}
