package com.example.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.qqtheme.framework.picker.DateTimePicker;

public class AndroidPickerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_picker);
    }

    public void OnShowDateTimePicker(View view) {

        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);//24小时值
        picker.setDateRangeStart(2017, 1, 1);//日期起点
        picker.setDateRangeEnd(2020, 1, 1);//日期终点
        picker.setTimeRangeStart(0, 0);//时间范围起点
        picker.setTimeRangeEnd(23, 59);//时间范围终点
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                //year:年，month:月，day:日，hour:时，minute:分
                Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day + " "
                        + hour + ":" + minute, Toast.LENGTH_LONG).show();
            }
        });
        picker.show();
    }

    public void OnShowDateTimePicker02(View view) {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = new Date(date.getTime());
                String time = sm.format(d);
                Toast.makeText(getApplicationContext(),time, Toast.LENGTH_LONG).show();
            }
        }).build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }
}
