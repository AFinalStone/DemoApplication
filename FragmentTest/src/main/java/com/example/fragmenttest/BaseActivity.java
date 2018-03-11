package com.example.fragmenttest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * Created by AFinalStone on 2018/3/9.
 */

public class BaseActivity extends RxAppCompatActivity {


    protected void addNewFragment(BaseFragment newFragment, String fragmentName) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        Fragment fromFragment = fm.findFragmentById(R.id.container);
        if (fromFragment != null) {
            tx.hide(fromFragment);
        }
        tx.add(R.id.container, newFragment, fragmentName);
        tx.addToBackStack(null);
        tx.commit();
    }

    protected void initFragment(BaseFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.container, fragment);
        tx.commit();
    }

    protected void initFragment(BaseFragment fragment, String fragmentName) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tx = fm.beginTransaction();
        tx.add(R.id.container, fragment, fragmentName);
        tx.commit();
    }


}
