package com.ohdoking.dansoondiary.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ohdoking.dansoondiary.db.DataBaseHelper;
import com.ohdoking.dansoondiary.dto.Diary;
import com.roomorama.caldroid.CalendarHelper;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import hirondelle.date4j.DateTime;

/**
 * Created by Administrator on 2016-02-26.
 */
public class DiaryDao {

    private DataBaseHelper dbHelper;
    private String[] DIARY_TABLE_COLUMNS = {DataBaseHelper.DIARYS_ID, DataBaseHelper.DIARYS_MEMO, DataBaseHelper.DIARYS_IMAGE, DataBaseHelper.DIARYS_DATE, DataBaseHelper.DIARYS_MONTH, DataBaseHelper.DIARYS_YEAR,DataBaseHelper.DIARYS_DAY };
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


        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = (c.get(Calendar.MONTH)+1);
        int day = c.get(Calendar.DAY_OF_MONTH);
        long startTime = c.getTimeInMillis();

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
        values.put(DataBaseHelper.DIARYS_DATE, startTime);
        values.put(DataBaseHelper.DIARYS_MONTH, month);
        values.put(DataBaseHelper.DIARYS_YEAR, year);
        values.put(DataBaseHelper.DIARYS_DAY, day);

        database.insert(DataBaseHelper.DIARYS_TABLE, null, values);
    }

    /**
     * Specific day Diary 추가하기
     * @param diary 다이어리 데이터
     * @return
     */
    public void addSpecificDayDiary(Diary diary,int year, int month, int day) {

        Long date = System.currentTimeMillis();
        int realMonth = month - 1;
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minite = c.get(Calendar.MINUTE);
        int seconds = c.get(Calendar.SECOND);
        Date date2 = new GregorianCalendar(year, month, day, hour, minite, seconds).getTime();

        DateTime tempdate = CalendarHelper.convertDateToDateTime(date2);

        c.set(year, realMonth, day, hour, minite, seconds);
        long startTime = c.getTimeInMillis();

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
        values.put(DataBaseHelper.DIARYS_DATE, startTime);
        values.put(DataBaseHelper.DIARYS_MONTH, month);
        values.put(DataBaseHelper.DIARYS_YEAR, year);
        values.put(DataBaseHelper.DIARYS_DAY, day);

        database.insert(DataBaseHelper.DIARYS_TABLE, null, values);
    }

    public boolean updateDiary(Diary diary){
        ContentValues values = new ContentValues();

        Gson gson = new Gson();
        String images= gson.toJson(diary.getImage());

        values.put(DataBaseHelper.DIARYS_MEMO, diary.getMemo());
        values.put(DataBaseHelper.DIARYS_IMAGE, images);

        String where = "id = ?";

        String[] whereArgs = { Integer.toString(diary.getId()) };
        boolean updateSuccessful = database.update(DataBaseHelper.DIARYS_TABLE, values, where, whereArgs) > 0;

        return updateSuccessful;
    }

    /**
     * id로 가져오기
     */
    public Diary getDiaryById(Integer id){
        String query = "SELECT * FROM "+DataBaseHelper.DIARYS_TABLE+" WHERE "+DataBaseHelper.DIARYS_ID+ "=" +id +";";

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();
        Diary diary = parseDiary(cursor);

        cursor.close();
        return diary;
    }
    /**
     * 날짜라 데이터 가져오기
     * @return
     */
    public ArrayList<Diary> getDiaryByDate(Integer year,Integer month,Integer day) {
        ArrayList<Diary> DIARYS_TABLE = new ArrayList<Diary>();

        String query = "SELECT * FROM "+DataBaseHelper.DIARYS_TABLE+" WHERE "
                +DataBaseHelper.DIARYS_YEAR+ "=" +year+" AND "
                +DataBaseHelper.DIARYS_MONTH+ "=" +month+" AND "
                +DataBaseHelper.DIARYS_DAY+ "=" +day
                +";";

        Cursor cursor = database.rawQuery(query, null);

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
     * Diary 데이터 삭제
     */
    public void deleteDiary(Integer id) {
        long _id = id;
        database.delete(DataBaseHelper.DIARYS_TABLE, DataBaseHelper.DIARYS_ID
                + " = " + _id, null);
    }

    /**
     * 모든 Diary 데이터 가져오기
     * @return 모든 Diary 데이터
     */
    public ArrayList<Diary> getAllDiary() {
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

    public ArrayList<Diary> getMonthDiary(Integer month, Integer year) {
        ArrayList<Diary> DIARYS_TABLE = new ArrayList<Diary>();

        /*Cursor cursor = database.query(DataBaseHelper.DIARYS_TABLE,
                DIARY_TABLE_COLUMNS, null, null, null, null, null);*/

        String query = "SELECT * FROM "+DataBaseHelper.DIARYS_TABLE+" WHERE "+DataBaseHelper.DIARYS_MONTH+ "=" +month +" AND "+DataBaseHelper.DIARYS_YEAR+ "="+ year+" ORDER BY "+DataBaseHelper.DIARYS_DATE+" DESC;";

        Cursor cursor = database.rawQuery(query, null);

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

//        Timestamp timestamp = new Timestamp(cursor.getInt(3));

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Integer>>() {}.getType();
        ArrayList<Integer> images = gson.fromJson(cursor.getString(2), type);

        Diary diary = new Diary();
        diary.setId((cursor.getInt(0)));
        diary.setImage(images);
        diary.setMemo(cursor.getString(1));
        diary.setDate(cursor.getLong(3));

        return diary;
    }

    /**
     * 현재 시간 가져오기
     * @return 현재 시간
     */
    private Long getNow(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return date.getTime();
    }



}