package com.shi.androidstudy.view.charting.interfaces.dataprovider;

import com.shi.androidstudy.view.charting.components.YAxis.AxisDependency;
import com.shi.androidstudy.view.charting.data.BarLineScatterCandleBubbleData;
import com.shi.androidstudy.view.charting.utils.Transformer;

public interface BarLineScatterCandleBubbleDataProvider extends ChartInterface {

    Transformer getTransformer(AxisDependency axis);
    boolean isInverted(AxisDependency axis);
    
    float getLowestVisibleX();
    float getHighestVisibleX();

    BarLineScatterCandleBubbleData getData();
}
