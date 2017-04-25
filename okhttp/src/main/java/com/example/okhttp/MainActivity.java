package com.example.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ProgressBar progressBar;
    private OkHttpClient client;
    private Request request;
    private String BASE_URL = "https://api.douban.com/v2/movie/top250?start=0&count=10";
    private String BASE_URL2 = "https://api.github.com/";
    private MyHandler handler = new MyHandler();
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.textView);
    }

    public void onClick(View view){
        textView.setText("正在请求。。。");
        progressBar.setVisibility(View.VISIBLE);
        client = new OkHttpClient();
        Request.Builder builder = new Request.Builder()
                .url(BASE_URL)
                .method("GET", null);

        request = builder.build();
        Call mCall = client.newCall(request);
        mCall.enqueue(new MyCallback());
    }

    private class MyCallback implements Callback {

        @Override
        public void onFailure(Call call, IOException e) {
            Message msg = new Message();
            msg.what = 100;
            msg.obj = e;
            handler.sendMessage(msg);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Message msg = new Message();
            msg.what = 200;
            msg.obj = response.body().string();
            handler.sendMessage(msg);
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressBar.setVisibility(View.GONE);
            switch (msg.what) {
                case 100:
                    Object e = msg.obj;
                    Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case 200:
                    String response = (String) msg.obj;
                    textView.setText(response);
                    break;
                case 300:
                    int percent = msg.arg1;
                    Log.e("llll", "the percent is " + percent);
                    if (percent < 100) {
                        progressBar.setProgress(percent);
                    } else {
                        progressBar.setVisibility(View.GONE);
//                        Glide.with(mContext).load(FILE_PATH).into(imageView);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
