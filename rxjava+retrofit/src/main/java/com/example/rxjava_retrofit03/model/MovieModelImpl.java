package com.example.rxjava_retrofit03.model;

import com.example.rxjava_retrofit03.entity.MovieSubjectsBean;
import com.example.rxjava_retrofit03.http.HttpMethods;

import org.reactivestreams.Subscriber;

import java.util.List;

import io.reactivex.Observer;

/**
 * Created by afinalstone on 17-4-26.
 *  Rxjava+retrofit的基本使用方法
 */

public class MovieModelImpl implements MovieModel<List<MovieSubjectsBean>> {


    @Override
    public void getMovieByObserver(int start, int count, Observer<List<MovieSubjectsBean>> observer) {
        HttpMethods.getInstance().getTopMovieByObserver(observer, 0, 10);
    }

    @Override
    public void getMovieBySubsuber(int start, int count, Subscriber<List<MovieSubjectsBean>> observer) {
        HttpMethods.getInstance().getTopMovieBySubscriber(observer, 0, 10);
    }

}
