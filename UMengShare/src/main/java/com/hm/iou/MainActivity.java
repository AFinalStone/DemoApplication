package com.hm.iou;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {


    public static String text = "欢迎使用【、友盟+】社会化组件U-Share，SDK包最小，集成成本最低，助力您的产品开发、运营与推广";

    String TAG = "友盟分享";

    public static String IMAGELOGO_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void onClick(View view) {
//        ShareQQ(R.mipmap.ic_launcher);
//        ShareText();
//        Bitmap bitmap = BitmapUtil.decodeBitmap(this, R.mipmap.ic_launcher);
//        ShareWeb(bitmap);
//        ShareImage(R.mipmap.ic_launcher);
//        ShareImage(R.mipmap.ic_launcher);
//        ShareWeb();
        ShareWebByMobileFile();
    }

    private void ShareText() {
//        new ShareAction(MainActivity.this).withText("hello")
//                .setDisplayList(SHARE_MEDIA.SINA
//                        , SHARE_MEDIA.QQ
//                        , SHARE_MEDIA.WEIXIN_CIRCLE
//                        , SHARE_MEDIA.WEIXIN
//                        , SHARE_MEDIA.SMS
//                        , SHARE_MEDIA.EMAIL)
//                .setCallback(new UMShareListener() {
//                    @Override
//                    public void onStart(SHARE_MEDIA share_media) {
//                        Log.e("onStart", "onStart");
//                    }
//
//                    @Override
//                    public void onResult(SHARE_MEDIA share_media) {
//                        Log.e("onResult", "onResult");
//                    }
//
//                    @Override
//                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
//                        Log.e("onError", "onError");
//                    }
//
//                    @Override
//                    public void onCancel(SHARE_MEDIA share_media) {
//                        Log.e("onCancel", "onCancel");
//                    }
//                }).open();
        new ShareAction(this).withText("友盟测试")
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(null).share();
    }

    //图片放在外网进行web分享
    private void ShareWebByWebPicUrl() {
        UMImage thumb = new UMImage(this, "http://img1.imgtn.bdimg.com/it/u=594559231,2167829292&fm=27&gp=0.jpg");
        UMWeb web = new UMWeb("http://www.baidu.com");
        web.setThumb(thumb);
        web.setDescription("描述信息");
        web.setTitle("借条管家");
        new ShareAction(this).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {

            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Log.e(TAG, "onError: " + throwable.getMessage());
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {

            }
        }).share();
    }

    //图片放在assets里面进行文件分享
    private void ShareWebByMobileFile() {
        InputStream is = null;
        try {
            is = getAssets().open("weidian.png");
            is.available();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap pic = BitmapFactory.decodeStream(is);
        UMImage thumb = new UMImage(this, pic);
        UMWeb web = new UMWeb("http://www.baidu.com");
        web.setThumb(thumb);
        web.setDescription("描述");
        web.setTitle("借条管家");
        new ShareAction(this).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(null).share();
    }

    private void ShareWeb(Bitmap thumb_img) {
        UMImage thumb = new UMImage(this, thumb_img);
        UMWeb web = new UMWeb("https://www.baidu.com");
        web.setThumb(thumb);
        web.setDescription("描述信息");
        web.setTitle("借条管家");
        new ShareAction(this).withMedia(web).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(null).share();
    }

    private void ShareImage(int image) {
        UMImage pic = new UMImage(this, image);
        pic.setThumb(new UMImage(this, image));
        new ShareAction(this).withMedia(pic).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(new UMShareListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.e("onStart", "onStart");
            }

            @Override
            public void onResult(SHARE_MEDIA share_media) {
                Log.e("onResult", "onResult");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                Log.e("onError", "onError");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                Log.e("onCancel", "onCancel");
            }
        }).withText("借条管家").share();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

}
