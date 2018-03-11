package com.hm.rxjava_retrofit02.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.rxjava_retrofit01.R;
import com.hm.rxjava_retrofit02.entity.MovieEntity;
import com.hm.rxjava_retrofit02.model.MovieModel;
import com.hm.rxjava_retrofit02.model.MovieModelImpl;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity {


    private TextView tv_content;
    private MovieModel<MovieEntity> movieModel = new MovieModelImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("rxjava+retrofit02");
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText("使用rxjava+retrofit进行封装网络请求，并封装请求对象为httpMethods对象");
    }

    public void onClick(View view) {
        getMovieInfo();
    }

    private void getMovieInfo() {

        Observer<MovieEntity> observer = new Observer<MovieEntity>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(MovieEntity value) {
                System.out.println(value);
                tv_content.setText(value.toString());
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }
        };
        movieModel.getMovie(0, 10, observer);
    }
}
