package com.example.rxjava_retrofit04.http;


import com.example.rxjava_retrofit04.entity.HttpResult;
import com.example.rxjava_retrofit04.entity.MovieSubjectsBean;

import org.reactivestreams.Subscriber;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by liukun on 16/3/9.
 */
public class HttpMethods {

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    private static final int DEFAULT_TIMEOUT = 5;

    private WebService webService_string;
    private WebService webService_jsonObj;
    //对返回结果进行统一预处理
    private WebService webService_jsonObjExt;

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit_jsonObj = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        webService_jsonObj = retrofit_jsonObj.create(WebService.class);


        Retrofit retrofit_String = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        webService_string = retrofit_String.create(WebService.class);

        Retrofit retrofit_result = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        webService_jsonObjExt = retrofit_result.create(WebService.class);

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
    public void getTopMovieString(Observer<String> observer, int start, int count){

        Observable observable = webService_string.getTopMovieString(start, count);
        toSubscribe(observable, observer);

    }

    /**
     * 用于获取豆瓣电影Top250的数据
     * @param observer  由调用者传过来的观察者对象
     * @param start 起始位置
     * @param count 获取长度
     */
    public void getTopMovieObject(Observer<HttpResult<List<MovieSubjectsBean>>> observer, int start, int count){

        Observable observable = webService_jsonObj.getTopMovieObject(start, count);
        toSubscribe(observable, observer);

    }

    /**
     * 用于获取豆瓣电影Top250的数据
     * @param observer  由调用者传过来的观察者对象
     * @param start 起始位置
     * @param count 获取长度
     */
    public void getTopMovieObjectExt(Observer<List<MovieSubjectsBean>> observer, int start, int count){

        Observable observable = webService_jsonObj.getTopMovieObject(start, count)
                .map(new HttpResultFunc<List<MovieSubjectsBean>>());
        toSubscribe(observable, observer);

    }


    private <T> void toSubscribe(Observable<T> o, Observer<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
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
