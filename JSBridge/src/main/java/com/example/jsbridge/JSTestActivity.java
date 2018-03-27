package com.example.jsbridge;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;

import java.util.HashMap;

/****
 * 使用jsbridge库来实现  Android端与Html+Js之间的交互
 */
public class JSTestActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = "JsBridge";
    private BridgeWebView bridgeWebView;
    boolean ifHaveGetChaHuaData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.button).setOnClickListener(this);
        bridgeWebView = findViewById(R.id.webView);
        bridgeWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JSTestActivity.this);
                AlertDialog alertDialog = builder.create();
                alertDialog.setMessage(message);
                alertDialog.show();
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100 && !ifHaveGetChaHuaData) {
                    ifHaveGetChaHuaData = true;
                    initData();
                }
            }
        });
        bridgeWebView.loadUrl("file:///android_asset/FunIOU/funiou/FunEdit.html");

    }

    private void initData() {
        String path = "detail";
        HashMap<String, String> hashMap02 = new HashMap<>();
        hashMap02.put("borrowerName", "李大伟");
        hashMap02.put("loanerName", "黄药师");
        hashMap02.put("thinesName", "飞机");
        hashMap02.put("todo", "吃饭");
        Gson gson = new Gson();
        String msg02 = gson.toJson(hashMap02);
        String function = "javascript:judgeWayByApp('" + path + "','" + msg02 + "')";
        bridgeWebView.loadUrl(function);
    }


    @Override
    public void onClick(View v) {

    }
}
