package com.example.rxjava_retrofit04.model;


import com.example.rxjava_retrofit04.entity.HttpResult;
import com.example.rxjava_retrofit04.entity.MovieSubjectsBean;

import java.util.List;

import io.reactivex.Observer;

/**
 * Created by afinalstone on 17-4-26.
 */

public interface MovieModel {
    public void getMovieString(int start, int count, Observer<String> observer);
    public void getMovieObject(int start, int count, Observer<HttpResult<List<MovieSubjectsBean>>> observer);
    public void getMovieObjectExt(int start, int count, Observer<List<MovieSubjectsBean>> observer);
}
