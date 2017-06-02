package com.shi.androidstudy.view.charting.interfaces.dataprovider;

import com.shi.androidstudy.view.charting.data.ScatterData;

public interface ScatterDataProvider extends BarLineScatterCandleBubbleDataProvider {

    ScatterData getScatterData();
}
