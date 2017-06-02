package com.shi.androidstudy.view.charting.interfaces.dataprovider;

import com.shi.androidstudy.view.charting.data.CandleData;

public interface CandleDataProvider extends BarLineScatterCandleBubbleDataProvider {

    CandleData getCandleData();
}
