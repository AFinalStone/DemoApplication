package com.example.vectordrawable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    private AnimatedVectorDrawable mAddToRemoveDrawable;
//    private AnimatedVectorDrawable mRemoveToAddDrawable;
//    private boolean mIsAddState = true;
//    ImageView mAddRemoveImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mAddRemoveImage = (ImageView) findViewById(R.id.image_add_remove);
//        mAddToRemoveDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_add_to_remove);
//        mRemoveToAddDrawable = (AnimatedVectorDrawable) getDrawable(R.drawable.avd_remove_to_add);

    }

//    public void onAddRemoveImageClick(View view) {
//        AnimatedVectorDrawable drawable =   mIsAddState ? mRemoveToAddDrawable : mAddToRemoveDrawable;
//        mAddRemoveImage.setImageDrawable(drawable);
//        drawable.start();
//        mIsAddState = !mIsAddState;
//    }
}
