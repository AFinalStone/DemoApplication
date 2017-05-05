package com.example.rxjava_retrofit04.http;


import com.example.rxjava_retrofit04.entity.HttpResult;
import com.example.rxjava_retrofit04.entity.MovieSubjectsBean;
import com.example.rxjava_retrofit04.entity.MsgCode;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by liukun on 16/3/9.
 */
public interface MovieService {

    @GET("top250")
    Observable<HttpResult<List<MovieSubjectsBean>>> getTopMovieList(@Query("start") int start, @Query("count") int count);

   // https://api.douban.com/v2/movie/top100?start=0&count=10
    @GET("top250")
    Observable<MsgCode> getTopMovieString(@Query("start") int start, @Query("count") int count);

}
