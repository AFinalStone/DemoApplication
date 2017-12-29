package com.example.administrator.myapplication;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.example.administrator.myapplication.scaner.CameraManager;
import com.example.administrator.myapplication.scaner.CaptureActivityHandler;
import com.example.administrator.myapplication.scaner.decoding.InactivityTimer;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class MainActivity extends AppCompatActivity {

    private InactivityTimer inactivityTimer;
    private CaptureActivityHandler handler;//扫描处理
    private View mCaptureRoot = null;//跟布局
    private View mCaptureScanLine = null;//扫描线条
    private View mCropLayout = null;//扫描框根布局
    private View linearLayout_pauseTip = null;//扫描框根布局
    private SurfaceView surfaceView;
//    private int mCropWidth = 0;//扫描边界的宽度
//    private int mCropHeight = 0;//扫描边界的高度
    private boolean hasSurface = false;//是否有预览
    private boolean vibrate = true;//扫描成功后是否震动
    private boolean mFlashing = true;//闪光灯开启状态
    private Activity mContext;
    ObjectAnimator objAnim;


    public static final int GET_IMAGE_FROM_ALBUM = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        initView();//界面控件初始化
        CameraManager.init(mContext);//初始化 CameraManager
        inactivityTimer = new InactivityTimer(this);
    }

    private void initView() {
        mCaptureRoot = findViewById(R.id.capture_root);
        mCropLayout = findViewById(R.id.capture_crop_layout);
        mCaptureScanLine = findViewById(R.id.capture_scan_line);
        surfaceView = (SurfaceView) findViewById(R.id.capture_preview);
        linearLayout_pauseTip = findViewById(R.id.linearLayout_pauseTip);
        //请求Camera权限 与 文件读写 权限
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
   protected void onResume() {
        super.onResume();
        CameraPlay();
        ScalePlay();//扫描动画初始化
    }

    @Override
    protected void onPause() {
        super.onPause();
        CameraPause();
        ScalePause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
//        mScanerListener = null;
        super.onDestroy();
    }


    public void ScalePlay() {
        if (objAnim == null) {
            objAnim = ObjectAnimator.ofFloat(mCaptureScanLine, "translationY", 0f, DisplayUtil.dip2px(this, 219), 0f);
            objAnim.setInterpolator(new LinearInterpolator());
            objAnim.setRepeatMode(ObjectAnimator.RESTART);
            objAnim.setDuration(4000);
            objAnim.setRepeatCount(-1);
        }
        mCaptureScanLine.setVisibility(View.VISIBLE);
        linearLayout_pauseTip.setVisibility(View.INVISIBLE);
        objAnim.start();
    }

    public void ScalePause() {
        objAnim.cancel();
        mCaptureScanLine.setVisibility(View.INVISIBLE);
        linearLayout_pauseTip.setVisibility(View.VISIBLE);
        surfaceView.setVisibility(View.VISIBLE);
    }


    public void CameraPlay() {

        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);//Camera初始化
        } else {
            surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (!hasSurface) {
                        hasSurface = true;
                        initCamera(holder);
                    }
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    hasSurface = false;
                }
            });
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
    }

    public void CameraPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_actionOpenAlbum:
                openLocalAlbum(mContext);
                break;
            case R.id.iv_actionOpenFlashlight:
                light();
                break;
            case R.id.tv_pause:
                ScalePause();
                CameraPause();
                break;
            case R.id.linearLayout_pauseTip:
            case R.id.tv_restart:
                ScalePlay();
                CameraPlay();
                break;
        }

    }

    public void openLocalAlbum(final Activity activity) {
        //请求Camera权限 与 文件读写 权限
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(intent, GET_IMAGE_FROM_ALBUM);
        }
    }

    private void light() {
        if (mFlashing) {
            mFlashing = false;
            // 开闪光灯
            CameraManager.get().openLight();
        } else {
            mFlashing = true;
            // 关闪光灯
            CameraManager.get().offLight();
        }

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
            Point point = CameraManager.get().getCameraResolution();
            AtomicInteger width = new AtomicInteger(point.y);
            AtomicInteger height = new AtomicInteger(point.x);
            int cropWidth = mCropLayout.getWidth() * width.get() / mCaptureRoot.getWidth();
            int cropHeight = mCropLayout.getHeight() * height.get() / mCaptureRoot.getHeight();
            CameraManager.FRAME_WIDTH = cropWidth;
            CameraManager.FRAME_HEIGHT = cropHeight;
        } catch (IOException | RuntimeException ioe) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this);
        }
    }
    //========================================打开本地图片识别二维码 end=================================

    //--------------------------------------打开本地图片识别二维码 start---------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == GET_IMAGE_FROM_ALBUM) {
            ContentResolver resolver = getContentResolver();
            // 照片的原始资源地址
            Uri originalUri = data.getData();
            try {
                // 使用ContentProvider通过URI获取原始图片
                Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);

                // 开始对图像资源解码
                Result rawResult = QrCodeTool.decodeFromPhoto(photo);
                if (rawResult != null) {
                    showMsg("----扫描成功：" + rawResult.getText());
                } else {
                    showMsg("----扫描失败：");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void handleDecode(Result result) {
        inactivityTimer.onActivity();
        PlayBeepTool.playBeep(mContext, vibrate);//扫描成功之后的振动与声音提示
        String result1 = result.getText();
        Log.v("二维码/条形码 扫描结果", result1);
        showMsg(result1);
    }

    public Handler getHandler() {
        return handler;
    }


    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
