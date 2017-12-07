package com.example.rxjava_retrofit01.model;


import com.example.rxjava_retrofit01.bean.MovieBean;

import io.reactivex.Observer;

/**
 * Created by afinalstone on 17-4-26.
 */

public interface MovieModel<T> {
    public void getMovie(int start, int count, Observer<T> subscriber);
}
