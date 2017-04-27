package com.example.mvc.model;

import com.example.mvc.entity.UserEntity;

/**
 * Created by afinalstone on 17-4-18.
 */

public interface OnUserListener {

    void onSuccess(UserEntity weather);

    void onError();
}
