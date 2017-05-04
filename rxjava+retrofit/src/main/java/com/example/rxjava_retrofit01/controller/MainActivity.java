package com.example.rxjava_retrofit01.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.rxjava_retrofit01.R;
import com.example.rxjava_retrofit01.bean.MovieBean;
import com.example.rxjava_retrofit01.model.MovieModel;
import com.example.rxjava_retrofit01.model.MovieModelImpl;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {


    private TextView tv_content;
    private MovieModel<MovieBean> movieModel = new MovieModelImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("rxjava+retrofit01");
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText("直接使用rxjava+retrofit进行网络请求");
    }

    public void onClick(View view){
        getMovieInfo();
    }

    private void getMovieInfo(){

        movieModel.getMovie(0, 10, new Subscriber<MovieBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MovieBean movieBean) {
                System.out.println(movieBean);
                tv_content.setText(movieBean.toString());
            }
        });
    }
}
