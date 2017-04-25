package com.example.sqlite;

/**
 * Created by afinalstone on 17-4-24.
 */

public class UserBean {

    private String age;
    private String userName;

    public UserBean(String age, String userName) {
        this.age = age;
        this.userName = userName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "age='" + age + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
