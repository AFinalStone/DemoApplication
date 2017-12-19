package com.example.opencameraandpicture;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    ImageView iv_photo;
    private Uri imageUri;
    private File fileUri = new File(Environment.getExternalStorageDirectory().getPath() + "/photo.jpg");
//    private File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/crop_photo.jpg");
    private static final int CODE_CAMERA_REQUEST = 0xa1;//相机
    private static final int CODE_GALLERY_REQUEST = 0xa0;//相册
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 0x03;//相机权限
    private static final int STORAGE_PERMISSIONS_REQUEST_CODE = 0x04;// 相册权限

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_photo = findViewById(R.id.iv_photo);
    }


    public void onTakePhoto(View view) {
        autoObtainCameraPermission();
    }

    public void onTakeGallery(View view) {
        autoObtainStoragePermission();
    }

    private void showMsg(String msg) {
        Snackbar.make(iv_photo, msg, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * 自动获取相机权限
     */
    private void autoObtainCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                showMsg("您已经拒绝过一次");
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {//有权限直接调用系统相机拍照
            openCamera();
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 自动获取sdk权限
     */

    private void autoObtainStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSIONS_REQUEST_CODE);
        } else {
            openPicture();
        }
    }

    //打开相机
    private void openCamera() {
        if (hasSdcard()) {
            imageUri = Uri.fromFile(fileUri);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            {
                imageUri = FileProvider.getUriForFile(MainActivity.this, "com.opencameraandpicture.fileprovider", fileUri);//通过FileProvider创建一个content类型的Uri
            }
            PhotoUtils.takePicture(this, imageUri, CODE_CAMERA_REQUEST);
        } else {
            showMsg("设备没有SD卡！");
        }
    }


    //打开相册
    private void openPicture() {
        if (hasSdcard()) {
            PhotoUtils.openPic(this, CODE_GALLERY_REQUEST);
        } else {
            showMsg("设备没有SD卡！");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            //调用系统相机申请拍照权限回调
            case CAMERA_PERMISSIONS_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    showMsg("请允许打开相机！！");
                }
                break;

            }
            //调用系统相册申请Sdcard权限回调
            case STORAGE_PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openPicture();
                } else {
                    showMsg("请允许操作SDCard！！");
                }
                break;
            default:
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                //拍照完成回调
                case CODE_CAMERA_REQUEST:
                    imageUri = Uri.fromFile(fileUri);
                    Bitmap bitmap = PhotoUtils.getBitmapFromUri(imageUri, this);
                    iv_photo.setImageBitmap(bitmap);
                    break;
                //访问相册完成回调
                case CODE_GALLERY_REQUEST:
                    Uri newUri = Uri.parse(PhotoUtils.getPath(this, data.getData()));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        newUri = FileProvider.getUriForFile(this, "com.opencameraandpicture.fileprovider", new File(newUri.getPath()));
                    }
                    bitmap = PhotoUtils.getBitmapFromUri(newUri, this);
                    iv_photo.setImageBitmap(bitmap);
                    break;
                default:
            }
        }
    }
}
