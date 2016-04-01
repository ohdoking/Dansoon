package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.CustomBarLineScatterCandleBubbleData;
import com.github.mikephil.charting.utils.Transformer;

public interface CustomBarLineScatterCandleBubbleDataProvider extends CustomChartInterface {

    Transformer getTransformer(AxisDependency axis);
    int getMaxVisibleCount();
    boolean isInverted(AxisDependency axis);
    
    int getLowestVisibleXIndex();
    int getHighestVisibleXIndex();

    CustomBarLineScatterCandleBubbleData getData();
}
