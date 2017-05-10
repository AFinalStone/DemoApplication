package com.example.rxjava_retrofit04.http;


import com.example.rxjava_retrofit04.entity.HttpResult;
import com.example.rxjava_retrofit04.entity.MovieSubjectsBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by AfinalStone on 16/3/9.
 */
public interface WebService {


   // https://api.douban.com/v2/movie/top100?start=0&count=10
    @GET("top250")
    Observable<String> getTopMovieString(@Query("start") int start, @Query("count") int count);

    //获取返回结果并把返回结果封装成对象
    @GET("top250")
    Observable<HttpResult<List<MovieSubjectsBean>>> getTopMovieObject(@Query("start") int start, @Query("count") int count);

    //获取返回结果并对返回结果进行统一预处理
    @GET("top250")
    Observable<HttpResult<List<MovieSubjectsBean>>> getTopMovieObjectEx(@Query("start") int start, @Query("count") int count);
}
