package com.example.administrator.myapplication;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.shi.androidstudy.widget.zxing.activity.CaptureFragment;
import com.shi.androidstudy.widget.zxing.activity.CodeUtils;
import com.shi.androidstudy.widget.zxing.view.ViewfinderView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private CaptureFragment captureFragment;
    ViewfinderView viewfinder_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxPermissions rxPermissions = new RxPermissions(this);

        setContentView(R.layout.activity_main);
        rxPermissions.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                if(aBoolean){
                    initView();
                }
            }
        });

    }

    public static boolean isOpen = false;

    private void initView() {
        captureFragment = new CaptureFragment();
        // 为二维码扫描界面设置定制化界面
        CodeUtils.setFragmentArgs(captureFragment, R.layout.my_camera);
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_my_container, captureFragment).commit();
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear1);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOpen) {
                    captureFragment.start();
                    CodeUtils.isLightEnable(true);
                    isOpen = true;
                } else {
                    captureFragment.stop();
                    CodeUtils.isLightEnable(false);
                    isOpen = false;
                }

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
//        viewfinder_view = (ViewfinderView) captureFragment.getView().findViewById(R.id.viewfinder_view);
//        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewfinder_view.stopScanLight();
//                viewfinder_view.setBackgroundColor(Color.BLACK);
//                captureFragment.stop();
//            }
//        });
//        findViewById(R.id.text).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewfinder_view.startScanLight();
//                viewfinder_view.setBackgroundColor(Color.TRANSPARENT);
//                captureFragment.start();
//
//            }
//        });
    }


    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_SUCCESS);
//            bundle.putString(CodeUtils.RESULT_STRING, result);
//            resultIntent.putExtras(bundle);
//            SecondActivity.this.setResult(RESULT_OK, resultIntent);
//            SecondActivity.this.finish();
            Snackbar.make(captureFragment.getView(),result, Snackbar.LENGTH_SHORT).show();
        }

        @Override
        public void onAnalyzeFailed() {
//            Intent resultIntent = new Intent();
//            Bundle bundle = new Bundle();
//            bundle.putInt(CodeUtils.RESULT_TYPE, CodeUtils.RESULT_FAILED);
//            bundle.putString(CodeUtils.RESULT_STRING, "");
//            resultIntent.putExtras(bundle);
//            SecondActivity.this.setResult(RESULT_OK, resultIntent);
//            SecondActivity.this.finish();
            Snackbar.make(captureFragment.getView(),"加载失败", Snackbar.LENGTH_SHORT).show();

        }
    };
}
