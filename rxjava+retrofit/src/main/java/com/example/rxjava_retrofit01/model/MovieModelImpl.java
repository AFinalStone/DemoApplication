package com.example.rxjava_retrofit01.model;

import com.example.rxjava_retrofit01.Interface.MovieService;
import com.example.rxjava_retrofit01.bean.MovieBean;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by afinalstone on 17-4-26.
 *  Rxjava+retrofit的基本使用方法
 */

public class MovieModelImpl implements MovieModel<MovieBean> {

    @Override
    public void getMovie(int start, int count, Subscriber<MovieBean> subscriber) {
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();


        MovieService movieService = retrofit.create(MovieService.class);

        movieService.getTopMovie(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
