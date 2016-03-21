package com.ohdoking.dansoondiary.service;

import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-03-15.
 */
public class CustomPieData extends ChartData<IPieDataSet> {

    public CustomPieData() {
        super();
    }

    public CustomPieData(List<String> xVals) {
        super(xVals);
    }

    public CustomPieData(String[] xVals) {
        super(xVals);
    }

    public CustomPieData(List<String> xVals, IPieDataSet dataSet) {
        super(xVals, toList(dataSet));
    }

    public CustomPieData(String[] xVals, IPieDataSet dataSet) {
        super(xVals, toList(dataSet));
    }

    private static List<IPieDataSet> toList(IPieDataSet dataSet) {
        List<IPieDataSet> sets = new ArrayList<IPieDataSet>();
        sets.add(dataSet);
        return sets;
    }

    /**
     * Sets the PieDataSet this data object should represent.
     *
     * @param dataSet
     */
    public void setDataSet(IPieDataSet dataSet) {
        mDataSets.clear();
        mDataSets.add(dataSet);
        init();
    }

    /**
     * Returns the DataSet this PieData object represents. A PieData object can
     * only contain one DataSet.
     *
     * @return
     */
    public IPieDataSet getDataSet() {
        return mDataSets.get(0);
    }

    /**
     * The PieData object can only have one DataSet. Use getDataSet() method instead.
     *
     * @param index
     * @return
     */
    @Override
    public IPieDataSet getDataSetByIndex(int index) {
        return index == 0 ? getDataSet() : null;
    }

    @Override
    public IPieDataSet getDataSetByLabel(String label, boolean ignorecase) {
        return ignorecase ? label.equalsIgnoreCase(mDataSets.get(0).getLabel()) ? mDataSets.get(0)
                : null : label.equals(mDataSets.get(0).getLabel()) ? mDataSets.get(0) : null;
    }

    /**
     * Returns the sum of all values in this PieData object.
     *
     * @return
     */
    public float getYValueSum() {

        float sum = 0;

        for (int i = 0; i < getDataSet().getEntryCount(); i++)
            sum += getDataSet().getEntryForIndex(i).getVal();


        return sum;
    }
}
