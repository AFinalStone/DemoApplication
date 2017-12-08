package com.example.myapplication2;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.shi.androidstudy.tool.SnackBarUtil;
import com.shi.androidstudy.widget.loading.LoadingDialog;
import com.shi.androidstudy.widget.pswkeyboard.PasswordView;

public class MainActivity extends AppCompatActivity {

    PasswordView passwordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passwordView = (PasswordView) findViewById(R.id.passwordView);
        passwordView.setOnPasswordInputListener(new PasswordView.OnPasswordInputListener() {
            @Override
            public void onPasswordInputFinish(String passWord) {
                LoadingDialog.getInstance().showDialogForLoading(MainActivity.this);
                new Thread(){
                    @Override
                    public void run() {

                        SystemClock.sleep(3000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                passwordView.setVisibility(View.INVISIBLE);
                                LoadingDialog.getInstance().cancelDialogForLoading();
                                showMsg("付款成功");
                            }
                        });
                    }
                }.start();
            }
        });
    }
    private void showMsg(String msg){
        SnackBarUtil.showLong(passwordView,msg);
    }
}
