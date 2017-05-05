package com.example.rxjava_retrofit04.model;

import com.example.rxjava_retrofit04.entity.MovieSubjectsBean;
import com.example.rxjava_retrofit04.entity.MsgCode;
import com.example.rxjava_retrofit04.http.HttpMethods;

import org.reactivestreams.Subscriber;

import java.util.List;

import io.reactivex.Observer;


/**
 * Created by afinalstone on 17-4-26.
 *  Rxjava+retrofit的基本使用方法
 */

public class MovieModelImpl implements MovieModel<String> {

    @Override
    public void getMovie(int start, int count, Observer<String> observer) {
//        HttpMethods.getInstance().getTopMovieList(observer, 0, 10);
    }

    @Override
    public void getMovieTop100(int start, int count, Subscriber<String> subscriber) {
        HttpMethods.getInstance().getTopMovieString(subscriber, 0, 10);
    }

}
