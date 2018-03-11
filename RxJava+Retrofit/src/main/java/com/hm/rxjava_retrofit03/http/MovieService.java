package com.hm.rxjava_retrofit03.http;


import com.hm.rxjava_retrofit03.entity.HttpResult;
import com.hm.rxjava_retrofit03.entity.MovieSubjectsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liukun on 16/3/9.
 */
public interface MovieService {

    @GET("top250")
    Observable<HttpResult<List<MovieSubjectsBean>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
