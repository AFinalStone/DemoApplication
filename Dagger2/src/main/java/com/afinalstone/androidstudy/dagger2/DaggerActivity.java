package com.afinalstone.androidstudy.dagger2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import javax.annotation.Nullable;
import javax.inject.Inject;

public class DaggerActivity extends AppCompatActivity {

    private static final String TAG = "DaggerActivity";
    TextView text;

    @Inject
    DaggerPresenter presenter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        inject();
        presenter.showUserName();
        //Log.i(TAG, "client = " + (client == null ? "null" : client));
    }

    private void inject() {
        DaggerActivityComponent.builder().activityModule(new ActivityModule(this))
                .build().inject(this);
    }

    public void showUserName(String name) {
        text.setText(name);
    }
}
