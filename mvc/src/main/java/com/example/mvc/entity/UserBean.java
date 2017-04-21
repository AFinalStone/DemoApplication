package com.example.mvc.entity;

import java.util.List;

/**
 * Created by afinalstone on 17-4-18.
 */

public class UserBean {

    private String name;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public UserBean(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }
}
