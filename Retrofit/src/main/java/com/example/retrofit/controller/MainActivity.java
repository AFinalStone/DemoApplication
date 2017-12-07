package com.example.retrofit.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.retrofit.Interface.MovieService;
import com.example.retrofit.R;
import com.example.retrofit.bean.MovieBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private TextView textView02;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        textView02 = (TextView) findViewById(R.id.textView02);
        textView.setOnClickListener(this);
        textView02.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView:
                getMovieObject();
                break;

            case R.id.textView02:
                getMovieString();
                break;
        }
    }

    //请求接口数据，并对接口返回的数据封装成为特定的对象
    private void getMovieObject(){
        progressBar.setVisibility(View.VISIBLE);
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieBean> call = movieService.getTopMovieObject(0, 10);
        call.enqueue(new Callback<MovieBean>() {
            @Override
            public void onResponse(Call<MovieBean> call, Response<MovieBean> response) {
                System.out.println(response.body());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieBean> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //请求接口数据，返回请求结果字符串
    private void getMovieString(){
        progressBar.setVisibility(View.VISIBLE);
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        Call<String> call = movieService.getTopMovieString(0, 10);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println(response.body());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
