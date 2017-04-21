package com.example.mvc.model;

/**
 * Created by afinalstone on 17-4-18.
 */

public interface UserModel {

    void getUser(String name, String password, OnUserListener listener);

}
