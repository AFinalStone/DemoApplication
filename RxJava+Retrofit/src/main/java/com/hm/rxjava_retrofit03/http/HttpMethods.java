package com.hm.rxjava_retrofit03.http;


import com.hm.rxjava_retrofit03.entity.HttpResult;
import com.hm.rxjava_retrofit03.entity.MovieSubjectsBean;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by liukun on 16/3/9.
 * 使用rxjava+retrofit进行封装网络请求，封装请求过程为httpMethods对象，并对返回相同格式的Http请求结果数据统一进行预处理
 */
public class HttpMethods {

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private MovieService movieService;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                //对http请求结果进行统一的预处理 GSonResponseBodyConvert
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieService = retrofit.create(MovieService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 用于获取豆瓣电影Top250的数据
     * @param observer  由调用者传过来的观察者对象
     * @param start 起始位置
     * @param count 获取长度
     */
    public void getTopMovieByObserver(Observer<List<MovieSubjectsBean>> observer, int start, int count){

        movieService.getTopMovie(start, count)
                .map(new HttpResultFunc<List<MovieSubjectsBean>>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T>   Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Function<HttpResult<T>, T> {

        @Override
        public T apply(HttpResult<T> tHttpResult) throws Exception {
            if (tHttpResult.getCount() == 0) {
                throw new RuntimeException("请求信息错误");
            }
            return tHttpResult.getSubjects();
        }
    }

}
