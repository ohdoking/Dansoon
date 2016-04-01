package com.ohdoking.dansoondiary.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.dao.DiaryDao;
import com.ohdoking.dansoondiary.dto.Diary;
import com.ohdoking.dansoondiary.service.CustomProgressDialog;
import com.ohdoking.dansoondiary.service.DiaryRecyclerAdapter;
import com.ohdoking.dansoondiary.service.OnSwipeTouchListener;
import com.ohdoking.dansoondiary.service.RecyclerItemClickListener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainListActivity extends BaseAppCompatActivity {

    private ArrayList<Diary> diarysList;
    private RecyclerView mRecyclerView;
    private DiaryRecyclerAdapter adapter;

    LinearLayout listContentView;

    DiaryDao diaryDao;

    ImageView moveList;
    ImageView moveSetting;
    ImageView moveStatic;

    TextView yearAndMonth;

    Calendar listCurrentCalendar;

    CustomProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initId();
        diarysList = new ArrayList<Diary>();

        pDialog = new CustomProgressDialog(MainListActivity.this);

        setDateInView();
        setRecyclerView();


        menuClickEvent();
        ClickEvent();
        SwipeEvent();

    }



    @Override
    protected void onResume() {
        super.onResume();
        setDateInView();
        adapter.swap(diarysList);
    }

    @Override
    protected void onStop() {
        hidepDialog();
        super.onStop();
    }

    /**
     * id init
     */
    void initId(){

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        moveList = (ImageView) findViewById(R.id.moveList);
        moveStatic = (ImageView) findViewById(R.id.moveStatic);
        moveSetting = (ImageView) findViewById(R.id.moveSetting);

        yearAndMonth = (TextView) findViewById(R.id.yearAndMonth);

        listContentView = (LinearLayout) findViewById(R.id.listContentView);
    }

    /**
     * 최초 데이터 초기화
     */
    void setDateInView(){
        listCurrentCalendar = Calendar.getInstance();
        diarysList = getMonthDate(listCurrentCalendar.get(Calendar.MONTH)+1,listCurrentCalendar.get(Calendar.YEAR));
    }

    /**
     * Swipe 시 데이터 변경
     */
    void setSiwpeDateInView(int value){
        listCurrentCalendar.add(Calendar.MONTH, value);
        diarysList = getMonthDate(listCurrentCalendar.get(Calendar.MONTH)+1,listCurrentCalendar.get(Calendar.YEAR));
        adapter.swap(diarysList);
    }

    /**
     * recycle view set
     */
    void setRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DiaryRecyclerAdapter(MainListActivity.this, new ArrayList<Diary>());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Diary diary = adapter.getItem(position);

                        Intent i = new Intent(MainListActivity.this, DetailDiaryActivity.class);
                        i.putExtra("key",diary.getId());
                        startActivity(i);
                    }
                })
        );
        adapter.addAllItem(diarysList);

    }

    /**
     * Click Event
     */
    private void ClickEvent() {

        /**
         * Floating Button
         */
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainListActivity.this,WriteDiaryActivity.class);
                startActivityForResult(i,1);
            }
        });
    }

    /**
     * menu click event
     */
    void menuClickEvent(){
        moveList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                showpDialog();

                Intent intent = new Intent(MainListActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        moveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                showpDialog();

                Intent intent = new Intent(MainListActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });
        moveStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                showpDialog();

                Intent intent = new Intent(MainListActivity.this, StaticsActivity.class);
                startActivity(intent);

                finish();
            }
        });
        moveList.setOnTouchListener(menuTouchListenr);
        moveSetting.setOnTouchListener(menuTouchListenr);
        moveStatic.setOnTouchListener(menuTouchListenr);
    }

    /**
     * Swipe Event
     */
    private void SwipeEvent() {
        listContentView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            @Override
            public void onSwipeLeft() {
                setSiwpeDateInView(1);
            }

            @Override
            public void onSwipeRight() {
                setSiwpeDateInView(-1);
            }
        });
        mRecyclerView.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()) {
            @Override
            public void onSwipeLeft() {
                setSiwpeDateInView(1);
            }

            @Override
            public void onSwipeRight() {
                setSiwpeDateInView(-1);
            }
        });
    }

    /**
     * 해당 년 월에 대한 데이터를 가져옴
     */
    ArrayList<Diary> getMonthDate(Integer month, Integer year){

        yearAndMonth.setText(year+"년 "+month+"월");

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                setDateInView();
                adapter.swap(diarysList);
                mRecyclerView.refreshDrawableState();
                break;

            default:
                break;
        }
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
