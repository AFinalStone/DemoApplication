package com.shi.androidstudy.view.charting.interfaces.datasets;

import android.graphics.drawable.Drawable;

import com.shi.androidstudy.view.charting.data.Entry;

/**
 * Created by Philipp Jahoda on 21/10/15.
 */
public interface ILineRadarDataSet<T extends Entry> extends ILineScatterCandleRadarDataSet<T> {

    /**
     * Returns the color that is used for filling the menu_line surface area.
     *
     * @return
     */
    int getFillColor();

    /**
     * Returns the drawable used for filling the area below the menu_line.
     *
     * @return
     */
    Drawable getFillDrawable();

    /**
     * Returns the alpha value that is used for filling the menu_line surface,
     * default: 85
     *
     * @return
     */
    int getFillAlpha();

    /**
     * Returns the stroke-width of the drawn menu_line
     *
     * @return
     */
    float getLineWidth();

    /**
     * Returns true if filled drawing is enabled, false if not
     *
     * @return
     */
    boolean isDrawFilledEnabled();

    /**
     * Set to true if the DataSet should be drawn filled (surface), and not just
     * as a menu_line, disabling this will give great performance boost. Please note that this method
     * uses the canvas.clipPath(...) method for drawing the filled area.
     * For devices with API level < 18 (Android 4.3), hardware acceleration of the chart should
     * be turned off. Default: false
     *
     * @param enabled
     */
    void setDrawFilled(boolean enabled);
}
