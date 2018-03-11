package com.hm.rxjava_retrofit03.model;


import io.reactivex.Observer;

/**
 * Created by afinalstone on 17-4-26.
 */

public interface MovieModel<T> {
    public void getMovie(int start, int count, Observer<T> observer);
}
