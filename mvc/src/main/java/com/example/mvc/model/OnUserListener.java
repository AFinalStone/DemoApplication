package com.example.mvc.model;

import com.example.mvc.entity.UserBean;

/**
 * Created by afinalstone on 17-4-18.
 */

public interface OnUserListener {

    void onSuccess(UserBean weather);

    void onError();
}
