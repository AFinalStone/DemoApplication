package com.example.afinalstone.demoapplication.controller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.afinalstone.demoapplication.R;
import com.example.afinalstone.demoapplication.entity.UserBean;
import com.example.afinalstone.demoapplication.model.UserModel;
import com.example.afinalstone.demoapplication.model.UserModelImpl;
import com.example.afinalstone.demoapplication.model.UserOnListener;


/**
 * create by AFinalStone
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private UserModel userModel;
    private AppCompatAutoCompleteTextView et_name;
    private AppCompatAutoCompleteTextView et_password;
    ArrayAdapter<String> arrayAdapter;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById(R.id.btn_login).setOnClickListener(this);
        et_name = findView(R.id.et_name);
        et_password = findView(R.id.et_password);
        et_name.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (et_name.getCompoundDrawables()[2] == null)
                    return false;

                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                //触摸点位置判断
                if (event.getX() > et_name.getWidth() - et_name.getPaddingRight() - getResources().getDisplayMetrics().density*30)
                {
                    et_name.setText("");
                }
                return false;
            }
        });

        userModel = new UserModelImpl();
        String[] arr = getResources().getStringArray(R.array.userNames);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        et_name.setAdapter(arrayAdapter);
    }



    /**
     * @param msg 需要描述的信息
     */
    public void showMsg(String msg){
        Snackbar.make(et_name,msg,Snackbar.LENGTH_SHORT).show();
    }

    public void showLoadingView(){
        if(progressBar == null){
            progressBar = new ProgressDialog(this);
            progressBar.setMessage("。。。请稍等。。。");
        }
        progressBar.show();
    }

    public void hideLoaingView(){
        progressBar.dismiss();
    }


    @Override
    public void onClick(View v) {
        showLoadingView();
        userModel.getUser(et_name.getText().toString().trim(), et_password.getText().toString().trim()
                , new UserOnListener() {
                    @Override
                    public void onSuccess( UserBean userBean) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideLoaingView();
                            }
                        });
                        displayResult(userBean);
                    }

                    @Override
                    public void onError() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideLoaingView();
                            }
                        });
                        showMsg("登录失败");
                    }
                });
    }


    /**
     * 显示结果
     * @param userBean
     */
    public void displayResult(UserBean userBean) {
        showMsg("恭喜"+userBean.getName()+"登录成功");
    }

    private <T extends View> T findView(int id) {
        return (T) findViewById(id);
    }
}
