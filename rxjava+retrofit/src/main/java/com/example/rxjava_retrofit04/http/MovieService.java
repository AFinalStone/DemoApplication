package com.example.rxjava_retrofit04.http;


import com.example.rxjava_retrofit04.entity.HttpResult;
import com.example.rxjava_retrofit04.entity.MovieSubjectsBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by liukun on 16/3/9.
 */
public interface MovieService {

    @GET("top250")
    Observable<HttpResult<List<MovieSubjectsBean>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
