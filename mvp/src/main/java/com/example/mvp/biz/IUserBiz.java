package com.example.mvp.biz;

/**
 * @author SHI
 * @time 2017/3/27 16:51
 */
public interface IUserBiz
{
    public void login(String username, String password, OnLoginListener loginListener);
}
