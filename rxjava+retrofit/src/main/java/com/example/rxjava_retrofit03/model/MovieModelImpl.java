package com.example.rxjava_retrofit03.model;

import com.example.rxjava_retrofit03.entity.MovieSubjectsBean;
import com.example.rxjava_retrofit03.http.HttpMethods;

import java.util.List;

import rx.Subscriber;

/**
 * Created by afinalstone on 17-4-26.
 *  Rxjava+retrofit的基本使用方法
 */

public class MovieModelImpl implements MovieModel<List<MovieSubjectsBean>> {

    @Override
    public void getMovie(int start, int count, Subscriber<List<MovieSubjectsBean>> subscriber) {
        HttpMethods.getInstance().getTopMovie(subscriber, 0, 10);
    }

}
