package com.ohdoking.dansoondiary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016-02-26.
 */
public class DataBaseHelper extends SQLiteOpenHelper{

    public static final String DIARYS_TABLE = "diarys";
    public static final String DIARYS_ID = "id";
    public static final String DIARYS_DATE = "date";
    public static final String DIARYS_IMAGE = "image";
    public static final String DIARYS_MEMO = "memo";
    public static final String DIARYS_MONTH = "month";
    public static final String DIARYS_YEAR = "year";
    public static final String DIARYS_DAY = "day";


    private static final String DATABASE_NAME = "Diary.db";
    private static final int DATABASE_VERSION = 1;

    // creation SQLite statement
    private static final String CREATE_DIARYS_TABLE= "create table " + DIARYS_TABLE
            + "(" + DIARYS_ID + " integer primary key autoincrement, "
            + DIARYS_MEMO + " text ,"
            + DIARYS_IMAGE + " text not null,"
            + DIARYS_DATE + " numeric not null, "
            + DIARYS_MONTH + " integer not null, "
            + DIARYS_YEAR + " integer not null,"
            + DIARYS_DAY + " integer not null"
            + ");";


    private static DataBaseHelper instance;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARYS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS "+DATABASE_NAME;
        db.execSQL(sql);

        onCreate(db);
    }
}

