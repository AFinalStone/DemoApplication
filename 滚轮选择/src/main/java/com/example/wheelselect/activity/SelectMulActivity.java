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
import java.util.List;

public class SelectMulActivity extends AppCompatActivity implements OnWheelChangedListener {
    /**
     * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
     */
    private List<AddressSelectModel> listData_All;
    private List<AddressSelectModel> listData_Second;
    private List<AddressSelectModel> listData_Third;

    private AddressSelectModel selectCurrentFirstPosition;
    private AddressSelectModel selectCurrentSecondPosition;
    private AddressSelectModel selectCurrentThirdPosition;
    /**
     * 省的WheelView控件
     */
    private WheelView mProvince;
    /**
     * 市的WheelView控件
     */
    private WheelView mCity;
    /**
     * 区的WheelView控件
     */
    private WheelView mArea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mul);
        initSelectAddressData();
    }


    private void initSelectAddressData() {
        AssetManager am = getAssets();
        Gson gson = new Gson();
        try {
            InputStream is = am.open("json_address_select");
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            listData_All = gson.fromJson(sb.toString(), new TypeToken<List<AddressSelectModel>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showSelectAddressView() {
        View selectAddressView = View.inflate(this, R.layout.dialog_select_mul, null);
        mProvince = (WheelView) selectAddressView.findViewById(R.id.id_province);
        mCity = (WheelView) selectAddressView.findViewById(R.id.id_city);
        mArea = (WheelView) selectAddressView.findViewById(R.id.id_area);
        mProvince.setViewAdapter(new MyWheelAdapter(this, listData_All));
        // 添加change事件
        mProvince.addChangingListener(this);
        // 添加change事件
        mCity.addChangingListener(this);
        // 添加change事件
        mArea.addChangingListener(this);
        mProvince.setVisibleItems(7);
        mCity.setVisibleItems(7);
        mArea.setVisibleItems(7);
        mProvince.setCurrentItem(0);
        updateCities();
        updateAreas();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(selectAddressView);
        builder.create().show();
    }


    /**
     * change事件的处理
     */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mProvince) {
            updateCities();
        } else if (wheel == mCity) {
            updateAreas();
        } else if (wheel == mArea) {
            selectCurrentThirdPosition = listData_Third.get(newValue);
        }
    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mCity.getCurrentItem();
        selectCurrentSecondPosition = listData_Second.get(pCurrent);
        listData_Third = selectCurrentSecondPosition.getSubChinaCity();
        mArea.setViewAdapter(new MyWheelAdapter(this, listData_Third));
        mArea.setCurrentItem(0);
        if (listData_Third != null && listData_Third.size() != 0) {
            selectCurrentThirdPosition = listData_Third.get(0);
        }
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mProvince.getCurrentItem();
        selectCurrentFirstPosition = listData_All.get(pCurrent);
        listData_Second = selectCurrentFirstPosition.getSubChinaCity();
        mCity.setViewAdapter(new MyWheelAdapter(this, listData_Second));
        mCity.setCurrentItem(0);
        updateAreas();
    }

    public void onSelectCity(View view) {
        showSelectAddressView();
    }


    private class MyWheelAdapter extends BaseWheelTextAdapter<AddressSelectModel> {

        public MyWheelAdapter(Context context, List<AddressSelectModel> listData) {
            super(context, listData);
        }

        @Override
        protected CharSequence getItemText(int index) {
            return listData.get(index).getName();
        }
    }
}
