package com.example.rxjava_retrofit02.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rxjava_retrofit01.R;
import com.example.rxjava_retrofit02.entity.MovieEntity;
import com.example.rxjava_retrofit02.model.MovieModel;
import com.example.rxjava_retrofit02.model.MovieModelImpl;

import rx.Subscriber;

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
        Subscriber<MovieEntity> subscriber = new Subscriber<MovieEntity>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(MovieEntity movieEntity) {
                System.out.println(movieEntity);
                tv_content.setText(movieEntity.toString());
            }
        };
        movieModel.getMovie(0, 10, subscriber);
    }
}
