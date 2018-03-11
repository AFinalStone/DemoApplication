package com.hm.rxjava_retrofit03.model;

import com.hm.rxjava_retrofit03.entity.MovieSubjectsBean;
import com.hm.rxjava_retrofit03.http.HttpMethods;

import java.util.List;

import io.reactivex.Observer;

/**
 * Created by afinalstone on 17-4-26.
 *  Rxjava+retrofit的基本使用方法
 *  使用rxjava+retrofit进行封装网络请求，封装请求过程为httpMethods对象，并对返回相同格式的Http请求结果数据统一进行预处理
 */

public class MovieModelImpl implements MovieModel<List<MovieSubjectsBean>> {


    @Override
    public void getMovie(int start, int count, Observer<List<MovieSubjectsBean>> observer) {
        HttpMethods.getInstance().getTopMovieByObserver(observer, 0, 10);
    }

}
