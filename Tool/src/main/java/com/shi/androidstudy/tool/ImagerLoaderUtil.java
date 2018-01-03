package com.shi.androidstudy.tool;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by SHI on 2016/9/10 15:36
 */
public class ImagerLoaderUtil {

    private Picasso picasso;
    private Context context;
    private static ImagerLoaderUtil imagerLoaderUtil;

    public ImagerLoaderUtil(Context context) {
        this.context = context;
    }

    public synchronized static ImagerLoaderUtil getInstance(Context context) {
        if (imagerLoaderUtil == null) {
            imagerLoaderUtil = new ImagerLoaderUtil(context);
            imagerLoaderUtil.initImageLoader();
        }
        return imagerLoaderUtil;
    }

    public void displayMyImage(String imageUrl, ImageView imageView) {
        picasso.load(imageUrl).into(imageView);
    }

    public void displayMyImage(Uri imageUrl, ImageView imageView) {
        picasso.load(imageUrl).into(imageView);
    }

    public void displayMyImage(File file, ImageView imageView) {
        picasso.load(file).into(imageView);
    }

    public void displayMyImage(String imageUrl, ImageView imageView, Callback callback) {
        picasso.load(imageUrl).into(imageView, callback);
    }

    public void displayMyImage(int resId, ImageView imageView) {
        picasso.load(resId).into(imageView);
    }


    private void initImageLoader() {
        picasso = Picasso.with(context);
    }
}
