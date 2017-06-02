package com.shi.androidstudy.view.charting.interfaces.dataprovider;

import com.shi.androidstudy.view.charting.components.YAxis;
import com.shi.androidstudy.view.charting.data.LineData;

public interface LineDataProvider extends BarLineScatterCandleBubbleDataProvider {

    LineData getLineData();

    YAxis getAxis(YAxis.AxisDependency dependency);
}
