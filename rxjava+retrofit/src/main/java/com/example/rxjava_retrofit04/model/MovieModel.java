package com.example.rxjava_retrofit04.model;


import com.example.rxjava_retrofit04.entity.MsgCode;

import rx.Subscriber;

/**
 * Created by afinalstone on 17-4-26.
 */

public interface MovieModel<T> {
    public void getMovie(int start, int count, Subscriber<T> subscriber);
    public void getMovieTop100(int start, int count, Subscriber<MsgCode> subscriber);
}
