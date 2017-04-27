package com.example.mvp.biz;


import com.example.mvp.bean.User;

/**
 * @author SHI
 * @time 2017/3/27 16:51
 */
public interface OnLoginListener
{
    void loginSuccess(User user);

    void loginFailed();
}
