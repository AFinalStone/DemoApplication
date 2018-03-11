package com.hm.rxjava_retrofit04.controller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hm.rxjava_retrofit01.R;
import com.hm.rxjava_retrofit04.entity.HttpResult;
import com.hm.rxjava_retrofit04.entity.MovieSubjectsBean;
import com.hm.rxjava_retrofit04.model.MovieModel;
import com.hm.rxjava_retrofit04.model.MovieModelImpl;
import com.hm.rxjava_retrofit04.util.LogUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {


    private TextView tv_content;
    private MovieModel movieModel = new MovieModelImpl();

    ProgressDialog progressDialog;
    Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("rxjava+retrofit04");
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText("网络请求");
        Button button = new Button(this);
        button.setText("取消网络请求");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.hide();
                if(disposable != null && !disposable.isDisposed()){
                    disposable.dispose();
                }
            }
        });
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        addContentView(button, layoutParams);
        progressDialog = new ProgressDialog(this);

    }

    public void onClick(View view) {
        getMovieInfo();
    }

    private void getMovieInfo() {
        progressDialog.show();
        movieModel.getMovieString(0, 10, new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(String value) {
                LogUtil.e("getMovieString",value);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "onComplete被执行", Toast.LENGTH_SHORT).show();
                progressDialog.hide();
                //重新开始新的请求
                progressDialog.show();
                movieModel.getMovieObject(0, 10, new Observer<HttpResult<List<MovieSubjectsBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(HttpResult<List<MovieSubjectsBean>> value) {
                        LogUtil.e("getMovieObject", value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, "onComplete被执行", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();

                        //重新开始新的请求
                        movieModel.getMovieObjectExt(0, 10, new Observer<List<MovieSubjectsBean>>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable = d;
                            }

                            @Override
                            public void onNext(List<MovieSubjectsBean> value) {
                                LogUtil.e("getMovieObjectExt",value.toString());
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                            }

                            @Override
                            public void onComplete() {

                                Toast.makeText(MainActivity.this, "onComplete被执行", Toast.LENGTH_SHORT).show();
                                progressDialog.hide();
                            }
                        });
                    }
                });
            }
        });


    }
}
