package com.hm.rxjava_retrofit03.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.rxjava_retrofit01.R;
import com.hm.rxjava_retrofit03.entity.MovieSubjectsBean;
import com.hm.rxjava_retrofit03.model.MovieModel;
import com.hm.rxjava_retrofit03.model.MovieModelImpl;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {


    private TextView tv_content;
    private MovieModel<List<MovieSubjectsBean>> movieModel = new MovieModelImpl();
    private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("rxjava+retrofit03");
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText("使用rxjava+retrofit进行封装网络请求，封装请求过程为httpMethods对象，并对返回相同格式的Http请求结果数据统一进行预处理");

    }

    public void onClick(View view) {
        getMovieInfoByObserver();
    }

    private void getMovieInfoByObserver() {
        Observer<List<MovieSubjectsBean>> observer = new Observer<List<MovieSubjectsBean>>() {

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
                System.out.println("OnComplete被执行");
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println("onError被执行");
            }

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                d.dispose();
                System.out.println("onSubscribe被执行");
            }

            @Override
            public void onNext(List<MovieSubjectsBean> movieEntity) {
                System.out.println(movieEntity);
                tv_content.setText(movieEntity.toString());
                System.out.println("onNext被执行");
            }
        };
        movieModel.getMovie(0, 10, observer);
    }

}
