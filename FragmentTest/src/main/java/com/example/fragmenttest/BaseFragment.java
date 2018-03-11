package com.example.fragmenttest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

/**
 * Created by AFinalStone on 2018/3/9.
 */

public class BaseFragment<T extends BaseActivity> extends RxFragment {

    T mActivity;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}
