package com.hm.calendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class NativeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native);
    }

    public void OnShowDatePicker(View view) {
        //日历控件
        DatePickerDialog dp = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int iyear, int monthOfYear, int dayOfMonth) {
                long maxDate = datePicker.getMaxDate();//日历最大能设置的时间的毫秒值
                int year = datePicker.getYear();//年
                int month = datePicker.getMonth();//月-1
                int dayOfMonth1 = datePicker.getDayOfMonth();//日
                //iyear:年，monthOfYear:月-1，dayOfMonth:日
                Toast.makeText(getApplicationContext(), iyear + ":" + (monthOfYear + 1) + ":" + dayOfMonth, Toast.LENGTH_LONG).show();
            }
        }, 2013, 2, 1);//2013:初始年份，2：初始月份-1 ，1：初始日期
        dp.show();
    }

    public void OnTimePicker(View view) {
        //钟表控件
        TimePickerDialog tp = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int iminute) {
                /*int minute = timePicker.getMinute();//分钟
                int hour = timePicker.getHour();//小时*/
                //hourOfDay：小时，iminute分钟
                Toast.makeText(getApplicationContext(), hourOfDay + ":" + iminute, Toast.LENGTH_LONG).show();
            }
        }, 12, 0, true);//12：钟表初始小时数，0：钟表初始分钟数
        tp.show();//记得show一下
    }
}
