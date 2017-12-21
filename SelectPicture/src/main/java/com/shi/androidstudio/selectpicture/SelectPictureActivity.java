package com.shi.androidstudio.selectpicture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.shi.androidstudio.selectpicture.view.ImageCropper;
import com.shi.androidstudio.selectpicture.view.ImageCropperHelp;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;

public class SelectPictureActivity extends AppCompatActivity {

    ListView listView;
    List<String> listImageUrl = new ArrayList<>();
    ImageView imageView;
    AdapterListView adapterListView;
    ImageCropper mImageCropper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_picture);

        initOnClick();
        initImageCropperWithTitle();
        imageView = (ImageView) findViewById(R.id.imageView);
        listView = (ListView) findViewById(R.id.listView);
        adapterListView = new AdapterListView();
        listView.setAdapter(adapterListView);

    }

    private void initOnClick(){

        findViewById(R.id.btn_selectPicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(false)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(SelectPictureActivity.this, PhotoPicker.REQUEST_CODE);
            }
        });

        findViewById(R.id.btn_selectPictureByCut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoPicker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(SelectPictureActivity.this, PhotoPicker.REQUEST_CODE+1);
            }
        });

        findViewById(R.id.btn_selectMulPicture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhotoPicker.builder()
                        .setPhotoCount(9)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(true)
                        .start(SelectPictureActivity.this, PhotoPicker.REQUEST_CODE+2);
            }
        });
    }


    private void initImageCropperWithTitle() {
        mImageCropper = ImageCropper.Helper.with(this)
                .setTitle("剪切图片")
                .setTranslucentStatusHeight(ImageCropperHelp.calcStatusBarHeight(this))
                .setCallback(new ImageCropper.Callback() {
                    @Override
                    public void onPictureCropOut(Bitmap bitmap, String tag) {
                        imageView.setImageBitmap(bitmap);
                        try {
                             // 保存图片到本地，记得申请权限
                            ImageCropperHelp.saveBitmapToTargetFile(ImageCropperHelp.getDiskCacheDir(SelectPictureActivity.this,"header"),bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).create();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == PhotoPicker.REQUEST_CODE) {

                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                Log.e("图片地址", photos.get(0));
                try {
                    Uri url = Uri.parse(photos.get(0));
                    imageView.setImageURI(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            else if (requestCode == PhotoPicker.REQUEST_CODE+1) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                mImageCropper.crop(photos.get(0),imageView.getWidth(),imageView.getHeight(),false,"");
            }

            else if (requestCode == PhotoPicker.REQUEST_CODE+2) {
                ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                listImageUrl.clear();
                listImageUrl.addAll(photos);
                adapterListView.notifyDataSetChanged();
            }
        }

    }


    private class AdapterListView extends BaseAdapter{

        @Override
        public int getCount() {
            return listImageUrl.size();
        }

        @Override
        public Object getItem(int position) {
            return listImageUrl.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(SelectPictureActivity.this);
            Uri url = Uri.parse(listImageUrl.get(position));
            imageView.setImageURI(url);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            return imageView;
        }
    }



}
