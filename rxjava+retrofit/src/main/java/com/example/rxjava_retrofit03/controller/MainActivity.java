package com.example.rxjava_retrofit03.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rxjava_retrofit01.R;
import com.example.rxjava_retrofit03.entity.MovieSubjectsBean;
import com.example.rxjava_retrofit03.model.MovieModel;
import com.example.rxjava_retrofit03.model.MovieModelImpl;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {


    private TextView tv_content;
    private MovieModel<List<MovieSubjectsBean>> movieModel = new MovieModelImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("rxjava+retrofit03");
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText("使用rxjava+retrofit进行封装网络请求，封装请求过程为httpMethods对象，" +
                "并对返回相同格式的Http请求结果数据统一进行预处理");

    }

    public void onClick(View view) {
//        getMovieInfoByObserver();
        getMovieInfoBySubscriber();
    }

    private void getMovieInfoByObserver() {
        Observer<List<MovieSubjectsBean>> observer = new Observer<List<MovieSubjectsBean>>() {

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<MovieSubjectsBean> movieEntity) {
                System.out.println(movieEntity);
                tv_content.setText(movieEntity.toString());
            }
        };
        movieModel.getMovieByObserver(0, 10, observer);
    }

    private void getMovieInfoBySubscriber() {
        Subscriber<List<MovieSubjectsBean>> subscriber = new Subscriber<List<MovieSubjectsBean>>() {

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(List<MovieSubjectsBean> movieEntity) {
                System.out.println(movieEntity);
                tv_content.setText(movieEntity.toString());
            }
        };
        movieModel.getMovieBySubsuber(0, 10, subscriber);
    }
}
