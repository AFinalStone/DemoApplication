package com.example.afinalstone.demoapplication.model;


import com.example.afinalstone.demoapplication.entity.UserBean;

/**
 * Created by afinalstone on 17-4-18.
 */

public interface UserOnListener {

    void onSuccess(UserBean userBean);

    void onError();
}
