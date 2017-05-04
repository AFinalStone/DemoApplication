package com.example.rxjava_retrofit04.model;

import com.example.rxjava_retrofit04.entity.MovieSubjectsBean;
import com.example.rxjava_retrofit04.entity.MsgCode;
import com.example.rxjava_retrofit04.http.HttpMethods;

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

    @Override
    public void getMovieTop100(int start, int count, Subscriber<MsgCode> subscriber) {
        HttpMethods.getInstance().getTopMovieTop100(subscriber, 0, 10);
    }

}
