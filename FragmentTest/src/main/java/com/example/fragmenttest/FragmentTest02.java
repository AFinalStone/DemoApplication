package com.example.fragmenttest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentTest02 extends BaseFragment<MainActivity> {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test02, container, false);
        view.findViewById(R.id.btn_test02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseActivity) getActivity()).addNewFragment( new FragmentTest03(), "Fragment03");
            }
        });
        return view;
    }
}
