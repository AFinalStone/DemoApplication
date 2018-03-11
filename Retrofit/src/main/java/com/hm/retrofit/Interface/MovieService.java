package com.hm.retrofit.Interface;

import com.hm.retrofit.bean.MovieBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by afinalstone on 17-4-25.
 */
//https://api.douban.com/v2/movie/top250?start=0&count=10
public interface MovieService {

    @GET("top250")
    Call<MovieBean> getTopMovieObject(@Query("start") int start, @Query("count") int count);

    @GET("top250")
    Call<String> getTopMovieString(@Query("start") int start, @Query("count") int count);

}