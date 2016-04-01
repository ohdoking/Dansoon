package com.github.mikephil.charting.interfaces.dataprovider;

import com.github.mikephil.charting.data.CustomBarData;

public interface CustomBarDataProvider extends CustomBarLineScatterCandleBubbleDataProvider {


    CustomBarData getBarData();
    boolean isDrawBarShadowEnabled();
    boolean isDrawValueAboveBarEnabled();
    boolean isDrawHighlightArrowEnabled();
}
