package com.ohdoking.dansoondiary.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ohdoking.dansoondiary.db.DataBaseHelper;
import com.ohdoking.dansoondiary.dto.Diary;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Administrator on 2016-02-26.
 */
public class DiaryDao {

    private DataBaseHelper dbHelper;
    private String[] DIARY_TABLE_COLUMNS = {DataBaseHelper.DIARYS_ID, DataBaseHelper.DIARYS_DATE, DataBaseHelper.DIARYS_IMAGE, DataBaseHelper.DIARYS_MEMO};
    private SQLiteDatabase database;

    public DiaryDao(Context context) {
        dbHelper = new DataBaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Diary 추가하기
     * @param diary 다이어리 데이터
     * @return
     */
    public void addDiary(Diary diary) {

        String date = getNow();
        Gson gson = new Gson();
        String images= gson.toJson(diary.getImage());

        ContentValues values = new ContentValues();

        if(diary.getMemo() != null){
            values.put(DataBaseHelper.DIARYS_MEMO, diary.getMemo());
        }
        else{
            values.put(DataBaseHelper.DIARYS_MEMO, "none");
        }
        values.put(DataBaseHelper.DIARYS_IMAGE, images);
        values.put(DataBaseHelper.DIARYS_DATE, date);

        database.insert(DataBaseHelper.DIARYS_TABLE, null, values);
    }

    /**
     * Diary 데이터 삭제
     * @param comment
     */
    public void deleteDiary(Diary comment) {
        long id = comment.getId();
        database.delete(DataBaseHelper.DIARYS_TABLE, DataBaseHelper.DIARYS_ID
                + " = " + id, null);
    }



    /**
     * 모든 Diary 데이터 가져오기
     * @return 모든 Diary 데이터
     */
    public ArrayList<Diary> getAllDIARYS_TABLE() {
        ArrayList<Diary> DIARYS_TABLE = new ArrayList<Diary>();

        Cursor cursor = database.query(DataBaseHelper.DIARYS_TABLE,
                DIARY_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Diary diary = parseDiary(cursor);
            DIARYS_TABLE.add(diary);
            cursor.moveToNext();
        }

        cursor.close();
        return DIARYS_TABLE;
    }

    /**
     * Diary 데이터 파싱
     * @param cursor
     * @return
     */
    private Diary parseDiary(Cursor cursor) {

        Timestamp timestamp = new Timestamp(Long.valueOf(cursor.getString(3)));
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        ArrayList<Integer> images = gson.fromJson(cursor.getString(2), type);

        Diary diary = new Diary();
        diary.setId((cursor.getInt(0)));
        diary.setImage(images);
        diary.setMemo(cursor.getString(2));
//        diary.setDate(new Date(timestamp.getTime()));

        return diary;
    }

    /**
     * 현재 시간 가져오기
     * @return 현재 시간
     */
    private String getNow(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp.toString();
    }
}