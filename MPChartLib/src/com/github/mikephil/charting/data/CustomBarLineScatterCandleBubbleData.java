
package com.github.mikephil.charting.data;

import android.graphics.Bitmap;

import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;

import java.util.List;

/**
 * Baseclass for all Line, Bar, Scatter, Candle and Bubble data.
 * 
 * @author Philipp Jahoda
 */
public abstract class CustomBarLineScatterCandleBubbleData<T extends IBarLineScatterCandleBubbleDataSet<? extends Entry>>
        extends CustomChartData<T> {

    public CustomBarLineScatterCandleBubbleData() {
        super();
    }

    public CustomBarLineScatterCandleBubbleData(List<Bitmap> xVals) {
        super(xVals);
    }

    public CustomBarLineScatterCandleBubbleData(Bitmap[] xVals) {
        super(xVals);
    }

    public CustomBarLineScatterCandleBubbleData(List<Bitmap> xVals, List<T> sets) {
        super(xVals, sets);
    }

    public CustomBarLineScatterCandleBubbleData(Bitmap[] xVals, List<T> sets) {
        super(xVals, sets);
    }
}
