package com.example.rxjava_retrofit04.controller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rxjava_retrofit01.R;
import com.example.rxjava_retrofit04.entity.MovieSubjectsBean;
import com.example.rxjava_retrofit04.entity.MsgCode;
import com.example.rxjava_retrofit04.model.MovieModel;
import com.example.rxjava_retrofit04.model.MovieModelImpl;

import java.util.List;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {


    private TextView tv_content;
    private MovieModel<List<MovieSubjectsBean>> movieModel = new MovieModelImpl();
    Subscriber<MsgCode> subscriber;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("rxjava+retrofit04");
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText("网络请求");
        Button button = new Button(this);
        button.setText("取消网络请求");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.hide();
                subscriber.unsubscribe();
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        addContentView(button, layoutParams);

        progressDialog = new ProgressDialog(this);
    }

    public void onClick(View view) {
        getMovieInfo();
    }

    private void getMovieInfo() {
        progressDialog.show();
//        subscriber = new Subscriber<List<MovieSubjectsBean>>() {
//            @Override
//            public void onCompleted() {
//                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
//                progressDialog.hide();
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                progressDialog.hide();
//            }
//
//            @Override
//            public void onNext(List<MovieSubjectsBean> movieEntity) {
//                System.out.println(movieEntity);
//                tv_content.setText(movieEntity.toString());
//            }
//        };
        subscriber = new Subscriber<MsgCode>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }

            @Override
            public void onNext(MsgCode msgCode) {
                System.out.println(msgCode);
                tv_content.setText(msgCode.toString());
            }
        };
        movieModel.getMovieTop100(0, 10, subscriber);
//        subscriber.unsubscribe();
    }
}
