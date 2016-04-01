package com.ohdoking.dansoondiary.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.CustomHorizontalBarChart;
import com.github.mikephil.charting.charts.CustomPieChart;
import com.github.mikephil.charting.components.CustomXAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CustomBarData;
import com.github.mikephil.charting.data.CustomPieData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.common.DsStatic;
import com.ohdoking.dansoondiary.dao.DiaryDao;
import com.ohdoking.dansoondiary.dto.Diary;
import com.ohdoking.dansoondiary.dto.DsIcon;
import com.roomorama.caldroid.CalendarHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import hirondelle.date4j.DateTime;

public class StaticsActivity extends BaseAppCompatActivity implements OnChartValueSelectedListener {

    private CustomPieChart mChart;
    protected CustomHorizontalBarChart barChart;

    private Typeface tf;
    ImageView moveList;

    DiaryDao diaryDao;

    private ArrayList<Diary> diarysList;
    ArrayList<DsIcon> iconList;
    Integer totalIconListCount = 0;

    DateTime tempdate;

    ImageView topIcon;
    ImageView secIcon;
    ImageView thirdIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        moveList = (ImageView) findViewById(R.id.moveList);
        topIcon = (ImageView) findViewById(R.id.top1);
        secIcon = (ImageView) findViewById(R.id.top2);
        thirdIcon = (ImageView) findViewById(R.id.top3);
        diaryDao = new DiaryDao(getApplicationContext());

        Date date = new Date(System.currentTimeMillis());
        tempdate = CalendarHelper.convertDateToDateTime(date);

        diarysList = getMonthDate(tempdate.getMonth(),tempdate.getYear());
        iconList = caculateRank(diarysList);
        Collections.sort(iconList,DsIcon.iconCount);

//        getTopThree();

        for(DsIcon ds:iconList){
            Log.i("ohdoking5",ds.getIcon()+" : " + ds.getCount());
        }


        moveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        moveList.setOnTouchListener(menuTouchListenr);

        if(diarysList.size() != 0){
            initIconRank();
            initPieChart();
//            initBarChart();
        }

    }

    void initIconRank(){
        //icon top 3 init

        for(int i=0 ; i < diarysList.size() ; i++){

            if(i == 0){
                topIcon.setImageResource(DsStatic.buttonList69[iconList.get(0).getIcon()]);
            }
            else if(i == 1){
                secIcon.setImageResource(DsStatic.buttonList69[iconList.get(1).getIcon()]);
            }
            else if(i == 2){
                thirdIcon.setImageResource(DsStatic.buttonList69[iconList.get(2).getIcon()]);
            }
        }
    }

    /**
     * PieChart 초기화
     */
    void initPieChart(){
        mChart = (CustomPieChart) findViewById(R.id.chart1);

        tf = Typeface.createFromAsset(getAssets(), "fonts/thejunggodic.ttf");

        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "fonts/thejunggodic130.ttf"));
        mChart.setCenterText(generateCenterSpannableText());

       // mChart.setCenterText(tempdate.getYear().toString()+"년\n"+tempdate.getMonth().toString()+"월");
        mChart.setCenterTextSize(30f);
        mChart.setDescription("");
        /*mChart.setDescription(tempdate.getMonth().toString()+"월");
        mChart.setDescriptionTextSize(15f);*/

        mChart.setUsePercentValues(true);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);


        mChart.setDrawHoleEnabled(true);
//        mChart.setHoleColor(Color.TRANSPARENT);

//        mChart.setHoleColorTransparent(true);
        /*
        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);*/
        mChart.setHoleRadius(60f);
        mChart.setTransparentCircleRadius(60f);
        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        setData(3, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);
/*
        Legend l = mChart.getLegend();
        l.setForm(Legend.LegendForm.SQUARE);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);*/

    }

    /**
     * BarChart 초기화
     */
    void initBarChart(){
//        barChart = (CustomHorizontalBarChart) findViewById(R.id.barchart);
//        barChart.setOnChartValueSelectedListener(this);
        // mChart.setHighlightEnabled(false);

        barChart.setDrawBarShadow(false);

        barChart.setDrawValueAboveBar(true);

        barChart.setDescription("");

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        // draw shadows for each bar that show the maximum value
        // mChart.setDrawBarShadow(true);

        // mChart.setDrawXLabels(false);

        barChart.setDrawGridBackground(false);
//         barChart.setDrawYLabels(false);

//        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        CustomXAxis xl = barChart.getXAxis();
        xl.setPosition(CustomXAxis.CustomXAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(false);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0f);


        YAxis yl = barChart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0f);
        yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        yl.setDrawLabels(false);

        YAxis yr = barChart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(false);
        yr.setDrawGridLines(false);
        yr.setGridLineWidth(0f);
        yr.setAxisMinValue(0f); // this replaces setStartAtZero(true)
        yr.setDrawLabels(false);
//        yr.setInverted(true);

        setBarChartData(12, 50);
        barChart.animateY(2500);


        /*Legend l = barChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setFormSize(8f);
        l.setXEntrySpace(4f)*/;

//         barChart.setDrawLegend(false);
    }

    /**
     * Barchart 데이터 초기화
     * @param count
     * @param range
     */
    private void setBarChartData(int count, float range) {

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        ArrayList<Bitmap> xVals = new ArrayList<Bitmap>();

        /*for (int i = 0; i < count; i++) {
            xVals.add(mMonths[i % 12]);
            yVals1.add(new BarEntry((float) (Math.random() * range), i));
        }*/

        ArrayList<DsIcon> barCharList = iconList;
        Collections.reverse(barCharList);
        /*for(int i = 0 ; i < barCharList.size() ; i++){
            yVals1.add(new BarEntry(barCharList.get(i).getCount(), i));
            xVals.add(mParties[i % mParties.length]);
        }*/

        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int i = 0 ; i < barCharList.size() ; i++){
            yVals1.add(new BarEntry(barCharList.get(i).getCount(), i));
            colors.add(DsStatic.buttonColor[barCharList.get(i).getIcon()]);
            Resources res = getResources();
            BitmapDrawable bd = (BitmapDrawable)res.getDrawable(DsStatic.buttonList69[barCharList.get(i).getIcon()]);
            Bitmap bit = bd.getBitmap();
            xVals.add(bit);
        }


        BarDataSet set1 = new BarDataSet(yVals1, "");


        /*for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
*/
//        colors.add(ColorTemplate.getHoloBlue());

        set1.setColors(colors);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);

        CustomBarData data = new CustomBarData(xVals, dataSets);
        data.setValueTextSize(30f);
        data.setValueTypeface(tf);

        barChart.setData(data);
    }



    /**
     * PieChart 데이터 초기화
     * @param count
     * @param range
     */
    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
/*
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }
*/


        ArrayList<Bitmap> xVals = new ArrayList<Bitmap>();

/*
        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);
*/

        ArrayList<DsIcon> barCharList = iconList;
        ArrayList<Integer> colors = new ArrayList<Integer>();


        Integer iconSize = iconList.size();
        if(iconSize > 10){
            iconSize = 10;
        }

        for(int i = 0 ; i < iconSize ; i++){
            yVals1.add(new Entry(iconList.get(i).getCount(), i));
            colors.add(DsStatic.buttonColor[iconList.get(i).getIcon()]);
            Resources res = getResources();
            BitmapDrawable bd = (BitmapDrawable)res.getDrawable(DsStatic.buttonList69[iconList.get(i).getIcon()]);
            Bitmap bit = bd.getBitmap();
            xVals.add(bit);
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        /*dataSet.setSliceSpace(3f);*/
        dataSet.setSelectionShift(10f);

        // add a lot of colors



       /* for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/

//        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        CustomPieData data = new CustomPieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
//        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString(tempdate.getYear().toString()+"년\n"+tempdate.getMonth().toString()+"월");
        s.setSpan(new RelativeSizeSpan(.8f), 0, 4, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length() - 5, 0);
        s.setSpan(new RelativeSizeSpan(1.4f), 5, 8, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
}


    /**
     * 해당 년 월에 대한 데이터를 가져옴
     */
    ArrayList<Diary> getMonthDate(Integer month, Integer year){

        ArrayList<Diary> diaryArrayList = new ArrayList<Diary>();
        diaryDao = new DiaryDao(getApplicationContext());
        try {
            diaryDao.open();
            diaryArrayList = diaryDao.getMonthDiary(month, year);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            diaryDao.close();
        }
        return  diaryArrayList;
    }

    /**
     * Rank를 계산하기 위해 아이콘 사용 횟수 대로 ArrayList에 넣음
     * @param list
     * @return
     */
    private ArrayList<DsIcon> caculateRank(ArrayList<Diary> list){
        ArrayList<DsIcon> iconList = new ArrayList<DsIcon>();
        for (Diary diary : list){
            for(Integer icon : diary.getImage()){
                totalIconListCount++;
                int input = 0 ;
                if(iconList.size() == 0){
                    DsIcon temp = new DsIcon();
                    temp.setIcon(icon);
                    temp.setCount(1);
                    iconList.add(temp);
                }
                else{
                    DsIcon temp = new DsIcon();
                    for(int i = 0 ; i < iconList.size() ; i++){
                        DsIcon dsIcon = iconList.get(i);
                        temp.setIcon(icon);
                        if (dsIcon.getIcon().equals(icon)) {
                            temp.setCount((dsIcon.getCount()+1));
                            iconList.set(i,temp);
                            input = 1;
                            break;
                        }
                    }
                    if(input == 0){
                        temp.setCount(1);
                        iconList.add(temp);
                    }
                }
            }
        }


        return iconList;
    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHole: {
                if (mChart.isDrawHoleEnabled())
                    mChart.setDrawHoleEnabled(false);
                else
                    mChart.setDrawHoleEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (mChart.isDrawCenterTextEnabled())
                    mChart.setDrawCenterText(false);
                else
                    mChart.setDrawCenterText(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleXVals: {

                mChart.setDrawSliceText(!mChart.isDrawSliceTextEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "Pictures/Dansoon");
                break;
            }
            case R.id.actionTogglePercent:
                mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
                mChart.invalidate();
                break;
            case R.id.animateX: {
                mChart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1400, 1400);
                break;
            }
        }
        return true;
    }*/


}
