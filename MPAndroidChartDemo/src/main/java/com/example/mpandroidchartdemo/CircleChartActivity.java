package com.example.mpandroidchartdemo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import com.shi.androidstudy.view.charting.animation.Easing;
import com.shi.androidstudy.view.charting.charts.PieChart;
import com.shi.androidstudy.view.charting.components.Legend;
import com.shi.androidstudy.view.charting.data.Entry;
import com.shi.androidstudy.view.charting.data.PieData;
import com.shi.androidstudy.view.charting.data.PieDataSet;
import com.shi.androidstudy.view.charting.data.PieEntry;
import com.shi.androidstudy.view.charting.formatter.PercentFormatter;
import com.shi.androidstudy.view.charting.highlight.Highlight;
import com.shi.androidstudy.view.charting.interfaces.datasets.IDataSet;
import com.shi.androidstudy.view.charting.listener.OnChartValueSelectedListener;
import com.shi.androidstudy.view.charting.utils.ColorTemplate;
import com.shi.androidstudy.view.charting.utils.MPPointF;

import java.util.ArrayList;

/**
 * @anthor AFinalStone
 * email:602392033@qq.com
 * @time 2017/6/2
 * description:扇形图表的使用
 * 项目API介绍:https://jitpack.io/com/github/PhilJay/MPAndroidChart/v3.0.2/javadoc/
 */
public class CircleChartActivity extends BaseActivity implements OnChartValueSelectedListener, SeekBar.OnSeekBarChangeListener {

    private PieChart mChart;
    private SeekBar mSeekBarX, mSeekBarY;
    private TextView tvX, tvY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_circle);

        tvX = (TextView) findViewById(R.id.tvXMax);
        tvY = (TextView) findViewById(R.id.tvYMax);

        mSeekBarX = (SeekBar) findViewById(R.id.seekBar1);
        mSeekBarY = (SeekBar) findViewById(R.id.seekBar2);
        mSeekBarX.setProgress(4);
        mSeekBarY.setProgress(10);

		mSeekBarX.setOnSeekBarChangeListener(this);
        mSeekBarY.setOnSeekBarChangeListener(this);
		
        mChart = (PieChart) findViewById(R.id.chart1);
        //显示白分布
        mChart.setUsePercentValues(true);
        //设置图表名称信息以及名称描述信息位置
        mChart.getDescription().setText("测试饼图");
        mChart.getDescription().setPosition(100,100);
        //设置图表外，布局内显示的偏移量
        mChart.setExtraOffsets(5, 10, 5, 5);
        //设置是否隐藏饼图上每个部分的文字，只显示每个部分百分比
        mChart.setDrawEntryLabels(true);
        //设置饼图上面的文字颜色,样式和大小
        mChart.setEntryLabelColor(Color.RED);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);


        //设置圆心的字体样式以及展示的字体内容
        mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText(generateCenterSpannableText());

        //设置中心圆是否显示,显示则整个图表为空心圆，不显示则整个图表为实心圆
        mChart.setDrawHoleEnabled(true);

        //设置中心圆填充颜色为红色
        mChart.setHoleColor(Color.RED);
        //设置中心圆边框颜色为蓝色
        mChart.setTransparentCircleColor(Color.BLUE);
        //设置中心圆透明度
        mChart.setTransparentCircleAlpha(110);
        //设置中心圆内圆半径和外圆半径
        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        //设置是否在中心圆中绘制文字
        mChart.setDrawCenterText(true);
        //设置旋转的启示角度
        mChart.setRotationAngle(0);
        //设置是否旋转
        mChart.setRotationEnabled(true);
        //双击是否高亮显示
        mChart.setHighlightPerTapEnabled(false);
        //拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
        mChart.setDragDecelerationEnabled(true);
        //与上面那个属性配合，持续滚动时的速度快慢，[0,1) 0代表立即停止。
        mChart.setDragDecelerationFrictionCoef(0.99f);
        //添加监听者
        mChart.setOnChartValueSelectedListener(this);
        //设置数据
        setData(4, 100);
        //设置动画
        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_circle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleIcons: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawIcons(!set.isDrawIconsEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHole: {
                if (mChart.isDrawHoleEnabled())
                    mChart.setDrawHoleEnabled(false);
                else
                    mChart.setDrawHoleEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (mChart.isDrawCenterTextEnabled())
                    mChart.setDrawCenterText(false);
                else
                    mChart.setDrawCenterText(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleXVals: {

                mChart.setDrawEntryLabels(!mChart.isDrawEntryLabelsEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.actionTogglePercent:
                mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
                mChart.invalidate();
                break;
            case R.id.animateX: {
                mChart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1400, 1400);
                break;
            }
            case R.id.actionToggleSpin: {
                mChart.spin(1000, mChart.getRotationAngle(), mChart.getRotationAngle() + 360, Easing.EasingOption
                        .EaseInCubic);
                break;
            }
        }
        return true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText("" + (mSeekBarX.getProgress()));
        tvY.setText("" + (mSeekBarY.getProgress()));

        setData(mSeekBarX.getProgress(), mSeekBarY.getProgress());
    }

    private void setData(int count, float range) {

        //添加图表模块以及模块所占比例，名称，图标
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (int i = 0; i < count ; i++) {
            PieEntry pieEntry = new PieEntry((float) ((Math.random() * range) + range / 5),
                    "第"+i+"部分",
                    getResources().getDrawable(R.mipmap.star));
            entries.add(pieEntry);
        }
        //
        PieDataSet dataSet = new PieDataSet(entries, "模块比例");
        dataSet.setDrawIcons(false);

        //在图表外部对图表的每个部分进行文字描述,设置这些文字的位置和样式
        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);
        //设置图表中每个模块的背景颜色
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        //设置图表中每个模块所占比例的文字样式
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.RED);
        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // TODO Auto-generated method stub

    }

}
