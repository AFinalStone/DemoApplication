package com.example.retrofit.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.retrofit.Interface.MovieService;
import com.example.retrofit.R;
import com.example.retrofit.bean.MovieBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

    }

    @Override
    public void onClick(View v) {
        getMovie();
    }

    private void getMovie(){
        progressBar.setVisibility(View.VISIBLE);
        String baseUrl = "https://api.douban.com/v2/movie/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieService movieService = retrofit.create(MovieService.class);
        Call<MovieBean> call = movieService.getTopMovie(0, 10);
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

}
