package com.hm.rxjava_retrofit02.model;

import com.hm.rxjava_retrofit02.entity.MovieEntity;
import com.hm.rxjava_retrofit02.http.HttpMethods;

import io.reactivex.Observer;

/**
 * Created by afinalstone on 17-4-26.
 *  Rxjava+retrofit的基本使用方法
 *  使用rxjava+retrofit进行封装网络请求，并封装请求对象为httpMethods对象
 */

public class MovieModelImpl implements MovieModel<MovieEntity> {

    @Override
    public void getMovie(int start, int count, Observer<MovieEntity> observer) {
        HttpMethods.getInstance().getTopMovie(observer, 0, 10);
    }

}
