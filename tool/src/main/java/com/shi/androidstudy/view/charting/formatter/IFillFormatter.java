package com.shi.androidstudy.view.charting.formatter;

import com.shi.androidstudy.view.charting.interfaces.dataprovider.LineDataProvider;
import com.shi.androidstudy.view.charting.interfaces.datasets.ILineDataSet;

/**
 * Interface for providing a custom logic to where the filling menu_line of a LineDataSet
 * should end. This of course only works if setFillEnabled(...) is set to true.
 * 
 * @author Philipp Jahoda
 */
public interface IFillFormatter
{

    /**
     * Returns the vertical (y-axis) position where the filled-menu_line of the
     * LineDataSet should end.
     * 
     * @param dataSet the ILineDataSet that is currently drawn
     * @param dataProvider
     * @return
     */
    float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider);
}
