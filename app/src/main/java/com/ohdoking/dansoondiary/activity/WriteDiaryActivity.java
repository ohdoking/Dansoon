package com.ohdoking.dansoondiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.dao.DiaryDao;
import com.ohdoking.dansoondiary.dto.Diary;
import com.ohdoking.dansoondiary.service.GridImageAdapter;

import java.sql.SQLException;
import java.util.ArrayList;

public class WriteDiaryActivity extends BaseAppCompatActivity {

    GridView gridView;
    GridImageAdapter gridImageAdapter;
    DiaryDao diaryDao;
    ArrayList<Integer> selectArrayList;

    ImageView backList;
    ImageView saveDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_diary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        selectArrayList = new ArrayList<Integer>();

        diaryDao = new DiaryDao(getApplicationContext());

        initId();
        setGridImageAdapter();

        clickEvent();
    }

    /**
     * id를 초기화
     */
    void initId(){
        backList = (ImageView) findViewById(R.id.moveMain);
        saveDiary = (ImageView) findViewById(R.id.saveDiary);

        gridView = (GridView) findViewById(R.id.gridView1);
    }

    /**
     * Grid Adapter를 세팅
     */
    void setGridImageAdapter(){
        gridImageAdapter = new GridImageAdapter(getApplicationContext());

        gridView.setAdapter(gridImageAdapter);

        gridImageAdapter.addImage(R.drawable.diary_baby_icon);
        gridImageAdapter.addImage(R.drawable.diary_presend_icon);
        gridImageAdapter.addImage(R.drawable.diary_baby_icon);
        gridImageAdapter.addImage(R.drawable.diary_presend_icon);
        gridImageAdapter.addImage(R.drawable.diary_baby_icon);
        gridImageAdapter.addImage(R.drawable.diary_presend_icon);

        gridImageAdapter.addImage(android.R.drawable.ic_menu_add);

    }

    /**
     * 클릭 이벤트
     */
    void clickEvent(){
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                int lastIconPostion = position+1;

                if(hasDuplicates(gridImageAdapter.getItem(position))){
                    //TODO 선택 해제 아이콘을 설정해야함
                }
                else{
                    //TODO 선택 아이콘을 설정해야함
                    selectArrayList.add(gridImageAdapter.getItem(position));
                }

                if(gridImageAdapter.getCount() == lastIconPostion){
                    Intent intent  = new Intent(WriteDiaryActivity.this, IconListActivity.class);
                    startActivity(intent);
                }
            }
        });

        backList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Diary diary = new Diary();
                diary.setImage(selectArrayList);
                try {
                    diaryDao.open();
                    diaryDao.addDiary(diary);
                    diaryDao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });


    }

    boolean hasDuplicates(Integer image) {
        for (Integer tempimage : selectArrayList) {
            Log.i("ohdoking1",image.toString() + " == " + tempimage);
            if (image.equals(tempimage)) {
                return true;
            }
        }
        return false;
    }
}
