package com.afinalstone.androidstudy.dagger2.demo02;

import com.afinalstone.androidstudy.dagger2.demo01.User;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

@Singleton
@Component(modules = {ApiModule.class})
public interface AppComponent {

    OkHttpClient getClient();

    Retrofit getRetrofit();

    User getUser();
}