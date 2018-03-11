package com.hm.rxjava_retrofit01.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hm.rxjava_retrofit01.R;
import com.hm.rxjava_retrofit01.bean.MovieBean;
import com.hm.rxjava_retrofit01.model.MovieModel;
import com.hm.rxjava_retrofit01.model.MovieModelImpl;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


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

        movieModel.getMovie(0, 10, new Observer<MovieBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MovieBean value) {
                System.out.println(value);
                tv_content.setText(value.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
