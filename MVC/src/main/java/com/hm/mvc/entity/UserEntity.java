package com.hm.mvc.entity;

/**
 * Created by afinalstone on 17-4-18.
 */

public class UserEntity {

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

    public UserEntity(String name, String sex) {
        this.name = name;
        this.sex = sex;
    }
}
