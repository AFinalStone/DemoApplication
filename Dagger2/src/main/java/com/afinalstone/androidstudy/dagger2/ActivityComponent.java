package com.afinalstone.androidstudy.dagger2;

import dagger.Component;

@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(DaggerActivity daggerActivity);
}