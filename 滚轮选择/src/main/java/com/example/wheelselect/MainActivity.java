package com.example.wheelselect;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.wheelselect.activity.SelectMulActivity;
import com.example.wheelselect.activity.SelectSingleActivity;
import com.example.wheelselect.wheel.widget.OnWheelChangedListener;
import com.example.wheelselect.wheel.widget.WheelView;
import com.example.wheelselect.wheel.widget.adapters.BaseWheelTextAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onJumpToSelectCity(View view) {
        startActivity(new Intent(this, SelectMulActivity.class));
    }

    public void onJumpToSelectMonthDay(View view) {
        startActivity(new Intent(this, SelectSingleActivity.class));
    }
}
