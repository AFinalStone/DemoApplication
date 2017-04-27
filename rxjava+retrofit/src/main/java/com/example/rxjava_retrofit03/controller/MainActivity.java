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

import java.util.List;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity {


    private TextView tv_content;
    private MovieModel<List<MovieSubjectsBean>> movieModel = new MovieModelImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText("rxjava+retrofit03");
    }

    public void onClick(View view) {
        getMovieInfo();
    }

    private void getMovieInfo() {
        Subscriber<List<MovieSubjectsBean>> subscriber = new Subscriber<List<MovieSubjectsBean>>() {
            @Override
            public void onCompleted() {
                Toast.makeText(MainActivity.this, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(List<MovieSubjectsBean> movieEntity) {
                System.out.println(movieEntity);
                tv_content.setText(movieEntity.toString());
            }
        };
        movieModel.getMovie(0, 10, subscriber);
    }
}
