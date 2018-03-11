package com.hm.mvc.model;

import android.os.SystemClock;

import com.hm.mvc.entity.UserEntity;

/**
 * Created by afinalstone on 17-4-19.
 */

public class UserModelImpl implements UserModel {

    @Override
    public void getUser(final String name, final String password, final OnUserListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                UserEntity userEntity = new UserEntity(name,password);
                listener.onSuccess(userEntity);
            }
        }).start();
    }

}
