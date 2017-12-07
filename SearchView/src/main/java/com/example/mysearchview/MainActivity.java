package com.example.mysearchview;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    LinearLayout background;
    private EditText et_search;
    private RecyclerView mRecyclerView;
    private ImageView iv_cancel;
    AllCommonAdapter<String> adapter;
    List<String> listData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initView() {
        background = (LinearLayout) findViewById(R.id.background);
        et_search = (EditText) findViewById(R.id.et_search);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        iv_cancel = (ImageView) findViewById(R.id.iv_cancel);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.e("Main", "afterTextChanged: ");
                String result = s.toString();
                if (result != null && !result.isEmpty()) {
                    background.setBackgroundColor(0x77000000);
                    if(result.equals("111")){
                        listData.add("1111");
                    }
                    if(result.equals("2")){
                        listData.add("22");
                        listData.add("22222");
                    }
                    if(result.equals("A")){
                        listData.add("AFinalStone");
                        listData.add("ABookLike");
                    }
                    if(result.equals("5")){
                        listData.add("5555555555555");
                        listData.add("151515");
                    }
                    adapter.notifyDataSetChanged();
                }else{
                    clearRecord();
                }
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_search.setText("");

            }
        });
    }

    private void initData() {

        adapter = new AllCommonAdapter<String>(this, R.layout.item_adapter_general, listData) {
            @Override
            public void convert(ViewHolder holder, final String myBean) {
                holder.setText(R.id.textView_context, myBean);
                holder.setOnClickListener(R.id.textView_context, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Snackbar.make(mRecyclerView,myBean,Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        };
        mRecyclerView.setAdapter(adapter);
    }


    private void clearRecord(){
        listData.clear();
        adapter.notifyDataSetChanged();
        background.setBackgroundColor(Color.WHITE);
    }


}
