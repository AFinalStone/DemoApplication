package com.afinalstone.androidstudy.dagger2.demo01;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.afinalstone.androidstudy.dagger2.R;
import com.afinalstone.androidstudy.dagger2.demo01.ActivityModule;
import com.afinalstone.androidstudy.dagger2.demo01.DaggerActivityComponent;
import com.afinalstone.androidstudy.dagger2.demo01.DaggerPresenter;

import javax.inject.Inject;

public class DaggerActivity extends AppCompatActivity {

    private static final String TAG = "DaggerActivity";
    TextView text_name;
    TextView text_sex;

    @Inject
    DaggerPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_name = (TextView) findViewById(R.id.text_name);
        text_sex = (TextView) findViewById(R.id.text_sex);
        inject();
        presenter.showUserName();
        presenter.showUserSex();
        //Log.i(TAG, "client = " + (client == null ? "null" : client));
    }

    private void inject() {
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this))
                .build().inject(this);
    }

    public void showUserName(String name) {
        text_name.setText(name);
    }

    public void showUserSex(String sex) {
        text_sex.setText(sex);
    }
}
