package com.example.rxjava_retrofit02.model;


import rx.Subscriber;

/**
 * Created by afinalstone on 17-4-26.
 */

public interface MovieModel<T> {
    public void getMovie(int start, int count, Subscriber<T> subscriber);
}
