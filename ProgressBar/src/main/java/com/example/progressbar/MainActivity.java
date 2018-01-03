package com.example.progressbar;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initHorizontalProgressBar();
        initMyProgressBar();
    }

    private void initHorizontalProgressBar(){
        final ProgressBar bar = (ProgressBar) findViewById(R.id.mProgressBar);
        final TextView textView= (TextView) findViewById(R.id.tv_progressBar);
        new Thread(){
            @Override
            public void run() {
                int i=0;
                while(i<100){
                    i++;
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int j=i;
                    bar.setProgress(i);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("自定义有进度ProgressBar:"+j+"%");
                        }
                    });
                }
            }
        }.start();
    }

    private void initMyProgressBar(){
        final ProgressBar bar = (ProgressBar) findViewById(R.id.mMyProgressBar);
        final TextView textView= (TextView) findViewById(R.id.tv_myProgressBar);
        new Thread(){
            @Override
            public void run() {
                int i=0;
                while(i<100){
                    i++;
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int j=i;
                    bar.setProgress(i);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("自定义有进度ProgressBar:"+j+"%");
                        }
                    });
                }
            }
        }.start();
    }
}

