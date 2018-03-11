package com.hm.rxjava_retrofit04.model;

import com.hm.rxjava_retrofit04.entity.HttpResult;
import com.hm.rxjava_retrofit04.entity.MovieSubjectsBean;
import com.hm.rxjava_retrofit04.http.HttpMethods;

import java.util.List;

import io.reactivex.Observer;


/**
 * Created by afinalstone on 17-4-26.
 *  Rxjava+retrofit的基本使用方法
 */

public class MovieModelImpl implements MovieModel{


    @Override
    public void getMovieString(int start, int count, Observer<String> observer) {
        HttpMethods.getInstance().getTopMovieString(observer,0,10);
    }

    @Override
    public void getMovieObject(int start, int count, Observer<HttpResult<List<MovieSubjectsBean>>> observer) {
        HttpMethods.getInstance().getTopMovieObject(observer,0,10);
    }

    @Override
    public void getMovieObjectExt(int start, int count, Observer<List<MovieSubjectsBean>> observer) {
        HttpMethods.getInstance().getTopMovieObjectExt(observer,0,10);
    }
}
