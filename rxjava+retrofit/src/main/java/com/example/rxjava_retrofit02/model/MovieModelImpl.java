package com.example.rxjava_retrofit02.model;

import com.example.rxjava_retrofit02.entity.MovieEntity;
import com.example.rxjava_retrofit02.http.HttpMethods;

import io.reactivex.Observer;

/**
 * Created by afinalstone on 17-4-26.
 *  Rxjava+retrofit的基本使用方法
 */

public class MovieModelImpl implements MovieModel<MovieEntity> {

    @Override
    public void getMovie(int start, int count, Observer<MovieEntity> observer) {
        HttpMethods.getInstance().getTopMovie(observer, 0, 10);
    }

}
