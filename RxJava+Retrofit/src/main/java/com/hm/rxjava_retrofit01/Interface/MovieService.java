package com.hm.rxjava_retrofit01.Interface;


import com.hm.rxjava_retrofit01.bean.MovieBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by afinalstone on 17-4-25.
 */
//https://api.douban.com/v2/movie/top250?start=0&count=10
public interface MovieService {

    @GET("top250")
    Observable<MovieBean> getTopMovie(@Query("start") int start, @Query("count") int count);

}