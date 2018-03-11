package com.example.wheelselect.activity;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.wheelselect.AddressSelectModel;
import com.example.wheelselect.R;
import com.example.wheelselect.wheel.widget.OnWheelChangedListener;
import com.example.wheelselect.wheel.widget.WheelView;
import com.example.wheelselect.wheel.widget.adapters.BaseWheelTextAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SelectSingleActivity extends AppCompatActivity implements OnWheelChangedListener {
    /**
     * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
     */
    private List<String> listData;
    /**
     * 编号number
     */
    private WheelView wheelViewNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_single);
        listData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            listData.add("" + i);
        }
    }


    private void showSelectSingleView() {
        View selectAddressView = View.inflate(this, R.layout.dialog_select_single, null);
        wheelViewNumber = selectAddressView.findViewById(R.id.id_number);

        wheelViewNumber.setViewAdapter(new MyWheelAdapter(this, listData));
        // 添加change事件
        wheelViewNumber.addChangingListener(this);
        wheelViewNumber.setVisibleItems(7);
        wheelViewNumber.setCurrentItem(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(selectAddressView);
        builder.create().show();
    }


    /**
     * change事件的处理
     */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
    }


    public void onSelectNumber(View view) {
        showSelectSingleView();
    }

    private class MyWheelAdapter extends BaseWheelTextAdapter<String> {

        public MyWheelAdapter(Context context, List<String> listData) {
            super(context, listData);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return listData.get(index);
        }
    }
}
