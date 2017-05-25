package com.example.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.View;

import com.example.loginbytencent.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_name)
    AppCompatAutoCompleteTextView etName;

    @BindView(R.id.et_password)
    AppCompatAutoCompleteTextView etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.btn_loginByQQ, R.id.btn_loginByWx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:

                break;
            case R.id.btn_loginByQQ:
                break;
            case R.id.btn_loginByWx:
                break;
        }
    }
}
