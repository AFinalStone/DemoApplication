package com.hm.photogallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements XGallery.OnGalleryPageSelectListener {

    XGallery xGallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xGallery = findViewById(R.id.xgallery);
        xGallery.setAdapter(new SampleAdapter());
        xGallery.setOnGalleryPageSelectListener(this);
    }

    @Override
    public void onGalleryPageSelected(int position) {
        Toast.makeText(this, "selected item: " + position, Toast.LENGTH_SHORT).show();
    }

}
