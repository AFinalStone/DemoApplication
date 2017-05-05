package com.example.rxjava_retrofit04.model;


import com.example.rxjava_retrofit04.entity.MsgCode;

import org.reactivestreams.Subscriber;

import io.reactivex.Observer;

/**
 * Created by afinalstone on 17-4-26.
 */

public interface MovieModel<T> {
    public void getMovie(int start, int count, Observer<T> subscriber);
    public void getMovieTop100(int start, int count, Subscriber<T> subscriber);
}
