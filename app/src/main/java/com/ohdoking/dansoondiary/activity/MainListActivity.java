package com.ohdoking.dansoondiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.dto.Diary;
import com.ohdoking.dansoondiary.service.DiaryRecyclerAdapter;

import java.util.ArrayList;
import java.util.Date;

public class MainListActivity extends AppCompatActivity {

    private ArrayList<Diary> diarysList;
    private RecyclerView mRecyclerView;
    private DiaryRecyclerAdapter adapter;

    ImageView moveList;
    ImageView moveSetting;
    ImageView moveStatic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        diarysList = new ArrayList<Diary>();

        initId();
        menuClickEvent();
        setRecyclerView();
        ClickEvent();

    }

    /**
     * id init
     */
    void initId(){

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        moveList = (ImageView) findViewById(R.id.moveList);
        moveStatic = (ImageView) findViewById(R.id.moveStatic);
        moveSetting = (ImageView) findViewById(R.id.moveSetting);
    }

    /**
     * recycle view set
     */
    void setRecyclerView(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DiaryRecyclerAdapter(MainListActivity.this, new ArrayList<Diary>());
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);


        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(R.mipmap.ic_launcher);
        list.add(R.mipmap.ic_launcher);
        list.add(R.mipmap.ic_launcher);
        Date date = new Date();
        Diary d = new Diary(list,"memo",date.getTime());


        adapter.addItem(d);
        adapter.addItem(d);
        adapter.addItem(d);
        adapter.addItem(d);
        adapter.addItem(d);

        ArrayList<Integer> list2 = new ArrayList<Integer>();
        list2.add(R.mipmap.ic_launcher);
        list2.add(R.mipmap.ic_launcher);

        Diary d3 = new Diary(list,"memo2",date.getTime());
        adapter.addItem(d);
        adapter.addItem(d);
        adapter.addItem(d);
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
                startActivity(i);
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
                Intent intent = new Intent(MainListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        moveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainListActivity.this, SettingActivity.class);
                startActivity(intent);

            }
        });
        moveStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainListActivity.this, StaticsActivity.class);
                startActivity(intent);
            }
        });
    }

}
