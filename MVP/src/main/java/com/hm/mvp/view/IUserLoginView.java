package com.hm.mvp.view;

import com.hm.mvp.bean.User;

/**
 * @author SHI
 * @time 2017/3/27 16:51
 */
public interface IUserLoginView
{
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();

}
