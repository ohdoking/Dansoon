
package com.github.mikephil.charting.data;

import android.graphics.Bitmap;

import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Data container for the RadarChart.
 *
 * @author Philipp Jahoda
 */
public class CustomRadarData extends CustomChartData<IRadarDataSet> {

    public CustomRadarData() {
        super();
    }

    public CustomRadarData(List<Bitmap> xVals) {
        super(xVals);
    }

    public CustomRadarData(Bitmap[] xVals) {
        super(xVals);
    }

    public CustomRadarData(List<Bitmap> xVals, List<IRadarDataSet> dataSets) {
        super(xVals, dataSets);
    }

    public CustomRadarData(Bitmap[] xVals, List<IRadarDataSet> dataSets) {
        super(xVals, dataSets);
    }

    public CustomRadarData(List<Bitmap> xVals, IRadarDataSet dataSet) {
        super(xVals, toList(dataSet));
    }

    public CustomRadarData(Bitmap[] xVals, IRadarDataSet dataSet) {
        super(xVals, toList(dataSet));
    }

    private static List<IRadarDataSet> toList(IRadarDataSet dataSet) {
        List<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(dataSet);
        return sets;
    }
}
