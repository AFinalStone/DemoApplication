package com.example.rxjava;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    ImageView imageView;
    EditText editAccount;
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);
        editAccount = (EditText) findViewById(R.id.editAccount);
        editPassword = (EditText) findViewById(R.id.editPassword);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnRxJava01:
                Sample01();
                break;
            case R.id.btnRxJava02:
                Sample02();
                break;
            case R.id.btnRxJava03:
                Sample03();
                break;
            case R.id.btnTest04:
                testOkHttp();
                break;
        }
    }

    private void Sample01() {
        //这里没有使用Schedulers指定具体的操作发生在那个线程中
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable.from(words)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {
                        textView.setText(name);
                    }
                });
    }

    private void Sample02() {
        final int drawableRes = R.mipmap.icon_dragon;
        Observable.create(new Observable.OnSubscribe<Drawable>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void call(Subscriber<? super Drawable> subscriber) {
                Drawable drawable = getTheme().getDrawable(drawableRes);
                subscriber.onNext(drawable);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onNext(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void Sample03() {

//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                String account = editAccount.getText().toString().trim();
//                String password = editPassword.getText().toString().trim();
//                String methodName = "DealerLoginExt";
//                SoapObject soapObject = new SoapObject(ConnectServiceUtil.NAMESPACE,methodName);
//                soapObject.addProperty("phoneNum", account);
//                soapObject.addProperty("passWord", password);
//                soapObject.addProperty("code", "36");
//                SoapObject resultSoapObject = ConnectServiceUtil.connectCustomService(soapObject,10000);
//                subscriber.onNext(resultSoapObject.toString());
//                subscriber.onCompleted();
//            }
//        })
//                .subscribeOn(Schedulers.newThread())// 指定 subscribe() 发生在 IO 线程
//                .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onNext(String drawable) {
//                        SnackBarUtil.show(editAccount,drawable);
//                    }
//
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    private void Sample04() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String account = editAccount.getText().toString().trim();
//                String password = editPassword.getText().toString().trim();
//                String methodName = "DealerLoginExt";
//                SoapObject soapObject = new SoapObject(ConnectServiceUtil.NAMESPACE,methodName);
//                soapObject.addProperty("phoneNum", account);
//                soapObject.addProperty("passWord", password);
//                soapObject.addProperty("code", "36");
//                final SoapObject resultSoapObject = ConnectServiceUtil.connectCustomService(soapObject,10000);
//                Log.e("测试", "run: "+resultSoapObject.toString() );
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        SnackBarUtil.show(editAccount,resultSoapObject.toString());
//                    }
//                });
//            }
//        }).start();
    }


    private void testOkHttp() {

//        CookieJarImpl cookieJar = new CookieJarImpl(new PersistentCookieStore(getApplicationContext()));
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cookieJar(cookieJar)
//                //其他配置
//                .build();
//
//        OkHttpUtils.initClient(okHttpClient);
//        OkHttpUtils.get()
//                .url("http://oaokms.cnki.net/im/home/find")
//                .build()
//                .connTimeOut(3000)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onError(Call call, Exception e, int id) {
//                        Log.d("抓包结果", "onResponse: "+e.getMessage());
//                    }
//
//                    @Override
//                    public void onResponse(String response, int id) {
//                        Log.d("抓包结果", "onResponse: "+response);
//                    }
//                });
    }
}