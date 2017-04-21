package com.example.afinalstone.demoapplication.model;

import android.os.SystemClock;

import com.example.afinalstone.demoapplication.entity.UserBean;


/**
 * Created by afinalstone on 17-4-19.
 */

public class UserModelImpl implements UserModel {

    @Override
    public void getUser(final String name, final String password, final UserOnListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(3000);
                UserBean userBean = new UserBean(name,password);
                listener.onSuccess(userBean);
            }
        }).start();
    }

}
